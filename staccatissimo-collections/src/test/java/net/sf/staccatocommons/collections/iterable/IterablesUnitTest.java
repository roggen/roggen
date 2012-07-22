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

package net.sf.staccatocommons.collections.iterable;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static net.sf.staccatocommons.lang.tuple.Tuples.*;
import static net.sf.staccatocommons.numbers.NumberTypes.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import net.sf.staccatocommons.collections.stream.Streams;
import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.Evaluable;
import net.sf.staccatocommons.defs.tuple.Tuple2;
import net.sf.staccatocommons.lang.Compare;
import net.sf.staccatocommons.lang.Option;
import net.sf.staccatocommons.lang.function.AbstractFunction;
import net.sf.staccatocommons.lang.predicate.Predicates;
import net.sf.staccatocommons.util.Strings;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link Iterables}
 * 
 * @author flbulgarelli
 * 
 */
public class IterablesUnitTest {

  private List<Integer> integersList = Arrays.asList(4, 5, 6);
  private List<String> stringsList;

  /**
   * Setups the test
   */
  @Before
  public void setup() {
    stringsList = Arrays.asList("Hello", "World", "everybody");
  }

  /**
   * Test for {@link Iterables#all(Iterable, Evaluable)}
   */
  @Test
  @SuppressWarnings("unchecked")
  public void testAll() {
    assertTrue(Iterables.all(Arrays.asList(1, 2, 3, 4, 5, 9), Compare.greaterThan(0)));
    assertFalse(Iterables.all(Arrays.asList(1, -9, 3, 4, 5, 9), Compare.greaterThan(0)));
    assertTrue(Iterables.all((List) emptyList(), Compare.greaterThan(0)));

  }

  /**
   * Test for {@link Iterables#filter(Iterable, Evaluable)}
   * 
   * @throws Exception
   */
  @Test
  public void testFilter() throws Exception {
    List<String> result = Iterables.filter(stringsList, Strings.startsWith("H"));
    assertEquals(asList("Hello"), result);
  }

  /**
   * Test for {@link Iterables#map(Collection, Applicable)}
   * 
   * @throws Exception
   */
  @Test
  public void testMap() throws Exception {
    assertEquals(Arrays.asList(false, true, false), Iterables.map(integersList, Predicates.equal(5)));
    assertEquals(
      Arrays.asList(false, true, false),
      Iterables.map((Iterable<Integer>) integersList, Predicates.equal(5)));
  }

