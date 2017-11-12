package com.bt.util

/**
  * Created by Narender Paul on 29-05-2016.
  */
trait Util {

  def getTimeStamp():Long={
    4.7L
  }

}

/*class Util {
  def readFile(file: String): String = {
    scala.io.Source.fromFile(file).mkString
  }
  def readProperty(path: String): Properties = {
    val prop = new Properties()
    val conf: Configuration = new Configuration()
    val fileSystem: FileSystem = FileSystem.get(URI.create(path), conf)
    val inputStream: InputStream = fileSystem.open(new Path(path))
    prop.load(inputStream)
    return prop
  }
}*/
