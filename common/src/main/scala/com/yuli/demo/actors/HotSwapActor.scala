package com.yuli.demo.actors

import akka.actor.{Actor, ActorLogging, Stash}
import com.yuli.demo.command.{Connected, Disconnected, GetRequest}

/**
 * 热交换actor
 */
class HotSwapActor extends Actor with Stash with ActorLogging {
  override def receive: Receive = {
    //必须写入message，不染stash不生效
    case message:GetRequest => stash
    case Connected => {
      context.become(online)
      unstashAll
    }

  }
  def online:Receive ={
    case message:GetRequest => processMessage(message)
    case Disconnected => context.unbecome
  }
  def processMessage(message:GetRequest): Unit ={
    log.info(message.message)
  }
}
