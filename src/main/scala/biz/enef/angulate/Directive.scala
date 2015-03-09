// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate

import acyclic.file
import biz.enef.angulate.core.{Attributes, JQLite}

import scala.scalajs.js

/**
 * Interface to be implemented by classes that represent an AngularJS directive.
 */
trait Directive {
  // def name: String = ???
  def restrict: String = ??? // subset of EACM
  // def multiElement: Boolean = ???
  // def replace: Boolean = ??? - deprecated
  // def terminal: Boolean = ???
  // def require: js.Any = ??? -string or array of strings

  def transclude: Boolean = ???

  def priority: Int = ???

  def controller(ctrl: ControllerType, scope: ScopeType, elem: JQLite, attrs: Attributes) : Unit = ???

  //def controller(scope: js.Dynamic, elem: JQLite, attrs: Attributes) : Unit = ???
  type ControllerType <: js.Any
  type ScopeType <: js.Any

  def controllerAs: String = ???
  //def bindToController: Boolean = ???

  def template: String = ???
  def template(element: JQLite, attrs: Attributes) : String = ???

  def templateUrl: String = ???
  def templateUrl(element: JQLite, attrs: Attributes) : String = ???
  // def templateNamespace: String = ???

  def scope: Boolean = ???
  def isolateScope: js.Dictionary[String] = ???
  // def bindToController: Boolean = ???

  def postLink(scope: ScopeType, element: JQLite, attrs: Attributes) : Unit = ???
  def postLink(scope: ScopeType, element: JQLite, attrs: Attributes, controller: ControllerType) : Unit = ???
  //def preLink(scope: Scope, element: JQLite, attrs: Attributes, controller: js.Dynamic) : Unit = ???

  def compile(tElement: js.Dynamic, tAttrs: Attributes) : js.Any = ???
}
