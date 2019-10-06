package com.yuli.demo.actors

import akka.actor.{Actor, ActorLogging}
import com.yuli.demo.command.{CacheResponse, GetCacheRequest, PutCacheRequest}

import scala.collection.mutable

/**
 * 缓存Actor
 */
class CacheActor extends Actor with ActorLogging{
  val map = new mutable.HashMap[String,String];
  override def receive: Receive = {
    //查询到就返回数据，没有查询到就不管
    case GetCacheRequest(uri) => {
      val body = map.get(uri)
      log.info("接收到缓存查询消息:{}",body)
      body match {
        case Some(value) => sender() ! CacheResponse(value)
        case None => {
          log.info("没有查询到消息")
          sender() ! "none"
        }
      }
    }
    //添加数据到缓存中
    case PutCacheRequest(uri,body)=>{
      log.info("收到数据缓存请求:{}",body)
      map.put(uri,body)
    }
  }
}
