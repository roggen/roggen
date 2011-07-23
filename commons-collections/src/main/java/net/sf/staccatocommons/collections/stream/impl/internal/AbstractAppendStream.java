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
package net.sf.staccatocommons.collections.stream.impl.internal;

import net.sf.staccatocommons.collections.stream.AbstractStream;
import net.sf.staccatocommons.collections.stream.Stream;
import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * @author flbulgarelli
 */
public abstract class AbstractAppendStream<A> extends AbstractStream<A> {

  private final Stream<A> source;

  /**
   * Creates a new {@link AbstractAppendStream}
   */
  public AbstractAppendStream(@NonNull Stream<A> source) {
    this.source = source;
  }

  protected final Stream<A> getSource() {
    return source;
  }

  public final int size() {
    return getSource().size() + 1;
  }

  public final boolean isEmpty() {
    return false;
  }

}