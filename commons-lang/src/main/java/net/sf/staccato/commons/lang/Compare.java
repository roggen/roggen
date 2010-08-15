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
package net.sf.staccato.commons.lang;

import java.util.Comparator;

import org.apache.commons.lang.ObjectUtils;

/**
 * Class methods that implement comparisons for {@link Comparable}s, like
 * between(max, min) and in(collection)
 * 
 * @author flbulgarelli
 * 
 */
public class Compare {

	/**
	 * Tests that given three {@link Comparable}s element, min and max, is true
	 * that:
	 * 
	 * <pre>
	 * min &lt;= element &lt;= max
	 * </pre>
	 * 
	 * @param <T>
	 *          the type of comparable element
	 * @param element
	 *          non null
	 * @param min
	 *          non null
	 * @param max
	 *          non null
	 * @return if element is between min inclusive, and max, inclusive
	 */
	public static <T extends Comparable<T>> boolean between(T element, T min,
		T max) {
		return element.compareTo(max) <= 0 && element.compareTo(min) >= 0;
	}

	/**
	 * Tests that given three {@link Comparable}s element, min and max, and a
	 * {@link Comparator}, using that comparator is true that:
	 * 
	 * <pre>
	 * min &lt;= element &lt;= max
	 * </pre>
	 * 
	 * @param <T>
	 *          the type of comparable element
	 * @param element
	 *          non null
	 * @param min
	 *          non null
	 * @param max
	 *          non null
	 * @param comparator
	 *          non null
	 * @return if element is between min inclusive, and max, inclusive, using the
	 *         given {@link Comparator} as ordering criteria
	 */
	public static <T> boolean between(T element, T min, T max,
		Comparator<T> comparator) {
		return comparator.compare(element, max) <= 0
			&& comparator.compare(element, min) >= 0;
	}

	/**
	 * Tests that given three <code>long</code>s element, min and max, is true
	 * that:
	 * 
	 * <pre>
	 * min &lt;= element &lt;= max
	 * </pre>
	 * 
	 * @param element
	 *          non null
	 * @param min
	 *          non null
	 * @param max
	 *          non null
	 * @return if element is between min inclusive, and max, inclusive
	 */
	public static boolean between(long element, long min, long max) {
		return element <= max && element >= min;
	}

	/**
	 * Tests that given three <code>int</code>s element, min and max, is true
	 * that:
	 * 
	 * <pre>
	 * min &lt;= element &lt;= max
	 * </pre>
	 * 
	 * @param element
	 *          non null
	 * @param min
	 *          non null
	 * @param max
	 *          non null
	 * @return if element is between min inclusive, and max, inclusive
	 */
	public static boolean between(int element, int min, int max) {
		return element <= max && element >= min;
	}

	/**
	 * Tests if a given array contains an element
	 * 
	 * @param element
	 * @param values
	 * @return if the array contains the given element
	 */
	public static boolean in(int element, int[] values) {
		for (int value : values)
			if (element == value)
				return true;
		return false;
	}

	/**
	 * Tests if a given array contains an element
	 * 
	 * @param element
	 * @param values
	 * @return if the array contains the given element
	 */
	public static boolean in(long element, long[] values) {
		for (long value : values)
			if (element == value)
				return true;
		return false;
	}

	/**
	 * Tests if a given array contains an element
	 * 
	 * @param <T>
	 * 
	 * @param element
	 * @param values
	 * @return if the array contains the given element, using equals comparison
	 */
	public static <T> boolean in(T element, T... values) {
		for (T value : values)
			if (element.equals(value))
				return true;
		return false;
	}

	/**
	 * @param <T>
	 * @param c1
	 * @param c2
	 * @return c1 if it is lower than or equal to c2, c2 otherwise.
	 */
	public static <T extends Comparable<T>> T min(T c1, T c2) {
		return (T) ObjectUtils.min(c1, c2);
	}

	/**
	 * @param <T>
	 * @param c1
	 * @param c2
	 * @return c1 if it is greater than or equal to c2, c2 otherwise.
	 */
	public static <T extends Comparable<T>> T max(T c1, T c2) {
		return (T) ObjectUtils.max(c1, c2);
	}

}
