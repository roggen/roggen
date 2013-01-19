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

import static net.sf.staccatocommons.lang.Compare.lessThan;
import static net.sf.staccatocommons.lang.tuple.Tuples._;
import static net.sf.staccatocommons.numbers.NumberTypes.add;
import static net.sf.staccatocommons.numbers.NumberTypes.bigInteger;
import static net.sf.staccatocommons.numbers.NumberTypes.double_;
import static net.sf.staccatocommons.numbers.NumberTypes.integer;
import static net.sf.staccatocommons.numbers.Numbers.i;
import static net.sf.staccatocommons.util.Strings.length;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import net.sf.staccatocommons.collections.Lists;
import net.sf.staccatocommons.collections.iterable.Iterables;
import net.sf.staccatocommons.defs.Applicable2;
import net.sf.staccatocommons.defs.Evaluable;
import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.tuple.Tuple2;
import net.sf.staccatocommons.defs.type.NumberType;
import net.sf.staccatocommons.lang.Compare;
import net.sf.staccatocommons.lang.function.AbstractFunction;
import net.sf.staccatocommons.lang.function.AbstractFunction2;
import net.sf.staccatocommons.lang.tuple.Tuples;
import net.sf.staccatocommons.reductions.Reductions;
import net.sf.staccatocommons.util.Strings;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Classic tests for methods of {@link AbstractStream} that are not covered by
 * {@link StreamTheories}, because it is costly to create and maintain theories
 * about them.
 * 
 * @author flbulgarelli
 * 
 */
public class AbstractStreamBasicTest {

  /**
   * Test for {@link Stream#product(NumberType)}
   */
  @Test
  public void foldWithMultilyIsProduct() throws Exception {
    assertEquals(Streams.enumerate(2, 10).fold(1, integer().multiply()), //
      Streams.enumerate(2, 10).product(integer()));
  }

  /** Test for sum */
  @Test
  public void sum() throws Exception {
    assertEquals((Integer) 65, Streams.cons(10, 20, 35).sum(integer()));
  }

  /** Test for product */
  @Test
  public void product() throws Exception {
    assertEquals(1 * 2 * 3, (int) Streams.cons(0, 1, 2).map(add(1)).product(integer()));
  }

  /** Test for implicit sum */
  @Test
  public void filteredSum() throws Exception {
    assertEquals((Integer) 70, //
      Streams.iterate(10, add(3)) //
        .take(7)
        .filter(Compare.lessThan(25))
        .tail()
        .sum(integer()));
  }

  /** Test for {@link Stream#average()} **/
  @Test
  public void testAvg() throws Exception {
    assertEquals(9.6, Streams.cons(10.0, 12.0, 15.0, 2.0, 9.0).average(double_()), 0.01);
    assertEquals(6, (int) Streams.enumerate(1, 11).average(integer()));
  }

  /** Test for {@link Stream#average()} **/
  @Test(expected = ArithmeticException.class)
  public void testAvgEmptyStream() throws Exception {
    Streams.<Integer> cons().average(integer());
  }

  /**
   * Test method for {@link AbstractStream#fold(java.lang.Object, Applicable2)}
   */
  @Test
  public void fold() {
    Stream<Collection<String>> stream = //
    Streams.<Collection<String>> cons(Arrays.asList("foo", "baz"), Collections.singleton("bar"));

    Collection<String> result = stream.fold(
      new ArrayList<String>(),
      new AbstractFunction2<Collection<String>, Collection<String>, Collection<String>>() {
        public Collection<String> apply(Collection<String> arg0, Collection<String> arg1) {
          arg0.addAll(arg1);
          return arg0;
        }
      });
    assertEquals(Arrays.asList("foo", "baz", "bar"), result);
  }

  /**
   * Test method for {@link AbstractStream#reduce(Applicable2)}
   */
  @Test
  public void testReduce() {
    Stream<BigInteger> bigints = Streams.cons(i(100), i(800), i(260));
    assertEquals(i(1160), bigints.reduce(bigInteger().add()));
  }

