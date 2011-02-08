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
package net.sf.staccatocommons.applicables.impl;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.Delayable;
import net.sf.staccatocommons.defs.Thunk;

/**
 * @author flbulgarelli
 * 
 */
public abstract class AbstractDelayable<A, B> implements Applicable<A, B>, Delayable<A, B> {

	public Thunk<B> delayed(final A arg) {
		return new Thunk<B>() {
			public B value() {
				return AbstractDelayable.this.apply(arg);
			}
		};
	}

}