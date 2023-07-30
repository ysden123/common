import java.util.Calendar

import sbt.Keys.{javacOptions, scalacOptions}

ThisBuild / scalaVersion := "3.3.0"
ThisBuild / organization := "com.stulsoft"
ThisBuild / version := "1.0.5"

Compile / packageBin / packageOptions += Package.ManifestAttributes("Build-Date" -> Calendar.getInstance().getTime.toString)

lazy val loggingVersion = "2.20.0"
lazy val scalatestVersion = "3.2.15"

lazy val app = (project in file("."))
  .settings(
    name := "common",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    libraryDependencies += "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
    libraryDependencies += "org.scalactic" %% "scalactic" % scalatestVersion,
    libraryDependencies += "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    javacOptions ++= Seq("-source", "11"),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-language:implicitConversions",
      "-language:postfixOps")

  )