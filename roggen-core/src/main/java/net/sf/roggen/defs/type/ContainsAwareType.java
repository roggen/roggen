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


package net.sf.roggen.defs.type;

import java.util.Collection;

import net.sf.roggen.defs.partial.ContainsAware;
import net.sf.roggen.defs.partial.EmptyAware;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * A Strategy for asking if an object contains another one.
 * 
 * *
 * <p>
 * {@link ContainsAwareType}s resolve the problem of dealing polimorphically
 * with objects that have no common superclass nor interface that describes the
 * behaviour of contain other objects. Examples of such interfaces are
 * {@link ContainsAware} and {@link Collection}
 * </p>
 * 
 * @author flbulgarelli
 * @see EmptyAware
 * @see <a href="http://en.wikipedia.org/wiki/Type_class">Type class</a>
 */
public interface ContainsAwareType<A, B> {

  /**
   * Answers if the given <code>container</code> that is aware of the concept of
   * contain elements, contains the given <code>element</code>
   * 
   * @param container
   *          - non null.
   * @param element
   * @return if the given <code>container</code> contains the given element.
   */
  boolean contains(@NonNull A container, B element);

}
