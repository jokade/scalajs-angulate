// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macro implementations for AnnotatedFunction
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.impl

import scala.reflect.macros.blackbox.Context

protected[angulate] class AnnotationMacros(val c: Context) extends MacroBase {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angulate.AnnotationMacros.debug" )

  def functionDIArray(f: c.Tree) = {
    val diArray = createFunctionDIArray(f)

    val tree =
    q"""{import scalajs.js
         import biz.enef.angulate.AnnotatedFunction
         new AnnotatedFunction($diArray)
         }"""

    if(logCode) printCode( tree )
    tree
  }

}
