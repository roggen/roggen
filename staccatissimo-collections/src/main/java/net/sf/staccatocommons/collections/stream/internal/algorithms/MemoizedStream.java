/**
 *  Copyright (c) 2012, The Roggen Team
 *  Copyright (c) 2010-2012, The StaccatoCommons Team
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 */


package net.sf.staccatocommons.collections.stream.internal.algorithms;

import java.util.NoSuchElementException;

import net.sf.staccatocommons.collections.stream.AbstractStream;
import net.sf.staccatocommons.collections.stream.Stream;
import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.iterators.EmptyThriterator;
import net.sf.staccatocommons.iterators.thriter.AdvanceThriterator;
import net.sf.staccatocommons.iterators.thriter.Thriterator;

/**
 * @author flbulgarelli
 * 
 */
public class MemoizedStream<A> extends AbstractStream<A> {

  private SingleLinkedDelayedQueue<A> previous = new SingleLinkedDelayedQueue<A>();

  private Thriterator<A> remaining;

  /**
   * 
   * Creates a new {@link MemoizedStream}
   */
  public MemoizedStream(Thriterator<A> source) {
    this.remaining = source;
  }

  @Override
  public boolean isEmpty() {
    return previous.isEmpty() && !remaining.hasNext();
  }

  @Override
  public Stream<A> memoize() {
    return this;
  }

  @Override
  public Thriterator<A> iterator() {
    if (remaining.isEmpty()) {
      if (previous.isEmpty())
        return EmptyThriterator.empty();
      return previous.iterator();
    }
    final Thriterator<A> previousIter = previous.iterator();
    return new AdvanceThriterator<A>() {
      private Thunk<A> current;
      private Thriterator<A> iter = previousIter;
      private boolean remainingIterationStarted = false;

      public boolean hasNext() {
        if (remainingIterationStarted)
          return iter.hasNext();
        return remaining.hasNext();
      }

      public void advanceNext() throws NoSuchElementException {
        if (!remainingIterationStarted && !iter.hasNext()) {
          iter = newRemaningIterator();
          remainingIterationStarted = true;
        }
        iter.advanceNext();
      }

      public A current() {
        return iter.current();
      }

      @Override
      public Thunk<A> delayedCurrent() {
        return iter.delayedCurrent();
      }

      public Thriterator<A> newRemaningIterator() {
        return new AdvanceThriterator<A>() {
          public boolean hasNext() {
            return remaining.hasNext();
          }

          public void advanceNext() throws NoSuchElementException {
            remaining.advanceNext();
            current = remaining.delayedCurrent();
            previous.add(current);
          }

          public A current() {
            return delayedCurrent().value();
          }

          @Override
          public Thunk<A> delayedCurrent() {
            return current;
          }
        };
      }
    };
  }

}
