// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides a base class for macros with common utility functions
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.impl

import acyclic.file
import biz.enef.angulate.named

import scala.reflect.macros.blackbox


protected[angulate] abstract class MacroBase {
  val c: blackbox.Context
  import c.universe._

  /* type definitions */
  val namedAnnotation = typeOf[named]

  // include runtime log messages if true
  protected lazy val runtimeLogging = c.settings.exists( _ == "biz.enef.angulate.runtimeLogging" )

  /**
   * Print to console during compilation
 *
   * @param tree
   * @param msg
   */
  protected[this] def printCode(tree: Tree, msg: String = "") =
    c.info( c.enclosingPosition,
     s"""$msg
        |${showCode(tree)}
      """.stripMargin, true )

  protected[this] def makeArgsList(f: MethodSymbol) =
    if(f.paramLists.isEmpty)
      (List(),List())
    else
      f.paramLists.head.map( p => {
        val name = TermName(c.freshName("x"))
        (q"$name: ${p.typeSignature}", q"$name")
      }).unzip

  protected[this] def getDINames(f: MethodSymbol) = {
    f.paramLists.head.map{ p=>
      p.annotations.find( _.tree.tpe =:= namedAnnotation ).map { a =>
        val name = a.tree.children.tail.head.toString
        // TODO: that's ludicrous... what is the right way to unquote the string???
        name.substring(1,name.length-1)
      }.getOrElse(p.name.toString)
    }
  }

  def getDINames(params: Iterable[Tree]): Iterable[String] =
    if(params.isEmpty) None
    else
      params map {
        case q"$mods val $name: $tpe = $e" =>
          val t = c.typecheck(tpe,c.TYPEmode).tpe
          t.typeSymbol.annotations.map(_.tree).collectFirst{
            case q"new $name( ..$params )" if name.toString == "scala.scalajs.js.annotation.JSName" => params.head match {
              case Literal(Constant(x)) => x.toString
            }
          }.getOrElse(t.toString)
      }

  /**
   * Creates a AngularJS constructor array for the specified type.
   *
   * @note the returned tree requires `js.Array` to be in scope
   * @param ct class type
   */
  protected[this] def createDIArray(ct: Type) = {
    val m = getConstructor(ct)
    val deps = getDINames(m)
    val (params,args) = makeArgsList(m)
    q"""js.Array[Any](..$deps, ((..$params) => new $ct(..$args)):js.Function)"""
  }


  protected[this] def getConstructor(ct: Type) = ct.decls.filter( _.isConstructor ).collect{ case m: MethodSymbol => m}.head

  // TODO: support DI name annotations
  protected[this] def createFunctionDIArray(t: c.Tree) = {
    val (f,params) = analyzeFunction(t)
    val diNames = params.map( p => p._2.toString )
    q"js.Array[Any](..$diNames, $f:js.Function)"
  }

  protected[this] def analyzeFunction(t: c.Tree) = {
    val (m:Tree,params:List[ValDef]) = t match {
      case q"(..$params) => $body" => (t,params)
      case q"{(..$params) => $body}" => (t.children.head,params)
    }
    val args = params.map{ p =>
      val q"$mods val $name: $tpe = $rhs" = p

      (mods,name,tpe,rhs)
    }
    (m,args)
  }

}
