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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */

package org.netbeans.modules.db.explorer.dlg;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.db.explorer.JDBCDriver;
import org.netbeans.api.db.explorer.JDBCDriverManager;
import org.netbeans.modules.db.util.DatabaseExplorerInternalUIs;
import org.netbeans.modules.db.util.JdbcUrl;
import org.openide.util.NbBundle;

/**
 *
 * @author Jiri Rechtacek
 */
public class ChoosingDriverUI extends javax.swing.JPanel {
    private final ChoosingDriverInterUI interPanel;
    private JDBCDriver drv;
    private ActionListener actionListener;

    /** Creates new form ChoosingDriverUI */
    public ChoosingDriverUI(ChoosingDriverPanel panel, String driverFileName, String driverPath, String downloadFrom, boolean found, JDBCDriver driver) {
        this.drv = driver;
        initComponents();
        interPanel = new ChoosingDriverInterUI(panel, driverFileName, driverPath, downloadFrom, found);
        pInter.add(interPanel, BorderLayout.CENTER);
        DatabaseExplorerInternalUIs.connect(cbDrivers, JDBCDriverManager.getDefault());
        if (drv == null) {
            cbDrivers.setSelectedIndex(0);
        } else {
            for (int i = 0; i < cbDrivers.getItemCount(); i++) {
                Object item = cbDrivers.getItemAt(i);
                if (item instanceof JdbcUrl) {
                    JdbcUrl url = ((JdbcUrl)item);
                    assert url.getDriver() != null;
                    if (url.getClassName().equals(drv.getClassName()) && url.getDriver().getName().equals(drv.getName())) {
                        cbDrivers.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateState();
            }
        };
    }

    @Override
    public void addNotify() {
        super.addNotify();
        cbDrivers.addActionListener(actionListener);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        cbDrivers.removeActionListener(actionListener);
    }

    private void updateState() {
        Object drvO = cbDrivers.getSelectedItem();
        if (drvO instanceof JdbcUrl) {
            System.out.println("###: GOT JdbcUrl");
            JDBCDriver curr = ((JdbcUrl) drvO).getDriver();
            System.out.println("###: DRV: " + curr);
            interPanel.updateDriver(curr);
        } else {
            System.out.println("###: GOT NEW DRIVER...");
        }
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ChoosingDriverInterUI.class, "ChoosingDriverUI.Name"); // NOI18N
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lDrivers = new javax.swing.JLabel();
        cbDrivers = new javax.swing.JComboBox();
        pInter = new javax.swing.JPanel();

        org.openide.awt.Mnemonics.setLocalizedText(lDrivers, org.openide.util.NbBundle.getMessage(ChoosingDriverUI.class, "ChoosingDriverUI.lDrivers.text")); // NOI18N

        pInter.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lDrivers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDrivers, 0, 322, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pInter, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lDrivers)
                    .addComponent(cbDrivers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pInter, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbDrivers;
    private javax.swing.JLabel lDrivers;
    private javax.swing.JPanel pInter;
    // End of variables declaration//GEN-END:variables

    boolean driverFound() {
        return interPanel.driverFound();
    }

    String getDriverLocation() {
        return interPanel.getDriverLocation();
    }
}
