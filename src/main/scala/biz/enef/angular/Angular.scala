// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.language.experimental.macros

import scala.scalajs.js

/**
 * Defines the bindings to the global angular object.
 *
 * @see [[https://docs.angularjs.org/api/ng]]
 */
trait Angular extends js.Object {

  /**
   * Use this function to manually start up an angular application.
   *
   * @param element
   * @param modules
   * @param config
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.bootstrap]]
   */
  def bootstrap(element: js.Object, modules: js.Object = ???, config: js.Object = ???) : js.Object

  /**
   * Creates a deep copy of `source`.
   *
   * @param source
   * @param destination
   */
  def copy(source: js.Any, destination: js.Object = ???) : js.Any = ???

  /**
   * Extends the destination object `dst` by copying own enumerable properties from the `src` object(s) to `dst`
   *
   * @param dst Destination object
   * @param src Source object(s)
   * @return reference to `dst`
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.extend]]
   */
  def extend(dst: js.Object, src: js.Object*) : js.Object = ???

  /**
   * Deserializes a JSON string.
   *
   * @param json
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.fromJson]]
   */
  def fromJson(json: String) : js.Any = ???

  /**
   * Determines if a reference is defined.
   *
   * @param value
   */
  def isDefined(value: js.Any) : Boolean = ???

  /**
   * Determines if a reference is undefined.
   *
   * @param value
   */
  def isUndefined(value: js.Any) : Boolean = ???

  /**
   * Invokes the `iterator` function once for each item in `obj`.
   *
   * @param obj Object to iterate over
   * @param iterator `function(value, key, obj)`
   * @param context Object to become context (`this`) for the iterator function
   * @return reference to `obj`
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.forEach]]
   */
  def forEach(obj: js.Object, iterator: js.Function3[js.Any,js.Any,js.Object,Unit], context: js.Object = ???) : js.Object = ???

  /**
   * Converts the specified string to lowercase.
   *
   * @param string to be converted
   */
  def lowercase(string: String) : String = ???

  /**
   * Creates or retrieves an Angular module.
   *
   * @param name The name of the module to create or retrieve.
   * @param requires Array with the names of other modules required by this module.
   *                 If specified then a new module is being created. If unspecified then the
   *                 module is being retrieved for further configuration.
   * @param configFn Optional configuration function for the module.
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.module]]
   */
  def module(name: String, requires: js.Array[String] = null, configFn: js.Function = null) : Module

  /**
   * Serializes input into a JSON-formatted string.
   *
   * @param obj
   * @param pretty
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.toJson]]
   */
  def toJson(obj: js.Any, pretty: js.Any) : String = ???

  /**
   * Converts the specified string to uppercase.
   *
   * @param string to be converted
   */
  def uppercase(string: String) : String = ???

}

object Angular {
  import scalajs.js.JSConverters._

  /**
   * Returns the global Angular object
   */
  def apply() : Angular = macro impl.AngularImpl.apply

  /**
   * Creates a new Angular
   * @param name
   * @param requires
   * @return
   */
  def module(name: String, requires: Iterable[String]) : Module = macro impl.AngularImpl.module
}
