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
package net.sf.staccato.commons.lang.block;

import net.sf.staccato.commons.lang.Executable2;

public abstract class Block2<T1, T2> implements Executable2<T1, T2> {

	public abstract void exec(T1 argument1, T2 argument2);

	// public final Block<T2> exec(final T1 argument1) {
	// return new Block<T2>() {
	// public void exec(T2 argument2) {
	// Block2.this.exec(argument1, argument2);
	// }
	// };
	// }

	public final Block2<T1, T2> then(final Block2<T1, T2> other) {
		return new Block2<T1, T2>() {
			public void exec(T1 argument1, T2 argument2) {
				Block2.this.exec(argument1, argument2);
				other.exec(argument1, argument2);
			}
		};
	}

}