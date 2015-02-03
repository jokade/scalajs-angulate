// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macro-based implementation of the Component pattern
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import biz.enef.angular.Component

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

protected[angular] class ComponentMacros(val c: Context) extends MacroBase {
  import c.universe._

  /* types */
  val componentAnnotation = typeOf[Component]

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angular.ComponentMacros.debug" )

  def componentOf[T: c.WeakTypeTag] = {
    val module = Select(c.prefix.tree, TermName("self"))
    val cdef = analyzeComponent(weakTypeOf[T])

    val tree =
      q"""$module.directive(${cdef.selector}, () => ())

       """

    if(logCode) printCode( tree )
    tree
  }


  private def analyzeComponent(t: c.Type) : ComponentDef = {
    val cs = t.typeSymbol.asClass

    val cdef = ComponentDef()

    // parse @Component
    getComponentAnnotation(cs) map { annot =>
      println(annot)
    }

    ComponentDef("hello")
  }

  private case class ComponentDef(selector: String = null)

  private def getComponentAnnotation(ts: ClassSymbol) = {
    ts.annotations.filter( _.tree.tpe =:= componentAnnotation ).
    map( _.tree.children(1) ).headOption
  }
}
