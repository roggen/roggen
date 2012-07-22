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


package net.sf.staccatocommons.defs.reduction;

import java.io.InputStream;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.restrictions.check.NonNull;
import net.sf.staccatocommons.restrictions.effect.SideEffectFree;

/**
 * A {@link Reduction} are aggregate-functions-like objects that encapsulates a
 * computation with effect that processes each element of a sequence of objects,
 * calculating intermediate, accumulative results. The last accumulation is the
 * final result of the reduction over such sequence.
 * <p>
 * Reductions can be applied to potentially any sequence of objects:
 * {@link Iterables}, {@link InputStream}s, etc.
 * </p>
 * <p>
 * Although {@link Reduction}s implement aggregation in an impure manner - its
 * {@link #newAccumulator()} message will return a mutable {@link Accumulator} -
 * they are sharable, since {@link Reduction} itself are stateless
 * 
 * @author flbulgarelli
 * @since 1.2
 * @param <B>
 * @param <A>
 * 
 * @see SideEffectFree
 * @see Function
 */
public interface Reduction<A, B> {

  /**
   * Answers a new, reseted accumulator to perform the reduction
   * 
   * @return a new, non null, {@link Accumulator}
   */
  @NonNull
  Accumulator<A, B> newAccumulator();

  /**
   * Composes the given function with this reduction, by returning a new
   * {@link Reduction} that applies the {@code function} to the resulting
   * element of the reduction
   * 
   * For example, the following code:
   * 
   * <pre>
   * Reductions.&lt;Strin&gt; max().then(Strings.length())
   * </pre>
   * 
   * will return a reduction that computes the length of the maximum -
   * lexicographically - string it processes:
   * 
   * <pre>
   *  accumulator = lengthOfMax.newAccumulator();
   *  accumulator.accumulate("aaa");
   *  accumulator.accumulate("bbbbb");
   *  accumulator.accumulate("dd");
   *  
   *  accumulator.value() // 2, because length of "dd", the max string,  is 2
   * </pre>
   * 
   * @param function
   * @return a new reduction
   */
  <D> Reduction<A, D> then(Applicable<B, D> function);

  /**
   * Composes this reduction with a function, by returning a new
   * {@link Reduction} that will apply {@code this} reduction to the results of
   * applying the given {@code function} to each element
   * 
   * For example, the following code:
   * 
   * <pre>
   * Reductions.&lt;Integer&gt; max().of(Strings.length())
   * </pre>
   * 
   * will return a reduction that computes the maximum length of the strings it
   * processes:
   * 
   * <pre>
   *  accumulator = maxLength.newAccumulator();
   *  accumulator.accumulate("aaa");
   *  accumulator.accumulate("bbbbb");
   *  accumulator.accumulate("dd");
   *  
   *  accumulator.value() // 5, because "bbbbb" has the max length, which is 5
   * </pre>
   * 
   * 
   * @param function
   * @return a new reduction
   */
  <D> Reduction<D, B> of(Applicable<D, A> function);

}