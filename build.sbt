name := "scalajs-nglite"

organization in ThisBuild := "biz.enef"

version in ThisBuild := "0.1-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.4"

scalacOptions ++= Seq("-deprecation","-feature","-Xlint")

scalaJSSettings

libraryDependencies in ThisBuild ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.4"
)

publishTo in ThisBuild := Some( Resolver.sftp("repo", "karchedon.de", "maven.karchedon.de/") )
