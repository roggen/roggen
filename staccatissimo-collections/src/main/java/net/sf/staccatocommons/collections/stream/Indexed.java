/**
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

package net.sf.staccatocommons.collections.stream;

import java.util.NoSuchElementException;

import net.sf.staccatocommons.collections.restrictions.Projection;
import net.sf.staccatocommons.defs.Evaluable;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * {@link Stream} interface for accessing elements in an ordered manner.
 * 
 * Although {@link Stream} allow such kind of access, they do not warrant it is
 * neither efficient - random access may be costly, for example - nor repeatable
 * - element returned by {@link #first()} may not be the same between
 * invocations, and it exclusively depends on the actual implementation.
 * 
 * All methods will throw an {@link NoSuchElementException} when trying to
 * access an element out of the size of the {@link Stream}
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 */
public interface Indexed<A> {

  
  /**
   * Answers the n-th element.
   * 
   * @param n
   * @return the n-th element, zero based
   * @throws IndexOutOfBoundsException
   *           if there is no n-th element, because stream has less than {@code n} elements
   */
  A get(int n);

  /**
   * Answers the zero-based index of the given element
   * 
   * @param element
   * @return the index of the element first element equal to {@code element}, or
   *         -1, if it is not contained by this stream
   */
  int indexOf(A element);

  /**
   * Answers the zero-based index of first element that matches the given
   * predicate
   * 
   * @param predicate
   * @return the index of the first element that evaluates the {@code predicate}
   *         to true, or -1, if no element satisfies it
   * @since 2.1
   */
  int findIndex(Evaluable<? super A> predicate);

  /**
   * Answers a stream containing all the zero-based indices of the elements that
   * matches the given predicate
   * 
   * @param predicate
   * @return a {@link Stream} with the indices of the elements that satisfy the
   *         given predicate
   * @since 2.1
   */
  @Projection
  Stream<Integer> indices(Evaluable<? super A> predicate);

  /**
   * Answers the index of the given <strong>present</strong> element. This
   * method behaves exactly like {@link #indexOf(Object)}, with the only
   * difference that it will throw a {@link NoSuchElementException} if the given
   * element is not present on the stream
   * 
   * @param element
   * @return the index of the given element
   * @throws NoSuchElementException
   *           if the element is no contained by this {@link Stream}
   */
  int positionOf(A element) throws NoSuchElementException;

  /**
   * Answers the zero-based index of first, <strong>present</strong> element
   * that matches the given predicate. This method behaves exactly like
   * {@link #findIndex(Evaluable)}, with the only difference that it will throw
   * a {@link NoSuchElementException} if the given element is not present on the
   * stream
   * 
   * @param predicate
   * @return the index of the first element that evaluates the {@code predicate}
   * @throws NoSuchElementException
   *           if no elements satisfies the given {@code predicate}
   * @since 2.1
   */
  int findPosition(Evaluable<? super A> predicate) throws NoSuchElementException;

  /**
   * Preserves elements that whose index satisfy the given
   * <code>predicate</code>
   * 
   * @param predicate
   * @return a new {@link Stream} projection that will retrieve only elements
   *         whose index evaluate to true
   */
  @Projection
  Stream<A> filterIndex(@NonNull Evaluable<Integer> predicate);

  /**
   * Answers a streams that retrieves all the elements of this one, except of
   * that at the given index
   * 
   * @param predicate
   * @return a new {@link Stream} that skips the element at the given index
   */
  @Projection
  Stream<A> skipIndex(int index);

}