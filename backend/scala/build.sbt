name := "tabmo-homework-scala"
organization := "tabmo.io"
version := "1.0.0"

val commonSettings = Seq(
  scalaVersion := "2.12.1",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
)

lazy val dna = (project in file("exercices/1-dna"))
  .settings(commonSettings)

lazy val radar = (project in file("exercices/2-radar"))
  .settings(commonSettings)
