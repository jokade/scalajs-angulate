// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import acyclic.file
import biz.enef.angular.Module.RichModule
import biz.enef.angular.core.Injector

import scala.scalajs.js

/**
 * Defines the bindings to the global angular object.
 *
 * @see [[https://docs.angularjs.org/api/ng]]
 */
trait Angular extends js.Object {

  def injector(modules: js.Any, strictDi: Boolean = false) : Injector = js.native

  /**
   * Retrieves an Angular module.
   *
   * @param name The name of the module to retrieve.
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.module]]
   */
  def module(name: String) : Module = js.native

  /**
   * Creates an Angular module.
   *
   * @param name The name of the module to create .
   * @param requires Array with the names of other modules required by this module.
   *                 If specified then a new module is being created. If unspecified then the
   *                 module is being retrieved for further configuration.
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.module]]
   */
  def module(name: String, requires: js.Array[String]) : Module = js.native

  /**
   * Creates  an Angular module.
   *
   * @param name The name of the module to create.
   * @param requires Array with the names of other modules required by this module.
   *                 If specified then a new module is being created. If unspecified then the
   *                 module is being retrieved for further configuration.
   * @param configFn Optional configuration function for the module.
   *
   * @see [[https://docs.angularjs.org/api/ng/function/angular.module]]
   */
  def module(name: String, requires: js.Array[String], configFn: js.Array[Any]) : Module = js.native

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

  /**
   * Returns the global Angular object
   */
  def apply() : Angular = js.Dynamic.global.angular.asInstanceOf[Angular]

  @inline final implicit class RichAngular(val self: Angular) extends AnyVal {
    import scala.scalajs.js.JSConverters._

    /**
     * Creates a new Angular module
     * @param name
     * @return
     */
    @inline def createModule(name: String) : RichModule = self.module(name, js.Array())

    /**
     * Creates a new Angular module
     * @param name
     * @param requires
     * @return
     */
    @inline def createModule(name: String, requires: Iterable[String]) : RichModule = self.module(name, requires.toJSArray)

    /**
     * Creates a new Angular module
     * @param name
     * @param requires
     * @return
     */
    @inline def createModule(name: String, requires: Iterable[String], configFn: AnnotatedFunction) : RichModule =
      self.module(name, requires.toJSArray, configFn.inlineArrayAnnotatedFn)
  }

}
