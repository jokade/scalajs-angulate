// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the binding for the Angular $timeout service
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.scalajs.js

trait Timeout extends js.Object {
  def apply(fn: js.Function, delay: Int = 0, invokeApply: Boolean = true) : QPromise = ???
}
