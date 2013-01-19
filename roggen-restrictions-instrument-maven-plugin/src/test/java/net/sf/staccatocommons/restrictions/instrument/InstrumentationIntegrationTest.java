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


package net.sf.staccatocommons.restrictions.instrument;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.io.File;

import net.sf.staccatocommons.instrument.InstrumentationRunner;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class InstrumentationIntegrationTest {

  /** setup */
  @BeforeClass
  public static void before() throws Exception {
    InstrumentationRunner.runInstrumentation(new RestrictionConfigurer(true, true, false), new File("target/test-classes"), "");
  }

  /** Test the {@link ConstantHandler} */
  @Test
  public void testConst() throws Exception {
    Assert.assertSame(Mock.getDateConstructor(), Mock.getDateConstructor());
  }

  /**
   * Test that the {@link ConstantHandler} does not process code with less than
   * two max locals
   */
  @Test
  public void testConstLessThan2MaxLocals() throws Exception {
    Assert.assertSame(Mock.getDateFieldAcess(), Mock.getDateFieldAcess());
    Assert.assertNotSame(Mock.getDateInvokationWithNoArgs(), Mock.getDateInvokationWithNoArgs());
    Assert.assertNotSame(Mock.getDateInvokationWithArg(), Mock.getDateInvokationWithArg());
  }

  /**
   * Test that Constant works in instance methods
   * 
   * @throws Exception
   */
  @Test
  public void testConstInstance() throws Exception {
    Mock m1 = new Mock();
    assertSame(m1.getBigDecimal(), m1.getBigDecimal());
    Mock m2 = new Mock();
    assertNotSame(m2.getBigDecimal(), m1.getBigDecimal());
  }

}
