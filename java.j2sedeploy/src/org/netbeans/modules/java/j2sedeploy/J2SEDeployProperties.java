/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.java.j2sedeploy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ant.AntBuildExtender;
import org.netbeans.modules.java.j2seproject.api.J2SEPropertyEvaluator;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.netbeans.spi.project.support.ant.PropertyEvaluator;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.Mutex;
import org.openide.util.MutexException;

/**
 *  Java SE Deployment panel Project Properties support
 * 
 * @author Petr Somol
 * @author Tomas Zezula
 * @since java.j2seproject 1.65
 */
public final class J2SEDeployProperties {

    // Deployment - native packaging
    public static final String NATIVE_BUNDLING_ENABLED = "native.bundling.enabled"; //NOI18N
    // copied from JFXProjectProperties
    public static final String JAVAFX_ENABLED = "javafx.enabled"; // NOI18N
    private static final String J2SEDEPLOY_EXTENSION = "j2sedeploy";    //NOI18N
    private static final String BUILD_SCRIPT_PROTOTYPE = String.format(
        "%s/resources/build-native-prototype.xml",  //NOI18N
        J2SEDeployProperties.class.getPackage().getName().replace('.','/'));   //NOI18N
    private static final String EXTENSION_BUILD_SCRIPT_PATH = "nbproject/build-native.xml";        //NOI18N
    
    // Project related references
    private J2SEPropertyEvaluator j2sePropEval;
    private PropertyEvaluator evaluator;
    private Project project;

    public Project getProject() {
        return project;
    }
    public PropertyEvaluator getEvaluator() {
        return evaluator;
    }
    
    boolean nativeBundlingEnabled;
    public boolean getNativeBundlingEnabled() {
        return nativeBundlingEnabled;
    }
    public void setNativeBundlingEnabled(boolean enabled) {
        this.nativeBundlingEnabled = enabled;
    }

    /** Keeps singleton instance of J2SEDeployProperties for SE project with Deployment category open */
    private static Map<String, J2SEDeployProperties> propInstance = new HashMap<String, J2SEDeployProperties>();

    /** Factory method */
    public static J2SEDeployProperties getInstance(Lookup context) {
        Project proj = context.lookup(Project.class);
        String projDir = proj.getProjectDirectory().getPath();
        J2SEDeployProperties prop = propInstance.get(projDir);
        if(prop == null) {
            prop = new J2SEDeployProperties(context);
            propInstance.put(projDir, prop);
        }
        return prop;
    }

    /** Getter method */
    public static J2SEDeployProperties getInstanceIfExists(Project proj) {
        assert proj != null;
        String projDir = proj.getProjectDirectory().getPath();
        J2SEDeployProperties prop = propInstance.get(projDir);
        if(prop != null) {
            return prop;
        }
        return null;
    }

    /** Getter method */
    public static J2SEDeployProperties getInstanceIfExists(Lookup context) {
        Project proj = context.lookup(Project.class);
        return getInstanceIfExists(proj);
    }

    public static void cleanup(Lookup context) {
        Project proj = context.lookup(Project.class);
        String projDir = proj.getProjectDirectory().getPath();
        propInstance.remove(projDir);
    }

    /** Creates a new instance of J2SEDeployProperties */
    private J2SEDeployProperties(Lookup context) {       
        project = context.lookup(Project.class);
        if (project != null) {
            j2sePropEval = project.getLookup().lookup(J2SEPropertyEvaluator.class);
            evaluator = j2sePropEval.evaluator();
            nativeBundlingEnabled = isTrue(evaluator.getProperty(NATIVE_BUNDLING_ENABLED));
        }
    }
    
    private static void setOrRemove(@NonNull org.netbeans.spi.project.support.ant.EditableProperties props, @NonNull String name, String value) {
        if (value != null) {
            props.setProperty(name, value);
        } else {
            props.remove(name);
        }
    }
  
