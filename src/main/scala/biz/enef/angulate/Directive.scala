// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Distributed under the MIT License (see included file LICENSE)
package biz.enef.angulate

import acyclic.file
import biz.enef.angulate.core.{Attributes, JQLite}

import scala.scalajs.js

/**
 * Interface to be implemented by classes that represent an AngularJS directive.
 */
trait Directive {
  def name: String = ???

  /**
   * String of subset of EACM which restricts the directive to a specific directive declaration style. If omitted, the
   * defaults (elements and attributes) are used.
   */
  val restrict: String = "EACM"
  /**
   * When this property is set to true, the HTML compiler will collect DOM nodes between nodes with the attributes
   * directive-name-start and directive-name-end, and group them together as the directive elements. It is recommended
   * that this feature be used on directives which are not strictly behavioural (such as ngClick), and which do not
   * manipulate or replace child nodes (such as ngInclude).
   */
  def multiElement: Boolean = ???
  @Deprecated
  def replace: Boolean = ???

  /**
   * Require another directive and inject its controller as the fourth argument to the linking function. The require
   * takes a string name (or array of strings) of the directive(s) to pass in. If an array is used, the injected argument
   * will be an array in corresponding order. If no such directive can be found, or if the directive does not have a
   * controller, then an error is raised (unless no link function is specified, in which case error checking is skipped).
   * The name can be prefixed with:
   *
   * - (no prefix) - Locate the required controller on the current element. Throw an error if not found.
   * - ? - Attempt to locate the required controller or pass null to the link fn if not found.
   * - {{{^}}} - Locate the required controller by searching the element and its parents. Throw an error if not found.
   * - {{{^^}}} - Locate the required controller by searching the element's parents. Throw an error if not found.
   * - {{{?^}}} - Attempt to locate the required controller by searching the element and its parents or pass null to the link fn if not found.
   * - {{{?^^}}} - Attempt to locate the required controller by searching the element's parents, or pass null to the link fn if not found.
   */
  def require: String = ???
  /**
   * Same as require but takes an array
   */
  def requireAll: js.Array[String] = ???

  def transclude: Boolean = ???

  /**
   * When there are multiple directives defined on a single DOM element, sometimes it is necessary to specify the order
   * in which the directives are applied. The priority is used to sort the directives before their compile functions get
   * called. Priority is defined as a number. Directives with greater numerical priority are compiled first. Pre-link
   * functions are also run in priority order, but post-link functions are run in reverse order. The order of directives
   * with the same priority is undefined. The default priority is 0.
   */
  def priority: Int = ???
  /**
   * If set to true then the current priority will be the last set of directives which will execute (any directives at
   * the current priority will still execute as the order of execution on same priority is undefined). Note that
   * expressions and other directives used in the directive's template will also be excluded from execution.
   */
  def terminal: Boolean = ???

  /**
   * Controller constructor function. The controller is instantiated before the pre-linking phase and it is shared with
   * other directives (see require attribute). This allows the directives to communicate with each other and augment
   * each other's behavior
   *
   * @param ctrl  New instance of the controller to define. As ControllerType is a JS trait, it cannot contain
   *              implementation of methods and you need to define them here (use a var of type js.Function instead of
   *              defining methods on the trait)
   * @param scope Current scope associated with the element
   * @param elem  Current element
   * @param attrs Current attributes object for the element
   */
  // TODO: support the transclude argument
  def controller(ctrl: ControllerType, scope: ScopeType, elem: JQLite, attrs: Attributes) : Unit = ???

  type ControllerType <: js.Any
  type ScopeType <: js.Any

  def controllerAs: String = ???

  /**
   * HTML markup that may:
   * - Replace the contents of the directive's element (default).
   * - Replace the directive's element itself (if replace is true - DEPRECATED).
   * - Wrap the contents of the directive's element (if transclude is true).
   */
  def template: String = ???
  /**
   * HTML markup that may:
   * - Replace the contents of the directive's element (default).
   * - Replace the directive's element itself (if replace is true - DEPRECATED).
   * - Wrap the contents of the directive's element (if transclude is true).
   * @param element template element - The element where the directive has been declared. It is safe to do template
   *                transformation on the element and child elements only.
   * @param attrs   template attributes - Normalized list of attributes declared on this element shared between all
   *                directive compile functions.
   */
  def template(element: JQLite, attrs: Attributes) : String = ???

  def templateUrl: String = ???
  def templateUrl(element: JQLite, attrs: Attributes) : String = ???

  /**
   * String representing the document type used by the markup in the template. AngularJS needs this information as those
   * elements need to be created and cloned in a special way when they are defined outside their usual containers like
   * {{{<svg>}}} and {{{<math>}}}.
   *
   * - html - All root nodes in the template are HTML. Root nodes may also be top-level elements such as {{{<svg>}}} or {{{<math>}}}.
   * - svg - The root nodes in the template are SVG elements (excluding {{{<math>}}}).
   * - math - The root nodes in the template are MathML elements (excluding {{{<svg>}}}).
   * If no templateNamespace is specified, then the namespace is considered to be html
   */
  def templateNamespace: String = ???

  /**
   * If set to true, then a new scope will be created for this directive. If multiple directives on the same element
   * request a new scope, only one new scope is created. The new scope rule does not apply for the root of the template
   * since the root of the template always gets a new scope
   */
  def scope: Boolean = ???
  /**
   * A new "isolate" scope is created. The 'isolate' scope differs from normal scope in that it does not prototypically
   * inherit from the parent scope. This is useful when creating reusable components, which should not accidentally read
   * or modify data in the parent scope.
   *
   * The 'isolate' scope takes an object hash which defines a set of local scope properties derived from the parent scope.
   * These local properties are useful for aliasing values for templates. Locals definition is a hash of local scope
   * property to its source.
   */
  def isolateScope: js.Dictionary[String] = ???

  /**
   * When an isolate scope is used for a component (see above), and controllerAs is used, bindToController: true will
   * allow a component to have its properties bound to the controller, rather than to scope. When the controller is
   * instantiated, the initial values of the isolate scope bindings are already available.
   */
  def bindToController: Boolean = ???

  def postLink(scope: ScopeType, element: JQLite, attrs: Attributes) : Unit = ???
  def postLink(scope: ScopeType, element: JQLite, attrs: Attributes, controller: ControllerType) : Unit = ???
  def preLink(scope: ScopeType, element: JQLite, attrs: Attributes) : Unit = ???
  def preLink(scope: ScopeType, element: JQLite, attrs: Attributes, controller: ControllerType) : Unit = ???

  def compile(tElement: JQLite, tAttrs: Attributes) : js.Any = ???
}
