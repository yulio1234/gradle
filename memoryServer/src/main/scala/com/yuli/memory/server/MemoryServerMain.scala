package com.yuli.memory.server

import akka.actor.{ActorSystem, Props}

object MemoryServerMain extends App {
  private val actorSystem = ActorSystem("memoryServer")
  actorSystem.actorOf(Props[MemoryServer],"server")
}
