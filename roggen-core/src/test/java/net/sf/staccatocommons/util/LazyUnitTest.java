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

package net.sf.staccatocommons.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for {@link Lazy}
 * 
 * @author flbulgarelli
 * 
 */
@Deprecated
public class LazyUnitTest {

  private int var;

  /***/
  @Test
  public void testValue() {

    var = 0;

    Lazy<Integer> lazyInteger = new Lazy<Integer>() {
      @Override
      protected Integer init() {
        return var;
      }
    };

    var = 1;

    assertEquals((Integer) 1, lazyInteger.value());

    var = 2;

    assertEquals((Integer) 1, lazyInteger.value());
  }

}
