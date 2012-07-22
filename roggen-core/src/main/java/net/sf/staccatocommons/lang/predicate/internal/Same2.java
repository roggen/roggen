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


package net.sf.staccatocommons.lang.predicate.internal;

import net.sf.staccatocommons.defs.predicate.Predicate;
import net.sf.staccatocommons.defs.predicate.Predicate2;
import net.sf.staccatocommons.lang.predicate.Predicates;
import net.sf.staccatocommons.restrictions.Constant;

/**
 * @author flbulgarelli
 * 
 */
public final class Same2<A> extends TopLevelPredicate2<A, A> {

  private static final long serialVersionUID = 5767947639495599795L;

  public boolean eval(A arg0, A arg1) {
    return arg0 == arg1;
  }

  public Predicate apply(A arg) {
    return Predicates.same(arg);
  }

  public Predicate2<A, A> nullSafe() {
    return this;
  }

  /**
   * 
   * @return a constant {@link Same2}
   */
  @Constant
  public static <A> Predicate2<A, A> same() {
    return new Same2();
  }

}
