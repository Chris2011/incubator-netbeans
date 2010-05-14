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

package org.netbeans.modules.php.editor.indent.ui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import static org.netbeans.modules.php.editor.indent.FmtOptions.*;
import static org.netbeans.modules.php.editor.indent.FmtOptions.CategorySupport.OPTION_ID;
import org.netbeans.modules.options.editor.spi.PreferencesCustomizer;


/**
 *
 * @author  Petr Pisl
 */
public class FmtWrapping extends javax.swing.JPanel implements FocusListener {
    
    /** Creates new form FmtWrapping */
    public FmtWrapping() {
        initComponents();
        
        scrollPane.getViewport().setBackground(java.awt.SystemColor.controlLtHighlight);
        
        extendsImplementsKeywordCombo.putClientProperty(OPTION_ID, wrapExtendsImplementsKeyword);
        extendsImplementsKeywordCombo.addFocusListener(this);
        extendsImplementsListCombo.putClientProperty(OPTION_ID, wrapExtendsImplementsList);
        extendsImplementsListCombo.addFocusListener(this);
        methodParamsCombo.putClientProperty(OPTION_ID, wrapMethodParams);
        methodParamsCombo.addFocusListener(this);
        methodCallArgsCombo.putClientProperty(OPTION_ID, wrapMethodCallArgs);
        methodCallArgsCombo.addFocusListener(this);
        chainedMethodCallsCombo.putClientProperty(OPTION_ID, wrapChainedMethodCalls);
        chainedMethodCallsCombo.addFocusListener(this);
        arrayInitCombo.putClientProperty(OPTION_ID, wrapArrayInit);
        arrayInitCombo.addFocusListener(this);
        forCombo.putClientProperty(OPTION_ID, wrapFor);
        forCombo.addFocusListener(this);
        forStatementCombo.putClientProperty(OPTION_ID, wrapForStatement );
        forStatementCombo.addFocusListener(this);
        ifStatementCombo.putClientProperty(OPTION_ID, wrapIfStatement);
        ifStatementCombo.addFocusListener(this);
        whileStatementComboBox.putClientProperty(OPTION_ID, wrapWhileStatement);
        whileStatementComboBox.addFocusListener(this);
        doWhileStatementCombo.putClientProperty(OPTION_ID, wrapDoWhileStatement);
        doWhileStatementCombo.addFocusListener(this);
        binaryOpsCombo.putClientProperty(OPTION_ID, wrapBinaryOps);
        binaryOpsCombo.addFocusListener(this);
        ternaryOpsCombo.putClientProperty(OPTION_ID, wrapTernaryOps);
        ternaryOpsCombo.addFocusListener(this);
        assignOpsCombo.putClientProperty(OPTION_ID, wrapAssignOps);
        assignOpsCombo.addFocusListener(this);
        cbOpenCloseBlockBrace.putClientProperty(OPTION_ID, wrapBlockBraces);
        cbStatements.putClientProperty(OPTION_ID, wrapStatementsOnTheLine);
    }
    
    public static PreferencesCustomizer.Factory getController() {
        String preview = "";
        try {
            preview = Utils.loadPreviewText(FmtWrapping.class.getClassLoader().getResourceAsStream("org/netbeans/modules/php/editor/indent/ui/Spaces.php"));
        } catch (IOException ex) {
            // TODO log it
        }
        return new CategorySupport.Factory("wrapping", FmtWrapping.class, //NOI18N
                preview);
    }

    public void focusGained(FocusEvent e) {
        scrollPane.getViewport().scrollRectToVisible(e.getComponent().getBounds());
    }

