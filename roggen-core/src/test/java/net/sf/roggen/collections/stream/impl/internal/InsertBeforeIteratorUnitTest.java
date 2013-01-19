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


package net.sf.roggen.collections.stream.impl.internal;

import net.sf.roggen.collections.stream.Streams;
import net.sf.roggen.testing.junit.theories.IterableTheories;

/**
 * @author flbulgarelli
 * 
 */
public class InsertBeforeIteratorUnitTest extends IterableTheories {

  protected Iterable<?> createTwoElementsIterable() {
    return Streams.cons(4).insertBefore(3, 2);
  }

  protected Iterable<?> createOneElementIterable() {
    return Streams.empty().insertBefore(2, 4);
  }

}
