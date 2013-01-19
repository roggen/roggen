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

package net.sf.roggen.util;

import static net.sf.roggen.testing.junit.jmock.MiscMatchers.*;
import static net.sf.roggen.util.Range.*;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import net.sf.roggen.util.Range;

import org.junit.Test;

/**
 * Test for {@link Range}
 * 
 * @author flbulgarelli
 */
public class RangeUnitTest {
  /***/
  @Test
  public void testContains() {
    assertTrue(from("Hello", "World").contains("Java"));
    assertTrue(Range.from(TimeUnit.SECONDS, TimeUnit.DAYS).contains(TimeUnit.HOURS));
    assertFalse(from(5, 9).contains(10));
    assertFalse(from(5, 9).contains(10));
  }

  /***/
  @Test
  public void testOverlaps() {
    assertTrue(from(10, 400).overlaps(from(60, 80)));
    assertTrue(from(10, 400).overlaps(from(5, 80)));
    assertTrue(from(10, 400).overlaps(from(5, 900)));
    assertFalse(from(10, 400).overlaps(from(500, 900)));
    assertFalse(from(500, 650).overlaps(from(50, 90)));
  }

  /***/
  @Test
  public void testIncludes() {
    assertTrue(from(5, 900).includes(from(9, 65)));
    assertFalse(from(9L, 65L).includes(from(5L, 900L)));
    assertFalse(from(10, 400).includes(from(5, 900)));
  }

  /***/
  @Test
  public void testGetMax() {
    assertEquals((Integer) 9, from(5, 9).getMax());
  }

  /***/
  @Test
  public void testGetMin() {
    assertEquals((Integer) 9, from(9, 98).getMin());
  }

  /**
   * Test method for {@link Range#isEmpty()}
   */
  @Test
  public void testIsEmpty() {
    assertFalse(from(8, 9).isEmpty());
    assertTrue(from(10, 10).isEmpty());
  }

  /***/
  @Test
  public void testSerialization() throws Exception {
    assertThat(Range.from(50, 90), canSerialize());
  }

}
