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

package net.sf.roggen.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.roggen.defs.Thunk;
import net.sf.roggen.iterators.thriter.AdvanceThriterator;
import net.sf.roggen.iterators.thriter.Thriter;
import net.sf.roggen.iterators.thriter.Thriterator;
import net.sf.roggen.iterators.thriter.Thriterators;
import net.sf.roggen.iterators.thriter.internal.ConstantThunk;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class AppendThriterator<A> extends AdvanceThriterator<A> {

  private final Thriter<? extends A> iterator;
  private final A element;
  private boolean unconsumed = true;

  /**
   * 
   * Creates a new {@link AppendThriterator}
   */
  public AppendThriterator(@NonNull Thriter<? extends A> iterator, A element) {
    this.iterator = iterator;
    this.element = element;
  }

  /**
   * 
   * Creates a new {@link AppendThriterator}
   */
  public AppendThriterator(@NonNull Iterator<? extends A> iterator, A element) {
    this((Thriter<? extends A>) Thriterators.from(iterator), element);
  }

  /**
   * 
   * Creates a new {@link AppendThriterator}
   */
  public AppendThriterator(@NonNull Thriterator<? extends A> iterator, A element) {
    this((Thriter<? extends A>) iterator, element);
  }

  public final boolean hasNext() {
    if (iterator.hasNext())
      return true;
    return unconsumed;
  }

  public final void advanceNext() throws NoSuchElementException {
    if (iterator.hasNext())
      iterator.advanceNext();
    else if (unconsumed)
      unconsumed = false;
    else
      throw new NoSuchElementException();
  }

  public final A current() throws NoSuchElementException {
    if (unconsumed)
      return iterator.current();
    return elementValue();
  }

  protected A elementValue() {
    return element;
  }

  protected Thunk<A> elementThunk() {
    return new ConstantThunk<A>(element);
  }

  public Thunk<A> delayedCurrent() {
    if (unconsumed)
      return (Thunk<A>) iterator.delayedCurrent();
    return elementThunk();
  }

  public String toString() {
    return "AppendThriterator(" + iterator + ", " + element + ")";
  }

}
