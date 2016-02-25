package com.braintaker.preprocess

class PreProcessing {
  val XYTSerie :String=""
  def load(fileName: String): Option[XYTSerie] = {
    val src = Source.fromFile(fileName)
    val fields = src.getLines.map(_.split(CSV_DELIM)).toArray //1
    val cols = fields.drop(1) //2
   // val data = transform(cols)
    src.close //3
    Some(data)
  }
  
  val mean = price.reduceLeft( _ + _ )/price.size
val s2 = price.foldLeft(0.0)((s,x) =>s+(x-mean)*(x-mean))
val stdDev = Math.sqrt(s2/(price.size-1) )
}