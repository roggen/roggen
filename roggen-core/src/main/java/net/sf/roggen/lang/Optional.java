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

import net.sf.roggen.defs.Applicable;
import net.sf.roggen.defs.Evaluable;
import net.sf.roggen.defs.Executable;
import net.sf.roggen.defs.ProtoMonad;
import net.sf.roggen.defs.Thunk;
import net.sf.roggen.defs.partial.ContainsAware;
import net.sf.roggen.defs.partial.SizeAware;
import net.sf.roggen.restrictions.check.NonNull;

public interface Optional<A> extends Thunk<A>, Iterable<A>, SizeAware, ContainsAware<A>,
    ProtoMonad<Optional<A>, Optional, A> {

  /**
   * Gets the optional value, if defined, or throws an
   * {@link NoSuchElementException}, otherwise.
   * 
   * @return The optional value. This value is nullable, if client code
   *         considers null as possible, valid values. Non null otherwise.
   *         Please prefer the second approach, as normally, null values are
   *         there in code to represent optional data, so nullable values in
   *         optional values is in most scenarios completely redundant,
   *         unnecessary an error prone.
   * @throws NoSuchElementException
   *           if this option is undefined, and thus there is no value.
   * @throws RuntimeException if this option           
   */
  A value() throws NoSuchElementException;

  /**
   * Returns if the value has been defined or not.
   * 
   * @return true is the value is defined. False otherwise
   */
  boolean isDefined();

  /**
   * 
   * @return !{@link #isDefined()}
   */
  boolean isUndefined();

  /**
   * Applies the given <code>function</code> to the Optional's value and wraps it
   * into an Optional, if defined. Returns {@link #none()}, otherwise
   * 
   * @param <T2>
   * @param function
   * @return the mapped {@link Optional}
   */
  <T2> Optional<T2> map(Applicable<? super A, ? extends T2> function);

  /**
   * Answers this option if defined and the given <code>predicate</code>
   * evaluates to <code>true</code>. Answers {@link #none()}, otherwise
   * 
   * @param predicate
   * @return the filtered Optional
   */
  Optional<A> filter(Evaluable<? super A> predicate);

  /**
   * Returns the value of this {@link Optional}, or the provided object if
   * undefined
   * 
   * @param other
   *          the return value in case this {@link Optional} is undefined
   * @return <code>this.value()</code> if defined, other <code>otherwise</code>
   */
  A valueOrElse(A other);

  /**
   * Returns the value of this {@link Optional}, or the provided object if
   * undefined
   * 
   * @param other
   *          the thunk of the return value in case this {@link Optional} is
   *          undefined
   * @return <code>this.value()</code> if defined, other.value()
   *         <code>otherwise</code>
   */
  A valueOrElse(Thunk<? extends A> other);

  /**
   * Returns the value of this {@link Optional}, or <code>null</code>, if
   * undefined.
   * 
   * @return <code>this.value()</code> if defined, or <code>null</code>,
   *         otherwise
   */
  A valueOrNull();

  /**
   * Executed the given block if this option is defined
   * 
   * @param block
   */
  void ifDefined(@NonNull Executable<? super A> block);
  
  

}