// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: API for Angular $httpProvider
//
// Based on https://github.com/greencatsoft/scalajs-angular .. angularjs/core/Http.scala
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.concurrent.Future
import scala.language.experimental.macros
import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.util.Try

trait HttpService extends js.Object {
  def get[T](url: String): HttpPromise[T] = js.native
  def get[T](url: String, config: HttpConfig): HttpPromise[T] = js.native
  def post[T](url: String): HttpPromise[T] = js.native
  def post[T](url: String, data: js.Any): HttpPromise[T] = js.native
  def post[T](url: String, data: js.Any, config: HttpConfig): HttpPromise[T] = js.native
  def jsonp[T](url: String, config: HttpConfig): HttpPromise[T] = js.native
  def put[T](url: String): HttpPromise[T] = js.native
  def put[T](url: String, data: js.Any): HttpPromise[T] = js.native
  def put[T](url: String, data: js.Any, config: HttpConfig): HttpPromise[T] = js.native
  def delete[T](url: String): HttpPromise[T] = js.native
  def delete[T](url: String, data: js.Any): HttpPromise[T] = js.native
  def delete[T](url: String, data: js.Any, config: HttpConfig): HttpPromise[T] = js.native
}

trait HttpConfig extends js.Object {
  var method: String = js.native
  var url: String = js.native
  var params : js.Dictionary[js.Any] = js.native
  var data : js.Any = js.native
  var headers : js.Dictionary[js.Any] = js.native
  var xsrfHeaderName : String = js.native
  var xsrfCookieName : String = js.native
  var transformResponse: js.Array[js.Function2[js.Any, js.Any, js.Any]] = js.native
  var transformRequest: js.Array[js.Function2[js.Any, js.Any, js.Any]] = js.native
  var cache: js.Any = js.native
  var withCredentials: Boolean = js.native
  var timeout: js.Any = js.native
  var responseType : String = js.native
}

object HttpConfig {
  import js.Dynamic.literal

  def apply[A](params: (String,A)*) : HttpConfig = literal(params= js.Dictionary(params:_*)).asInstanceOf[HttpConfig]

  def apply(method: String = null,
            url: String = null,
            params: js.Dictionary[js.Any] = null,
            data: js.Any = null,
            headers: js.Dictionary[js.Any] = null,
            xsrfHeaderName : String = null,
            xsrfCookieName : String = null,
            transformResponse: js.Array[js.Function2[js.Any, js.Any, js.Any]] = null,
            transformRequest: js.Array[js.Function2[js.Any, js.Any, js.Any]] = null,
            cache: js.Any = null,
            withCredentials: Boolean = false,
            timeout: js.Any = null,
            responseType : String = null) : HttpConfig = {
    literal(method = method, url=url, params=params, data=data, headers=headers,
            xsrfCookieName= xsrfCookieName, xsrfHeaderName= xsrfHeaderName,
            transformRequest=transformRequest, transformResponse=transformResponse,
            cache=cache, withCredentials=withCredentials, timeout=timeout,
            responseType=responseType).asInstanceOf[HttpConfig]
  }

}

trait HttpPromise[T] extends js.Object {
  def success(callback: js.Function): HttpPromise[T] = js.native

  def success(callback: js.Function1[js.Any, Unit]): this.type = js.native

  def success(callback: js.Function2[js.Any, Int, Unit]): this.type = js.native

  def success(callback: js.Function3[js.Any, js.Any, Int, Unit]): this.type = js.native

  def success(callback: js.Function4[js.Any, Int, js.Any, js.Any, Unit]): this.type = js.native

  def success(callback: js.Function5[js.Any, Int, js.Any, js.Any, js.Any, Unit]): this.type = js.native

  def error(callback: js.Function): HttpPromise[T] = js.native

  def error(callback: js.Function1[js.Any, Unit]): this.type = js.native

  def error(callback: js.Function2[js.Any, Int, Unit]): this.type = js.native

  def error(callback: js.Function3[js.Any, js.Any, Int, Unit]): this.type = js.native

  def error(callback: js.Function4[js.Any, Int, js.Any, js.Any, Unit]): this.type = js.native

  def error(callback: js.Function5[js.Any, Int, js.Any, js.Any, UndefOr[String], Unit]): this.type = js.native

  var `then`: js.Function3[js.Function, js.Function, js.Function, HttpPromise[T]] = js.native
  //var `then`: js.Function3[js.Function1[T,Unit],js.Function,js.Function,Unit] = js.native
}

object HttpPromise {
  //------------------------- ANGULATE ENHANCEMENTS --------------------------
  final implicit class RichHttpPromise[T](self: HttpPromise[T]) {
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

    def onSuccess(f: T => Unit): HttpPromise[T] = macro impl.HttpPromiseMacros.onSuccess

    def onComplete(f: Try[T] => Unit): HttpPromise[T] = macro impl.HttpPromiseMacros.onComplete

    def onFailure(f: (HttpError) => Unit): HttpPromise[T] = macro impl.HttpPromiseMacros.onFailure

    def map[U](f: T => U): HttpPromise[U] = macro impl.HttpPromiseMacros.map

    /**
     * Returns a Scala Future for this HttpPromise.
     *
     * @note This is a scalajs-angulate enhancement.
     */
    def future: Future[T] = macro impl.HttpPromiseMacros.future
  }
}

class HttpError(msg: String, val status: Int) extends RuntimeException(msg)


