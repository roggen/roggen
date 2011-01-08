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
package net.sf.staccatocommons.lang.tuple;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static net.sf.staccatocommons.lang.tuple.Tuple._;

import java.util.Date;

/**
 * Test for {@link Quadruple}
 * 
 * @author flbulgarelli
 * 
 */
public class QuadrupleUnitTest extends TupleAbstractUnitTest {

	@Override
	public void testComponents() throws Exception {
		Quadruple<Integer, String, String, String> quad = _(9, "Hello", "World", "!");
		assertEquals((Integer) 9, quad.getFirst());
		assertSame(quad._1(), quad.getFirst());

		assertEquals("Hello", quad.getSecond());
		assertSame(quad._2(), quad.getSecond());

		assertEquals("World", quad.getThird());
		assertSame(quad._3(), quad.getThird());

		assertEquals("!", quad.getFourth());
		assertSame(quad._4(), quad.getFourth());
	}

	@Override
	public void testComparability() throws Exception {
		assertTrue(_(50, 2, 1, 5).compareTo(_(50, 2, 1, 5)) == 0);
		assertTrue(_(50, 2, 1, 5).compareTo(_(50, 2, 1, 4)) > 0);
		assertTrue(_(50, 2, 1, 5).compareTo(_(50, 2, 0, 5)) > 0);
		assertTrue(_(50, 2, 1, 5).compareTo(_(50, 1, 1, 5)) > 0);
		assertTrue(_(50, 2, 1, 5).compareTo(_(49, 2, 1, 5)) > 0);
	}

	@Override
	public void testEqualty() throws Exception {
		assertEquals(_(5, 90L, "blah", "bleh"), _(5, 90L, "blah", "bleh"));
		assertFalse(_("Hello", 5, 2, 3).equals(_("World", 5, 2, 4)));
		Date date = new Date();
		assertEquals(_(40, date, "hello", 'A').hashCode(), _(40, date.clone(), "hello", 'A').hashCode());
	}

	@Override
	public void testToString() throws Exception {
		assertEquals("(90,6,5,9)", _(90, 6, 5, 9).toString());
	}

	@Override
	public void testToArray() throws Exception {
		Quadruple<Integer, Integer, Integer, Integer> quad = _(90, 6, 9, 5);
		Object[] a = quad.toArray();
		assertEquals(quad, _(a[0], a[1], a[2], a[3]));
	}

	@Override
	protected Tuple sampleTuple() {
		return _(9, "", 4);
	}
}