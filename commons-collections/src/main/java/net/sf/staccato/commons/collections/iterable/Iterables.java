/*
 Copyright (c) 2010, The Staccato-Commons Team   
 
 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; version 3 of the License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 */
package net.sf.staccato.commons.collections.iterable;

import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.AMOUNT_OF_ELEMENTS;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.addAllInternal;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.anyInternal;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.anyOrNoneInternal;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.collectInternal;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.filterInternal;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.foldInternal;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.isEmptyInternal;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.reduceInternal;
import static net.sf.staccato.commons.collections.iterable.internal.IterablesInternal.takeInternal;
import static net.sf.staccato.commons.lang.tuple.Tuple._;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.staccato.commons.check.annotation.ForceChecks;
import net.sf.staccato.commons.check.annotation.NonNull;
import net.sf.staccato.commons.check.annotation.NotNegative;
import net.sf.staccato.commons.check.annotation.Size;
import net.sf.staccato.commons.lang.Applicable;
import net.sf.staccato.commons.lang.Applicable2;
import net.sf.staccato.commons.lang.Evaluable;
import net.sf.staccato.commons.lang.Option;
import net.sf.staccato.commons.lang.function.Function2;
import net.sf.staccato.commons.lang.predicate.Predicate;
import net.sf.staccato.commons.lang.tuple.Pair;

import org.apache.commons.lang.ObjectUtils;

/**
 * Class methods that complement the {@link java.util.Collections}
 * functionality, providing common algorithms for collections and iterables.
 * 
 * Otherwise stated, null collections, functors and iterables are prohibited as
 * parameter, but empty collections and iterables are allowed.
 * 
 * {@link Iterables} class contains only side-effect-free methods that do not
 * modify any of its arguments, and thus can be used with immutable collections.
 * 
 * For algorithms that modify the state of the input collections, see
 * {@link ModifiableIterables}. However, Stacatto-commons-collection-extra API
 * recommends to avoid those methods when possible, as will fail with
 * unmodifiable collections and are not functional.
 * 
 * @author flbulgarelli
 */
public class Iterables {

	/*
	 * Filtering
	 */

	/**
	 * Selects all elements that evaluate to true.
	 * 
	 * @param iterable
	 * @param predicate
	 * @param <A>
	 * @return a list containing only elements from the original iterable that
	 *         evaluate to true
	 */
	@NonNull
	public static <A> List<A> filter(@NonNull Iterable<A> iterable,
		@NonNull Evaluable<? super A> predicate) {
		return filterInternal(iterable, predicate, new LinkedList<A>());
	}

	/**
	 * Selects at most the fist N elements from the iterable, according to its
	 * iteration order.
	 * 
	 * @param <A>
	 * @param iterable
	 * @param amountOfElements
	 * @return a new list containing at most the first N elements from original
	 *         iterable
	 */
	@NonNull
	public static <A> List<A> take(@NonNull Iterable<A> iterable,
		@NotNegative(AMOUNT_OF_ELEMENTS) int amountOfElements) {
		return takeInternal(iterable, amountOfElements, new ArrayList<A>(amountOfElements));
	}

	// TODO take while
	// TODO drop while

	/*
	 * Reduction
	 */

	@NonNull
	public static <A> A reduce(@NonNull Iterable<A> iterable,
		@NonNull Applicable2<? super A, ? super A, ? extends A> function) {
		return reduceInternal(iterable, function);
	}

	@NonNull
	public static <A, B> B fold(@NonNull Iterable<A> iterable, B initial,
		@NonNull Applicable2<? super B, ? super A, ? extends B> function) {
		return foldInternal(iterable, initial, function);
	}

	/*
	 * Search
	 */

	/**
	 * Alternative version of {@link #findOrNone(Iterable, Predicate)}, where the
	 * element is returned if found, or a {@link NoSuchElementException} is thrown
	 * otherwise
	 * 
	 * @param <A>
	 * @param iterable
	 * @param predicate
	 * @return the element if found
	 * @throws NoSuchElementException
	 *           if no element matches the predicate
	 */
	public static <A> A find(@NonNull Iterable<A> iterable, @NonNull Evaluable<? super A> predicate) {
		for (A o : iterable)
			if (predicate.eval(o))
				return o;
		throw new NoSuchElementException();
	}

