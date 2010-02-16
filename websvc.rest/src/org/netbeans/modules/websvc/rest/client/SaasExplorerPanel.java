/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans.modules.websvc.rest.client;

import javax.swing.JPanel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.border.EtchedBorder;
import org.netbeans.modules.websvc.saas.model.WadlSaasResource;
import org.netbeans.modules.websvc.saas.spi.SaasViewProvider;
import org.openide.DialogDescriptor;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author Milan Kuchtiak
 * Panel for displaying web services in projects
 * TODO: Needs to be moved to a common place since this serves both JAXWS and JAXRPC
 */
public class SaasExplorerPanel extends JPanel implements ExplorerManager.Provider, PropertyChangeListener {
    
    private DialogDescriptor descriptor;
    private ExplorerManager manager;
    private BeanTreeView treeView;
    private Node selectedResourceNode;
    
    public SaasExplorerPanel() {
        manager = new ExplorerManager();
        selectedResourceNode = null;
        
        initComponents();
        initUserComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLblTreeView = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLblTreeView, NbBundle.getMessage(SaasExplorerPanel.class, "LBL_SelectRESTResource")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 0, 11);
        add(jLblTreeView, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLblTreeView;
    // End of variables declaration//GEN-END:variables
    
    private void initUserComponents() {
        treeView = new BeanTreeView();
        treeView.setRootVisible(false);
        treeView.setPopupAllowed(false);
        treeView.setBorder(new EtchedBorder());
        
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 0, 11);
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(treeView, gridBagConstraints);
        jLblTreeView.setLabelFor(treeView.getViewport().getView());
        treeView.getAccessibleContext().setAccessibleName(NbBundle.getMessage(SaasExplorerPanel.class, "ACSD_RESTResourcesTreeView"));
        treeView.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(SaasExplorerPanel.class, "ACSD_RESTResourcesTreeView"));
    }
    
    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        manager.addPropertyChangeListener(this);
        SaasViewProvider saasView = Lookup.getDefault().lookup(SaasViewProvider.class);
        if (saasView != null) {
            Node saasRootNode = saasView.getSaasView();
            manager.setRootContext(saasRootNode);
        }
        // !PW If we preselect a node, this can go away.
        descriptor.setValid(false);
    }
    
    @Override
    public void removeNotify() {
        manager.removePropertyChangeListener(this);
        super.removeNotify();
    }
    
    public void setDescriptor(DialogDescriptor descriptor) {
        this.descriptor = descriptor;
    }
    
    public Node getSelectedService() {
        return selectedResourceNode;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() == manager) {
            if(ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
                Node nodes[] = manager.getSelectedNodes();
                if(nodes != null && nodes.length > 0 ) {
                    Node node = nodes[0];
                    WadlSaasResource saasResource = node.getLookup().lookup(WadlSaasResource.class);
                    if(saasResource != null) {
                        // This is a resource node.
                        if (saasResource.getMethods().size() > 0) {
                        selectedResourceNode = node;
                        descriptor.setValid(true);
                        } else {
                            // Contrains no methods.
                            selectedResourceNode = null;
                            descriptor.setValid(false);
                        }
                    } else {
                        // This is not a service node.
                        selectedResourceNode = null;
                        descriptor.setValid(false);
                    }
                }
            }
        }
    }
}
