// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Binding and enhancements for Angular $routeProvider API
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.ext

import scala.scalajs.js

/**
 * Defines the bindings to the ngRoute.$rootProvider API and enhancements provided by scalajs-angulate.
 *
 * @see [[https://docs.angularjs.org/api/ngRoute/provider/$routeProvider]]
 */
trait RouteProvider extends js.Object {

  //------------------------------ ANGULAR.JS --------------------------------

  def when(path: String, route: js.Object) : RouteProvider

  def otherwise(params: String) : RouteProvider
}

/*trait Route extends js.Object {


  var template: js.Object = ???
}*/

