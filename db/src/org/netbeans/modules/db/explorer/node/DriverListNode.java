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

package org.netbeans.modules.db.explorer.node;

import org.netbeans.api.db.explorer.node.BaseNode;
import org.netbeans.api.db.explorer.node.ChildNodeFactory;
import java.awt.Image;
import java.beans.BeanInfo;
import javax.swing.Icon;
import javax.swing.UIManager;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Rob Englander
 */
public class DriverListNode extends BaseNode {
    private static final String NAME = "Drivers";
    private static final String DISPLAYNAME = "Drivers";
    private static final String ICONBASE = null;
    private static final String FOLDER = "DriverList"; //NOI18N

    public DriverListNode(NodeDataLookup lookup) {
        super(new ChildNodeFactory(lookup), lookup, FOLDER);
    }
        
    protected void initialize() {
    }
    
    @Override
    public Image getIcon(int type) {
        Image result = null;
        if (type == BeanInfo.ICON_COLOR_16x16) {
            result = icon2Image("Nb.Explorer.Folder.icon"); // NOI18N
        }
        if (result == null) {
            result = icon2Image("Tree.closedIcon"); // NOI18N
        }
        if (result == null) {
            result = super.getIcon(type);
        }
        return result;
    }

    @Override
    public Image getOpenedIcon(int type) {
        Image result = null;
        if (type == BeanInfo.ICON_COLOR_16x16) {
            result = icon2Image("Nb.Explorer.Folder.openedIcon"); // NOI18N
        }
        if (result == null) {
            result = icon2Image("Tree.openIcon"); // NOI18N
        }
        if (result == null) {
            result = super.getOpenedIcon(type);
        }
        return result;
    }

    private static Image icon2Image(String key) {
        Object obj = UIManager.get(key);
        if (obj instanceof Image) {
            return (Image)obj;
        }

        if (obj instanceof Icon) {
            Icon icon = (Icon)obj;
            return ImageUtilities.icon2Image(icon);
        }

        return null;
    }  

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDisplayName() {
        return DISPLAYNAME;
    }

    @Override
    public String getIconBase() {
        return ICONBASE;
    }
}
