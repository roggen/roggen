/*
 Copyright (c) 2010, The Staccato-Commons Team

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; version 3 of the License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 */
package net.sf.staccatocommons.instrument.context.internal;

import java.util.Set;

import javassist.ClassPool;
import net.sf.staccatocommons.collections.stream.Streams;
import net.sf.staccatocommons.instrument.context.AnnotationContext;
import net.sf.staccatocommons.lang.function.Function;

import org.slf4j.Logger;

/**
 * @author flbulgarelli
 * 
 */
public class AbstractAnnotationContext implements AnnotationContext {

	private final Logger logger;
	private final ClassPool classPool;

	private Set<String> presentAnnotations;

	public AbstractAnnotationContext(ClassPool pool, Logger logger) {
		this.logger = logger;
		this.classPool = pool;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	@Override
	public void logDebugMessage(String message, Object... arguments) {
		getLogger().debug(message, arguments);
	}

	@Override
	public void logInfoMessage(String message, Object... arguments) {
		getLogger().info(message, arguments);
	}

	@Override
	public void logWarnMessage(String message, Object... arguments) {
		getLogger().warn(message, arguments);
	}

	@Override
	public void logErrorMessage(String message, Object... arguments) {
		getLogger().error(message, arguments);
	}

	public ClassPool getClassPool() {
		return classPool;
	}

	/**
	 * @return the presentAnnotations
	 */
	public Set<String> getPresentAnnotationsTypes(Object[] annotations) {
		if (presentAnnotations == null) {
			presentAnnotations = Streams.from(annotations).map(new Function<Object, String>() {
				public String apply(Object arg) {
					return arg.getClass().getName();
				}
			}).toSet();
		}
		return presentAnnotations;
	}
}