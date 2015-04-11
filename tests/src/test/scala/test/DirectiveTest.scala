// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Test cases for the angulate Directive binding
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angulate.core.{Compile, AugmentedJQLite, Attributes, JQLite}
import biz.enef.angulate._
import org.scalajs.dom
import org.scalajs.dom.Element
import utest._

import scala.scalajs.js
import js.Dynamic.literal
import scala.scalajs.js.annotation.JSExport

object DirectiveTest extends AngulateTestSuite {
  override val tests = TestSuite {
    implicit val module = angular.createModule("test", Nil)

    'directiveOf-{
      module.directiveOf[Directive1]
      module.directiveOf[Directive1]("anotherDirective")
      val $compile = injection[Compile]("$compile")

      val elem1 = $compile("<directive1></directive1>")(literal())(0)
      assert( elem1.textContent == "foo" )

      val elem2 = $compile("<another-directive></another-directive>")(literal())(0)
      assert( elem2.textContent == "foo" )
    }

    'postLink-{
      module.directiveOf[Directive2]
      val $compile = injection[Compile]("$compile")

      val elem = $compile("""<directive2></directive2>""")(literal(bar = "foo"))(0)
      js.Dynamic.global.console.log(elem.nodeName)
      js.Dynamic.global.console.log(elem.nodeValue)
      js.Dynamic.global.console.log("content: " + elem.textContent)
      assert( elem.textContent == "foo" )
    }

    'controllerAs-{
      module.directiveOf[Directive3]
      val $compile = injection[Compile]("$compile")
      val $rootScope = injection[Scope]("$rootScope")

      val elem = $compile("""<directive3 x="1"></directive3>""")($rootScope)(0)
      $rootScope.$digest()
      println( elem.textContent )
    }
  }


  class Directive1 extends Directive {
    override def template: String = "foo"
  }

  trait Directive2Ctrl extends js.Object {
    var bar: String = js.native
  }

  class Directive2 extends Directive {
    override type ControllerType = Directive2Ctrl

    override def controller(ctrl: ControllerType, scope: ScopeType, elem: JQLite, attrs: Attributes): Unit = {
      ctrl.bar = "foo"
    }

    override def postLink(scope: ScopeType, element: JQLite, attrs: Attributes, ctrl: Directive2Ctrl): Unit = {
      element(0).textContent = ctrl.bar
    }
  }

  trait Directive3Ctrl extends js.Object {
    var bar: String = js.native
    var click: js.Function = js.native
  }

  class Directive3 extends Directive {
    override type ControllerType = Directive3Ctrl
    //override val controllerAs = "ctr"
    override val template = "<div>x</div>"

    override def isolateScope = js.Dictionary(
      "x" -> "=x"
    )
/*
    override def controller(ctrl: ControllerType, scope: Scope, elem: JQLite, attrs: Attributes): Unit = {
      println(ctrl)
      ctrl.bar = "foo"
      ctrl.click = () => ctrl.bar = "bar"

    }*/
  }
}
