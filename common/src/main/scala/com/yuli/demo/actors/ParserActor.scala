package com.yuli.demo.actors

import akka.actor.{Actor, ActorLogging}
import com.yuli.demo.command.{ParserRequest, ParserResponse}

class ParserActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case ParserRequest(uri,html) => {
      val body = html.replaceAll("<html>","").replaceAll("</html>","")
      log.info("收到解析html请求：{}",body)
      sender() ! ParserResponse(uri,body)
    }
  }
}
