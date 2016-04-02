// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Façade traits for the Angular 1.5 component router
//
// Distributed under the MIT License (see included file LICENSE)

//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:
package biz.enef.angulate

import de.surfice.smacrotools.JSOptionsObject

import scala.annotation.StaticAnnotation
import scala.scalajs.js

class RouteConfig(defs: RDef*) extends StaticAnnotation

@JSOptionsObject
case class RDef(path: String,
                name: String,
                component: js.UndefOr[String] = js.undefined,
                redirectTo: js.UndefOr[String] = js.undefined,
                useAsDefault: js.UndefOr[Boolean] = js.undefined,
                data: js.UndefOr[js.Object] = js.undefined)
