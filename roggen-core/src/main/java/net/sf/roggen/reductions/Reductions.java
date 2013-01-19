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


package net.sf.roggen.reductions;

import java.util.Comparator;
import java.util.List;

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.Applicable2;
import net.sf.roggen.defs.Executable;
import net.sf.roggen.defs.reduction.Accumulator;
import net.sf.roggen.defs.reduction.Reduction;
import net.sf.roggen.defs.type.NumberType;
import net.sf.roggen.lang.Compare;
import net.sf.roggen.reductions.internal.Append;
import net.sf.roggen.reductions.internal.Count;
import net.sf.roggen.reductions.internal.Foldl;
import net.sf.roggen.reductions.internal.Foldl1;
import net.sf.roggen.reductions.internal.Sum;
import net.sf.roggen.restrictions.Constant;
import net.sf.roggen.restrictions.check.NonNull;
import net.sf.roggen.restrictions.processing.IgnoreRestrictions;

/**
 * Common {@link Reduction}s modeled after <a
 * href="http://en.wikipedia.org/wiki/Aggregate_function">SQL aggregate
 * functions</a>
 * 
 * @author flbulgarelli
 * @since 1.2
 */
public class Reductions {

  /**
   * Answers a reduction that counts elements it processes
   * 
   * @return a {@link Constant} count reduction
   */
  @Constant
  public static <A> Reduction<A, Integer> count() {
    return new Count<A>();
  }

  /**
   * Answers a function that appends to an unmodifiable list the elements it
   * processes.
   * 
   * @return a {@link Constant} append reduction
   */
  @Constant
  public static <A> Reduction<A, List<A>> append() {
    return new Append<A>();
  }

  /**
   * Integer reduction that computes the elements sum
   * 
   * @return the integer elements-sum reduction
   */
  @Constant
  public static Reduction<Integer, Integer> sum() {
    return new Sum();
  }

  /**
   * Generic reduction that computes the elements sum
   * 
   * @param numberType
   *          the type of numbers to be summed
   * @return the elements-sum reduction
   */
  public static <A> Reduction<A, A> sum(@NonNull NumberType<A> numberType) {
    return from(numberType.zero(), numberType.add());
  }

  /**
   * Integer reduction that computes the sum of the results of applying the
   * given function the elements it processes
   * 
   * For example:
   * 
   * <pre>
   * Reductions.sumOn(Thunks.&lt;Integer&gt; value())
   * </pre>
   * 
   * Is a reduction that sums the values of the thunks it processes
   * 
   * @param function
   * @return sum reduction composed with the given {@code function}
   */
  public static <A> Reduction<A, Integer> sumOf(Applicable<A, Integer> function) {
    return sum().of(function);
  }

  /**
   * Generic reduction that computes the sum of the results of applying the
   * given function the elements it processes
   * 
   * @param function
   * @param numberType
   *          the type of number to sum
   * @return sum reduction composed with the given {@code function}
   * @see #sumOf(Applicable)
   */
  public static <A, B> Reduction<A, B> sumOf(Applicable<A, B> function, NumberType<B> numberType) {
    return from(numberType.zero(), numberType.add().of(function).flip());
  }

  // /**
  // * Reduction that computes the first element
  // *
  // * @param <A>
  // * @return the first-element reduction
  // */
  // @Constant
  // public static <A> Reduction<A, A> first() {
  // return new AbstractReduction<A,A>() {
  // public A reduce(A accum, A element) {
  // return accum;
  // }
  // public Accumulator<A, A> start() {
  // // TODO Auto-generated method stub
  // return null;
  // }
  // };
  // }
  //
  // @Constant
  // public static <A> Reduction<A, A> last() {
  // return new AbstractReduction<A,A>() {
  // public A reduce(A accum, A element) {
  // return element;
  // }
  // public Accumulator<A, A> start() {
  // // TODO Auto-generated method stub
  // return null;
  // }
  // };
  // }

  /**
   * Answers reduction that computes the maximum element of the processed
   * elements
   * 
   * @return a reduction that computes the maximum element of the processed
   */
  @Constant
  public static <A extends Comparable<A>> Reduction<A, A> max() {
    return max(Compare.<A> natural());
  }

  /**
   * Answers reduction that computes the maximum element of the processed
   * elements, according to the given {@link Comparator}
   * 
   * @return a reduction that computes the maximum element of the processed
   */
  @IgnoreRestrictions
  public static <A> Reduction<A, A> max(@NonNull Comparator<A> comparator) {
    return from(Compare.max(comparator));
  }

  /**
   * Answers reduction that computes the maximum element according to the given
   * function
   * 
   * @return Reductions.max(Compare.on(function))
   * @see Compare#on(Applicable)
   */
  @IgnoreRestrictions
  public static <A, B extends Comparable<B>> Reduction<A, A> maxOn(
    @NonNull Applicable<A, B> function) {
    return max(Compare.on(function));
  }

  /**
   * Answers reduction that computes the minimum element of the processed
   * elements
   * 
   * @return a reduction that computes the minimum element of the processed
   */
  @Constant
  public static <A extends Comparable<A>> Reduction<A, A> min() {
    return min(Compare.<A> natural());
  }

  /**
   * Answers reduction that computes the minimum element according to the given
   * function
   * 
   * @return Reductions.min(Compare.on(function))
   * @see Compare#on(Applicable)
   */
  @IgnoreRestrictions
  public static <A> Reduction<A, A> min(Comparator<A> comparator) {
    return from(Compare.min(comparator));
  }

  /**
   * Answers reduction that computes the minimum element of the result of
   * applying the given function to the processed elements
   * 
   * @return a reduction that computes the minimum result of aplying the given
   *         {@code function} to each processed element
   * @see Compare#on(Applicable)
   */
  @IgnoreRestrictions
  public static <A, B extends Comparable<B>> Reduction<A, A> minOn(
    @NonNull Applicable<A, B> function) {
    return min(Compare.on(function));
  }

  /**
   * Answers a reduction that wraps the given folding with no initial element
   * 
   * @param function
   *          the folding
   * @return new {@link Reduction}
   */
  public static <A> Reduction<A, A> from(
    @NonNull final Applicable2<? super A, ? super A, ? extends A> function) {
    return new Foldl1<A>(function);
  }

  /**
   * Answers a reduction that wraps the given folding function with an initial
   * element
   * 
   * @param initial
   *          the initial element of the reduction
   * @param function
   *          the folding function
   * @return a new {@link Reduction}
   */
  public static <A, B> Reduction<A, B> from(final B initial,
    @NonNull final Applicable2<? super B, ? super A, ? extends B> function) {
    return new Foldl<A, B>(initial, function);
  }
  
  /**
   * Answers an executable that accumulates the given accumulator
   * @param accumularor
   * @return
   */
  public static <A, B> Executable<A> accumulate(final Accumulator<A, B> accumularor) {
    return new Executable<A>() {
      public void exec(A argument) {
        accumularor.accumulate(argument);
      }
    };
  }

}
