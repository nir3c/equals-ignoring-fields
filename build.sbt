ThisBuild / name := "equals-ignoring-fields"
ThisBuild / organization := "com.niro"
ThisBuild / version := "0.1"
ThisBuild / crossPaths := false

val scalaVersionNumber = "2.13.8"
val scalatestVersion = "3.2.10"

ThisBuild / scalaVersion := scalaVersionNumber

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersionNumber,
  "org.scalatest" %% "scalatest" % scalatestVersion % Test
)
