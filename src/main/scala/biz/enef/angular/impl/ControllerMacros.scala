// -   Project: scalajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description: Macro-based enhancements for Angular controllers, etc
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import biz.enef.angular.ScopeController

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

protected[angular] class ControllerMacros(val c: Context) {
  import c.universe._

  // include runtime log messages if true
  lazy val runtimeLogging = c.settings.exists( _ == "biz.enef.angular.runtimeLogging" )

  private def printCode(tree: Tree) = c.info( c.enclosingPosition, showCode(tree), true )

  /* type definitions */
  val scopeController = typeOf[ScopeController[_]]


  def controllerOf[T: c.WeakTypeTag] = {
    val controllerType = weakTypeOf[T]
    val name = controllerType.toString
    if( controllerType <:< scopeController)
      createScopeController(controllerType, q"$name")
    else
      createController(controllerType, q"$name")
  }

  def controllerOfWithName[T: c.WeakTypeTag](name: c.Expr[String]) = {
    val controllerType = weakTypeOf[T]
    if( controllerType <:< scopeController)
      createScopeController(controllerType, q"$name")
    else
      createController(controllerType, q"name")
  }



  private def createScopeController(ct: Type, name: Tree) = {
    val module = c.prefix
    val ctrlDef = createControllerProxy(ct)

    val debug =
      if(runtimeLogging)
        q"""global.console.debug("Created ScopeController "+$name, ctrl.asInstanceOf[js.Dynamic])"""
      else q"()"

    val constructor =
      q"""js.Array[Any]("$$scope",
          ((scope:js.Dynamic) => {$ctrlDef;val ctrl = new Ctrl(scope);$debug;ctrl}):js.Function)"""

    val tree =
      q"""{import scala.scalajs.js
           import js.Dynamic.{global,literal}
           $module.controller($name,$constructor)
           module}"""
    //printCode( tree )
    tree
  }

  private def createController(ct: Type, name: Tree) = {
    val module = c.prefix
    val ctrlDef = createControllerProxy(ct)

    // print debug information at runtime if runtimeLogging==true

    val debug =
      if(runtimeLogging)
        q"""global.console.debug("Created Controller "+$name, ctrl.asInstanceOf[js.Dynamic], "with scope:", scope)"""
      else q"()"

    val constructor =
      q"""js.Array[Any]("$$scope",
          ((scope:js.Dynamic,parentScope:js.Dynamic) => {
            $ctrlDef
            val ctrl = new Ctrl(parentScope)
            ..${copyMembers(ct)}
            $debug
          }):js.ThisFunction)"""


    val tree =
      q"""{import scala.scalajs.js
           import js.Dynamic.{global,literal}
           $module.controller($name,$constructor)
           }"""
    printCode( tree )
    tree
  }

  private def createControllerProxy(ct: Type) = {
    copyMembers(ct)
    val tree = q"""class Ctrl(override val dynamicScope: js.Dynamic) extends $ct"""
    tree
  }

  private def copyMembers(ct: Type) = {
    val props = ct.decls.filter( p => p.isPublic && p.isMethod && !p.isConstructor).map( _.asMethod )
    val funcs = props.filter( p => !(p.isGetter||p.isSetter) )
    val getters = props.filter(_.isGetter)
    val setters = props.filter(_.isSetter).map{ s=>
      val name = s.name.toString
      val getterName = name.substring(0,name.length-4)
      getterName -> s
    }.toMap

    (getters map { getter =>
      val getterName = getter.name.toString
      val setterOption = setters.get(getterName).map{ setter =>
        val setterType = setter.paramLists.head.head.typeSignature
        q"""global.Object.defineProperty(scope,$getterName,
              literal(get = ()=>ctrl.$getter,
                      set = (v:$setterType) => ctrl.$getter = v))"""
      }
      setterOption.getOrElse {
        q"""global.Object.defineProperty(scope,$getterName,literal(get = ()=>ctrl.$getter))"""
      }
    }) ++
    (funcs map { func =>
      val funcName = func.name.toString
      val (params,args) = makeArgsList(func)
      q"""global.Object.defineProperty(scope,$funcName,literal(value = (..$params) => ctrl.$func(..$args)))"""
    })
  }

  private def makeArgsList(f: MethodSymbol) = {
    f.paramLists.head.map( p => {
      val name = TermName(c.freshName("x"))
      (q"$name: ${p.typeSignature}", q"$name")
    }).unzip
  }

}
