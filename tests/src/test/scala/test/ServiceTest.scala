// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Test cases for angulate service enhancements
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package test

import biz.enef.angular.core.HttpService
import biz.enef.angular.{named, Service, Angular}
import utest._

import scala.scalajs.js

object ServiceTest extends AngulateTestSuite {
  override val tests = TestSuite {
    implicit val module = Angular.module("test", Nil)

    'serviceOf-{

      'explicitName-{
        module.serviceOf[Service1]("$service1")
        val service1 = injection[Service1]("$service1")
        assert( service1.id == "service1" )
      }

      'derivedName-{
        module.serviceOf[Service1]
        val service1 = injection[Service1]("service1")
        assert( service1.id == "service1" )
      }

      'dependencyInjection-{
        module.serviceOf[Service1]
        module.serviceOf[Service2]
        val service1 = injection[Service1]("service1")
        val service2 = injection[Service2]("service2")
        val $http = injection[HttpService]("$http")
        val $log = injection[js.Dynamic]("$log")

        assert(
          service2.id == "service2",
          service2.s1.id == "service1",
          defined($http) && defined(service2.http),
          defined($log) && defined(service2.logger)
        )

      }

    }

  }

  class Service1 extends Service {
    val id = "service1"
  }

  class Service2($http: HttpService, service1: Service1, @named("$log") val logger: js.Dynamic) extends Service {
    val id = "service2"
    def http = $http
    def s1 = service1
  }
}

