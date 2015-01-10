lazy val commonSettings = Seq(
  organization := "biz.enef",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.2",
  scalacOptions ++= Seq("-deprecation","-feature","-Xlint")
)

  
lazy val root = project.in(file(".")).
  settings(commonSettings: _*).
  settings( 
    name := "scalajs-angulate",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    ),
    publishTo := { Some( Resolver.sftp("repo", "karchedon.de", "/www/htdocs/w00be83c/maven.karchedon.de") ) }
  ).
  settings(scalaJSSettings:_*)

val angulateDebugFlags = Seq(
  "runtimeLogging",
  "ModuleMacros.debug"
  //"ControllerMacros.debug"
  //"DirectiveMacros.debug"
  //"ServiceMacros.debug"
//  "HttpPromiseMacros.debug"
).map( f => s"-Xmacro-settings:biz.enef.angular.$f" )

lazy val tests = project.
  dependsOn(root).
  settings(commonSettings: _*).
  settings(
    publish := {},
    scalacOptions ++= angulateDebugFlags
  )
