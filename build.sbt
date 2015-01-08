name := "scalajs-angulate"

organization in ThisBuild := "biz.enef"

version in ThisBuild := "0.1-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.2"

scalacOptions ++= Seq("-deprecation","-feature","-Xlint")

scalaJSSettings

libraryDependencies in ThisBuild ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.2"
)


publishTo in ThisBuild := {
  Some( Resolver.sftp("repo", "karchedon.de", "/www/htdocs/w00be83c/maven.karchedon.de") )
}
