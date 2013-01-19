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

import net.sf.roggen.defs.tuple.Tuple2;

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