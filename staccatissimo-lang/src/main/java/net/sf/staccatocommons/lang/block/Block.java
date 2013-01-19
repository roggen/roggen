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

package net.sf.staccatocommons.lang.block;

import net.sf.staccatocommons.defs.Executable;
import net.sf.staccatocommons.defs.partial.NullSafeAware;
import net.sf.staccatocommons.lang.SoftException;
import net.sf.staccatocommons.lang.function.AbstractDelayable;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * An abstract, one argument code block, that implements {@link Executable}
 * 
 * @author flbulgarelli
 * 
 * @param <T>
 */
public abstract class Block<T> extends AbstractDelayable<T, Void> implements Executable<T>,
  NullSafeAware<Block<T>> {

  /**
   * Executes this block. This implementation just invokes
   * {@link #softExec(Object)}, and softens any exception it may throw.
   * Subclasses may override this behavior.
   * 
   */
  @Override
  public void exec(@NonNull T argument) {
    try {
      softExec(argument);
    } catch (Throwable e) {
      throw SoftException.soften(e);
    }
  }

  /**
   * Executes this block, potentially throwing a checked {@link Throwable}
   * 
   * @see #exec(Object)
   * 
   * @param argument
   * @throws Exception
   */
  protected void softExec(T argument) throws Throwable {}

  public Void apply(T arg) {
    exec(arg);
    return null;
  }

  public Block<T> nullSafe() {
    return new Block<T>() {
      public void exec(T argument) {
        if (argument == null)
          return;
        Block.this.exec(argument);
      };
    };
  }

  /**
   * Chains this block with the given executable, creating a new {@link Block}
   * that executes this one and then the another one.
   * 
   * @param other
   *          the block to execute after this
   * @return a new block that first invokes execute on this, and then on the
   *         {@link Executable} provided
   */
  @NonNull
  public Block<T> then(@NonNull final Executable<? super T> other) {
    return new Block<T>() {
      public void exec(T argument) {
        Block.this.exec(argument);
        other.exec(argument);
      }
    };
  }

  public String toString() {
    return "Block";
  }

}
