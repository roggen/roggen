package net.sf.staccato.commons.collections.stream.impl.internal;

import java.util.Iterator;

import net.sf.staccato.commons.collections.iterable.internal.AbstractUnmodifiableIterator;
import net.sf.staccato.commons.collections.stream.AbstractStream;
import net.sf.staccato.commons.collections.stream.Stream;

/**
 * @author flbulgarelli
 * 
 */
public final class ConcatStream<A> extends AbstractStream<A> {
	private final Stream<A> stream;
	private final Stream<A> other;

	/**
	 * Creates a new {@link ConcatStream}
	 */
	public ConcatStream(Stream<A> stream, Stream<A> other) {
		this.stream = stream;
		this.other = other;
	}

	public Iterator<A> iterator() {
		return new AbstractUnmodifiableIterator<A>() {
			private Iterator<A> iter = stream.iterator();
			private boolean second = false;

			public boolean hasNext() {
				if (iter.hasNext())
					return true;

				if (second)
					return false;

				iter = other.iterator();
				second = true;
				return iter.hasNext();
			}

			public A next() {
				return iter.next();
			}

		};
	}
}