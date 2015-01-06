// -   Project: salajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

trait Controller[T<:Scope] {
  def dynamicScope: js.Dynamic = ???
  def scope: T = this.dynamicScope.asInstanceOf[T]
}

