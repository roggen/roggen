package net.sf.staccato.commons.collections.stream.impl.internal;

import java.util.Iterator;

import net.sf.staccato.commons.collections.iterable.internal.AbstractUnmodifiableIterator;
import net.sf.staccato.commons.collections.stream.AbstractStream;
import net.sf.staccato.commons.collections.stream.Stream;
import net.sf.staccato.commons.lang.Applicable;

/**
 * @author flbulgarelli
 * 
 * @param <B>
 */
public final class MapStream<A, B> extends AbstractStream<B> {
	private final Stream<A> stream;
	private final Applicable<? super A, ? extends B> function;

	/**
	 * Creates a new {@link MapStream}
	 */
	public MapStream(Stream<A> stream, Applicable<? super A, ? extends B> function) {
		this.stream = stream;
		this.function = function;
	}

	public Iterator<B> iterator() {
		final Iterator<A> iter = stream.iterator();
		return new AbstractUnmodifiableIterator<B>() {
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			public B next() {
				return function.apply(iter.next());
			}
		};
	}

}