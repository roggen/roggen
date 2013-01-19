/**
 *  Copyright (c) 2012, The Roggen Team
 *  Copyright (c) 2010-2012, The StaccatoCommons Team
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 */


package net.sf.staccatocommons.control.monad;

/**
 * @author flbulgarelli
 * 
 * @since 1.2
 */
public class UnboundMonad<A> extends AbstractMonad<A> {

  private final MonadicValue<A> monadValue;

  /**
   * Creates a new {@link UnboundMonad}
   */
  public UnboundMonad(MonadicValue<A> monadValue) {
    this.monadValue = monadValue;
  }

  public MonadicValue<A> monadicValue() {
    return monadValue;
  }

  public final void run() {}

}
