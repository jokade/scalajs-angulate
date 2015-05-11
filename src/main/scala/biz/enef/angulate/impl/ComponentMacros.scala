// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macro-based implementation of the Component pattern
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.impl

import biz.enef.angulate._

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.scalajs.js

protected[angulate] class ComponentMacros(val c: Context) extends MacroBase with ControllerMacroUtils {
  import c.universe._

  /* types */
  val componentAnnotation = typeOf[Component]
  val componentDefType = typeOf[ComponentDef]

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angulate.ComponentMacros.debug" )

  def componentOf[T: c.WeakTypeTag] = {
    val ct = weakTypeOf[T]
    val module = Select(c.prefix.tree, TermName("self"))
    val cdef = analyzeComponent(ct)
    val ctrlName = ct.typeSymbol.fullName
    val defs = cdef - "selector" ++ Map(
      "restrict"     -> q""""E"""",
      "controller"   -> q"""${ctrlName}"""
    )
    //println(defs)

    // controller construction array
    val debugCtrl =
      if(runtimeLogging)
        q"""global.console.debug("Created Component controller "+${ctrlName}, ctrl.asInstanceOf[js.Dynamic], "with scope:", scope)"""
      else q"()"

    val cm = getConstructor(ct)
    val (ctrlDeps,ctrlArgs) = makeArgsList(cm)
    val ctrlDepNames = "$scope" +: getDINames(cm)
    val constructor = q"""js.Array[Any](..$ctrlDepNames,
          ((scope:js.Dynamic, ..$ctrlDeps) => {
            val ctrl = new $ct(..$ctrlArgs)
            ..${copyMembers(ct)}
            $debugCtrl
          }):js.Function)"""


    val tree =
      q"""{import scalajs.js
           import js.Dynamic.{global,literal}
           $module.directive(${cdef("selector")},() => js.Dictionary(
           ..${defs.map{ p => q"${p._1} -> ${p._2}"}}
           )).controller(${ctrlName},$constructor)
          } """

    if(logCode) printCode( tree )
    tree
  }


  private def analyzeComponent(t: c.Type) : CDef = {
    val cs = t.typeSymbol.asClass

    // parse @Component
    val defs : CDef = getComponentAnnotation(cs).headOption.map{ a =>
      a.children.head match {
        case Select(_,_) => analyzeOrderedComponentDef(a.children.tail)
        case _ => analyzeUnorderedComponentDef(a.children)
      }
    }.getOrElse{
      c.error(c.enclosingPosition,"components must be annotated with @Component")
      Map()
    }

    if(defs.contains("bind")) {
      val bindings = defs("bind")
      defs - "bind" + ("scope"->bindings)
    }
    else
      defs + ("scope"->q"js.Dictionary()")
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
      "selector"    -> defs(names(0)),
      "template"    -> defs(names(1)),
      "templateUrl" -> defs(names(2)),
      "bind"        -> defs(names(3))
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
