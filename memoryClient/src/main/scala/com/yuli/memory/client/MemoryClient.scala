package com.yuli.memory.client

import java.util.concurrent.TimeUnit
import akka.pattern.ask
import akka.actor.ActorSystem
import akka.util.Timeout
import com.yuli.memory.domain.{GetRequest, SetRequest}
class MemoryClient(host:String){
    private implicit val timeout = Timeout(2 ,TimeUnit.SECONDS)
    private implicit val system = ActorSystem("LocalSystem")

  private val remoteDb = system.actorSelection(
    s"akka.tcp://memoryServer@$host/user/server")
  def set(key: String, value: Object) = {
    remoteDb ? SetRequest(key, value)
  }
  def get(key: String) = {
    remoteDb ? GetRequest(key)
  }
}
