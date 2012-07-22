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


package net.sf.staccatocommons.collections.stream;

import static junit.framework.Assert.*;
import static net.sf.staccatocommons.numbers.NumberTypes.*;

import java.util.Arrays;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.Evaluable;
import net.sf.staccatocommons.defs.Thunk;

import org.junit.Test;

/**
 * 
 * Test for {@link Streams}
 * 
 * @author flbulgarelli
 * 
 */
public class StreamsUnitTest {

  /**
   * Test method for
   * {@link Streams#from(java.lang.Object, Applicable, Evaluable)}.
   */
  @Test
  public void testFromSeq() {
    assertEquals(Streams.enumerate(10, 50, 20).toList(), Streams.from(Streams.enumerate(10, 50, 20)).toList());

    assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), //
      Streams.iterate(1, add(1)).take(10).toList());
  }

  /**
   * Test method for replicate with null argument
   * 
   * @throws Exception
   */
  @Test
  public void testRepeat() throws Exception {
    Stream<Object> repeat = Streams.repeat((Object) null).memoize();
    assertNull(repeat.first());
    assertNull(repeat.third());
  }

  /**
   * Test method for repeat(Thunk)
   * 
   * @throws Exception
   */
  @Test
  public void testRepeatThunk() throws Exception {
    Streams.repeat(new Thunk<Integer>() {
      int i = 0;

      public Integer value() {
        return i++;
      }
    }).take(4).equiv(0, 1, 2, 3);
  }

}
