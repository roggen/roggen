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

/*
 Copyright (c) 2012, The Staccato-Commons Team

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; version 3 of the License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 */
package net.sf.roggen.collections.internal.iterator;

import net.sf.roggen.defs.Thunk;
import net.sf.roggen.iterators.thriter.AdvanceThriterator;
import net.sf.roggen.iterators.thriter.Thriterator;
import net.sf.roggen.lang.thunk.Thunks;

/**
 * @author flbulgarelli
 * 
 */
public abstract class AbstractInsertBeforeIterator<A> extends AdvanceThriterator<A> {
  private final A element;
  private final Thriterator<A> iterator;

  /**
   * Creates a new {@link AbstractInsertBeforeIterator}
   */
  public AbstractInsertBeforeIterator(A element, Thriterator<A> iterator) {
    this.element = element;
    this.iterator = iterator;
  }

  protected abstract boolean atInsertionPoint();

  public final A current() {
    return atInsertionPoint() ? element : iterator().current();
  }

  public final Thunk<A> delayedCurrent() {
    return atInsertionPoint() ? Thunks.constant(element) : iterator().delayedCurrent();
  }

  protected Thriterator<A> iterator() {
    return iterator;
  }

}
