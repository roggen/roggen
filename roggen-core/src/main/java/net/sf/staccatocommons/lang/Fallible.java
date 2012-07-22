package net.sf.staccatocommons.lang;

import java.util.NoSuchElementException;

import net.sf.staccatocommons.defs.Thunk;
import net.sf.staccatocommons.restrictions.check.NonNull;

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