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

package net.sf.staccatocommons.collections.iterable.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.staccatocommons.check.Validate;
import net.sf.staccatocommons.collections.EmptySourceException;
import net.sf.staccatocommons.collections.stream.Stream;
import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.Evaluable;

/**
 * This class is not part of of the public API of collections-extra.
 * 
 * @author fbulgarelli
 */
public class IterablesInternal {

  /***/
  public static final String ITERABLE = "iterable";
  private static final Validate<EmptySourceException> CHECK_WITH_EMPTY_SOURCE = Validate
    .throwing(EmptySourceException.class);

  /***/
  public static <T> void addAllInternal(Collection<T> collection, Iterable<? extends T> iterable) {
    for (T element : iterable)
      collection.add(element);
  }

  /***/
  public static <T, C extends Collection<T>> C takeInternal(Iterable<T> iterable, int amountOfElements,
    C outputCollection) {
    Iterator<T> iter = iterable.iterator();
    for (int i = 0; i < amountOfElements && iter.hasNext(); i++)
      outputCollection.add(iter.next());
    return outputCollection;
  }

  /***/
  public static <T, C extends Collection<T>> C filterInternal(Iterable<T> iterable, Evaluable<? super T> predicate,
    C collection) {
    for (T element : iterable)
      if (predicate.eval(element))
        collection.add(element);
    return collection;
  }

  /***/
  public static <I, O, C extends Collection<O>> C collectInternal(Iterable<I> iterable,
    Applicable<? super I, ? extends O> function, C outputCollection) {
    for (I element : iterable)
      outputCollection.add(function.apply(element));
    return outputCollection;
  }

  /***/
  public static <T> boolean containsInternal(Iterable<T> iterable, T element) {
    for (T each : iterable)
      if (each.equals(element))
        return true;
    return false;
  }

  /**
   * Checks that source is not empty. Throws a {@link EmptySourceException} if
   * check fails
   */
  public static void checkNotEmpty(Iterator<?> source) {
    CHECK_WITH_EMPTY_SOURCE.that(source.hasNext(), "Source is empty");
  }

  /**
   * Checks that source is not empty. Throws a {@link EmptySourceException} if
   * check fails
   */
  public static void checkNotEmpty(Stream<?> source) {
    CHECK_WITH_EMPTY_SOURCE.that(!source.isEmpty(), "Source is empty");
  }

  /**
   * Answers a {@link NoSuchElementException}
   */
  public static NoSuchElementException noElementSatisfiesPredicate() {
    return new NoSuchElementException("No element in source satisfies the given predicate");
  }

}
