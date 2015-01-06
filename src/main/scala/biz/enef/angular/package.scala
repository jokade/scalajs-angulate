// -   Project: salajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef

import biz.enef.angular.Module.RichModule

import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.scalajs.js

package object angular {

  implicit def module2RichModule(m: Module) : RichModule = m.asInstanceOf[RichModule]
}
