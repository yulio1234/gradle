package com.yuli.demo.actors

import java.util.concurrent.TimeUnit

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, OneForOneStrategy, Props, SupervisorStrategy}
import com.yuli.demo.command._

import scala.concurrent.TimeoutException
import scala.concurrent.duration.Duration

class CoreActor(cacheActorPatch:String,httpActorPath:String,parserActorPath:String) extends Actor with ActorLogging{
  private val cacheActor: ActorSelection = context.actorSelection(cacheActorPatch)
  private val httpActor: ActorSelection = context.actorSelection(httpActorPath)
  private val parserActor: ActorSelection = context.actorSelection(parserActorPath)
  override def receive: Receive = {
    case BodyRequest(uri) =>{
      val extraActor = buildExtraActor(sender())
      cacheActor.tell(GetCacheRequest(uri),extraActor)
      httpActor.tell(HttpRequest(uri),extraActor)
      context.system.scheduler.scheduleOnce(Duration(3,TimeUnit.SECONDS),extraActor,TimeoutRequest)(context.dispatcher)
    }
  }
  /**
   * 重写监督策略，重试三次
   * @return
   */
  override def supervisorStrategy: SupervisorStrategy = {
    OneForOneStrategy(maxNrOfRetries = 4){
      case _:Exception => Restart
    }
  }


  /**
   * 额外的actor，用于处理请求
   */
  private def buildExtraActor(senderRef: ActorRef):ActorRef={
    context.actorOf(Props(new Actor {

      override def supervisorStrategy: SupervisorStrategy = {super.supervisorStrategy}

      override def receive: Receive = {
        //如果超时就停止actor
        case TimeoutRequest => {
          log.error("请求超时")
          senderRef ! new TimeoutException("get body was timeout!")
          context.stop(self)
        }
          //如果
        case CacheResponse(body) => {
          senderRef ! BodyResponse(body)
          context.stop(self)
        }
          //如果是html返回请求，就去解析
        case HttpResponse(uri,html) => {
          parserActor ! ParserRequest(uri,html)
        }
          //如果是解析返回请求，就缓存,并返回发送者
        case ParserResponse(uri,body) =>{
          cacheActor ! PutCacheRequest(uri,body)
          senderRef ! BodyResponse(body)
          context.stop(self)
        }
      }
    }))
  }

}
