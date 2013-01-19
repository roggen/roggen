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


package net.sf.roggen.lang.block.internal;

import java.io.Serializable;

import net.sf.roggen.lang.block.Block;
import net.sf.roggen.lang.internal.ToString;

/**
 * @author flbulgarelli
 * 
 */
public abstract class TopLevelBlock<T> extends Block<T> implements Serializable {

  private static final long serialVersionUID = 4373223469080466884L;

  public String toString() {
    return ToString.toString(this);
  }

}
