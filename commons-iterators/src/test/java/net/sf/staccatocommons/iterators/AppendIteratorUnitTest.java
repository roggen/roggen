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

import net.sf.staccatocommons.testing.junit.theories.IteratorTheories;

/**
 * @author flbulgarelli
 * 
 */
public class AppendIteratorUnitTest extends IteratorTheories {

	protected Iterator<?> createTwoElementsIterator() {
		return new AppendIterator<Integer>(SingleIterator.from(20), 10);
	}

	protected Iterator<?> createOneElementIterator() {
		return new AppendIterator(EmptyIterator.empty(), 10);
	}

}