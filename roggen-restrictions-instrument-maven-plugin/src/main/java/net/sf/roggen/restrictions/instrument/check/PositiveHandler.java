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

package net.sf.roggen.restrictions.instrument.check;

import net.sf.roggen.instrument.context.AnnotationContext;
import net.sf.roggen.restrictions.check.Positive;

/**
 * @author flbulgarelli
 * 
 */
public class PositiveHandler extends AbstractCheckAnnotationHandler<Positive> {

  /**
   * Creates a new {@link PositiveHandler}
   */
  public PositiveHandler(boolean ignoreReturns) {
    super(ignoreReturns);
  }

  public Class<Positive> getSupportedAnnotationType() {
    return Positive.class;
  }

  protected String createCheckCode(String argumentMnemonic, String argumentIdentifier, Positive annotation,
    AnnotationContext context) {
    return String.format("that().isPositive( \"%s\", %s)", argumentMnemonic, argumentIdentifier);
  }

  protected String getVarMnemonic(Positive annotation) {
    return annotation.value();
  }

}
