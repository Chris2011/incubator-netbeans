/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */

package org.netbeans.modules.form.layoutdesign;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.openide.filesystems.FileUtil;

public class ALT_Duplicating_Bug145134Test extends LayoutTestCase {

    public ALT_Duplicating_Bug145134Test(String name) {
        super(name);
        try {
	    className = this.getClass().getName();
	    className = className.substring(className.lastIndexOf('.') + 1, className.length());
            startingFormFile = FileUtil.toFileObject(new File(url.getFile() + goldenFilesPath + className + "-StartingForm.form").getCanonicalFile());
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

    /**
     * Select jButton1 and jButton2 and duplicate them.
     */
    public void doChanges0() {
        ld.externalSizeChangeHappened();
// > UPDATE CURRENT STATE
        contInterior.put("Form", new Rectangle(0, 0, 386, 286));
        compBounds.put("jButton1", new Rectangle(10, 11, 73, 23));
        baselinePosition.put("jButton1-73-23", new Integer(15));
        compPrefSize.put("jButton1", new Dimension(73, 23));
        compBounds.put("jLabel1", new Rectangle(87, 15, 34, 14));
        baselinePosition.put("jLabel1-34-14", new Integer(11));
        compPrefSize.put("jLabel1", new Dimension(34, 14));
        compBounds.put("jButton2", new Rectangle(10, 45, 73, 23));
        baselinePosition.put("jButton2-73-23", new Integer(15));
        compPrefSize.put("jButton2", new Dimension(73, 23));
        contInterior.put("Form", new Rectangle(0, 0, 386, 286));
        compBounds.put("jButton1", new Rectangle(10, 11, 73, 23));
        baselinePosition.put("jButton1-73-23", new Integer(15));
        compBounds.put("jLabel1", new Rectangle(87, 15, 34, 14));
        baselinePosition.put("jLabel1-34-14", new Integer(11));
        compBounds.put("jButton2", new Rectangle(10, 45, 73, 23));
        baselinePosition.put("jButton2-73-23", new Integer(15));
        ld.updateCurrentState();
// < UPDATE CURRENT STATE
// > DUPLICATE
        {
            String[] sourceIds = new String[]{"jButton1", "jButton2"};
            String[] targetIds = new String[]{"jButton3", "jButton4"};
            int dimension = -1;
            int direction = -1;
            ld.duplicateLayout(sourceIds, targetIds, dimension, direction);
        }
// < DUPLICATE
        ld.externalSizeChangeHappened();
// > UPDATE CURRENT STATE
        contInterior.put("Form", new Rectangle(0, 0, 386, 286));
        compBounds.put("jButton1", new Rectangle(10, 11, 73, 23));
        baselinePosition.put("jButton1-73-23", new Integer(15));
        compPrefSize.put("jButton1", new Dimension(73, 23));
        compBounds.put("jLabel1", new Rectangle(87, 15, 34, 14));
        baselinePosition.put("jLabel1-34-14", new Integer(11));
        compPrefSize.put("jLabel1", new Dimension(34, 14));
        compBounds.put("jButton2", new Rectangle(10, 45, 73, 23));
        baselinePosition.put("jButton2-73-23", new Integer(15));
        compPrefSize.put("jButton2", new Dimension(73, 23));
        compBounds.put("jButton3", new Rectangle(10, 74, 73, 23));
        baselinePosition.put("jButton3-73-23", new Integer(15));
        compPrefSize.put("jButton3", new Dimension(73, 23));
        compBounds.put("jButton4", new Rectangle(10, 108, 73, 23));
        baselinePosition.put("jButton4-73-23", new Integer(15));
        compPrefSize.put("jButton4", new Dimension(73, 23));
        prefPaddingInParent.put("Form-jButton4-1-1", new Integer(11));
        // parentId-compId-dimension-compAlignment
        contInterior.put("Form", new Rectangle(0, 0, 386, 286));
        compBounds.put("jButton1", new Rectangle(10, 11, 73, 23));
        baselinePosition.put("jButton1-73-23", new Integer(15));
        compBounds.put("jLabel1", new Rectangle(87, 15, 34, 14));
        baselinePosition.put("jLabel1-34-14", new Integer(11));
        compBounds.put("jButton2", new Rectangle(10, 45, 73, 23));
        baselinePosition.put("jButton2-73-23", new Integer(15));
        compBounds.put("jButton3", new Rectangle(10, 74, 73, 23));
        baselinePosition.put("jButton3-73-23", new Integer(15));
        compBounds.put("jButton4", new Rectangle(10, 108, 73, 23));
        baselinePosition.put("jButton4-73-23", new Integer(15));
        ld.updateCurrentState();
    }

}
