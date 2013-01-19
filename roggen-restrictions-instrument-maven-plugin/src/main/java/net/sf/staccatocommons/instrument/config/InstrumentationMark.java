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

package net.sf.staccatocommons.instrument.config;

import net.sf.staccatocommons.restrictions.check.NonNull;

/**
 * 
 * An instrumentation mark is pair of a string key and a byte array value that
 * is inserted on each file processed by an instrumenter, in order to
 * distinguish a processed from unprocessed class files.
 * 
 * Client code <strong>should not</strong> implement this interface directly,
 * but implement {@link SimpleInstrumentationMark} instead.
 * 
 * @author flbulgarelli
 * 
 */
public interface InstrumentationMark {

  /**
   * @return an attribute value added to processed class files
   */
  @NonNull
  byte[] getMarkAttributeValue();

  /**
   * @return an attribute keyF added to processed class files
   */
  @NonNull
  String getMarkAttributeName();

}