    public void store() throws IOException {
        final EditableProperties ep = new EditableProperties(true);
        final FileObject projPropsFO = project.getProjectDirectory().getFileObject(AntProjectHelper.PROJECT_PROPERTIES_PATH);
        try {
            ProjectManager.mutex().writeAccess(new Mutex.ExceptionAction<Void>() {
                @Override
                public Void run() throws Exception {
                    try (final InputStream is = projPropsFO.getInputStream()) {
                        ep.load(is);
                    }
                    setOrRemove(ep, NATIVE_BUNDLING_ENABLED, nativeBundlingEnabled ? "true" : null); //NOI18N
                    FileLock lock = projPropsFO.lock();
                    try (OutputStream os = projPropsFO.getOutputStream(lock)) {
                        ep.store(os);
                    } finally {
                        lock.releaseLock();
                    }
                    final AntBuildExtender extender = project.getLookup().lookup(AntBuildExtender.class);
                    if (extender != null) {
                        AntBuildExtender.Extension extension = extender.getExtension(J2SEDEPLOY_EXTENSION);
                        if (nativeBundlingEnabled) {
                            if (extension == null) {
                                final FileObject buildExFoBack = project.getProjectDirectory().getFileObject(String.format(
                                    "%s~",  //NOI18N
                                    EXTENSION_BUILD_SCRIPT_PATH));
                                if (buildExFoBack != null) {
                                    buildExFoBack.delete();
                                }
                                FileObject buildExFo = project.getProjectDirectory().getFileObject(EXTENSION_BUILD_SCRIPT_PATH);
                                if (buildExFo != null) {
                                    lock = buildExFo.lock();
                                    try {
                                        buildExFo.rename(
                                            lock,
                                            buildExFo.getName(),
                                            String.format(
                                                "%s~",  //NOI18N
                                                buildExFo.getExt()));
                                    } finally {
                                        lock.releaseLock();
                                    }
                                }
                                buildExFo = FileUtil.createData(project.getProjectDirectory(), EXTENSION_BUILD_SCRIPT_PATH);
                                lock = buildExFo.lock();
                                try (final InputStream in = getClass().getClassLoader().getResourceAsStream(BUILD_SCRIPT_PROTOTYPE);
                                     final OutputStream out = buildExFo.getOutputStream(lock)) {
                                    FileUtil.copy(in, out);
                                } finally {
                                    lock.releaseLock();
                                }
                                extension = extender.addExtension(J2SEDEPLOY_EXTENSION, buildExFo);
                            }
                        } else {
                            if (extension != null) {
                                extender.removeExtension(J2SEDEPLOY_EXTENSION);
                            }
                            final FileObject buildExFo = project.getProjectDirectory().getFileObject(EXTENSION_BUILD_SCRIPT_PATH);
                            if (buildExFo != null) {
                                buildExFo.delete();
                            }
                        }
                    }
                    return null;
                }
            });
        } catch (MutexException mux) {
            throw (IOException) mux.getException();
        }
    }
    
    public static boolean isTrue(final String value) {
        return value != null &&
                (value.equalsIgnoreCase("true") ||  //NOI18N
                 value.equalsIgnoreCase("yes") ||   //NOI18N
                 value.equalsIgnoreCase("on"));     //NOI18N
    }

    public static boolean isNonEmpty(String s) {
        return s != null && !s.isEmpty();
    }
            
    public static boolean isEqual(final String s1, final String s2) {
        return (s1 == null && s2 == null) ||
                (s1 != null && s2 != null && s1.equals(s2));
    }                                   

    public static boolean isEqualIgnoreCase(final String s1, final String s2) {
        return (s1 == null && s2 == null) ||
                (s1 != null && s2 != null && s1.equalsIgnoreCase(s2));
    }                                   

    public static boolean isEqualText(final String s1, final String s2) {
        return ((s1 == null || s1.isEmpty()) && (s2 == null || s2.isEmpty())) ||
                (s1 != null && s2 != null && s1.equals(s2));
    }                                   

}
