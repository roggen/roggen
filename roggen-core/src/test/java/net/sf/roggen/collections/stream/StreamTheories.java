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

package net.sf.roggen.collections.stream;

import static net.sf.roggen.lang.predicate.Predicates.equal;
import static net.sf.roggen.lang.predicate.Predicates.false_;
import static net.sf.roggen.lang.predicate.Predicates.isInstanceOf;
import static net.sf.roggen.lang.predicate.Predicates.notNull;
import static net.sf.roggen.lang.predicate.Predicates.true_;
import static net.sf.roggen.numbers.NumberTypes.integer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.util.NoSuchElementException;

import net.sf.roggen.collections.EmptySourceException;
import net.sf.roggen.collections.stream.Stream;
import net.sf.roggen.collections.stream.Streams;
import net.sf.roggen.defs.Evaluable;
import net.sf.roggen.defs.Executable;
import net.sf.roggen.defs.partial.EmptyAware;
import net.sf.roggen.defs.predicate.Predicate;
import net.sf.roggen.lang.Compare;
import net.sf.roggen.lang.block.Block;
import net.sf.roggen.lang.predicate.AbstractPredicate;
import net.sf.roggen.lang.predicate.Predicates;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.RunWith;

/**
 * @author flbulgarelli
 */
@RunWith(Theories.class)
public abstract class StreamTheories {

  /** predicates for testing */
  @DataPoints
  public static final Predicate[] PREDICATES = new Predicate[] { Predicates.equal(5),
      isInstanceOf(Integer.class).and(Compare.greaterThan(90)), //
      false_(), //
      true_(), //
      notNull(), //
      equal(26), //
  };

  /** Sizes for testing */
  @DataPoints
  public static final int[] SIZES_OR_INDICES = new int[] { 0, 1, 4, 90 };

  private boolean emptyImpossible;

  /**
   */
  @Theory
  public final void sizeOfStreamReturnedByTakeIsLessOrEqualToTakeArgument(int size, Stream<?> stream) {
    assertTrue(stream.take(size).size() <= size);
  }

  /**
   */
  @Theory
  public final void reduceFailsWithEmptySourceExceptionOnEmptyStream(Stream<Integer> stream) {
    assumeEmptyAndExpect(stream, EmptySourceException.class, new Block<Stream>() {
      public void exec(Stream argument) {
        argument.reduce(integer().add());
      }
    });
  }

  /**
   */
  @Theory
  public final void anyOrNoneIsDefinedOnNonEmptyStream(Stream<?> stream) {
    assumeTrue(!stream.isEmpty());
    assertTrue(stream.anyOrNone().isDefined());
  }

  /**
   */
  @Theory
  @Test(expected = NoSuchElementException.class)
  public final void findFailsWithNoSuchElementExceptionOnFalsePredicate(Stream<?> stream) {
    stream.find(Predicates.false_());
  }
  
  /**
   */
  @Theory
  public final void findFailsWithEmptySourceExceptionOnEmptyStream(Stream<?> stream) {
    assumeEmptyAndExpect(stream, EmptySourceException.class, new Executable<Stream>() {
      public void exec(Stream stream) {
        stream.find(Predicates.true_());
      }
    });
  }

  /**
   * @param stream
   */
  @Theory
  public final void allIsTrueOnTruePredicate(Stream<?> stream) {
    assertTrue(stream.all(Predicates.true_()));
  }

  /** Test for any */
  @Theory
  public final <A> void emptyStreamSatisfyAnyPredicate(Stream<A> stream, final Evaluable<A> predicate) {
    assumeEmpty(stream, new Block<Stream>() {
      public void exec(Stream stream) {
        assertTrue(stream.all(predicate));
      }
    });
  }

  /**
   */
  @Theory
  public final <A> void anyIsTrueForTruePredicateInNonEmptyStream(Stream<A> stream) {
    assumeTrue(!stream.isEmpty());
    assertTrue(stream.any(Predicates.true_()));
  }

  /**
   */
  @Theory
  public final <A> void anyFailsWithEmptySourceExceptionOnEmptyStream(Stream<A> stream) {
    assumeEmptyAndExpect(stream, EmptySourceException.class, new Executable<Stream>() {
      public void exec(Stream stream) {
        stream.any();
      }
    });
  }

