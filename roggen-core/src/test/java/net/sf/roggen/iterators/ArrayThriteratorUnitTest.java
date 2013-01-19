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

import static org.junit.Assert.*;

import java.util.Iterator;

import net.sf.roggen.iterators.thriter.Thriterators;
import net.sf.roggen.testing.junit.theories.IteratorTheories;

import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class ArrayThriteratorUnitTest extends IteratorTheories {

  protected Iterator<?> createTwoElementsIterator() {
    return Thriterators.from(20, 10);
  }

  protected Iterator<?> createOneElementIterator() {
    return Thriterators.from(10);
  }

  /** test for toString */
  @Test
  public void testToString() throws Exception {
    assertEquals("ArrayThriterator([10, 20, 30])", Thriterators.from(10, 20, 30).toString());
  }

}
