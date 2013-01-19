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

package net.sf.staccatocommons.defs;

import net.sf.staccatocommons.restrictions.FunctionLikeObject;
import net.sf.staccatocommons.restrictions.ValueObject;


/**
 * {@link Applicable2}s are transformations that take two arguments and have a
 * return value, by implementing an {@link #apply(Object, Object)} method
 * 
 * @author flbulgarelli
 * 
 * @param <T1>
 *          first argument type
 * @param <T2>
 *          second argument type
 * @param <R>
 *          return type
 * @see FunctionLikeObject Recomendations for implementing
 */
@ValueObject
@FunctionLikeObject
public interface Applicable2<T1, T2, R> {

  /**
   * Performs a transformation on the given element, and returns its result.
   * This method <strong>should not</strong> have side effects
   * 
   * @param arg0
   *          the first transformation argument
   * @param arg1
   *          the second transformation argument
   * @return the transformation result
   */
  R apply(T1 arg0, T2 arg1);

}