// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import scala.scalajs.js

@js.native
trait Injector extends js.Object with ProvidedService {
  def get[T <: js.Any](name: String) : T = js.native
  def invoke(fn: js.Function, self: js.Object = null, locals: js.Object = null) : Unit = js.native
}
