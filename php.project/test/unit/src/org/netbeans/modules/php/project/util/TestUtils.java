/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.netbeans.modules.php.project.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.junit.MockServices;
import org.netbeans.modules.php.project.api.PhpLanguageOptions.PhpVersion;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;

/**
 * @author Tomas Mysik
 */
public final class TestUtils {

    private TestUtils() {
    }

    static {
        MockServices.setServices(MockInstalledFileLocator.class);
    }

    public static void init() {
        // noop
    }

    public static Project createPhpProject(File workDir) throws IOException {
        String projectName = "phpProject" + new Random().nextInt();
        File projectDir = new File(workDir, projectName);
        File srcDir = projectDir;

        final PhpProjectGenerator.ProjectProperties properties = new PhpProjectGenerator.ProjectProperties()
                .setProjectDirectory(projectDir)
                .setSourcesDirectory(srcDir)
                .setName(projectName)
                .setUrl("http://localhost/" + projectName)
                .setCharset(Charset.defaultCharset())
                .setPhpVersion(PhpVersion.PHP_53);

        AntProjectHelper antProjectHelper = PhpProjectGenerator.createProject(properties, null);

        final Project project = ProjectManager.getDefault().findProject(antProjectHelper.getProjectDirectory());
        ProjectManager.getDefault().saveProject(project);
        return project;
    }

    //~ Inner classes

    public static final class MockInstalledFileLocator extends InstalledFileLocator {

        @Override
        public File locate(String name, String codeNameBase, boolean localized) {
            if (name.equals("phpunit/NetBeansSuite.php")) {
                try {
                    // create dummy file and return it
                    File tmpFile = File.createTempFile("nb-test-file", null);
                    tmpFile.deleteOnExit();
                    return tmpFile;
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            return null;
        }
    }
}
