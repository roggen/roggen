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

/**
 * Interface for accessing the third element of a tuple
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 */

public interface ThirdAware<A> {
  /**
   * Answers the third component
   * 
   * @return the third component
   */
  A third();

  /**
   * 
   * Synonym for {@link #third()}
   * 
   * @return the third component
   */
  A _2();
}