    public void focusLost(FocusEvent e) {
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        panel1 = new javax.swing.JPanel();
        extendsImplemetsKeywordLabel = new javax.swing.JLabel();
        extendsImplementsKeywordCombo = new javax.swing.JComboBox();
        extendsImplementsListLabel = new javax.swing.JLabel();
        extendsImplementsListCombo = new javax.swing.JComboBox();
        methodParamsLabel = new javax.swing.JLabel();
        methodParamsCombo = new javax.swing.JComboBox();
        methodCallArgsLabel = new javax.swing.JLabel();
        methodCallArgsCombo = new javax.swing.JComboBox();
        chainedMethodCallsLabel = new javax.swing.JLabel();
        chainedMethodCallsCombo = new javax.swing.JComboBox();
        arrayInitLabel = new javax.swing.JLabel();
        arrayInitCombo = new javax.swing.JComboBox();
        forLabel = new javax.swing.JLabel();
        forCombo = new javax.swing.JComboBox();
        forStatementLabel = new javax.swing.JLabel();
        forStatementCombo = new javax.swing.JComboBox();
        ifStatementLabel = new javax.swing.JLabel();
        ifStatementCombo = new javax.swing.JComboBox();
        whileStatementLabel = new javax.swing.JLabel();
        whileStatementComboBox = new javax.swing.JComboBox();
        doWhileStatementLabel = new javax.swing.JLabel();
        doWhileStatementCombo = new javax.swing.JComboBox();
        binaryOpsLabel = new javax.swing.JLabel();
        binaryOpsCombo = new javax.swing.JComboBox();
        ternaryOpsLabel = new javax.swing.JLabel();
        ternaryOpsCombo = new javax.swing.JComboBox();
        assignOpsLabel = new javax.swing.JLabel();
        assignOpsCombo = new javax.swing.JComboBox();
        cbOpenCloseBlockBrace = new javax.swing.JCheckBox();
        cbStatements = new javax.swing.JCheckBox();

        setFocusTraversalPolicy(new java.awt.FocusTraversalPolicy() {
            public java.awt.Component getDefaultComponent(java.awt.Container focusCycleRoot){
                return cbStatements;
            }//end getDefaultComponent

            public java.awt.Component getFirstComponent(java.awt.Container focusCycleRoot){
                return cbStatements;
            }//end getFirstComponent

            public java.awt.Component getLastComponent(java.awt.Container focusCycleRoot){
                return cbStatements;
            }//end getLastComponent

            public java.awt.Component getComponentAfter(java.awt.Container focusCycleRoot, java.awt.Component aComponent){
                if(aComponent ==  cbOpenCloseBlockBrace){
                    return cbStatements;
                }
                if(aComponent ==  assignOpsCombo){
                    return cbOpenCloseBlockBrace;
                }
                if(aComponent ==  chainedMethodCallsCombo){
                    return arrayInitCombo;
                }
                if(aComponent ==  methodCallArgsCombo){
                    return chainedMethodCallsCombo;
                }
                if(aComponent ==  methodParamsCombo){
                    return methodCallArgsCombo;
                }
                if(aComponent ==  extendsImplementsListCombo){
                    return methodParamsCombo;
                }
                if(aComponent ==  doWhileStatementCombo){
                    return binaryOpsCombo;
                }
                if(aComponent ==  extendsImplementsKeywordCombo){
                    return extendsImplementsListCombo;
                }
                if(aComponent ==  ternaryOpsCombo){
                    return assignOpsCombo;
                }
                if(aComponent ==  binaryOpsCombo){
                    return ternaryOpsCombo;
                }
                if(aComponent ==  whileStatementComboBox){
                    return doWhileStatementCombo;
                }
                if(aComponent ==  forStatementCombo){
                    return ifStatementCombo;
                }
                if(aComponent ==  ifStatementCombo){
                    return whileStatementComboBox;
                }
                if(aComponent ==  arrayInitCombo){
                    return forCombo;
                }
                if(aComponent ==  forCombo){
                    return forStatementCombo;
                }
                return cbStatements;//end getComponentAfter
            }
            public java.awt.Component getComponentBefore(java.awt.Container focusCycleRoot, java.awt.Component aComponent){
                if(aComponent ==  cbStatements){
                    return cbOpenCloseBlockBrace;
                }
                if(aComponent ==  cbOpenCloseBlockBrace){
                    return assignOpsCombo;
                }
                if(aComponent ==  arrayInitCombo){
                    return chainedMethodCallsCombo;
                }
                if(aComponent ==  chainedMethodCallsCombo){
                    return methodCallArgsCombo;
                }
                if(aComponent ==  methodCallArgsCombo){
                    return methodParamsCombo;
                }
                if(aComponent ==  methodParamsCombo){
                    return extendsImplementsListCombo;
                }
                if(aComponent ==  binaryOpsCombo){
                    return doWhileStatementCombo;
                }
                if(aComponent ==  extendsImplementsListCombo){
                    return extendsImplementsKeywordCombo;
                }
                if(aComponent ==  assignOpsCombo){
                    return ternaryOpsCombo;
                }
                if(aComponent ==  ternaryOpsCombo){
                    return binaryOpsCombo;
                }
                if(aComponent ==  doWhileStatementCombo){
                    return whileStatementComboBox;
                }
                if(aComponent ==  ifStatementCombo){
                    return forStatementCombo;
                }
                if(aComponent ==  whileStatementComboBox){
                    return ifStatementCombo;
                }
                if(aComponent ==  forCombo){
                    return arrayInitCombo;
                }
                if(aComponent ==  forStatementCombo){
                    return forCombo;
                }
                return cbStatements;//end getComponentBefore

            }}
        );
        setName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_Wrapping")); // NOI18N
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        scrollPane.setBackground(java.awt.SystemColor.controlLtHighlight);
        scrollPane.setMinimumSize(new java.awt.Dimension(300, 200));
        scrollPane.setPreferredSize(new java.awt.Dimension(350, 600));

        panel1.setOpaque(false);

        extendsImplemetsKeywordLabel.setLabelFor(extendsImplementsKeywordCombo);
        org.openide.awt.Mnemonics.setLocalizedText(extendsImplemetsKeywordLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_extendsImplementsKeyword")); // NOI18N

        extendsImplementsKeywordCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        extendsImplementsListLabel.setLabelFor(extendsImplementsListCombo);
        org.openide.awt.Mnemonics.setLocalizedText(extendsImplementsListLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_extendsImplementsList")); // NOI18N

        extendsImplementsListCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        methodParamsLabel.setLabelFor(methodParamsCombo);
        org.openide.awt.Mnemonics.setLocalizedText(methodParamsLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_methodParameters")); // NOI18N

        methodParamsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        methodCallArgsLabel.setLabelFor(methodCallArgsCombo);
        org.openide.awt.Mnemonics.setLocalizedText(methodCallArgsLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_methodCallArgs")); // NOI18N

        methodCallArgsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        chainedMethodCallsLabel.setLabelFor(chainedMethodCallsCombo);
        org.openide.awt.Mnemonics.setLocalizedText(chainedMethodCallsLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_chainedMethodCalls")); // NOI18N

        chainedMethodCallsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        arrayInitLabel.setLabelFor(arrayInitCombo);
        org.openide.awt.Mnemonics.setLocalizedText(arrayInitLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_arrayInit")); // NOI18N

        arrayInitCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        forLabel.setLabelFor(forCombo);
        org.openide.awt.Mnemonics.setLocalizedText(forLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_for")); // NOI18N

        forCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        forStatementLabel.setLabelFor(forStatementCombo);
        org.openide.awt.Mnemonics.setLocalizedText(forStatementLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_forStatement")); // NOI18N

        forStatementCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ifStatementLabel.setLabelFor(ifStatementCombo);
        org.openide.awt.Mnemonics.setLocalizedText(ifStatementLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_ifStatement")); // NOI18N

        ifStatementCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        whileStatementLabel.setLabelFor(whileStatementComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(whileStatementLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_whileStatement")); // NOI18N

        whileStatementComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        doWhileStatementLabel.setLabelFor(doWhileStatementCombo);
        org.openide.awt.Mnemonics.setLocalizedText(doWhileStatementLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_doWhileStatement")); // NOI18N

        doWhileStatementCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        binaryOpsLabel.setLabelFor(binaryOpsCombo);
        org.openide.awt.Mnemonics.setLocalizedText(binaryOpsLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_binaryOps")); // NOI18N

        binaryOpsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ternaryOpsLabel.setLabelFor(ternaryOpsCombo);
        org.openide.awt.Mnemonics.setLocalizedText(ternaryOpsLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_ternaryOps")); // NOI18N

        ternaryOpsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        assignOpsLabel.setLabelFor(assignOpsCombo);
        org.openide.awt.Mnemonics.setLocalizedText(assignOpsLabel, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "LBL_wrp_assignOps")); // NOI18N

        assignOpsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.openide.awt.Mnemonics.setLocalizedText(cbOpenCloseBlockBrace, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "cb_wrp_open_close_block_brace")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(cbStatements, org.openide.util.NbBundle.getMessage(FmtWrapping.class, "cb_wrp_Statements")); // NOI18N
        cbStatements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatementsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(extendsImplemetsKeywordLabel)
                        .addGap(6, 6, 6)
                        .addComponent(extendsImplementsKeywordCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(extendsImplementsListLabel)
                        .addGap(37, 37, 37)
                        .addComponent(extendsImplementsListCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(methodParamsLabel)
                        .addGap(67, 67, 67)
                        .addComponent(methodParamsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(methodCallArgsLabel)
                        .addGap(44, 44, 44)
                        .addComponent(methodCallArgsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(chainedMethodCallsLabel)
                        .addGap(56, 56, 56)
                        .addComponent(chainedMethodCallsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(arrayInitLabel)
                        .addGap(100, 100, 100)
                        .addComponent(arrayInitCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(forLabel)
                        .addGap(174, 174, 174)
                        .addComponent(forCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(forStatementLabel)
                        .addGap(101, 101, 101)
                        .addComponent(forStatementCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(ifStatementLabel)
                        .addGap(114, 114, 114)
                        .addComponent(ifStatementCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(whileStatementLabel)
                        .addGap(88, 88, 88)
                        .addComponent(whileStatementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(doWhileStatementLabel)
                        .addGap(54, 54, 54)
                        .addComponent(doWhileStatementCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(binaryOpsLabel)
                        .addGap(86, 86, 86)
                        .addComponent(binaryOpsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(ternaryOpsLabel)
                        .addGap(78, 78, 78)
                        .addComponent(ternaryOpsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbStatements)
                    .addComponent(cbOpenCloseBlockBrace)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(assignOpsLabel)
                        .addGap(50, 50, 50)
                        .addComponent(assignOpsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(extendsImplemetsKeywordLabel))
                    .addComponent(extendsImplementsKeywordCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(extendsImplementsListLabel))
                    .addComponent(extendsImplementsListCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(methodParamsLabel))
                    .addComponent(methodParamsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(methodCallArgsLabel))
                    .addComponent(methodCallArgsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(chainedMethodCallsLabel))
                    .addComponent(chainedMethodCallsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(arrayInitLabel))
                    .addComponent(arrayInitCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(forLabel))
                    .addComponent(forCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(forStatementLabel))
                    .addComponent(forStatementCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(ifStatementLabel))
                    .addComponent(ifStatementCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(whileStatementLabel))
                    .addComponent(whileStatementComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(doWhileStatementLabel))
                    .addComponent(doWhileStatementCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(binaryOpsLabel))
                    .addComponent(binaryOpsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(ternaryOpsLabel))
                    .addComponent(ternaryOpsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(assignOpsLabel))
                    .addComponent(assignOpsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbOpenCloseBlockBrace)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbStatements)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        extendsImplemetsKeywordLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.extendsImplemetsKeywordLabel.AccessibleContext.accessibleName")); // NOI18N
        extendsImplemetsKeywordLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.extendsImplemetsKeywordLabel.AccessibleContext.accessibleDescription")); // NOI18N
        extendsImplementsKeywordCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.extendsImplementsKeywordCombo.AccessibleContext.accessibleName")); // NOI18N
        extendsImplementsKeywordCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.extendsImplementsKeywordCombo.AccessibleContext.accessibleDescription")); // NOI18N
        extendsImplementsListLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.extendsImplementsListLabel.AccessibleContext.accessibleName")); // NOI18N
        extendsImplementsListLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.extendsImplementsListLabel.AccessibleContext.accessibleDescription")); // NOI18N
        extendsImplementsListCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.extendsImplementsListCombo.AccessibleContext.accessibleName")); // NOI18N
        extendsImplementsListCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.extendsImplementsListCombo.AccessibleContext.accessibleDescription")); // NOI18N
        methodParamsLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.methodParamsLabel.AccessibleContext.accessibleName")); // NOI18N
        methodParamsLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.methodParamsLabel.AccessibleContext.accessibleDescription")); // NOI18N
        methodParamsCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.methodParamsCombo.AccessibleContext.accessibleName")); // NOI18N
        methodParamsCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.methodParamsCombo.AccessibleContext.accessibleDescription")); // NOI18N
        methodCallArgsLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.methodCallArgsLabel.AccessibleContext.accessibleName")); // NOI18N
        methodCallArgsLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.methodCallArgsLabel.AccessibleContext.accessibleDescription")); // NOI18N
        methodCallArgsCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.methodCallArgsCombo.AccessibleContext.accessibleName")); // NOI18N
        methodCallArgsCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.methodCallArgsCombo.AccessibleContext.accessibleDescription")); // NOI18N
        chainedMethodCallsLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.chainedMethodCallsLabel.AccessibleContext.accessibleName")); // NOI18N
        chainedMethodCallsLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.chainedMethodCallsLabel.AccessibleContext.accessibleDescription")); // NOI18N
        chainedMethodCallsCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.chainedMethodCallsCombo.AccessibleContext.accessibleName")); // NOI18N
        chainedMethodCallsCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.chainedMethodCallsCombo.AccessibleContext.accessibleDescription")); // NOI18N
        arrayInitLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.arrayInitLabel.AccessibleContext.accessibleName")); // NOI18N
        arrayInitLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.arrayInitLabel.AccessibleContext.accessibleDescription")); // NOI18N
        arrayInitCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.arrayInitCombo.AccessibleContext.accessibleName")); // NOI18N
        arrayInitCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.arrayInitCombo.AccessibleContext.accessibleDescription")); // NOI18N
        forLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.forLabel.AccessibleContext.accessibleName")); // NOI18N
        forLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.forLabel.AccessibleContext.accessibleDescription")); // NOI18N
        forCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.forCombo.AccessibleContext.accessibleName")); // NOI18N
        forCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.forCombo.AccessibleContext.accessibleDescription")); // NOI18N
        forStatementLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.forStatementLabel.AccessibleContext.accessibleName")); // NOI18N
        forStatementLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.forStatementLabel.AccessibleContext.accessibleDescription")); // NOI18N
        forStatementCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.forStatementCombo.AccessibleContext.accessibleName")); // NOI18N
        forStatementCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.forStatementCombo.AccessibleContext.accessibleDescription")); // NOI18N
        ifStatementLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.ifStatementLabel.AccessibleContext.accessibleName")); // NOI18N
        ifStatementLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.ifStatementLabel.AccessibleContext.accessibleDescription")); // NOI18N
        ifStatementCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.ifStatementCombo.AccessibleContext.accessibleName")); // NOI18N
        ifStatementCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.ifStatementCombo.AccessibleContext.accessibleDescription")); // NOI18N
        whileStatementLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.whileStatementLabel.AccessibleContext.accessibleName")); // NOI18N
        whileStatementLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.whileStatementLabel.AccessibleContext.accessibleDescription")); // NOI18N
        whileStatementComboBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.whileStatementComboBox.AccessibleContext.accessibleName")); // NOI18N
        whileStatementComboBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.whileStatementComboBox.AccessibleContext.accessibleDescription")); // NOI18N
        doWhileStatementLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.doWhileStatementLabel.AccessibleContext.accessibleName")); // NOI18N
        doWhileStatementLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.doWhileStatementLabel.AccessibleContext.accessibleDescription")); // NOI18N
        doWhileStatementCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.doWhileStatementCombo.AccessibleContext.accessibleName")); // NOI18N
        doWhileStatementCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.doWhileStatementCombo.AccessibleContext.accessibleDescription")); // NOI18N
        binaryOpsLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.binaryOpsLabel.AccessibleContext.accessibleName")); // NOI18N
        binaryOpsLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.binaryOpsLabel.AccessibleContext.accessibleDescription")); // NOI18N
        binaryOpsCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.binaryOpsCombo.AccessibleContext.accessibleName")); // NOI18N
        binaryOpsCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.binaryOpsCombo.AccessibleContext.accessibleDescription")); // NOI18N
        ternaryOpsLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.ternaryOpsLabel.AccessibleContext.accessibleName")); // NOI18N
        ternaryOpsLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.ternaryOpsLabel.AccessibleContext.accessibleDescription")); // NOI18N
        ternaryOpsCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.ternaryOpsCombo.AccessibleContext.accessibleName")); // NOI18N
        ternaryOpsCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.ternaryOpsCombo.AccessibleContext.accessibleDescription")); // NOI18N
        assignOpsLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.assignOpsLabel.AccessibleContext.accessibleName")); // NOI18N
        assignOpsLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.assignOpsLabel.AccessibleContext.accessibleDescription")); // NOI18N
        assignOpsCombo.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.assignOpsCombo.AccessibleContext.accessibleName")); // NOI18N
        assignOpsCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.assignOpsCombo.AccessibleContext.accessibleDescription")); // NOI18N
        cbOpenCloseBlockBrace.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.cbOpenCloseBlockBrace.AccessibleContext.accessibleName")); // NOI18N
        cbOpenCloseBlockBrace.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.cbOpenCloseBlockBrace.AccessibleContext.accessibleDescription")); // NOI18N
        cbStatements.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.cbStatements.AccessibleContext.accessibleName")); // NOI18N
        cbStatements.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.cbStatements.AccessibleContext.accessibleDescription")); // NOI18N

        scrollPane.setViewportView(panel1);
        panel1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.panel1.AccessibleContext.accessibleName")); // NOI18N
        panel1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.panel1.AccessibleContext.accessibleDescription")); // NOI18N

        add(scrollPane, java.awt.BorderLayout.CENTER);
        scrollPane.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.scrollPane.AccessibleContext.accessibleName")); // NOI18N
        scrollPane.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.scrollPane.AccessibleContext.accessibleDescription")); // NOI18N

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.AccessibleContext.accessibleName")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(FmtWrapping.class, "FmtWrapping.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void cbStatementsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatementsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatementsActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox arrayInitCombo;
    private javax.swing.JLabel arrayInitLabel;
    private javax.swing.JComboBox assignOpsCombo;
    private javax.swing.JLabel assignOpsLabel;
    private javax.swing.JComboBox binaryOpsCombo;
    private javax.swing.JLabel binaryOpsLabel;
    private javax.swing.JCheckBox cbOpenCloseBlockBrace;
    private javax.swing.JCheckBox cbStatements;
    private javax.swing.JComboBox chainedMethodCallsCombo;
    private javax.swing.JLabel chainedMethodCallsLabel;
    private javax.swing.JComboBox doWhileStatementCombo;
    private javax.swing.JLabel doWhileStatementLabel;
    private javax.swing.JComboBox extendsImplementsKeywordCombo;
    private javax.swing.JComboBox extendsImplementsListCombo;
    private javax.swing.JLabel extendsImplementsListLabel;
    private javax.swing.JLabel extendsImplemetsKeywordLabel;
    private javax.swing.JComboBox forCombo;
    private javax.swing.JLabel forLabel;
    private javax.swing.JComboBox forStatementCombo;
    private javax.swing.JLabel forStatementLabel;
    private javax.swing.JComboBox ifStatementCombo;
    private javax.swing.JLabel ifStatementLabel;
    private javax.swing.JComboBox methodCallArgsCombo;
    private javax.swing.JLabel methodCallArgsLabel;
    private javax.swing.JComboBox methodParamsCombo;
    private javax.swing.JLabel methodParamsLabel;
    private javax.swing.JPanel panel1;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JComboBox ternaryOpsCombo;
    private javax.swing.JLabel ternaryOpsLabel;
    private javax.swing.JComboBox whileStatementComboBox;
    private javax.swing.JLabel whileStatementLabel;
    // End of variables declaration//GEN-END:variables

}
