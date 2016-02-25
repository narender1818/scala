name := "scalat"

version := "1.0"

scalaVersion := "2.11.7"

autoScalaLibrary := false

mainClass := Some("Hi")

libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test"
)

EclipseKeys.withSource := true
