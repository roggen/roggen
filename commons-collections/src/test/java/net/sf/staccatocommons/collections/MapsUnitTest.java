/**
 *  Copyright (c) 2011, The Staccato-Commons Team
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

package net.sf.staccatocommons.collections;

import static net.sf.staccatocommons.lang.tuple.Tuples._;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;

import net.sf.staccatocommons.lang.MapBuilder;
import net.sf.staccatocommons.lang.Option;
import net.sf.staccatocommons.lang.Strings;

import org.junit.Test;

/**
 * Test for {@link Maps}
 * 
 * @author flbulgarelli
 * 
 */
public class MapsUnitTest {
  private Map<String, Integer> map = MapBuilder.mapWith("Foo", 50).with("Bar", 90).with("Foobar", 120).build();

  /**
   * Test method for {@link Maps#anyKey(Map)}
   */
  @Test
  public void testAnyKey() {
    assertThat(Maps.anyKey(map).value(), isOneOf("Foo", "Bar", "Foobar"));
    assertTrue(Maps.anyKey(Collections.emptyMap()).isUndefined());
  }

  /**
   * Test method for {@link Maps#anyKeyOrNull(Map)}
   */
  @Test
  public void testAnyKeyOrNull() {
    assertThat(Maps.anyKeyOrNull(map), isOneOf("Foo", "Bar", "Foobar"));
    assertNull(Maps.anyKeyOrNull(Collections.emptyMap()));
  }

  /**
   * Test method for {@link Maps#anyKeyValue(Map)}
   */
  @Test
  public void testAnyValue() {
    assertThat(Maps.anyValue(map).value(), isOneOf(50, 90, 120));
    assertNull(Maps.anyValueOrNull(Collections.emptyMap()));
  }

  /**
   * Test method for {@link Maps#get(Map, Object)}
   */
  @Test
  public void testGet() throws Exception {
    assertEquals(50, (int) Maps.get(map, "Foo").value());
    assertTrue(Maps.get(map, "FOO").isUndefined());
    assertEquals(Option.someNull(), Maps.get(MapBuilder.mapWith(10, null).build(), 10));
  }

  /**
   * Test method for
   * {@link Maps#from(net.sf.staccatocommons.lang.tuple.Pair...)}
   */
  @Test
  public void fromEntries() throws Exception {
    Map<String, String> m = Maps.from(_("Hello", "World"), _("Foo", "Bar"));
    assertEquals("World", m.get("Hello"));
    assertEquals("Bar", m.get("Foo"));
  }
  
  @Test 
  public void foo() {
    Map<String, Integer> result = Maps.delayedMapKeys(Maps.from(_("Hello", "World"), _("Foo", "Bar")), Strings.length());
    
    System.out.println(result.get("Hello"));
    System.out.println(result.containsValue(5));
    System.out.println(result);
  }
}
