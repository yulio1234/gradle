package com.yuli.memory.server

import akka.actor.{ActorRef, ActorSystem, Props}

object MemoryServerMain extends App {
  private val actorSystem = ActorSystem("memoryServer")
  private val server: ActorRef = actorSystem.actorOf(Props[MemoryServer],"server")

}
