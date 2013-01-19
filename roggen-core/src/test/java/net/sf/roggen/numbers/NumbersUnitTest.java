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


package net.sf.roggen.numbers;

import static net.sf.roggen.numbers.Numbers.*;
import static org.junit.Assert.*;

import net.sf.roggen.numbers.Numbers;

import org.junit.Test;

/**
 * Test for {@link Numbers}
 * 
 * @author flbulgarelli
 * 
 */
public class NumbersUnitTest {

  /**
   * Test method for {@link net.sf.roggen.numbers.Numbers#d(long)}.
   */
  @Test
  public void testD() {
    assertEquals("500", d(500).toString());
  }

  /**
   * Test method for
   * {@link net.sf.roggen.numbers.Numbers#e(long, int)}.
   */
  @Test
  public void testE() {
    assertEquals("89.56", e(8956, -2).toString());
  }

  /**
   * Test method for {@link net.sf.roggen.numbers.Numbers#i(long)}.
   */
  @Test
  public void testI() {
    assertEquals("596", i(596).toString());
  }

}
