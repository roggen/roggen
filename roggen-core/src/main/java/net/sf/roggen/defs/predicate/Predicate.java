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
import net.sf.roggen.defs.Evaluable;
import net.sf.roggen.defs.Executable;
import net.sf.roggen.defs.NullSafe;
import net.sf.roggen.restrictions.FunctionLikeObject;
import net.sf.roggen.restrictions.ValueObject;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * A rich {@link Evaluable}
 * 
 * @author flbulgarelli
 */
@ValueObject
@FunctionLikeObject
public interface Predicate<A> extends Evaluable<A>, Applicable<A, Boolean> {

  /**
   * Negates this {@link Predicate}
   * 
   * @return a {@link Predicate} that negates this {@link Predicate}'s result.
   */
  Predicate<A> not();

  /**
   * Returns a predicate that, performs a short-circuit logical-or between this
   * {@link Predicate}'s {@link #eval(Object)} and other
   * 
   * @param other
   *          another {@link Evaluable}. Non null.
   * @return A new predicate that performs the short circuited or between this
   *         and other when evaluated.
   */
  Predicate<A> or(@NonNull final Evaluable<? super A> other);

  /**
   * Returns a predicate that performs a short-circuit logical-and between this
   * {@link Predicate}'s {@link #eval(Object)} and other
   * 
   * @param other
   *          another {@link Evaluable}. Non null.
   * @return A new predicate that performs the short circuited logical-and
   *         between this and other when evaluated. Non Null
   */
  Predicate<A> and(@NonNull final Evaluable<? super A> other);

  /**
   * Returns a null-safe predicate that, when evaluated, answers
   * <code>false</code> if its argument is null, or evaluates this predicate,
   * otherwise.
   * 
   * @return a predicate that returns <code>arg != null && this.eval(arg)</code>
   */
  @NullSafe
  Predicate<A> andNotNull();

  /**
   * Returns a null-safe predicate that, when evaluated, answers
   * <code>true</code> if its argument is null, or evaluates this predicate,
   * otherwise.
   * 
   * @return a predicate that returns <code>arg == null || this.eval(arg)</code>
   */
  @NullSafe
  Predicate<A> orNull();

  // /**
  // * Adds a side effect to this predicate, that will be evaluated whenever the
  // * predicate is evaluated.
  // *
  // * @param executable
  // * @return a new Predicate that adds an {@link Executable} effect to this
  // */
  // Predicate<A> withEffect(Executable<A> executable);

  /**
   * Adds a side effect to this predicate, that will be evaluated whenever the
   * predicate evaluation is <code>true</code>
   * 
   * @param executable
   * @return a new Predicate that adds an {@link Executable} effect to this
   */
  Predicate<A> withEffectOnTrue(Executable<A> executable);

  /**
   * Adds a a side effect to this predicate, that will be evaluated whenever the
   * predicate evaluation is <code>false</code>
   * 
   * @param executable
   * @return a new Predicate that adds an {@link Executable} effect to this
   */
  Predicate<A> withEffectOnFalse(Executable<A> executable);

  /**
   * <a href="http://en.wikipedia.org/wiki/Function_composition">Composes</a>
   * this predicate with another {@link Applicable}, resulting in a new
   * {@link Predicate} that when applied returns
   * <code>this.eval(other.apply(arg)</code>
   * 
   * @param <B>
   * @param other
   * @return a new predicate, <code>this</code> composed with <code>other</code>
   */
  <B> Predicate<B> of(@NonNull final Applicable<? super B, ? extends A> other);
}