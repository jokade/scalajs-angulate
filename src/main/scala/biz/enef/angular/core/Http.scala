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
  def get[T](url: String): HttpFuture[T] = ???
  def get[T](url: String, config: HttpConfig): HttpFuture[T] = ???
  def post[T](url: String): HttpFuture[T] = ???
  def post[T](url: String, data: js.Any): HttpFuture[T] = ???
  def post[T](url: String, data: js.Any, config: HttpConfig): HttpFuture[T] = ???
  def jsonp[T](url: String, config: HttpConfig): HttpFuture[T] = ???
  def put[T](url: String): HttpFuture[T] = ???
  def put[T](url: String, data: js.Any): HttpFuture[T] = ???
  def put[T](url: String, data: js.Any, config: HttpConfig): HttpFuture[T] = ???
  def delete[T](url: String): HttpFuture[T] = ???
  def delete[T](url: String, data: js.Any): HttpFuture[T] = ???
  def delete[T](url: String, data: js.Any, config: HttpConfig): HttpFuture[T] = ???
}

trait HttpConfig extends js.Object {
  var cache = false
  var responseType = ""
  var headers = new js.Array[js.Any]
  var transformResponse: js.Array[js.Function2[js.Any, js.Any, js.Any]] = _
  var transformRequest: js.Array[js.Function2[js.Any, js.Any, js.Any]] = _
}

trait HttpFuture[T] extends js.Object {
  def success(callback: js.Function): this.type
  def success(callback: js.Function1[js.Any, Unit]): this.type
  def success(callback: js.Function2[js.Any, Int, Unit]): this.type
  def success(callback: js.Function3[js.Any, js.Any, Int, Unit]): this.type
  def success(callback: js.Function4[js.Any, Int, js.Any, js.Any, Unit]): this.type
  def success(callback: js.Function5[js.Any, Int, js.Any, js.Any, js.Any, Unit]): this.type
  def error(callback: js.Function): this.type
  def error(callback: js.Function1[js.Any, Unit]): this.type
  def error(callback: js.Function2[js.Any, Int, Unit]): this.type
  def error(callback: js.Function3[js.Any, js.Any, Int, Unit]): this.type
  def error(callback: js.Function4[js.Any, Int, js.Any, js.Any, Unit]): this.type
  def error(callback: js.Function5[js.Any, Int, js.Any, js.Any, UndefOr[String], Unit]): this.type

  var `then`: js.Function3[js.Function5[T,js.Any,js.Any,js.Any,js.Any,Unit],js.Function,js.Function,Unit] = ???

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

  def onSuccess(f: T=>Unit) : this.type = macro impl.HttpPromiseMacros.onSuccess

  def onComplete(f: (Try[T])=>Unit) : this.type = macro impl.HttpPromiseMacros.onComplete

  def onFailure(f: (HttpError)=>Unit) : this.type = macro impl.HttpPromiseMacros.onFailure

  def map[U](f: T=>U) : HttpFuture[U] = macro impl.HttpPromiseMacros.map

  /**
   * Returns a Scala Future for this HttpFuture.
   *
   * @note This is a scalajs-angulate enhancement.
   */
  def future: Future[T] = macro impl.HttpPromiseMacros.future
}

class HttpError(msg: String, val status: Int) extends RuntimeException(msg)

