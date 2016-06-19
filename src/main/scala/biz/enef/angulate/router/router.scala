// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)

// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)

// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Façade traits for the Angular 1.5 component router
//
// Distributed under the MIT License (see included file LICENSE)

//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description:
package biz.enef.angulate.router

import de.surfice.smacrotools.JSOptionsObject

import scala.annotation.StaticAnnotation
import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.{JSName, ScalaJSDefined}

class RouteConfig(defs: RDef*) extends StaticAnnotation

@JSOptionsObject
@deprecated("The Angular ComponentRouter is deprecated","0.3.0")
case class RDef(path: String,
                name: String,
                component: js.UndefOr[String] = js.undefined,
                redirectTo: js.UndefOr[String] = js.undefined,
                useAsDefault: js.UndefOr[Boolean] = js.undefined,
                data: js.UndefOr[js.Object] = js.undefined)

@ScalaJSDefined
@deprecated("The Angular ComponentRouter is deprecated","0.3.0")
trait RouterOnActivate extends js.Object {
  //@JSName("$routerOnActivate")
  def $routerOnActivate(next: ComponentInstruction) : Unit
}

@js.native
@deprecated("The Angular ComponentRouter is deprecated","0.3.0")
trait ComponentInstruction extends js.Object {
  def params: js.Dictionary[js.Any]
}

@js.native
@deprecated("The Angular ComponentRouter is deprecated","0.3.0")
trait Router extends js.Object {
  def navigate(changes: js.Array[js.Any]): Promise[js.Any] = js.native
  def navigateByUrl(url: String): Promise[js.Any] = js.native
}

@deprecated("The Angular ComponentRouter is deprecated","0.3.0")
object Router {
  implicit final class RichRouter(val router: Router) extends AnyVal {
    def navigateTo(name: String) = router.navigate(js.Array(name))
    def navigateTo(name: String, params: (String,js.Any)*) = router.navigate(js.Array(name,js.Dictionary(params:_*)))
  }
}