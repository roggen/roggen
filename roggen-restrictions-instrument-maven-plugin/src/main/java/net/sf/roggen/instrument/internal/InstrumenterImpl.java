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

package net.sf.roggen.instrument.internal;

import java.lang.annotation.Annotation;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import net.sf.roggen.instrument.config.InstrumentationMark;
import net.sf.roggen.instrument.config.InstrumenterConfiguration;
import net.sf.roggen.instrument.context.ClassAnnotationContext;
import net.sf.roggen.instrument.context.internal.DefaultArgumentAnnotationContext;
import net.sf.roggen.instrument.context.internal.DefaultClassAnnotationContext;
import net.sf.roggen.instrument.context.internal.DefaultConstructorAnnotationContext;
import net.sf.roggen.instrument.context.internal.DefaultMethodAnnotationContext;
import net.sf.roggen.instrument.handler.AnnotationHandler;
import net.sf.roggen.instrument.handler.ArgumentAnnotationHandler;
import net.sf.roggen.instrument.handler.ClassAnnotationHandler;
import net.sf.roggen.instrument.handler.ConstructorAnnotationHandler;
import net.sf.roggen.instrument.handler.MethodAnnotationHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flbulgarelli
 */
public class InstrumenterImpl implements InstrumenterConfiguration, Instrumenter {

  private final Logger logger = LoggerFactory.getLogger("Processor-Logger");

  private final AnnotationProcessor<ClassAnnotationHandler> classProcessor;
  private final AnnotationProcessor<MethodAnnotationHandler> methodProcessor;
  private final AnnotationProcessor<ArgumentAnnotationHandler> argumentProcessor;
  private final AnnotationProcessor<ConstructorAnnotationHandler> constructorProcessor;
  private InstrumentationMark instrumentationMark;
  private final ClassPool classPool;
  private int handlersCount;

  /**
   * Creates a new {@link InstrumenterImpl}
   * 
   * @param classPool
   * 
   * @param processors
   */
  public InstrumenterImpl(ClassPool classPool) {
    this.classProcessor = new AnnotationProcessor();
    this.methodProcessor = new AnnotationProcessor();
    this.argumentProcessor = new AnnotationProcessor();
    this.constructorProcessor = new AnnotationProcessor();
    this.classPool = classPool;
  }

  /**
   * @param instrumentationMark
   *          the instrumentationMark to set
   * @return this
   */
  public InstrumenterConfiguration setInstrumentationMark(InstrumentationMark instrumentationMark) {
    this.instrumentationMark = instrumentationMark;
    return this;
  }

  public InstrumenterConfiguration addAnnotationHanlder(AnnotationHandler handler) {
    if (handler instanceof ClassAnnotationHandler)
      classProcessor.addHandler((ClassAnnotationHandler) handler);

    if (handler instanceof ArgumentAnnotationHandler)
      argumentProcessor.addHandler((ArgumentAnnotationHandler) handler);

    if (handler instanceof ConstructorAnnotationHandler)
      constructorProcessor.addHandler((ConstructorAnnotationHandler) handler);

    if (handler instanceof MethodAnnotationHandler)
      methodProcessor.addHandler((MethodAnnotationHandler) handler);

    handlersCount++;
    return this;
  }

  /**
   * Ensures that at least a handler has being registered, and the
   * instrumentation mark was set
   */
  public void ensureConfigured() {
    if (instrumentationMark == null) {
      throw new IllegalStateException("Instrumentation mark is null");
    }
    if (handlersCount == 0) {
      throw new IllegalStateException("There must be at least one handler");
    }
  }

