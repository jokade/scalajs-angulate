// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Provides various macro enhancements to the Module API
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.impl

import scala.reflect.macros.blackbox

protected[angular] class ModuleMacros(val c: blackbox.Context) extends MacroBase {
  import c.universe._

  // print generated code to console during compilation
  private lazy val logCode = c.settings.exists( _ == "biz.enef.angular.ModuleMacros.debug" )

  def config(f: c.Tree) = {
    val tree = q"${c.prefix}.config( ${createFunctionDIArray(f)} )"

    if(logCode) printCode(tree)

    tree
  }

}
