package com.yuli.demo.actors

import akka.actor.{Actor, ActorLogging, Props}
import com.yuli.demo.command.Connect
object DatabaseActor{
  def props(host:String,port:Int):Props=Props(new DatabaseActor(host,port))
}
/**
 * 数据库actor,保证重试不会丢失链接
 * @param host
 * @param port
 */
class DatabaseActor(host:String,port:Int) extends Actor with ActorLogging{
  //启动之后发送链接数据库命令，更优雅，能处理异常情况
  override def preStart(): Unit = self ! Connect

  override def receive: Receive = {
    case Connect =>{
      log.info("连接数据库,host:{},port:{}",host,port)
    }
  }
}
