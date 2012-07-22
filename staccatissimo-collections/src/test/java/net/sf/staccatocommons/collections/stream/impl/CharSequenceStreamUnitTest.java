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

package net.sf.staccatocommons.collections.stream.impl;

import static junit.framework.Assert.*;
import net.sf.staccatocommons.collections.stream.RepetableStreamTheories;
import net.sf.staccatocommons.collections.stream.Stream;
import net.sf.staccatocommons.collections.stream.Streams;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;

/**
 * @author flbulgarelli
 * 
 */
public class CharSequenceStreamUnitTest extends RepetableStreamTheories {

  /** Streams for testing */
  @DataPoints
  public static final Stream[] STREAMS = new Stream[] { //
  Streams.from(""), //
      Streams.from("hello"), //
      Streams.from("h") //
  };

  /** Test that joining a character stream returns back the original string */
  @Test
  public void testJoin() throws Exception {
    String string = "hello world!";
    assertEquals(string, Streams.from(string).joinStrings(""));
  }

  public void ifContainsBeforeThenReferencesIsNotContainedInFirstSegment(Stream<Integer> stream, int element, int next) {
    // TODO can not test, since StreamTheories are not generic enough
  }
}
