package biz.enef.angular.impl

import scala.reflect.macros.blackbox.Context

protected[angular] class AnnotationMacros(val c: Context) extends MacroBase {
  import c.universe._

  def functionDIArray(f: c.Tree) = {
    val diArray = createFunctionDIArray(f)

    q"""{import biz.enef.angular.AnnotatedFunction
         new AnnotatedFunction($diArray)
        }"""

  }

}
