// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Facade and enhancements for AngularJS Module API
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate

import acyclic.file
import scala.scalajs.js
import scala.scalajs.js.|

/**
 * Defines the bindings to the angular.Module API and enhancements provided by scalajs-angulate.
 *
 * @see [[https://docs.angularjs.org/api/ng/type/angular.Module]]
 */
@js.native
trait Module  extends js.Object {

  //------------------------------ ANGULAR.JS --------------------------------
  /**
   * The name of the module
   */
  def name: String = js.native

  /**
   * Defines an animation hook that can be later used with the \$animate service and directives that use this service.
   *
   * @note animations take effect only if the ngAnimate module is loaded
   * @param name animation name
   * @param animationFactory Array with the names of the dependencies to be injected.
   *                         The last element in this array must be the factory function
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#animate]]
   */
  def animation(name: String, animationFactory: js.Array[Any]): Module = js.native

  def component(name: String, options: js.Any): Module = js.native

  /**
   * Use this method to register work which needs to be performed on module loading.
   *
   * @param configFn Array with the names of the dependencies to be injected.
   *                 The last element in this array must be the function to be called on module load
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#config]]
   */
  def config(configFn: js.Array[Any]): Module = js.native

  /**
   * Register a constant service, such as a string, number, array ...
   *
   * @param name The name of the constant
   * @param value The constant value
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#constant]]
   */
  def constant(name: String, value: js.Any): Module = js.native

  /**
   * Registers a controller.
   *
   * @param name The name of the controller
   * @param constructor Array containing the names of the dependencies to be injected and
   *                    the constructor function as last element
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#controller]]
   */
  def controller(name: String, constructor: js.Array[Any]): Module = js.native

  /**
   * Register a new directive with the compiler.
   *
   * @param name Name of the directive in camel-case (ie `ngBind`)
   * @param directiveFactory Array containing the names of the dependencies to be injected and
   *                         the constructor function as last element
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#directive]]
   */
  def directive(name: String, directiveFactory: js.Array[Any]): Module = js.native

  /**
   * Register a new directive with the compiler.
   *
   * @param name Name of the directive in camel-case (ie `ngBind`)
   * @param directiveFactory Function that returns the directive definition object (DDO) when called
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#directive]]
   */
  def directive(name: String, directiveFactory: js.Function): Module = js.native

  /**
   * Register a service factory.
   *
   * @param name The name of the service
   * @param constructor Array containing the names of the dependencies to be injected and
   *                    the constructor function as last element
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#factory]]
   */
  def factory(name: String, constructor: js.Array[Any]): Module = js.native

  /**
   * Register a filter factory.
   *
   * @param name The name of the filter
   * @param filterFactory Array containing the names of the dependencies to be injected and
   *                      the constructor function as last element
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#filter]]
   */
  def filter(name: String, filterFactory: js.Array[Any]): Module = js.native

  /**
   * Register a provider function with the \$injector.
   *
   * @param name The name of the instance. NOTE: the provider will be available under name + 'Provider' key.
   * @param constructor Array containing the names of the dependencies to be injected and
   *                    the constructor function as last element
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#provider]]
   */
  def provider(name: String, constructor: js.Array[Any]): Module = js.native

  /**
   * Use this method to register work which should be performed when the injector is done loading all modules.
   *
   * @param initializationFn Array containing the names of the dependencies to be injected and
   *                         the initialization function as last element
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#run]]
   */
  def run(initializationFn: js.Array[Any]): Module = js.native

  /**
   * Register a service constructor which will be invoked with `new` to create the service instance.
   *
   * @param name The name of the service
   * @param constructor A class constructor function
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#service]]
   */
  def service(name: String, constructor: js.Array[Any]): Module = js.native

  /**
   * Register a constant value.
   *
   * @param name The name of the instance
   * @param value The value.
   * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#value]]
   */
  def value(name: String, value: js.Any): Module = js.native

}

object Module {

  import scala.language.experimental.macros
  import scala.language.implicitConversions