  /**
   * Test for {@link Stream#flatMap(net.sf.staccatocommons.defs.Applicable)}
   */
  @Test
  public void flatMap() throws Exception {
    assertTrue(Streams
      .iterate(4, add(1))
      .take(3)
      .flatMap(new AbstractFunction<Integer, Iterable<Integer>>() {
        public Iterable<Integer> apply(Integer arg) {
          return Streams.enumerate(1, arg);
        }
      })
      .equiv(1, 2, 3, 4, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6));
  }

  /** Test for {@link AbstractStream#append(Iterable)} ***/
  @Test
  public void concat() {
    assertEquals(
      Arrays.asList(10, 90, 60, 1, 2, 20),
      Streams.cons(10, 90, 60).concat(Arrays.asList(1, 2)).concat(Streams.cons(20)).toList());

    assertEquals(Arrays.asList("foo"), Streams.cons("foo").appendUndefined().take(1).toList());
  }
  
  /***/
  @Test
  public void concatArrayOverloadingJoinsTwoStreams() throws Exception {
    Streams.cons(0, 1).concat(2, 3).equiv(0, 1, 2, 3);
  }

  /** Tets for indexof */
  @Test
  public void testIndexof() throws Exception {
    assertEquals(3, Streams.from(Arrays.asList(12, 69, null, 1).iterator()).indexOf(1));
    assertEquals(0, Streams.cons(10, 90).indexOf(10));
    assertEquals(-1, Streams.cons(10, 90).indexOf(87));
  }

  /** Test for {@link Stream#findIndex(Evaluable)} */
  @Test
  public void testFindIndex() throws Exception {
    assertEquals(1, Streams.cons("hello", "world").findIndex(Strings.startsWith("wo")));
    assertEquals(-1, Streams.cons("hello", "world").findIndex(Strings.startsWith("foo")));
    assertEquals(0, Streams.cons("hello", "world").findPosition(Strings.startsWith("he")));
  }

  /** Test for {@link Stream#findIndex(Evaluable)} */
  @Test(expected = NoSuchElementException.class)
  public void findPositionThrowsNoSuchElementException() throws Exception {
    Streams.cons("hello", "world").findPosition(Strings.startsWith("foo"));
  }

  /** Test for {@link Stream#indices(Evaluable)} */
  @Test
  public void indices() throws Exception {
    assertEquals(
      Lists.from(2, 3),
      Streams.cons("hello", "world", "foo", "bar").indices(lessThan(4).of(length())).toList());
  }

  /** Tets for positionOf */
  @Test
  public void testPositionOf() throws Exception {
    assertEquals(1, Streams.cons(2, 10, 90).positionOf(10));
  }

  /** Tets for positionOf */
  @Test(expected = NoSuchElementException.class)
  public void testPositionOfBad() throws Exception {
    Streams.cons(10, 90).positionOf(87);
  }

  /** Test for {@link Stream#intersperse(Object)} */
  @Test
  public void testIntersperse() throws Exception {
    assertTrue(Streams//
      .cons(4, 5, 6)
      .intersperse(1)
      .equiv(4, 1, 5, 1, 6));
  }

  /** Test for {@link Stream#intersperse(Object)} */
  @Test
  public void interspereIterator() throws Exception {
    assertTrue(Streams//
      .from(Arrays.asList(4, 5).iterator())
      .append(10)
      .intersperse(1)
      .take(4)
      .equiv(4, 1, 5, 1));
  }

  /** Test for {@link Stream#intersperse(Object)} */
  @Test
  public void testIntersperseTwoElementsStream() throws Exception {
    assertEquals("[56, 0, 1]", Streams//
      .cons(56, 1)
      .intersperse(0)
      .printString());
  }

  /** Test for {@link Stream#incorporate(Function)} */
  @Test
  public void testIncorporate() throws Exception {
    assertTrue(Streams
      .cons(10, 9, 90)
      .incorporate(integer().negate())
      .equiv(10, -10, 9, -9, 90, -90));
  }

  /** Test for {@link Stream#incorporate(Object)} */
  @Test
  public void testIncorporateElement() throws Exception {
    assertTrue(Streams //
      .cons('a', 'b', 'c')
      .incorporate('d')
      .equiv('a', 'd', 'b', 'd', 'c', 'd'));
  }

  /** Test for {@link Stream#incorporate(Function)} */
  @Test
  public void testIncorporateIsLazy() throws Exception {
    assertEquals((Integer) 0, Streams.repeat(10).incorporate(-10).take(10).sum(integer()));
  }

