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

package net.sf.staccatocommons.defs.type;

import java.util.Comparator;

import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.function.Function2;
import net.sf.staccatocommons.restrictions.Constant;
import net.sf.staccatocommons.restrictions.SideEffectFree;
import net.sf.staccatocommons.restrictions.ValueObject;

/**
 * A Strategy for dealing with {@link Number}s is a polymorphic way.
 * <p>
 * All its methods <strong>should</strong> be side effect-free, which implies
 * that arguments their <strong>should</strong> not be mutated, and that return
 * values correspond to new numbers.
 * </p>
 * <p>
 * Although all public JDK {@link Number} classes are immutable, there is no
 * restriction for numbers for being mutable, like for example Apache Commons
 * Lang MutableInts
 * </p>
 * <p>
 * In those cases, it is valid implement side-effect-full {@link NumberType}s
 * that may mutate its single or first argument, but such implementation
 * <strong>must not</strong> be passed as arguments of methods or constructor
 * that does not clearly state that accept non side-effect-free
 * {@link NumberType}.
 * </p>
 * <p>
 * In both cases, implementors {@link NumberType}s <strong>must not</strong>
 * mutate argument second arguments of methods that take two parameters
 * </p>
 * <p>
 * {@link NumberType}s <strong>must</strong> be {@link ValueObject}s
 * </p>
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 *          The type of number. This type <strong>should</strong> be a subclass
 *          of type {@link Number}. However, this type bound is not included in
 *          the {@link NumberType} interface by design reasons an to allow
 *          maximum flexibility
 * @see <a href="http://en.wikipedia.org/wiki/Type_class">Type class</a>
 */
@ValueObject
public interface NumberType<A> extends Comparator<A> {

  /**
   * Adds two numbers, and returns the result.
   * 
   * @param n0
   *          first addition operand
   * @param n1
   *          second addition operand
   * @return <code>n0 + n1</code>
   */
  A add(A n0, A n1);

  /**
   * Answers a 2-arguments function that perform addition as specified by
   * {@link #add(Object, Object)}
   * 
   * @return a function that adds its two arguments using this
   *         {@link NumberType}
   */
  @Constant
  Function2<A, A, A> add();

  /**
   * Answers function that adds the given number to its argument, as specified
   * by {@link #add(Object, Object)}. This message is a shortcut to
   * <code>add().apply(n)</code>
   * 
   * @return a function that adds its its argument with the given <code>n</code>
   *         using this {@link NumberType}
   */
  Function<A, A> add(A n);

  /**
   * Subtracts two numbers, and returns the result.
   * 
   * For any {@link Number}s x and y, a side-effect-free {@link NumberType}s nt
   * <strong>must</strong> grant that
   * <code>nt.subtract(x, y) == nt.add(x, nt.negate(y))</code>
   * 
   * @param n0
   *          first subtraction operand
   * @param n1
   *          second subtraction operand
   * @return <code>n0 - n1</code>
   */
  A subtract(A n0, A n1);

  /**
   * Multiplies two numbers, and returns the result
   * 
   * @param n0
   *          first multiplication operand
   * @param n1
   *          second multiplication operand
   * @return <code>n0 * n1</code>
   */
  A multiply(A n0, A n1);

  /**
   * Answers a 2-arguments function that perform multiplication as specified by
   * {@link #multiply(Object, Object)}
   * 
   * @return a function that multiplies its two arguments using this
   *         {@link NumberType}
   */
  @Constant
  Function2<A, A, A> multiply();

  /**
   * Divides two numbers, and returns the result.
   * 
   * @param n0
   *          first division operand
   * @param n1
   *          second division operand
   * @throws ArithmeticException
   *           if second argument is zero and the numeric type A does not
   *           support zero division
   * @return <code>n0 / n1</code>
   */
  A divide(A n0, A n1) throws ArithmeticException;

  /**
   * Negates a given number
   * 
   * @param n
   *          the number to negate
   * @return <code>-n</code>
   */
  A negate(A n);

