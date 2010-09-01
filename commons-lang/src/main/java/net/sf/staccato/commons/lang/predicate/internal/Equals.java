package net.sf.staccato.commons.lang.predicate.internal;

import net.sf.staccato.commons.lang.predicate.Predicate;


/**
 * @author flbulgarelli
 * 
 * @param <T>
 */
public final class Equals<T> extends Predicate<T> {
	private final T value;

	/**
	 * Creates a new {@link Equals}
	 * 
	 * @param value
	 *          the value to test equality
	 */
	public Equals(T value) {
		this.value = value;
	}

	public boolean eval(T argument) {
		return value.equals(argument);
	}
}