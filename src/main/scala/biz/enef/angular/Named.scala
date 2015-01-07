// -   Project: salajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import scala.annotation.StaticAnnotation

/**
 * Annotation for explicitly setting the name of a service to be injected.
 *
 * @param name
 */
@annotation.meta.param
case class Named(name: String) extends StaticAnnotation
