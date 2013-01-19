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

import java.lang.reflect.Constructor;

import net.sf.roggen.lang.None;
import net.sf.roggen.lang.Option;
import net.sf.roggen.lang.SoftException;
import net.sf.roggen.testing.junit.theories.WellDefinedEqualsTheories;

import org.junit.experimental.theories.DataPoints;

/**
 * @author flbulgarelli
 * 
 */
public class OptionEqualsTest extends WellDefinedEqualsTheories {

  /** Options data points */
  @DataPoints
  public static final Object[] OPTIONS = { Option.none(), Option.some(10), Option.some(5), Option.some(5), newNone() };

  private static Object newNone() {
    try {
      Constructor<None> constructor = None.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      return constructor.newInstance();
    } catch (Exception e) {
      throw SoftException.soften(e);
    }
  }

}