  /**
   * Test for {@link Stream#intersperse(Object)}
   */
  @Test
  public void testMemorize() throws Exception {
    Stream<Integer> interspersed = Streams.cons(10, 20, 30).intersperse(1).memoize();
    assertEquals(10, (int) interspersed.first());
    assertEquals(10, (int) interspersed.first());
    assertEquals(1, (int) interspersed.second());
    assertEquals(20, (int) interspersed.third());
    assertEquals(30, (int) interspersed.get(4));
    assertEquals(1, (int) interspersed.get(3));
    assertEquals(1, (int) interspersed.get(3));
    assertEquals(30, (int) interspersed.get(4));

    Stream<Integer> mapped = Streams.cons(10, 20, 30).map(add(1));
    assertEquals(21, (int) mapped.second());
    assertEquals(11, (int) mapped.first());
    assertEquals(31, (int) mapped.third());
    assertEquals(31, (int) mapped.third());
    assertEquals(11, (int) mapped.first());
    assertEquals(21, (int) mapped.second());

  }

  /**
   * Tests that memorized streams are completely lazy in the particular case of
   * a append stream
   */
  @Test
  public void testMemorizeLazyOnAppend() throws Exception {
    Stream<Integer> stream = Streams.cons(10, 98).append(65).appendUndefined().append(9).memoize();
    assertEquals(stream.last(), stream.last());
  }

  /**
   * Tests that memorized streams are completely lazy, in the particular case of
   * a map stream
   */
  @Test
  public void testMemorizeLazyOnMap() throws Exception {
    Stream<Integer> stream = Streams.cons(10, 0, 20).map(new AbstractFunction<Integer, Integer>() {
      public Integer apply(Integer arg) {
        return 1 / arg;
      }
    }).memoize();

    assertEquals(stream.third(), stream.third());
  }

  /** Test for {@link Stream#streamPartition(Evaluable)} */
  @Test
  public void testPartition() throws Exception {
    Tuple2<Stream<Integer>, Stream<Integer>> partition = Streams
      .cons(50, 60, 1, 6, 9, 10, 100)
      .streamPartition(Compare.greaterThan(9));

    assertTrue(partition._0().equiv(50, 60, 10, 100));
    assertTrue(partition._1().equiv(1, 6, 9));

  }

  /** Test for {@link Stream#reverse()} */
  @Test
  public void testReverse() throws Exception {
    assertTrue(//
    Streams.cons(50, 30, 9, 12, 0, 3, -5, null).reverse().equiv(null, -5, 3, 0, 12, 9, 30, 50));
  }

  /** Test for {@link Stream#maximum()} */
  @Test
  public void testMaximum() throws Exception {
    String max = Streams
      .cons(
        _(new Object(), 10, "hello"),
        _(new Object(), 12, "foo"),
        _(new Object(), 9, "bye"),
        _(new Object(), 6, ""))
      .maximumOn(Tuples.<Integer> second())
      ._2();
    assertEquals("foo", max);
    assertEquals(150, (int) Streams.cons(90, 10, 30, 6, 150, 65).maximum());
  }

  /** Test for {@link Stream#minimum()} */
  @Test
  public void testMinimum() throws Exception {
    assertEquals(6, (int) Streams.cons(90, 10, 30, 6, 150, 65).minimum());
  }

  /**
   * Test for
   * {@link AbstractStream#groupOn(net.sf.staccatocommons.defs.Applicable, Applicable2)}
   */
  @Test
  public void testGroupOn() {
    // Gets the sum of integers grouped by their modulo-3
    Map<BigInteger, BigInteger> mapReduce = Streams.cons(i(2), i(2), i(9), i(5)) //
      .groupOn(new AbstractFunction<BigInteger, BigInteger>() {
        public BigInteger apply(BigInteger arg) {
          return arg.remainder(i(3));
        }
      }, Reductions.from(bigInteger().add()));

    assertEquals(i(9), mapReduce.get(i(0)));
    assertFalse(mapReduce.containsKey(i(1)));
    assertEquals(i(2 + 2 + 5), mapReduce.get(i(2)));
  }

