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

import java.util.List;

import net.sf.roggen.collections.stream.Stream;
import net.sf.roggen.restrictions.check.NonNull;
import net.sf.roggen.restrictions.check.NotNegative;
import net.sf.roggen.restrictions.processing.EnforceRestrictions;
import net.sf.roggen.restrictions.processing.IgnoreRestrictions;

/**
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 */
@EnforceRestrictions
public class ListStream<A> extends CollectionStream<A> {

  /**
   * Creates a new {@link ListStream}
   * 
   * @param iterable
   *          the list to wrap
   */
  @IgnoreRestrictions
  public ListStream(@NonNull List<? extends A> iterable) {
    super(iterable);
  }

  @Override
  public final A get(int n) {
    return getList().get(n);
  }

  @Override
  public final int indexOf(A element) {
    return getList().indexOf(element);
  }

  protected List<A> getList() {
    return (List<A>) getCollection();
  }

  @Override
  public A last() {
    return get(size() - 1);
  }

  
  public Stream<A> take(@NotNegative int amountOfElements) {
    return new ListStream<A>(getList().subList(0, atMost(amountOfElements)));
  }

  public Stream<A> drop(@NotNegative int amountOfElements) {
    return new ListStream<A>(getList().subList(atMost(amountOfElements), size()));
  }

}