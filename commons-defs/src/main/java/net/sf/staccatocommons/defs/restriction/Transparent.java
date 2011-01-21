/*
 Copyright (c) 2010, The Staccato-Commons Team

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; version 3 of the License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 */
package net.sf.staccatocommons.defs.restriction;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Transparent is a {@link Restriction} that applies to methods that are
 * referentially transparent - are {@link SideEffectFree} and return always
 * equal results for equals arguments.
 * <p>
 * When applied to types, it means that all its methods are {@link Transparent}.
 * As a consequence, statefull classes that are annotated this way
 * <strong>must</strong> be immutable, as otherwise, changes of state would
 * eventually impact on changes in results. Thus there is no reason no annotate
 * {@link Transparent} classes as {@link Immutable}, as it is implicit.
 * </p>
 * Being {@link SideEffectFree} necessary for being {@link Transparent}, there
 * is no reason no annotate {@link Transparent} classes or methods as
 * {@link SideEffectFree}, as it is implicit.
 * 
 * @author flbulgarelli
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Referential_transparency_(computer_science)">Referential
 *      transparency</a>
 * @see Transparent
 */
@Documented
@Inherited
@Restriction
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Transparent {

}