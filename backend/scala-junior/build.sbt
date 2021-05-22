name := "tabmo-homework-scala"
organization := "tabmo.io"
version := "1.1.0"

val commonSettings = Seq(
  scalaVersion := "2.12.6",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.slf4j" % "slf4j-api" % "1.7.25",
    "org.slf4j" % "slf4j-simple" % "1.7.25"
  )
)

lazy val dna = (project in file("exercices/1-dna"))
  .settings(commonSettings)

lazy val radar = (project in file("exercices/2-radar"))
  .settings(commonSettings)

lazy val currencies = (project in file("exercices/3-currencies"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.9",
    "org.scala-lang.modules" %% "scala-xml" % "1.1.0"
  ))
