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


package net.sf.roggen.defs;

import net.sf.roggen.restrictions.FunctionLikeObject;
import net.sf.roggen.restrictions.ValueObject;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * {@link Delayable2}s are delayed transformations that two arguments and return
 * a thunk that will perform the actual processing when evaluated, by
 * implementing a {@link #delayed(Object, Object)} method.
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 *          first argument type
 * @param <B>
 *          second argument type
 * @param <C>
 *          type of returned thunk
 * @see FunctionLikeObject Recomendations for implementing
 * @see Thunk
 */
@ValueObject
@FunctionLikeObject
public interface Delayable2<A, B, C> {

  /**
   * Asynchronously applies this {@link Delayable2}, by returning a
   * {@link Thunk} that will perform the actual transformation each time it is
   * evaluated.
   * 
   * @param arg0
   * @param arg1
   * @return a new {@link Thunk}. Non null.
   */
  @NonNull
  Thunk<C> delayed(final A arg0, final B arg1);

  /**
   * Asynchronously applies this {@link Delayable2}, by returning a
   * {@link Thunk} that will perform the actual transformation on the given
   * thunk's values each time it is evaluated.
   * 
   * @param thunk0
   * @param thunk1
   * @return a new {@link Thunk}.
   */
  @NonNull
  Thunk<C> delayedValue(@NonNull Thunk<A> thunk0, @NonNull Thunk<B> thunk1);

}
