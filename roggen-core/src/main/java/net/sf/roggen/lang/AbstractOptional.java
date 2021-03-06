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

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.Evaluable;
import net.sf.roggen.defs.Executable;
import net.sf.roggen.defs.Thunk;
import net.sf.roggen.iterators.thriter.Thriterators;
import net.sf.roggen.restrictions.check.NonNull;

import org.apache.commons.lang.ObjectUtils;

public abstract class AbstractOptional<A> extends AbstractProtoMonad<Optional<A>, Optional, A> implements
   Optional<A> {


  public boolean isUndefined() {
    return !isDefined();
  }
  
  @Override
  public boolean isEmpty() {
    return isUndefined();
  }
  
  public boolean contains(A element) {
    return isDefined() && ObjectUtils.equals(value(), element);
  }

  @Override
  public int size() {
    return isDefined() ? 1 : 0;
  }
  
  public final <T2> Optional<T2> map(Applicable<? super A, ? extends T2> function) {
    if (isDefined())
      return Option.some((T2) function.apply(value()));
    return Option.none();
  }

  public final Optional<A> filter(Evaluable<? super A> predicate) {
    if (isDefined() && predicate.eval(value()))
      return this;
    return Option.none();
  }

  @Override
  public A valueOrElse(A ifUndefined) {
    return isUndefined() ? ifUndefined : value();
  }

  @Override
  public A valueOrNull() {
    return isDefined() ? value() : null;
  }
  
  @Override
  public A valueOrElse(@NonNull Thunk<? extends A> ifUndefined) {
    return isUndefined() ? ifUndefined.value() : value();
  }

  @Override
  public void ifDefined(Executable<? super A> block) {
    if(isDefined()) block.exec(value());
  }

  public void forEach(@NonNull Executable<? super A> block) {
    ifDefined(block);
  }
  
  @Override
  public Iterator<A> iterator() {
    if (isDefined()) 
      return Thriterators.from(value());
    else
      return Thriterators.empty();
  }

}