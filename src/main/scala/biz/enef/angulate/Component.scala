// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Annotation for defining Angular 1.5 components
//
// Distributed under the MIT License (see included file LICENSE)

//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:
package biz.enef.angulate

import biz.enef.angulate.impl.{ControllerMacroUtils, MacroBase}
import de.surfice.smacrotools.JsWhiteboxMacroTools

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.scalajs.js

@compileTimeOnly("enable macro paradise to expand macro annotations")
class Component(selector: String,
                template: String = "",
                templateUrl: String = "",
                controllerAs: String = "$ctrl",
                bindings: js.Dictionary[String] = null) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Component.Macro.impl
}

object Component {


  private [angulate] class Macro(val c: whitebox.Context) extends JsWhiteboxMacroTools {
    import c.universe._

    val annotationParamNames = Seq(
      "selector",
      "template",
      "templateUrl",
      "controllerAs",
      "bindings"
    )

    def componentOf[T: c.WeakTypeTag] = {
      val ct = weakTypeOf[T]
      val module = Select(c.prefix.tree, TermName("self"))

      val comp = TermName(ct.typeSymbol.fullName)

      val tree = q"""$module.component($comp.selector,$comp.options)"""
      tree
    }

    def impl(annottees: c.Expr[Any]*) : c.Expr[Any] = annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee for @Component")
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val parts = extractClassParts(classDecl)
      import parts._

      val module = Select(c.prefix.tree, TermName("self"))

      val objName = fullName + "_"

      val annots = extractAnnotationParameters(c.prefix.tree,annotationParamNames) collect {
        case (p,Some(v)) if p != "selector" => q""" ${p.toString} -> $v"""
      }

      val base = getJSBaseClass(parents)

      val tree =
        q"""{
             @scalajs.js.annotation.JSExport($fullName)
             @scalajs.js.annotation.ScalaJSDefined
             class $name ( ..$params ) extends ..$base { ..$body }
             @scalajs.js.annotation.JSExport($objName)
             @scalajs.js.annotation.ScalaJSDefined
             object ${name.toTermName} extends scalajs.js.Object {
               def selector = "test"
               def controller = js.Array((() => new $name(..$params)):js.Function)
               def options = scalajs.js.Dictionary( "controller" -> controller, ..$annots )
             }
            }
       """
      c.Expr[Any](tree)
    }
  }
}
