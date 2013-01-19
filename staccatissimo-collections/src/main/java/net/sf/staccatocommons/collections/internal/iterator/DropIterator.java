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


package net.sf.staccatocommons.collections.internal.iterator;

import java.util.NoSuchElementException;

import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.iterators.thriter.AdvanceThriterator;
import net.sf.staccatocommons.iterators.thriter.Thriter;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class DropIterator<A> extends AdvanceThriterator<A> {

  private int n;
  private final Thriter<A> thriter;

  /**
   * Creates a new {@link DropIterator}
   */
  public DropIterator(int n, @NonNull Thriter<A> thriter) {
    this.n = n;
    this.thriter = thriter;
  }

  @Override
  public boolean hasNext() {
    while (n > 0) {
      if (!thriter.hasNext())
        return false;
      thriter.advanceNext();
      n--;
    }
    return thriter.hasNext();
  }

  @Override
  public void advanceNext() {
    if (!hasNext())
      throw new NoSuchElementException();
    thriter.advanceNext();
  }

  @Override
  public A current() {
    return thriter.current();
  }

  @Override
  public Thunk<A> delayedCurrent() {
    return thriter.delayedCurrent();
  }

}
