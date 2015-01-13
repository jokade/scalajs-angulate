// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Defines marker traits for angulate controllers
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

import biz.enef.angular.core.{Attributes, JQLite}

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExport, JSName}

sealed trait NGController

/**
 * Marks a class as AngularJS controller, to be used with the "controllerAs" syntax.
 *
 * All public `val`s, `var`s and `def`s defined in the controller class will be exported
 * to the controller scope.
 */
trait Controller extends NGController


trait ScopeController extends NGController

