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


package net.sf.staccatocommons.collections.stream.internal;

import java.util.Collection;
import java.util.Deque;

import net.sf.staccatocommons.collections.iterable.internal.IterablesInternal;
import net.sf.staccatocommons.collections.stream.AbstractStream;
import net.sf.staccatocommons.collections.stream.Stream;
import net.sf.staccatocommons.iterators.thriter.Thriterator;
import net.sf.staccatocommons.iterators.thriter.Thriterators;

/**
 * @author flbulgarelli
 * 
 */
public class DequeStream<A> extends CollectionStream<A> {

  /**
   * Creates a new {@link DequeStream}
   */
  public DequeStream(Collection collection) {
    super(collection);
  }

  public Stream reverse() {
    return new AbstractStream<A>() {
      public Thriterator<A> iterator() {
        return Thriterators.from(getCollection().descendingIterator());
      }

      public boolean contains(A element) {
        return DequeStream.this.contains(element);
      }

      public boolean isEmpty() {
        return DequeStream.this.isEmpty();
      }

      public Stream<A> reverse() {
        return DequeStream.this;
      }
    };
  }

  public final A head() {
    IterablesInternal.checkNotEmpty(this);
    return getCollection().getFirst();
  }

  public final A last() {
    return getCollection().getLast();
  }

  protected Deque<A> getCollection() {
    return (Deque<A>) super.getCollection();
  }

}
