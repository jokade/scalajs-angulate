package biz.enef.angulate.core

import scala.scalajs.js

/**
 * Marks a class that can be used as a AngularJS provider
 */
@js.native
trait Provider extends js.Any

/**
 * All service providers extend this interface
 */
@js.native
trait ServiceProvider extends js.Object with Provider {
  def $get: js.Any = js.native
}

/**
 * Marks a service provided by Angular
 */
@js.native
trait ProvidedService extends js.Any
