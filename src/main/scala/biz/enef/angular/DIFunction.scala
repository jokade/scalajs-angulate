// -   Project: scalajs-angulate (https://github.com/jokade/scalajs-angulate)
// Description:
//
// Copyright (c) 2015 Johannes Kastner <jkspam@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package biz.enef.angular

sealed trait DIFunction

object DIFunction{
  def apply(f: Function0[Any]) = DIFunction0(f)
  def apply(f: Function1[Nothing,Any]) = DIFunction1(f)
  def apply(f: Function2[Nothing,Nothing,Any]) = DIFunction2(f)

  sealed case class DIFunction0(f: Function0[Any]) extends DIFunction
  sealed case class DIFunction1(f: Function1[Nothing,Any]) extends DIFunction
  sealed case class DIFunction2(f: Function2[Nothing,Nothing,Any]) extends DIFunction
}
