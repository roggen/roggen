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

package net.sf.roggen.collections.iterable;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.sf.roggen.collections.iterable.ModifiableIterables;
import net.sf.roggen.defs.Evaluable;
import net.sf.roggen.lang.Compare;
import net.sf.roggen.lang.predicate.Predicates;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class ModifiableIterablesUnitTest {

  /**
   * Test method for
   * {@link ModifiableIterables#removeAll(java.lang.Iterable, Evaluable)} .
   */
  @Test
  public void testRemoveAll() {
    LinkedList<Integer> list = new LinkedList<Integer>();
    Collections.addAll(list, 2, 9, 6, 8, 10, 2);
    assertEquals(list, ModifiableIterables.removeAll(list, Compare.greaterThan(8)));
    assertEquals(Arrays.asList(2, 6, 8, 2), list);
  }

  /**
   * Test method for
   * {@link ModifiableIterables#removeWhile(java.lang.Iterable, Evaluable)} .
   */
  @Test
  public void testRemoveWhile() {
    LinkedList<Integer> list = new LinkedList<Integer>();
    Collections.addAll(list, 2, 5, 8, 6, 8, 10, 2);
    assertEquals(list, ModifiableIterables.removeWhile(list, Compare.lessThan(8)));
    assertEquals(Arrays.asList(8, 6, 8, 10, 2), list);
  }

  /**
   * Test method for {@link ModifiableIterables#remove(java.lang.Iterable, int)}
   * .
   */
  @Ignore
  @Test
  public void testRemove() {
    fail("Not yet implemented");
  }

  /**
   * Test method for
   * {@link ModifiableIterables#addAll(java.util.Collection, java.lang.Iterable)}
   * .
   */
  @Test
  public void testAddAll() {
    LinkedList<String> list = new LinkedList<String>();
    Collections.addAll(list, "foo", "bat");
    assertEquals(list, ModifiableIterables.addAll(list, Arrays.asList("foobar", "bar")));
    assertEquals(Arrays.asList("foo", "bat", "foobar", "bar"), list);
  }

  /**
   * Test method for
   * {@link ModifiableIterables#move(java.lang.Iterable, java.util.Collection, Evaluable)}
   * .
   */
  @Test
  public void testMoveIterableOfTCEvaluableOfT() {
    List<Integer> ints = ModifiableIterables.addAll(new LinkedList<Integer>(), Arrays.asList(4, 5, 9, 10));
    ArrayList<Integer> moved = ModifiableIterables.move(ints, new ArrayList<Integer>(), Predicates.in(5, 9));

    assertThat(moved, hasItem(5));
    assertThat(moved, hasItem(9));
    assertEquals(Arrays.asList(4, 10), ints);
  }

  /**
   * Test method for
   * {@link ModifiableIterables#move(java.lang.Iterable, int, java.util.Collection)}
   * .
   */
  @Ignore
  @Test
  public void testMoveIterableOfTIntC() {
    fail("Not yet implemented");
  }

}
