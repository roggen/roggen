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

package net.sf.staccatocommons.iterators;

import java.util.Arrays;

import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class ArrayThriterator<A> extends IndexedThriterator<A> {

  private final A[] array;

  /**
   * Creates a new {@link ArrayThriterator}
   */
  public ArrayThriterator(@NonNull A[] array) {
    this.array = array;
  }

  protected A elementAt(int position) {
    return array[position];
  }

  protected int length() {
    return array.length;
  }

  public String toString() {
    return "ArrayThriterator(" + Arrays.toString(array) + ")";
  }

}
