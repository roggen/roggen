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
package net.sf.staccatocommons.check.internal;

import net.sf.staccatocommons.defs.EmptyAware;
import net.sf.staccatocommons.defs.type.EmptyAwareType;

/**
 * {@link EmptyAwareType}s for internal usage
 * 
 * @author flbulgarelli
 */
public class EmptyAwareTypes {

	/**
	 * An {@link EmptyAwareType} for {@link EmptyAware}s. It implements
	 * {@link EmptyAwareType#isEmpty(Object)} sending {@link EmptyAware#isEmpty()}
	 * to its argument.
	 */
	public static final EmptyAwareType<EmptyAware> EMPTY_AWARE = new EmptyAwareType<EmptyAware>() {
		public boolean isEmpty(EmptyAware emptyAware) {
			return emptyAware.isEmpty();
		}
	};
}