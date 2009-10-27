/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.netbeans.modules.cnd.remote.support;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.project.Project;
import org.netbeans.modules.cnd.api.utils.IpeUtils;
import org.netbeans.modules.cnd.makeproject.api.configurations.ConfigurationSupport;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfiguration;
import org.netbeans.modules.cnd.makeproject.api.configurations.MakeConfigurationDescriptor;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.openide.filesystems.FileUtil;

/**
 * Misc projct related utility functions
 * @author Vladimir Kvashin
 */
public class RemoteProjectSupport {

    public static ExecutionEnvironment getExecutionEnvironment(Project project) {
        MakeConfiguration mk = ConfigurationSupport.getProjectActiveConfiguration(project);
        if (mk != null) {
            return mk.getDevelopmentHost().getExecutionEnvironment();
        }
        return null;
    }

    public static File getPrivateStorage(Project project) {
        File baseDir = FileUtil.toFile(project.getProjectDirectory()).getAbsoluteFile();
        final File privProjectStorage = new File(new File(baseDir, "nbproject"), "private"); //NOI18N
        return privProjectStorage;
    }

    public static File[] getProjectSourceDirs(Project project) {
        MakeConfiguration conf = ConfigurationSupport.getProjectActiveConfiguration(project);
        if (conf == null) {
            File baseDir = FileUtil.toFile(project.getProjectDirectory()).getAbsoluteFile();
            return new File[] { baseDir };
        } else {
            return getProjectSourceDirs(project, conf);
        }
    }

    public static File[] getProjectSourceDirs(Project project, MakeConfiguration conf) {
        File baseDir = FileUtil.toFile(project.getProjectDirectory()).getAbsoluteFile();
        if (conf == null) {
            return new File[] { baseDir };
        }
        // the project itself
        List<File> extraSourceRoots = new ArrayList<File>();
        MakeConfigurationDescriptor mcs = MakeConfigurationDescriptor.getMakeConfigurationDescriptor(project);
        for(String soorceRoot : mcs.getSourceRoots()) {
            String path = IpeUtils.toAbsolutePath(baseDir.getAbsolutePath(), soorceRoot);
            File file = new File(path); // or canonical?
            extraSourceRoots.add(file);
        }
        // Make sure 1st level subprojects are visible remotely
        // First, remembr all subproject locations
        for (String subprojectDir : conf.getSubProjectLocations()) {
            subprojectDir = IpeUtils.toAbsolutePath(baseDir.getAbsolutePath(), subprojectDir);
            extraSourceRoots.add(new File(subprojectDir));
        }
        // Then go trough open subprojects and add their external source roots
        for (Project subProject : conf.getSubProjects()) {
            File subProjectDir = FileUtil.toFile(subProject.getProjectDirectory());
            MakeConfigurationDescriptor subMcs =
                    MakeConfigurationDescriptor.getMakeConfigurationDescriptor(subProject);
            for(String soorceRoot : mcs.getSourceRoots()) {
                File file = new File(soorceRoot).getAbsoluteFile(); // or canonical?
                extraSourceRoots.add(file);
            }
        }
        List<File> allFiles = new ArrayList<File>(extraSourceRoots.size() + 1);
        allFiles.add(baseDir);
        allFiles.addAll(extraSourceRoots);
        return allFiles.toArray(new File[allFiles.size()]);
    }
}
