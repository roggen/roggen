package net.sf.staccatocommons.lang;

import net.sf.staccatocommons.defs.tuple.Tuple2;

public abstract class Either<A, B> {

  public A left() {
    throw new IllegalStateException();
  }

  public B right() {
    throw new IllegalStateException();
  }
  
  public boolean isRight() {
    return false;
  }
  
  public boolean isLeft() {
    return !isRight();
  }

  public static class Left<A, B> extends Either<A, B> {
    private final A value;

    public Left(A value) {
      this.value = value;
    }

    @Override
    public A left() {
      return value;
    }
  }

  public static class Right<A, B> extends Either<A, B> {
    private final B value;

    public Right(B value) {
      this.value = value;
    }

    @Override
    public B right() {
      return value;
    }
    
    @Override
    public boolean isRight() {
      return true;
    }
  }

}