  /**
   * Answers a function that negates its argument, as specified by
   * {@link #negate(Object)}
   * 
   * @return a function
   */
  @Constant
  Function<A, A> negate();

  /**
   * Decrements the given number.
   * 
   * For any {@link Number} x, a side-effect-free {@link NumberType}s nt it
   * <strong>must</strong> grant that
   * <code>nt.decrement(x) == nt.subtract(x, nt.one())</code>
   * 
   * As a particular case, it <strong>must</strong> grant that
   * <code>nt.compare(nt.zero(), nt.decrement(nt.one())) == 0</code>
   * 
   * 
   * @param n
   *          the number to decrement
   * @return <code>n - 1</code>
   */
  A decrement(A n);

  /**
   * Increments a given number
   * 
   * For any {@link Number} x, a side-effect-free {@link NumberType}s nt
   * <strong>must</strong> grant that
   * <code>nt.increment(x) == nt.add(x, nt.one())</code>
   * 
   * As a particular case, it <strong>must</strong> grant that
   * <code>nt.compare(nt.one(), nt.increment(nt.zero())) == 0</code>
   * 
   * 
   * @param n
   *          the number increment
   * @return <code>n + 1</code>
   */
  A increment(A n);

  /**
   * Answers that inverse of a given number, that is, <code>n^-1</code>
   * 
   * For any number x for which division is defined, a {@link NumberType} nt
   * <strong>must</strong> grant that
   * <code>nt.divide(nt.one(), x).compareTo(nt.inverse(x)) == 0</code>
   * 
   * @param n
   * @return <code>1/n</code>
   */
  A inverse(A n);

  /**
   * Answers a function that returns the ivnerse of its argument, as defined by
   * {@link #inverse(Object)}
   * 
   * @return a function
   */
  @Constant
  Function<A, A> inverse();

  /**
   * Answers the absolute value of the given number. It is the same number, if
   * non negative, or the negated number, otherwise
   * 
   * @param n
   * @return <code>isNegative(n) ? negate(n) : n</code>
   */
  A abs(A n);

  /**
   * Answers a function that returns the absolute value of its argument, as
   * specified by {@link #abs(Object)}
   * 
   * @return a funciton
   */
  @Constant
  Function<A, A> abs();

  /**
   * Answers if the given number is greater than zero
   * 
   * * For any {@link Number} x, a {@link NumberType}s nt <strong>must</strong>
   * grant that <code>nt.isZero(x) == (nt.compare(x, zero()) > 0)</code>
   * 
   * @param n
   * @return if the number is positive
   */
  @SideEffectFree
  boolean isPositive(A n);

  /**
   * Answers if the given number is less than zero
   * 
   * For any {@link Number} x, a {@link NumberType}s nt <strong>must</strong>
   * grant that <code>nt.isZero(x) == (nt.compare(x, zero()) < 0)</code>
   * 
   * @param n
   * @return if the number is negative
   */
  @SideEffectFree
  boolean isNegative(A n);

  /**
   * Answers if the given number is zero.
   * 
   * For any {@link Number} x, a {@link NumberType}s nt <strong>must</strong>
   * grant that <code>nt.isZero(x) == (nt.compare(x, nt.zero()) == 0)</code>
   * 
   * @param n
   * @return if the number is zero
   */
  @SideEffectFree
  boolean isZero(A n);

  /**
   * Answers the representation of 0 for this {@link NumberType}
   * 
   * @return the addition identity element
   */
  @Constant
  A zero();

  /**
   * Answers the representation of 1 for this {@link NumberType}
   * 
   * @return the multiplication identity element
   */
  @Constant
  A one();

  /**
   * Answers a, eventually approximate, representation of the given integer
   * value in this {@link NumberType}
   * 
   * @param ordinal
   * @return a representation of the given integer in this number type
   * @throws IllegalArgumentException
   *           if the given integer value has no exact or approximate
   *           representation for the this {@link NumberType}
   * @since 2.1
   */
  @SideEffectFree
  A fromInt(int value);

}
