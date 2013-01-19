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

package net.sf.roggen.lang.block;

import static junit.framework.Assert.*;

import java.io.IOException;

import net.sf.roggen.defs.Executable3;
import net.sf.roggen.defs.Thunk;
import net.sf.roggen.lang.SoftException;
import net.sf.roggen.lang.block.Block3;
import net.sf.roggen.testing.junit.jmock.JUnit4MockObjectTestCase;

import org.apache.commons.lang.mutable.MutableInt;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class Block3UnitTest extends JUnit4MockObjectTestCase {

  private Block3<MutableInt, String, Character> block;
  private Executable3<MutableInt, String, Character> executable, otherExecutable;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    executable = mock(Executable3.class);
    otherExecutable = mock(Executable3.class, "other");
    block = new Block3<MutableInt, String, Character>() {
      @Override
      public void exec(MutableInt arg1, String arg2, Character arg3) {
        executable.exec(arg1, arg2, arg3);
      }
    };
  }

  /**
   * Test method for {@link Block3#exec(Object, Object, Object)} when softExec
   * is overriden
   */
  @Test(expected = SoftException.class)
  public void testSoftExec() throws Exception {
    block = new Block3<MutableInt, String, Character>() {
      @Override
      protected void softExec(MutableInt arg1, String arg2, Character arg3) throws Exception {
        throw new IOException();
      }
    };
    block.exec(new MutableInt(5), "", 'a');
  }

  /**
   * Test for {@link Block3#apply(Object)} and
   * {@link Block3#apply(Object, Object)}
   */
  @Test
  public void testApply() throws Exception {
    checking(new Expectations() {
      {
        exactly(3).of(executable).exec(new MutableInt(5), "", 'a');
      }
    });
    block.exec(new MutableInt(5), "", 'a');
    block.apply(new MutableInt(5)).exec("", 'a');
    block.apply(new MutableInt(5), "").exec('a');
  }

  /**
   * Test method for {@link Block3#then(Executable3)} .
   */
  @Test
  public void testThen() {
    final MutableInt mi = new MutableInt(5);
    checking(new Expectations() {
      {
        one(executable).exec(new MutableInt(5), "", 'a');
        will(new IncrementAction(mi));
        one(otherExecutable).exec(new MutableInt(6), "", 'a');
      }
    });
    block.then(otherExecutable).exec(mi, "", 'a');
    assertEquals(6, mi.getValue());
  }

  /** Test for {@link Block3#delayed(Object, Object, Object)} */
  @Test
  public void testDelayed() {
    MutableInt mi = new MutableInt(10);
    Thunk<Void> delayed = block.delayed(mi, "hello", 'a');
    checking(new Expectations() {
      {
        exactly(2).of(executable).exec(new MutableInt(10), "hello", 'a');
      }
    });
    delayed.value();
    delayed.value();
  }
}
