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

package net.sf.staccatocommons.instrument;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import net.sf.staccatocommons.instrument.config.InstrumenterConfigurer;
import net.sf.staccatocommons.instrument.internal.ClassNames;
import net.sf.staccatocommons.instrument.internal.Instrumenter;
import net.sf.staccatocommons.instrument.internal.InstrumenterImpl;
import net.sf.staccatocommons.restrictions.check.NonNull;

import org.apache.commons.io.DirectoryWalker;

/**
 * Instrumentation API entry point.
 * 
 * @see #runInstrumentation(InstrumenterConfigurer, Directory, String)
 * @author flbulgarelli
 */
public final class InstrumentationRunner {

  // TODO improve exception handling, provide more descriptive exceptions

  private InstrumentationRunner() {}

  /**
   * Instruments the given directory, reading each .class file, processing it,
   * and writing it back to the filesystem
   * 
   * @param configurer
   *          an {@link InstrumenterConfigurer} that will setup the actual
   *          {@link Instrumenter} that will be used to process the directory
   * @param processDirectory
   *          the directory with .class files to process
   * @param extraPath
   *          a string containing a list of additional paths to add to the
   *          classpath used on instrumentation. This string is
   *          system-dependent, using the platform separator and directory
   *          separator
   * @throws Exception
   * @see File#pathSeparator
   * @see File#separator
   */
  public static void runInstrumentation(@NonNull InstrumenterConfigurer configurer, @NonNull File processDirectory,
    @NonNull String extraPath) throws Exception {
    ClassPool classPool = new ClassPool(true);
    classPool.appendPathList(extraPath);
    classPool.appendClassPath(processDirectory.getAbsolutePath());
    InstrumenterImpl instrumenter = new InstrumenterImpl(classPool);
    configurer.configureInstrumenter(instrumenter);
    instrumenter.ensureConfigured();
    new InstrumentationContext(instrumenter, processDirectory, classPool).doInstrument();
  }

  private static class InstrumentationContext {
    private File processDirectory;
    private ClassPool classPool;
    private Instrumenter classInstrumenter;

    private InstrumentationContext(Instrumenter classInstrumenter, File processDirectory, ClassPool pool)
      throws NotFoundException {
      this.classInstrumenter = classInstrumenter;
      this.processDirectory = processDirectory;
      this.classPool = pool;
    }

    private void processAndWriteClass(File baseDir, File classfile) throws Exception {
      CtClass clazz = classPool.get(ClassNames.getClassName(baseDir, classfile));
      classInstrumenter.instrumentClass(clazz);
      if (clazz.isModified())
        clazz.writeFile(processDirectory.getAbsolutePath());
      clazz.detach();
    }

    private void doInstrument() throws Exception {
      new DirectoryWalker<File>() {
        protected void handleFile(File file, int depth, Collection<File> results) throws IOException {
          if (file.getName().endsWith(".class")) {
            try {
              processAndWriteClass(processDirectory, file);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }
        }
        public void run() throws IOException {
          walk(processDirectory, new LinkedList<File>());
        }
      }.run();
    }
  }
}
