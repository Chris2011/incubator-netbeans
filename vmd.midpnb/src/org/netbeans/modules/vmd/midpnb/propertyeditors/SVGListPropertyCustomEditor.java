/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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

/*
 * CustomEditor.java
 *
 * Created on 04.12.2008, 15:20:12
 */

package org.netbeans.modules.vmd.midpnb.propertyeditors;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.netbeans.modules.vmd.api.model.PropertyValue;

/**
 *
 * @author den
 */
public class SVGListPropertyCustomEditor extends javax.swing.JPanel {
    
    private static final String NEW_LINE = "\n";    // NOI18N
    private static final String CARET_RET = "\r";   // NOI18N

    /** Creates new form CustomEditor */
    public SVGListPropertyCustomEditor() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        myLabel = new javax.swing.JLabel();
        myScrollPane = new javax.swing.JScrollPane();
        myTextArea = new javax.swing.JTextArea();

        myLabel.setLabelFor(myTextArea);
        org.openide.awt.Mnemonics.setLocalizedText(myLabel, org.openide.util.NbBundle.getMessage(SVGListPropertyCustomEditor.class, "LBL_DefaultListDescription")); // NOI18N

        myTextArea.setColumns(20);
        myTextArea.setRows(5);
        myScrollPane.setViewportView(myTextArea);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(myLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, myScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(myLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(myScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    void cleanUp(){
        
    }

    void setValue( PropertyValue value ) {
        List<PropertyValue> list = value.getArray();
        if ( list != null ){
            int i=0;
            for (PropertyValue propertyValue : list) {
                String item = propertyValue.getPrimitiveValue().toString();
                i++;
                myTextArea.append( item );
                if ( i != list.size() ){
                    myTextArea.append(NEW_LINE);
                }
            }
        }
    }
    
    List<String> getValue(){
        String text = myTextArea.getText();
        StringTokenizer tokenizer = new StringTokenizer( text , NEW_LINE + CARET_RET);
        List<String> result = new LinkedList<String>();
        while ( tokenizer.hasMoreTokens() ){
            result.add( tokenizer.nextToken() );
        }
        return result;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel myLabel;
    private javax.swing.JScrollPane myScrollPane;
    private javax.swing.JTextArea myTextArea;
    // End of variables declaration//GEN-END:variables

}
