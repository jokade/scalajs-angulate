// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angular.{Module, Angular}
import utest._

import scala.scalajs.js
import scala.scalajs.js.UndefOr

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
  def defined(value: js.Any) : Boolean = value != null && value.asInstanceOf[UndefOr[js.Any]].isDefined
}
