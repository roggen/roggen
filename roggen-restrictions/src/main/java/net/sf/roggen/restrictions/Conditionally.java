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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;

/**
 * {@link Conditionally} is a restriction modifier that indicates that an
 * object may meet set of restrictions depending on some condition. There are
 * two standard scenarios:
 * <ol>
 * <li>When the object's class has generic type parameter, and the set of
 * restrictions depend on the concrete type used</li>
 * <li>When the object has as configurable attributes - subject to dependency
 * injection - and the type of such attributes does not make any assumptions
 * about such restrictions, perhaps they are specified in its subtypes or it
 * depends on instances</li>
 * </ol>
 * <p>
 * Types annotated as {@link Conditionally} denote that all their instances
 * grant to conditionally meet the set constraints, depending on the attributes
 * injected by constructor or setters, or in its constructor type parameters.
 * </p>
 * <p>
 * Typical example of <code>@Conditionally(ValueObject.class)</code> objects are
 * Lists returned by {@link Collections#singletonList(Object)}, which are
 * {@link ValueObject}s as long as its argument are too.
 * </p>
 * 
 * @author flbulgarelli
 */
@Documented
@Inherited
@Restriction
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Conditionally {

  /**
   * An array with interfaces and/or restriction annotations
   */
  Class[] value();
}
