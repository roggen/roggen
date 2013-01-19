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

import static net.sf.roggen.lang.tuple.Tuples.*;
import static org.junit.Assert.*;

import java.util.regex.Pattern;

import net.sf.roggen.lang.function.Functions;
import net.sf.roggen.util.Strings;

import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class StringsUnitTest {
  /** Test for {@link Strings#reflectionToString()} **/
  @Test
  public void testReflectionToString() throws Exception {
    assertEquals("Pair(10, 5)", Strings.reflectionToString().apply(_(10, 5)));
  }

  /**
   * Test for {@link Strings#empty()}
   * */
  @Test
  public void testEmpty() throws Exception {
    assertTrue(Strings.empty().apply(""));
    assertFalse(Strings.empty().apply("fsfs"));
  }

  /**
   * Test for {@link Strings#notEmpty()}
   * */
  @Test
  public void testNotEmpty() throws Exception {
    assertFalse(Strings.notEmpty().apply(""));
    assertTrue(Strings.notEmpty().apply("fsfs"));
  }

  /** Test for {@link Functions#toString_()} */
  @Test
  public void testToString() throws Exception {
    assertEquals("50", Strings.toString_().apply(50));
  }

  /**
   * Test method for
   * {@link net.sf.roggen.util.Strings#equalsIgnoreCase(java.lang.String)}
   * .
   */
  @Test
  public void testEqualsIgnoreCase() {
    assertTrue(Strings.equalsIgnoreCase("Hello").eval("hello"));
    assertFalse(Strings.equalsIgnoreCase("Hello").eval("world"));
  }

  /**
   * Test method for
   * {@link net.sf.roggen.util.Strings#matches(java.lang.String)} .
   */
  @Test
  public void testMatchesRegexp() {
    assertTrue(Strings.matches("[Hh]el+o").eval("hello"));
    assertFalse(Strings.matches("[Hh]el+o").eval("world"));
  }

  /***/
  @Test
  public void testMatchesPattern() throws Exception {
    assertTrue(Strings.matches(Pattern.compile("[Hh]el+o")).eval("hello"));
    assertFalse(Strings.matches(Pattern.compile("[Hh]el+o")).eval("world"));
  }

  /***/
  @Test
  public void testConstains() {
    assertTrue(Strings.contains("foo").eval("The word foo has no special meaning"));
    assertFalse(Strings.contains("foo").eval("The word bar has no special meaning, neither"));
  }
}
