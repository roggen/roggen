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

package net.sf.staccatocommons.restrictions.check;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;

import net.sf.staccatocommons.restrictions.Restriction;

/**
 * A check annotation that signals the annotated element's size must be, at
 * minimum, the given value.
 * <p>
 * This annotation should only be applied to elements that understand a length
 * or size attribute, or length, or size method, like {@link Collections},
 * {@link String}s, arrays and SizeAware's from Staccato-Commons-Defs
 * </p>
 * 
 * @author flbulgarelli
 * @see Restriction
 */
@Restriction
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD })
public @interface MinSize {

  /**
   * The minimum value of the annotated element's size
   * 
   * @return the minimum size.
   */
  @Positive
  int value();

  /**
   * @return The variable name of the constrained element, or the empty string
   *         if unspecified. This value may help tools that analyze this
   *         annotation without access to source code.
   */
  String var() default "";
}
