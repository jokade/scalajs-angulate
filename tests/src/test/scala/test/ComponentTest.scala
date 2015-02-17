// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Test cases for angulate component API
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angulate.core.HttpService
import utest._
import biz.enef.angulate._

import scala.scalajs.js.annotation.JSExport

object ComponentTest extends AngulateTestSuite {
  override val tests = TestSuite {
    implicit val module = angular.createModule("test", Nil)

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
/*
    'controller-{
      module.componentOf[Component3]
      val elem3 = compileAndLink("<foo></foo>")
    }
    */
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
    template = "{{ctrl.foo}}"
  ))
  class Component3($http: HttpService) {
    val foo = "bar"
  }
}
