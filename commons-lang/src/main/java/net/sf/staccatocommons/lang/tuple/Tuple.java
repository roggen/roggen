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
package net.sf.staccatocommons.lang.tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import net.sf.staccatocommons.check.annotation.NonNull;
import net.sf.staccatocommons.defs.restriction.ConditionallyImmutable;
import net.sf.staccatocommons.defs.restriction.ConditionallySerializable;
import net.sf.staccatocommons.defs.restriction.Value;

/**
 * <p>
 * A {@link Tuple} is a fixed size sequence of heterogeneous objects. They are
 * {@link ConditionallyImmutable} and {@link ConditionallySerializable}. They
 * are comparable, as long as they components are, too.
 * </p>
 * <p>
 * Tuples are aimed to be used in those situations where an object that just
 * relates others together is needed. Such object does not encapsulate any
 * business concept nor any specific behavior except of {@link #toString()},
 * {@link #equals(Object)}, {@link #hashCode()} and
 * {@link #compare(Object, Object)}. Tuples are thus not intended to be used
 * everywhere nor extended, but there are some concrete scenarios where they are
 * useful:
 * </p>
 * <ul>
 * <li>
 * When immutable components are used, they are valid keys for hashed maps or
 * elements of hashed sets</li>
 * <li>When comparable components are used, they are valid keys for sorted maps
 * or elements of sorted sets and sorted lists</li>
 * <li>For debugging purposes, when it is needed to print to an string multiple
 * objects.</li>
 * <li>When a method needs to return a fixed amount of different results. For
 * example, using 2-components tuple, it is easy to implement a divmod method
 * that returns the quotient and modulus of an integral division, with the
 * signature <code>Pair&lt;Integer, Integer&gt; divMod(int x, int y)</code></li>
 * </ul>
 * 
 * Although it is possible to create tuples of arities from 2 to 4 invoking the
 * appropriate constructor, the recommended way of instantiating tuples is using
 * the family of class methods named <code>_</code>. Although it looks odd at
 * first glance, combining it with static imports produces quite clean code. For
 * example, using again the divMod method:
 * 
 * <pre>
 *  import static net.sf.staccatocommons.lang.tuple.Tuple._;
 *  
 *  ...
 *  Pair&lt;Integer, Integer&gt; divMod(int x, int y)
 *   return _(x/y, x%y)
 *  ...
 * 
 * </pre>
 * 
 * 
 * @author flbulgarelli
 * @see Pair
 * @see Triple
 * @see Quadruple
 */
@Value
@ConditionallyImmutable
@ConditionallySerializable
public abstract class Tuple implements Serializable {

	private static final long serialVersionUID = -3943649706502147516L;

	Tuple() {
	}

	/**
	 * Creates a new {@link Pair}
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param first
	 *          nullable.
	 * @param second
	 *          nullable
	 * @return a new {@link Pair}. Non null.
	 */
	@NonNull
	public static <T1, T2> Pair<T1, T2> _(T1 first, T2 second) {
		return new Pair<T1, T2>(first, second);
	}

	/**
	 * Creates a new {@link Triple}
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param first
	 *          nullable.
	 * @param second
	 *          nullable
	 * @param third
	 *          nullable
	 * @return a new {@link Triple}. Non null.
	 */
	@NonNull
	public static <T1, T2, T3> Triple<T1, T2, T3> _(T1 first, T2 second, T3 third) {
		return new Triple<T1, T2, T3>(first, second, third);
	}

	/**
	 * Creates a new {@link Quadruple}
	 * 
	 * @param <T1>
	 * @param <T2>
	 * @param <T3>
	 * @param <T4>
	 * @param first
	 *          nullable.
	 * @param second
	 *          nullable
	 * @param third
	 *          nullable
	 * @param fourth
	 *          nullable
	 * @return a new {@link Quadruple}. Non null.
	 */
	@NonNull
	public static <T1, T2, T3, T4> Quadruple<T1, T2, T3, T4> _(T1 first, T2 second, T3 third,
		T4 fourth) {
		return new Quadruple<T1, T2, T3, T4>(first, second, third, fourth);
	}

	/**
	 * Converts this tuple into an array
	 * 
	 * @return an new Object[] containing each of the elements of this tuple
	 */
	@NonNull
	public abstract Object[] toArray();

	/**
	 * Gets an unmodifiable list containing each components of this tuple as
	 * elements
	 * 
	 * @return a non null, unmodifiable list
	 */
	@NonNull
	public List<Object> toList() {
		return Arrays.asList(toArray());
	}

	protected static <T1> int compare(T1 a, T1 b) {
		return ((Comparable<T1>) a).compareTo(b);
	}

}