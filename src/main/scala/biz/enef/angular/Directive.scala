// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import biz.enef.angular.core.{Attributes, JQLite}

import scala.scalajs.js

/**
 * Interface to be implemented by classes that represent an AngularJS directive.
 */
trait Directive {
  def restrict: String = ???

  //def controller[T<:Scope](scope: T, elem: JQLite, attrs: Attributes) : Unit = ???

  //def controller(scope: js.Dynamic, elem: JQLite, attrs: Attributes) : Unit = ???
  type withController <: DirectiveController

  def controllerAs: String = ???

  def template: String = ???
  def template(element: JQLite, attrs: Attributes) : String = ???

  def templateUrl: String = ???
  def templateUrl(element: JQLite, attrs: Attributes) : String = ???

  def scope: Boolean = ???
  def isolateScope: js.Dictionary[String] = ???

  def postLink(scope: Scope, element: JQLite, attrs: Attributes, controller: js.Dynamic) : Unit = ???
}
