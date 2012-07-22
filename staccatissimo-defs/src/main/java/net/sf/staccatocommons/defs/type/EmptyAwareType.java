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


package net.sf.staccatocommons.defs.type;

import java.util.Collection;

import net.sf.staccatocommons.defs.partial.EmptyAware;
import net.sf.staccatocommons.restrictions.SideEffectFree;
import net.sf.staccatocommons.restrictions.ValueObject;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * A Strategy for asking if an object is empty.
 * 
 * *
 * <p>
 * {@link EmptyAwareType}s resolve the problem of dealing polimorphically with
 * objects that have no common superclass nor interface that describes the
 * behaviour of being empty. Examples of such interfaces are {@link EmptyAware}
 * and {@link Collection}
 * </p>
 * 
 * @author flbulgarelli
 * @see EmptyAware
 * @see <a href="http://en.wikipedia.org/wiki/Type_class">Type class</a>
 */
@ValueObject
public interface EmptyAwareType<A> {

  /**
   * Answers if the given object that is aware of the concept of "emptyness" is
   * empty or not.
   * 
   * @param emptyAware
   *          - non null.
   * @return is the given <code>emptyAware</code> is empty
   */
  @SideEffectFree
  boolean isEmpty(@NonNull A emptyAware);

}
