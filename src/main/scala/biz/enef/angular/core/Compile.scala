// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Binding to the AngularJS $compile function
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular.core

import scala.scalajs.js

trait Compile extends js.Object {

  def apply(element: js.Any) : js.Function3[js.Any,js.Any,js.Any,js.Any] = js.native
}
