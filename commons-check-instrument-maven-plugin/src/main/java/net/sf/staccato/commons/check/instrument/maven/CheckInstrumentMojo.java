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
package net.sf.staccato.commons.check.instrument.maven;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.staccato.commons.check.instrument.ArgumentsOnlyFilteredAnotationProcessor;
import net.sf.staccato.commons.check.instrument.MatchesProcessor;
import net.sf.staccato.commons.check.instrument.NonEmptyProcessor;
import net.sf.staccato.commons.check.instrument.NonIgnoredCheckBehaviorFilteredAnotationProcessor;
import net.sf.staccato.commons.check.instrument.NonNullProcessor;
import net.sf.staccato.commons.collections.stream.Streams;
import net.sf.staccato.commons.instrument.DefaultClassInstrumenter;
import net.sf.staccato.commons.instrument.Instrumentation;
import net.sf.staccato.commons.instrument.internal.PublicBehaviourFilteredAnnotationProcessor;
import net.sf.staccato.commons.instrument.processor.AnnotationProcessor;
import net.sf.staccato.commons.io.Directory;
import net.sf.staccato.commons.lang.SoftException;
import net.sf.staccato.commons.lang.function.Function;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @author flbulgarelli
 * @goal instrument
 */
public class CheckInstrumentMojo extends AbstractMojo {

	/**
	 * @required
	 * @parameter expression="${instrument.location}"
	 *            default-value="${project.build.directory}/classes"
	 */
	private String location;

	/**
	 * @readonly
	 * @required
	 * @parameter expression="${plugin.artifacts}"
	 */
	protected List<Artifact> pluginArtifactsList;

	/**
	 * @parameter default-value="false"
	 */
	protected boolean processNonPublicMethods;

	/**
	 * @parameter default-value="true"
	 */
	private boolean ignoreReturnChecks;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Instrumenting checks to classes located in " + location);
		String extraClasspath = createClassPathString();
		getLog().debug("Using classpath " + extraClasspath);

		try {
			Instrumentation.instrumentClasspath(//
				new DefaultClassInstrumenter(getProcessors()),
				new Directory(location),
				extraClasspath);
		} catch (Exception e) {
			getLog().error(e.getMessage());
			throw new MojoExecutionException("Unexpected error", e);
		}
		getLog().info("Checks instrumented sucessfully");
	}

	/**
	 * @return
	 */
	private Iterable<AnnotationProcessor> getProcessors() {
		return Streams
			.from(
				new NonNullProcessor(),
				new NonEmptyProcessor(),
				new MatchesProcessor())
			.map(new Function<AnnotationProcessor, AnnotationProcessor>() {

				public AnnotationProcessor apply(AnnotationProcessor arg) {
					arg = new NonIgnoredCheckBehaviorFilteredAnotationProcessor(arg);
					arg = processNonPublicMethods ? arg
						: new PublicBehaviourFilteredAnnotationProcessor(arg);
					arg = !ignoreReturnChecks ? arg
						: new ArgumentsOnlyFilteredAnotationProcessor(arg);
					return arg;
				}
			})
			.toList();
	}

	private String createClassPathString() {
		return StringUtils.join(
			Streams.from(pluginArtifactsList).map(new Function<Artifact, String>() {
				public String apply(Artifact arg) {
					try {
						return arg.getFile().getCanonicalPath();
					} catch (IOException e) {
						throw SoftException.soften(e);
					}
				}
			}).iterator(),
			File.pathSeparator);
	}

}