	/**
	 * Looks for a object that matches the given predicate. If such element does
	 * not exist, or collection is empty, returns {@link Option#none()}. Otherwise
	 * returns {@link Option#some(Object)}, for the first object found
	 * 
	 * @param <A>
	 * @param iterable
	 *          non null
	 * @param predicate
	 *          non null
	 * @return None if no element matches the predicate or collection is empty, or
	 *         some(element) if at least one exists
	 */
	@NonNull
	public static <A> Option<A> findOrNone(@NonNull Iterable<A> iterable,
		@NonNull Evaluable<? super A> predicate) {
		for (A o : iterable)
			if (predicate.eval(o))
				return Option.some(o);
		return Option.none();
	}

	/**
	 * Returns the single element of the given collection. It must be of size 1,
	 * otherwise, throws an IllegalArgumentException.
	 * 
	 * @param <A>
	 *          the collection type
	 * @param collection
	 *          a single element (size==1) collection
	 * @return The unique element of the collection
	 */
	@ForceChecks
	public static <A> A single(@Size(1) Collection<A> collection) {
		return anyInternal(collection);
	}

	/**
	 * Returns any element from this iterable, or throws a
	 * {@link NoSuchElementException} it iterable is empty. Notice that
	 * <strong>any does not mean random</strong>, it may return always the same
	 * element - for example the first for a list -, but the exact element
	 * returned from the iterable unspecified.
	 * 
	 * @param <A>
	 * @param iterable
	 * @throws NoSuchElementException
	 *           if the iterable is empty
	 * @return an element contained in the given iterable. This is nullable, if
	 *         the iterables's iterator may return null.
	 */
	public static <A> A any(@NonNull Iterable<A> iterable) {
		return anyInternal(iterable);
	}

	/**
	 * Gets any element of the given collection. Returns Option.some(element) if
	 * not empty, or Option.none(), if empty.
	 * 
	 * @param <A>
	 * @param iterable
	 * @return some(element) if not empty, or none, otherwise.
	 */
	@NonNull
	public static <A> Option<A> anyOrNone(@NonNull Iterable<A> iterable) {
		return anyOrNoneInternal(iterable);
	}

	/*
	 * Validating
	 */

	/**
	 * Tests if all the elements of the given {@link Iterable} satisfy the given
	 * predicate
	 * 
	 * @param <A>
	 * @param iterable
	 * @param predicate
	 *          the predicate that will be used to evaluate each element
	 * @return <code>true</code> if all the elements satisfy the given predicate,
	 *         <code>false</code> otherwise. As a particular case of this rule,
	 *         this method will return <code>true</code> for empty iterables,
	 *         regardless of the predicate.
	 */
	public static <A> boolean all(@NonNull Iterable<A> iterable,
		@NonNull Evaluable<? super A> predicate) {
		for (A o : iterable)
			if (!predicate.eval(o))
				return false;
		return true;
	}

	/**
	 * Answers if all elements in the collection are equal.
	 * 
	 * @param <A>
	 * @param iterable
	 *          non null. May be empty.
	 * @return <code>true</code>if all element are equal. <code>false</code>
	 *         otherwise. As a particular case of this rule, if this collection is
	 *         empty or has only one element, it will return <code>true</code>.
	 */
	public static <A> boolean allEqual(@NonNull Iterable<A> iterable) {
		Iterator<A> iter = iterable.iterator();
		if (!iter.hasNext())
			return true;
		A any = iter.next();
		for (; iter.hasNext();)
			if (!ObjectUtils.equals(any, iter.next()))
				return false;
		return true;
	}

	/**
	 * Answers if all elements in the collection are the same object.
	 * 
	 * @param <A>
	 * @param iterable
	 *          non null. May be empty.
	 * @return <code>true</code>if all element are the same object.
	 *         <code>false</code> otherwise. As a particular case of this rule, if
	 *         this collection is empty or has only one element, it will return
	 *         <code>true</code>.
	 */
	public static <A> boolean allSame(@NonNull Iterable<A> iterable) {
		Iterator<A> iter = iterable.iterator();
		if (!iter.hasNext())
			return true;
		A any = iter.next();
		for (; iter.hasNext();)
			if (any != iter.next())
				return false;
		return true;
	}

	/**
	 * Tests if any of the elements of the given {@link Iterable} satisfy the
	 * given predicate
	 * 
	 * @param <A>
	 * @param iterable
	 * @param predicate
	 *          the predicate that will be used to evaluate each element
	 * @return <code>true</code> if at least one element satisfies the given
	 *         predicate, <code>false</code> otherwise. As a particular case of
	 *         this rule, this method will return <code>false</code> for empty
	 *         iterables, regardless of the predicate.
	 */
	public static <A> boolean any(@NonNull Iterable<A> iterable, Evaluable<? super A> predicate) {
		for (A o : iterable)
			if (predicate.eval(o))
				return true;
		return false;
	}

