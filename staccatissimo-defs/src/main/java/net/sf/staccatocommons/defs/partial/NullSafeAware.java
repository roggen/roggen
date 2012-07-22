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


package net.sf.staccatocommons.defs.partial;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.NullSafe;
import net.sf.staccatocommons.restrictions.FunctionLikeObject;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * A {@link NullSafeAware}s are object that may be converted to an
 * {@link FunctionLikeObject} object of type {@code A} that is {@link NullSafe}, that
 * is, that accepts nulls in its applicative method.
 * 
 * {@link NullSafeAware}s parameterized by type {@code A}
 * <strong>should</strong> be of type {@code A} too.
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 */
public interface NullSafeAware<A> {

  /**
   * Answers a new {@link FunctionLikeObject} of type {@code A} that accepts nulls for
   * its applicative method, that is, it will not throw any exception if any of
   * its arguments is null.
   * 
   * The return value of the applicative method of the returned object for a
   * null input is not specified by this interface. In particular, it
   * <strong>may</strong> be <code>null</code> in such cases.
   * 
   * @return a new null-safe {@link Applicable} of type {@code A}. Non null.
   */
  @NonNull
  @NullSafe
  A nullSafe();

}