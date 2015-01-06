// -   Project: scalajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description: Macro implementations for Module
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import biz.enef.angular.Module
import biz.enef.angular.Module.RichModule

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.scalajs.js

protected[angular] class ModuleImpl(val c: Context) {
  import c.universe._

  private def printCode(tree: Tree) = c.info( c.enclosingPosition, showCode(tree), true )

  /* type definitions */
  val jsDynamic  = typeOf[js.Dynamic]
  val jsFunction = typeOf[js.Function]

  def controllerOf[T: c.WeakTypeTag] = {
    val controllerType = weakTypeOf[T]
    val name = controllerType.toString
    createController(controllerType, q"$name")
  }

  def controllerOfWithName[T: c.WeakTypeTag](name: c.Expr[String]) = {
    val controllerType = weakTypeOf[T]
    createController(controllerType,name.tree)
  }

  private def createController(controllerType: Type, name: Tree) = {
    val module = c.prefix
    // extend controller class
    val extd =
      q"""class Ctrl(override val dynamicScope: $jsDynamic) extends $controllerType {

          }"""
    val constructor =
      q"""scala.scalajs.js.Array[Any]("$$scope",
          ((scope:$jsDynamic) => {$extd;new Ctrl(scope)}):$jsFunction)"""

    val tree = q"$module.controller($name,$constructor);module"
    printCode( tree )
    tree
  }

  private def createConstructor(fn: Tree) = q"js.Array[Any]( $fn )"

}
