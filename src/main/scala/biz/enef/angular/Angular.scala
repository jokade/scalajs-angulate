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

  def injector(modules: js.Any, strictDi: Boolean = false) : Angular.Injector = js.native

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
  def toJson(obj: js.Any, pretty: js.Any) : String = js.native

  /**
   * Converts the specified string to uppercase.
   *
   * @param string to be converted
   */
  def uppercase(string: String) : String = js.native

}

object Angular {
  import scalajs.js.JSConverters._

  /**
   * Returns the global Angular object
   */
  def apply() : Angular = js.Dynamic.global.angular.asInstanceOf[Angular] //macro impl.AngularImpl.apply

  /**
   * Creates a new Angular
   * @param name
   * @param requires
   * @return
   */
  def module(name: String, requires: Iterable[String]) : Module = macro impl.AngularImpl.module

  trait Injector extends js.Object {
    def get(name: String) : js.Any = js.native
  }
}
