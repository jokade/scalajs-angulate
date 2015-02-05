// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macro implementations for Angular
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import acyclic.file
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

protected[angular] class AngularImpl(val c: Context) {
  import c.universe._

  private def printCode(tree: Tree) = c.info( c.enclosingPosition, showCode(tree), true )

  val angular = q"scala.scalajs.js.Dynamic.global.angular.asInstanceOf[biz.enef.angular.Angular]"

  def apply() = angular

  def module(name: c.Expr[String], requires: c.Expr[Iterable[String]]) = {
    // create js.Array from Iterable with dependies
    val dependencies = q"new scala.scalajs.js.JSConverters.JSRichGenTraversableOnce($requires).toJSArray"
    // create call to Angular.module()
    val tree = q"$angular.module($name,$dependencies)"
    //printCode(tree)
    tree
  }
}
