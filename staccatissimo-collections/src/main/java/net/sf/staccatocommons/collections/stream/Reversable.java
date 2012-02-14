/**
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


package net.sf.staccatocommons.collections.stream;

import net.sf.staccatocommons.collections.restrictions.Projection;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public interface Reversable<A> {

  /**
   * Reverses this Stream, by returning a new Stream that retrieves elements in
   * the inverse order of this Stream.
   * 
   * This may not be a {@link Projection}, depending on if the stream's source
   * permits it.
   * 
   * @return a new {@link Stream} that retrieves elements in the inverse order
   *         of this stream.
   */
  @NonNull
  Stream<A> reverse();

}