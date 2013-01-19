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

package net.sf.roggen.lang.value;

import static net.sf.roggen.lang.tuple.Tuples.*;
import static org.junit.Assert.*;

import javax.xml.ws.Holder;

import net.sf.roggen.lang.value.NamedTupleToStringStyle;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class NamedTupleToStringStyleTest {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * @throws Exception
   */
  @Test
  public void testStyleWithOneAttribute() throws Exception {
    assertEquals(
      "Holder(hello)",
      ToStringBuilder.reflectionToString(new Holder<String>("hello"), new NamedTupleToStringStyle()));
  }

  /***/
  @Test
  public void testStyleWithTwoAttributes() throws Exception {
    assertEquals("Pair(10, 30)", ToStringBuilder.reflectionToString(_(10, 30), new NamedTupleToStringStyle()));
  }

}
