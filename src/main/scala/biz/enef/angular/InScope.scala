// -   Project: salajs-nglite (https://github.com/jokade/scalajs-nglite)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import annotation.meta._
/**
 * This annotation marks a field or method in a [[Controller]] class to be exported to the scope.
 */
@getter @setter @field @beanGetter @beanGetter
class InScope extends annotation.StaticAnnotation
