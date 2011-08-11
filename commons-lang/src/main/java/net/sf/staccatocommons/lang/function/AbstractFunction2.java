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
package net.sf.staccatocommons.lang.function;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.function.Function2;
import net.sf.staccatocommons.restrictions.check.NonNull;
import net.sf.staccatocommons.restrictions.processing.ForceRestrictions;

/**
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 * @param <B>
 * @param <C>
 */
public abstract class AbstractFunction2<A, B, C> extends AbstractDelayable2<A, B, C> implements Function2<A, B, C> {

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

  @ForceRestrictions
  public <D> Function2<D, B, C> of(@NonNull final Applicable<? super D, ? extends A> function) {
    return new AbstractFunction2<D, B, C>() {
      public C apply(D arg0, B arg1) {
        return AbstractFunction2.this.apply(function.apply(arg0), arg1);
      }
    };
  }

  public String toString() {
    return "Function2";
  }

}