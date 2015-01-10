// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: API for Angular $locationProvider
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.scalajs.js

/**
 * Defines the bindings to the $$location service.
 *
 * @see [[https://docs.angularjs.org/api/ng/service/$location]]
 */
trait Location {

  def absUrl(): String = ???
  def url(url: String = null, replace: String = null): String = ???
  def protocol(): String = ???
  def host(): String = ???
  def port(): Int = ???
  def path(): String = ???
  def path(path: String): Location = ???
  def search(search: js.Any, paramValue: js.Any = null) : js.Object = ???
  def hash(hash: String = null): String = ???
  def replace(): Unit = ???
}
