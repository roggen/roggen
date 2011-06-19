/*
 Copyright (c) 2011, The Staccato-Commons Team

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; version 3 of the License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 */
package net.sf.staccatocommons.lang;

import java.util.regex.Pattern;

import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.predicate.Predicate;
import net.sf.staccatocommons.lang.function.AbstractFunction;
import net.sf.staccatocommons.lang.predicate.AbstractPredicate;
import net.sf.staccatocommons.lang.predicate.internal.ContainsSubstringPredicate;
import net.sf.staccatocommons.lang.predicate.internal.EqualsIgnoreCase;
import net.sf.staccatocommons.lang.predicate.internal.Matches;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class Strings {
  /**
   * Returns a new {@link Predicate} that tests
   * <code>argument.equalsIgnoreCase(value)</code>
   * 
   * @param value
   * @return a new predicate
   */
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
  public static Predicate<String> contains(@NonNull String substring) {
    return new ContainsSubstringPredicate(substring);
  }

  /**
   * Returns a new {@link Predicate} that answers if its {@link String} argument
   * starts with a given prefix
   * 
   * @param string
   * @return a new Predicate that evaluates <code>args.startsWith(string)</code>
   */
  public static Predicate<String> startsWith(@NonNull final String string) {
    return new AbstractPredicate<String>() {
      @Override
      public boolean eval(String args) {
        return args.startsWith(string);
      }
    };
  }

  /**
   * Returns a function that returns the result of sending
   * {@link Object#toString()} to its argument
   * 
   * @param <A>
   * @return a function that returns <code>arg.toString()</code>
   */
  public static <A> Function<A, String> toString_() {
    return new AbstractFunction<A, String>() {
      public String apply(A arg) {
        return arg.toString();
      }
    };
  }

}
