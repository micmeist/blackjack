import sbt._

name := "webui"

version := "1.0"

scalaVersion := "2.11.7"

lazy val core = RootProject(file("../core"))

lazy val `webui` = (project in file(".")).enablePlugins(PlayScala).dependsOn(core)

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  