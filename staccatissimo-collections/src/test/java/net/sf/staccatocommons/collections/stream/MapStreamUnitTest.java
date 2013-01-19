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

import static net.sf.staccatocommons.numbers.NumberTypes.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import net.sf.staccatocommons.collections.stream.internal.algorithms.MapStream;
import net.sf.staccatocommons.lang.Compare;
import net.sf.staccatocommons.lang.function.Functions;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * @author flbulgarelli
 * 
 */
public class MapStreamUnitTest extends StreamTheories {

  /** data point */
  @DataPoint
  public static Stream empty() {
    return new MapStream(Streams.empty(), Functions.identity());
  }

  /** data point */
  @DataPoint
  public static Stream oneElement() {
    return new MapStream(Streams.cons(10), Functions.identity());
  }

  /** data point */
  @DataPoint
  public static Stream twoElementsNonRepetable() {
    return new MapStream(Streams.from(Arrays.asList(20, 30, 40).iterator()).filter(Compare.greaterThan(25)), add(10));
  }
  
  /**
   * Tests that mapping and then mapping is the same that composing mapping
   * functions
   */
  @Test
  public void mapMapIsTheSameThatComposing() throws Exception {
    assertTrue(Streams.cons(10, 9, 6).map(add(1)).map(integer().negate()).equiv(-11, -10, -7));
  }

}
