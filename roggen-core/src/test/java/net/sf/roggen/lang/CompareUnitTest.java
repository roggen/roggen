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

package net.sf.roggen.lang;

import static net.sf.roggen.lang.Compare.*;
import static net.sf.roggen.lang.tuple.Tuples.*;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.lang.Compare;
import net.sf.roggen.lang.tuple.Tuples;

import org.junit.Test;

/**
 * Test for {@link Compare}
 * 
 * @author flbulgarelli
 * 
 */
public class CompareUnitTest {

  /***
   * Test for {@link Compare#between(Comparable, Comparable, Comparable)}
   */
  @Test
  public void testBetweenComparable() {

    Calendar calendar = Calendar.getInstance();
    Date date1 = calendar.getTime();

    calendar.add(Calendar.MONTH, 1);
    Date date2 = calendar.getTime();

    calendar.add(Calendar.MONTH, 1);
    Date date3 = calendar.getTime();

    assertTrue(between(date2, date1, date3));
    assertTrue(between(date3, date1, date3));
    assertTrue(between(date1, date1, date3));
    assertFalse(between(date1, date2, date3));
  }

  /***
   * Test for
   * {@link Compare#between(Object, Object, Object, java.util.Comparator)}
   */
  @Test
  public void testBetweenComparator() {
    assertTrue(between(50, 60, 10, Collections.reverseOrder()));
    assertFalse(between(80, 60, 10, Collections.reverseOrder()));
  }

  /**
   * Test for {@link Compare#between(long, long, long)}
   */
  @Test
  public void testBetweenLong() {
    assertTrue(between(90L, 60, 156));
    assertFalse(between(90L, 100, 156));
  }

  /**
   * Test for {@link Compare#between(int, int, int)}
   */
  @Test
  public void testBetweenInt() {
    assertTrue(between(90, 60, 156));
    assertTrue(between(156, 60, 156));
    assertTrue(between(60, 60, 156));
    assertFalse(between(190, 60, 156));
  }

  /***/
  @Test
  public void testInIntValues() {
    assertTrue(in(5, 1, 2, 3, 5, 9, 60));
    assertFalse(in(6, 1, 2, 3, 5, 9, 60));
  }

  /***/
  @Test
  public void testInLongValues() {
    assertTrue(in(5, new int[] { 1, 2, 3, 5, 9, 60 }));
    assertFalse(in(6, new int[] { 1, 2, 3, 5, 9, 60 }));
  }

  /***/
  @Test
  public void testIn() {
    assertTrue(in(5, new long[] { 1, 2, 3, 5, 9, 60 }));
    assertFalse(in(6, new long[] { 1, 2, 3, 5, 9, 60 }));
  }

  /***/
  @Test
  public void testMax() {
    assertEquals((Integer) 5, max(5, 2));
    assertEquals((Integer) 9, max(5, 9));
    assertEquals((Integer) 9, max(9, 9));
  }

  /***/
  @Test
  public void testMin() {
    assertEquals("ABC", min("FGH", "ABC"));
    assertEquals("AAA", min("AAA", "ABC"));
    assertEquals("EEE", min("EEE", "EEE"));
  }

  /*** Test for {@link Compare#on(Applicable)} */
  @Test
  public void testOn() throws Exception {
    assertEquals(0, Compare.on(Tuples.<Integer> first()).compare(_(20, 30), _(20, 40)));
    assertTrue(Compare.on(Tuples.<Integer> second()).compare(_(20, 30), _(20, 40)) < 0);
  }

  /** Test for {@link Compare#between(Comparable, Comparable)} */
  @Test
  public void testBetween() throws Exception {
    assertTrue(Compare.between("bar", "foo").eval("baz"));
    assertFalse(Compare.between(9, 50).eval(4));
  }

}
