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

import net.sf.staccatocommons.check.annotation.NonNull;
import net.sf.staccatocommons.defs.restriction.Constant;

/**
 * @author flbulgarelli
 * 
 */
public class SingleIterator<A> extends AbstractUnmodifiableIterator<A> {

	private final A element;
	private boolean consumed;

	public SingleIterator(A element) {
		this.element = element;
	}

	public boolean hasNext() {
		return consumed;
	}

	public A next() {
		if (!hasNext())
			throw new NoSuchElementException();
		consumed = true;
		return element;
	}

	@NonNull
	@Constant
	public static <A> Iterator<A> from(A element) {
		return new SingleIterator<A>(element);
	}
}
