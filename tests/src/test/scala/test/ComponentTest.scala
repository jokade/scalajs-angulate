// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Test cases for angulate component API
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import utest._
import biz.enef.angular._

object ComponentTest extends AngulateTestSuite {
  override val tests = TestSuite {
    implicit val module = Angular.module("test", Nil)

    'componentDef-{
      'orderedComponentDef- {
        module.componentOf[Component1]
        val elem1 = compileAndLink("<foo></foo>")
        assert(elem1.head.textContent == "component1")
      }

      'unorderedComponentDef-{
        module.componentOf[Component2]
        val elem2 = compileAndLink("<foo></foo>")
        assert(elem2.head.textContent == "component2" )
      }
    }

    'controller-{

    }
  }


  @Component( ComponentDef(
    selector = "foo",
    template = "component1"
  ))
  class Component1

  @Component( ComponentDef(
    template = "component2",
    selector = "foo"
  ))
  class Component2

  @Component( ComponentDef(
    selector = "foo",
    template = "ctrl"

  ))
  class Component3 {
    val foo = "bar"
  }
}
