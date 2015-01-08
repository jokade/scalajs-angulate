// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

trait Controller {
  def dynamicScope: js.Dynamic = ???
  lazy val scope: Scope = this.dynamicScope.asInstanceOf[Scope]
}

trait ScopeController[T<:Scope] extends Controller {
  override lazy val scope: T = this.dynamicScope.asInstanceOf[T]
}

