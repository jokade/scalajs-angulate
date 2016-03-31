// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate

import scala.language.implicitConversions
import scala.scalajs.js

@inline class AnnotatedFunction(val inlineArrayAnnotatedFn: js.Array[Any]) extends AnyVal

object AnnotatedFunction {

  import scala.language.experimental.macros

  @inline implicit def annotatedFunction(f: Function0[Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function1[Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function2[Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function3[Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function4[Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function5[Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function6[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function7[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function8[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function9[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function10[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function11[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function12[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function13[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function14[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function15[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function16[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function17[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function18[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function19[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function20[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function21[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

  @inline implicit def annotatedFunction(f: Function22[Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Nothing, Any]): AnnotatedFunction = macro impl.AnnotationMacros.functionDIArray

}
