/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */


package org.netbeans.modules.ruby.railsprojects.ui.wizards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.StringTokenizer;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 *
 * @author  phrebejk
 */
public class PanelOptionsVisual extends SettingsPanel implements ActionListener, PropertyChangeListener {
    
    private static boolean lastMainClassCheck = true; // XXX Store somewhere
    
    private PanelConfigureProject panel;
    private boolean valid;
    
    /** Creates new form PanelOptionsVisual */
    public PanelOptionsVisual( PanelConfigureProject panel, int type ) {
        initComponents();
        this.panel = panel;

        switch (type) {
//            case NewRailsProjectWizardIterator.TYPE_LIB:
//                setAsMainCheckBox.setVisible( false );
//                createMainCheckBox.setVisible( false );
//                mainClassTextField.setVisible( false );
//                break;
            case NewRailsProjectWizardIterator.TYPE_APP:
                //createMainCheckBox.addActionListener( this );
                //createMainCheckBox.setSelected( lastMainClassCheck );
                //mainClassTextField.setEnabled( lastMainClassCheck );
                break;
            case NewRailsProjectWizardIterator.TYPE_EXT:
                setAsMainCheckBox.setVisible( true );
                //createMainCheckBox.setVisible( false );
                //mainClassTextField.setVisible( false );
                break;
        }
        
        //this.mainClassTextField.getDocument().addDocumentListener( new DocumentListener () {
        //   
        //    public void insertUpdate(DocumentEvent e) {
        //        mainClassChanged ();
        //    }
        //    
        //    public void removeUpdate(DocumentEvent e) {
        //        mainClassChanged ();
        //    }
        //    
        //    public void changedUpdate(DocumentEvent e) {
        //        mainClassChanged ();
        //    }
        //    
        //});
    }

    public void actionPerformed( ActionEvent e ) {        
        //if ( e.getSource() == createMainCheckBox ) {
        //    lastMainClassCheck = createMainCheckBox.isSelected();
        //    mainClassTextField.setEnabled( lastMainClassCheck );        
        //    this.panel.fireChangeEvent();
        //}                
    }
    
    public void propertyChange (PropertyChangeEvent event) {
        //if (PanelProjectLocationVisual.PROP_PROJECT_NAME.equals(event.getPropertyName())) {
        //    String newProjectName = NewRailsProjectWizardIterator.getPackageName((String) event.getNewValue());
        //    if (!Utilities.isJavaIdentifier(newProjectName)) {
        //        newProjectName = NbBundle.getMessage (PanelOptionsVisual.class, "TXT_PackageNameSuffix", newProjectName); 
        //    }
        //    this.mainClassTextField.setText (MessageFormat.format(
        //        NbBundle.getMessage (PanelOptionsVisual.class,"TXT_ClassName"), new Object[] {newProjectName}
        //    ));
        //}
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        setAsMainCheckBox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        setAsMainCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(setAsMainCheckBox, org.openide.util.NbBundle.getBundle(PanelOptionsVisual.class).getString("LBL_setAsMainCheckBox")); // NOI18N
        setAsMainCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(setAsMainCheckBox, gridBagConstraints);
        setAsMainCheckBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getBundle(PanelOptionsVisual.class).getString("ACSN_setAsMainCheckBox")); // NOI18N
        setAsMainCheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(PanelOptionsVisual.class).getString("ACSD_setAsMainCheckBox")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
        jPanel1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getBundle(PanelOptionsVisual.class).getString("ACSN_jPanel1")); // NOI18N
        jPanel1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(PanelOptionsVisual.class).getString("ASCD_jPanel1")); // NOI18N

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PanelOptionsVisual.class, "ACSN_PanelOptionsVisual")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PanelOptionsVisual.class, "ACSD_PanelOptionsVisual")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
    boolean valid(WizardDescriptor settings) {
        //if (mainClassTextField.isVisible () && mainClassTextField.isEnabled ()) {
        //    if (!valid) {
        //        settings.putProperty( "WizardPanel_errorMessage", // NOI18N
        //            NbBundle.getMessage(PanelOptionsVisual.class,"ERROR_IllegalMainClassName")); //NOI18N
        //    }
        //    return this.valid;
        //}
        //else {
            return true;
        //}
    }
    
    void read (WizardDescriptor d) {
        //TODO:
    }
    
    void validate (WizardDescriptor d) throws WizardValidationException {
        // nothing to validate
    }

    void store( WizardDescriptor d ) {
        d.putProperty( /*XXX Define somewhere */ "setAsMain", setAsMainCheckBox.isSelected() && setAsMainCheckBox.isVisible() ? Boolean.TRUE : Boolean.FALSE ); // NOI18N
        //d.putProperty( /*XXX Define somewhere */ "mainClass", createMainCheckBox.isSelected() && createMainCheckBox.isVisible() ? mainClassTextField.getText() : null ); // NOI18N
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox setAsMainCheckBox;
    // End of variables declaration//GEN-END:variables
    
    //private void mainClassChanged () {
    //    
    //    String mainClassName = this.mainClassTextField.getText ();
    //    StringTokenizer tk = new StringTokenizer (mainClassName, "."); //NOI18N
    //    boolean valid = true;
    //    while (tk.hasMoreTokens()) {
    //        String token = tk.nextToken();
    //        if (token.length() == 0 || /* !Utilities.isJavaIdentifier(token)*/ token.equals(" ")) {
    //            valid = false;
    //            break;
    //        }            
    //    }
    //    this.valid = valid;
    //    this.panel.fireChangeEvent();
    //}
}

