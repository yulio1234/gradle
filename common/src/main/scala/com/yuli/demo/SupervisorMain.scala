package com.yuli.demo

import akka.actor.{ActorSystem, Props}
import com.yuli.demo.actors.SupervisorActor

object SupervisorMain extends App {
  private val actorSystem = ActorSystem("supervisor")
  actorSystem.actorOf(Props[SupervisorActor]) ! "test"
}
