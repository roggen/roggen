/*
 Copyright (c) 2011, The Staccato-Commons Team

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; version 3 of the License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 */
package net.sf.staccatocommons.collections.stream.impl.internal.delayed;

import static net.sf.staccatocommons.lang.tuple.Tuples._;
import net.sf.staccatocommons.collections.stream.Stream;
import net.sf.staccatocommons.collections.stream.Streams;
import net.sf.staccatocommons.collections.stream.impl.internal.PrependStream;
import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.iterators.delayed.DelayedPrependIterator;
import net.sf.staccatocommons.iterators.thriter.Thriterator;
import net.sf.staccatocommons.lang.tuple.Pair;
import net.sf.staccatocommons.restrictions.check.NonNull;

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
  public Pair<Thunk<A>, Stream<A>> delayedDecons() {
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
