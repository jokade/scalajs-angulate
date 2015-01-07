scalajs-nglite
==============

A lighweight macro-based ScalaJS binding for AngularJS


## How to Use

### SBT Settings
Add the following line to your ```sbt``` build definition:

```scala
resolvers += "karchedon" at "http://maven.karchedon.de"

libraryDependencies += "biz.enef" %%% "scalajs-nglite" % "0.1-SNAPSHOT"
```

### Defining a Module

```scala
import biz.enef.angular._

val module = Angular.module("app", Seq("ui.bootstrap"))

module.controller(CounterCtrl)
```

### Defining Controllers

License
-------
This code is open source software licensed under the [MIT License](http://opensource.org/licenses/MIT)