  /**
   * Test for
   * {@link AbstractStream#groupOn(net.sf.staccatocommons.defs.Applicable, Applicable2)}
   */
  @Test
  public void testGroupOnReduction() {
    // Gets the sum of integers grouped by their modulo-3
    Map<BigInteger, BigInteger> mapReduce = Streams.cons(i(2), i(2), i(9), i(5)) //
      .groupOn(new AbstractFunction<BigInteger, BigInteger>() {
        public BigInteger apply(BigInteger arg) {
          return arg.remainder(i(3));
        }
      }, Reductions.sum(bigInteger()));

    assertEquals(i(9), mapReduce.get(i(0)));
    assertFalse(mapReduce.containsKey(i(1)));
    assertEquals(i(2 + 2 + 5), mapReduce.get(i(2)));
  }

  /**
   * Test for {@link AbstractStream#sort()}
   * 
   * @throws Exception
   */
  @Test
  public void testSort() throws Exception {
    Stream<Integer> sort = Streams.cons(10, 20, 6, 9, 18, 6, 26, 32).sort();
    assertEquals(6, (int) sort.first());
    assertEquals(32, (int) sort.last());
    assertTrue(sort.equiv(6, 6, 9, 10, 18, 20, 26, 32));
  }

  /**
   * Test that isEmpty is repeatable in intersperse
   */
  @Test
  public void testIntersperseEmptyRepeatable() throws Exception {
    Stream<Integer> s = Streams.cons(1, 3).intersperse(0);
    assertFalse(s.isEmpty());
    assertFalse(s.isEmpty());
    assertFalse(s.isEmpty());
    assertFalse(s.isEmpty());

    s = s.tail();
    assertFalse(s.isEmpty());
    assertFalse(s.isEmpty());

    s = s.tail();
    assertFalse(s.isEmpty());

    s = s.tail();
    assertTrue(s.isEmpty());
  }

  /** Test that no StackOverflow is raised on large, recursive streams */
  @Test
  public void testRecursiveLongStream() throws Exception {
    assertEquals(90000, Streams.iterate(1, add(1)).intersperse(0).take(90000).size());
  }

  /** Test that no StackOverflow is raised on large, flat mapped streams */
  @Test
  // FIXME
  @Ignore("Extremly time-consuming when running cobertura")
  public void testLongFlatMapStream() throws Exception {
    Streams.enumerate(1, 4000).cross(Streams.enumerate(1, 4000)).size();
  }

  /** Tests simple crossing */
  @Test
  public void testCross() throws Exception {
    assertTrue(Streams
      .enumerate(1, 20)
      .cross(Streams.enumerate(20, 40))
      .equiv(Iterables.cross(Streams.enumerate(1, 20), Streams.enumerate(20, 40))));
  }
  /***/
  @Test
  public void crossArrayOverloadingAnswersCartesianProduct() throws Exception {
    Streams.cons(0, 1).cross(0, 1).equiv(_(0, 0), _(0, 1), _(1, 0), _(1, 1));
  }

  /**
   * Tests full-crossing
   * 
   * @throws Exception
   */
  @Test
  public void testFullCross() throws Exception {
    assertEquals(
      "[[1, 3, 5], [1, 3, 6], [1, 4, 5], [1, 4, 6], [2, 3, 5], [2, 3, 6], [2, 4, 5], [2, 4, 6]]", //
      Streams
        .cons(1, 2)
        .crossStreams(Streams.<Stream<Integer>> cons(Streams.cons(3, 4), Streams.cons(5, 6)))
        .printString());
  }

  /**
   * Tests {@link Stream#isBefore(Object, Object)}
   */
  @Test
  public void testIsBefore() throws Exception {
    assertFalse(Streams.cons(90, 2, 6, 3, 6).isBefore(6, 2));
    assertTrue(Streams.cons(90, 2, 6, 3, 6).isBefore(2, 6));
  }

  /**
   * Tests
   * {@link Stream#equivOn(net.sf.staccatocommons.defs.Applicable, Iterable)}
   */
  @Test
  public void testEquivOn() throws Exception {
    // Test that both streams have as as elements collections of the same size
    assertTrue(Streams//
      .cons(Arrays.asList(10, 20), Arrays.asList(5, 26, 9))
      .equivOn(new AbstractFunction<Collection, Integer>() {
        public Integer apply(Collection arg) {
          return arg.size();
        }
      }, Arrays.asList(0, 0), Arrays.asList(5, 6, 96)));
  }

