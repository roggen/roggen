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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.sf.roggen.collections.iterable.Iterables;
import net.sf.roggen.iterators.thriter.Thriterator;
import net.sf.roggen.iterators.thriter.Thriterators;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 */
public class CollectionStream<A> extends StrictStream<A> {

  private final Collection<? extends A> collection;

  /**
   * Creates a new {@link CollectionStream}
   * 
   * @param collection
   *          the collection to wrap
   */
  public CollectionStream(@NonNull Collection<? extends A> collection) {
    this.collection = collection;
  }

  @Override
  public final int size() {
    return collection.size();
  }

  @Override
  public final boolean contains(A element) {
    return collection.contains(element);
  }

  @Override
  public boolean isEmpty() {
    return collection.isEmpty();
  }

  @Override
  public final Thriterator<A> iterator() {
    return Thriterators.from(collection.iterator());
  }

  @Override
  public List<A> toList() {
    return Iterables.toList(getCollection());
  }

  public Set<A> toSet() {
    return Iterables.toSet(getCollection());
  }

  @Override
  public A[] toArray(Class<? super A> clazz) {
    return toArray(clazz, getCollection());
  }

  protected Collection<A> getCollection() {
    return (Collection<A>) collection;
  }

}