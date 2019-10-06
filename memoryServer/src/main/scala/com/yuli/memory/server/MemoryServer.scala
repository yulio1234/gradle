package com.yuli.memory.server

import akka.actor.{Actor, ActorLogging}
import com.yuli.memory.domain.{GetRequest, SetRequest}

import scala.collection.mutable

class MemoryServer extends Actor with ActorLogging{
  val map = new mutable.HashMap[String,Any]
  override def receive: Receive = {
    case GetRequest(key) => {
      log.info("获取数据:{}",key)
      val option = map.get(key)
      sender() ! option
    }
    case SetRequest(key,value) => {
      log.info("存储数据key:{},value:{}",key,value)
      map.put(key,value)
    }
  }
}
