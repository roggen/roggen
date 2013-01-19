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

package net.sf.staccatocommons.lang;

import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * Class methods to operate with nulls
 * 
 * @author flbulgarelli
 * 
 */
public class Nulls {

  /**
   * Returns the given value, if non null, or other, otherwise.
   * 
   * @param <T>
   * @param value
   *          nullable.
   * @param other
   *          nullable, although it has little sense to be be null
   * @return <code> value != null ? value : other</code>
   */
  public static <T> T coalesce(T value, T other) {
    return value != null ? value : other;
  }

  /**
   * Returns the given value, if non null, or the value provided by the given
   * {@link Thunk}, otherwise.
   * 
   * @param <T>
   * @param value
   *          nullable.
   * @param thunk
   *          non null.
   * @return <code>value != null ? value : thunk.value()</code>
   */
  public static <T> T coalesce(T value, @NonNull Thunk<T> thunk) {
    return value != null ? value : thunk.value();
  }
}
