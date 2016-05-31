package com.bt.dataframe

import java.util.Properties

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
/**
  * Created by Narender Paul on 28-05-2016.
  */
class CommonOpt(master:String)  {

  def useDataFrame(prop:Properties): Unit={
    val appname=prop.getProperty("appname")
    val sc=new SparkContext(new SparkConf().setMaster(master).setAppName(appname)) // An existing SparkContext.
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val df = sqlContext.read.json(prop.getProperty("json_file"))
    // Displays the content of the DataFrame to stdout
    df.show()

  }
}
