package com.yuli.memory.client

import java.util.concurrent.TimeUnit

import org.scalatest.{FunSpecLike, Matchers}
import concurrent.duration._
import scala.concurrent.Await
class ClientTest extends FunSpecLike with Matchers{
  private val client = new MemoryClient("127.0.0.1:2552")
  describe("memoryClient"){
    it("should set a value"){
      client.set("123",new Integer(123))
      val future = client.get("123")
      val result = Await.result(future,Duration.create(10,TimeUnit.SECONDS))
      println(result)
      result should equal(Some(123))
    }
  }
}
