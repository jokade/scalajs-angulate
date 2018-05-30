
lazy val commonSettings = Seq(
  organization := "biz.enef",
  version := "0.2.5-SNAPSHOT",
  scalaVersion := "2.12.5",
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-language:implicitConversions","-Xlint"),
  autoCompilerPlugins := true,
  addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.7"),
  libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.1.7" % "provided",
  scalacOptions ++= (if (isSnapshot.value) Seq.empty else Seq({
        val a = baseDirectory.value.toURI.toString.replaceFirst("[^/]+/?$", "")
        val g = "https://raw.githubusercontent.com/jokade/scalajs-angulate"
        s"-P:scalajs:mapSourceURI:$a->$g/v${version.value}/"
      })),
  resolvers += Resolver.sonatypeRepo("snapshots")
)


lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(publishingSettings: _*).
  settings( 
    name := "scalajs-angulate",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
      "org.scala-js"   %%% "scalajs-dom" % "0.9.5",
      "de.surfice"     %%% "smacrotools-sjs"  % "0.0.8",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.3" % "provided"
    ),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  )


lazy val tests = project.
  dependsOn(root).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    publish := {},
    scalacOptions ++= angulateDebugFlags,
    scalaJSStage in Test := FastOptStage,
    testFrameworks += new TestFramework("utest.runner.Framework"),
    requiresDOM := true,
    libraryDependencies ++= Seq("com.lihaoyi" %%% "utest" % "0.4.4" % "test",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.1"),
    jsDependencies += RuntimeDOM,
    jsDependencies += "org.webjars" % "angularjs" % "1.3.8" / "angular.min.js" % "test",
    jsDependencies += ("org.webjars" % "angularjs" % "1.3.8" / "angular-mocks.js" dependsOn "angular.min.js") % "test"
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
      <developer>
        <id>ludovicc</id>
        <name>Ludovic Claude</name>
        <email>ludovic.claude@laposte.net</email>
      </developer>
    </developers>
  )
)
 
lazy val angulateDebugFlags = Seq(
  // include some code for runtime debugging
  //"runtimeLogging",
  //"ModuleMacros.debug",
  //"ControllerMacros.debug"
  //"DirectiveMacros.debug"
  //"ServiceMacros.debug"
  //"ComponentMacros.debug"
  //"HttpPromiseMacros.debug"
).map( f => s"-Xmacro-settings:biz.enef.angulate.$f" )

