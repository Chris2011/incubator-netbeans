/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.core.windows.options;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.LifecycleManager;
import org.openide.awt.Notification;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.windows.WindowManager;

@OptionsPanelController.Keywords(keywords={"#KW_LafOptions"}, location="Appearance", tabTitle="#Laf_DisplayName")
public class LafPanel extends javax.swing.JPanel {

    protected final LafOptionsPanelController controller;
    
    private final Preferences prefs = NbPreferences.forModule(LafPanel.class);
    
    private final boolean isAquaLaF = "Aqua".equals(UIManager.getLookAndFeel().getID()); //NOI18N
    private static final boolean NO_RESTART_ON_LAF_CHANGE = Boolean.getBoolean("nb.laf.norestart"); //NOI18N
    private int defaultLookAndFeelIndex;
    private final ArrayList<LookAndFeelInfo> lafs = new ArrayList<LookAndFeelInfo>( 10 );
    
    protected LafPanel(final LafOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        // TODO listen to changes in form fields and call controller.changed()
        checkMaximizeNativeLaF.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                fireChanged();
            }
        });
        initLookAndFeel();
        lblRestart.setVisible(!NO_RESTART_ON_LAF_CHANGE);
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for( LookAndFeelInfo li : lafs ) {
            model.addElement( li.getName() );
        }
        comboLaf.setModel( model );
        comboLaf.addItemListener( new ItemListener() {

            @Override
            public void itemStateChanged( ItemEvent e ) {
                fireChanged();
            }
        });
    }
    
    private void fireChanged() {
        boolean isChanged = false;
        if (checkMaximizeNativeLaF.isSelected() != prefs.getBoolean(WinSysPrefs.MAXIMIZE_NATIVE_LAF, false)
                || comboLaf.getSelectedIndex() != lafs.indexOf(isForcedLaF() ? getCurrentLaF() : getPreferredLaF())) {
            isChanged = true;
        }
        controller.changed(isChanged);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelLaF = new javax.swing.JPanel();
        checkMaximizeNativeLaF = new javax.swing.JCheckBox();
        panelLaFCombo = new javax.swing.JPanel();
        comboLaf = new javax.swing.JComboBox();
        lblLaf = new javax.swing.JLabel();
        lblRestart = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new java.awt.GridBagLayout());

        panelLaF.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(checkMaximizeNativeLaF, org.openide.util.NbBundle.getMessage(LafPanel.class, "LafPanel.checkMaximizeNativeLaF.text")); // NOI18N
        checkMaximizeNativeLaF.setToolTipText(org.openide.util.NbBundle.getMessage(LafPanel.class, "LafPanel.checkMaximizeNativeLaF.toolTipText")); // NOI18N
        panelLaF.add(checkMaximizeNativeLaF, java.awt.BorderLayout.WEST);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(panelLaF, gridBagConstraints);

        panelLaFCombo.setLayout(new java.awt.BorderLayout(3, 0));
        panelLaFCombo.add(comboLaf, java.awt.BorderLayout.CENTER);

        lblLaf.setLabelFor(comboLaf);
        org.openide.awt.Mnemonics.setLocalizedText(lblLaf, NbBundle.getMessage(LafPanel.class, "LafPanel.lblLaf.text")); // NOI18N
        panelLaFCombo.add(lblLaf, java.awt.BorderLayout.WEST);

        org.openide.awt.Mnemonics.setLocalizedText(lblRestart, NbBundle.getMessage(LafPanel.class, "LafPanel.lblRestart.text")); // NOI18N
        panelLaFCombo.add(lblRestart, java.awt.BorderLayout.LINE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(panelLaFCombo, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    protected void load() {
        checkMaximizeNativeLaF.setSelected(prefs.getBoolean(WinSysPrefs.MAXIMIZE_NATIVE_LAF, false));
        
        boolean isForcedLaF = isForcedLaF();
        defaultLookAndFeelIndex = lafs.indexOf( isForcedLaF ? getCurrentLaF() : getPreferredLaF() );
        comboLaf.setSelectedIndex( defaultLookAndFeelIndex );
        comboLaf.setEnabled( !isForcedLaF );
    }

    protected boolean store() {
        prefs.putBoolean(WinSysPrefs.MAXIMIZE_NATIVE_LAF, checkMaximizeNativeLaF.isSelected());
        System.setProperty("nb.native.filechooser", checkMaximizeNativeLaF.isSelected() ? "true" : "false"); //NOI18N

        int selLaFIndex = comboLaf.getSelectedIndex();
        if( selLaFIndex != defaultLookAndFeelIndex && !isForcedLaF() ) {
            LookAndFeelInfo li = lafs.get( comboLaf.getSelectedIndex() );
            NbPreferences.root().node( "laf" ).put( "laf", li.getClassName() ); //NOI18N
            if (NO_RESTART_ON_LAF_CHANGE) {
                try {
                    UIManager.setLookAndFeel(li.getClassName());
                    WindowManager wmgr = Lookup.getDefault().lookup(WindowManager.class);
                    wmgr.updateUI();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
                    askForRestart();
                }
            } else {
                askForRestart();
            }
        }

        return false;
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkMaximizeNativeLaF;
    private javax.swing.JComboBox comboLaf;
    private javax.swing.JLabel lblLaf;
    private javax.swing.JLabel lblRestart;
    private javax.swing.JPanel panelLaF;
    private javax.swing.JPanel panelLaFCombo;
    // End of variables declaration//GEN-END:variables


    private void initLookAndFeel() {
        lafs.clear();
        for( LookAndFeelInfo i : UIManager.getInstalledLookAndFeels() ) {
            lafs.add( i );
        }
    }

    private boolean isForcedLaF() {
        return null != System.getProperty( "nb.laf.forced" ); //NOI18N
    }

    private LookAndFeelInfo getCurrentLaF() {
        LookAndFeelInfo currentLaf = null;
        String currentLAFClassName = UIManager.getLookAndFeel().getClass().getName();
        boolean isAqua = "Aqua".equals(UIManager.getLookAndFeel().getID()); //NOI18N
        for( LookAndFeelInfo li : lafs ) {
            if( currentLAFClassName.equals( li.getClassName() ) 
                    || (isAqua && li.getClassName().contains("com.apple.laf.AquaLookAndFeel")) ) { //NOI18N
                currentLaf = li;
                break;
            }
        }
        return currentLaf;
    }

    private LookAndFeelInfo getPreferredLaF() {
        String lafClassName = NbPreferences.root().node( "laf" ).get( "laf", null ); //NOI18N
        if( null == lafClassName )
            return getCurrentLaF();
        LookAndFeelInfo currentLaf = null;
        boolean isAqua = "Aqua".equals(UIManager.getLookAndFeel().getID()); //NOI18N
        for( LookAndFeelInfo li : lafs ) {
            if( lafClassName.equals( li.getClassName() )
                    || (isAqua && li.getClassName().contains("com.apple.laf.AquaLookAndFeel")) ) { //NOI18N
                currentLaf = li;
                break;
            }
        }
        return currentLaf;
    }

    private static Notification restartNotification;

    private void askForRestart() {
        if( null != restartNotification ) {
            restartNotification.clear();
        }
        restartNotification = NotificationDisplayer.getDefault().notify( NbBundle.getMessage(LafPanel.class, "Hint_RESTART_IDE"),
                ImageUtilities.loadImageIcon( "org/netbeans/core/windows/resources/restart.png", true ), //NOI18N
                createRestartNotificationDetails(), createRestartNotificationDetails(),
                NotificationDisplayer.Priority.HIGH, NotificationDisplayer.Category.INFO);
    }

    void selectDarkLookAndFeel() {
        comboLaf.requestFocusInWindow();
        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {
                comboLaf.setPopupVisible( true );
            }
        });
    }

    //Use reflection to instantiate ColorModel class and get/set the current profile
    private static final String COLOR_MODEL_CLASS_NAME = "org.netbeans.modules.options.colors.ColorModel"; //NOI18N

    private boolean isChangeEditorColorsPossible() {
        String preferredProfile = getPreferredColorProfile();
        if( preferredProfile == null )
            return false;
        ClassLoader cl = Lookup.getDefault().lookup( ClassLoader.class );
        if( null == cl )
            cl = LafPanel.class.getClassLoader();
        try {
            Class klz = cl.loadClass( COLOR_MODEL_CLASS_NAME );
            Object colorModel = klz.newInstance();
            Method m = klz.getDeclaredMethod( "getCurrentProfile", new Class[0] ); //NOI18N
            Object res = m.invoke( colorModel, new Object[0] );
            return res != null && !preferredProfile.equals( res );
        } catch( Exception ex ) {
            //ignore
        }
        return false;
    }

    private void switchEditorColorsProfile() {
        if( !isChangeEditorColorsPossible() )
            return;
        String preferredProfile = getPreferredColorProfile();

        ClassLoader cl = Lookup.getDefault().lookup( ClassLoader.class );
        if( null == cl )
            cl = LafPanel.class.getClassLoader();
        try {
            Class klz = cl.loadClass( COLOR_MODEL_CLASS_NAME );
            Object colorModel = klz.newInstance();
            Method m = klz.getDeclaredMethod( "getAnnotations", String.class ); //NOI18N
            Object annotations = m.invoke( colorModel, preferredProfile );
            m = klz.getDeclaredMethod( "setAnnotations", String.class, Collection.class ); //NOI18N
            m.invoke( colorModel, preferredProfile, annotations );
            m = klz.getDeclaredMethod( "setCurrentProfile", String.class ); //NOI18N
            m.invoke( colorModel, preferredProfile );
        } catch( Exception ex ) {
            //ignore
            Logger.getLogger( LafPanel.class.getName() ).log( Level.INFO, "Cannot change editor colors profile.", ex ); //NOI18N
        }
    }

    private JComponent createRestartNotificationDetails() {
        JPanel res = new JPanel( new BorderLayout( 10, 10) );
        res.setOpaque( false );
        JLabel lbl = new JLabel( NbBundle.getMessage( LafPanel.class, "Descr_Restart") ); //NOI18N
        lbl.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        res.add( lbl, BorderLayout.CENTER );
        final JCheckBox checkEditorColors = new JCheckBox( NbBundle.getMessage( LafPanel.class, "Hint_ChangeEditorColors" ) ); //NOI18N
        if( isChangeEditorColorsPossible() ) {
            checkEditorColors.setSelected( true );
            checkEditorColors.setOpaque( false );
            res.add( checkEditorColors, BorderLayout.SOUTH );
        }
        lbl.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                if( null != restartNotification ) {
                    restartNotification.clear();
                    restartNotification = null;
                }
                if( checkEditorColors.isSelected() ) {
                    switchEditorColorsProfile();
                }
                LifecycleManager.getDefault().markForRestart();
                LifecycleManager.getDefault().exit();
            }
        });
        return res;
    }

    private String getPreferredColorProfile() {
        String className = NbPreferences.root().node( "laf" ).get( "laf", null );
        if( null == className )
            return null;

        ClassLoader loader = Lookup.getDefault().lookup( ClassLoader.class );
        if( null == loader )
            loader = ClassLoader.getSystemClassLoader();

        try {
            Class klazz = loader.loadClass( className );
            LookAndFeel laf = ( LookAndFeel ) klazz.newInstance();
            return laf.getDefaults().getString( "nb.preferred.color.profile" ); //NOI18N
        } catch( Exception e ) {
            //ignore
        }
        return null;
    }
}
