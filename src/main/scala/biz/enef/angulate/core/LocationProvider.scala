// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: API for Angular $locationProvider
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import scala.scalajs.js

@js.native
trait LocationProvider extends Provider {
  def hashPrefix() : String = js.native
  def hashPrefix(prefix: String) : LocationProvider = js.native
  def html5Mode() : js.Object = js.native
  def html5Mode(mode: Boolean) : LocationProvider = js.native
  def html5Mode(mode: js.Object) : LocationProvider = js.native
}
