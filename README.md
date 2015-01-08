scalajs-angulate
================

A lighweight macro-based ScalaJS binding for AngularJS.

Introduction
------------
**scalajs-angulate** is a small library to simplify the development of [AngularJS](http://angularjs.org/) applications in [Scala](http://www.scala-lang.org) (via [Scala.js](http://www.scala-js.org)). To this end it provides:

*  [façade traits](http://www.scala-js.org/doc/calling-javascript.html) for the Angular core API
*  macros to allow defining controllers, service and directives) in a more natural style compared to direct use of the API

This project is at the very early stage of development (no release yet), and especially the semantics of the macro-based enhancements are subject to frequent changes.

scalajs-angulate was inspired by [scalajs-angular](https://github.com/greencatsoft/scalajs-angular), which currently provides a more complete Scala.js binding for Angular.

How to Use
----------

### SBT Settings
Add the following lines to your ```sbt``` build definition:

```scala
resolvers += "karchedon" at "http://maven.karchedon.de"

libraryDependencies += "biz.enef" %%% "scalajs-angulate" % "0.1-SNAPSHOT"
```

### Defining a Module

```scala
import biz.enef.angular._

val module = Angular.module("app", Seq("ui.bootstrap"))

module.serviceOf[UserService]
module.controllerOf[UserCtrl]
```

### Defining Controllers

#### Plain Class Controllers (used with controller `as` syntax)
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
    
    // private properties and functions are not exported to the controller scope
    private def foo() : Unit = ...
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
The controller scope can be accessed via the `scope` or `dynamicScope`properties from within the controller class.


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


### Dependency Injection
scalajs-angulate supports constructor dependency injection of Angular services:

```scala
class UserCtrl($http: HttpService) extends Controller {
  /* ... */
  
  $http.get("/rest/users").onSuccess{ res => ... }
}
```
the Angular `$http` service will be injected during Controller instantion; no annotations or additional traits are required, as long as the parameter name in the constructor matches the name of the service to be injected (don't worry about JS minification, the macro translates the constructor into a String-based DI array).

If you cannot or don't want to use the service name as parameter name, you can define the service to be injected explicitly with the `@named` annotation:
```scala
class UserCtrl(@named("$http") httpService: HttpService) extends Controller {
  /* ... */
}
```

### Services
Services can be implemented as plain classes extending the `Service` trait. As with controllers,
constructor based dependency injection is supported:
```scala
class UserService($http: HttpService) extends Service {
  def getUsers() : js.Array[User] = $http.get("/rest/users/").onSuccess( ... )
}

// registers the service with the name 'userService'
module.serviceOf[UserService]

// -- or --

// registers the service with the name 'users'
module.serviceOf[UserService]("Users")


class UserCtrl(userService: UserService) extends Controller {
  /* ... */
}
```
If no explicit service name is provided to `serviceOf[Service]`, then the class name will be used as service name,
__with the first letter in lower case__ (to support derivation of dependencies from argument names, which begin with a lower case letter by convention).

License
-------
This code is open source software licensed under the [MIT License](http://opensource.org/licenses/MIT)

