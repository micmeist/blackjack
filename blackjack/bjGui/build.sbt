import sbt._

name := "BJGui"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11+"

lazy val core = RootProject(file("../core"))

val main = Project(id = "BJGui", base = file(".")).dependsOn(core)
