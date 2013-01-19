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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import javassist.CtMethod;
import javassist.NotFoundException;
import net.sf.roggen.instrument.context.ArgumentAnnotationContext;
import net.sf.roggen.instrument.context.internal.DefaultArgumentAnnotationContext;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link DefaultArgumentAnnotationContext}
 * 
 * @author flbulgarelli
 * 
 */
public class DefaultArgumentAnnotationContextUnitTest extends AbstractAnnotationContextUnitTest {

  private ArgumentAnnotationContext context;
  private CtMethod method;

  /**
   * Instatiates the context
   * 
   * @throws NotFoundException
   **/
  @Before
  public void createContext() throws NotFoundException {
    context = new DefaultArgumentAnnotationContext(getPool(), getLogger());
    method = getPool().getMethod("java.util.Collections", "singleton");
    ((DefaultArgumentAnnotationContext) context).setBehavior(method);
    ((DefaultArgumentAnnotationContext) context).setParameterNumber(1);
  }

  /**
   * Test method for
   * {@link DefaultArgumentAnnotationContext#getArgumentBehavior()} .
   */
  @Test
  public void testGetArgumentBehavior() {
    assertSame(method, context.getArgumentBehavior());
  }

  /**
   * Test method for
   * {@link DefaultArgumentAnnotationContext#getArgumentNumber()} .
   */
  @Test
  public void testGetArgumentNumber() {
    assertEquals(1, context.getArgumentNumber());
  }

  /**
   * Test method for
   * {@link DefaultArgumentAnnotationContext#isConstructorArgument()} .
   */
  @Test
  public void testIsConstructorArgument() {
    assertFalse(context.isConstructorArgument());
  }

  /**
   * Test method for
   * {@link DefaultArgumentAnnotationContext#getArgumentIdentifier()} .
   */
  @Test
  public void testGetArgumentIdentifier() {
    assertEquals("$2", context.getArgumentIdentifier());
  }

}
