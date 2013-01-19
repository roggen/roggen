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


package net.sf.roggen.iterators;

import java.util.Arrays;
import java.util.Iterator;

import net.sf.roggen.iterators.EmptyThriterator;
import net.sf.roggen.iterators.PrependThriterator;
import net.sf.roggen.testing.junit.theories.IteratorTheories;

/**
 * @author flbulgarelli
 * 
 */
public class ConsIteratorUnitTest extends IteratorTheories {

  protected Iterator<?> createTwoElementsIterator() {
    return new PrependThriterator(50, Arrays.asList(10).iterator());
  }

  protected Iterator<?> createOneElementIterator() {
    return new PrependThriterator(60, EmptyThriterator.empty());
  }

}
