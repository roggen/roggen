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
package net.sf.staccatocommons.lang.predicate;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import net.sf.staccatocommons.check.annotation.ForceChecks;
import net.sf.staccatocommons.check.annotation.NonNull;
import net.sf.staccatocommons.defs.Evaluable;
import net.sf.staccatocommons.defs.Evaluable2;
import net.sf.staccatocommons.lang.predicate.internal.All;
import net.sf.staccatocommons.lang.predicate.internal.Any;
import net.sf.staccatocommons.lang.predicate.internal.CompareTest;
import net.sf.staccatocommons.lang.predicate.internal.ContainsSubstringPredicate;
import net.sf.staccatocommons.lang.predicate.internal.EqualityTest;
import net.sf.staccatocommons.lang.predicate.internal.Equals;
import net.sf.staccatocommons.lang.predicate.internal.EqualsIgnoreCase;
import net.sf.staccatocommons.lang.predicate.internal.EvaluablePredicate;
import net.sf.staccatocommons.lang.predicate.internal.False;
import net.sf.staccatocommons.lang.predicate.internal.GreaterThan;
import net.sf.staccatocommons.lang.predicate.internal.InPredicate;
import net.sf.staccatocommons.lang.predicate.internal.LessThan;
import net.sf.staccatocommons.lang.predicate.internal.Matches;
import net.sf.staccatocommons.lang.predicate.internal.NullPredicates;
import net.sf.staccatocommons.lang.predicate.internal.Same;
import net.sf.staccatocommons.lang.predicate.internal.True;

/**
 * Factory methods for common predicates
 * 
 * @author flbulgarelli
 */
public class Predicates {

	private Predicates() {}

	/**
	 * @param <T>
	 * @return A {@link Predicate} that always returns <code>true</code>
	 */
	@NonNull
	public static <T> Predicate<T> true_() {
		return True.getInstance();
	}

	/**
	 * @param <T>
	 * @return A {@link Predicate} that always returns <code>false</code>
	 */
	@NonNull
	public static <T> Predicate<T> false_() {
		return False.getInstance();
	}

	/*
	 * Object predicates
	 */

	/**
	 * Returns a preficate that tests if its argument is not null
	 * 
	 * @param <T>
	 * @return A singleton {@link Predicate}
	 */
	@NonNull
	public static <T> Predicate<T> notNull() {
		return NullPredicates.notNull();
	}

	/**
	 * Returns a predicate that tests if its argument is null
	 * 
	 * @param <T>
	 * @return A singleton {@link Predicate}
	 */
	@NonNull
	public static <T> Predicate<T> null_() {
		return NullPredicates.null_();
	}

	/**
	 * Returns a predicate that tests if its argument is equal to the given value:
	 * <code>argument.equals(value)</code>
	 * 
	 * @param <T>
	 * @param value
	 * @return a new {@link Predicate}
	 */
	@NonNull
	public static <T> Predicate<T> equal(T value) {
		return new Equals<T>(value);
	}

	/**
	 * Returns a predicate that tests if its argument is the same that the given
	 * value
	 * 
	 * @param <T>
	 * @param value
	 * @return a new {@link Predicate}
	 */
	@NonNull
	public static <T> Predicate<T> same(T value) {
		return new Same<T>(value);
	}

	/**
	 * Returns a predicate that tests if its argument is instance of the given
	 * class
	 * 
	 * @param <T>
	 * @param clazz
	 * @return a new {@link Predicate}
	 */
	@ForceChecks
	@NonNull
	public static <T> Predicate<T> isInstanceOf(@NonNull final Class<? extends T> clazz) {
		return new Predicate<T>() {
			public boolean eval(T argument) {
				return clazz.isAssignableFrom(argument.getClass());
			}
		};
	}

	/**
	 * Returns a predicate that tests if its argument is equal to any of the given
	 * values
	 * 
	 * @param <T>
	 * @param values
	 * @return a new {@link Predicate}
	 */
	@NonNull
	public static <T> Predicate<T> in(@NonNull T... values) {
		return new InPredicate<T>(values);
	}

	/**
	 * Returns a predicate that tests if its argument is equal to any of the
	 * values in the given collection
	 * 
	 * @param <T>
	 * @param values
	 * @return a new {@link Predicate}
	 */
	@NonNull
	public static <T> Predicate<T> in(@NonNull Collection<T> values) {
		return new InPredicate<T>(values);
	}

	/*
	 * String predicates
	 */

	/**
	 * Returns a new {@link Predicate} that tests
	 * <code>argument.equalsIgnoreCase(value)</code>
	 * 
	 * @param value
	 * @return a new predicate
	 */
	@NonNull
	public static Predicate<String> equalsIgnoreCase(@NonNull String value) {
		return new EqualsIgnoreCase(value);
	}

	/**
	 * Returns a new {@link Predicate} that tests
	 * <code>argument.matches(value)</code>
	 * 
	 * @param regexp
	 * @return a new predicate
	 */
	@NonNull
	public static Predicate<String> matches(@NonNull String regexp) {
		return new Matches(regexp);
	}

	/**
	 * Returns a new {@link Predicate} that tests
	 * <code>pattern.matcher(value).matches()</code>
	 * 
	 * @param pattern
	 * @return a new predicate
	 */
	@NonNull
	public static Predicate<String> matches(@NonNull Pattern pattern) {
		return new Matches(pattern);
	}

