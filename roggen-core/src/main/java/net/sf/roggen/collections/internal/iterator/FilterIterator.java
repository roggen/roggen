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

package net.sf.roggen.collections.internal.iterator;

import java.util.Iterator;

import net.sf.roggen.defs.Evaluable;
import net.sf.roggen.iterators.UpdateCurrentThriterator;

/**
 * @author flbulgarelli
 * 
 */
public final class FilterIterator<A> extends UpdateCurrentThriterator<A> {

  private final Iterator<A> iter;
  private final Evaluable<? super A> predicate;

  /**
   * Creates a new {@link FilterIterator}
   */
  public FilterIterator(Iterator<A> iter, Evaluable<? super A> predicate) {
    this.iter = iter;
    this.predicate = predicate;
  }

  protected void updateCurrent() {
    while (iter.hasNext()) {
      A element = iter.next();
      if (predicate.eval(element)) {
        setCurrent(element);
        break;
      }
    }
  }

}