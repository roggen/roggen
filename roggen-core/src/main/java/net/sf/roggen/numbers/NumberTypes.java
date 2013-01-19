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

package net.sf.roggen.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.sf.roggen.defs.function.Function;
import net.sf.roggen.defs.type.IntegralType;
import net.sf.roggen.defs.type.NumberType;
import net.sf.roggen.numbers.internal.BigDecimalType;
import net.sf.roggen.numbers.internal.BigIntegerType;
import net.sf.roggen.numbers.internal.DoubleType;
import net.sf.roggen.numbers.internal.FloatType;
import net.sf.roggen.numbers.internal.IntegerType;
import net.sf.roggen.numbers.internal.LongType;
import net.sf.roggen.restrictions.Constant;
import net.sf.roggen.restrictions.SideEffectFree;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * 
 * A hub for various {@link NumberType}s standard number classes
 * <p>
 * All the methods from number types exposed here are {@link SideEffectFree}
 * </p>
 * 
 * @author flbulgarelli
 * 
 */
public class NumberTypes {

  /**
   * Creates a new {@link NumberTypes}
   */
  private NumberTypes() {}

  /**
   * Answers a {@link NumberType} that works with {@link BigDecimal}s
   * <p>
   * The returned type implements {@link NumberType#one()} and
   * {@link NumberType#zero()} so that it returns {@link BigDecimal#ONE} and
   * {@link BigDecimal#ZERO}, respectively
   * </p>
   * 
   * @return a {@link BigDecimal} numberType
   */
  @Constant
  public static NumberType<BigDecimal> bigDecimal() {
    return BigDecimalType.TYPE;
  }

  /**
   * Answers a {@link NumberType} that works with {@link BigInteger}s.
   * 
   * <p>
   * The returned type implements {@link NumberType#one()} and
   * {@link NumberType#zero()} so that it returns {@link BigInteger#ONE} and
   * {@link BigInteger#ZERO}, respectively
   * </p>
   * 
   * @return a {@link BigInteger} numberType
   */
  @Constant
  public static IntegralType<BigInteger> bigInteger() {
    return BigIntegerType.TYPE;
  }

  /**
   * Answers a {@link NumberType} that works with {@link Double}s.
   * 
   * @return a {@link Double} numberType
   */
  @Constant
  public static NumberType<Double> double_() {
    return DoubleType.TYPE;
  }

  /**
   * Answers a {@link NumberType} that works with {@link Float}s.
   * 
   * @return a {@link Float} numberType
   */
  @Constant
  public static NumberType<Float> float_() {
    return FloatType.TYPE;
  }

  /**
   * Answers a {@link NumberType} that works with {@link BigInteger}s.
   * 
   * @return a {@link BigInteger} numberType
   */
  @Constant
  public static IntegralType<Integer> integer() {
    return IntegerType.TYPE;
  }

  /**
   * Answers a {@link NumberType} that works with {@link Long}s.
   * 
   * @return a {@link Long} numberType
   */
  @Constant
  public static IntegralType<Long> long_() {
    return LongType.TYPE;
  }

  /**
   * Shortcut for <code>(Function&lt;BigInteger&gt;) bigInteger().add(n)</code>
   */
  @NonNull
  public static Function<BigInteger, BigInteger> add(@NonNull BigInteger n) {
    return BigIntegerType.TYPE.add(n);
  }

  /**
   * Shortcut for <code>(Function&lt;BigDecimal&gt;) bigDecimal().add(n)</code>
   */
  @NonNull
  public static Function<BigDecimal, BigDecimal> add(@NonNull BigDecimal n) {
    return BigDecimalType.TYPE.add(n);
  }

  /**
   * Shortcut for <code>(Function&lt;Integer&gt;) integer().add(n)</code>
   */
  @NonNull
  public static Function<Integer, Integer> add(@NonNull Integer n) {
    return IntegerType.TYPE.add(n);
  }

}
