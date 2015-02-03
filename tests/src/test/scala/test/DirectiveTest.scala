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

      val elem = $compile("<directive2></directive2>").apply(literal(foo = "bar")).asInstanceOf[JQLite].head
      assert( elem.textContent == "bar" )
    }
  }


  class Directive1 extends Directive {
    override def template: String = "foo"
  }

  class Directive2 extends Directive {
    override type withController = Directive2Ctrl
    override def postLink(scope: Scope, element: JQLite, attrs: Attributes, controller: js.Dynamic): Unit = {
      element.head.textContent = scope.asInstanceOf[js.Dynamic].foo.toString
      controller.asInstanceOf[Directive2Ctrl]
    }
  }

  class Directive2Ctrl extends Controller {
    val foo = "bar"
  }
}
