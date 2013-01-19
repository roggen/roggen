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


package net.sf.staccatocommons.defs.tuple;

import java.util.Map.Entry;

import net.sf.staccatocommons.defs.partial.FirstAware;
import net.sf.staccatocommons.defs.partial.SecondAware;
import net.sf.staccatocommons.defs.partial.ToListAware;
import net.sf.staccatocommons.restrictions.ValueObject;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * @since 1.2 
 */
@ValueObject
public interface Tuple2<A, B> extends ToListAware<Object>, FirstAware<A>, SecondAware<B>, Comparable<Tuple2<A, B>>,
  Entry<A, B> {

  /**
   * answers a new {@link Tuple2}, with swaped components
   * 
   * @return a new pair, never null.
   */
  @NonNull
  Tuple2<B, A> swap();

}
