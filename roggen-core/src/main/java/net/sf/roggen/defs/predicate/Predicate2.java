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


package net.sf.roggen.defs.predicate;

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.Applicable2;
import net.sf.roggen.defs.Evaluable2;
import net.sf.roggen.defs.NullSafe;
import net.sf.roggen.defs.partial.NullSafeAware;
import net.sf.roggen.defs.tuple.Tuple2;
import net.sf.roggen.restrictions.FunctionLikeObject;
import net.sf.roggen.restrictions.ValueObject;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * A rich {@link Evaluable2}
 * 
 * @author flbulgarelli
 */
@ValueObject
@FunctionLikeObject
public interface Predicate2<A, B> extends Evaluable2<A, B>, Applicable2<A, B, Boolean>,
  Applicable<A, Predicate<B>>, NullSafeAware<Predicate2<A, B>> {

  /**
   * Negates this {@link Predicate}
   * 
   * @return a {@link Predicate2} that negates this {@link Predicate2}'s result.
   */
  Predicate2<A, B> not();

  /**
   * Returns a predicate that, performs a short-circuit logical-or between this
   * {@link Predicate2}'s {@link #eval(Object,Object)} and other
   * 
   * @param other
   *          another {@link Evaluable2}. Non null.
   * @return A new predicate that performs the short circuited or between this
   *         and other when evaluated.
   */
  Predicate2<A, B> or(@NonNull final Evaluable2<? super A, ? super B> other);

  /**
   * Returns a predicate that performs a short-circuit logical-and between this
   * {@link Predicate2}'s {@link #eval(Object,Object)} and other
   * 
   * @param other
   *          another {@link Evaluable2}. Non null.
   * @return A new predicate that performs the short circuited logical-and
   *         between this and other when evaluated. Non Null
   */
  Predicate2<A, B> and(@NonNull final Evaluable2<? super A, ? super B> other);

  /**
   * Answers a {@link Predicate2} that returns <code>true</code> if both
   * arguments are <code>null</code>, <code>false</code> if only one of them is
   * <code>null</code>, or evalutes this predicate, otherwise.
   */
  @NullSafe
  Predicate2<A, B> nullSafe();

  /**
   * <a href="http://en.wikipedia.org/wiki/Currying">Uncurries</a> this
   * predicate, by returning a {@link Predicate} that takes a single pair, being
   * its components each of the original predicate parameters
   * 
   * @return a new {@link Predicate}
   */
  Predicate<Tuple2<A, B>> uncurry();

}
