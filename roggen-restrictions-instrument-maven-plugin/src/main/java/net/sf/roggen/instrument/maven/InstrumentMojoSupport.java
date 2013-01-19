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

package net.sf.roggen.instrument.maven;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.sf.roggen.instrument.InstrumentationRunner;
import net.sf.roggen.instrument.config.InstrumenterConfigurer;
import net.sf.roggen.instrument.internal.Ensure;
import net.sf.roggen.restrictions.check.NonNull;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @author flbulgarelli
 * 
 */
public abstract class InstrumentMojoSupport {

  private final String location;

  private final Artifact artifact;

  private final Collection<Artifact> projectArtifactsList;

  private final Mojo mojo;

  /**
   * Creates a new {@link InstrumentMojoSupport}
   */
  public InstrumentMojoSupport(@NonNull Mojo mojo, @NonNull String location, @NonNull Artifact artifact,
    Collection<Artifact> projectArtifactsList) {
    Ensure.isNotNull("mojo", mojo);
    Ensure.isNotNull("location", location);
    Ensure.isNotNull("artifact", artifact);
    Ensure.isNotNull("projectArtifactsList", projectArtifactsList);
    this.mojo = mojo;
    this.location = location;
    this.artifact = artifact;
    this.projectArtifactsList = projectArtifactsList;
  }

  /**
   * Executes the mojo
   * 
   * @throws MojoExecutionException
   * @throws MojoFailureException
   */
  public void execute() throws MojoExecutionException, MojoFailureException {
    mojo.getLog().info("Instrumenting classes located in " + location);
    try {
      String extraClasspath = createClassPathString();
      mojo.getLog().debug("Using classpath " + extraClasspath);
      InstrumentationRunner.runInstrumentation(//
        createConfigurer(),
        new File(location),
        extraClasspath);
    } catch (Exception e) {
      mojo.getLog().error(e.getMessage());
      throw new MojoExecutionException("Unexpected error", e);
    }
    mojo.getLog().info("Classes instrumented sucessfully");
  }

  private String createClassPathString() throws IOException {
    List<String> aritifactsCannonicalPaths = new LinkedList<String>();
    for (Artifact projectArtifact : projectArtifactsList) {
      if (!projectArtifact.equals(artifact)) {
        aritifactsCannonicalPaths.add(projectArtifact.getFile().getCanonicalPath());
      }
    }
    return StringUtils.join(aritifactsCannonicalPaths, File.pathSeparator);
  }

  /**
   * Create the instrumenter configurer
   */
  @NonNull
  protected abstract InstrumenterConfigurer createConfigurer();

}