  /**
   * @param clazz
   * @throws ClassNotFoundException
   * @throws CannotCompileException
   */
  public void instrumentClass(final CtClass clazz) throws CannotCompileException, ClassNotFoundException {
    if (clazz.isInterface())
      return;

    if (alreadyProcessed(clazz)) {
      logger.debug("Class {} was already processed. Ignoring", clazz);
      return;
    }

    final ClassAnnotationContext context = //
    new DefaultClassAnnotationContext(classPool, logger, clazz);
    classProcessor.processUsing(clazz.getAvailableAnnotations(), new Executable2<Object, ClassAnnotationHandler>() {
      public void exec(Object annotation, ClassAnnotationHandler handler) throws Exception {
        handler.preProcessAnnotatedClass((Annotation) annotation, context);
      }
    });

    for (CtMethod method : clazz.getDeclaredMethods())
      if (!Modifier.isAbstract(method.getModifiers()))
        instrumentMethod(method);

    for (CtConstructor constructor : clazz.getDeclaredConstructors())
      instrumentConstructor(constructor);

    classProcessor.processUsing(clazz.getAvailableAnnotations(), new Executable2<Object, ClassAnnotationHandler>() {
      public void exec(Object annotation, ClassAnnotationHandler handler) throws Exception {
        handler.postProcessAnnotatedClass((Annotation) annotation, context);
      }
    });

    markAsProcessed(clazz);
  }

  private void markAsProcessed(CtClass clazz) {
    clazz.setAttribute(instrumentationMark.getMarkAttributeName(), //
      instrumentationMark.getMarkAttributeValue());
  }

  private boolean alreadyProcessed(CtClass clazz) {
    return clazz.getAttribute(instrumentationMark.getMarkAttributeName()) != null;
  }

  private void instrumentMethod(CtMethod method) throws CannotCompileException, ClassNotFoundException {
    final DefaultMethodAnnotationContext methodContext = new DefaultMethodAnnotationContext(classPool, logger);
    methodContext.setMethod(method);
    Object[] availableAnnotations = method.getAvailableAnnotations();

    methodProcessor.processUsing(availableAnnotations, new Executable2<Object, MethodAnnotationHandler>() {
      public void exec(Object annotation, MethodAnnotationHandler handler) throws Exception {
        handler.preProcessAnnotatedMethod((Annotation) annotation, methodContext);
      }
    });

    instrumentArguments(method);

    methodProcessor.processUsing(availableAnnotations, new Executable2<Object, MethodAnnotationHandler>() {
      public void exec(Object annotation, MethodAnnotationHandler handler) throws Exception {
        handler.postProcessAnnotatedMethod((Annotation) annotation, methodContext);
      }
    });
  }

  /**
   * @param constructor
   * @throws CannotCompileException
   * @throws ClassNotFoundException
   */
  private void instrumentConstructor(CtConstructor constructor) throws CannotCompileException, ClassNotFoundException {
    final DefaultConstructorAnnotationContext context = new DefaultConstructorAnnotationContext(classPool, logger);
    context.setConstructor(constructor);
    Object[] availableAnnotations = constructor.getAvailableAnnotations();

    constructorProcessor.processUsing(availableAnnotations, new Executable2<Object, ConstructorAnnotationHandler>() {
      public void exec(Object annotation, ConstructorAnnotationHandler handler) throws Exception {
        handler.preProcessAnnotatedConstructor((Annotation) annotation, context);
      }
    });

    instrumentArguments(constructor);

    constructorProcessor.processUsing(availableAnnotations, new Executable2<Object, ConstructorAnnotationHandler>() {
      public void exec(Object annotation, ConstructorAnnotationHandler handler) throws Exception {
        handler.postProcessAnnotatedConstructor((Annotation) annotation, context);
      }
    });
  }

  private void instrumentArguments(CtBehavior behaviour) throws CannotCompileException {
    Object[][] parameterAnnotations = behaviour.getAvailableParameterAnnotations();
    final DefaultArgumentAnnotationContext argumentContext = new DefaultArgumentAnnotationContext(classPool, logger);
    argumentContext.setBehavior(behaviour);
    for (int i = 0; i < parameterAnnotations.length; i++) {
      argumentContext.setParameterNumber(i);
      argumentProcessor.processUsing(parameterAnnotations[i], new Executable2<Object, ArgumentAnnotationHandler>() {
        public void exec(Object annotation, ArgumentAnnotationHandler handler) throws Exception {
          handler.processAnnotatedArgument((Annotation) annotation, argumentContext);
        }
      });
    }
  }

}
