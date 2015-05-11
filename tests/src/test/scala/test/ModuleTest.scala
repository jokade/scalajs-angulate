// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angulate._
import biz.enef.angulate.ext.{Route, RouteProvider}
import utest._

import scala.scalajs.js
import org.scalajs.dom

object ModuleTest extends AngulateTestSuite {
  override val tests = TestSuite {
    'new-{
      val module1 = angular.createModule("module1")
      assert( module1.name == "module1" )

      val module2 = angular.createModule("module2", Seq("module1"))
      assert( module2.name == "module2" )

    }

    'newWithConfig-{
      var configFnCalled = false
      val module3 = angular.createModule("module3", Nil, ($logProvider: js.Dynamic) => {
        $logProvider.debugEnabled(true)
        configFnCalled = true
      })
      angular.bootstrap(dom.document.body, Seq("module3"))
      assert( module3.name == "module3" )
      assert( configFnCalled )
    }

    'xx-{
      val module4 = angular.createModule("module4", Nil)
      module4.config( ($routeProvider:RouteProvider)=> {
        $routeProvider
          .when("/frontend/search",
            Route(templateUrl = "search.html", controller = "SearchCtrl"))
      })
    }
  }
}
