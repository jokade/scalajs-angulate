// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Test cases for angulate component API
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import utest._
import biz.enef.angular._

object ComponentTest extends TestSuite {
  override val tests = TestSuite {
    implicit val module = Angular.module("test", Nil)

    'componentOf-{
      module.componentOf[Component1]
    }
  }

  @Component(selector = "world")
  class Component1
}
