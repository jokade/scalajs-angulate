// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angular._
import utest._

import scala.scalajs.js

object ModuleTest extends AngulateTestSuite {
  override val tests = TestSuite {
    'new-{
      val module1 = angular.createModule("module1")
      assert( module1.name == "module1" )

      val module2 = angular.createModule("module2", Seq("module1"))
      assert( module2.name == "module2" )

    }

    'newWithConfig-{
      val module3 = angular.createModule("module3", Nil, ($logProvider: js.Dynamic) => {
        $logProvider.debugEnabled(true)
      })
      assert( module3.name == "module3" )
    }
  }
}
