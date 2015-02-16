// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import scala.scalajs.js

trait Injector extends js.Object {
  def get(name: String) : js.Any = js.native
  def invoke(fn: js.Function, self: js.Object = null, locals: js.Object = null) : Unit = js.native
}
