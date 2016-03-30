// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Bindings for the Angular \$resource API
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.ext

import biz.enef.angulate.core.{HttpConfig, ProvidedService}

import scala.scalajs.js

/**
 * Defines the bindings to the ngResource.\$resource service.
 *
 * @see [[https://docs.angularjs.org/api/ngResource/service/\$resource]]
 */
@js.native
trait ResourceService extends ProvidedService {

  def apply(url: String) : Resource = js.native
  def apply(url: String, paramDefaults: js.Object) : Resource = js.native
  def apply(url: String, paramDefaults: js.Object, actions: js.Dictionary[HttpConfig]) : Resource = js.native
  def apply(url: String, paramDefaults: js.Object, actions: js.Dictionary[HttpConfig], options: js.Object) : Resource = js.native
}


@js.native
trait Resource extends js.Object {
  def get: HttpConfig = js.native
  def save: HttpConfig = js.native
  def query: HttpConfig = js.native
  def remove: HttpConfig = js.native
  def delete: HttpConfig = js.native
}

