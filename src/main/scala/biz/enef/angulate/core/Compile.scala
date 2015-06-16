// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description: Binding to the AngularJS $compile function
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate.core

import acyclic.file
import biz.enef.angulate._
import org.scalajs.dom.html.Element
import org.scalajs.jquery.JQuery
import scala.scalajs.js
import scala.scalajs.js.RegExp

trait Compile extends js.Object with ProvidedService {

  def apply(element: String): TemplateLinkingFunction = js.native
  def apply(element: String, transclude: TranscludeFunction): TemplateLinkingFunction = js.native
  def apply(element: String, transclude: TranscludeFunction, maxPriority: Int): TemplateLinkingFunction = js.native

  def apply(element: Element): TemplateLinkingFunction = js.native
  def apply(element: Element, transclude: TranscludeFunction): TemplateLinkingFunction = js.native
  def apply(element: Element, transclude: TranscludeFunction, maxPriority: Int): TemplateLinkingFunction = js.native

  def apply(element: JQLite): TemplateLinkingFunction = js.native
  def apply(element: JQLite, transclude: TranscludeFunction): TemplateLinkingFunction = js.native
  def apply(element: JQLite, transclude: TranscludeFunction, maxPriority: Int): TemplateLinkingFunction = js.native

  // These signatures result in a compilation error when JQuery bindings are not in classpath,
  // since the compiler probably checks all signatures, even if we don't use them!
  //def apply(element: JQuery): TemplateLinkingFunction = js.native
  //def apply(element: JQuery, transclude: TranscludeFunction): TemplateLinkingFunction = js.native
  //def apply(element: JQuery, transclude: TranscludeFunction, maxPriority: Int): TemplateLinkingFunction = js.native

}

trait CompileProvider extends ServiceProvider {
  def directive(name: String, directiveFactory: js.Function): CompileProvider = js.native

  // Undocumented, but it is there...
  def directive(directivesMap: js.Any): CompileProvider = js.native

  def aHrefSanitizationWhitelist(): RegExp = js.native
  def aHrefSanitizationWhitelist(regexp: RegExp): CompileProvider = js.native

  def imgSrcSanitizationWhitelist(): RegExp = js.native
  def imgSrcSanitizationWhitelist(regexp: RegExp): CompileProvider = js.native

  def debugInfoEnabled(enabled: Boolean = ???): js.Any = js.native
}

trait CloneAttachFunction extends js.Function {
  // Let's hint but not force cloneAttachFn's signature
  def apply(): js.Any = js.native
  def apply(scope: js.Object): js.Any = js.native
  def apply(scope: Scope): js.Any = js.native
  def apply(clonedElement: JQLite, scope: js.Object): js.Any = js.native
  def apply(clonedElement: JQLite, scope: Scope): js.Any = js.native
  def apply(clonedElement: JQuery, scope: js.Object): js.Any = js.native
  def apply(clonedElement: JQuery, scope: Scope): js.Any = js.native
}

// This corresponds to the "publicLinkFn" returned by $compile.
trait TemplateLinkingFunction extends js.Function {
  def apply(): AugmentedJQLite = js.native
  def apply(scope: js.Object): AugmentedJQLite = js.native
  def apply(scope: Scope): AugmentedJQLite = js.native
  def apply(scope: js.Object, cloneAttachFn: CloneAttachFunction): AugmentedJQLite = js.native
  def apply(scope: Scope, cloneAttachFn: CloneAttachFunction): AugmentedJQLite = js.native
  def apply(scope: js.Object, cloneAttachFn: js.Function): AugmentedJQLite = js.native
}

// This corresponds to $transclude (and also the transclude function passed to link).
trait TranscludeFunction extends js.Function {
  // If the scope is provided, then the cloneAttachFn must be as well.
  def apply(scope: js.Object, cloneAttachFn: CloneAttachFunction): AugmentedJQLite = js.native
  // If one argument is provided, then it's assumed to be the cloneAttachFn.
  def apply(cloneAttachFn: CloneAttachFunction = ???): AugmentedJQLite = js.native
}