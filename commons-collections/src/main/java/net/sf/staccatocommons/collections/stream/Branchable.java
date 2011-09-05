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
package net.sf.staccatocommons.collections.stream;

import net.sf.staccatocommons.collections.restrictions.Projection;
import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.lang.tuple.Pair;

/**
 * @author flbulgarelli
 * @since 1.2
 */
public interface Branchable<A> {

  @Projection
  <B> Stream<Pair<A, B>> clone(Applicable<? super A, ? extends B> function);

  /**
   * Answers a Stream of pairs, where each one contains both results of applying
   * the given functions. Equivalent to
   * <code>this.map(Tuples.branch(function0, function1))</code>
   * 
   * @param <B>
   * @param <C>
   * @param function0
   * @param function1
   * @return a new {@link Stream}
   * @since 1.2
   */
  @Projection
  <B, C> Stream<Pair<B, C>> branch(Applicable<? super A, ? extends B> function0,
    Applicable<? super A, ? extends C> function1);

}