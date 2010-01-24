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

package org.netbeans.modules.php.editor.indent;

import java.io.IOException;
import org.netbeans.modules.options.editor.spi.PreferencesCustomizer;
import org.netbeans.modules.php.editor.indent.FmtOptions.CategorySupport;
import org.netbeans.modules.php.editor.indent.ui.Utils;
import static  org.netbeans.modules.php.editor.indent.FmtOptions.CategorySupport.OPTION_ID;

/**
 *
 * @author  phrebejk
 */
public class FmtTabsIndents extends javax.swing.JPanel {
   
    /** Creates new form FmtTabsIndents */
    public FmtTabsIndents() {
        initComponents();
        
//        expandTabCheckBox.putClientProperty(OPTION_ID, expandTabToSpaces);
//        tabSizeField.putClientProperty(OPTION_ID, tabSize);
//        indentSizeField.putClientProperty(OPTION_ID, new String [] { indentSize, spacesPerTab });
//        rightMarginField.putClientProperty(OPTION_ID, rightMargin);
        continuationIndentSizeField.putClientProperty(OPTION_ID, FmtOptions.continuationIndentSize);
        initialIndentSizeField.putClientProperty(OPTION_ID, FmtOptions.initialIndent);
    }
    
    public static PreferencesCustomizer.Factory getController() {
        String preview = "";
        try {
            preview = Utils.loadPreviewText(FmtTabsIndents.class.getClassLoader().getResourceAsStream("org/netbeans/modules/php/editor/indent/ui/TabsIndents.php"));
        } catch (IOException ex) {
            // TODO log it
        }
        return new CategorySupport.Factory(PreferencesCustomizer.TABS_AND_INDENTS_ID, FmtTabsIndents.class, //NOI18N
                preview, // NOI18N
                new String[] { FmtOptions.rightMargin, "30" }, //NOI18N
                new String[] { FmtOptions.initialIndent, "0" } //NOI18N
                );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        continuationIndentSizeLabel = new javax.swing.JLabel();
        continuationIndentSizeField = new javax.swing.JTextField();
        initialIndentLabel = new javax.swing.JLabel();
        initialIndentSizeField = new javax.swing.JTextField();

        setName(org.openide.util.NbBundle.getMessage(FmtTabsIndents.class, "LBL_TabsAndIndents")); // NOI18N
        setOpaque(false);

        continuationIndentSizeLabel.setLabelFor(continuationIndentSizeField);
        org.openide.awt.Mnemonics.setLocalizedText(continuationIndentSizeLabel, org.openide.util.NbBundle.getMessage(FmtTabsIndents.class, "LBL_ContinuationIndentSize")); // NOI18N

        initialIndentLabel.setLabelFor(initialIndentSizeField);
        org.openide.awt.Mnemonics.setLocalizedText(initialIndentLabel, org.openide.util.NbBundle.getMessage(FmtTabsIndents.class, "FmtTabsIndents.initialIndentLabel.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(continuationIndentSizeLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(continuationIndentSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(layout.createSequentialGroup()
                .add(initialIndentLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 116, Short.MAX_VALUE)
                .add(initialIndentSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(12, 12, 12)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(initialIndentLabel)
                    .add(initialIndentSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(continuationIndentSizeLabel)
                    .add(continuationIndentSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        continuationIndentSizeField.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtTabsIndents.class, "FmtTabsIndents.continuationIndentSizeField.AccessibleContext.accessibleName")); // NOI18N
        continuationIndentSizeField.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtTabsIndents.class, "FmtTabsIndents.continuationIndentSizeField.AccessibleContext.accessibleDescription")); // NOI18N
        initialIndentSizeField.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtTabsIndents.class, "FmtTabsIndents.initialIndentSizeField.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField continuationIndentSizeField;
    private javax.swing.JLabel continuationIndentSizeLabel;
    private javax.swing.JLabel initialIndentLabel;
    private javax.swing.JTextField initialIndentSizeField;
    // End of variables declaration//GEN-END:variables
    
}
