/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 * 
 * Contributor(s):
 * 
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.profiler.utils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledDocument;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.classpath.GlobalPathRegistry;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.ui.ElementOpen;
import org.netbeans.api.project.Project;
import org.netbeans.lib.profiler.ProfilerLogger;
import org.netbeans.modules.profiler.projectsupport.utilities.SourceUtils;
import org.netbeans.modules.profiler.spi.GoToSourceProvider;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.LineCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.text.Line;

/**
 *
 * @author Jaroslav Bachorik
 */
@org.openide.util.lookup.ServiceProvider(service=org.netbeans.modules.profiler.spi.GoToSourceProvider.class)
public class GoToJavaSourceProvider implements GoToSourceProvider {

    public boolean openSource(final Project project, final String className, final String methodName, final String signature) {
        final JavaSource js = SourceUtils.getSources(project);
        final AtomicBoolean result = new AtomicBoolean(false);
        final CountDownLatch latch = new CountDownLatch(1);
        
        try {
            // use the prepared javasource repository and perform a task
            js.runUserActionTask(new CancellableTask<CompilationController>() {
                    public void cancel() {
                    }

                    public void run(CompilationController controller)
                             throws Exception {
                        controller.toPhase(JavaSource.Phase.ELEMENTS_RESOLVED);

                        Element destinationElement = null;

                        // resolve the class by name
                        TypeElement classElement = SourceUtils.resolveClassByName(className, controller);

                        if ((methodName != null) && (methodName.length() > 0)) {
                            // if a method name has been specified try to resolve the method
                            if (classElement != null) {
                                destinationElement = SourceUtils.resolveMethodByName(classElement, methodName, signature);
                            }
                        }

                        if (destinationElement == null) {
                            // unsuccessful attempt to resolve a method -> use the class instead
                            destinationElement = classElement;
                        }

                        if (destinationElement != null) {
                            ProfilerLogger.debug("Opening element: " + destinationElement); // NOI18N

                            final Element openElement = destinationElement;
                            
                            SwingUtilities.invokeLater(new Runnable() {
                                    // manipulates the TopComponent - must be executed in EDT
                                    public void run() {
                                        // opens the source code on the found method position
                                        result.set(ElementOpen.open(js.getClasspathInfo(), openElement));
                                        latch.countDown();
                                    }
                                });
                        } else {
                            latch.countDown();
                        }
                    }
                }, false);
        } catch (IOException ex) {
            ProfilerLogger.log(ex);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return result.get();
    }

    public boolean openSource(String className, int line) {
        FileObject source = GlobalPathRegistry.getDefault().findResource(className);
        if (source != null) {
            doOpen(source, line);
        }
        return false;
    }

    private static boolean doOpen(FileObject fo, int line) {
        try {
            DataObject od = DataObject.find(fo);
            EditorCookie ec = (EditorCookie) od.getCookie(EditorCookie.class);
            LineCookie lc = (LineCookie) od.getCookie(LineCookie.class);

            if (ec != null && lc != null && line != -1) {
                StyledDocument doc = ec.openDocument();
                if (doc != null) {
                    if (line != -1) {
                        Line l = lc.getLineSet().getCurrent(line-1);

                        if (l != null) {
                            l.show(Line.ShowOpenType.OPEN, Line.ShowVisibilityType.FOCUS);
                            return true;
                        }
                    }
                }
            }

            OpenCookie oc = (OpenCookie) od.getCookie(OpenCookie.class);

            if (oc != null) {
                oc.open();
                return true;
            }
        } catch (IOException e) {
            Logger.getLogger(GoToJavaSourceProvider.class.getName()).log(Level.FINE, "Can not open " + fo.getPath(), e);
        }

        return false;
    }
}