  /** Test that any in an empty stream returns false always */
  @Theory
  public final <A> void emptyStreamsSatisfyNoPredicate(Stream<A> stream, final Evaluable<A> predicate) {
    assumeEmpty(stream, new Block<Stream>() {
      public void exec(Stream stream) {
        assertFalse(stream.any(predicate));
      }
    });
  }

  /** Test that empty streams have no any element */
  @Theory
  public final void anyOrNullEqualsNullInEmptyStream(Stream<?> stream) {
    assumeEmpty(stream, new Block<Stream>() {
      @Override
      public void exec(Stream stream) {
        assertNull(stream.anyOrNull());
      }
    });

  }

  /** Test that decons succeeds alwas on non empty streams */
  @Theory
  public final <A> void deconsSuceedsInNonEmptyStream(Stream<A> stream) throws Exception {
    assumeTrue(!stream.isEmpty());
    assertNotNull(stream.decons());
  }

  /** test that decons fails on empty streams */
  @Theory
  public final <A> void deconsFailsOnEmptyStreamWithEmptySourceException(Stream<A> stream) throws Exception {
    assumeEmptyAndExpect(stream, EmptySourceException.class, new Block<Stream>() {
      public void exec(Stream stream) {
        stream.decons();
      }
    });
  }

  /** Test that empty streams have no tail */
  @Theory
  public void tailFailsInEmptyStreamWithEmptySourceException(Stream<?> stream) {
    assumeEmptyAndExpect(stream, EmptySourceException.class, new Block<Stream>() {
      public void exec(Stream stream) {
        stream.tail();
      }
    });
  }

  /** Test that empty streams have no head */
  @Theory
  public void headFailsInEmptyStreamWithEmptySourceException(Stream<?> stream) {
    assumeEmptyAndExpect(stream, EmptySourceException.class, new Block<Stream>() {
      public void exec(Stream stream) {
        stream.head();
      }
    });
  }

  /** Tests that if **/
  @Theory
  public void emptyIsAlwaysConsistent(Stream<?> stream) throws Exception {
	  
    assertTrue(Streams.repeat(stream).map(new AbstractPredicate<EmptyAware>(){
		public boolean eval(EmptyAware argument) {
			return argument.isEmpty();
		}}).take(10).allEquiv());
  }

  /** Tests that memorizing grants repeatable iteration order */
  @Theory
  public <A> void memorizeGrantsRepeatableIterationOrder(Stream<A> stream) throws Exception {
    Stream<A> memorized = stream.memoize();
    assertEquals(memorized.isEmpty(), memorized.isEmpty());
    assertTrue(memorized.equiv(memorized));
  }

  /** Ignores test that require empty streams */
  public void emptyIsImpossible() {
    emptyImpossible = true;
  }

  protected void assumeEmpty(Stream<?> stream, Executable<Stream> block) {
    if (emptyImpossible)
      return;
    assumeTrue(stream.isEmpty());
    block.exec(stream);
  }
  
  /***/
  @Theory
  public void insertBeforeIndexAssureElementIsBeforeIndex(int index, int element, Stream<Object> stream) throws Exception {
    assertTrue(stream.insertBeforeIndex(element, index).containsBeforeIndex(element, index + 1));
  }
  
  /***/
  @Theory
  public void insertBeforeReferenceAssureElementIsBeforeReference(int element, int ref, Stream<Object> stream) throws Exception {
    assertTrue(stream.insertBefore(element, ref).containsBefore(element, ref));
  }

  protected void assumeEmptyAndExpect(Stream<?> stream, final Class<? extends Exception> exception, final Executable<Stream> block) {
    try {
      assumeEmpty(stream, new Executable<Stream>(){
        public void exec(Stream argument) {
          block.exec(argument);
          fail("Expected exception " + exception);
        }});
    } catch (AssumptionViolatedException e) {
      throw e;
    } catch (Exception e) {
      if (e.getClass() != exception) {
        fail("Unexpected exception " + e);
      }
    }
  }
}
