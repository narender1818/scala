package com.braintaker.preprocess

object YahooFinancials extends Enumeration {
  type YahooFinancials = Value
  val DATE, OPEN, HIGH, LOW, CLOSE, VOLUME, ADJ_CLOSE = Value
  val volatility = (fs: Array[String]) => fs(HIGH.id).toDouble - fs(LOW.
    id).toDouble

}