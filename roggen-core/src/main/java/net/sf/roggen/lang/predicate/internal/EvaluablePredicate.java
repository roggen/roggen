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

import net.sf.roggen.defs.Evaluable;

/**
 * @author flbulgarelli
 * @param <T>
 * 
 */
public class EvaluablePredicate<T> extends TopLevelPredicate<T> {

  private static final long serialVersionUID = -7926781232880056763L;
  private final Evaluable<? super T> evaluable;

  /**
   * 
   * Creates a new {@link EvaluablePredicate}
   * 
   * @param evaluable
   */
  public EvaluablePredicate(Evaluable<? super T> evaluable) {
    this.evaluable = evaluable;
  }

  @Override
  public boolean eval(T argument) {
    return evaluable.eval(argument);
  }

}
