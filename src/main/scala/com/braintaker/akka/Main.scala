package com.braintaker.akka

import com.braintaker.preprocess.Test

object Main extends App {

  def matchTest(x: Any): Any = x match {
    case "1" =>
      val ss = new Test()
      ss.callActor
    case "2" =>
      println("args 2 : ")

  }
  matchTest(args(0))
}