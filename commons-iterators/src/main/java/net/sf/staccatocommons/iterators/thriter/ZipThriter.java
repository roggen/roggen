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
package net.sf.staccatocommons.iterators.thriter;

import java.util.NoSuchElementException;

import net.sf.staccatocommons.check.annotation.NonNull;
import net.sf.staccatocommons.defs.Applicable2;

/**
 * @author flbulgarelli
 * 
 */
public class ZipThriter<A, B, C> extends AdvanceThriter<C> {
	final Thriter<A> thriter1;
	final Thriter<B> thriter2;
	final Applicable2<A, B, C> function;

	public ZipThriter(@NonNull Thriter<A> thriter1, @NonNull Thriter<B> thriter2,
		@NonNull Applicable2<A, B, C> function) {
		this.thriter1 = thriter1;
		this.thriter2 = thriter2;
		this.function = function;
	}

	public boolean hasNext() {
		return thriter1.hasNext() && thriter2.hasNext();
	}

	public void advance() throws NoSuchElementException {
		thriter1.advance();
		thriter2.advance();
	}

	public C current() throws NoSuchElementException {
		return function.apply(thriter1.current(), thriter2.current());
	}
}
