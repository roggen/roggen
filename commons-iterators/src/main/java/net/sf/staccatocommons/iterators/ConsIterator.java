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
package net.sf.staccatocommons.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.staccatocommons.iterators.thriter.AdvanceThriter;
import net.sf.staccatocommons.iterators.thriter.ConsThriter;

/**
 * @author flbulgarelli
 * 
 */
public class ConsIterator<A> extends AdvanceThriter<A> {

	private A next;
	private final Iterator<? extends A> tail;
	private boolean headConsumed;

	/**
	 * Creates a new {@link ConsThriter}
	 */
	public ConsIterator(A head, Iterator<? extends A> tail) {
		this.next = head;
		this.tail = tail;
	}

	public boolean hasNext() {
		return !headConsumed || tail.hasNext();
	}

	public void advance() throws NoSuchElementException {
		if (!headConsumed) {
			headConsumed = true;
		} else {
			next = tail.next();
		}
	}

	public A current() throws NoSuchElementException {
		return next;
	}

}
