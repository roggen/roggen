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


package net.sf.staccatocommons.restrictions.instrument.maven;

import java.io.File;
import java.util.Collection;

import net.sf.staccatocommons.instrument.config.InstrumenterConfigurer;
import net.sf.staccatocommons.instrument.maven.InstrumentMojoSupport;
import net.sf.staccatocommons.restrictions.instrument.RestrictionConfigurer;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Instruments a project build path with
 * staccato-commons-restrictions-instrument, processing restriction annotations
 * supported by the tool
 * 
 * @author flbulgarelli
 * @goal instrument
 * @requiresDependencyResolution
 */
public class RestrictionsInstrumentMojo extends AbstractMojo {

  /**
   * @readonly
   * @required
   * @parameter expression="${project.build.outputDirectory}"
   */
  private String location;

  /**
   * @readonly
   * @required
   * @parameter expression="${project.artifacts}"
   */
  private Collection<Artifact> projectArtifactsList;

  /**
   * If check annotation on methods should be ignored, or should be processed
   * with assertion checks on their return types
   * 
   * @parameter default-value="true"
   */
  private boolean ignoreReturnChecks;

  /**
   * If check annotations should be ignored or not. If set to <code>true</code>,
   * then <code>ignoreReturnChecks</code> has no effect.
   * 
   * Notice: in order to properly instrument checks, target project must depend
   * on the staccato-commons-check dependency
   * 
   * @parameter default-value="false"
   */
  private boolean ignoreChecks;

  /**
   * If constant annotation on methods should be ignored or not
   * 
   * @parameter default-value="false"
   */
  private boolean ignoreConstants;

  /**
   * The current artifact being instrumented. The mojo will normally not need
   * this object, except on those projects from staccato-commons that are both
   * dependencies of this mojo and consumers of it - like staccato-commons-lang,
   * io, etc
   * 
   * @readonly
   * @required
   * @parameter default-value="${project.artifact}"
   */
  private Artifact artifact;

  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("*** Staccato-Commons-Restrictions-Instrument *** ");
    if (!new File(location).exists()) {
      getLog().info("Nothing to instrument");
      return;
    }
    new InstrumentMojoSupport(this, location, artifact, projectArtifactsList) {

      @Override
      protected InstrumenterConfigurer createConfigurer() {
        return new RestrictionConfigurer(ignoreReturnChecks, ignoreChecks, ignoreConstants);
      }
    }.execute();
  }

}
