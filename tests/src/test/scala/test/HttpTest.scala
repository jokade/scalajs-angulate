// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angulate._
import biz.enef.angulate.core.HttpService
import utest._

import scala.scalajs.js
import scala.util.{Failure, Success}
import js.Dynamic.literal

import scala.language.implicitConversions
import AnnotatedFunction._
import Module._

object HttpTest extends AngulateTestSuite {

  override val tests = TestSuite {
    implicit val module = angular.createModule("test", Seq("ngMockE2E"))
    module.run ( ($httpBackend: js.Dynamic) => {
      $httpBackend.whenGET("/ok").respond( literal(id = 200) )
      $httpBackend.whenGET("/error").respond(404,"resource not found")
      $httpBackend.whenPOST("/empty").respond(204)
    })
    val $http = dependency[HttpService]("$http")

    var ok = false

    'onComplete-{

      'success-{
        val p = promise()
        $http.get[Data]("/ok").onComplete {
          case Success(data) => p.assert( data.id == 200 )
          case x => p.fail()
        }
        p.future
      }

      'failure-{
        val p = promise()
        $http.get[Data]("/error").onComplete {
          case Success(_) => p.fail()
          case Failure(ex) => p.ok()
        }
        p.future
      }

      'expectEmptyResponse-{
        val p = promise()

        $http.post[Unit]("/empty").onComplete{
          case Success(x) => p.ok()
          case _ => p.fail()
        }
        p.future
      }
    }


    'onSuccess-{

      'success-{
        val p = promise()
        $http.get[Data]("/ok").onSuccess { data => p.assert( data.id == 200 ) }
        p.future
      }

      'expectEmptyResponse-{
        val p = promise()

        $http.post[Unit]("/empty").onSuccess( x => p.ok() )

        p.future
      }
    }
  }

  trait Data extends js.Object {
    def id: Int = js.native
  }
}
