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

package net.sf.roggen.util;

import java.util.regex.Pattern;

import net.sf.roggen.defs.function.Function;
import net.sf.roggen.defs.predicate.Predicate;
import net.sf.roggen.lang.function.Functions;
import net.sf.roggen.lang.function.internal.TopLevelFunction;
import net.sf.roggen.lang.predicate.internal.ContainsSubstringPredicate;
import net.sf.roggen.lang.predicate.internal.EqualsIgnoreCase;
import net.sf.roggen.lang.predicate.internal.Matches;
import net.sf.roggen.lang.predicate.internal.TopLevelPredicate;
import net.sf.roggen.lang.value.NamedTupleToStringStyle;
import net.sf.roggen.restrictions.Constant;
import net.sf.roggen.restrictions.check.NonNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * String {@link Function}s and {@link Predicate}s 
 * @author flbulgarelli
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
  public static Predicate<String> contains(@NonNull CharSequence substring) {
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
    class StartsWith extends TopLevelPredicate<String> {
      private static final long serialVersionUID = 1L;

      @Override
      public boolean eval(String args) {
        return args.startsWith(string);
      }
    }
    return new StartsWith();
  }

  /**
   * Returns a {@link Predicate} that tells if its String argument is empty
   * 
   * @return a new {@link Predicate} that evaluates {@code argument.isEmpty()}
   */
  @Constant
  public static Predicate<String> empty() {
    class EmptyStringPredicate extends TopLevelPredicate<String> {
      private static final long serialVersionUID = -7092542419751229862L;

      public boolean eval(String argument) {
        return argument.isEmpty();
      }
    }
    return new EmptyStringPredicate();
  }

  /**
   * Returns a {@link Predicate} that tells if its String argument is not empty
   * 
   * @return a new {@link Predicate} that evaluates {@code !argument.isEmpty()}
   */
  @Constant
  public static Predicate<String> notEmpty() {
    class NotEmptyPredicate extends TopLevelPredicate<String> {
      private static final long serialVersionUID = -6617185455944513316L;

      public boolean eval(String argument) {
        return !argument.isEmpty();
      }
    }
    return new NotEmptyPredicate();
  }

  /**
   * Returns a {@link Function} that answers the char sequence length
   * 
   * @param value
   * @return a constant function
   */
  @Constant
  public static Function<CharSequence, Integer> length() {
    class StringLengthFunction extends TopLevelFunction<CharSequence, Integer> {
      private static final long serialVersionUID = -1564325023701893305L;

      public Integer apply(CharSequence arg) {
        return arg.length();
      }
    }
    return new StringLengthFunction();
  }

  /**
   * Returns a function that returns the result of sending
   * {@link Object#toString()} to its argument
   * 
   * @param <A>
   * @return a function that returns <code>arg.toString()</code>
   */
  @Constant
  public static <A> Function<A, String> toString_() {
    return Functions.toString_();
  }

  /**
   * Returns a function that returns the result of sending
   * {@link ToStringBuilder#reflectionToString(Object, org.apache.commons.lang.builder.ToStringStyle)}
   * to its argument, using the given {@link ToStringStyle}
   * 
   * @param <A>
   * @return a function that returns toString() of the argument using reflection
   */
  public static <A> Function<A, String> reflectionToString(final ToStringStyle toStringStyle) {
    class ReflectionToString extends TopLevelFunction<A, String> {
      private static final long serialVersionUID = 1L;

      public String apply(A arg) {
        return ToStringBuilder.reflectionToString(arg, toStringStyle);
      }
    }
    return new ReflectionToString();
  }

  /**
   * Returns a function that returns the result of sending
   * {@link ToStringBuilder#reflectionToString(Object, org.apache.commons.lang.builder.ToStringStyle)}
   * to its argument, using the {@link NamedTupleToStringStyle}
   * 
   * @param <A>
   * @return a function that returns toString() of the argument using reflection
   */
  public static <A> Function<A, String> reflectionToString() {
    return reflectionToString(NamedTupleToStringStyle.getInstance());
  }

  /**
   * Answers a function that returns {@code arg.toUpperCase()} 
   */
  @Constant
  public static Function<String, String> toUpperCase() {
    class ToUpperCaseFunction extends TopLevelFunction<String, String> {
      private static final long serialVersionUID = 1L;

      public String apply(String arg) {
        return arg.toUpperCase();
      }
    }
    return new ToUpperCaseFunction();
  }
   
  /**
   * Answers a function that returns {@code arg.toLowerCase()}
   */
  @Constant
  public static Function<String, String> toLowerCase() {
    class ToLowerCaseFunction extends TopLevelFunction<String, String> {
      private static final long serialVersionUID = 1L;

      public String apply(String arg) {
        return arg.toLowerCase();
      }
    }
    return new ToLowerCaseFunction();
  }

}
