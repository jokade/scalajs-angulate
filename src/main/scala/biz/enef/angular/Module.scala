// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.language.experimental.macros
import scala.scalajs.js

/**
 * Defines the bindings to the angular.Module API.
 *
 * @see [[https://docs.angularjs.org/api/ng/type/angular.Module]]
 */
trait Module  extends js.Object {

  /**
   * Registers a controller.
   *
   * @param name The name of the controller
   * @param constructor Controller construction function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#controller]]
   */
  def controller(name: String, constructor: js.Function) : Module

  def controller(name: String, constructor: js.Array[Any]) : Module

  /**
   * Register a service factory.
   *
   * @param name
   * @param constructor
   * @return
   */
  def factory(name: String, constructor: js.Function) : Module

  def factory(name: String, constructor: js.Array[Any]) : Module

  /**
   * Registers the specified controller using the fully qualified class as the name of the controller.
   *
   * @note This is a scalajs-angulate enhancement
   *
   * @tparam T Controller class
   */
  def controllerOf[T<:Controller] : Module = macro impl.ControllerMacros.controllerOf[T]

  /**
   * Registers the specified controller using an explicitly given controller name.
   *
   * @note This is a scalajs-angulate enhancement
   *
   * @param name The controller name
   * @tparam T Controller class
   */
  def controllerOf[T<:Controller](name: String) : Module = macro impl.ControllerMacros.controllerOfWithName[T]

  /**
   * Registers the specified class as Angular service.
   * The class name is used as the name of the service, with the __first letter in lower case__.
   *
   * @note This is a scalajs-angulate enhancement
   *
   * @tparam T
   * @return
   */
  def serviceOf[T<:Service] : Module = macro impl.ServiceMacros.serviceOf[T]

  /**
   * Registers the specified class as Angular service using the explicitly given service name.
   *
   * @param name
   * @tparam T
   * @return
   */
  def serviceOf[T<:Service](name: String) : Module = macro impl.ServiceMacros.serviceOfWithName[T]
}

