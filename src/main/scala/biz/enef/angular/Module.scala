// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Facade and enhancements for AngularJS Module API
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.language.experimental.macros
import scala.scalajs.js

/**
 * Defines the bindings to the angular.Module API and enhancements provided by scalajs-angulate.
 *
 * @see [[https://docs.angularjs.org/api/ng/type/angular.Module]]
 */
trait Module  extends js.Object {

  //------------------------------ ANGULAR.JS --------------------------------
  /**
   * The name of the module
   */
  def name: String

  /**
   * Defines an animation hook that can be later used with the $animate service and directives that use this service.
   *
   * @note animations take effect only if the ngAnimate module is loaded
   *
   * @param name animation name
   * @param animationFactory Factory function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#animate]]
   */
  def animation(name: String, animationFactory: js.Function) : Module

  /**
   * Defines an animation hook that can be later used with the $animate service and directives that use this service.
   *
   * @note animations take effect only if the ngAnimate module is loaded
   *
   * @param name animation name
   * @param animationFactory Array with the names of the dependencies to be injected.
   *                         The last element in this array must be the factory function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#animate]]
   */
  def animation(name: String, animationFactory: js.Array[Any]) : Module

  /**
   * Use this method to register work which needs to be performed on module loading.
   *
   * @param configFn This function is executed on module load
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#config]]
   */
  def config(configFn: js.Function) : Module

  /**
   * Use this method to register work which needs to be performed on module loading.
   *
   * @param configFn Array with the names of the dependencies to be injected.
   *                 The last element in this array must be the function to be called on module load
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#config]]
   */
  def config(configFn: js.Array[Any]) : Module

  /**
   * Register a constant service, such as a string, number, array ...
   *
   * @param name The name of the constant
   * @param value The constant value
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#constant]]
   */
  def constant(name: String, value: js.Any) : Module

  /**
   * Registers a controller.
   *
   * @param name The name of the controller
   * @param constructor Controller construction function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#controller]]
   */
  def controller(name: String, constructor: js.Function) : Module

  /**
   * Registers a controller.
   *
   * @param name The name of the controller
   * @param constructor Array containing the names of the dependencies to be injected and
   *                    the constructor function as last element
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#controller]]
   */
  def controller(name: String, constructor: js.Array[Any]) : Module

  /**
   * Register a new directive with the compiler.
   *
   * @param name Name of the directive in camel-case (ie `ngBind`)
   * @param directiveFactory Directive constructor function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#directive]]
   */
  def directive(name: String, directiveFactory: js.Function) : Module

  /**
   * Register a new directive with the compiler.
   *
   * @param name Name of the directive in camel-case (ie `ngBind`)
   * @param directiveFactory Array containing the names of the dependencies to be injected and
   *                         the constructor function as last element
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#directive]]
   */
  def directive(name: String, directiveFactory: js.Array[Any]) : Module

  /**
   * Register a service factory.
   *
   * @param name The name of the service
   * @param constructor Service constructor function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#factory]]
   */
  def factory(name: String, constructor: js.Function) : Module

  /**
   * Register a service factory.
   *
   * @param name The name of the service
   * @param constructor Array containing the names of the dependencies to be injected and
   *                    the constructor function as last element
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#factory]]
   */
  def factory(name: String, constructor: js.Array[Any]) : Module

  /**
   * Register a filter factory.
   *
   * @param name The name of the filter
   * @param filterFactory Filter constructor function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#filter]]
   */
  def filter(name: String, filterFactory: js.Function) : Module

  /**
   * Register a filter factory.
   *
   * @param name The name of the filter
   * @param filterFactory Array containing the names of the dependencies to be injected and
   *                      the constructor function as last element
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#filter]]
   */
  def filter(name: String, filterFactory: js.Array[Any]) : Module

  /**
   * Register a provider function with the $injector.
   *
   * @param name The name of the instance. NOTE: the provider will be available under name + 'Provider' key.
   * @param constructor Provider constructor function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#provider]]
   */
  def provider(name: String, constructor: js.Function) : Module

  /**
   * Register a provider function with the $injector.
   *
   * @param name The name of the instance. NOTE: the provider will be available under name + 'Provider' key.
   * @param constructor Array containing the names of the dependencies to be injected and
   *                    the constructor function as last element
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#provider]]
   */
  def provider(name: String, constructor: js.Array[Any]) : Module
  
  /**
   * Use this method to register work which should be performed when the injector is done loading all modules.
   *
   * @param initializationFn This function is executed when all modules have been loaded
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#run]]
   */
  def run(initializationFn: js.Function) : Module

  /**
   * Use this method to register work which should be performed when the injector is done loading all modules.
   *
   * @param initializationFn Array containing the names of the dependencies to be injected and
   *                         the initialization function as last element
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#run]]
   */
  def run(initializationFn: js.Array[Any]) : Module

  /**
   * Register a service constructor which will be invoked with `new` to create the service instance.
   *
   * @param name The name of the service
   * @param constructor A class constructor function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#service]]
   */
  def service(name: String, constructor: js.Function) : Module

  /**
   * Register a service constructor which will be invoked with `new` to create the service instance.
   *
   * @param name The name of the service
   * @param constructor A class constructor function
   *
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#service]]
   */
  def service(name: String, constructor: js.Array[Any]) : Module


  //------------------------------ ENHANCEMENTS ------------------------------
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
   * @tparam T Service class
   */
  def serviceOf[T<:Service] : Module = macro impl.ServiceMacros.serviceOf[T]

  /**
   * Registers the specified class as Angular service using the explicitly given service name.
   *
   * @note This is a scalajs-angulate enhancement
   *
   * @param name The service name
   * @tparam T Service class
   */
  def serviceOf[T<:Service](name: String) : Module = macro impl.ServiceMacros.serviceOfWithName[T]

  /**
   * Registers the specified class as Angular directive.
   * The name of the directive will be the name of the class, with the first letter in lower case and
   * without the suffix 'Directive'.
   *
   * @note This is a scalajs-angulate enhancement
   *
   * @tparam T Class defining the directive
   */
  def directiveOf[T<:Directive] : Module = macro impl.DirectiveMacros.directiveOf[T]

  /**
   * Registers the specified class as Angular directive under the given name.
   *
   * @note This is a scalajs-angulate enhancement
   *
   * @param name The name of the directive
   * @tparam T Class defining the directive
   */
  def directiveOf[T<:Directive](name: String) : Module = macro impl.DirectiveMacros.directiveOfWithName[T]

}

