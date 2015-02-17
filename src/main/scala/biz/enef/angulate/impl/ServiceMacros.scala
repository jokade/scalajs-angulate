// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.impl

import acyclic.file
import scala.reflect.macros.blackbox

protected [angulate] class ServiceMacros(val c: blackbox.Context) extends MacroBase {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angulate.ServiceMacros.debug" )

  def serviceOf[T: c.WeakTypeTag] = {
    val serviceType = weakTypeOf[T]
    val name = serviceType.toString.split('.').last
    createService(serviceType, q"${name.head.toLower+name.tail}")
  }

  def serviceOfWithName[T: c.WeakTypeTag](name: c.Tree) = {
    val serviceType = weakTypeOf[T]
    createService(serviceType, q"${name}")
  }

  private def createService(ct: c.Type, name: c.Tree) = {
    val module = Select(c.prefix.tree, TermName("self"))

    val constr = ct.members.filter( _.isConstructor ).map( _.asMethod ).head

    val (params,args) = makeArgsList(constr)
    val deps = getDINames(constr)

    // AngularJS service construction array
    val carray =
     q"""js.Array[Any](..$deps, ((..$params) =>
          new $ct(..$args)):js.Function)
      """

    val tree = q"""{import scala.scalajs.js
                    import js.Dynamic.{global,literal}
                    $module.self.factory($name,$carray)
                   }"""

    if (logCode) printCode(tree, "\nserviceOf macro:")
    tree
  }
}
