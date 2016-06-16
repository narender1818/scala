package com.bt.mongodb

import com.mongodb.Mongo

/**
  * Created by Narender Paul on 04-06-2016.
  */
class MongoClient(val host:String, val port:Int) {
  require(host != null, "You have to provide a host name")
  private val underlying = new Mongo(host, port)
  def this() = this("127.0.0.1", 27017)
  def version = underlying.getAddress
  def dropDB(name:String) = underlying.dropDatabase(name)
  def createDB(name:String) = DB(underlying.getDB(name))
  def db(name:String) = DB(underlying.getDB(name))
}
import com.mongodb.{DB => MongoDB}
class DB private(val underlying: MongoDB) {
}
object DB {
  def apply(underlying: MongoDB) = new DB(underlying)
}
class AddressBean(
                   var address1:String,
                   var address2:String,
                   var city:String,
                   var zipCode:Int)

object RichConsole {
  def p(x: Any) = println(x)
}