  /**
   * Tests that equivalence test works on streams of streams
   * 
   * @throws Exception
   */
  @Test
  public void testEquivStreamOfStreams() throws Exception {
    assertTrue(Streams.cons(Streams.enumerate(2, 4), Streams.cons(4, 4, 4)).equiv(
      Streams.cons(Streams.cons(2, 3, 4), Streams.repeat(4).take(3))));
  }

  /**
   * Tests the {@link Stream#skipIndex(int)} method
   * 
   * @throws Exception
   */
  @Test
  public void skipIndex() throws Exception {
    assertFalse(Streams.cons("hello", "hola", "hallo", "ohayou").skipIndex(2).contains("hallo"));
  }

  /**
   * Tests that empty is repeatable for filter operation
   */
  @Test
  public void testEmptyRepeatable() throws Exception {
    Stream<Integer> s = Streams.from(Arrays.asList(11, 6, 9, 36, 39, 1, 32, 1).iterator()).filter(
      Compare.greaterThan(38));
    assertFalse(s.isEmpty());
    assertFalse(s.isEmpty());

  }
  /***/
  @Test
  public void sliceEqualLowerAndUpperBoundsIsAnEmptyStream() throws Exception {
    assertTrue(Streams.enumerate(1, 20).slice(5, 5).toList().isEmpty());
  }
  
  /***/
  @Test
  public void sliceIsCompatibleWithSubstringForEnoughLongStreams() throws Exception {
    assertEquals("urge", Streams.from("hamburger").slice(4, 8).joinStrings(""));
    assertEquals("mile", Streams.from("smiles").slice(1, 5).joinStrings(""));
  }
  
  /***/
  @Test
  public void sliceWorksWithUpperBoundBeyondSize() throws Exception {
    assertEquals("world!", Streams.from("hello world!").slice(6, 309).joinStrings(""));
  }
    

  /** test for {@link AbstractStream#printString()} */
  @Test
  public void printStingNotEmptyAnswersNonEmptyBracketString() {
    assertEquals(Streams.cons(4, 5, 9).printString(), "[4, 5, 9]");
  }

  /** test for {@link AbstractStream#printString()} */
  @Test
  public void printStreamEmptyAnswersEmptyBracketString() {
    assertEquals(Streams.empty().printString(), "[]");
  }
  
  /***/
  @Test
  public void zipArrayOverloadingAnswersPairsOfElementsOfBothStreams() throws Exception {
    Streams.cons(0, 1, 2).zip('a', 'b', 'c').equiv(_(0, 'a'), _(1, 'b'), _(2, 'c'));
  }
  
  /***/
  @Test
  public void zipWithIteratorOverloadingAppliesBinaryFunctionToPairsOfElementsOfBothStreams() throws Exception {
    Streams.cons(1, 2).zipWith(integer().add(), 4, 5).equiv(5, 7);
  }
  
  /***/
  @Test
  public void insertBeforeIndexAddsElementAtIndexMinusOneOrEnd() throws Exception {
    assertEquals(Streams.cons(4, 5, 0, 6).toList(), Streams.cons(4, 5, 6).insertBeforeIndex(0, 2).toList());
    assertEquals(Streams.cons(1, 1, 0, 1, 1).toList(), Streams.repeat(1).take(4).insertBeforeIndex(0, 2).toList());
    assertEquals(Streams.cons(1, 1, 1, 1, 0).toList(), Streams.repeat(1).take(4).insertBeforeIndex(0, 20).toList());
  }
  
 
  /***/
  @Test
  public void inserBeforeAddsElementBeforeReferenceOrAtEnd() throws Exception {
    assertEquals("x,a,b,c", Streams.cons('a', 'b', 'c').insertBefore('x', 'a').joinStrings(","));
    assertEquals("a,b,x,c", Streams.cons('a', 'b', 'c').insertBefore('x', 'c').joinStrings(","));
    assertEquals("a,b,c,x", Streams.cons('a', 'b', 'c').insertBefore('x', 'd').joinStrings(","));
  }
 
