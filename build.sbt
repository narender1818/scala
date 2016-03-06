name := "scala"
version := "1.0"
scalaVersion := "2.11.7"
autoScalaLibrary := true




libraryDependencies ++= Seq(

 	 "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.1.0",
	 "org.slf4j" % "slf4j-api" % "1.7.5",
	 "org.slf4j" % "slf4j-simple" % "1.7.5",			
  	 "com.typesafe.akka" % "akka-actor_2.11" % "2.4.2",
     "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test"
)

scalacOptions += "-deprecation"

