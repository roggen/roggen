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


package net.sf.roggen.collections.stream.internal.algorithms.delayed;

import static net.sf.roggen.lang.tuple.Tuples.*;
import net.sf.roggen.collections.stream.Stream;
import net.sf.roggen.collections.stream.Streams;
import net.sf.roggen.collections.stream.internal.algorithms.PrependStream;
import net.sf.roggen.defs.Thunk;
import net.sf.roggen.defs.tuple.Tuple2;
import net.sf.roggen.iterators.delayed.DelayedPrependIterator;
import net.sf.roggen.iterators.thriter.Thriterator;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class DelayedPrependStream<A> extends PrependStream<A> {

  /**
   * Creates a new {@link DelayedPrependStream}
   */
  public DelayedPrependStream(@NonNull Thunk<A> head, @NonNull Stream<A> tail) {
    super((A) head, tail);
  }

  @Override
  public Thriterator<A> iterator() {
    return new DelayedPrependIterator<A>(headThunk(), tailIterator());
  }

  @Override
  public Tuple2<Thunk<A>, Stream<A>> delayedDecons() {
    return _(headThunk(), Streams.from(tail()));
  }

  @Override
  public A head() {
    return headThunk().value();
  }

  protected final Thunk<A> headThunk() {
    return (Thunk<A>) super.head();
  }
}
