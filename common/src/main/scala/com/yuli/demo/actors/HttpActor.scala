package com.yuli.demo.actors

import akka.actor.{Actor, ActorLogging}
import com.yuli.demo.command.{HttpRequest, HttpResponse}

class HttpActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case HttpRequest(uri)=>{
      log.info("接收到http文章获取请求")
      sender() ! HttpResponse(uri,"<html>hello world</html>")
    }
  }

}
