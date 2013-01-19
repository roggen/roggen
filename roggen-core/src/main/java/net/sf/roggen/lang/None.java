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

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.roggen.defs.Executable;
import net.sf.roggen.defs.Thunk;
import net.sf.roggen.iterators.thriter.Thriterators;
import net.sf.roggen.restrictions.EquivObject;
import net.sf.roggen.restrictions.ValueObject;
import net.sf.roggen.restrictions.check.NonNull;

/**
 * An undefined {@link Option}, that it, and option that does not have a value
 * 
 * @author flbulgarelli
 * 
 * @param <T>
 *          the type of value - although {@link None} options have no associated
 *          value at all.
 * @see Option
 */
@ValueObject
@EquivObject
public final class None<T> extends Option<T> {

  private static final long serialVersionUID = 6950027007611799776L;

  @SuppressWarnings("unchecked")
  private static final None<?> INSTANCE = new None();

  private None() {}

  @Override
  public T value() {
    throw new NoSuchElementException();
  }

  @Override
  public boolean isDefined() {
    return false;
  }

  @Override
  public T valueOrElse(T ifUndefined) {
    return ifUndefined;
  }

  @Override
  public T valueOrElse(@NonNull Thunk<? extends T> ifUndefined) {
    return ifUndefined.value();
  }

  @Override
  public void ifDefined(Executable<? super T> block) {

  }

  // @Override
  // public <B> Option<B> bind(Applicable<? super T, Option<B>> function) {
  // return none();
  // }

  @Override
  public T valueOrNull() {
    return null;
  }

  public Iterator<T> iterator() {
    return Thriterators.empty();
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean contains(Object element) {
    return false;
  }

  public Option<T> skip(T element) {
    return this;
  }

  public int hashCode() {
    return 37;
  }

  public boolean equals(Object obj) {
    return obj == this || obj instanceof None;
  }

  @Override
  public String toString() {
    return "None";
  }

  @NonNull
  public static <T> None<T> none() {
    return (None<T>) INSTANCE;
  }

}