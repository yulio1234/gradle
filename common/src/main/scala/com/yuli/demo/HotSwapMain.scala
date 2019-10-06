package com.yuli.demo

import akka.actor.{ActorRef, ActorSystem, Props}
import com.yuli.demo.actors.HotSwapActor
import com.yuli.demo.command.{Connected, Disconnected, GetRequest}

object HotSwapMain extends App {
  private val actorSystem = ActorSystem("hotSwap")
  private val ref: ActorRef = actorSystem.actorOf(Props[HotSwapActor])
  ref ! GetRequest("1")
  ref ! GetRequest("2")
  ref ! GetRequest("3")
  ref ! GetRequest("4")
  ref ! GetRequest("5")
  ref ! GetRequest("6")
  ref ! Connected
  ref ! GetRequest("7")
  ref ! GetRequest("8")
  ref ! GetRequest("9")
  ref ! GetRequest("10")
  ref ! Disconnected
}
