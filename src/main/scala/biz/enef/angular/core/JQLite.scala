// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import org.scalajs.dom.Element

import scala.scalajs.js

trait JQLite extends js.Array[Element] {

  def on(events: String, handler: js.Function) : JQLite = ???

  def off(events: String, handler: js.Function = null) : JQLite = ???
}
