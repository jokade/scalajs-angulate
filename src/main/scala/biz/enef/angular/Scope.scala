// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
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
  val $id : Int = js.native

  /**
   * Reference to the parent scope.
   */
  val $parent : Scope = js.native

  /**
   * Reference to the root scope.
   */
  val $root : Scope = js.native

  def $watch(watchExpression: String, listener: js.Function) = js.native

  def $watch(watchExpression: js.Function, listener: js.Function) = js.native
}
