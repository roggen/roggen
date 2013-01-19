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

import java.util.Collection;
import java.util.LinkedList;

import net.sf.roggen.instrument.handler.AnnotationHandler;

/**
 * @author flbulgarelli
 * 
 */
class AnnotationProcessor<HandlerType extends AnnotationHandler> {

  private final Collection<HandlerType> handlers;

  /**
   * Creates a new {@link AnnotationProcessor}
   */
  public AnnotationProcessor() {
    this.handlers = new LinkedList<HandlerType>();
  }

  public void processUsing(final Object[] annotations, Executable2<Object, HandlerType> block) {
    for (Object annotation : annotations)
      for (HandlerType handler : handlers) {
        if (handler.getSupportedAnnotationType().isAssignableFrom(annotation.getClass())) {
          try {
            block.exec(annotation, handler);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
      }
  }

  /** Adds a handler */
  public void addHandler(HandlerType handler) {
    this.handlers.add(handler);
  }

}