  /***/
  @Test
  public void inserBeforeIsNullSafe() throws Exception {
    assertTrue(Streams.cons('a', 'b', 'c').insertBefore(null, 'a').equiv(null, 'a', 'b', 'c'));
    assertTrue(Streams.cons('a', 'b', 'c').insertBefore('x', null).equiv('a', 'b', 'c', 'x'));
    assertTrue(Streams.cons('a', 'b', 'c', null).insertBefore('x', null).equiv('a', 'b', 'c', 'x', null));
  }
  
  /*
   * TODO
  @Test
  public void splitBeforeIndex() throws Exception {
    Tuple2<Stream<Character>, Stream<Character>> streams = Streams.from("hello world!").splitBeforeIndex(5);
    assertEquals(" world!", streams.second().joinStrings(""));
    assertEquals("hello", streams.first().joinStrings(""));

    streams = Streams.from("hello world!").splitBeforeIndex(25);
    assertEquals("hello world!", streams.first().joinStrings(""));
    assertEquals("", streams.second().joinStrings(""));
    
    assertTrue(Streams.cons(4, 5, 6, 9).drop(100).isEmpty());

  }
  
  @Test
  public void splitBefore() throws Exception {
    Tuple2<Stream<Character>, Stream<Character>> streams = Streams.from("aaaaabbbbbcddddd").splitBefore('d');
    assertEquals("aaaaabbbbbc", streams.first().joinStrings(""));
    assertEquals("ddddd", streams.second().joinStrings(""));
    
    streams = Streams.from("aaaaabbbbbcddddd").splitBefore('x');
    assertEquals("", streams.second().joinStrings(""));
    assertEquals("aaaaabbbbbcddddd", streams.first().joinStrings(""));
  }*/

  /***/
  @Test
  public void containsBeforeIfFirstIsContainedAndAppearsBeforSecond() throws Exception {
    assertFalse(Streams.from("abcd").containsBefore('c', 'a'));
    assertFalse(Streams.from("abcd").containsBefore('x', 'd'));
    assertTrue(Streams.from("abcd").containsBefore('a', 'a'));
    assertTrue(Streams.from("abcd").containsBefore('b', 'b'));
    assertTrue(Streams.from("abcd").containsBefore('a', 'b'));
    assertTrue(Streams.from("abcd").containsBefore('b', 'd'));
    assertFalse(Streams.from("abcd").containsBefore('x', 'x'));
  }
  
  /***/
  @Test
  public void containsBeforeIndexIfFirstIsContainedAndBeforeIndex() throws Exception {
    assertTrue(Streams.from("abcd").containsBeforeIndex('d', 10));
    assertTrue(Streams.from("abcd").containsBeforeIndex('a', 1));
    assertTrue(Streams.from("abcd").containsBeforeIndex('b', 3));
    assertTrue(Streams.from("abcd").containsBeforeIndex('b', 40));
    assertFalse(Streams.from("abcd").containsBeforeIndex('x', 2));
    assertFalse(Streams.from("abcd").containsBeforeIndex('a', 0));
  }
  
  /***/
  @Test
  public void testIntersects() throws Exception {
    assertTrue(Streams.cons(1, 2, 3, 4).intersects(4, 5, 6));
    assertFalse(Streams.cons(1, 2, 3, 4).intersects(23, 12, 54));
  }
  
  /***/
  @Test
  public void lookupAnswersAnElementWhenItsKeyMatches() throws Exception {
    assertEquals("hello", Streams.cons("bar", "hello", "world", "foo").lookup(5, Strings.length()));
  }
  
  /***/
  @Test
  public void lookupOrNoneAnswersNoneWhenNoKeyMatches() throws Exception {
    assertTrue(Streams.cons("bar", "hello", "world", "foo").lookupOrNone(null, Strings.length()).isUndefined());
  }
  
  /***/
  @Test
  public void lookupOrNoneWorksWithNullsAsKeys() throws Exception {
    assertEquals("foo", Streams.cons("foobar", "hello", null, "foo").lookup(3, Strings.length().nullSafe()));
  }
  
  /***/
  @Test
  public void skipNullFiltersNulls() throws Exception {
    assertEquals(Lists.from(4, 5), Streams.cons(4, 5, null).skipNull().toList());
  }

  /***/
  @Test
  public void skipAcceptsNull() throws Exception {
    assertEquals(Lists.from(4, 5), Streams.cons(4, 5, null).skip(null).toList());
  }
}
