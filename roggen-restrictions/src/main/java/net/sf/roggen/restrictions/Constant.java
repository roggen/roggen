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

import net.sf.roggen.restrictions.check.NonNull;

/**
 * {@link Restriction} that denotes that the return value of a method is always
 * the same object. <strong>This does not necessary mean that the returned
 * object is a singleton</strong>.
 * <p>
 * {@link Constant} methods <strong>must</strong> be {@link SideEffectFree}.
 * They do not need to be annotated with that annotation too, as it is implied.
 * </p>
 * <p>
 * Constant methods <strong>must</strong> be not null, thus they do not need to
 * be annotated as {@link NonNull}, as it is implied
 * </p>
 * 
 * @author flbulgarelli
 */
@Documented
@Restriction
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Constant {

}
