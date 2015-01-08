// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides a base class for macros with common utility functions
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import scala.reflect.macros.blackbox


protected[angular] abstract class MacroBase {
  val c: blackbox.Context
  import c.universe._

  /**
   * Print to console during compilation
   * @param tree
   * @param msg
   */
  def printCode(tree: Tree, msg: String = "") =
    c.info( c.enclosingPosition,
     s"""$msg
        |${showCode(tree)}
      """.stripMargin, true )

}
