// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macro-based implementation of the Component pattern
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import biz.enef.angular.{ComponentDef, Component}

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

protected[angular] class ComponentMacros(val c: Context) extends MacroBase {
  import c.universe._

  /* types */
  val componentAnnotation = typeOf[Component]
  val componentDefType = typeOf[ComponentDef]

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angular.ComponentMacros.debug" )

  def componentOf[T: c.WeakTypeTag] = {
    val ct = weakTypeOf[T]
    val module = Select(c.prefix.tree, TermName("self"))
    val cdef = analyzeComponent(ct)
    val ctrlName = ct.typeSymbol.fullName
    val defs = cdef - "selector" ++ Map(
      "controller"   -> q"""${ctrlName}""",
      "controllerAs" -> q""""ctrl""""
    )

    val tree =
      q"""{import scalajs.js
           $module.directive(${cdef("selector")},() => js.Dictionary(
           ..${defs.map{ p => q"${p._1} -> ${p._2}"}}
           )).controller(${ctrlName},()=>js.Object())
          } """

    if(logCode) printCode( tree )
    tree
  }


  private def analyzeComponent(t: c.Type) : CDef = {
    val cs = t.typeSymbol.asClass

    // parse @Component
    getComponentAnnotation(cs).headOption.map{ a =>
      println(a.children)
      a.children.head match {
        case Select(_,_) => analyzeOrderedComponentDef(a.children.tail)
        case _ => analyzeUnorderedComponentDef(a.children)
      }
    }.getOrElse{
      c.error(c.enclosingPosition,"components must be annotated with @Component")
      Map()
    }
  }

  // analyze ComponentDef with out-of-order parameters
  private def analyzeUnorderedComponentDef(children: List[Tree]) : CDef = {
    val defs = children.init.map { v =>
      val q"${_} val $id: ${_} = $rhs" = v
      (id.toString, rhs)
    }.toMap[String, Tree]
    val q"${_}(..$args)" = children.last
    val names = (args: List[Tree]).map(_.toString)

    Map(
      "selector" -> defs(names(0)),
      "template" -> defs(names(1))
    )
  }

  // analyze ComponentDef with all parameters specified in the defined order
  private def analyzeOrderedComponentDef(children: List[Tree]) : CDef = {
    val names = List("template")
    val defs = names.zip(children.tail).filter( _._2.children.isEmpty ).toMap
    defs +  ("selector" -> children.head)
  }

  private def getComponentAnnotation(ts: ClassSymbol) = {
    ts.annotations.filter( _.tree.tpe =:= componentAnnotation ).
    map( _.tree.children(1) )
  }

  type CDef = Map[String,Tree]
}
