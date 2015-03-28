// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Defines angulate-specific annotations
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate

import acyclic.file
import scala.annotation.StaticAnnotation
import scala.scalajs.js

/**
 * Annotation for explicitly setting the name of a service to be injected.
 *
 * @param name
 */
@annotation.meta.param
case class named(name: String) extends StaticAnnotation

case class ComponentDef(selector: String,
                        template: String = null,
                        templateUrl: String = null,
                        map: js.Dictionary[String] = null)

case class Component(cd: ComponentDef) extends StaticAnnotation


