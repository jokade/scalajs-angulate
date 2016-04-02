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
                templateFn: js.Function0[String] = null,
                templateUrl: String = "",
                templateUrlFn: js.Function0[String] = null,
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
      "templateFn",
      "templateUrl",
      "templateUrlFn",
      "controllerAs",
      "bindings"
    )

    def componentOf[T: c.WeakTypeTag] = {
      val ct = weakTypeOf[T]
      val module = Select(c.prefix.tree, TermName("self"))
      val comp = ct.typeSymbol.companion

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

      val annots = extractAnnotationParameters(c.prefix.tree,annotationParamNames)
      val options = annots collect {
        case ("templateFn",Some(v)) => q""" "template" -> $v """
        case ("templateUrlFn",Some(v)) => q""" "templateUrl" -> $v """
        case (p,Some(v)) if p != "selector" => q""" ${p.toString} -> $v"""
      }
      val selector = (annots collect {
        case ("selector",Some(v)) => v
      }).head

      val base = getJSBaseClass(parents)

      val args = paramNames(params)
      val diNames = args map ( _.toString )

//      @scalajs.js.annotation.JSExport($fullName)
//      @scalajs.js.annotation.JSExport($objName)
      val tree =
        q"""{
             @scalajs.js.annotation.ScalaJSDefined
             class $name ( ..$params ) extends ..$base { ..$body }
             @scalajs.js.annotation.ScalaJSDefined
             object ${name.toTermName} extends scalajs.js.Object {
               def selector = $selector
               def controller = scalajs.js.Array(..$diNames,((..$params) => new $name(..$args)):scalajs.js.Function)
               def options = scalajs.js.Dictionary( "controller" -> controller, ..$options )
             }
            }
       """

      println(tree)
      c.Expr[Any](tree)
    }

  }
}
