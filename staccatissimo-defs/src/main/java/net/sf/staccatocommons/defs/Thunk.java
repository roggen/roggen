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
 * A {@link Thunk} is a computation that takes no arguments a returns a value.
 * In other words, is an object that is capable of providing another one,
 * through it {@link #value()} method.
 * 
 * <p>
 * {@link Thunk}s of return type {@link Void} have semantics compatible with
 * {@link Runnable}, as they do not provide a value, but a side effect instead
 * </p>
 * 
 * <p>
 * Notice: the meaning of the term "Thunk" is sometimes vague and varies
 * depending the context and author. Within Staccato-Commons, thunk
 * <strong>must</strong> be interpreted a "no arguments expression"
 * </p>
 * 
 * @author flbulgarelli
 * 
 * @param <T>
 *          the type of provided value
 * 
 * @see FunctionLikeObject Recomendations for implementing
 */
@ValueObject
@FunctionLikeObject
public interface Thunk<T> {

  /**
   * Returns the value provided. Sending this message to the {@link Thunk} is
   * also known as <em>evaluating the thunk</em>.
   * 
   * @return the provided object. It is sometimes referred as the
   *         <em>thunk's element</em>
   */
  T value();

}
