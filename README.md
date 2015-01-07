scalajs-nglite
==============

A lighweight macro-based ScalaJS binding for AngularJS.

Introduction
------------
**scalajs-nglite** is a small library to simplify the development of [AngularJS](http://angularjs.org/) applications in pure [Scala](http://www.scala-lang.org) (via [Scala.js](http://www.scala-js.org)). To this end it provides:

*  [façade traits](http://www.scala-js.org/doc/calling-javascript.html) for the Angular core API
*  macros to allow defining controllers (and directives) in a more natural style compared to direct use of the API

There is currently no enhanced support for defining Angular services, since Scala singleton objects are a straightforward replacement for these in most instances. 

This project is at the very early stage of development (no release yet), and especially the semantics of the macro-based enhancements are subject to frequent changes.

scalajs-nglite was inspired by [scalajs-angular](https://github.com/greencatsoft/scalajs-angular), which currently provides a more complete Scala.js binding for Angular.

How to Use
----------

### SBT Settings
Add the following lines to your ```sbt``` build definition:

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

#### Controllers with explicit Scope ("old-style" AngularJS controllers)
```scala
object App {
  
  val module Angular.module("counter", Nil)
  module.controllerOf[CounterCtrl]
  
  /* Option A: using an explicitly defined Scope type */
  trait CounterScope extends Scope {
    var count : Int = ???
    var inc : js.Function = ???
    var dec : js.Function = ???
  }

  class CounterCtrl extends ScopeController[CounterScope] {
    scope.count = 0
  
    scope.inc = () => scope.count += 1
  
    scope.dec = () => scope.count -= 1
  }
  
  /* Option B: without explicit Scope type (using dynamicScope instead) */
  class DynamicCounterCtrl extends ScopeController[Scope] {
    dynamicScope.count = 0
    
    dynamicScope.inc = () => scope.count += 1
    
    dynamicScope.dec = () => scope.count -= 1
  }
}
```
```html
<html ng-app="counter">
  <body>
  <div ng-controller="App.CounterCtrl">
  Count: {{count}} <button ng-click="inc()">+</button> <button ng-click="dec()">&ndash;</button>
  </div>
  
  <!-- ... -->
  
  </body>
</html>
```

#### "Plain" Scala Controllers (with `as` syntax)
Plain Scala `Controller`s export all public `var`s, `val`s and `def`s into the controller scope.
Definition of custom `Scope` types or dynamic access via `dynamicScope` are not required.
Instantiation of the controller in the template requires the new AngularJS `as` syntax.

```scala
object App {
  
  val module Angular.module("counter", Nil)
  module.controllerOf[CounterCtrl]
  
  class CounterCtrl extends Controller {
    var count = 0
    
    def inc() = count += 1
    
    def dec() = count -= 1
  }
}
```
```html
<html ng-app="counter">
  <body>
  <div ng-controller="App.CounterCtrl as ctrl">
  Count: {{ctrl.count}} <button ng-click="ctrl.inc()">+</button> <button ng-click="ctrl.dec()">&ndash;</button>
  </div>
  
  <!-- ... -->
  
  </body>
</html>
```

License
-------
This code is open source software licensed under the [MIT License](http://opensource.org/licenses/MIT)

