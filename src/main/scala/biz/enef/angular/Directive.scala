// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExport}

/**
 * Interface to be implemented by classes that represent an AngularJS directive.
 */
trait Directive {
  def restrict: String = ???

  def template: String = ???
  def template(element: js.Dynamic, attrs: js.Dynamic) : String = ???

  def templateUrl: String = ???
  def templateUrl(element: js.Dynamic, attrs: js.Dynamic) : String = ???

  def scope: Boolean = ???
  def isolateScope: js.Dictionary[String] = ???

  def postLink(scope: Scope, element: js.Dynamic, attrs: js.Dynamic, controller: js.Dynamic) : Unit = ???
}
