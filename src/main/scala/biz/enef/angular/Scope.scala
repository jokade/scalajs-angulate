// -   Project: salajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.scalajs.js

/**
 * Defines the bindings to the global angular object.
 *
 * @see [[https://docs.angularjs.org/api/ng/type/$rootScope.Scope]]
 */
trait Scope extends js.Object {

  /**
   * Unique scope ID
   */
  val $id : Int = ???

  /**
   * Reference to the parent scope.
   */
  val $parent : Scope = ???

  /**
   * Reference to the root scope.
   */
  val $root : Scope = ???

  def $watch(watchExpression: String, listener: js.Function)

  def $watch(watchExpression: js.Function, listener: js.Function)
}