	/**
	 * Answers if the given {@link Iterable} is empty or not, that is, if its
	 * iterator returns at least one element.
	 * 
	 * @param <A>
	 *          the iterable element type
	 * @param iterable
	 * @return if the iterable is empty or not
	 */
	public static <A> boolean isEmpty(@NonNull Iterable<A> iterable) {
		return isEmptyInternal(iterable);
	}

	/**
	 * Answers if the given {@link Iterable} is empty or null
	 * 
	 * @param <A>
	 *          the iterable element type
	 * @param iterable
	 * @return if the iterable is null or empty
	 * @see #isEmpty(Iterable)
	 */
	public static <A> boolean isNullOrEmpty(@NonNull Iterable<A> iterable) {
		return iterable == null || isEmptyInternal(iterable);
	}

	/**
	 * Answers if the given {@link Collection} is empty or null
	 * 
	 * @param <A>
	 *          the collection element type
	 * @param collection
	 * @return if the collection is null or empty
	 */
	public static <A> boolean isNullOrEmpty(@NonNull Collection<A> collection) {
		return collection == null || collection.isEmpty();
	}

	/*
	 * Mapping
	 */

	/**
	 * Maps the the given collection into a new list, using the given function
	 * 
	 * @param <A>
	 *          the element type of the given collection
	 * @param <B>
	 *          the element type of the resulting list
	 * @param collection
	 *          the source of the mapping.
	 * @param function
	 *          the function applied to each element of the source collection.
	 * @return a new, non null {@link List}, which contains an element for the
	 *         result of the application of each element of the given collection.
	 *         As a particular case, this method will return an empty list if the
	 *         given collection is empty
	 */
	@NonNull
	public static <A, B> List<B> map(@NonNull Collection<A> collection,
		@NonNull Applicable<? super A, ? extends B> function) {
		return collectInternal( //
			collection,
			function,
			new ArrayList<B>(collection.size()));
	}

	/**
	 * Maps the the given iterable into a new list, using the given function
	 * 
	 * @param <A>
	 *          the element type of the given iterable
	 * @param <B>
	 *          the element type of the resulting list
	 * @param iterable
	 *          the source of the mapping.
	 * @param function
	 *          the function applied to each element of the source iterable.
	 * @return a new, non null {@link List}, which contains an element for the
	 *         result of the application of each element of the given collection.
	 *         As a particular case, this method will return an empty list if the
	 *         given iterable is empty
	 */
	@NonNull
	public static <A, B> List<B> map(@NonNull Iterable<A> iterable,
		@NonNull Applicable<? super A, ? extends B> function) {
		return collectInternal(iterable, function, new LinkedList<B>());
	}

	public static <A, B> List<B> flatMap(@NonNull Iterable<A> iterable,
		@NonNull Applicable<? super A, ? extends Iterable<B>> function) {
		LinkedList<B> list = new LinkedList<B>();

		for (A element : iterable)
			for (B applyedElement : function.apply(element))
				list.add(applyedElement);

		return list;
	}

	/*
	 * Sorting and converting
	 */

	/**
	 * Sorts a new list containing all the collection elements, using the given
	 * comparator. Null collections are treated as empty collections.
	 * 
	 * @param <A>
	 * @param iterable
	 *          the the collection.
	 * @param comparator
	 *          . Not null.
	 * @return a new list containing all the original colleciton elements, sorted
	 *         using the given criteria, or an empty mutable list, if the
	 *         collection was null or empty.
	 */
	public static <A> List<A> toSortedList(@NonNull Iterable<A> iterable,
		@NonNull Comparator<? super A> comparator) {
		List<A> list = new LinkedList<A>();
		addAllInternal(list, iterable);
		java.util.Collections.sort(list, comparator);
		return list;
	}

	public static <A> SortedSet<A> toSortedSet(@NonNull Iterable<A> iterable,
		@NonNull Comparator<? super A> comparator) {
		TreeSet<A> sortedSet = new TreeSet<A>(comparator);
		addAllInternal(sortedSet, iterable);
		return sortedSet;
	}

	public static <A> SortedSet<A> toSortedSet(@NonNull Iterable<A> iterable) {
		TreeSet<A> sortedSet = new TreeSet<A>();
		addAllInternal(sortedSet, iterable);
		return sortedSet;
	}

