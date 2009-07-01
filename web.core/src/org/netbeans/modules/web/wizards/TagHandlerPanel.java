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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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

package org.netbeans.modules.web.wizards;

import java.awt.Dimension;
import org.netbeans.api.j2ee.core.Profile;
import org.openide.util.NbBundle;
import org.netbeans.modules.web.api.webmodule.WebModule;

/** A single panel for a wizard - the GUI portion.
 *
 * @author  mk115033
 */
public class TagHandlerPanel extends javax.swing.JPanel {

    /** The wizard panel descriptor associated with this GUI panel.
     * If you need to fire state changes or something similar, you can
     * use this handle to do so.
     */
    private TagHandlerSelection wizardPanel;
    private Profile j2eeVersion;
    /** Create the wizard panel and set up some basic properties. */
    public TagHandlerPanel(TagHandlerSelection wizardPanel, Profile j2eeVersion) {
        this.wizardPanel=wizardPanel;
        this.j2eeVersion=j2eeVersion;
        initComponents();
        // Provide a name in the title bar.
        setName(NbBundle.getMessage(TagHandlerPanel.class, "TITLE_tagHandlerPanel"));
        if (Profile.J2EE_13.equals(j2eeVersion)) {
            simpleTagButton.setEnabled(false);
            bodyTagButton.setSelected(true);
        } else {
            simpleTagButton.setSelected(true);
        }
        /*
        // Optional: provide a special description for this pane.
        // You must have turned on WizardDescriptor.WizardPanel_helpDisplayed
        // (see descriptor in standard iterator template for an example of this).
        try {
            putClientProperty (WizardDescriptor.PROP_HELP_URL, // NOI18N
                new URL ("nbresloc:/org/netbeans/modules/web/wizards/TagHandlerHelp.html")); // NOI18N
        } catch (MalformedURLException mfue) {
            throw new IllegalStateException (mfue.toString ());
        }
         */
        // a11y part
        
        //issue #84131
        int height = jLabel1.getFontMetrics(jLabel1.getFont()).getHeight() * 6 + 35;
        if (height > this.getHeight()) {
            Dimension dim = new Dimension(this.getWidth(), height);
            setMinimumSize(dim);
            setPreferredSize(dim);
        }

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        simpleTagButton = new javax.swing.JRadioButton();
        bodyTagButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();

        setRequestFocusEnabled(false);
        setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(simpleTagButton);
        simpleTagButton.setMnemonic(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "LBL_SimpleTag_Mnemonic").charAt(0));
        simpleTagButton.setText(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "OPT_SimpleTag")); // NOI18N
        simpleTagButton.setMargin(new java.awt.Insets(2, 2, 0, 2));
        simpleTagButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TagHandlerPanel.this.itemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(simpleTagButton, gridBagConstraints);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/web/wizards/Bundle"); // NOI18N
        simpleTagButton.getAccessibleContext().setAccessibleDescription(bundle.getString("DESC_SimpleTag")); // NOI18N

        buttonGroup1.add(bodyTagButton);
        bodyTagButton.setMnemonic(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "LBL_BodyTag_Mnemonic").charAt(0));
        bodyTagButton.setText(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "OPT_BodyTag")); // NOI18N
        bodyTagButton.setMargin(new java.awt.Insets(0, 2, 2, 2));
        bodyTagButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TagHandlerPanel.this.itemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(bodyTagButton, gridBagConstraints);
        bodyTagButton.getAccessibleContext().setAccessibleDescription(bundle.getString("DESC_BodyTag")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "LBL_TagSupportClass")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        add(jLabel1, gridBagConstraints);

        jLabel2.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("org/netbeans/modules/web/wizards/Bundle").getString("A11Y_Description_mnem").charAt(0));
        jLabel2.setLabelFor(descriptionArea);
        jLabel2.setText(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "LBL_description")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(jLabel2, gridBagConstraints);

        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setText(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "DESC_SimpleTag")); // NOI18N
        descriptionArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(descriptionArea);
        descriptionArea.getAccessibleContext().setAccessibleDescription(bundle.getString("A11Y_DESC_Description")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 6);
        add(jScrollPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    private void itemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_itemStateChanged
        if (simpleTagButton.isSelected())
            descriptionArea.setText(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "DESC_SimpleTag"));
        else
            descriptionArea.setText(org.openide.util.NbBundle.getMessage(TagHandlerPanel.class, "DESC_BodyTag"));
        wizardPanel.fireChangeEvent();
    }//GEN-LAST:event_itemStateChanged
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton bodyTagButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton simpleTagButton;
    // End of variables declaration//GEN-END:variables
    
    boolean isBodyTagSupport() {
        return bodyTagButton.isSelected();
    }

}
