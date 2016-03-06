package com.braintaker.preprocess

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef
import com.typesafe.scalalogging.LazyLogging

case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

class Message(pong: ActorRef) extends Actor {
  var count = 0
  def incrementAndPrint { count += 1; println("ping") }
  def receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
    case PongMessage =>
      incrementAndPrint
      if (count > 99) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      } else {
        sender ! PingMessage
      }
  }
}
class PongMessag extends Actor {
  def receive = {
    case PingMessage =>
      println("  pong")
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
      context.stop(self)
  }

    
}

class Test {
  def callActor(): Unit = {
     // logger.info("Here goes my debug message.")
      val system = ActorSystem("PingPongSystem")
      val pong = system.actorOf(Props[PongMessag], name = "pong")
      val ping = system.actorOf(Props(new Message(pong)), name = "ping")
      //  // start them going
      ping ! StartMessage
    }
}
