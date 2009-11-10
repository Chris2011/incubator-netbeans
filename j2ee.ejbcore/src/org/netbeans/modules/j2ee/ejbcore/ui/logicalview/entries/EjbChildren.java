/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans.modules.j2ee.ejbcore.ui.logicalview.entries;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.j2ee.ejbcore.ui.logicalview.ejb.mdb.MessageNode;
import org.netbeans.modules.j2ee.spi.ejbjar.EjbNodesFactory;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;

/**
 * Provide a set of children representing the ejb nodes.
 * @author ChrisWebster
 */
public class EjbChildren extends Children.Array implements NodeListener{
    private final Node projectNode;
    private final Node ejbsNode;

    /** Creates a new instance of EjbChildren */
    public EjbChildren(Node projectNode) {
        this.projectNode = projectNode;
        this.ejbsNode = projectNode.getChildren().findChild(EjbNodesFactory.CONTAINER_NODE_NAME);
    }

    @Override
    protected void addNotify() {
        super.addNotify();
        if (ejbsNode != null) {
            ejbsNode.addNodeListener(this);
            addChildrens(ejbsNode.getChildren().getNodes(true));
                }
            }

    @Override
    protected void removeNotify() {
        super.removeNotify();
        if (ejbsNode != null){
            ejbsNode.removeNodeListener(this);
        }
    }

    private void addChildrens(Node[] nodesToAdd){
        if (nodesToAdd != null){
            Project project = projectNode.getLookup().lookup(Project.class);
            List<Node> filteredNodes = new ArrayList<Node>();
            for (Node node : nodesToAdd) {
                // #75721: MDB should not appear in Call EJB dialog
                if (node instanceof MessageNode)
                    continue;

                FileObject fo = node.getLookup().lookup(FileObject.class);
                Project foProject = fo == null ? null : FileOwnerQuery.getOwner(fo);
                if((foProject != null) && (project != foProject))
                    continue;

                filteredNodes.add(new FilterNode(node, Children.LEAF) {
                    @Override
                    public Action[] getActions(boolean context) {
                        return new Action[0];
                    }
                });
            }
            Node[] filteredNodesArray = new Node[filteredNodes.size()];
            add(filteredNodes.toArray(filteredNodesArray));
        }
    }

    public void childrenAdded(NodeMemberEvent ev) {
        addChildrens(ev.getDelta());
    }

    public void childrenRemoved(NodeMemberEvent ev) {
        remove(ev.getDelta());
    }

    public void childrenReordered(NodeReorderEvent ev) {
        //nothing to do
    }

    public void nodeDestroyed(NodeEvent ev) {
        remove(new Node[]{ev.getNode()});
    }

    public void propertyChange(PropertyChangeEvent evt) {
        //nothing to do
    }
    
}