  @inline final implicit class RichModule(val self: Module) extends AnyVal {

    /**
     * The name of the module
     */
    @inline def name: String = self.name

    /**
     * Defines an animation hook that can be later used with the \$animate service and directives that use this service.
     *
     * @note animations take effect only if the ngAnimate module is loaded
     * @param name animation name
     * @param animationFactory Factory function
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#animate]]
     */
    @inline def animation(name: String, animationFactory: AnnotatedFunction): Module = self.animation(name, animationFactory.inlineArrayAnnotatedFn)

    /**
     * Use this method to register work which needs to be performed on module loading.
     *
     * @param configFn This function is executed on module load
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#config]]
     */
    @deprecated("use configFn() instead","0.3")
    @inline def config(configFn: AnnotatedFunction): Module = self.config(configFn.inlineArrayAnnotatedFn)

    /**
     * Use this method to register work which needs to be performed on module loading.
     *
     * @param configFn This function is executed on module load
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#config]]
     */
    @inline def configFn(configFn: AnnotatedFunction): Module = self.config(configFn.inlineArrayAnnotatedFn)


    /**
     * Registers a controller.
     *
     * @param name The name of the controller
     * @param constructor Controller construction function
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#controller]]
     */
    @inline def controller(name: String, constructor: AnnotatedFunction): Module = self.controller(name, constructor.inlineArrayAnnotatedFn)

    /**
     * Register a new directive with the compiler.
     *
     * @param name Name of the directive in camel-case (ie `ngBind`)
     * @param directiveFactory Directive constructor function
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#directive]]
     */
    @inline def directive(name: String, directiveFactory: AnnotatedFunction): Module = self.directive(name, directiveFactory.inlineArrayAnnotatedFn)

    /**
     * Register a service factory.
     *
     * @param name The name of the service
     * @param constructor Service constructor function
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#factory]]
     */
    @inline def factory(name: String, constructor: AnnotatedFunction): Module = self.factory(name, constructor.inlineArrayAnnotatedFn)

    /**
     * Register a filter factory.
     *
     * @param name The name of the filter
     * @param filterFactory Filter constructor function
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#filter]]
     */
    @inline def filter(name: String, filterFactory: AnnotatedFunction): Module = self.filter(name, filterFactory.inlineArrayAnnotatedFn)

    /**
     * Register a provider function with the \$injector.
     *
     * @param name The name of the instance. NOTE: the provider will be available under name + 'Provider' key.
     * @param constructor Provider constructor function
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#provider]]
     */
    @inline def provider(name: String, constructor: AnnotatedFunction): Module = self.provider(name, constructor.inlineArrayAnnotatedFn)

    /**
     * Use this method to register work which should be performed when the injector is done loading all modules.
     *
     * @param initializationFn This function is executed on module fully loaded
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#run]]
     */
    @deprecated("use runFn() instead","0.3")
    @inline def run(initializationFn: AnnotatedFunction): Module = self.run(initializationFn.inlineArrayAnnotatedFn)

    /**
     * Use this method to register work which should be performed when the injector is done loading all modules.
     *
     * @param initializationFn This function is executed on module fully loaded
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#run]]
     */
    @inline def runFn(initializationFn: AnnotatedFunction): Module = self.run(initializationFn.inlineArrayAnnotatedFn)

    /**
     * Register a service constructor which will be invoked with `new` to create the service instance.
     *
     * @param name The name of the service
     * @param constructor A class constructor function
     * @see [[https://docs.angularjs.org/api/ng/type/angular.Module#service]]
     */
    @inline def service(name: String, constructor: AnnotatedFunction): Module = self.service(name, constructor.inlineArrayAnnotatedFn)


    //------------------------------ ENHANCEMENTS ------------------------------
    /**
     * Registers the specified controller using the fully qualified class as the name of the controller.
     *
     * @note This is a scalajs-angulate enhancement
     * @tparam T Controller class
     */
    @inline def controllerOf[T <: NGController]: Module = macro impl.ControllerMacros.controllerOf[T]

    /**
     * Registers the specified controller using an explicitly given controller name.
     *
     * @note This is a scalajs-angulate enhancement
     * @param name The controller name
     * @tparam T Controller class
     */
    @inline def controllerOf[T <: NGController](name: String): Module = macro impl.ControllerMacros.controllerOfWithName[T]

    /**
     * Registers the specified class as Angular service.
     * The class name is used as the name of the service, with the __first letter in lower case__.
     *
     * @note This is a scalajs-angulate enhancement
     * @tparam T Service class
     */
    @inline def serviceOf[T <: Service]: Module = macro impl.ServiceMacros.serviceOf[T]

    /**
     * Registers the specified class as Angular service using the explicitly given service name.
     *
     * @note This is a scalajs-angulate enhancement
     * @param name The service name
     * @tparam T Service class
     */
    @inline def serviceOf[T <: Service](name: String): Module = macro impl.ServiceMacros.serviceOfWithName[T]

    /**
     * Registers the specified class as Angular directive.
     * The name of the directive will be the name of the class, with the first letter in lower case and
     * without the suffix 'Directive'.
     *
     * @note This is a scalajs-angulate enhancement
     * @tparam T Class defining the directive
     */
    @inline def directiveOf[T <: Directive]: Module = macro impl.DirectiveMacros.directiveOf[T]

    /**
     * Registers the specified class as Angular directive under the given name.
     *
     * @note This is a scalajs-angulate enhancement
     * @param name The name of the directive
     * @tparam T Class defining the directive
     */
    @inline def directiveOf[T <: Directive](name: String): Module = macro impl.DirectiveMacros.directiveOfWithName[T]

    /**
     * Registers an Angular2-style Component.
     *
     * @tparam T
     */
    @inline def componentOf[T] : Module = macro Component.Macro.componentOf[T]

    @inline def rootComponent(name: String): Module = self.value("$routerRootComponent",name)

  }

  object RichModule {
    @inline implicit def autoUnwrapRichModule(m: RichModule): Module = m.self
  }

}
