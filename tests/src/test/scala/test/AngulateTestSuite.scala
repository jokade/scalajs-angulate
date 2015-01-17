// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angular.{Controller, Scope, Module, Angular}
import utest._

import scala.scalajs.js
import scala.scalajs.js.{Dictionary, UndefOr}
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
  def injection[T](name: String)(implicit module: Module) : T =
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
  def controller[T](name: String)(implicit module: Module) : T = {
    val $controller = injection[js.Function2[String,js.Object,js.Any]]("$controller")
    val $rootScope = injection[Scope]("$rootScope")
    val scope = $rootScope.$new(false)
    val res = $controller(name, literal($scope = scope))
    scope.asInstanceOf[T]
  }
}