  /**
   * Test method for {@link Iterables#flatMap(Iterable, Applicable)}
   * 
   * @throws Exception
   */
  @Test
  public void testFlatMap() throws Exception {
    assertEquals(
      Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6),
      Iterables.flatMap(Streams.enumerate(4, 6), new AbstractFunction<Integer, Iterable<Integer>>() {
        public Iterable<Integer> apply(Integer arg) {
          return Streams.enumerate(1, arg);
        }
      }));
  }

  /** Test for {@link Iterables#findOrNone(Iterable, Evaluable)} */
  @Test
  public void testFindOrNone() throws Exception {
    Option<String> result = Iterables.findOrNone(stringsList, Strings.startsWith("H"));
    assertEquals("Hello", result.value());
  }

  /**
   * Test for {@link Iterables#allSame(Iterable)}
   */
  @Test
  public void testAllSame() {
    Object o1 = new Object();
    Object o2 = new Object();

    assertTrue(Iterables.allSame(emptyList()));
    assertTrue(Iterables.allSame(Arrays.asList(o1)));
    assertTrue(Iterables.allSame(Arrays.asList(o1, o1, o1)));
    assertFalse(Iterables.allSame(Arrays.asList(o1, o1, o2)));
  }

  /**
   * Test for {@link Iterables#allEqual(Iterable)}
   */
  @Test
  public void testAllEqual() {
    Calendar c1 = new GregorianCalendar(1995, 10, 10);
    Calendar c2 = new GregorianCalendar(1996, 10, 10);
    Calendar c3 = (Calendar) c2.clone();

    assertTrue(Iterables.allEqual(emptyList()));
    assertTrue(Iterables.allEqual(Arrays.asList(c1)));
    assertTrue(Iterables.allEqual(Arrays.asList(c2, c3)));
    assertFalse(Iterables.allEqual(Arrays.asList(c2, c1)));
  }

  /**
   * Test for {@link Iterables#indexOf(Iterable, Object)}
   */
  @Test
  public void testIndexOf() {
    List<Object> list = Arrays.<Object> asList("hola", 80, 20, 'a');
    assertEquals(3, Iterables.indexOf(list, 'a'));
    assertEquals(0, Iterables.indexOf(list, "hola"));
    assertEquals(-1, Iterables.indexOf(list, 13));
  }

  /**
   * Test for {@link Iterables#isBefore(Iterable, Object, Object)}
   */
  @Test
  public void testIsBefore() {
    List<Object> list = Arrays.<Object> asList("hola", 80, 20, 90, 60, 90, 'a');
    assertTrue(Iterables.isBefore(list, 90, 60));
    assertFalse(Iterables.isBefore(list, 60, 90));
    assertTrue(Iterables.isBefore(list, 80, 20));
    assertTrue(Iterables.isBefore(list, "hola", 'a'));
    assertFalse(Iterables.isBefore(list, 80, "hola"));
    assertFalse(Iterables.isBefore(list, 20, 40));
    assertFalse(Iterables.isBefore(list, 90, 10));
    assertFalse(Iterables.isBefore(list, 90, 20));
    assertFalse(Iterables.isBefore(list, 698, 789));
    
    assertFalse(Iterables.isBefore(list, 986, 986));
    assertFalse(Iterables.isBefore(list, 20, 20));
    assertTrue(Iterables.isBefore(list, 90, 90));
  }
  
  
  /**
   * Test for {@link Iterables#isBefore(Iterable, Object, Object)}
   */
  @Test
  public void testContainsBefore() {
    List<Object> list = Arrays.<Object> asList("hola", 80, 20, 90, 60, 90, 'a');
    
    assertTrue(Iterables.containsBefore(list, 90, 60));
    assertFalse(Iterables.containsBefore(list, 60, 90));
    assertTrue(Iterables.containsBefore(list, 80, 20));
    assertTrue(Iterables.containsBefore(list, "hola", 'a'));
    
    assertFalse(Iterables.containsBefore(list, 80, "hola"));
    assertTrue(Iterables.containsBefore(list, 20, 40));
    assertTrue(Iterables.containsBefore(list, 90, 10));
    assertFalse(Iterables.containsBefore(list, 90, 20));
    assertFalse(Iterables.containsBefore(list, 698, 789));
    
    assertFalse(Iterables.containsBefore(list, 986, 986));
    assertTrue(Iterables.containsBefore(list, 90, 90));
    assertTrue(Iterables.containsBefore(list, 20, 20));
  }

  /**
   * Test method for {@link Iterables#toList(java.util.Collection)}
   */
  @Test
  public void testToList() throws Exception {
    assertSame(Iterables.toList(integersList), integersList);
    assertTrue(Iterables.toList(new HashSet<Integer>(integersList)).containsAll(integersList));
  }

  /**
   * Test method for {@link Iterables#toSet(java.util.Collection)}
   */
  @Test
  public void testToSet() throws Exception {
    HashSet<Integer> set = new HashSet<Integer>();

    assertTrue(Iterables.toSet(integersList).containsAll(integersList));
    assertSame(Iterables.toSet(set), set);
  }

  /**
   * Test for {@link Iterables#isNullOrEmpty(Collection)} and
   * {@link Iterables#isNullOrEmpty(Iterable)}
   * 
   * @throws Exception
   */
  @Test
  public void testIsNullOrEmpty() throws Exception {
    assertTrue(Iterables.isNullOrEmpty((Collection) null));
    assertTrue(Iterables.isNullOrEmpty((Iterable) null));
    assertTrue(Iterables.isNullOrEmpty(Collections.emptyList()));
    assertTrue(Iterables.isNullOrEmpty((Iterable) Collections.emptyList()));
    assertFalse(Iterables.isNullOrEmpty(Collections.singleton(5)));
    assertFalse(Iterables.isNullOrEmpty((Iterable) Collections.singleton(5)));
  }

  /**
   * Test for {@link Iterables#single(Collection)}
   * 
   * @throws Exception
   */
  @Test
  public void testSingle() throws Exception {
    assertEquals(90, (int) Iterables.single(Collections.singleton(90)));
  }

  /**
   * Test method for {@link Iterables#partition(Iterable, Evaluable)}
   */
  @Test
  public void testPartition() throws Exception {
    Tuple2<List<Integer>, List<Integer>> partition = Iterables.partition(Streams.enumerate(10, 20), integer().isEven());

    assertEquals(Streams.enumerate(10, 20, 2).toList(), partition._0());
    assertEquals(Streams.enumerate(11, 20, 2).toList(), partition._1());
  }

  /**
   * Test method for {@link Iterables#take(Iterable, int)}
   * 
   * @throws Exception
   */
  @Test
  public void testTake() throws Exception {
    assertEquals(Arrays.asList(11, 12, 13, 14), Iterables.take(Streams.enumerate(11), 4));
  }

  /**
   * Test for {@link Iterables#zip(Iterable, Iterable)}
   * 
   * @throws Exception
   */
  @Test
  public void testZip() throws Exception {
    assertEquals(
      Arrays.asList(_(10, 8), _(12, 7), _(14, 6)),
      Iterables.zip(Arrays.asList(10, 12, 14, 23), Arrays.asList(8, 7, 6)));
  }

  /**
   * Test for sum
   */
  @Test
  public void testSum() throws Exception {
    assertEquals(55, (int) Iterables.sum(Streams.enumerate(1, 10, 1), integer()));
  }

  /**
   * Test for product
   */
  @Test
  public void testProduct() throws Exception {
    assertEquals(3628800, (int) Iterables.product(Streams.enumerate(1, 10, 1), integer()));
  }

  /** test for {@link Iterables#cross(Iterable, Iterable)} */
  @Test
  public void testCross() throws Exception {
    assertEquals(
      Arrays.asList(_(1, 'a'), _(1, 'b'), _(2, 'a'), _(2, 'b')),
      Iterables.cross(Arrays.asList(1, 2), Arrays.asList('a', 'b')));
  }

  /** Test for {@link Iterables#equiv(Iterable, Iterable)} */
  @Test
  public void testElementsEquals() throws Exception {
    assertFalse(Iterables.equiv(Arrays.asList(10, 20), Arrays.asList(10, 26)));
    assertFalse(Iterables.equiv(Arrays.asList(10, 20), Arrays.asList(10, 20, 6)));
    assertFalse(Iterables.equiv(Arrays.asList(10, 20, 4), Arrays.asList(10, 20)));
    assertTrue(Iterables.equiv(Arrays.asList(10, 20, 4), Arrays.asList(10, 20, 4)));
  }

  /**
   * Test method for
   * {@link Iterables#toSortedList(Iterable, java.util.Comparator)}
   */
  @Test
  public void testToSortedSet() throws Exception {
    assertEquals(
      Arrays.asList(3, 6, 9, 12, 50, 60),
      Iterables.toList(Iterables.toSortedSet(Arrays.asList(50, 60, 12, 6, 9, 3))));
  }

  /**
   * Test for {@link Iterables#get(Iterable, int)}
   * 
   * @throws Exception
   */
  @Test
  public void testGet() throws Exception {
    assertEquals(5, (int) Iterables.get(Arrays.asList(1, 5, 95, 6), 1));
    assertEquals(1, (int) Iterables.get(Arrays.asList(1, 5, 95, 6), 0));
  }

  /**
   * Test for {@link Iterables#get(Iterable, int)}
   * 
   * @throws Exception
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetThrowsIndexOutOfBounds() throws Exception {
    Iterables.get(Arrays.asList(1, 5, 95, 6), 4);
  }
}
