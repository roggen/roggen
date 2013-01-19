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

package net.sf.roggen.restrictions.instrument;

import javassist.CannotCompileException;
import net.sf.roggen.instrument.context.ClassAnnotationContext;
import net.sf.roggen.instrument.context.ConstructorAnnotationContext;
import net.sf.roggen.instrument.context.MethodAnnotationContext;
import net.sf.roggen.instrument.handler.ClassAnnotationHandler;
import net.sf.roggen.instrument.handler.ConstructorAnnotationHandler;
import net.sf.roggen.instrument.handler.MethodAnnotationHandler;
import net.sf.roggen.instrument.handler.deactivator.AbstractActivationAnnotationHandler;
import net.sf.roggen.restrictions.processing.IgnoreRestrictions;

/**
 * @author flbulgarelli
 * 
 */
public class IgnoreCheckHandler extends AbstractActivationAnnotationHandler<IgnoreRestrictions> implements
  ClassAnnotationHandler<IgnoreRestrictions>, ConstructorAnnotationHandler<IgnoreRestrictions>,
  MethodAnnotationHandler<IgnoreRestrictions> {

  /**
   * Creates a new {@link IgnoreCheckHandler}
   */
  public IgnoreCheckHandler() {
    super();
  }

  public Class<IgnoreRestrictions> getSupportedAnnotationType() {
    return IgnoreRestrictions.class;
  }

  public void preProcessAnnotatedMethod(IgnoreRestrictions annotation, MethodAnnotationContext context) {
    deactivateAll();
  }

  public void postProcessAnnotatedMethod(IgnoreRestrictions annotation, MethodAnnotationContext context) {
    activateAll();
  }

  public void preProcessAnnotatedConstructor(IgnoreRestrictions annotation, ConstructorAnnotationContext context) {
    deactivateAll();
  }

  public void postProcessAnnotatedConstructor(IgnoreRestrictions annotation, ConstructorAnnotationContext context) {
    activateAll();
  }

  @Override
  public void preProcessAnnotatedClass(IgnoreRestrictions annotation, ClassAnnotationContext context)
    throws CannotCompileException {
    deactivateAll();

  }

  @Override
  public void postProcessAnnotatedClass(IgnoreRestrictions annotation, ClassAnnotationContext context)
    throws CannotCompileException {
    activateAll();
  }

}
