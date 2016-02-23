// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the bindings to Angular promises and $q service
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import scala.scalajs.js

trait QPromise extends js.Object {

  def `then`(successCallback: js.Function1[js.Any,_]) : QPromise = js.native

  def `then`(successCallback: js.Function1[js.Any,_],
             errorCallback: js.Function1[js.Any,_]) : QPromise = js.native

  def `then`(successCallback: js.Function1[js.Any,_],
             errorCallback: js.Function1[js.Any,_],
             notifyCallback: js.Function1[js.Any,_]) : QPromise = js.native

  def `catch`(errorCallback: js.Function1[js.Any,_]) : QPromise = js.native

  def `finally`(callback: js.Function1[js.Any,_]) : QPromise = js.native
}

object QPromise {
  implicit class RichQPromise(val p: QPromise) extends AnyVal {
    def onSuccess(f: (js.Any)=>Unit) = p.`then`(f:js.Function1[js.Any,Unit])
  }
}

trait QService extends ProvidedService {
  def reject(reason: js.Any) : QPromise = js.native
  def defer() : js.Dynamic = js.native
  def when(value: js.Any) : QPromise = js.native
  def all(promises: js.Array[QPromise]) : QPromise = js.native
}