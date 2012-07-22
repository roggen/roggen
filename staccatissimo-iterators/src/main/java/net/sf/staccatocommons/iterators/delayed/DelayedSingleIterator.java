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


package net.sf.staccatocommons.iterators.delayed;

import java.util.NoSuchElementException;

import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.iterators.SingleThriterator;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class DelayedSingleIterator<A> extends SingleThriterator<A> {

  /**
   * Creates a new {@link DelayedSingleIterator}
   */
  public DelayedSingleIterator(@NonNull Thunk<A> element) {
    super((A) element);
  }

  public A current() throws NoSuchElementException {
    return ((Thunk<A>) super.current()).value();
  }

  public Thunk<A> delayedCurrent() {
    return (Thunk<A>) super.current();
  }

}
