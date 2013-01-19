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

package net.sf.roggen.collections.stream.internal;

import net.sf.roggen.collections.stream.AbstractStream;
import net.sf.roggen.iterators.thriter.Thriterator;
import net.sf.roggen.iterators.thriter.Thriterators;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * A stream that retrieves elements from an {@link Iterable}
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 *          the element type
 */
public class IterableStream<A> extends AbstractStream<A> {

  private final Iterable<? extends A> iterable;

  /**
   * Creates a new {@link IterableStream} that wraps the given {@link Iterable}
   * 
   * @param iterable
   */
  public IterableStream(@NonNull Iterable<? extends A> iterable) {
    this.iterable = iterable;
  }

  @Override
  public Thriterator<A> iterator() {
    return Thriterators.from(iterable.iterator());
  }
}