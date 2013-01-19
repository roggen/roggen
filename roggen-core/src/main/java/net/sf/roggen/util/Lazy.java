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

import net.sf.roggen.defs.Thunk;
import net.sf.roggen.lang.Option;

/**
 * 
 * A {@link Lazy} is an abstract wrapper around a value initialization code that
 * lets it postpone it creation, or even avoid it if not necessary, and ensure
 * that initialization is evaluated only once.
 * <p>
 * Client code creating the lazy value just need to extend {@link Lazy} and
 * implement {@link #init()} method. Client code that needs to consume its
 * value, must call {@link #value()}
 * </p>
 * 
 * {@link Lazy} is not thread safe. For a thread safe version, use
 * {@link Lazy.Atomic}
 * 
 * @author flbulgarelli
 * 
 * @param <T>
 * @deprecated 
 */
@Deprecated
public abstract class Lazy<T> implements Thunk<T> {

  private Option<T> lazyValue = Option.none();

  /**
   * Initializes the lazy value if necessary, and returns it.
   * 
   * @return the lazy value
   */
  public T value() {
    if (lazyValue.isUndefined())
      lazyValue = Option.some(init());
    return lazyValue.value();
  }

  /**
   * Initializes the lazy value. This code will never be evaluated, if
   * {@link #value()} is never sent, or evaluated once and only once, if
   * {@link #value()} is sent at least once.
   * 
   * @return the initialized value. May be null.
   */
  protected abstract T init();

  /**
   * A synchronized {@link Lazy} thunk
   * 
   * @author flbulgarelli
   * 
   * @param <T>
   */
  public abstract static class Atomic<T> extends Lazy<T> {

    @Override
    public final synchronized T value() {
      return super.value();
    }
  }

}
