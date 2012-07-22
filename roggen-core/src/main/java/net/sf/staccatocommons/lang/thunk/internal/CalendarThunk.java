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


package net.sf.staccatocommons.lang.thunk.internal;

import java.util.Calendar;

import net.sf.staccatocommons.defs.Thunk;

/**
 * @author flbulgarelli
 * 
 */
public class CalendarThunk implements Thunk<Calendar> {

  /** An instance */
  public static final Thunk<Calendar> INSTANCE = new CalendarThunk();

  public Calendar value() {
    return Calendar.getInstance();
  }

}
