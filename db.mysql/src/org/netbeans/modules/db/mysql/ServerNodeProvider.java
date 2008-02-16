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

package org.netbeans.modules.db.mysql;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.db.api.explorer.NodeProvider;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;

/**
 * Provides a node for working with a local MySQL Server instance
 * 
 * @author David Van Couvering
 */
public class ServerNodeProvider implements NodeProvider {

    private static ServerNodeProvider DEFAULT = new ServerNodeProvider();
    private static MySQLOptions options = MySQLOptions.getDefault();
    ArrayList<Node> nodes = new ArrayList<Node>();
    private static final ArrayList<Node> emptyNodeList = new ArrayList<Node>();
    
    static ServerNodeProvider getDefault() {
        return DEFAULT;
    }
    
    private ServerNodeProvider() {
        // Right now only one server, although this may (likely) change
        nodes.add(ServerNode.create(ServerInstance.getDefault()));
    }

    public List<Node> getNodes() {
        if ( options.getProviderRegistered() ) {
            return nodes;
        } else {
            return emptyNodeList;
        }
    } 
    
    static class ServerNodeListener implements NodeListener {

        public void childrenAdded(NodeMemberEvent ev) {
        }

        public void childrenRemoved(NodeMemberEvent ev) {
        }

        public void childrenReordered(NodeReorderEvent ev) {
        }

        public void nodeDestroyed(NodeEvent ev) {
            options.setProviderRemoved(true);
        }

        public void propertyChange(PropertyChangeEvent arg0) {
        }
        
    }

    public void register() {
        if ( options.getProviderRegistered() ) {
            return;
        }
        
        options.setProviderRegistered(true);
    }
}
