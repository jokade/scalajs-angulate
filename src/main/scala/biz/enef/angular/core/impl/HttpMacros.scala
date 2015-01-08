// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides the macros for enhancements to the Angular $http API
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core.impl

import biz.enef.angular.core.{HttpError, HttpPromise}
import biz.enef.angular.impl.MacroBase

import scala.reflect.macros.blackbox
import scala.scalajs.js
import scala.util.{Success, Failure}


class HttpPromiseMacros(val c: blackbox.Context) extends MacroBase {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angular.HttpPromiseMacros.debug" )

  lazy val tHttpError = typeOf[HttpError]
  lazy val tFailure = q"scala.util.Failure"
  lazy val tSuccess = q"scala.util.Success"
/*
  def onSuccessWithType[T: c.WeakTypeTag](callback: c.Tree) = {
    val ft = weakTypeOf[T]
    val q"(..$params) => $body" = callback
    val tree = q"${c.prefix}.success( ($callback:js.Function).asInstanceOf[Function1[Any,Unit]] )"
    //val tree = q"${c.prefix}.success( ((..$params) => $body).asInstanceOf[js.Function1[Any,Unit]] )"

    if(logCode) printCode(tree)
    tree
  }

  def onSuccess(pf: c.Tree) = {
    // TODO: is there a better way to handle the PartialFunction? maybe we can replace it?
    val q"{case ..$cases}" = pf
    val tree = q"${c.prefix}.success{ x:Any => x match {case ..$cases} }"

    if(logCode) printCode(tree)
    tree
  }*/

  def onSuccess(f: c.Tree) = {
    val tree = q"${c.prefix}.success( $f )"

    if(logCode) printCode(tree)
    tree
  }

  def onComplete(f: c.Tree) = {
    val tree = q"""${c.prefix}.success( ((x:Any)=>$f($tSuccess(x))):js.Function1[Any,Unit] )
                              .error( ((msg:Any,status:Int) => $f($tFailure(new $tHttpError(msg.toString,status)))):js.Function2[Any,Int,Unit] )
                """

    if(logCode) printCode(tree)
    tree
  }

  def onFailure(f: c.Tree) = {
    val tree = q"""${c.prefix}.error( (msg:Any,status:Int)=>$f(new $tHttpError(msg.toString,status)))"""

    if(logCode) printCode(tree)
    tree
  }

}

