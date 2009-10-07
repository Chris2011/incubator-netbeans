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

package org.netbeans.modules.xml.multiview.ui;

import javax.swing.UIManager;
import org.netbeans.modules.xml.multiview.Error;
//import org.netbeans.modules.xml.multiview.cookies.ErrorComponentContainer;

/** 
 * A panel for error messages.
 *
 * Created on November 19, 2004, 10:44 AM
 * @author  mkuchtiak
 */
public class ErrorPanel extends javax.swing.JPanel {

    private Error error;
    private ErrorLabel errorLabel;
    private String errorMessage;


    /** Creates new form ErrorPanel */
    public ErrorPanel(final ToolBarDesignEditor editor) {
        initComponents();
        
        errorLabel = new ErrorLabel();
        errorLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Error error = getError();
                if (error!=null) {
                    Error.ErrorLocation errorLocation = error.getErrorLocation();
                    if (errorLocation!=null) {
                        SectionPanel sectPanel = ((SectionView)editor.getContentView()).findSectionPanel(errorLocation.getKey());
                        if (sectPanel.getInnerPanel()==null) sectPanel.open();
                        sectPanel.scroll();
                        javax.swing.JComponent errorComp = sectPanel.getErrorComponent(errorLocation.getComponentId());
                        if (errorComp!=null) errorComp.requestFocus();
                    }
                }
            }
        });
        add(errorLabel,java.awt.BorderLayout.CENTER);
        
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    /*
    public ErrorComponentContainer getErrorComponentContainer() {
        return errorContainer;
    }
    */
    public Error getError() {
        return error;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
 
    
    public void setError(Error error) {
        switch (error.getErrorType()) {
            case Error.ERROR_MESSAGE : {
                errorMessage="Error: "+error.getErrorMessage();
                break;
            }
            case Error.WARNING_MESSAGE : {
                errorMessage="Warning: "+error.getErrorMessage();
                break;
            }
            case Error.MISSING_VALUE_MESSAGE : {
                errorMessage="Missing Value: "+error.getErrorMessage();
                break;
            }            
            case Error.DUPLICATE_VALUE_MESSAGE : {
                errorMessage="Duplicate Value: "+error.getErrorMessage();
                break;
            }
        }
        this.error=error;
        errorLabel.setText(errorMessage);
        errorLabel.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/org/netbeans/modules/xml/multiview/resources/error-glyph.gif"))); //NOI18N
    }
    
    public void clearError() {
        error=null;
        errorLabel.setIcon(null);
        errorLabel.setText("");
        errorMessage="";
    }
    
    private class ErrorLabel extends javax.swing.JLabel {
        ErrorLabel() {
            super();
            //setForeground(SectionVisualTheme.hyperlinkColor);
            setForeground(UIManager.getDefaults().getColor("ToolBar.dockingForeground")); //NOI18N
            setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            setText(""); //NOI18N
        }

        public void setText(String text) {
            if (text.length()==0) super.setText(" "); //NOI18N
            else super.setText("<html><u>"+text+"</u></html>"); //NOI18N
        }
    }
    

}
