// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Defines angulate-specific annotations
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate

import acyclic.file
import scala.annotation.StaticAnnotation

/**
 * Annotation for explicitly setting the name of a service to be injected.
 *
 * @param name
 */
@annotation.meta.param
case class named(name: String) extends StaticAnnotation

case class ComponentDef(selector: String,
                        template: String = null,
                        templateUrl: String = null)

case class Component(cd: ComponentDef) extends StaticAnnotation

case class NgAttr() extends StaticAnnotation
case class NgOneWay() extends StaticAnnotation
case class NgTwoWay() extends StaticAnnotation

