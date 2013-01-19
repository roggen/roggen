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
import net.sf.roggen.restrictions.check.NotEmpty;

/**
 * @author flbulgarelli
 * 
 */
public class NotEmptyHandler extends AbstractCheckAnnotationHandler<NotEmpty> {

  /**
   * Creates a new {@link NotEmptyHandler}
   */
  public NotEmptyHandler(boolean ignoreReturns) {
    super(ignoreReturns);
  }

  @Override
  public Class<NotEmpty> getSupportedAnnotationType() {
    return NotEmpty.class;
  }

  protected String createCheckCode(String argumentMnemonic, String argumentIdentifier, NotEmpty annotation,
    AnnotationContext context) {
    return String.format("that().isNotEmpty( \"%s\", %s)", argumentMnemonic, argumentIdentifier);
  }

  protected String getVarMnemonic(NotEmpty annotation) {
    return annotation.value();
  }

}
