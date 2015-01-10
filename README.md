Enhanced Scala.js Bindings for AngularJS
========================================

Introduction
------------
**scalajs-angulate** is a small library to simplify the development of [AngularJS](http://angularjs.org/) applications in [Scala](http://www.scala-lang.org) (via [Scala.js](http://www.scala-js.org)). To this end it provides:

*  [façade traits](http://www.scala-js.org/doc/calling-javascript.html) for the Angular core API
*  macros to allow defining controllers, service and directives) in a more natural style compared to direct use of the API

This project is at the very early stage of development (no release yet), and especially the semantics of the macro-based enhancements are subject to frequent changes.

scalajs-angulate was inspired by [scalajs-angular](https://github.com/greencatsoft/scalajs-angular), which currently provides a more complete Scala.js binding for Angular.

_NOTE: the handling of controllers has recently changed_

How to Use
----------

### SBT Settings
Add the following lines to your ```sbt``` build definition:

```scala
resolvers += "karchedon" at "http://maven.karchedon.de"

libraryDependencies += "biz.enef" %%% "scalajs-angulate" % "0.1-SNAPSHOT"
```

scalajs-angulate supports Scala.js `0.5.x` and `0.6.0-M3`.

### Defining a Module

```scala
import biz.enef.angular._
import biz.enef.angular.ext.{Route, RouteProvider}

val module = Angular.module("app", Seq("ui.bootstrap"))

module.serviceOf[UserService]
module.controllerOf[UserCtrl]
module.directiveOf[UserDirective]
module.config( ($routeProvider:RouteProvider) => {
  $routeProvider.
    when("/user/:id", Route( templateUrl = "/tpl/userDetails.html" )).
    otherwise( Route( redirectTo = "/" ) )
})
```

### Controllers
scalajs-angulate provides macros to allow using plain scala classes as Angular controllers.
Two flavors are currently supported:
*  [Body Scope controllers](#body-scope-controllers), extending `Controller`
*  [Controllers with explicit scope](#controllers-with-explicit-scope), extending `ScopeController`

Both flavors support constructor [dependency injection](#dependency-injection).

#### Body Scope Controllers
Classes extending `Controller` export all their public `var`s, `val`s and `def`s into the controller scope.
Defining a custom `Scope` type is not required. However, instantiating the controller in the template requires the new AngularJS `as` syntax:

```scala
val module Angular.module("counter", Nil)
module.controllerOf[CounterCtrl]
  
class CounterCtrl extends Controller {
  var count = 0
    
  def inc() = count += 1
    
  def dec() = count -= 1
    
  // private properties and functions are not exported to the controller scope
  private def foo() : Unit = ...
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

If you need access to the AngularJS `$scope` object, just inject it into the constructor:
```scala
class Ctrl($scope: Scope) extends Controller {
  $scope.$watch( /* ... */ )
}
```

#### Controllers with explicit Scope
Class extending `ScopeController` are "old-style" AngularJS controllers, where the scope available in the template must be injected explicitly:

```scala
val module Angular.module("counter", Nil)
module.controllerOf[CounterCtrl]
  
/* Option A: using a custom defined Scope type */
trait CounterScope extends Scope {
  var count : Int = ???
  var inc : js.Function = ???
  var dec : js.Function = ???
}

class CounterCtrl($scope: CounterScope) extends ScopeController {
  $scope.count = 0
  
  $scope.inc = () => $scope.count += 1
  
  $scope.dec = () => $scope.count -= 1
}
  
/* Option B: without explicit Scope type (using js.Dynamic instead) */
class DynamicCounterCtrl($scope: js.Dynamic) extends ScopeController {
  $scope.count = 0
    
  $scope.inc = () => $scope.count += 1
    
  $scope.dec = () => $scope.count -= 1
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
scalajs-angulate supports constructor dependency injection of Angular services into controllers, services and directives:

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
  def getUsers() : HttpPromise = $http.get("/rest/users/")
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


### Directives
There is some basic support for defining Directives:
```scala
class HelloUserDirective($animate: AnimationService) extends Directive {
  override val restrict = "E"
  
  override val template = """<div>Hello {{user}}><div>"""
  // -- or --
  // override def template(element,attrs) = ...
  // -- or --
  // override val templateUrl = "/url"
  // -- or --
  // override def templateUrl(element,attrs) = ...
  
  override val isolateScope = js.Dictionary( "user" -> "@" )
  // -- or --
  // override val scope = true
  
  override def postLink(scope: Scope,
                        element: js.Dynamic,
                        attrs: js.Dynamic,
                        controller: js.Dynamic) = ...
}

// defines the directive under the name "helloUser"
module.directiveOf[HelloUserDirective]
// -- or --
// module.directiveOf[HelloUserDirective]("sayHello")
```

License
-------
This code is open source software licensed under the [MIT License](http://opensource.org/licenses/MIT)

