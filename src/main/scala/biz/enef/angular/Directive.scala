// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import acyclic.file
import biz.enef.angular.core.{Attributes, JQLite}

import scala.scalajs.js

/**
 * Interface to be implemented by classes that represent an AngularJS directive.
 */
trait Directive {
  def restrict: String = ???

  def transclude: Boolean = ???

  def priority: Int = ???

  //def controller[T<:Scope](scope: T, elem: JQLite, attrs: Attributes) : Unit = ???

  //def controller(scope: js.Dynamic, elem: JQLite, attrs: Attributes) : Unit = ???
  type withController <: NGController

  def controllerAs: String = ???

  def template: String = ???
  def template(element: JQLite, attrs: Attributes) : String = ???

  def templateUrl: String = ???
  def templateUrl(element: JQLite, attrs: Attributes) : String = ???

  def scope: Boolean = ???
  def isolateScope: js.Dictionary[String] = ???

  def postLink(scope: Scope, element: JQLite, attrs: Attributes, controller: js.Dynamic) : Unit = ???
  //def preLink(scope: Scope, element: JQLite, attrs: Attributes, controller: js.Dynamic) : Unit = ???

  def compile(tElement: js.Dynamic, tAttrs: Attributes) : js.Any = ???
}
