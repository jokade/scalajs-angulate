package biz.enef.angular

import scala.scalajs.js

class AnnotatedFunction[F <: js.Function](val inlineArrayAnnotatedFn: js.Array[js.Any]) extends AnyVal

object AnnotatedFunction {

  import scala.language.implicitConversions
  import scala.language.experimental.macros

  @inline implicit def annotatedFunction[F <: js.Function](f: F): AnnotatedFunction[F] = macro impl.AnnotationMacros.functionDIArray

  val none = new AnnotatedFunction[js.Function](js.Array())

}
