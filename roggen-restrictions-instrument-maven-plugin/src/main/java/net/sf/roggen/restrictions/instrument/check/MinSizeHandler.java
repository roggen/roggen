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

import javassist.NotFoundException;
import net.sf.roggen.instrument.context.AnnotationContext;
import net.sf.roggen.restrictions.check.MinSize;

/**
 * @author flbulgarelli
 * 
 */
public class MinSizeHandler extends AbstractCheckAnnotationHandler<MinSize> {

  /**
   * Creates a new {@link MinSizeHandler}
   */
  public MinSizeHandler(boolean ignoreReturns) {
    super(ignoreReturns);
  }

  public Class<MinSize> getSupportedAnnotationType() {
    return MinSize.class;
  }

  protected String createCheckCode(String argumentMnemonic, String argumentIdentifier, MinSize annotation,
    AnnotationContext context) throws NotFoundException {
    return String.format(
      "that().isMinSize( \"%s\", %s, %s, %s)",
      argumentMnemonic,
      argumentIdentifier,
      annotation.value(),
      SizeAwareTypeCode.getSizeAwareCode(context));
  }

  protected String getVarMnemonic(MinSize annotation) {
    return annotation.var();
  }

}
