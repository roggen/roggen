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

package net.sf.staccatocommons.defs.reduction;

import net.sf.staccatocommons.defs.Thunk;

/**
 * An imperative accumulator. See {@link Reduction} for more details.
 * 
 * @author flbulgarelli
 * @param <A>
 *          the type of the input accumulated value
 * @param <B>
 *          the type of the output accumulated value
 * @since 1.2
 */
public interface Accumulator<A, B> extends Thunk<B> {

  /**
   * Adds an element to this accumulator
   * 
   * @param element
   *          the element to add.
   */
  void accumulate(A element);

  /**
   * Answers the accumulated value
   */
  B value();

}
