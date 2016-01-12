import sbt._

lazy val commonSettings = Seq(

  version := "1.0",

  scalaVersion := "2.11.7"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "blackjack"
  ).
  aggregate(textui, webui, webuiangular, core)


lazy val core = project.settings(commonSettings: _*)

lazy val webuiangular = project.settings(commonSettings: _*).dependsOn(core)

lazy val webui = project.settings(commonSettings: _*).dependsOn(core)

lazy val textui = project.settings(commonSettings: _*).dependsOn(core)