// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the bindings to Angular promises and $q service
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import scala.scalajs.js

trait QPromise[A <: js.Object] extends js.Object {

  def `then`(successCallback: js.Function1[A,js.Any]) : QPromise[A] = js.native

  def `then`(successCallback: js.Function1[A,js.Any],
             errorCallback: js.Function1[js.Any,Unit]) : QPromise[A] = js.native

  def `then`(successCallback: js.Function1[A,js.Any],
             errorCallback: js.Function1[js.Any,Unit],
             notifyCallback: js.Function1[js.Any,Unit]) : QPromise[A] = js.native

  def `catch`(errorCallback: js.Function1[js.Any,Unit]) : QPromise[A] = js.native

  def `finally`(callback: js.Function1[js.Any,Unit]) : QPromise[A] = js.native
}

trait QService extends ProvidedService {
  def reject[A <: js.Object](reason: js.Any) : QPromise[A] = js.native
  def defer() : js.Dynamic = js.native
  def when[A <: js.Object](value: A) : QPromise[A] = js.native
  def all[A <: js.Object](promises: js.Array[QPromise[A]]) : QPromise[A] = js.native
}
