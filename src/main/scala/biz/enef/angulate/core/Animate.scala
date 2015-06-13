// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: API for Angular $animate service
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import org.scalajs.dom.Element

import scala.scalajs.js

/**
 * API for Angular \$animate service.
 *
 * @see [[https://docs.angularjs.org/api/ng/service/\$animate]]
 */
trait AnimateService  extends ProvidedService {

  def enter(element: Element, parent: Element, after: Element) : QPromise = js.native
  def enter(element: Element, parent: Element, after: Element, options: js.Object) : QPromise = js.native

  def leave(element: Element) : QPromise = js.native
  def leave(element: Element, options: js.Object) : QPromise = js.native

  def move(element: Element, parent: Element, after: Element) : QPromise = js.native
  def move(element: Element, parent: Element, after: Element, options: js.Object) : QPromise = js.native

  def addClass(element: Element, className: String) : QPromise = js.native
  def addClass(element: Element, className: String, options: js.Object) : QPromise = js.native

  def removeClass(element: Element, className: String) : QPromise = js.native
  def removeClass(element: Element, className: String, options: js.Object) : QPromise = js.native

  def setClass(element: Element, add: String, remove: String) : QPromise = js.native
  def setClass(element: Element, add: String, remove: String, options: js.Object) : QPromise = js.native

}
