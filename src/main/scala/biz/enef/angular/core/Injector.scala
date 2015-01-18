// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.scalajs.js

trait Injector extends js.Object {
  def get(name: String) : js.Any = ???
  def invoke(fn: js.Function, self: js.Object = null, locals: js.Object = null) : Unit = ???
}
