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

package net.sf.roggen.lang.thunk;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;

import net.sf.roggen.defs.Thunk;
import net.sf.roggen.lang.Option;
import net.sf.roggen.lang.SoftException;
import net.sf.roggen.lang.thunk.Thunks;
import net.sf.roggen.testing.junit.jmock.JUnit4MockObjectTestCase;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class ThunksUnitTest extends JUnit4MockObjectTestCase {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * Test method for
   * {@link net.sf.roggen.lang.thunk.Thunks#constant(java.lang.Object)}
   * .
   */
  @Test
  public void testConstant() {
    Object value = new Object();
    assertSame(value, Thunks.constant(value).value());
  }

  /** Test method for {@link Thunks#null_()} */
  @Test
  public void testNull_() throws Exception {
    assertNull(Thunks.null_().value());
  }

  /** Test method for {@link Thunks#from(Callable)} */
  @Test
  public void testCallable() throws Exception {
    final Callable<Integer> callable = mock(Callable.class);
    checking(new Expectations() {
      {
        one(callable).call();
        will(returnValue(50));
      }
    });
    Thunk<Integer> callableProvider = Thunks.from(callable);
    assertEquals(50, (int) callableProvider.value());
  }

  /** Test method for {@link Thunks#from(Callable)} */
  @Test(expected = SoftException.class)
  public void testCallableException() throws Exception {
    final Callable<Integer> callable = mock(Callable.class);
    checking(new Expectations() {
      {
        one(callable).call();
        will(throwException(new IOException()));
      }
    });
    Thunks.from(callable).value();
  }

  /**
   * Test method for
   * {@link net.sf.roggen.lang.thunk.internal.CallableThunk#value()}
   * when callable throws an exception.
   */
  @Test(expected = SoftException.class)
  public void testCallableValueException() {
    Thunks.from(new Callable<String>() {
      @Override
      public String call() throws Exception {
        throw new IOException();
      }
    }).value();
  }

  /**
   * Test method for
   * {@link net.sf.roggen.lang.thunk.internal.CallableThunk#value()}
   * when call succeeds.
   */
  @Test
  public void testCallableValueOK() {
    assertEquals("Hello", Thunks.from(new Callable<String>() {
      @Override
      public String call() throws Exception {
        return "Hello";
      }
    }).value());
  }

  /**
   * Test method for {@link Thunks#from(Runnable)}
   */
  @Test
  public void testRunnable() {
    final Runnable runnable = mock(Runnable.class);
    checking(new Expectations() {
      {
        one(runnable).run();
      }
    });
    assertNull(Thunks.from(runnable).value());
  }

  /** Test method for {@link Thunks#currentDate()} */
  @Test
  public void testCurrentDate() throws Exception {
    Date currentTime = Thunks.currentDate().value();
    assertNotNull(currentTime);
    assertNotSame(currentTime, Thunks.currentDate());
  }

  /**
   * Test for {@link Thunks#value()}
   */
  @Test
  public void testValue() throws Exception {
    assertEquals("Hello", Thunks.<String> value().apply(Option.some("Hello")));
  }
  
  /**
   * Test that {@link Thunks#fail(String, Object...)} actually fails when evaluated
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFail() throws Exception {
    Thunks.fail("Something %s happend", "wrong").value();
  }
}
