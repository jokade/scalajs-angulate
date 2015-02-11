package biz.enef.angular

import scala.scalajs.js
import scala.language.implicitConversions

@inline class AnnotatedFunction(val inlineArrayAnnotatedFn: js.Array[_]) extends AnyVal

object AnnotatedFunction {

  import scala.language.experimental.macros

  @inline implicit def annotatedFunction(f: Function0[Nothing]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function1[Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function2[Nothing, Any, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function3[Nothing, Any, Any, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function4[Nothing, Any, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function5[Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

}
