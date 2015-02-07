// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Test cases for the angulate Directive binding
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angular.core.{Attributes, JQLite}
import biz.enef.angular._
import org.scalajs.dom.Element
import utest._

import scala.scalajs.js
import js.Dynamic.literal
import scala.scalajs.js.annotation.JSExport

object DirectiveTest extends AngulateTestSuite {
  override val tests = TestSuite {
    implicit val module = Angular.module("test", Nil)

    'directiveOf-{
      module.directiveOf[Directive1]
      module.directiveOf[Directive1]("anotherDirective")
      val $compile = injection[js.Dynamic]("$compile")

      val elem1 = $compile("<directive1></directive1>").apply(literal()).asInstanceOf[JQLite].head
      assert( elem1.textContent == "foo" )

      val elem2 = $compile("<another-directive></another-directive>").apply(literal()).asInstanceOf[JQLite].head
      assert( elem2.textContent == "foo" )
    }

    'postLink-{
      module.directiveOf[Directive2]
      val $compile = injection[js.Dynamic]("$compile")

      val elem = $compile("""<directive2></directive2>""").apply(literal(foo = "bar")).asInstanceOf[JQLite]
      assert( elem.head.textContent == "foo" )
    }

    'controllerAs-{
      module.directiveOf[Directive3]
      val $compile = injection[js.Dynamic]("$compile")
      val $rootScope = injection[Scope]("$rootScope")

      val elem = $compile("""<directive3 x="1"></directive3>""").apply(literal()).asInstanceOf[JQLite]
      //$rootScope.$digest()
      //println( elem.head.textContent )
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

    override def controller(ctrl: ControllerType, scope: Scope, elem: JQLite, attrs: Attributes): Unit = {
      ctrl.bar = "foo"
    }

    override def postLink(scope: Scope, element: JQLite, attrs: Attributes, ctrl: Directive2Ctrl): Unit = {
      element.head.textContent = ctrl.bar
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
