package com.bt.ml

import java.util.Properties
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.sql.Row
import org.apache.spark.{SparkConf, SparkContext}
import java.io.File

/**
  * Created by Narender Paul on 28-05-2016.
  */
class CommonOpt(master: String) {

  def prossessEstimator(prop: Properties): Unit = {

    val appname = prop.getProperty("appname")
    val sc = new SparkContext(new SparkConf().setMaster(master).setAppName(appname)) // An existing SparkContext.
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    // Prepare training data from a list of (label, features) tuples.
    val training = sqlContext.createDataFrame(Seq(
      (1.0, Vectors.dense(0.0, 1.1, 0.1)),
      (0.0, Vectors.dense(2.0, 1.0, -1.0)),
      (0.0, Vectors.dense(2.0, 1.3, 1.0)),
      (1.0, Vectors.dense(0.0, 1.2, -0.5))
    )).toDF("label", "features")

    // Create a LogisticRegression instance.  This instance is an Estimator.
    val lr = new LogisticRegression()
    // Print out the parameters, documentation, and any default values.
    println("LogisticRegression parameters:\n" + lr.explainParams() + "\n")

    // We may set parameters using setter methods.
    lr.setMaxIter(10)
      .setRegParam(0.01)

    // Learn a LogisticRegression model.  This uses the parameters stored in lr.
    val model1 = lr.fit(training)
    // Since model1 is a Model (i.e., a Transformer produced by an Estimator),
    // we can view the parameters it used during fit().
    // This prints the parameter (name: value) pairs, where names are unique IDs for this
    // LogisticRegression instance.
    println("Model 1 was fit using parameters: " + model1.parent.extractParamMap)

    // We may alternatively specify parameters using a ParamMap,
    // which supports several methods for specifying parameters.
    val paramMap = ParamMap(lr.maxIter -> 20)
      .put(lr.maxIter, 30) // Specify 1 Param.  This overwrites the original maxIter.
      .put(lr.regParam -> 0.1, lr.threshold -> 0.55) // Specify multiple Params.

    // One can also combine ParamMaps.
    val paramMap2 = ParamMap(lr.probabilityCol -> "myProbability") // Change output column name
    val paramMapCombined = paramMap ++ paramMap2

    // Now learn a new model using the paramMapCombined parameters.
    // paramMapCombined overrides all parameters set earlier via lr.set* methods.
    val model2 = lr.fit(training, paramMapCombined)
    println("Model 2 was fit using parameters: " + model2.parent.extractParamMap)

    // Prepare test data.
    val test = sqlContext.createDataFrame(Seq(
      (1.0, Vectors.dense(-1.0, 1.5, 1.3)),
      (0.0, Vectors.dense(3.0, 2.0, -0.1)),
      (1.0, Vectors.dense(0.0, 2.2, -1.5))
    )).toDF("label", "features")

    // Make predictions on test data using the Transformer.transform() method.
    // LogisticRegression.transform will only use the 'features' column.
    // Note that model2.transform() outputs a 'myProbability' column instead of the usual
    // 'probability' column since we renamed the lr.probabilityCol parameter previously.
    model2.transform(test)
      .select("features", "label", "myProbability", "prediction")
      .collect()
      .foreach { case Row(features: Vector, label: Double, prob: Vector, prediction: Double) =>
        println(s"($features, $label) -> prob=$prob, prediction=$prediction")
      }
  }

  import org.apache.spark.ml.classification.LogisticRegression
  import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
  import org.apache.spark.ml.{Pipeline, PipelineModel}
  import org.apache.spark.mllib.linalg.Vector
  import org.apache.spark.sql.Row

  def proceePipeline(prop: Properties): Unit = {
    val appname = prop.getProperty("appname")
    val sc = new SparkContext(new SparkConf().setMaster(master).setAppName(appname)) // An existing SparkContext.
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    getQualifiedBinPath("C:\\Users\\Narender Paul\\Desktop\\buffer\\spark\\bin\\winutils.exe")
    //System.setProperty("hadoop.home.dir", "C:\\Users\\Narender Paul\\Desktop\\buffer\\spark\\bin\\winutils.exe")
    val training = sqlContext.createDataFrame(Seq(
      (0L, "a b c d e spark", 1.0),
      (1L, "b d", 0.0),
      (2L, "spark f g h", 1.0)
      //(3L, "hadoop mapreduce", 0.0)
    )).toDF("id", "text", "label")

    // Configure an ML pipeline, which consists of three stages: tokenizer, hashingTF, and lr.
    val tokenizer = new Tokenizer()
      .setInputCol("text")
      .setOutputCol("words")
    val hashingTF = new HashingTF()
      .setNumFeatures(1000)
      .setInputCol(tokenizer.getOutputCol)
      .setOutputCol("features")
    val lr = new LogisticRegression()
      .setMaxIter(10)
      .setRegParam(0.01)
    val pipeline = new Pipeline()
      .setStages(Array(tokenizer, hashingTF, lr))

    // Fit the pipeline to training documents.
    val model = pipeline.fit(training)

    // now we can optionally save the fitted pipeline to disk
    model.save(prop.getProperty("tmp_save") + "spark-logistic-regression-model")

    // we can also save this unfit pipeline to disk
    pipeline.save(prop.getProperty("tmp_save") + "unfit-lr-model")

    // and load it back in during production
    val sameModel = PipelineModel.load(prop.getProperty("tmp_save") ++ "spark-logistic-regression-model")

    // Prepare test documents, which are unlabeled (id, text) tuples.
    val test = sqlContext.createDataFrame(Seq(
      (4L, "spark i j k"),
      (5L, "l m n"),
      (6L, "mapreduce spark"),
      (7L, "apache hadoop")
    )).toDF("id", "text")

    // Make predictions on test documents.
    model.transform(test)
      .select("id", "text", "probability", "prediction")
      .collect()
      .foreach { case Row(id: Long, text: String, prob: Vector, prediction: Double) =>
        println(s"($id, $text) --> prob=$prob, prediction=$prediction")
      }
  }

  def getQualifiedBinPath(executable: String): String = {
    val exeFile = new File(executable);
    return exeFile.getCanonicalPath();
  }

}
