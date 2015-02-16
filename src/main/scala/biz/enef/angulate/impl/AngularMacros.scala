// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macro implementations for Angular
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.impl

import acyclic.file
import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

// TODO: remove
protected[angulate] class AngularMacros(val c: Context) extends MacroBase {
  import c.universe._

  private def printCode(tree: Tree) = c.info( c.enclosingPosition, showCode(tree), true )

  val angular = q"scala.scalajs.js.Dynamic.global.angular.asInstanceOf[biz.enef.angulate.Angular]"
/*
  def module(name: c.Expr[String], requires: c.Expr[Iterable[String]]) = {
    // create js.Array from Iterable with dependencies
    val dependencies = q"new scala.scalajs.js.JSConverters.JSRichGenTraversableOnce($requires).toJSArray"
    // create call to Angular.module()
    val tree = q"$angular.module($name,$dependencies)"
    //printCode(tree)
    tree
  }
*/
}
