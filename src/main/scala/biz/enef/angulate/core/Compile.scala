// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Binding to the AngularJS $compile function
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import scala.scalajs.js

trait Compile extends js.Object {

  def apply(element: js.Any) : js.Function3[js.Any,js.Any,js.Any,js.Any] = js.native
}
