package com.bt.dataframe

import java.util.Properties

import org.apache.spark.SparkContext

/**
  * Created by Narender Paul on 28-05-2016.
  */
case class Data1(c1: String, c2: String, c3: String, c4: String, c5: String, c6: String, c7: String, c8: String)
case class Data2(d1: String, d2: String, d3: String, d4: String, d5: String, d6: String, d7: String)
class CommonOpt(sc:SparkContext) extends java.io.Serializable {
 private val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._

  def useDataFrame(sc:SparkContext,prop: Properties): Unit = {
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val df = sqlContext.read.json(prop.getProperty("json_file"))
    // Displays the content of the DataFrame to stdout
    df.show()
  }

  def forOperation(prop: Properties): Unit ={
    forDataSet1(prop)
    forDataSet2(prop)
    val teenagers = sqlContext.sql("SELECT c1, c2,d1, d2 FROM dataset1 a inner join dataset2 d on a.c2 = d.d1  ")
    teenagers.collect().foreach(println)
  }

  def forDataSet1(prop: Properties): Unit = {
    val dataset1 = sc.textFile(prop.getProperty("dataset1"))
      .map(_.split("\t"))
      .map(p => Data1(p(0), p(1), p(2), p(3), p(4), p(5), p(6), p(7))).toDF()
    dataset1.registerTempTable("dataset1")

  }
  def forDataSet2(prop: Properties): Unit = {

    val dataset2 = sc.textFile(prop.getProperty("dataset2"))
      .map(_.split(","))
      .map(p => Data2(p(0), p(1), p(2), p(3), p(4), p(5), p(6))).toDF()
                dataset2.registerTempTable("dataset2")
  }
}
