// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides a base class for macros with common utility functions
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import biz.enef.angular.named

import scala.reflect.macros.blackbox


protected[angular] abstract class MacroBase {
  val c: blackbox.Context
  import c.universe._

  /* type definitions */
  val namedAnnotation = typeOf[named]


  /**
   * Print to console during compilation
   * @param tree
   * @param msg
   */
  def printCode(tree: Tree, msg: String = "") =
    c.info( c.enclosingPosition,
     s"""$msg
        |${showCode(tree)}
      """.stripMargin, true )


  def makeArgsList(f: MethodSymbol) = {
    f.paramLists.head.map( p => {
      val name = TermName(c.freshName("x"))
      (q"$name: ${p.typeSignature}", q"$name")
    }).unzip
  }

  def getDINames(f: MethodSymbol) = {
    f.paramLists.head.map{ p=>
      p.annotations.find( _.tree.tpe =:= namedAnnotation ).map { a =>
        val name = a.tree.children.tail.head.toString
        // TODO: that's ludicrous... what is thr right way to unquote the string???
        name.substring(1,name.length-1)
      }.getOrElse(p.name.toString)
    }
  }

  /**
   * Creates a AngularJS constructor array for the specified type.
   *
   * @note the returned tree requires `js.Array` to be in scope
   *
   * @param ct class type
   */
  def createDIArray(ct: Type) = {
    val m = getConstructor(ct)
    val deps = getDINames(m)
    val (params,args) = makeArgsList(m)
    q"""js.Array[Any](..$deps, ((..$params) => new $ct(..$args)):js.Function)"""
  }

  def getConstructor(ct: Type) = ct.decls.filter( _.isConstructor ).collect{ case m: MethodSymbol => m}.head
}