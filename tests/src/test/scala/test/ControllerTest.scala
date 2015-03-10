// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Test cases for angulate controller enhancements
//
// Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angulate.core.HttpService
import biz.enef.angulate._
import utest._

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportAll}

object ControllerTest extends AngulateTestSuite {
  override val tests = TestSuite {
    implicit val module = angular.createModule("test")

    'ScopeController-{

      'explicitName-{
        module.controllerOf[ScopeCtrlSettingValue]("sctrl1")
        val scope = controller[TestScope]("sctrl1")
        assert(
          defined(scope),
          scope.name == "scope-ctrl-setting-value"
        )
      }

      'implicitName-{
        module.controllerOf[ScopeCtrlSettingValue]
        val scope = controller[TestScope]("test.ControllerTest.ScopeCtrlSettingValue")
        assert(
          defined(scope),
          scope.name == "scope-ctrl-setting-value"
        )
      }

      'dependencyInjection-{
        module.controllerOf[ScopeCtrlWithDI]("ScopeCtrlWithDI")
        module.serviceOf[TestService]
        val scope = controller[TestScope]("ScopeCtrlWithDI")
        assert(
          defined( scope ),
          scope.name == "scope-ctrl-with-DI"
        )
      }
    }


    'Controller-{

      'explicitName-{
        module.controllerOf[CtrlWithExplicitScope]("CtrlWithExplicitScope")
        val scope = controller[TestScope]("CtrlWithExplicitScope as hello")
        //scope.asInstanceOf[js.Dictionary[js.Any]].keys.foreach(println)
        assert(
          defined( scope ),
          defined(scope.asInstanceOf[js.Dynamic].$id),
          defined(scope.asInstanceOf[js.Dynamic].hello),
          scope.name == "ctrl-with-explicit-scope"
        )
      }

      'implicitName-{
        module.controllerOf[CtrlWithExplicitScope]
        val scope = controller[TestScope]("test.ControllerTest.CtrlWithExplicitScope as ctrl")
        //scope.asInstanceOf[js.Dictionary[js.Any]].keys.foreach(println)
        assert(
          defined( scope ),
          scope.name == "ctrl-with-explicit-scope"
        )
      }

      'dependencyInjection-{
        module.serviceOf[TestService]
        module.controllerOf[CtrlWithDI]("CtrlWithDI")
        val scope = controller[js.Dynamic]("CtrlWithDI as ctrl")
        assert(
          defined(scope),
          defined(scope.ctrl)
        )
      }

      'bodyScope-{
        module.controllerOf[CtrlIncludingScope]("CtrlIncludingScope")
        val scope = controller[js.Dynamic]("CtrlIncludingScope as ctrl")
        assert(
          defined(scope),
          defined(scope.ctrl),
          defined(scope.ctrl.myValue),
          scope.ctrl.myValue.asInstanceOf[String] == "ctrl-including-scope"
        )
      }
    }
  }


  class ScopeCtrlSettingValue($scope: TestScope) extends ScopeController {
    $scope.name = "scope-ctrl-setting-value"
  }

  class ScopeCtrlWithDI($http: HttpService, $scope: TestScope, @named("testService") service: TestService) extends ScopeController {
    assert( defined($http) )
    assert( defined($scope) )
    assert( defined(service) )
    assert( service.id == "testService" )
    $scope.name = "scope-ctrl-with-DI"
  }

  trait TestScope extends Scope {
    var name: String = js.native
  }

  class TestService extends Service {
    def id = "testService"
  }

  @JSExportAll
  class CtrlWithExplicitScope($scope: TestScope) extends Controller {
    $scope.name = "ctrl-with-explicit-scope"
  }

  @JSExportAll
  class CtrlWithDI(testService: TestService, @named("$http") http: js.Dynamic) extends Controller {
    assert( defined(testService) )
    assert( defined(http) )
  }

  @JSExportAll
  class CtrlIncludingScope extends Controller {
    var myValue = "ctrl-including-scope"
  }
}

