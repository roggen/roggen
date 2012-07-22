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

package net.sf.staccatocommons.lang.function;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.Applicable2;
import net.sf.staccatocommons.defs.Executable;
import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.function.Function2;
import net.sf.staccatocommons.lang.function.internal.ApplicableFunction;
import net.sf.staccatocommons.lang.function.internal.ConstantFunction;
import net.sf.staccatocommons.lang.function.internal.IdentityFunction;
import net.sf.staccatocommons.restrictions.Constant;
import net.sf.staccatocommons.restrictions.SideEffectFree;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * Class factory methods for some common {@link Function}s
 * 
 * @author flbulgarelli
 */
public class Functions {

  private Functions() {}

  /**
   * Converts the given {@link Applicable} into a {@link Function} by casting
   * it, is possible, or creating a new function that delegates its apply method
   * to it.
   * 
   * @param <A>
   * @param <B>
   * @param applicable
   *          the {@link Applicable} to convert
   * @return new a function that applies the given {@link Applicable}, if it is
   *         not already a {@link Function}, or the given
   *         <code>applicable</code> casted to {@link Function}, otherwise
   */
  @NonNull
  public static <A, B> Function<A, B> from(@NonNull Applicable<? super A, ? extends B> applicable) {
    if (applicable instanceof Function)
      return (Function<A, B>) applicable;
    return new ApplicableFunction<A, B>(applicable);
  }

  /**
   * Converts the given {@link Applicable2} into a {@link Function2} by casting
   * it, is possible, or creating a new function that delegates its apply method
   * to it.
   * 
   * @param <A>
   * @param <B>
   * @param <C>
   * @param applicable
   *          the {@link Applicable} to convert
   * @return new a function that applies the given {@link Applicable2}, if it is
   *         not already a {@link Function2}, or the given
   *         <code>applicable</code> casted to {@link Function2}, otherwise
   */
  @NonNull
  public static <A, B, C> Function2<A, B, C> from(
    @NonNull final Applicable2<? super A, ? super B, ? extends C> applicable) {
    if (applicable instanceof Function2)
      return (Function2<A, B, C>) applicable;
    return new AbstractFunction2<A, B, C>() {
      public C apply(A arg0, B arg1) {
        return applicable.apply(arg0, arg1);
      }
    };
  }

  /**
   * Returns the identity function, that is, a {@link Function} that takes an
   * argument and returns it. This functions grants to be {@link SideEffectFree}
   * 
   * @param <A>
   * @return the constant identity function
   */
  @Constant
  @SideEffectFree
  public static <A> Function<A, A> identity() {
    return IdentityFunction.identity();
  }

  /**
   * Returns a function that takes one argument, and regardless of it, returns a
   * given value. This function grants to be {@link SideEffectFree} and
   * {@link Constant}
   * 
   * @param <A>
   * @param <B>
   * @param value
   *          the value the function will return when applied
   * @return a new function
   */
  @SideEffectFree
  public static <A, B> Function<A, B> constant(B value) {
    return new ConstantFunction<A, B>(value);
  }

  /**
   * Returns a function that takes one argument, and regadless of it, returns
   * the given thunk's value.
   * <p>
   * This function grants to be {@link SideEffectFree} and {@link Constant} only as
   * long as the given {@code thunk} is transparent too. As a consequence,
   * passing a non-constant {@link Thunk} may be effective, but
   * counterintuitive, as the resulting function would be impure and not
   * constant at all.
   * </p>
   * 
   * @param <A>
   * @param <B>
   * @param thunk
   * @return a new function
   */
  @NonNull
  public static <A, B> Function<A, B> constant(@NonNull final Thunk<B> thunk) {
    return new AbstractFunction<A, B>() {
      public B apply(A arg) {
        return thunk.value();
      }
    };
  }

  /**
   * Answers an impure - with side effect - function that executes the given
   * block and answers its argument
   * 
   * @param <A>
   * @param block
   *          the block to wrap
   * @return a new, impure, function.
   */
  public static <A> Function<A, A> impure(@NonNull final Executable<? super A> block) {
    return new AbstractFunction<A, A>() {
      public A apply(A arg) {
        block.exec(arg);
        return arg;
      }
    };
  }

  /**
   * Returns a function that returns the result of sending
   * {@link Object#toString()} to its argument
   * 
   * @param <A>
   * @return a function that returns <code>arg.toString()</code>
   * @since 2.2
   */
  @Constant
  public static <A> Function<A, String> toString_() {
    return new AbstractFunction<A, String>() {
      public String apply(A arg) {
        return arg.toString();
      }
    };
  }

  /**
   * Returns a function that returns the class of its argument.
   * 
   * @param <A>
   * @return a function that returns <code>{@code argument.getClass()}</code>
   * @since 2.2
   */
  @Constant
  public static <A> Function<A, Class<A>> getClass_() {
    return new AbstractFunction<A, Class<A>>() {
      public Class<A> apply(A arg) {
        return (Class) arg.getClass();
      }
    };
  }

}
