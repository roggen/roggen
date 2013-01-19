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

package net.sf.roggen.lang;

import java.util.Comparator;

import org.apache.commons.lang.ObjectUtils;

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.function.Function2;
import net.sf.roggen.defs.predicate.Predicate;
import net.sf.roggen.lang.function.AbstractFunction2;
import net.sf.roggen.lang.internal.NaturalComparator;
import net.sf.roggen.lang.predicate.AbstractPredicate;
import net.sf.roggen.lang.predicate.Predicates;
import net.sf.roggen.lang.predicate.internal.GreaterThan;
import net.sf.roggen.lang.predicate.internal.LessThan;
import net.sf.roggen.restrictions.Constant;
import net.sf.roggen.restrictions.check.NonNull;
import net.sf.roggen.restrictions.processing.IgnoreRestrictions;

/**
 * Class methods that implement comparisons for {@link Comparable}s, like
 * between(max, min) and in(collection)
 * 
 * @author flbulgarelli
 * 
 */
public class Compare {

  /**
   * Tests that given three {@link Comparable}s <code>element</code>,
   * <code>min</code> and <code>max</code>, is true that:
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
  @IgnoreRestrictions
  public static <T extends Comparable<T>> boolean between(@NonNull T element, @NonNull T min, @NonNull T max) {
    return between(element, min, max, Compare.<T> natural());
  }

  /**
   * Tests that given three {@link Comparable}s <code>element</code>,
   * <code>min</code> and <code>max</code>, and a {@link Comparator}, using that
   * comparator is true that:
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
  public static <T> boolean between(@NonNull T element, @NonNull T min, @NonNull T max,
    @NonNull Comparator<T> comparator) {
    return comparator.compare(element, max) <= 0 && comparator.compare(element, min) >= 0;
  }

  /**
   * Tests that given three <code>long</code>s <code>element</code>,
   * <code>min</code> and <code>max</code>, is true that:
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
   * Tests that given three <code>int</code>s <code>element</code>,
   * <code>min</code> and <code>max</code>, is true that:
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
   * Answers a predicate that evalutes if its argument is between
   * <code>min</code> and <code>max</code>
   * 
   * @param <T>
   * @param min
   * @param max
   * @return a new {@link Predicate}
   */
  public static <T extends Comparable<T>> Predicate<T> between(@NonNull final T min, @NonNull final T max) {
    return new AbstractPredicate<T>() {
      public boolean eval(T argument) {
        return between(argument, min, max);
      }
    };
  }

  /**
   * Tests if a given array contains an element
   * 
   * @param element
   * @param values
   * @return if the array contains the given element
   */
  public static boolean in(int element, @NonNull int[] values) {
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
  public static boolean in(long element, @NonNull long[] values) {
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
  public static <T> boolean in(@NonNull T element, @NonNull T... values) {
    for (T value : values)
      if (ObjectUtils.equals(element, value))
        return true;
    return false;
  }

  /**
   * Returns a predicate that tests if its argument is equal to any of the given
   * values
   * 
   * @param <T>
   * @param values
   * @return a new {@link Predicate}
   */
  @IgnoreRestrictions
  public static <T> Predicate<T> in_(@NonNull T... values) {
    return Predicates.in(values);
  }

  /**
   * @param <T>
   * @param c1
   * @param c2
   * @return c1 if it is lower than or equal to c2, c2 otherwise.
   */
  @NonNull
  @IgnoreRestrictions
  public static <T extends Comparable<T>> T min(@NonNull T c1, @NonNull T c2) {
    return min(c1, c2, Compare.<T> natural());
  }

  /**
   * @param <T>
   * @param c1
   * @param c2
   * @return c1 if it is greater than or equal to c2, c2 otherwise.
   */
  @NonNull
  @IgnoreRestrictions
  public static <T extends Comparable<T>> T max(@NonNull T c1, @NonNull T c2) {
    return max(c1, c2, Compare.<T> natural());
  }

  /**
   * Answers a new {@link AbstractFunction2} that returns the min of its
   * arguments using the given comparator.
   * 
   * @param <A>
   * @param comparator
   * @return a new {@link AbstractFunction2}
   */
  @NonNull
  public static <A> Function2<A, A, A> min(@NonNull final Comparator<? super A> comparator) {
    return new AbstractFunction2<A, A, A>() {
      public A apply(A arg0, A arg1) {
        return Compare.min(arg0, arg1, comparator);
      }
    };
  }

  /**
   * Answers a new {@link AbstractFunction2} that returns the max of its
   * arguments using the given comparator.
   * 
   * @param <A>
   * @param comparator
   * @return a new {@link AbstractFunction2}
   */
  @NonNull
  public static <A> Function2<A, A, A> max(@NonNull final Comparator<? super A> comparator) {
    return new AbstractFunction2<A, A, A>() {
      public A apply(A arg0, A arg1) {
        return Compare.max(arg0, arg1, comparator);
      }
    };
  }

  /**
   * Answers the min element between <code>c1</code> and <code>c2</code>, using
   * the given {@link Comparator}
   * 
   * @param <T>
   * @param c1
   * @param c2
   * @param comparator
   * @return <code>comparator.compare(c1, c2) >= 0 ? c1 : c2</code>
   */
  @NonNull
  public static <T> T max(@NonNull T c1, @NonNull T c2, @NonNull Comparator<? super T> comparator) {
    return comparator.compare(c1, c2) >= 0 ? c1 : c2;
  }

  /**
   * Answers the max element between <code>c1</code> and <code>c2</code>, using
   * the given {@link Comparator}
   * 
   * @param <T>
   * @param c1
   * @param c2
   * @param comparator
   * @return <code> comparator.compare(c1, c2) <= 0 ? c1 : c2</code>
   */
  @NonNull
  public static <T> T min(@NonNull T c1, @NonNull T c2, Comparator<? super T> comparator) {
    return comparator.compare(c1, c2) <= 0 ? c1 : c2;
  }

  /**
   * Answers a new {@link Comparator} that performs the comparison between the
   * results of applying the given <code>function</code> to its arguments.
   * 
   * @param <A>
   * @param <B>
   * @param function
   * @return a new {@link Comparator}
   */
  @NonNull
  public static <A, B extends Comparable<B>> Comparator<A> on(@NonNull final Applicable<? super A, B> function) {
    return new Comparator<A>() {
      public int compare(A arg0, A arg1) {
        return function.apply(arg0).compareTo(function.apply(arg1));
      }
    };
  }

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
  @IgnoreRestrictions
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
  @IgnoreRestrictions
  public static <T extends Comparable<T>> Predicate<T> greaterThanOrEqualTo(@NonNull T value) {
    return lessThan(value).not();
  }

  /**
   * Answers a natural comparator, that is, a comparator that compares elements
   * using its natural order, as defined by {@link Comparable}.
   * 
   * @param <A>
   * @return a natural {@link Comparator}
   */
  @Constant
  public static <A extends Comparable<A>> Comparator<A> natural() {
    return NaturalComparator.natural();
  }
}
