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


package net.sf.roggen.defs.function;

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.Applicable2;
import net.sf.roggen.defs.Delayable2;
import net.sf.roggen.defs.NullSafe;
import net.sf.roggen.defs.partial.NullSafeAware;
import net.sf.roggen.defs.tuple.Tuple2;
import net.sf.roggen.restrictions.FunctionLikeObject;
import net.sf.roggen.restrictions.ValueObject;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * {@link Function2}s are rich interfaced {@link Applicable2}s - two arguments
 * {@link Delayable2} and {@link NullSafeAware} transformations.
 * 
 * 
 * {@link Function2} can also be <a
 * href="http://en.wikipedia.org/wiki/Partial_application">partially
 * applied</a>, which means, applying it with less arguments than required,
 * returning, instead of the result of the transformation, a new function that
 * expects the rest of the arguments. Thus, {@link Function2} do also implement
 * {@link Applicable}
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 *          function first argument type
 * @param <B>
 *          function second argument type
 * @param <C>
 *          function return type
 * 
 */
@ValueObject
@FunctionLikeObject
public interface Function2<A, B, C> extends Applicable2<A, B, C>, Applicable<A, Function<B, C>>, Delayable2<A, B, C>,
  NullSafeAware<Function2<A, B, C>> {

  /**
   * Partially applies the function passing just its first parameter
   */
  Function<B, C> apply(final A arg0);

  /**
   * Applies the function
   */
  C apply(A arg0, B arg1);

  /**
   * Inverts function parameters order
   * 
   * @return a new {@link Function2} that produces the same result of this one
   *         when applied, but with arguments flipped
   */
  Function2<B, A, C> flip();

  /**
   * Answers a new function that returns null if any of its arguments is null,
   * or the result of applying this function, otherwise.
   * 
   * @return a new null-safe {@link Function2}
   */
  @NullSafe
  Function2<A, B, C> nullSafe();

  /**
   * 
   * @param <D>
   * @param function
   * @return
   * @since 1.2
   */
  <D> Function2<D, B, C> of(Applicable<? super D, ? extends A> function);

  /**
   * Function composition, like {@link Function#of(Applicable2)}, but with
   * receptor and argument interchanged. Equivalent to {@code other.of(this)}
   * 
   * Functions get combined in the following figure:
   * 
   * <pre>
   * >----+
   *      +--this---+---other---->
   * >----+
   * </pre>
   * 
   * @param <C>
   * @param other
   * @return a new {@link Function2}
   * @since 1.2
   */
  <D> Function2<A, B, D> then(@NonNull Function<? super C, ? extends D> other);

  /**
   * Answers a three arg function that combines <code>this</code> function with
   * <code>other</code> function, using a <code>binaryFunction</code> to merge
   * the results.
   * 
   * The answered {@link Function2} will apply this function to its first and
   * second argument argument, <code>other</code> function to the third
   * argument, and return the application of <code>binaryFunction</code> to both
   * resulting values.
   * 
   * Functions get combined in the following figure:
   * 
   * <pre>
   * >----+
   *      +--this-+
   * >----+       +---binaryFunction---->
   *              |
   * >--other-----+
   * </pre>
   * 
   * @param <A2>
   * @param <B2>
   * @param <C>
   * @param binayFunction
   * @param other
   * @return a new {@link Function3} the merges {@code this} and {@code other}
   *         using the {@code binaryFunciton}
   * @since 1.2
   */
  <A2, B2, D> Function3<A, B, A2, D> then(Function2<C, B2, D> binaryFunction,
    @NonNull Function<? super A2, ? extends B2> other);
  
  
  /**
   * <a href="http://en.wikipedia.org/wiki/Currying">Uncurries</a> this function,
   * by returning a {@link Function} that takes a
   * single pair, being its components each of the original function parameters
   * 
   * @return a new {@link Function}
   */
  Function<Tuple2<A, B>, C> uncurry();

}
