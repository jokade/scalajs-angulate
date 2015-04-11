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
import scala.scalajs.js.annotation.{JSExportAll, JSExport}

object DirectiveTest extends AngulateTestSuite {
  override val tests = TestSuite {
    implicit val module = angular.createModule("test", Nil)

    'directiveOf-{
      module.directiveOf[Directive1]
      module.directiveOf[Directive1]("anotherDirective")
      val $compile = dependency[Compile]("$compile")

      val elem1 = $compile("<directive1></directive1>")(literal())(0)
      assert( elem1.textContent == "foo" )

      val elem2 = $compile("<another-directive></another-directive>")(literal())(0)
      assert( elem2.textContent == "foo" )
    }

    'postLink-{
      module.directiveOf[Directive2]
      val $compile = dependency[Compile]("$compile")

      val elem = $compile("""<directive2></directive2>""")(literal(bar = "foo"))(0)
      js.Dynamic.global.console.log(elem.nodeName)
      js.Dynamic.global.console.log(elem.nodeValue)
      js.Dynamic.global.console.log("content: " + elem.textContent)
      assert( elem.textContent == "foo" )
    }

    // TODO
    'controllerAs-{
      module.directiveOf[Directive3]
      val $compile = dependency[Compile]("$compile")
      val $rootScope = dependency[Scope]("$rootScope")

      val elem = $compile("""<directive3 x="1"></directive3>""")($rootScope)(0)
      $rootScope.$digest()
      println( elem.textContent )
    }

    // TODO
    'require-{
      module.directiveOf[TabsDirective]("myTabs")
      module.directiveOf[PaneDirective]("myPane")
      val $compile = dependency[Compile]("$compile")
      val $rootScope = dependency[Scope]("$rootScope")

      val elem = $compile("""<my-tabs>
                            |  <my-pane title="Hello">
                            |    <h4>Hello</h4>
                            |    <p>Lorem ipsum dolor sit amet</p>
                            |  </my-pane>
                            |  <my-pane title="World">
                            |    <h4>World</h4>
                            |    <em>Mauris elementum elementum enim at suscipit.</em>
                            |    <p><a href ng-click="i = i + 1">counter: {{i || 0}}</a></p>
                            |  </my-pane>
                            |</my-tabs>""")($rootScope)(0)
      js.Dynamic.global.console.log(elem.nodeName)
      js.Dynamic.global.console.log(elem.nodeValue)
      js.Dynamic.global.console.log("content: " + elem.textContent)
      //assert( elem.textContent == "foo" )
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

  trait Pane extends js.Object {
    var selected: Boolean = js.native
  }

  trait TabsDirectiveController extends js.Object {
    var addPane: js.Function1[Pane, Unit] = js.native
  }
  trait TabsDirectiveScope extends js.Object {
    var panes: Seq[Pane] = js.native
    var select: js.Function1[Pane, Unit] = js.native
  }

  class TabsDirective extends Directive {

    override def restrict= "E"
    override def transclude = true

    override def isolateScope = js.Dictionary()
    override type ControllerType = TabsDirectiveController
    override type ScopeType = TabsDirectiveScope
    override def controller(ctrl: ControllerType, scope: ScopeType, elem: JQLite, attrs: Attributes) = {
      scope.panes = Seq()
      scope.select = (pane: Pane) => {
        scope.panes.foreach(_.selected = false)
        pane.selected = true
      }
      ctrl.addPane = (pane: Pane) => {
        if (scope.panes.isEmpty) scope.select(pane)
        scope.panes = scope.panes :+ pane
      }
    }

    override def template = """<div class="tab-pane" ng-show="selected" ng-transclude>
                              |</div>"""
  }

  class PaneDirective extends Directive {
    override def require = "^myTabs"
    override def restrict= "E"
    override def transclude = true
    override def isolateScope = js.Dictionary("title" -> "@")
    override type ScopeType = Pane
    override type ControllerType = TabsDirectiveController
    override def postLink(scope: ScopeType, element: JQLite, attrs: Attributes, tabsCtrl: TabsDirectiveController): Unit = {
      tabsCtrl.addPane(scope)
    }
    override def template = """<div class="tabbable">
                              |  <ul class="nav nav-tabs">
                              |    <li ng-repeat="pane in panes" ng-class="{active:pane.selected}">
                              |      <a href="" ng-click="select(pane)">{{pane.title}}</a>
                              |    </li>
                              |  </ul>
                              |  <div class="tab-content" ng-transclude></div>
                              |</div>"""

  }
}
