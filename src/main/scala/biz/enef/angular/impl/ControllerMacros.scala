// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macro-based enhancements for Angular controllers
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import biz.enef.angular.{named, ScopeController}

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

// TODO: understand Scala macros and clean up this hacked mess ...
protected[angular] class ControllerMacros(val c: Context) extends MacroBase {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angular.ControllerMacros.debug" )

  // include runtime log messages if true
  private lazy val runtimeLogging = c.settings.exists( _ == "biz.enef.angular.runtimeLogging" )


  /* type definitions */
  val scopeController = typeOf[ScopeController[_]]
  val namedAnnotation = typeOf[named]


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
      createController(controllerType, q"$name")
  }



  private def createScopeController(ct: Type, name: Tree) = {
    val module = c.prefix

    // ctrlDef: definition of the controller proxy class
    // ctrlDeps: the list of dependencies required by the controller constructor
    // ctrlDepNames: list with names of the dependencies to be injected
    val (ctrlDef,ctrlDeps,ctrlDepNames) = createControllerProxy(ct)

    val debug =
      if(runtimeLogging)
        q"""global.console.debug("Created ScopeController "+$name, ctrl.asInstanceOf[js.Dynamic])"""
      else q"()"

    val constructor =
      q"""js.Array[Any]("$$scope",..$ctrlDepNames,
          ((scope:js.Dynamic, ..$ctrlDeps) => {
            $ctrlDef
            val ctrl = new CtrlProxy(scope)
            $debug
            ctrl
          }):js.Function)"""

    val tree =
      q"""{import scala.scalajs.js
           import js.Dynamic.{global,literal}
           $module.controller($name,$constructor)
           module}"""

    if(logCode) printCode( tree )
    tree
  }

  private def createController(ct: Type, name: Tree) = {
    val module = c.prefix

    // ctrlDef: definition of the controller proxy class
    // ctrlDeps: the list of dependencies required by the controller constructor
    // ctrlDepNames: list with names of the dependencies to be injected
    val (ctrlDef,ctrlDeps,ctrlDepNames) = createControllerProxy(ct)

    // print debug information at runtime if runtimeLogging==true
    val debug =
      if(runtimeLogging)
        q"""global.console.debug("Created Controller "+$name, ctrl.asInstanceOf[js.Dynamic], "with scope:", scope)"""
      else q"()"

    // AngularJS controller construction array
    val constructor =
      q"""js.Array[Any]("$$scope",..$ctrlDepNames,
          ((scope:js.Dynamic,parentScope:js.Dynamic, ..$ctrlDeps) => {
            $ctrlDef
            val ctrl = new CtrlProxy(parentScope)
            ..${copyMembers(ct)}
            $debug
          }):js.ThisFunction)"""

    // controller registration
    val tree =
      q"""{import scala.scalajs.js
           import js.Dynamic.{global,literal}
           $module.controller($name,$constructor)
          }"""

    if(logCode) printCode( tree )
    tree
  }


  private def createControllerProxy(ct: Type) = {
    val constructor = ct.decls.filter( _.isConstructor ).collect{ case m: MethodSymbol => m}.head
    //val deps = constructor.paramLists.head.map( p => p.name.toString )
    val deps = getDINames(constructor)
    val (params,args) = makeArgsList(constructor)
    val tree = q"""class CtrlProxy(override val dynamicScope: js.Dynamic) extends $ct(..$args)"""
    (tree,params,deps)
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

  private def getDINames(f: MethodSymbol) = {
    f.paramLists.head.map{ p=>
      p.annotations.find( _.tree.tpe =:= namedAnnotation ).map { a =>
        val name = a.tree.children.tail.head.toString
        // TODO: that's ludicrous... what is thr right way to unquote the string???
        name.substring(1,name.length-1)
      }.getOrElse(p.name.toString)
    }
  }

}
