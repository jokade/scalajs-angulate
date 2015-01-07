// -   Project: scalajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description: Macro implementations for Module
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import biz.enef.angular.ScopeController

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

protected[angular] class ModuleImpl(val c: Context) {
  import c.universe._

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
    createScopeController(controllerType,name.tree)
  }



  private def createScopeController(controllerType: Type, name: Tree) = {
    val module = c.prefix
    val ctrlDef = createControllerDefinition(controllerType)
    val constructor =
      q"""js.Array[Any]("$$scope",
          ((scope:js.Dynamic) => {$ctrlDef;new Ctrl(scope)}):js.Function)"""

    val tree =
      q"""{import scala.scalajs.js
           import js.Dynamic.{global,literal}
           $module.controller($name,$constructor)
           module}"""
    //printCode( tree )
    tree
  }

  private def createController(controllerType: Type, name: Tree) = {
    val module = c.prefix
    val ctrlDef = createControllerDefinition(controllerType)
    val constructor =
      q"""js.Array[Any]("$$scope",
          ((scope:js.Dynamic,parentScope:js.Dynamic) => {
            $ctrlDef
            val ctrl = new Ctrl(parentScope)
            ..${copyMembers(controllerType)}
            global.ctrl = ctrl.asInstanceOf[js.Dynamic]
            global.scope = scope
          }):js.ThisFunction)"""


    val tree =
      q"""{import scala.scalajs.js
           import js.Dynamic.{global,literal}
           $module.controller($name,$constructor)
           module}"""
    printCode( tree )
    tree
  }

  private def createControllerDefinition(controllerType: Type) = {
    copyMembers(controllerType)
    val tree = q"""class Ctrl(override val dynamicScope: js.Dynamic) extends $controllerType"""
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
