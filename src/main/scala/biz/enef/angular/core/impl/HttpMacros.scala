// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the macros for enhancements to the Angular $http API
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core.impl

import scala.reflect.macros.whitebox

class HttpMacros(val c: whitebox.Context) {
  import c.universe._

  def onCompleteImpl() = q"()"
}
