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
package net.sf.staccato.commons.io;

import java.util.Iterator;

import net.sf.staccato.commons.check.annotation.NonNull;

/**
 * @author flbulgarelli
 * 
 */
public class IOIterators {

	@NonNull
	public static <A> Iterator<A> from(@NonNull Readable redable,
		@NonNull ReadStrategy<A> readStrategy) {
		return new ReadableIterator(readStrategy, redable);
	}

}