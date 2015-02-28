// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import biz.enef.angulate.Scope
import org.scalajs.dom
import org.scalajs.dom.Element
import org.scalajs.jquery.{JQuery, JQueryStatic}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

trait JQLite extends js.Object {

  def addClass(classNames: String): JQLite = js.native
  def addClass(func: js.Function2[js.Any, js.Any, JQLite]): js.Dynamic = js.native
  def attr(attributeName: String): String = js.native
  def attr(attributeName: String, value: js.Any): JQLite = js.native
  def attr(map: js.Any): JQLite = js.native
  def attr(attributeName: String, func: js.Function2[js.Any, js.Any, js.Any]): JQLite = js.native
  def hasClass(className: String): Boolean = js.native
  def html(htmlString: String): JQLite = js.native
  def html(): String = js.native
  def prop(propertyName: String): js.Dynamic = js.native
  def prop(propertyName: String, value: js.Any): JQLite = js.native
  def prop(map: js.Any): JQLite = js.native
  def prop(propertyName: String, func: js.Function2[js.Any, js.Any, js.Any]): JQLite = js.native
  def removeAttr(attributeName: js.Any): JQLite = js.native
  def removeClass(className: js.Any): JQLite = js.native
  def removeClass(): JQLite = js.native
  def removeClass(func: js.Function2[js.Any, js.Any, js.Any]): JQLite = js.native
  def toggleClass(className: js.Any, swtch: Boolean): JQLite = js.native
  def toggleClass(className: js.Any): JQLite = js.native
  def toggleClass(swtch: Boolean): JQLite = js.native
  def toggleClass(): JQLite = js.native
  def toggleClass(func: js.Function3[js.Any, js.Any, js.Any, js.Any]): JQLite = js.native
  def `val`(): js.Dynamic = js.native
  def `val`(value: js.Array[String]): JQLite = js.native
  def `val`(value: String): JQLite = js.native
  def `val`(func: js.Function2[js.Any, js.Any, js.Any]): JQLite = js.native
  @JSName("val") def value(): js.Dynamic = js.native
  @JSName("val") def value(value: js.Array[String]): JQLite = js.native
  @JSName("val") def value(value: String): JQLite = js.native
  @JSName("val") def value(func: js.Function2[js.Any, js.Any, js.Any]): JQLite = js.native
  def css(propertyNames: js.Array[js.Any]): String = js.native
  def css(propertyName: String): String = js.native
  def css(propertyName: String, value: js.Any): JQLite = js.native
  def css(propertyName: js.Any, value: js.Any): JQLite = js.native
  def css(propertyName: js.Any): JQLite = js.native
  def data(key: String, value: js.Any): JQLite = js.native
  def data(obj: js.Any): JQLite = js.native
  def data(key: String): js.Dynamic = js.native
  def data(): js.Dynamic = js.native
  def removeData(nameOrList: js.Any): JQLite = js.native
  def removeData(): JQLite = js.native
  def bind(eventType: String, preventBubble: Boolean): JQLite = js.native
  def bind(events: js.Any*): js.Dynamic = js.native
  def off(events: String): JQLite = js.native
  def off(): JQLite = js.native
  def off(eventsMap: js.Any): JQLite = js.native
  def on(events: String): JQLite = js.native
  def on(eventsMap: js.Any): JQLite = js.native
  def one(events: String): JQLite = js.native
  def one(eventsMap: js.Any): JQLite = js.native
  def ready(handler: js.Any): JQLite = js.native
  def triggerHandler(eventType: String, extraParameters: js.Any*): Object = js.native
  def unbind(eventType: String): JQLite = js.native
  def unbind(): JQLite = js.native
  def unbind(eventType: String, fls: Boolean): JQLite = js.native
  def unbind(evt: js.Any): JQLite = js.native
  def after(content: js.Any*): JQLite = js.native
  def after(func: js.Function1[js.Any, js.Any]): js.Dynamic = js.native
  def append(content: js.Any*): JQLite = js.native
  def append(func: js.Function2[js.Any, js.Any, js.Any]): js.Dynamic = js.native
  def clone(withDataAndEvents: Boolean, deepWithDataAndEvents: Boolean): JQLite = js.native
  def clone(withDataAndEvents: Boolean): JQLite = js.native
  override def clone(): JQLite = js.native
  def detach(selector: js.Any): JQLite = js.native
  def detach(): JQLite = js.native
  def empty(): JQLite = js.native
  def prepend(content: js.Any*): JQLite = js.native
  def prepend(func: js.Function2[js.Any, js.Any, js.Any]): JQLite = js.native
  def remove(selector: js.Any): JQLite = js.native
  def remove(): JQLite = js.native
  def replaceWith(func: js.Any): JQLite = js.native
  def text(textString: String): JQLite = js.native
  def text(): String = js.native
  def wrap(wrappingElement: js.Any): JQLite = js.native
  def wrap(func: js.Function1[js.Any, js.Any]): JQLite = js.native
  @js.annotation.JSBracketAccess
  def apply(x: Int): dom.html.Element = js.native
  def children(selector: js.Any): JQLite = js.native
  def children(): JQLite = js.native
  def contents(): JQLite = js.native
  def eq(index: Int): JQLite = js.native
  def find(selector: String): JQLite = js.native
  def next(): JQLite = js.native
  def parent(): JQLite = js.native
}

