package com.yuli.demo

import akka.actor.{ActorRef, ActorSystem, Kill, PoisonPill}
import com.yuli.demo.actors.DatabaseActor

object DatabaseMain extends App {
  private val actorSystem = ActorSystem("database")
  private val ref: ActorRef = actorSystem.actorOf(DatabaseActor.props("192.168.0.1",80))
//  ref ! PoisonPill
//  ref ! Kill
}
