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

package net.sf.roggen.lang.predicate.internal;

import net.sf.roggen.defs.predicate.Predicate2;
import net.sf.roggen.restrictions.Constant;

/**
 * @author flbulgarelli
 * 
 * @param <A>
 */
public final class Compare2<A extends Comparable<A>> extends TopLevelPredicate2<A, A> {

  private static final long serialVersionUID = 2333778927729616411L;

  public boolean eval(A arg0, A arg1) {
    if (arg0 == arg1)
      return true;
    if (arg0 == null || arg1 == null)
      return false;
    return arg0.compareTo(arg1) == 0;
  }

  /**
   * @return a constant instance
   */
  @Constant
  public static <A extends Comparable<A>> Predicate2<A, A> compareTest() {
    return new Compare2();
  }
  
  public Predicate2<A, A> nullSafe() {
    return this;
  }
}