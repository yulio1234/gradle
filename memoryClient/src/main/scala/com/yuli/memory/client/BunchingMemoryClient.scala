package com.yuli.memory.client

import akka.actor.{ActorLogging, FSM}
import com.yuli.memory.client.StateContainerTypes.RequestQueue
import com.yuli.memory.domain.GetRequest


sealed trait State
case object Disconnected extends State
case object ConnectedAndPending extends State
case object Connected extends State
case object Flush
class BunchingMemoryClient extends FSM[State,RequestQueue] with ActorLogging{
  startWith(Disconnected, null)
  when(Disconnected){
    case Event(Connected, container:RequestQueue)=>{
      if (container.headOption.isEmpty){
        goto(Connected)
      }else{
        goto(ConnectedAndPending)
      }
    }
    case Event(x:GetRequest,container:RequestQueue)=> stay using(container :+ x)
  }
}
