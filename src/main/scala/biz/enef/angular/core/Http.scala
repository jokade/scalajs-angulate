// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: API for Angular $httpProvider
//
// Based on https://github.com/greencatsoft/scalajs-angular .. angularjs/core/Http.scala
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, CanAwait, TimeoutException, Future}
import scala.reflect.macros.whitebox
import scala.language.experimental.macros
import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.util.Try

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

  //------------------------- ANGULATE ENHANCEMENTS --------------------------
  /**
   * The provided callback will be called when a successful response is available
   * (response status codes between 200 and 299 are considered a success status).
   *
   * @note This is a scalajs-angulate enhancement. The response data provided to
   *       the callback will be cast to the specified type T.
   *
   * @param callback Callback function that will receive the response data as its first argument
   * @tparam T Type of the response data object
   */
  //def success[T](callback: (T) => Unit) : this.type = macro impl.HttpPromiseMacros.onSuccessWithType[T]

  //def onSuccess(pf: PartialFunction[Any,Unit]) : Unit = macro impl.HttpPromiseMacros.onSuccess

  def onSuccess(f: (Any)=>Unit) : this.type = macro impl.HttpPromiseMacros.onSuccess

  def onComplete(f: (Try[Any])=>Unit) : this.type = macro impl.HttpPromiseMacros.onComplete

  def onFailure(f: (HttpError)=>Unit) : this.type = macro impl.HttpPromiseMacros.onFailure
}

class HttpError(msg: String, val status: Int) extends RuntimeException(msg)


