package biz.enef.angular.core.impl

import scala.reflect.macros.whitebox

class HttpMacros(val c: whitebox.Context) {
  import c.universe._

  def onCompleteImpl() = q"()"
}
