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


package net.sf.roggen.restrictions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
  * Side effect free is a {@link Restriction} that applies to methods that have
 * no side effects. As a consequence, such methods <strong>must not</strong>
 * modify the state of its arguments, if any, nor the <code>this</code>
 * reference, if non-static, nor any attribute in scope.
 * <p>
 * When applied to types, it means that all its methods are
 * {@link SideEffectFree}. As a consequence, classes that are
 * annotated this must be {@link ValueObject}, as mutators are
 * inherently non-side-effect free. Thus there is no reason no annotate
 * {@link SideEffectFree} classes as {@link ValueObject}, as it is implied.
 * </p>
 * @author flbulgarelli
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Side_effect_(computer_science)">Side
 *      effect</a>
 * @since 3 
 * @author flbulgarelli
 *
 */
@Restriction
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface SideEffectFree {

}
