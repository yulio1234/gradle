package com.yuli.demo

import akka.actor.{ActorRef, ActorSystem, Props}
import com.yuli.demo.actors.{CacheActor, CoreActor, HttpActor, ParserActor}
import com.yuli.demo.command.BodyRequest



object HtmlMain extends App{
  private val actorSystem = ActorSystem("htmlParser")
  actorSystem.actorOf(Props[HttpActor],"httpActor")
  actorSystem.actorOf(Props[CacheActor],"cacheActor")
  actorSystem.actorOf(Props[ParserActor],"parserActor")
  private val coreActor: ActorRef = actorSystem.actorOf(Props(classOf[CoreActor],"/user/cacheActor","/user/httpActor","/user/parserActor"),"coreActor")
  coreActor ! BodyRequest("1")

}
