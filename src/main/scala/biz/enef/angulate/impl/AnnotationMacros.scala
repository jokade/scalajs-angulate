// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Macro implementations for AnnotatedFunction
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.impl

import scala.reflect.macros.blackbox.Context

protected[angulate] class AnnotationMacros(val c: Context) extends MacroBase {
  import c.universe._

  def functionDIArray(f: c.Tree) = {
    val diArray = createFunctionDIArray(f)

    q"""{import biz.enef.angulate.AnnotatedFunction
         new AnnotatedFunction($diArray)
        }"""

  }

}
