import sbt._

name := "webui-angular"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val core = RootProject(file("../core"))

lazy val `webuiangular` = (project in file(".")).enablePlugins(PlayScala).dependsOn(core)

libraryDependencies ++= Seq(
  "org.webjars" % "angularjs" % "1.3.0-beta.2",
  "org.webjars" % "requirejs" % "2.1.11-1"
)

pipelineStages := Seq(rjs, digest, gzip)