trait JQLiteStatic extends js.Object

/**
 * angular.element
 * when calling angular.element, angular returns a jQuery object,
 * augmented with additional methods like e.g. scope.
 * see: http://docs.angularjs.org/api/angular.element
 */
trait AugmentedJQStatic[Q, AQ <: Q] extends js.Any {

  def apply(selector: String, context: js.Any): AQ = js.native
  def apply(selector: String): AQ = js.native
  def apply(element: Element): AQ = js.native
  def apply(`object`: js.Any): AQ = js.native
  def apply(elementArray: js.Array[Element]): AQ = js.native
  def apply(`object`: Q): AQ = js.native
  def apply(func: js.Function): AugmentedJQuery = js.native
  def apply(): AugmentedJQuery = js.native
}

trait AugmentedJQLiteStatic extends JQLiteStatic with AugmentedJQStatic[JQLite, AugmentedJQLite]

trait AugmentedJQueryStatic extends JQueryStatic with AugmentedJQStatic[JQuery, AugmentedJQuery] {
  override def apply(selector: String, context: js.Any): AugmentedJQuery = js.native
  override def apply(selector: String): AugmentedJQuery = js.native
  override def apply(element: Element): AugmentedJQuery = js.native
  override def apply(`object`: js.Any): AugmentedJQuery = js.native
  override def apply(elementArray: js.Array[Element]): AugmentedJQuery = js.native
  override def apply(`object`: JQuery): AugmentedJQuery = js.native
  override def apply(func: js.Function): AugmentedJQuery = js.native
  override def apply(): AugmentedJQuery = js.native

}

trait AugmentedJQ[Q] extends js.Any {
  def find(selector: String): AugmentedJQ[Q] = js.native
  def find(element: js.Any): AugmentedJQ[Q] = js.native
  def find(obj: Q): AugmentedJQ[Q] = js.native
  def controller(): js.Any = js.native
  def controller(name: String): js.Any = js.native
  def injector(): Injector = js.native
  def scope(): Scope = js.native
  def isolateScope(): Scope = js.native
  def inheritedData(key: String, value: js.Any): Q = js.native
  def inheritedData(obj: js.Dictionary[Any]): Q = js.native
  def inheritedData(key: String): js.Any = js.native
  def inheritedData(): js.Any = js.native
}

trait AugmentedJQLite extends JQLite with AugmentedJQ[JQLite] {
  override def find(selector: String): AugmentedJQLite = js.native
  override def find(element: js.Any): AugmentedJQLite = js.native
  override def find(obj: JQLite): AugmentedJQLite = js.native
  override def controller(): js.Any = js.native
  override def controller(name: String): js.Any = js.native
  override def injector(): Injector = js.native
  override def scope(): Scope = js.native
  override def isolateScope(): Scope = js.native
  override def inheritedData(key: String, value: js.Any): JQLite = js.native
  override def inheritedData(obj: js.Dictionary[Any]): JQLite = js.native
  override def inheritedData(key: String): js.Any = js.native
  override def inheritedData(): js.Any = js.native
}

trait AugmentedJQuery extends JQuery with AugmentedJQ[JQuery] {
  override def find(selector: String): AugmentedJQuery = js.native
  override def find(element: js.Any): AugmentedJQuery = js.native
  override def find(obj: JQuery): AugmentedJQuery = js.native
  override def controller(): js.Any = js.native
  override def controller(name: String): js.Any = js.native
  override def injector(): Injector = js.native
  override def scope(): Scope = js.native
  override def isolateScope(): Scope = js.native
  override def inheritedData(key: String, value: js.Any): JQuery = js.native
  override def inheritedData(obj: js.Dictionary[Any]): JQuery = js.native
  override def inheritedData(key: String): js.Any = js.native
  override def inheritedData(): js.Any = js.native
}
