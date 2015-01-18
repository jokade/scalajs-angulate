// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the bindings to Angular promises and $q service
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.scalajs.js

trait QPromise extends js.Object {

  def `then`(successCallback: js.Function1[js.Any,js.Any]) : QPromise = ???

  def `then`(successCallback: js.Function1[js.Any,js.Any],
             errorCallback: js.Function1[js.Any,Unit]) : QPromise = ???

  def `then`(successCallback: js.Function1[js.Any,js.Any],
             errorCallback: js.Function1[js.Any,Unit],
             notifyCallback: js.Function1[js.Any,Unit]) : QPromise = ???

  def `catch`(errorCallback: js.Function1[js.Any,Unit]) : QPromise = ???

  def `finally`(callback: js.Function1[js.Any,Unit]) : QPromise = ???
}
