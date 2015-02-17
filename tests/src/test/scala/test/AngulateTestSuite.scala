// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Utility trait for tests
//
// Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angulate.Module.RichModule
import biz.enef.angulate.{Scope, Angular}
import biz.enef.angulate.core.JQLite
import biz.enef.angulate.{Controller, Scope, Module, angular}
import utest._

import scala.concurrent.Promise
import scala.scalajs.js
import scala.scalajs.js.UndefOr
import js.Dynamic.literal

/**
 * Base trait for all angulate tests; provides some common utility functions
 */
trait AngulateTestSuite extends TestSuite {
  /**
   * Returns the dependency with the specified name from the (implicitly) specified module.
   *
   * @param name
   * @param module
   * @tparam T
   * @return
   */
  def dependency[T](name: String)(implicit module: RichModule) : T =
    angular.injector(js.Array("ng",module.name)).get(name).asInstanceOf[T]

  /**
   * Checks that the specified value is neither null nor undefined.
   *
   * @param value
   */
  def defined(value: Any) : Boolean = value != null && value.asInstanceOf[UndefOr[js.Any]].isDefined

  /**
   * Compiles the specified template and links it with the provided scope
   *
   * @param tpl
   * @param scope
   * @param module
   */
  def compileAndLink(tpl: String, scope: js.Object = literal())(implicit module: RichModule) : JQLite = {
    dependency[js.Dynamic]("$compile").apply(tpl).apply(scope).asInstanceOf[JQLite]
  }

  /**
   * Creates an instance specified controller and returns its scope object.
   *
   * @param name the name of the controller type
   * @param module
   * @tparam T the scope type
   */
  def controller[T](name: String)(implicit module: RichModule) : T = {
    val $controller = dependency[js.Function2[String,js.Object,js.Any]]("$controller")
    val $rootScope = dependency[Scope]("$rootScope")
    val scope = $rootScope.$new(false)
    val res = $controller(name, literal($scope = scope))
    scope.asInstanceOf[T]
  }

  def promise() : AssertionPromise = new AssertionPromise( Promise[Unit]() )

  class AssertionPromise(p: Promise[Unit]) {
    def ok() = p.success( () )
    def fail() = p.failure(new RuntimeException)
    def future = p.future
    def assert(expr: =>Boolean) : Unit = if(expr) ok() else fail()
  }
}


