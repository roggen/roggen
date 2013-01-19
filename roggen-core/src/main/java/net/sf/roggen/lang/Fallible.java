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

package net.sf.roggen.lang;

import java.util.NoSuchElementException;

import net.sf.roggen.defs.Thunk;
import net.sf.roggen.restrictions.check.NonNull;

public class Fallible<A> extends AbstractOptional<A> {

  private final Thunk<A> thunk;

  public Fallible(@NonNull Thunk<A> thunk) {
    this.thunk = thunk;
  }

  @Override
  public boolean isDefined() {
    return either().isRight();
  }

  @Override
  public A value() throws NoSuchElementException {
    if (either().isRight()) return either().right();
    throw either().left();
  }

  private volatile Either<RuntimeException, A> either;

  protected Either<RuntimeException, A> either() {
    Either<RuntimeException, A> result = either;
    if (result == null) {
      synchronized (this) {
        result = either;
        if (result == null) either = result = init();
      }
    }
    return result;
  }

  protected Either<RuntimeException, A> init() {
    try {
      return new Either.Right(thunk.value());
    } catch (RuntimeException e) {
      return new Either.Left(e);
    }
  }

}