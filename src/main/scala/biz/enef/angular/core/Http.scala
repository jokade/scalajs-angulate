// -   Project: salajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description: API for Angular $httpProvider
//
// Adopted from https://github.com/greencatsoft/scalajs-angular .. angularjs/core/Http.scala
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.reflect.macros.whitebox
import scala.language.experimental.macros
import scala.scalajs.js
import scala.scalajs.js.UndefOr

trait HttpService extends js.Object {
  def get(url: String): HttpPromise = ???
  def get(url: String, config: HttpConfig): HttpPromise = ???
  def post(url: String): HttpPromise = ???
  def post(url: String, data: js.Any): HttpPromise = ???
  def post(url: String, data: js.Any, config: HttpConfig): HttpPromise = ???
  def jsonp(url: String, config: HttpConfig): HttpPromise = ???
  def put(url: String): HttpPromise = ???
  def put(url: String, data: js.Any): HttpPromise = ???
  def put(url: String, data: js.Any, config: HttpConfig): HttpPromise = ???
  def delete(url: String): HttpPromise = ???
  def delete(url: String, data: js.Any): HttpPromise = ???
  def delete(url: String, data: js.Any, config: HttpConfig): HttpPromise = ???
}

trait HttpConfig extends js.Object {
  var cache = false
  var responseType = ""
  var headers = new js.Array[js.Any]
  var transformResponse: js.Array[js.Function2[js.Any, js.Any, js.Any]] = _
  var transformRequest: js.Array[js.Function2[js.Any, js.Any, js.Any]] = _
}

trait HttpPromise extends js.Object {
  def success(callback: js.Function1[js.Any, Unit]): this.type
  def success(callback: js.Function2[js.Any, Int, Unit]): this.type
  def success(callback: js.Function3[js.Any, js.Any, Int, Unit]): this.type
  def success(callback: js.Function4[js.Any, Int, js.Any, js.Any, Unit]): this.type
  def success(callback: js.Function5[js.Any, Int, js.Any, js.Any, js.Any, Unit]): this.type
  def error(callback: js.Function1[js.Any, Unit]): this.type
  def error(callback: js.Function2[js.Any, Int, Unit]): this.type
  def error(callback: js.Function3[js.Any, js.Any, Int, Unit]): this.type
  def error(callback: js.Function4[js.Any, Int, js.Any, js.Any, Unit]): this.type
  def error(callback: js.Function5[js.Any, Int, js.Any, js.Any, UndefOr[String], Unit]): this.type
  def onComplete() : Unit = macro HttpMacros.onCompleteImpl
}

class HttpMacros(val c: whitebox.Context) {
  import c.universe._

  def onCompleteImpl() = q"()"
}