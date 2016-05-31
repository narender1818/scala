name := "scala"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(

  "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.1.0",
    "org.slf4j" % "slf4j-simple" % "1.7.5",
 // "com.typesafe.akka" % "akka-actor_2.11" % "2.4.2",
  "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
  //"org.apache.spark" % "spark-core_2.10" % "1.6.1"
  "org.apache.spark" % "spark-core_2.11" % "1.6.1",
  "org.apache.spark" % "spark-sql_2.11" % "1.6.1",
  "org.apache.spark" % "spark-streaming_2.11" % "1.6.1",
  "org.apache.spark" % "spark-mllib_2.11" % "1.6.1"

)

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

)


scalacOptions += "-deprecation"
//addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")
resolvers += Resolver.sonatypeRepo("releases")