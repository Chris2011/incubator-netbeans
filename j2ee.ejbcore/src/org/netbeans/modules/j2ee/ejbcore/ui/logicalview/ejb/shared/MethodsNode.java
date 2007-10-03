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

package org.netbeans.modules.j2ee.ejbcore.ui.logicalview.ejb.shared;

import java.io.IOException;
import javax.swing.Action;
import org.netbeans.modules.j2ee.api.ejbjar.EjbJar;
import org.netbeans.modules.j2ee.dd.api.ejb.EjbJarMetadata;
import org.netbeans.modules.j2ee.dd.api.ejb.EntityAndSession;
import org.netbeans.modules.j2ee.ejbcore.Utils;
import org.netbeans.modules.j2ee.ejbcore.ui.logicalview.ejb.action.AddActionGroup;
import org.netbeans.modules.j2ee.ejbcore.ui.logicalview.ejb.action.GoToSourceAction;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModel;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModelAction;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Represents Local/Remote Methods node under Session and Entity nodes 
 * in EJB logical view
 *
 * @author Martin Adamek
 */
public class MethodsNode extends AbstractNode implements OpenCookie {
    
    private final String ejbClass;
    private final MetadataModel<EjbJarMetadata> model;
    private final EjbViewController controller;
    private FileObject fileObject;
    private boolean local;

    public MethodsNode(String ejbClass, EjbJar ejbModule, Children children, boolean local) {
        this(new InstanceContent(), ejbClass, ejbModule, children, local);
    }
    
    private MethodsNode(InstanceContent content, final String ejbClass, EjbJar ejbModule, Children children, final boolean local) {
        super(children, new AbstractLookup(content));
        this.ejbClass = ejbClass;
        this.model = ejbModule.getMetadataModel();
        this.controller = new EjbViewController(ejbClass, ejbModule);
        this.local = local;
        try {
            this.fileObject = model.runReadAction(new MetadataModelAction<EjbJarMetadata, FileObject>() {
                public FileObject run(EjbJarMetadata metadata) throws Exception {
                    EntityAndSession entityAndSession = (EntityAndSession) metadata.findByEjbClass(ejbClass);
                    String className = local ? entityAndSession.getLocal() : entityAndSession.getRemote();
                    return metadata.findResource(Utils.toResourceName(className));
                }
            });
        } catch (IOException ioe) {
            Exceptions.printStackTrace(ioe);
        }
        content.add(this);
        if (fileObject != null) {
            try {
                content.add(DataObject.find(fileObject));
            } catch (DataObjectNotFoundException donfe) {
                Exceptions.printStackTrace(donfe);
            }
        }
    }
    
    public Action[] getActions(boolean context) {
        return new Action[] {
            new GoToSourceAction(fileObject, NbBundle.getMessage(MethodsNode.class, "LBL_GoToSourceGroup")),
            SystemAction.get(AddActionGroup.class),
        };
    }

    public Action getPreferredAction() {
        return new GoToSourceAction(fileObject, NbBundle.getMessage(MethodsNode.class, "LBL_GoToSourceGroup"));
    }

    public void open() {
        DataObject dataObject = controller.getBeanDo();
        if (dataObject != null) {
            OpenCookie cookie = dataObject.getCookie(OpenCookie.class);
            if(cookie != null){
                cookie.open();
            }
        }
    }
    
    public boolean isLocal() {
	return local;
    }
}
