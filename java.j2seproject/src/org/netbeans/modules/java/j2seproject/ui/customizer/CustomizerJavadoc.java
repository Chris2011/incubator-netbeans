/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2004 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.java.j2seproject.ui.customizer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import org.openide.util.HelpCtx;

/** Customizer for general project attributes.
 *
 * @author  phrebejk
 */
public class CustomizerJavadoc extends JPanel implements J2SECustomizer.Panel, HelpCtx.Provider {
    
    private final J2SEProjectProperties j2seProperties;
    private final VisualPropertySupport vps;
        
    /** Creates new form CustomizerCompile */
    public CustomizerJavadoc( J2SEProjectProperties j2seProperties ) {
        initComponents();
        this.j2seProperties = j2seProperties;
        vps = new VisualPropertySupport( j2seProperties );
    }
    
    
    public void initValues() {
        
        vps.register( jCheckBoxPrivate, J2SEProjectProperties.JAVADOC_PRIVATE );
        vps.register( jCheckBoxTree, J2SEProjectProperties.JAVADOC_NO_TREE );
        vps.register( jCheckBoxUsages, J2SEProjectProperties.JAVADOC_USE );
        vps.register( jCheckBoxNavigation, J2SEProjectProperties.JAVADOC_NO_NAVBAR ); 
        vps.register( jCheckBoxIndex, J2SEProjectProperties.JAVADOC_NO_INDEX ); 
        vps.register( jCheckBoxSplitIndex, J2SEProjectProperties.JAVADOC_SPLIT_INDEX ); 
        vps.register( jCheckBoxAuthor, J2SEProjectProperties.JAVADOC_AUTHOR ); 
        vps.register( jCheckBoxVersion, J2SEProjectProperties.JAVADOC_VERSION );
        vps.register( jTextFieldWinTitle, J2SEProjectProperties.JAVADOC_WINDOW_TITLE );
        // vps.register( jTextFieldEncoding, J2SEProjectProperties.JAVADOC_ENCODING ); 
        vps.register( jCheckBoxPreview, J2SEProjectProperties.JAVADOC_PREVIEW ); 
                
        reenableSplitIndex( null );
        
        // XXX Temporarily removing some controls
        remove( jLabelPackage );
        remove( jTextFieldPackage );
        remove( jButtonPackage );       
        remove( jCheckBoxSubpackages );
        jPanel1.remove( jLabelEncoding );
        jPanel1.remove( jTextFieldEncoding );
        
        
    } 
    
