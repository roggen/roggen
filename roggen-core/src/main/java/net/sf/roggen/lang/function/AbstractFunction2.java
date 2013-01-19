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

package net.sf.roggen.lang.function;

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.function.Function;
import net.sf.roggen.defs.function.Function2;
import net.sf.roggen.defs.function.Function3;
import net.sf.roggen.defs.tuple.Tuple2;
import net.sf.roggen.lang.SoftException;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 *          function first argument type
 * @param <B>
 *          function second argument type
 * @param <C>
 *          function return type
 */
public abstract class AbstractFunction2<A, B, C> extends AbstractDelayable2<A, B, C> implements
  Function2<A, B, C> {

  @NonNull
  public Function<B, C> apply(final A arg0) {
    return new AbstractFunction<B, C>() {
      public C apply(B arg1) {
        return AbstractFunction2.this.apply(arg0, arg1);
      }
    };
  }

  public Function2<B, A, C> flip() {
    return new AbstractFunction2<B, A, C>() {
      public C apply(B arg1, A arg0) {
        return AbstractFunction2.this.apply(arg0, arg1);
      }
    };
  }

  public final Function2<A, B, C> nullSafe() {
    return new AbstractFunction2<A, B, C>() {
      public C apply(A arg0, B arg1) {
        if (arg0 == null || arg1 == null)
          return null;
        return AbstractFunction2.this.apply(arg0, arg1);
      }
    };
  }

  public <D> Function2<D, B, C> of(@NonNull final Applicable<? super D, ? extends A> function) {
    return new AbstractFunction2<D, B, C>() {
      public C apply(D arg0, B arg1) {
        return AbstractFunction2.this.apply(function.apply(arg0), arg1);
      }
    };
  }

  public <A2, B2, D> Function3<A, B, A2, D> then(@NonNull final Function2<C, B2, D> combinator,
    @NonNull final Function<? super A2, ? extends B2> other) {
    return new AbstractFunction3<A, B, A2, D>() {
      public D apply(A arg0, B arg1, A2 arg2) {
        return combinator.apply(AbstractFunction2.this.apply(arg0, arg1), other.apply(arg2));
      }
    };
  }

  public <D> Function2<A, B, D> then(Function<? super C, ? extends D> other) {
    return (Function2<A, B, D>) other.of(this);
  }

  public Function<Tuple2<A, B>, C> uncurry() {
    return new AbstractFunction<Tuple2<A, B>, C>() {
      public C apply(Tuple2<A, B> argument) {
        return AbstractFunction2.this.apply(argument.first(), argument.second());
      }
    };
  }

  public String toString() {
    return "Function2";
  }

  /**
   * {@link AbstractFunction2} that handles exceptions by softening them using
   * {@link SoftException#soften(Throwable)}
   * 
   * @author flbulgarelli
   * 
   * @param <A>
   *          function first argument type
   * @param <B>
   *          function second argument type
   * @param <C>
   *          function return type
   */
  public abstract static class Soft<A, B, C> extends AbstractFunction2<A, B, C> {

    public final C apply(A arg0, B arg1) {
      try {
        return softApply(arg0, arg1);
      } catch (Throwable e) {
        throw SoftException.soften(e);
      }
    }

    /**
     * Applies this function, potentially throwing a checked exception
     * 
     * @see Function2#apply(Object, Object)
     */
    protected abstract C softApply(A arg0, B arg1) throws Throwable;
  }

}