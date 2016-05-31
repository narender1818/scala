package com.bt.stream

import java.util.Properties
import org.apache.spark._
import org.apache.spark.streaming._
/**
  * Created by Narender Paul on 28-05-2016.
  */
class CommonOpt(master:String) {

  def processStreaming(prop:Properties): Unit ={

    val appname=prop.getProperty("appname")
    val conf = new SparkConf().setMaster(master).setAppName(appname)
    val ssc = new StreamingContext(conf, Seconds(1))
    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    println("wordCounts :"+wordCounts.count())
  }

}
