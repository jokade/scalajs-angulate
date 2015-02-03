// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macros to support using a scala class as Angular directive
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import scala.reflect.macros.blackbox

protected[angular] class DirectiveMacros(val c: blackbox.Context) extends MacroBase with ControllerMacroUtils {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angular.DirectiveMacros.debug" )

  // types
  val tNotImplemented = typeOf[NotImplementedError]


  def directiveOf[T: c.WeakTypeTag] = {
    val directiveType = weakTypeOf[T]
    val name = directiveType.toString.split('.').last.replaceFirst("Directive$","")
    createDirective(directiveType, q"${name.head.toLower+name.tail}")
  }

  def directiveOfWithName[T: c.WeakTypeTag](name: c.Tree) = {
    val directiveType = weakTypeOf[T]
    createDirective(directiveType, q"${name}")
  }

  private def createDirective(ct: c.Type, name: c.Tree) = {
    val module = Select(c.prefix.tree, TermName("self"))

    // assemble all defined directive attributes (ie 'template', 'restrict', ...)
    val atts = (getDirectiveAttributes(ct).map( a => (a.name.toString,a)) map {
      case ("isolateScope",_)  => q"scope = dimpl.isolateScope"
      case ("postLink",_)      => q"link = dimpl.postLink _"
      case (_,a) if a.isGetter => q"${a.name} = dimpl.${a.name}"
      case (_,a)               => q"${a.name} = (dimpl.${a.name} _):js.Function"
    }) ++ (
      getControllerType(ct).map( createController(_) )
      )

    // create directive definition object
    val ddo = q"""literal( ..$atts )"""

    // print debug information at runtime if runtimeLogging==true
    val debug =
      if(runtimeLogging)
        q"""global.console.debug("Created Directive "+$name, ddo)"""
      else q"()"

    // directive factory function
    val constr = ct.members.filter( _.isConstructor ).map( _.asMethod ).head
    val (params,args) = makeArgsList(constr)
    val deps = getDINames(constr)
    val carray =
      q"""js.Array[Any](..$deps, ((..$params) => {
            val dimpl = new $ct(..$args)
            val ddo = $ddo
            $debug
            ddo
          }):js.Function)
       """
    println(carray)

    val tree = q"""{import scala.scalajs.js
                    import js.Dynamic.{global,literal}
                    $module.self.directive($name,$carray)
                   }"""
    if(logCode) printCode(tree)

    tree
  }

  private def getDirectiveAttributes(ct: c.Type) =
    ct.decls.filter( m=> m.isMethod && !m.isConstructor ).map( _.asMethod )

  private def getControllerType(ct: c.Type) =
    ct.decls.filter( m=> m.isType && m.name.toString == "withController" ).map( _.asType )

  private def createController(ts: TypeSymbol) = {
    val ct = ts.toType.dealias
    val cm = getConstructor(ct)
    val exportAs = getExportToScope(ct.typeSymbol.asClass)
    val (ctrlDeps,ctrlArgs) = makeArgsList(cm)
    val ctrlDepNames = getDINames(cm)


    // AngularJS controller construction array
    val constructor = q"""js.Array[Any]("$$scope",..$ctrlDepNames,
          ((scope:js.Dictionary[js.Any],..$ctrlDeps) => {
            val ctrl = (new $ct(..$ctrlArgs)).asInstanceOf[js.Any]
            ${if(exportAs.isDefined) q"scope(${exportAs.get}) = ctrl" else q"()"}
          }):js.Function)"""

    //val constructor = createControllerConstructor(ts.toType.finalResultType.dealias,q"ctrl")
    //println(constructor)
    q"controller = $constructor" //$constructor"
  }

}
