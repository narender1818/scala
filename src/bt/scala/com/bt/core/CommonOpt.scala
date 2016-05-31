package com.bt.core

import java.util.Properties
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
/**
  * Created by Narender Paul on 28-05-2016.
  */
class CommonOpt(master:String) {

 def wordCount(prop:Properties): Unit ={
   val appname=prop.getProperty("appname")
   val sc = new SparkContext(new SparkConf().setMaster(master).setAppName(appname))
   val threshold = 1
   // split each document into words
   val filellocation=prop.getProperty("filellocation")
   val tokenized = sc.textFile(filellocation).flatMap(_.split(" "))
   // count the occurrence of each word
   val wordCounts = tokenized.map((_, 1)).reduceByKey(_ + _)
   // filter out words with less than threshold occurrences
   val filtered = wordCounts.filter(_._2 >= threshold)
   // count characters
   val charCounts = filtered.flatMap(_._1.toCharArray).map((_, 1)).reduceByKey(_ + _)
   System.out.println("Word count  : "+charCounts.collect().mkString(", "))
 }
}
