/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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

package org.netbeans.modules.groovy.gsp.loaders;

import java.io.IOException;
import org.netbeans.modules.groovy.gsp.lexer.GspTokenId;
import static org.netbeans.modules.groovy.gsp.loaders.GspDataLoader.ACTIONS;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject.Registration;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

@Registration(position = 99999, displayName = "org.netbeans.modules.groovy.gsp.resources.Bundle#Loaders/text/x-gsp/Factories/org-netbeans-modules-groovy-gsp-loaders-GspDataLoader.instance", mimeType = GspTokenId.MIME_TYPE,iconBase="org/netbeans/modules/groovy/gsp/resources/GspFile16x16.png")

@ActionReferences(value = {
    @ActionReference(id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"), path = ACTIONS, position = 100),
    @ActionReference(id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"), path = ACTIONS, position = 200, separatorAfter = 300),
    @ActionReference(id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"), path = ACTIONS, position = 400),
    @ActionReference(id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"), path = ACTIONS, position = 500, separatorAfter = 600),
    @ActionReference(id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"), path = ACTIONS, position = 700),
    @ActionReference(id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"), path = ACTIONS, position = 800, separatorAfter = 900),
    @ActionReference(id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"), path = ACTIONS, position = 1000, separatorAfter = 1100),
    @ActionReference(id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"), path = ACTIONS, position = 1200),
    @ActionReference(id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"), path = ACTIONS, position = 1300)
})

public class GspDataLoader extends UniFileLoader {

    private static final long serialVersionUID = 1L;
    protected static final String ACTIONS = "Loaders/" + GspTokenId.MIME_TYPE + "/Actions";

    public GspDataLoader() {
        super("org.netbeans.modules.groovy.gsp.loaders.GspDataObject");
    }

    @Override
    protected String defaultDisplayName() {
        return NbBundle.getMessage(GspDataLoader.class, "LBL_Gsp_loader_name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(GspTokenId.MIME_TYPE);
    }

    @Override
    protected MultiDataObject createMultiObject(FileObject primaryFile) throws IOException {
        return new GspDataObject(primaryFile, this);
    }

    @Override
    protected String actionsContext() {
        return ACTIONS;
    }
}
