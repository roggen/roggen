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


package net.sf.staccatocommons.collections.stream.internal.algorithms;

import java.util.List;

import net.sf.staccatocommons.collections.stream.AbstractStream;
import net.sf.staccatocommons.collections.stream.Stream;
import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.defs.tuple.Tuple2;
import net.sf.staccatocommons.iterators.thriter.Thriterator;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 */
public abstract class AbstractTransformStream<A, B> extends AbstractStream<B> {

  private final Stream<A> stream;
  private Stream<B> streamCached;

  /**
   * Creates a new {@link AbstractTransformStream}
   */
  public AbstractTransformStream(@NonNull Stream<A> stream) {
    this.stream = stream;
  }

  @Override
  public final Thriterator<B> iterator() {
    return applyCached().iterator();
  }

  @Override
  public final B get(int n) {
    return applyCached().get(n);
  }

  @Override
  public final Tuple2<Thunk<B>, Stream<B>> delayedDecons() {
    return applyCached().delayedDecons();
  }

  @Override
  public final boolean isEmpty() {
    return applyCached().isEmpty();
  }

  public final int size() {
    return applyCached().size();
  }

  @Override
  public final List<B> toList() {
    return applyCached().toList();
  }

  private Stream<B> applyCached() {
    if (streamCached == null)
      streamCached = apply();
    return streamCached;
  }

  protected abstract Stream<B> apply();

  /**
   * @return the stream
   */
  protected Stream<A> getStream() {
    return stream;
  }

}
