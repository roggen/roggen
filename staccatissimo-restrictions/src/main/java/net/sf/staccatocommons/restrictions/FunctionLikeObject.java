/**
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

package net.sf.staccatocommons.restrictions;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * {@link FunctionLikeObject} is an annotation for objects that have
 * code-block/command/strategy/closure/lambda semantics, with understand mainly
 * a single message that evaluates it.
 * <p/>
 * {@link FunctionLikeObject} <strong>must</strong> be {@link ValueObject}s. It
 * is not necessary to annotate them that way, as it is implied.
 * <p/>
 * 
 * {@link FunctionLikeObject} <strong>should</strong> be {@link SideEffectFree}.
 * An obvious case where this is not generally possible is for
 * {@link FunctionLikeObject}s that return {@link Void}, which will be applied
 * for its side effects only
 * 
 * <p/>
 * Public available {@link FunctionLikeObject} <strong>should</strong> be
 * {@link Serializable}
 * 
 * <p/>
 * Methods that return {@link FunctionLikeObject}s <strong>must</strong> be
 * {@link NonNull} and {@link SideEffectFree}.
 * 
 * <p/>
 * In addition, methods that return {@link FunctionLikeObject}s and are
 * annotated with restrictions, alter {@link Restriction} semantic in some way:
 * they do not indicate that the method is restricted, but that the
 * applicative's method is restricted. For example, in the following code:
 * 
 * <pre>
 * &#064;NonNull
 * Thunk&lt;Integer&gt; foo();
 * </pre>
 * 
 * The {@link NonNull} annotations does not indicate that method foo() is
 * non-null, but that the applicative method of the returned Thunks, that is,
 * Thunk.value(), is non-null.
 * 
 * @author flbulgarelli
 * @since 2.3
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface FunctionLikeObject {

}
