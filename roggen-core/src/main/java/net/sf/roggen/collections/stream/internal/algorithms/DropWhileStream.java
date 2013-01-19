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


package net.sf.roggen.collections.stream.internal.algorithms;

import net.sf.roggen.collections.stream.AbstractStream;
import net.sf.roggen.collections.stream.Stream;
import net.sf.roggen.defs.Evaluable;
import net.sf.roggen.iterators.PrependThriterator;
import net.sf.roggen.iterators.thriter.Thriterator;
import net.sf.roggen.iterators.thriter.Thriterators;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class DropWhileStream<A> extends AbstractStream<A> {
  private final Evaluable<? super A> predicate;
  private Stream<A> source;
  private Thriterator<A> iter;
  
  /**
   * Creates a new {@link TakeWhileStream}
   */
  public DropWhileStream(@NonNull Stream<A> stream, @NonNull Evaluable<? super A> predicate) {
    this.source = stream;
    this.predicate = predicate;
  }

  public Thriterator<A> iterator() {
    if (iter == null) {
      iter = source.iterator();
      A next = null;
      while (iter.hasNext() && predicate.eval(next = iter.next())) {
        next = null;
      }
      if (next == null)
        iter = Thriterators.from(iter);
      else
        iter = new PrependThriterator<A>(next, iter);
    }
    return iter;

  }
}