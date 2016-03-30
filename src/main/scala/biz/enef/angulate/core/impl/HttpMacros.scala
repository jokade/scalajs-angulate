// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the macros for enhancements to the Angular $http API
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core.impl

// don't import acyclic.file, there is a known circular dependency with core.Http
import biz.enef.angulate.core.{HttpError, HttpPromise}
import biz.enef.angulate.impl.MacroBase

import scala.Predef
import scala.concurrent.duration.Duration
import scala.concurrent._
import scala.reflect.macros.whitebox
import scala.scalajs.js
import scala.scalajs.js._
import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import scala.util.{Try, Success, Failure}


protected[angulate] class HttpPromiseMacros(val c: whitebox.Context) extends MacroBase {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angulate.HttpPromiseMacros.debug" )

  lazy val tHttpError = typeOf[HttpError]
  lazy val tFailure = q"scala.util.Failure"
  lazy val tSuccess = q"scala.util.Success"
  lazy val tAny = q"scalajs.js.Any"

  def onSuccess(f: c.Tree) = {
    val T = f.tpe.typeArgs.head
    val self = Select(c.prefix.tree, TermName("self"))
    val tree = q"""{import scalajs.js
                    val fun = $f
                   $self.success( (x: js.UndefOr[$T]) => fun(x.getOrElse(null)) )}"""

    if(logCode) printCode(tree)
    tree
  }

  def onComplete(f: c.Tree) = {
    val T = f.tpe.typeArgs.head.typeArgs.head

    val self = Select(c.prefix.tree, TermName("self"))

    val tree = q"""{import scalajs.js
                    val fun = $f
                    $self.success( ((x:js.UndefOr[$T]) =>fun($tSuccess(x.getOrElse(null)))) ).
                                error( (msg:Any,status:Int)=>fun($tFailure(new $tHttpError(if(msg==null) "" else msg.toString,status))) )
                   }"""
    if(logCode) printCode(tree)
    tree
  }

  def onFailure(f: c.Tree) = {
    val self = Select(c.prefix.tree, TermName("self"))
    val tree =
      q"""{import scalajs.js
           $self.error( (msg:Any,status:Int)=>$f(new $tHttpError(if(msg==null) "" else msg.toString,status)))
           }"""

    if(logCode) printCode(tree)
    tree
  }

  def map(f: c.Tree) = {
    val T = f.tpe.typeArgs(1)
    val self = Select(c.prefix.tree, TermName("self"))
    val tree = q"""{import biz.enef.angulate.core
                    val mapped = new core.impl.MappedHttpPromise($self,$f)
                   mapped.asInstanceOf[core.HttpPromise[$T]]
                   }
                   """

    if(logCode) printCode(tree)
    tree
  }

  def future = {
    val tree = q"""(new biz.enef.angulate.core.impl.HttpPromiseWrapper(${c.prefix}))"""

    if(logCode) printCode(tree)
    tree
  }

}


object HttpMacroUtils {

  def mapHttpPromise[T,U](p: HttpPromise[T], f: T=>U) : HttpPromise[U] = {
    val mapped = js.Object.create(p).asInstanceOf[HttpPromise[U]]

    mapped.`then` = (success:js.Function,error:js.Function,notify:js.Function) => {
      val mappedSuccess = ((data: T, status: Any, headers: Any, config: Any, statusText: Any) =>
        success.asInstanceOf[js.Function5[U,js.Any,js.Any,js.Any,js.Any,Unit]].apply(f(data),status,headers,config,statusText)):js.Function5[T,Any,Any,Any,Any,Unit] //.asInstanceOf[Callback[T]]
      p.`then`(mappedSuccess,error,notify)
      this.asInstanceOf[HttpPromise[U]]
    }
    mapped
  }

}

// TODO: use scalajs.js.Promise instead of scla.concurrent.Promise
class HttpPromiseWrapper[T](wrapped: HttpPromise[T]) extends Future[T] {
  private val _promise = scala.concurrent.Promise[T]()
  wrapped.success( ((t:T)=> _promise.success(t)) )

  override def onComplete[U](f: (Try[T]) => U)(implicit executor: ExecutionContext): Unit = future.onComplete(f)

  override def isCompleted: Boolean = _promise.isCompleted

  override def value: Option[Try[T]] = future.value

  lazy val future = _promise.future

  override def result(atMost: Duration)(implicit permit: CanAwait): T = future.result(atMost)

  override def ready(atMost: Duration)(implicit permit: CanAwait): this.type = {future.ready(atMost);this}
}

@JSExportAll
class MappedHttpPromise[T,U](wrapped: HttpPromise[T], f: T=>U) {
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
