// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Test cases for angulate controller enhancements
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angular.core.HttpService
import biz.enef.angular._
import utest._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

object ControllerTest extends AngulateTestSuite {
  override val tests = TestSuite {
    implicit val module = Angular.module("test", Nil)

    'ScopeController-{

      'explicitName-{
        module.controllerOf[ScopeCtrl1]("sctrl1")
        val scope = controller[TestScope]("sctrl1")
        assert(
          defined(scope),
          scope.name == "sctrl1"
        )
      }

      'implicitName-{
        module.controllerOf[ScopeCtrl1]
        val scope = controller[TestScope]("test.ControllerTest.ScopeCtrl1")
        assert(
          defined(scope),
          scope.name == "sctrl1"
        )
      }

      'dependencyInjection-{
        module.controllerOf[ScopeCtrl2]("ScopeCtrl")
        module.serviceOf[TestService]
        val scope = controller[TestScope]("ScopeCtrl")
        assert(
          defined( scope ),
          scope.name == "sctrl2"
        )
      }
    }


    'Controller-{

      'explicitName-{
        module.controllerOf[Ctrl1]("Ctrl1")
        val scope = controller[TestScope]("Ctrl1 as hello")
        assert(
          defined( scope ),
          scope.name == "ctrl1"
        )
      }

      'implicitName-{
        module.controllerOf[Ctrl1]
        val scope = controller[TestScope]("test.ControllerTest.Ctrl1 as ctrl")
        assert(
          defined( scope ),
          scope.name == "ctrl1"
        )
      }

      'dependencyInjection-{
        module.serviceOf[TestService]
        module.controllerOf[Ctrl2]("Ctrl2")
        val scope = controller[js.Dynamic]("Ctrl2 as ctrl")
        assert(
          defined(scope),
          defined(scope.ctrl)
        )
      }

      // TODO: test if property name from class body is copied to the scope!
      'bodyScope-{
        module.controllerOf[Ctrl3]("Ctrl3")
        val scope = controller[js.Dynamic]("Ctrl3 as ctrl")
        //scope.$$ChildScope.asInstanceOf[js.Dictionary[js.Any]].keys.foreach(println)
        /*assert(
          defined(scope),
          scope.name.asInstanceOf[String] == "ctrl3"
        )*/
      }
    }
  }


  class ScopeCtrl1($scope: TestScope) extends ScopeController {
    $scope.name = "sctrl1"
  }

  class ScopeCtrl2($http: HttpService, $scope: TestScope, @named("testService") service: TestService) extends ScopeController {
    assert( defined($http) )
    assert( service.id == "testService" )
    $scope.name = "sctrl2"
  }

  trait TestScope extends Scope {
    var name: String = ???
  }

  class TestService extends Service {
    def id = "testService"
  }

  class Ctrl1($scope: TestScope) extends Controller {
    $scope.name = "ctrl1"
  }

  class Ctrl2(testService: TestService, @named("$http") http: js.Dynamic) extends Controller {
    assert( defined(testService) )
    assert( defined(http) )
  }

  class Ctrl3 extends Controller {
    var name = "ctrl3"
  }
}

