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

package net.sf.staccatocommons.lang.function.internal;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.Applicable2;
import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.function.Function2;
import net.sf.staccatocommons.lang.function.Functions;
import net.sf.staccatocommons.lang.thunk.Thunks;
import net.sf.staccatocommons.restrictions.Constant;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 * @param <A>
 */
public final class IdentityFunction<A> extends TopLevelFunction<A, A> {

  private static final long serialVersionUID = -9042770205177366369L;

  @Override
  public A apply(A argument) {
    return argument;
  }

  @Override
  @NonNull
  public Function<A, A> nullSafe() {
    return this;
  }

  @NonNull
  @Override
  public Thunk<A> delayed(A arg) {
    return Thunks.constant(arg);
  }

  @NonNull
  @Override
  public Thunk<A> delayedValue(Thunk<? extends A> thunk) {
    return (Thunk<A>) thunk;
  }

  @NonNull
  @Override
  public <Tp1, Tp2> Function2<Tp1, Tp2, A> of(Applicable2<Tp1, Tp2, ? extends A> other) {
    return Functions.from(other);
  }

  @NonNull
  @Override
  public <C> Function<C, A> of(Applicable<? super C, ? extends A> other) {
    return Functions.from(other);
  }

  /**
   * @param <I>
   * @return a constant instance
   */
  @Constant
  public static <I> Function<I, I> identity() {
    return new IdentityFunction();
  }
  
  @Override
  public boolean isIdentity() {
    return true;
  }

}