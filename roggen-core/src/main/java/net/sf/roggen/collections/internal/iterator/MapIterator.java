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

import net.sf.roggen.defs.Thunk;
import net.sf.roggen.defs.function.Function;
import net.sf.roggen.iterators.thriter.AdvanceThriterator;
import net.sf.roggen.iterators.thriter.Thriter;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class MapIterator<A, B> extends AdvanceThriterator<B> {

  private final Function<? super A, ? extends B> function;
  private final Thriter<? extends A> thriter;

  /**
   * Creates a new {@link MapIterator}
   */
  public MapIterator(@NonNull Function<? super A, ? extends B> function, @NonNull Thriter<? extends A> thriter) {
    this.function = function;
    this.thriter = thriter;
  }

  @Override
  public boolean hasNext() {
    return thriter.hasNext();
  }

  @Override
  public void advanceNext() {
    thriter.advanceNext();
  }

  @Override
  public B current() {
    return function.apply(thriter.current());
  }

  @Override
  public Thunk<B> delayedCurrent() {
    return (Thunk<B>) function.delayedValue(thriter.delayedCurrent());
  }
}
