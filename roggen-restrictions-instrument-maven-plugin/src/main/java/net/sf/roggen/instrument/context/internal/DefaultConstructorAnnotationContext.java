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

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.Modifier;
import javassist.NotFoundException;
import net.sf.roggen.instrument.context.ConstructorAnnotationContext;
import net.sf.roggen.restrictions.check.NonNull;

import org.slf4j.Logger;

/**
 * @author flbulgarelli
 * 
 */
public class DefaultConstructorAnnotationContext extends AbstractAnnotationContext implements
  ConstructorAnnotationContext {

  private CtConstructor constructor;

  /**
   * Creates a new {@link DefaultConstructorAnnotationContext}
   */
  public DefaultConstructorAnnotationContext(@NonNull ClassPool pool, @NonNull Logger logger) {
    super(pool, logger);
  }

  /**
   * @return the constructor
   */
  public CtConstructor getConstructor() {
    return constructor;
  }

  public CtClass getDeclaringClass() {
    return getConstructor().getDeclaringClass();
  }

  /**
   * @param constructor
   *          the constructor to set
   */
  public void setConstructor(CtConstructor constructor) {
    this.constructor = constructor;
  }

  public CtClass getElementType() throws NotFoundException {
    return constructor.getDeclaringClass();
  }

  @Override
  public boolean isPublic() {
    return Modifier.isPublic(getConstructor().getModifiers());
  }
}