    public HelpCtx getHelpCtx() {
        return new HelpCtx( CustomizerJavadoc.class );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelPackage = new javax.swing.JLabel();
        jTextFieldPackage = new javax.swing.JTextField();
        jButtonPackage = new javax.swing.JButton();
        jCheckBoxSubpackages = new javax.swing.JCheckBox();
        jCheckBoxPrivate = new javax.swing.JCheckBox();
        jLabelGenerate = new javax.swing.JLabel();
        jCheckBoxTree = new javax.swing.JCheckBox();
        jCheckBoxUsages = new javax.swing.JCheckBox();
        jCheckBoxNavigation = new javax.swing.JCheckBox();
        jCheckBoxIndex = new javax.swing.JCheckBox();
        jCheckBoxSplitIndex = new javax.swing.JCheckBox();
        jLabelTags = new javax.swing.JLabel();
        jCheckBoxAuthor = new javax.swing.JCheckBox();
        jCheckBoxVersion = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabelWinTitle = new javax.swing.JLabel();
        jTextFieldWinTitle = new javax.swing.JTextField();
        jLabelEncoding = new javax.swing.JLabel();
        jTextFieldEncoding = new javax.swing.JTextField();
        jCheckBoxPreview = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.EtchedBorder(), new javax.swing.border.EmptyBorder(new java.awt.Insets(12, 12, 12, 12))));
        jLabelPackage.setText(org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Package_JLabel"));
        jLabelPackage.setLabelFor(jTextFieldPackage);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        add(jLabelPackage, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 6);
        add(jTextFieldPackage, gridBagConstraints);
        jTextFieldPackage.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jTextFieldPackage"));

        jButtonPackage.setText(org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Package_JButton"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(jButtonPackage, gridBagConstraints);
        jButtonPackage.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jButtonPackage"));

        jCheckBoxSubpackages.setText(org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Subpackages_JCheckBox"));
        jCheckBoxSubpackages.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(jCheckBoxSubpackages, gridBagConstraints);
        jCheckBoxSubpackages.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxSubpackages"));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxPrivate, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Private_JCheckBox"));
        jCheckBoxPrivate.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(jCheckBoxPrivate, gridBagConstraints);
        jCheckBoxPrivate.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxPrivate"));

        jLabelGenerate.setText(org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Generate_JLabel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        add(jLabelGenerate, gridBagConstraints);
        jLabelGenerate.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jLabelGenerate"));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxTree, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Tree_JCheckBox"));
        jCheckBoxTree.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 5, 0);
        add(jCheckBoxTree, gridBagConstraints);
        jCheckBoxTree.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxTree"));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxUsages, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Usages_JCheckBox"));
        jCheckBoxUsages.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 5, 0);
        add(jCheckBoxUsages, gridBagConstraints);
        jCheckBoxUsages.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxUsages"));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxNavigation, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Navigation_JCheckBox"));
        jCheckBoxNavigation.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 5, 0);
        add(jCheckBoxNavigation, gridBagConstraints);
        jCheckBoxNavigation.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxNavigation"));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxIndex, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Index_JCheckBox"));
        jCheckBoxIndex.setMargin(new java.awt.Insets(0, 0, 0, 2));
        jCheckBoxIndex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reenableSplitIndex(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 5, 0);
        add(jCheckBoxIndex, gridBagConstraints);
        jCheckBoxIndex.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxIndex"));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxSplitIndex, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_SplitIndex_JCheckBox"));
        jCheckBoxSplitIndex.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 12, 0);
        add(jCheckBoxSplitIndex, gridBagConstraints);
        jCheckBoxSplitIndex.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxSplitIndex"));

        jLabelTags.setText(org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Tags_JLabel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        add(jLabelTags, gridBagConstraints);
        jLabelTags.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jLabelTags"));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAuthor, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Author_JCheckBox"));
        jCheckBoxAuthor.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 5, 0);
        add(jCheckBoxAuthor, gridBagConstraints);
        jCheckBoxAuthor.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxAuthor"));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxVersion, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Version_JCheckBox"));
        jCheckBoxVersion.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 12, 0);
        add(jCheckBoxVersion, gridBagConstraints);
        jCheckBoxVersion.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxVersion"));

        jPanel1.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabelWinTitle, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_WinTitle_JLabel"));
        jLabelWinTitle.setLabelFor(jTextFieldWinTitle);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 6);
        jPanel1.add(jLabelWinTitle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        jPanel1.add(jTextFieldWinTitle, gridBagConstraints);
        jTextFieldWinTitle.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jTextFieldWinTitle"));

        jLabelEncoding.setText(org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Encoding_JLabel"));
        jLabelEncoding.setLabelFor(jTextFieldEncoding);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 6);
        jPanel1.add(jLabelEncoding, gridBagConstraints);

        jTextFieldEncoding.setPreferredSize(new java.awt.Dimension(150, 22));
        jTextFieldEncoding.setMinimumSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jTextFieldEncoding, gridBagConstraints);
        jTextFieldEncoding.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jTextFieldEncoding"));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxPreview, org.openide.util.NbBundle.getMessage(CustomizerJavadoc.class, "LBL_CustomizeJavadoc_Preview_JCheckBox"));
        jCheckBoxPreview.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(jCheckBoxPreview, gridBagConstraints);
        jCheckBoxPreview.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/java/j2seproject/ui/customizer/Bundle").getString("AD_jCheckBoxPreview"));

    }//GEN-END:initComponents

    private void reenableSplitIndex(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reenableSplitIndex
        jCheckBoxSplitIndex.setEnabled( jCheckBoxIndex.isSelected() );
        // Cannot use -splitindex with -noindex:
        if (!jCheckBoxIndex.isSelected()) {
            j2seProperties.put(J2SEProjectProperties.JAVADOC_SPLIT_INDEX, Boolean.FALSE);
        }
    }//GEN-LAST:event_reenableSplitIndex
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPackage;
    private javax.swing.JCheckBox jCheckBoxAuthor;
    private javax.swing.JCheckBox jCheckBoxIndex;
    private javax.swing.JCheckBox jCheckBoxNavigation;
    private javax.swing.JCheckBox jCheckBoxPreview;
    private javax.swing.JCheckBox jCheckBoxPrivate;
    private javax.swing.JCheckBox jCheckBoxSplitIndex;
    private javax.swing.JCheckBox jCheckBoxSubpackages;
    private javax.swing.JCheckBox jCheckBoxTree;
    private javax.swing.JCheckBox jCheckBoxUsages;
    private javax.swing.JCheckBox jCheckBoxVersion;
    private javax.swing.JLabel jLabelEncoding;
    private javax.swing.JLabel jLabelGenerate;
    private javax.swing.JLabel jLabelPackage;
    private javax.swing.JLabel jLabelTags;
    private javax.swing.JLabel jLabelWinTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldEncoding;
    private javax.swing.JTextField jTextFieldPackage;
    private javax.swing.JTextField jTextFieldWinTitle;
    // End of variables declaration//GEN-END:variables
        
    // Storing methods ---------------------------------------------------------
    
    /** Stores the value according to the src component into the helper
     */
    private void store( JComponent src ) {
    
        /*
        if ( src == jTextFieldSrcDir ) {
            j2seProperties.put( J2SEProjectProperties.SRC_DIR, jTextFieldSrcDir.getText() );
        }
        else if ( src == jTextFieldBuildDir ) {
            j2seProperties.put( J2SEProjectProperties.BUILD_DIR, jTextFieldBuildDir.getText() );
        }
        else if ( src == jListClasspath ) {
            
            List elements = new ArrayList( classpathModel.size() );
            
            for ( Enumeration e = classpathModel.elements(); e.hasMoreElements(); ) {
                elements.add( e.nextElement() );
            }
            j2seProperties.put( J2SEProjectProperties.JAVAC_CLASSPATH, elements );
        }
        
        assert true : "CustomizerCompile - Unknown component : " + src; // NOI18N
        */
    } 
    
    
    
    // Private methods for classpath data manipulation -------------------------
        
}
