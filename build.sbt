ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "EightQueens"
  )
libraryDependencies ++= Seq(
  "org.processing" % "core" % "3.0b5",
//  "org.processing" % "net" % "3.0b5",
//  "org.processing" % "video" % "3.0b5",
//  "org.processing" % "serial" % "3.0b5",
//  "org.processing" % "pde" % "3.0b5",
 // "org.processing" % "pdf" % "3.0b5"
)