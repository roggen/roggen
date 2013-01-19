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

/**
 * @author flbulgarelli
 * 
 * @param <T>
 */
public final class Same<T> extends TopLevelPredicate<T> {
  private static final long serialVersionUID = 3404033724148091585L;
  private final T value;

  /**
   * Creates a new {@link Same}
   * 
   * @param value
   */
  public Same(T value) {
    this.value = value;
  }

  public boolean eval(T argument) {
    return value == argument;
  }
}