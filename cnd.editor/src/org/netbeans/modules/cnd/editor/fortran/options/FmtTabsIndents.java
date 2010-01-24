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

package org.netbeans.modules.cnd.editor.fortran.options;

import javax.swing.JPanel;
import org.netbeans.modules.options.editor.spi.PreferencesCustomizer;

/**
 *
 * @author Alexander Simon
 */
public class FmtTabsIndents extends JPanel {
    public static final String PROP_FREE_FORMAT_FORTRAN = "freeFormatFortran"; // NOI18N

    /** Creates new form FmtTabsIndents */
    public FmtTabsIndents() {
        initComponents();
        freeFormatCheckBox.putClientProperty(FmtOptions.CategorySupport.OPTION_ID, FmtOptions.freeFormat);
    }

    public static PreferencesCustomizer.Factory getController() {
        return new FmtOptions.CategorySupport.Factory(PreferencesCustomizer.TABS_AND_INDENTS_ID, FmtTabsIndents.class,
                org.openide.util.NbBundle.getMessage(FmtTabsIndents.class, "SAMPLE_Default"), // NOI18N
                new String[] { FmtOptions.rightMargin, "30" }  // NOI18N
                );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        freeFormatCheckBox = new javax.swing.JCheckBox();

        setName(null);
        setOpaque(false);

        org.openide.awt.Mnemonics.setLocalizedText(freeFormatCheckBox, org.openide.util.NbBundle.getMessage(FmtTabsIndents.class, "LBL_Free_Format")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(freeFormatCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(freeFormatCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox freeFormatCheckBox;
    // End of variables declaration//GEN-END:variables
    
}
