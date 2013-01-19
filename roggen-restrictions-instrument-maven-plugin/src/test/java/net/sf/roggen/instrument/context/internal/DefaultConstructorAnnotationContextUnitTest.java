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


package net.sf.roggen.instrument.context.internal;

import static org.junit.Assert.assertSame;
import javassist.CtConstructor;

import net.sf.roggen.instrument.context.internal.DefaultConstructorAnnotationContext;

import org.junit.Test;

/**
 * @author flbulgarelli
 * 
 */
public class DefaultConstructorAnnotationContextUnitTest extends AbstractAnnotationContextUnitTest {

  /**
   * Test method for
   * {@link DefaultConstructorAnnotationContext#getConstructor()}.
   * 
   * @throws Exception
   */
  @Test
  public void testGetConstructor() throws Exception {
    DefaultConstructorAnnotationContext context = new DefaultConstructorAnnotationContext(
      getPool(),
      getLogger());
    CtConstructor constructor = getPool()
      .get("java.util.LinkedList")
      .getConstructors()[0];
    context.setConstructor(constructor);
    assertSame(constructor, context.getConstructor());
  }

}
