// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the macros for enhancements to the Angular $http API
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core.impl

import biz.enef.angular.core.{HttpError, HttpFuture}
import biz.enef.angular.impl.MacroBase

import scala.Predef
import scala.concurrent.duration.Duration
import scala.concurrent._
import scala.reflect.macros.blackbox
import scala.scalajs.js
import scala.scalajs.js._
import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import scala.util.{Try, Success, Failure}


protected[angular] class HttpPromiseMacros(val c: blackbox.Context) extends MacroBase {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angular.HttpPromiseMacros.debug" )

  lazy val tHttpError = typeOf[HttpError]
  lazy val tFailure = q"scala.util.Failure"
  lazy val tSuccess = q"scala.util.Success"

  def onSuccess(f: c.Tree) = {
    val tree = q"${c.prefix}.success( ($f):js.Function )"

    if(logCode) printCode(tree)
    tree
  }

  def onComplete(f: c.Tree) = {
    val tree = q"""${c.prefix}.then( ((x:Any)=>$f($tSuccess(x))):js.Function1[Any,Unit],
                                     ((msg:Any,status:Int) => $f($tFailure(new $tHttpError(msg.toString,status)))):js.Function2[Any,Int,Unit] )
                """

    if(logCode) printCode(tree)
    tree
  }

  def onFailure(f: c.Tree) = {
    val tree = q"""${c.prefix}.error( (msg:Any,status:Int)=>$f(new $tHttpError(msg.toString,status)))"""

    if(logCode) printCode(tree)
    tree
  }

  def map(f: c.Tree) = {
    val T = f.tpe.typeArgs(1)
    val tree = q"""{import biz.enef.angular.core
                    val mapped = new core.impl.MappedHttpFuture(${c.prefix},$f)
                   mapped.asInstanceOf[core.HttpFuture[$T]]
                   }
                   """

    if(logCode) printCode(tree)
    tree
  }

}


object HttpMacroUtils {

  def mapHttpFuture[T,U](p: HttpFuture[T], f: T=>U) : HttpFuture[U] = {
    val mapped = js.Object.create(p).asInstanceOf[HttpFuture[U]]

    mapped.`then` = (success:js.Function5[U,Any,Any,Any,Any,Unit],error:js.Function,notify:js.Function) => {
      val mappedSuccess = ((data: T, status: Any, headers: Any, config: Any, statusText: Any) =>
        success(f(data),status,headers,config,statusText)):js.Function5[T,Any,Any,Any,Any,Unit] //.asInstanceOf[Callback[T]]
      p.`then`(mappedSuccess,error,notify)
    }
    mapped
  }

}

class HttpFutureWrapper[T](wrapped: HttpFuture[T]) extends Future[T] {
  private val _promise = Promise[T]()
  wrapped.success( ((t:T)=> _promise.success(t)) )

  override def onComplete[U](f: (Try[T]) => U)(implicit executor: ExecutionContext): Unit = future.onComplete(f)

  override def isCompleted: Boolean = _promise.isCompleted

  override def value: Option[Try[T]] = future.value

  lazy val future = _promise.future

  override def result(atMost: Duration)(implicit permit: CanAwait): T = future.result(atMost)

  override def ready(atMost: Duration)(implicit permit: CanAwait): this.type = {future.ready(atMost);this}
}

@JSExportAll
class MappedHttpFuture[T,U](wrapped: HttpFuture[T], f: T=>U) {
  def `then`(success: js.Function5[U,Any,Any,Any,Any,Unit], error: js.Function, notify: js.Function) = {
    wrapped.`then`((data: T, a: js.Any, b: js.Any, c: js.Any, d: js.Any) => {
      success(f(data),a,b,c,d)
    }, error, notify)
    this
  }

  def success(cb: js.Function4[U,Any,Any,Any,Unit]) = {
    wrapped.success((data: T, a: js.Any, b: js.Any, c: js.Any) => {
      cb(f(data), a, b, c)
    })
    this
  }

  def error(cb: js.Function4[Any,Any,Any,Any,Unit]) = {wrapped.error(cb);this}
}
