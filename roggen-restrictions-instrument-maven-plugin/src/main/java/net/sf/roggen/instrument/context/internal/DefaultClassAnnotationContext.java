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
import javassist.Modifier;
import javassist.NotFoundException;
import net.sf.roggen.instrument.context.ClassAnnotationContext;
import net.sf.roggen.instrument.internal.Ensure;
import net.sf.roggen.restrictions.check.NonNull;

import org.slf4j.Logger;

/**
 * @author flbulgarelli
 * 
 */
public class DefaultClassAnnotationContext extends AbstractAnnotationContext implements ClassAnnotationContext {

  private final CtClass clazz;

  /**
   * Creates a new {@link DefaultClassAnnotationContext}
   */
  public DefaultClassAnnotationContext(@NonNull ClassPool pool, @NonNull Logger logger, @NonNull CtClass clazz) {
    super(pool, logger);
    Ensure.isNotNull("clazz", clazz);
    this.clazz = clazz;
  }

  public CtClass getAnnotatedClass() {
    return clazz;
  }

  public CtClass getDeclaringClass() throws NotFoundException {
    return clazz.getDeclaringClass() != null ? clazz.getDeclaringClass() : clazz;
  }

  public CtClass getElementType() throws NotFoundException {
    return clazz;
  }

  @Override
  public boolean isPublic() {
    try {
      return Modifier.isPublic(getDeclaringClass().getModifiers());
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

}
