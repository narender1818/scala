package com.bt.main

import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

object Entry extends App {
  val reader = Source.fromURL(getClass.getResource("/prop.properties")).bufferedReader()
  val prop = new Properties()
  prop.load(reader)
  private val master = prop.getProperty("master")

  def entryPoint(x: Any): Unit = x match {
    case "core" => {
      val test = new com.bt.core.CommonOpt(master)
      test.wordCount(prop)
    }
    case "dataframe" => {
      val appname = prop.getProperty("appname")
      val sc = new SparkContext(new SparkConf().setMaster(master).setAppName(appname)) // An existing SparkContext.
      val test = new com.bt.dataframe.CommonOpt(sc)
      test.forOperation(prop)
    }
    case "stream" => {
      val test = new com.bt.stream.CommonOpt(master)
      test.processStreaming(prop)
    }
    case "ml" => {
      val test = new com.bt.ml.CommonOpt(master)
      //test.prossessEstimator(prop)
      test.proceePipeline(prop)
    }
  }

  entryPoint("dataframe")
}