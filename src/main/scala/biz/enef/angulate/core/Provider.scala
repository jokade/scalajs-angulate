package biz.enef.angulate.core

import scala.scalajs.js

/**
 * Marks a class that can be used as a AngularJS provider
 */
trait Provider extends js.Any

/**
 * All service providers extend this interface
 */
trait ServiceProvider extends js.Object with Provider {
  def $get: js.Any = js.native
}

/**
 * Marks a service provided by Angular
 */
trait ProvidedService extends js.Any
