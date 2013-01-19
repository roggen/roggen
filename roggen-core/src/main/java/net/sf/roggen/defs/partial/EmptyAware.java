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

package net.sf.roggen.defs.partial;

import net.sf.roggen.restrictions.SideEffectFree;

/**
 * Interface for objects that understand {@link #isEmpty()} message.
 * 
 * @author flbulgarelli
 * 
 */
public interface EmptyAware {

  /**
   * Answers if this {@link EmptyAware} is empty. This message
   * <strong>should</strong> be {@link SideEffectFree}
   * 
   * @return if the object is empty.
   */
  boolean isEmpty();

}
