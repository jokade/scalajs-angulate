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

  /**
   * Used to execute an expression in angular from outside of the angular framework and returns the result.
   *
   * @param exp An angular expression to be executed (string or a `function(scope)`)
   */
  def $apply(exp: js.Any = js.native) : js.Any = js.native

  /**
   * Schedule the invocation of `$$apply` to occur at a later time.
   *
   * @param exp An angular expression to be executed (string or a `function(scope)`)
   */
  def $applyAsync(exp: js.Object = js.native) : js.Any = js.native

  /**
   * Dispatches an event `name` downwards to all child scopes.
   *
   * @param name Event name to broadcast
   * @param args Arguments passed to event listeners
   * @return event object
   */
  def $broadcast(name: String, args: js.Any*) : js.Object = js.native

  /**
   * Removes the current scope (and all of its children) from the parent scope.
   */
  def $destroy() : Unit = js.native

  /**
   * Processes all of the watchers of the current scope and its children.
   */
  def $digest() : Unit = js.native

  /**
   * Dispatches an event `name` upwards through the scope hierarchy.
   *
   * @param name Event name to emit
   * @param args Arguments that will be passed to event listeners
   * @return event object
   */
  def $emit(name: String, args: js.Any*) : js.Object = js.native

  /**
   * Executes the expression on the current scope and returns the result.
   *
   * @param expression An angular expression to be executed (either a string or a `function(scope)`)
   * @param locals Local variables object, useful for overriding values in scope
   */
  def $eval(expression: js.Object = js.native, locals: js.Object = js.native) : js.Any = js.native

  /**
   * Evaluates the expression on the current scope at a later point in time.
   *
   * @param expression An angular expression to be executed (either a string or a `function(scope)`)
   * @param locals Local variables object, useful for overriding values in scope
   */
  def $evalAsync(expression: js.Object = js.native, locals: js.Object = js.native) : js.Any = js.native

  /**
   * Creates a new child scope.
   *
   * @param isolate If true, then the scope does not prototypically inherit from the parent scope
   * @param parent The parent scope
   */
  def $new(isolate: Boolean, parent: Scope) : Scope = js.native

  /**
   * Listens on events of a given type.
   *
   * @param name Event name to listen on
   * @param listener Callback function (`function(event, ...args)`
   */
  def $on(name: String, listener: js.Function) : js.Function = js.native

  /**
   * Registers a listener callback to be executed whenever the `watchExpression` changes.
   *
   * @param watchExpression Called on every `$$digest()` and should return the value that should be watched
   * @param listener Callback function which is only executed when the `watchExpression` has changed.
   *                 Called with three arguments: new value, old value, current scope
   * @return de-registration function
   */
  def $watch(watchExpression: js.Any, listener: js.Function = null) : js.Function = js.native

  /**
   * Registers a listener callback to be executed whenever the `watchExpression` changes.
   *
   * @param watchExpression Called on every `$$digest()` and should return the value that should be watched
   * @param listener Callback function which is only executed when the `watchExpression` has changed
   *                 Called with three arguments: new value, old value, current scope
   * @return de-registration function
   */
  //def $watch(watchExpression: js.Function, listener: js.Function3[js.Any,js.Any,Scope,Unit]) : js.Function = js.native

  /**
   * A variant of `$$watch` where it watches an array of `watchExpressions`
   *
   * @param watchExpressions Array of expressions that will be individually watched using `$$watch`
   * @param listener Callback function which is executed whenever any of the expressions in `watchExpression` has changed
   *                 Called with three arguments: new values, old values, current scope
   * @return de-registration function
   */
  def $watchGroup(watchExpressions: js.Array[js.Object], listener: js.Function) : js.Function = js.native

  /**
   * Shallow watches the properties of an object and fires whenever any of the properties change.
   *
   * @param obj Collection watched via `$watch`
   * @param listener Callback function with three arguments: new collection, old collection, current scope
   * @return De-registration function
   */
  def $watchCollection(obj: js.Object, listener: js.Function3[js.Object,js.Object,Scope,Unit]) : js.Function = js.native

}
