// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: API for module ngCookies
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.ext

import biz.enef.angulate.core.ProvidedService

import scala.scalajs.js

/**
 * API for the Angular \$cookie service (Angular 1.4+).
 *
 * @note The `ngCookies` module needs to be loaded for this service to be available.
 * @see [[https://docs.angularjs.org/api/ngCookies/service/\$cookies]]
 */
trait CookiesService extends ProvidedService {
  def get(key: String) : String = js.native
  def getObject[T<:js.Object](key: String) : T = js.native
  def getAll(): js.Dictionary[js.Any] = js.native
  def put(key: String, value: String) : Unit = js.native
  def put(key: String, value: String, options: js.Object) : Unit = js.native
  def putObject(key: String, value: js.Object) : Unit = js.native
  def putObject(key: String, value: js.Object, options: js.Object) : Unit = js.native
  def remove(key: String) : Unit = js.native
  def remove(key: String, options: js.Object) : Unit = js.native

}
