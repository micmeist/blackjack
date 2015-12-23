import sbt._

name := "textui"

version := "1.0"

scalaVersion := "2.11.7"


lazy val core = RootProject(file("../core"))

val main = Project(id = "textui", base = file(".")).dependsOn(core)
    