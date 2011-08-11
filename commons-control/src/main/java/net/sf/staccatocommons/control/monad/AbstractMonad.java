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
package net.sf.staccatocommons.control.monad;

import java.util.concurrent.ExecutorService;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.Evaluable;
import net.sf.staccatocommons.defs.Executable;
import net.sf.staccatocommons.defs.computation.Computation;
import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.lang.computation.Computations;
import net.sf.staccatocommons.lang.predicate.Predicates;

/**
 * @author flbulgarelli
 * 
 */
public abstract class AbstractMonad<A> implements Monad<A> {

  public final Monad<A> filter(final Evaluable<? super A> predicate) {
    return bind(new Applicable<A, Monad<A>>() {
      public Monad<A> apply(A arg) {
        if (predicate.eval(arg))
          return Monads.from(arg);
        return Monads.nil();
      }
    });
  }

  public final Monad<A> skip(A element) {
    return filter(Predicates.equal(element).not());
  }

  /* fmap f xs == xs >>= return . f */
  public final <B> Monad<B> map(final Applicable<? super A, ? extends B> function) {
    return bind(new Applicable<A, Monad<B>>() {
      public Monad<B> apply(A arg) {
        return Monads.from((B) function.apply(arg));
      }
    });
  }

  public final <B> Monad<B> flatMap(final Function<? super A, ? extends Iterable<? extends B>> function) {
    return bind(new Applicable<A, Monad<B>>() {
      public Monad<B> apply(A arg) {
        return Monads.iterable(function.apply(arg));
      }
    });
  }

  public Monad<A> fork(ExecutorService executor) {
    return bind(Monads.<A> async(executor));
  }

  public final Monad<Void> each(final Executable<? super A> block) {
    return bind(new Applicable<A, Monad<Void>>() {
      public Monad<Void> apply(A arg) {
        block.exec(arg);
        return Monads.from(null);
      }
    });
  }

  public final Computation<Void> processEach(Executable<? super A> block) {
    return each(block).process();
  }

  public final Computation<Void> process() {
    return Computations.from(this);
  }

}
