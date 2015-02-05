// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import org.scalajs.dom.Element

import scala.scalajs.js

trait JQLite extends js.Array[Element] {

  def on(events: String, handler: js.Function) : JQLite = js.native

  def off(events: String, handler: js.Function = null) : JQLite = js.native

  def triggerHandler(eventType: String) : JQLite = js.native
}
