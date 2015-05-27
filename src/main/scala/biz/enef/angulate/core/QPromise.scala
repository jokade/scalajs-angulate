// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the bindings to Angular promises and $q service
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import scala.scalajs.js

trait QPromise extends js.Object {

  def `then`(successCallback: js.Function1[js.Any,js.Any]) : QPromise = js.native

  def `then`(successCallback: js.Function1[js.Any,js.Any],
             errorCallback: js.Function1[js.Any,Unit]) : QPromise = js.native

  def `then`(successCallback: js.Function1[js.Any,js.Any],
             errorCallback: js.Function1[js.Any,Unit],
             notifyCallback: js.Function1[js.Any,Unit]) : QPromise = js.native

  def `catch`(errorCallback: js.Function1[js.Any,Unit]) : QPromise = js.native

  def `finally`(callback: js.Function1[js.Any,Unit]) : QPromise = js.native
}

trait QService extends ProvidedService {
  def reject(reason: js.Any) : QPromise = js.native
  def defer() : js.Dynamic = js.native
  def when(value: js.Any) : QPromise = js.native
  def all(promises: js.Array[QPromise]) : QPromise = js.native
}