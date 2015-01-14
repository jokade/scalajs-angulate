// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angular.Angular
import utest._

object ModuleTest extends AngulateTestSuite {
  override val tests = TestSuite {
    'new-{
      val module1 = Angular.module("module1", Nil)
      assert( module1.name == "module1" )

      val module2 = Angular.module("module2", Seq("module1"))
      assert( module2.name == "module2" )

    }
  }
}
