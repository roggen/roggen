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


package net.sf.roggen.control.monad.internal;

import java.util.concurrent.BlockingQueue;

import net.sf.roggen.control.monad.Monad;
import net.sf.roggen.control.monad.MonadicValue;
import net.sf.roggen.defs.Applicable;

/**
 * @author flbulgarelli
 * 
 */
public class BlockingMonadValue<A> implements MonadicValue<A> {

  private final BlockingQueue<? extends A> queue;

  /**
   * Creates a new {@link BlockingMonadValue}
   */
  public BlockingMonadValue(BlockingQueue<? extends A> queue) {
    this.queue = queue;
  }

  public <T> void eval(Applicable<? super A, Monad<T>> function) {
    try {
      while (true)
        function.apply(queue.take()).value();
    } catch (InterruptedException e) {
      // stream end
    }
  }

}
