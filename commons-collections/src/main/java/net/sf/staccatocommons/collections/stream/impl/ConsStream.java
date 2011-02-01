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
package net.sf.staccatocommons.collections.stream.impl;

import static net.sf.staccatocommons.lang.tuple.Tuple.*;
import net.sf.staccatocommons.check.annotation.NonNull;
import net.sf.staccatocommons.collections.stream.AbstractStream;
import net.sf.staccatocommons.collections.stream.Stream;
import net.sf.staccatocommons.collections.stream.Streams;
import net.sf.staccatocommons.defs.type.NumberType;
import net.sf.staccatocommons.iterators.ConsIterator;
import net.sf.staccatocommons.iterators.thriter.Thriterator;
import net.sf.staccatocommons.lang.number.ImplicitNumberType;
import net.sf.staccatocommons.lang.tuple.Pair;

/**
 * 
 * A {@link ConsStream} is a {@link Stream} that retrieves first a single
 * element - the head - and the elements from another {@link Iterable} - the
 * tail.
 * 
 * @author flbulgarelli
 * 
 * @param <A>
 */
public final class ConsStream<A> extends AbstractStream<A> {
	private final Iterable<? extends A> tail;
	private final A head;

	/**
	 * Creates a new {@link ConsStream}
	 */
	public ConsStream(A head, @NonNull Iterable<? extends A> tail) {
		this.tail = tail;
		this.head = head;
	}

	public Thriterator<A> iterator() {
		return new ConsIterator<A>(head, tail.iterator());
	}

	public boolean isEmpty() {
		return false;
	}

	public Pair<A, Stream<A>> decons() {
		return _(head, Streams.from(tail));
	}

	public A head() {
		return head;
	}

	public NumberType<A> numberType() {
		return ((ImplicitNumberType<A>) tail).numberType();
	}

}