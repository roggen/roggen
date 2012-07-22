/*
 Copyright (c) 2012, The Staccato-Commons Team

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; version 3 of the License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 */
package net.sf.staccatocommons.restrictions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link ValueObject} annotation describes objects with value semantic.
 * 
 * That is:
 * <ul>
 * <li>Its identity is not important: which means that you can use that object
 * or another one with same value, behavior plus state, interchangabily. Thus,
 * it has no sense to test two value objects for identity with {@code ==}
 * operator. Two objects with the same value behave exactly the same way, and
 * <strong>must</strong> be treated indistinctly</li>
 * <li>Are immutable: are stateless or its state is constant. This is a
 * consequence of previous item. In order to ensure this, they
 * <strong>must</strong> be unmodifiable by exposing no mutator messages, nor
 * any non-final attribute. The <strong>should</strong> also have all its fields
 * final. They <strong>may</strong> implement lazy initializations</li>
 * 
 * </ul>
 * 
 * <p/>
 * Typical cases of {@link ValueObject}s provided by the JDK are {@link Number}
 * s, {@link String}s and unmodifiable collections with immutable elements.
 * </p>
 * {@link ValueObject} <strong>should</strong> implement {@link #toString()} in
 * order to reflect their class and state. </p> {@link ValueObject}
 * <strong>may</strong>, and typically do, override {@link #equals(Object)}s and
 * {@link #hashCode()}, that is, are {@link EquivObject}.
 * {@link FunctionLikeObject}s, for instance, are {@link ValueObject}s that are
 * not {@link EquivObject}s.
 * 
 * <p />
 * </p>
 * {@link ValueObject}s <strong>must</strong> be {@link ThreadSafe}. This is
 * inherently true when no lazy initialization is implemented, but must be
 * ensured by the {@link ValueObject} developer implements it.
 * 
 * <p/>
 * 
 * {@link ValueObject} <strong>should</strong> only understand
 * {@link SideEffectFree} messages.
 * 
 * <p />
 * 
 * {@link ValueObject} annotation is applied to types whose instances are
 * {@link ValueObject}s, or to methods that return {@link ValueObject}s.
 * 
 * <p />
 * 
 * @see Restriction
 * @author flbulgarelli
 * @since 3
 */
@Documented
@Inherited
@Restriction
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface ValueObject {

}
