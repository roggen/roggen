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

/**
 * This package contains {@link net.sf.staccatocommons.io.internal.lifecycle.Lifecycle}
 * and {@link net.sf.staccatocommons.io.internal.lifecycle.CloseableLifecycle}, 
 * classes that help to automate handling of resources which need to be initialized
 * before being used, and then disposed.
 * 
 * This package has a similar intention of JDK7 proposal for a try-catch
 * with automated close of resources, so it may become deprecated in the near future.
 * 
 */
package net.sf.staccatocommons.io.internal.lifecycle;

