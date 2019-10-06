package com.yuli.demo.actors

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props, SupervisorStrategy}

/**
 * 测试重写监督策略
 */
class SupervisorActor extends Actor with ActorLogging{

  /**
   * 重写监督策略，重试三次，重试不会再发送已发送到消息，并且重试结束后直接关闭子actor
   * @return
   */
  override def supervisorStrategy: SupervisorStrategy = {
    OneForOneStrategy(maxNrOfRetries = 4){
      case _:Exception => Restart
    }
  }
  override def receive: Receive = {
    case "test" => {
      val actor = context.actorOf(Props(new Actor {
        log.info("actor创建")
        override def receive: Receive = {
          case _=>throw new Exception
        }
      }))
    actor ! "test"
    actor ! "test"
    actor ! "test"
    actor ! "test"
    actor ! "test"
    actor ! "test"
    actor ! "test"
    actor ! "test"
    actor ! "test"
    actor ! "test"
    actor ! "test"

    }
  }
}
