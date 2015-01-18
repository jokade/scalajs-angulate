// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: API for AngularJS attributes objectes (provided to link/compile functions)
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSBracketAccess

trait Attributes extends js.Object {

  @JSBracketAccess
  def apply(name: String) : UndefOr[String] = ???

  @JSBracketAccess
  def update(name: String, value: String) : Unit = ???

}
