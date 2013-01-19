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


package net.sf.roggen.util;

import java.util.concurrent.Callable;

import net.sf.roggen.defs.Thunk;
import net.sf.roggen.lang.SoftException;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class Callables {

  /**
   * Converts the givne {@link Thunk} into a {@link Callable}, whose
   * {@link Callable#call()} method answers the thunk's value, and hardens any
   * exception thrown by the thunk's evaluation.
   * 
   * @param <A>
   * @param thunk
   * @return a new {@link Callable}
   * 
   * @see SoftException#harden(RuntimeException)
   */
  public static <A> Callable<A> from(@NonNull final Thunk<A> thunk) {
    class ThunkCallable implements Callable<A> {
      public A call() throws Exception {
        return SoftException.valueOrHarden(thunk);
      }
    }
    return new ThunkCallable();
  }

}
