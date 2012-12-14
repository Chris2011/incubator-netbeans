/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.php.zend2.util;

import java.io.File;
import org.netbeans.junit.NbTestCase;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public class Zend2UtilsTest extends NbTestCase {

    private FileObject zf2project = null;


    public Zend2UtilsTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        zf2project = FileUtil.toFileObject(new File(getDataDir(), "zf2project"));
        assertNotNull(zf2project);
    }

    public void testIsView() {
        FileObject deleteView = zf2project.getFileObject("module/Album/view/album/album/delete.phtml");
        assertNotNull(deleteView);
        assertTrue(Zend2Utils.isView(FileUtil.toFile(deleteView)));
        FileObject helloWorldView = zf2project.getFileObject("module/Album/view/album/my-test/hello-world.phtml");
        assertNotNull(helloWorldView);
        assertTrue(Zend2Utils.isView(FileUtil.toFile(helloWorldView)));
    }

    public void testIsNotView() {
        File project = FileUtil.toFile(zf2project);
        assertNotNull(project);

        assertFalse(Zend2Utils.isView(null));

        File notView = new File(project, "module/Album/view/album/my-test/non-existing-file.phtml");
        assertFalse(notView.exists());
        assertFalse(Zend2Utils.isView(notView));

        notView = new File(project, "module/Album/view/album/my-test/not-view.html");
        assertTrue(notView.exists());
        assertFalse(Zend2Utils.isView(notView));

        notView = new File(project, "module/Album/view/album/my-test/not-view.php");
        assertTrue(notView.exists());
        assertFalse(Zend2Utils.isView(notView));

        notView = new File(project, "module/Album/view/album/my-test/dummy/not-view.phtml");
        assertTrue(notView.exists());
        assertFalse(Zend2Utils.isView(notView));

        notView = new File(project, "module/Album/view/album/not-view.phtml");
        assertTrue(notView.exists());
        assertFalse(Zend2Utils.isView(notView));

        notView = new File(project, "module/Album/view/not-view.phtml");
        assertTrue(notView.exists());
        assertFalse(Zend2Utils.isView(notView));
    }

    public void testdashize() {
        assertEquals("test", Zend2Utils.dashize("Test"));
        assertEquals("test", Zend2Utils.dashize("test"));

        assertEquals("all-jobs", Zend2Utils.dashize("AllJobs"));
        assertEquals("all-jobs", Zend2Utils.dashize("allJobs"));

        assertEquals("all-my-jobs", Zend2Utils.dashize("AllMyJobs"));
        assertEquals("all-my-jobs", Zend2Utils.dashize("allMyJobs"));
    }

    public void testUndashize() {
        assertEquals("Test", Zend2Utils.undashize("test", false));
        assertEquals("test", Zend2Utils.undashize("test", true));

        assertEquals("AllJobs", Zend2Utils.undashize("all-jobs", false));
        assertEquals("allJobs", Zend2Utils.undashize("all-jobs", true));

        assertEquals("AllMyJobs", Zend2Utils.undashize("all-my-jobs", false));
        assertEquals("allMyJobs", Zend2Utils.undashize("all-my-jobs", true));
    }

}