	/**
	 * Converts the given collection into a {@link Set}. If the collection is
	 * already a set, it just returns it
	 * 
	 * @param <A>
	 * @param collection
	 * @return a new {@link Set} that contains all the elements from the given
	 *         collection, or the given collection, if it is already a set
	 */
	public static <A> Set<A> toSet(Collection<A> collection) {
		return collection instanceof Set ? (Set<A>) collection : new HashSet<A>(collection);
	}

	public static <A> Set<A> toSet(Iterable<A> iterable) {
		return ModifiableIterables.addAll(new HashSet<A>(), iterable);
	}

	/**
	 * Converts the given collection into a {@link List}. If the collection is
	 * already a list, it just returns it
	 * 
	 * @param <A>
	 * @param collection
	 * @return a new {@link List} that contains all the elements from the given
	 *         collection, or the given collection, if it is already a list
	 */
	public static <A> List<A> toList(@NonNull Collection<A> collection) {
		return collection instanceof List ? (List<A>) collection : new ArrayList<A>(collection);
	}

	/**
	 * Converts the given iterable into a {@link List}. If the iterable is already
	 * a list, it just returns it
	 * 
	 * @param <A>
	 * @param collection
	 * @return a new {@link List} that contains all the elements from the given
	 *         iterable, or the given collection, if it is already a list
	 */
	public static <A> List<A> toList(Iterable<A> iterable) {
		if (iterable instanceof List)
			return (List<A>) iterable;
		LinkedList<A> list = new LinkedList<A>();
		ModifiableIterables.addAll(list, iterable);
		return list;
	}

	/*
	 * Partioning
	 */

	public static <A> Pair<List<A>, List<A>> partition(@NonNull Iterable<A> iterable,
		Evaluable<? super A> predicate) {

		List<A> left = new LinkedList<A>();
		List<A> right = new LinkedList<A>();
		for (A element : iterable)
			if (predicate.eval(element))
				left.add(element);
			else
				right.add(element);
		return _(left, right);
	}

	/*
	 * MISC
	 */

	public static <A> A get(@NonNull Iterable<A> iterable, int at) throws IndexOutOfBoundsException {
		A element = null;
		Iterator<A> iter = iterable.iterator();
		for (int i = 0; i <= at; i++)
			try {
				element = iter.next();
			} catch (NoSuchElementException e) {
				throw new IndexOutOfBoundsException("At " + at);
			}
		return element;
	}

	/**
	 * The 0-based index of a given element in the iterable when iterating over it
	 * 
	 * @param <A>
	 * @param iterable
	 * @param element
	 * @return the index of the element in the given iterable, or -1 if it is not
	 *         contained on it
	 */
	public static <A> int indexOf(@NonNull Iterable<A> iterable, A element) {
		int i = 0;
		for (Object o : iterable) {
			if (ObjectUtils.equals(element, o))
				return i;
			i++;
		}
		return -1;
	}

	/**
	 * If an element is before another when iterating through the given iterable.
	 * 
	 * @param <A>
	 * @param iterable
	 * @param previous
	 *          the element to test if exist in the iterable and is be before next
	 * @param next
	 *          the element to test if exists in the iterable and is after
	 *          previous
	 * @return true if both elements are contained by the given iterable, and
	 *         first element is before second one
	 */
	public static <A> boolean isBefore(@NonNull Iterable<A> iterable, A previous, A next) {
		if (ObjectUtils.equals(previous, next))
			return false;
		boolean previousFound = false;
		for (A o : iterable) {
			if (!previousFound && ObjectUtils.equals(o, previous)) {
				previousFound = true;
				continue;
			}
			if (ObjectUtils.equals(o, next))
				return previousFound;
		}
		return false;
	}

	@NonNull
	public static <A, B, C> List<C> zip(@NonNull Iterable<A> iterable1,
		@NonNull Iterable<B> iterable2, Applicable2<A, B, C> function) {
		Iterator<A> iter1 = iterable1.iterator();
		Iterator<B> iter2 = iterable2.iterator();
		List<C> result = new LinkedList<C>();
		while (iter1.hasNext() && iter2.hasNext())
			result.add(function.apply(iter1.next(), iter2.next()));
		return result;
	}

	@NonNull
	public static <A, B> List<Pair<A, B>> zip(@NonNull Iterable<A> iterable1,
		@NonNull Iterable<B> iterable2) {
		return zip(iterable1, iterable2, new Function2<A, B, Pair<A, B>>() {
			@Override
			public Pair<A, B> apply(A arg1, B arg2) {
				return _(arg1, arg2);
			}
		});
	}

	// TODO product
}
