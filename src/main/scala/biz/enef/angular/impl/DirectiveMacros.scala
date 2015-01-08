// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macros to support using a scala class as Angular directive
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import scala.reflect.macros.blackbox

protected[angular] class DirectiveMacros(val c: blackbox.Context) extends MacroBase {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angular.DirectiveMacros.debug" )

  // types
  val tNotImplemented = typeOf[NotImplementedError]


  def directiveOf[T: c.WeakTypeTag] = {
    val directiveType = weakTypeOf[T]
    val name = directiveType.toString.split('.').last
    createDirective(directiveType, q"${name.head.toLower+name.tail}")
  }


  private def createDirective(ct: c.Type, name: c.Tree) = {
    val module = c.prefix

    // assemble all defined directive attributes (ie 'template', 'restrict', ...)
    val atts = getDirectiveAttributes(ct).map( a => (a.name.toString,a)) map {
      case ("isolateScope",_)  => q"scope = dimpl.isolateScope"
      case ("postLink",_)      => q"link = (dimpl.postLink _):js.Function"
      case (_,a) if a.isGetter => q"${a.name} = dimpl.${a.name}"
      case (_,a)               => q"${a.name} = (dimpl.${a.name} _):js.Function"
    }

    // create directive definition object
    val ddo = q"""literal( ..$atts )"""

    // directive factory function
    val constr = ct.members.filter( _.isConstructor ).map( _.asMethod ).head
    val (params,args) = makeArgsList(constr)
    val deps = getDINames(constr)
    val carray =
      q"""js.Array[Any](..$deps, ((..$params) => {
            val dimpl = new $ct(..$args)
            $ddo
          }):js.Function)
       """

    val tree = q"""{import scala.scalajs.js
                    import js.Dynamic.{global,literal}
                    $module.directive($name,$carray)
                   }"""
    if(logCode) printCode(tree)

    tree
  }

  private def getDirectiveAttributes(ct: c.Type) =
    ct.decls.filter( m=> m.isMethod && !m.isConstructor ).map( _.asMethod )
}
