import SonatypeKeys._
import ScalaJSKeys._


lazy val commonSettings = Seq(
  organization := "biz.enef",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.2",
  scalacOptions ++= Seq("-deprecation","-feature","-Xlint"),
  // work around for a bug during publishing
  scalacOptions in (Compile,doc) ~= { _.filterNot(_.contains("scalajs-compiler_")) }
) ++ scalaJSSettings


lazy val root = project.in(file(".")).
  settings(commonSettings: _*).
  settings(publishingSettings: _*).
  settings(sonatypeSettings: _*).
  settings( 
    name := "scalajs-angulate",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scala-lang.modules.scalajs"   %%% "scalajs-dom" % "0.6" 
    )
  )


lazy val tests = project.
  dependsOn(root).
  settings(commonSettings: _*).
  settings(utest.jsrunner.Plugin.utestJsSettings: _*).
  settings(
    publish := {},
    scalacOptions ++= angulateDebugFlags,
    requiresDOM := true,
    //jsDependencies += scala.scalajs.sbtplugin.RuntimeDOM,
    jsDependencies += "org.webjars" % "angularjs" % "1.3.8" / "angular.min.js" % "test"
  )


lazy val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := (
    <url>https://github.com/jokade/scalajs-angulate</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jokade/scalajs-angulate</url>
      <connection>scm:git:git@github.com:jokade/scalajs-angulate.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jokade</id>
        <name>Johannes Kastner</name>
        <email>jokade@karchedon.de</email>
      </developer>
    </developers>
  )
)
 
lazy val angulateDebugFlags = Seq(
  // include some code for runtime debugging
  "runtimeLogging",
  "ModuleMacros.debug",
  //"ControllerMacros.debug"
  //"DirectiveMacros.debug"
  //"ServiceMacros.debug"
  "HttpPromiseMacros.debug"
).map( f => s"-Xmacro-settings:biz.enef.angular.$f" )

