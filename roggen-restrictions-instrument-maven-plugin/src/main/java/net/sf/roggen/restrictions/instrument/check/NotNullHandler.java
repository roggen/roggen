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
import net.sf.roggen.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class NotNullHandler extends AbstractCheckAnnotationHandler<NonNull> {

  /**
   * Creates a new {@link NotNullHandler}
   */
  public NotNullHandler(boolean ignoreReturns) {
    super(ignoreReturns);
  }

  @Override
  public Class<NonNull> getSupportedAnnotationType() {
    return NonNull.class;
  }

  @Override
  protected String getVarMnemonic(NonNull nonNull) {
    return nonNull.value();
  }

  @Override
  protected String createCheckCode(String argumentMnemonic, String argumentIdentifier, NonNull annotation,
    AnnotationContext context) {
    return String.format("isNotNull( \"%s\", %s)", argumentMnemonic, argumentIdentifier);
  }

}
