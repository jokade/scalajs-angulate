// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate

import acyclic.file
import biz.enef.angulate.Module.RichModule
import biz.enef.angulate.core._
import org.scalajs.dom.html.{Document, Element}
import org.scalajs.jquery.JQuery

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

  def bootstrap(element: String, modules: js.Array[Any]): Injector = js.native

  def bootstrap(element: String, modules: js.Array[Any], config: AngularConfiguration): Injector = js.native

  def bootstrap(element: String, modules: String): Injector = js.native

  def bootstrap(element: String, modules: String, config: AngularConfiguration): Injector = js.native

  def bootstrap(element: Element, modules: js.Array[Any]): Injector = js.native

  def bootstrap(element: Element, modules: js.Array[Any], config: AngularConfiguration): Injector = js.native

  def bootstrap(element: Element, modules: String): Injector = js.native

  def bootstrap(element: Element, modules: String, config: AngularConfiguration): Injector = js.native

  def bootstrap(element: Document, modules: js.Array[Any]): Injector = js.native

  def bootstrap(element: Document, modules: js.Array[Any], config: AngularConfiguration): Injector = js.native

  def bootstrap(element: Document, modules: String): Injector = js.native

  def bootstrap(element: Document, modules: String, config: AngularConfiguration): Injector = js.native

  def bootstrap(element: JQLite, modules: js.Array[Any]): Injector = js.native

  def bootstrap(element: JQLite, modules: js.Array[Any], config: AngularConfiguration): Injector = js.native

  def bootstrap(element: JQLite, modules: String): Injector = js.native

  def bootstrap(element: JQLite, modules: String, config: AngularConfiguration): Injector = js.native

  def bootstrap(element: JQuery, modules: js.Array[Any]): Injector = js.native

  def bootstrap(element: JQuery, modules: js.Array[Any], config: AngularConfiguration): Injector = js.native

  def bootstrap(element: JQuery, modules: String): Injector = js.native

  def bootstrap(element: JQuery, modules: String, config: AngularConfiguration): Injector = js.native

  def element: AugmentedJQLiteStatic = js.native

  def reloadWithDebugInfo(): Unit = js.native

  val version: AngularVersion = js.native

}

case class AngularConfiguration(strictDi: Boolean = false)

trait AngularVersion extends js.Object {
  val full: String = js.native
  val major: Int = js.native
  val minor: Int = js.native
  val dot: Int = js.native
  val codeName: String = js.native
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

    @inline def bootstrap(element: Element, modules: Iterable[String]) = self.bootstrap(element, modules.toJSArray.asInstanceOf[js.Array[Any]])

    @inline def bootstrap(element: Element, modules: Seq[AnnotatedFunction]) = self.bootstrap(element, modules.map(_.inlineArrayAnnotatedFn).toJSArray.asInstanceOf[js.Array[Any]])

    @inline def bootstrap(element: Element, modules: String, config: AngularConfiguration) = self.bootstrap(element, modules, config)

    @inline def bootstrap(element: Element, modules: Iterable[String], config: AngularConfiguration) = self.bootstrap(element, modules.toJSArray.asInstanceOf[js.Array[Any]], config)

    @inline def bootstrap(element: Element, modules: Seq[AnnotatedFunction], config: AngularConfiguration) = self.bootstrap(element, modules.map(_.inlineArrayAnnotatedFn).toJSArray.asInstanceOf[js.Array[Any]], config)

    @inline def jQueryElement: AugmentedJQueryStatic = self.element.asInstanceOf[AugmentedJQueryStatic]

  }

}
