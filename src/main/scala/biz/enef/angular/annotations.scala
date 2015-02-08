// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.annotation.StaticAnnotation

/**
 * Annotation for explicitly setting the name of a service to be injected.
 *
 * @param name
 */
@annotation.meta.param
case class named(name: String) extends StaticAnnotation

/**
 * This annotation marks an NGController to be exported to the corresponding
 * scope under the specified name.
 *
 * @param name variable name under which the controller will be exported to the scope
 */
case class ExportToScope(name: String) extends StaticAnnotation

case class ComponentDef(selector: String,
                        template: String = null,
                        templateUrl: String = null)

case class Component(cd: ComponentDef) extends StaticAnnotation

case class NgAttr() extends StaticAnnotation
case class NgOneWay() extends StaticAnnotation
case class NgTwoWay() extends StaticAnnotation