	/**
	 * Returns a new {@link Predicate} that tests
	 * <code>argument.contains(substring)</code>
	 * 
	 * @param substring
	 *          the substring to test if it is contained
	 * @return a new predicate
	 */
	@NonNull
	public static Predicate<String> contains(@NonNull String substring) {
		return new ContainsSubstringPredicate(substring);
	}

	/*
	 * Logical predicates
	 */

	/**
	 * Returns a predicate that evaluates to true if and only if all the given
	 * predicates evaluate true
	 * 
	 * @param <T>
	 * @param predicates
	 * @return the all predicate
	 */
	@NonNull
	public static <T> Predicate<T> all(@NonNull Evaluable<T>... predicates) {
		return all(Arrays.asList(predicates));
	}

	/**
	 * Returns a predicate that evaluates to true if and only if all the given
	 * predicates evaluate true
	 * 
	 * @param <T>
	 * @param predicates
	 * @return the all predicate
	 */
	@NonNull
	public static <T> Predicate<T> all(@NonNull Iterable<Evaluable<T>> predicates) {
		return new All<T>(predicates);
	}

	/**
	 * Returns a predicate that evaluates to false if and only if all the given
	 * predicates evaluate false
	 * 
	 * @param <T>
	 * @param predicates
	 * @return the any predicate
	 */
	@NonNull
	public static <T> Predicate<T> any(@NonNull Evaluable<T>... predicates) {
		return any(Arrays.asList(predicates));
	}

	/**
	 * Returns a predicate that evaluates to false if and only if all the given
	 * predicates evaluate false
	 * 
	 * @param <T>
	 * @param predicates
	 * @return the any predicate
	 */
	@NonNull
	public static <T> Predicate<T> any(@NonNull Iterable<Evaluable<T>> predicates) {
		return new Any<T>(predicates);
	}

	/*
	 * Comparable predicates
	 */

	/**
	 * Returns a predicate that evaluates if its argument is less than the given
	 * value.
	 * 
	 * More formally, this method returns a new predicate that evaluates
	 * comparable argument with the statement
	 * <code>argument.compareTo(value) < 0</code>
	 * 
	 * @param <T>
	 * @param value
	 * @return a new predicate
	 */
	@NonNull
	public static <T extends Comparable<T>> Predicate<T> lessThan(@NonNull T value) {
		return new LessThan<T>(value);
	}

	/**
	 * Returns a predicate that evaluates if its argument is lower than or equal
	 * to the given value.
	 * 
	 * More formally, this method returns a new predicate that evaluates
	 * comparable argument with the statement
	 * <code>argument.compareTo(value) <= 0</code>
	 * 
	 * @param <T>
	 * @param value
	 * @return a new predicate
	 */
	@NonNull
	public static <T extends Comparable<T>> Predicate<T> lessThanOrEqualTo(@NonNull T value) {
		return greaterThan(value).not();
	}

	/**
	 * Returns a predicate that evaluates if its argument is greater than the
	 * given value.
	 * 
	 * More formally, this method returns a new predicate that evaluates
	 * comparable argument with the statement
	 * <code>argument.compareTo(value) > 0</code>
	 * 
	 * @param <T>
	 * @param value
	 * @return a new predicate
	 */
	@NonNull
	public static <T extends Comparable<T>> Predicate<T> greaterThan(@NonNull T value) {
		return new GreaterThan<T>(value);
	}

	/**
	 * Returns a predicate that evaluates if its argument is greater than or equal
	 * to the given value.
	 * 
	 * More formally, this method returns a new predicate that evaluates
	 * comparable argument with the statement
	 * <code>argument.compareTo(value) >= 0</code>
	 * 
	 * @param <T>
	 * @param value
	 * @return a new predicate
	 */
	@NonNull
	public static <T extends Comparable<T>> Predicate<T> greaterThanOrEqualTo(@NonNull T value) {
		return lessThan(value).not();
	}

	/*
	 * Predicate - Evaluable bridge
	 */

	/**
	 * Converts the given {@link Evaluable} into a {@link Predicate}. If it is
	 * already a Predicate, returns it.
	 * 
	 * @param evaluable
	 * @param <T>
	 * @return a {@link Predicate} view of the given evaluable, or the evaluable,
	 *         it is a {@link Predicate} already
	 */
	@NonNull
	public static <T> Predicate<T> from(@NonNull Evaluable<? super T> evaluable) {
		if (evaluable instanceof Predicate)
			return (Predicate) evaluable;
		return new EvaluablePredicate<T>(evaluable);
	}

	/**
	 * Answers an {@link Evaluable2} that performs an equality test between its
	 * nullable arguments, that it returns true if both are null or both are non
	 * null and equal
	 * 
	 * @param <A>
	 * @return a constant {@link Evaluable2} that performs an equality test
	 */
	@NonNull
	public static <A> Evaluable2<A, A> equalOrNull() {
		return EqualityTest.equalityTest();
	}

	/**
	 * Answers an {@link Evaluable2} that performs an compare test between its
	 * nullable {@link Comparable} arguments, that it returns true if both are
	 * null or <code>arg0.compareTo(arg1) == 0</code>
	 * 
	 * @param <A>
	 * @return a constant {@link Evaluable2} that performs a compare test
	 */
	@NonNull
	public static <A extends Comparable<A>> Evaluable2<A, A> compareOrNull() {
		return CompareTest.compareTest();
	}

	// TODO have a similar or strategy for nulls with eval2

}
