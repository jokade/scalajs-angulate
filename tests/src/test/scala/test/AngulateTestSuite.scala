// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angular.Module.RichModule
import biz.enef.angular.{Scope, Angular}
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
  def injection[T](name: String)(implicit module: RichModule) : T =
    Angular().injector(js.Array("ng",module.name)).get(name).asInstanceOf[T]

  /**
   * Checks that the specified value is neither null nor undefined.
   *
   * @param value
   */
  def defined(value: Any) : Boolean = value != null && value.asInstanceOf[UndefOr[js.Any]].isDefined

  /**
   * Creates an instance specified controller and returns its scope object.
   *
   * @param name the name of the controller type
   * @param module
   * @tparam T the scope type
   */
  def controller[T](name: String)(implicit module: RichModule) : T = {
    val $controller = injection[js.Function2[String,js.Object,js.Any]]("$controller")
    val $rootScope = injection[Scope]("$rootScope")
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


