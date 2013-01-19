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

import static net.sf.roggen.collections.iterable.internal.IterablesInternal.*;
import static net.sf.roggen.lang.tuple.Tuples.*;

import java.util.Iterator;

import net.sf.roggen.collections.stream.AbstractStream;
import net.sf.roggen.collections.stream.Stream;
import net.sf.roggen.defs.tuple.Tuple2;
import net.sf.roggen.iterators.thriter.Thriterator;
import net.sf.roggen.iterators.thriter.Thriterators;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * 
 * An {@link Stream} that gets elements from an iterator. This Stream can not be
 * iterated more than once
 * 
 * @author flbulgarelli
 * @param <A>
 *          element type
 */
public class IteratorStream<A> extends AbstractStream<A> {

  private final Thriterator<A> iterator;

  /**
   * 
   * Creates a new {@link IteratorStream}
   * 
   * @param iterator
   *          the iterator to wrap
   */
  public IteratorStream(@NonNull Iterator<? extends A> iterator) {
    this.iterator = Thriterators.from(iterator);
  }

  public final Thriterator<A> iterator() {
    return iterator;
  }

  public final Tuple2<A, Stream<A>> decons() {
    checkNotEmpty(this);
    return _(head(), (Stream<A>) this);
  }
}
