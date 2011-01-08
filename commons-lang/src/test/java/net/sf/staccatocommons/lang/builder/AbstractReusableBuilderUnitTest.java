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
package net.sf.staccatocommons.lang.builder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class AbstractReusableBuilderUnitTest {

	@Test
	public void testDone_OK() throws Exception {
		Map<String, String> c = new TestBuilder().withY("hello").withX("world").build();
		assertNotNull(c);
	}

	@Test(expected = ObjectUnderConstructionException.class)
	public void testDone_NotFinished() throws Exception {
		new TestBuilder() //
			.withX("hello")
			.build();
	}

	@Test
	public void testDone_InvokedTwice() throws Exception {
		TestBuilder builder = new TestBuilder() //
			.withY("hello")
			.withX("world");

		Map<String, String> firstBuild = builder.build();
		assertNotSame(firstBuild, builder.build());
	}

	private final class TestBuilder extends AbstractReusableBuilder<Map<String, String>> {

		private String x;
		private String y;

		private TestBuilder() {
			super();
		}

		public TestBuilder withX(String x) {
			this.x = x;
			return this;
		}

		public TestBuilder withY(String y) {
			this.y = y;
			return this;
		}

		@Override
		protected Map<String, String> buildObject() {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("X", x);
			map.put("Y", y);
			return map;
		}

		@Override
		protected void checkDone(DoneCheck c) {
			c.isNotNull("X", x);
			c.isNotNull("Y", y);
		}
	}

}