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

package org.netbeans.modules.j2ee.ejbjarproject.ui.customizer;

import java.util.Collections;
import java.util.List;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import org.netbeans.modules.websvc.api.client.WsCompileClientEditorSupport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.filesystems.FileObject;

import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.modules.j2ee.ejbjarproject.EjbJarProvider;
import org.netbeans.modules.j2ee.ejbjarproject.EjbJarProjectType;

import org.netbeans.modules.websvc.spi.webservices.WebServicesConstants;
import org.netbeans.modules.websvc.api.client.WsCompileClientEditorSupport;


/** Host for WsCompile features editor for editing the features enabled for
 *  running WsCompile on a web service or a web service client.
 *
 *  property format: 'webservice.client.[servicename].features=xxx,yyy,zzz
 *
 * @author Peter Williams
 */
public class CustomizerWSClientHost extends javax.swing.JPanel 
    implements PropertyChangeListener, HelpCtx.Provider { // WebCustomizer.Panel, WebCustomizer.ValidatingPanel
    
    private EjbJarProjectProperties ejbJarProperties;
    private WsCompileClientEditorSupport.Panel wsCompileEditor;

    private List serviceSettings;
    
    public CustomizerWSClientHost(EjbJarProjectProperties ejbJarProperties, List serviceSettings) {
        assert serviceSettings != null && serviceSettings.size() > 0;
        initComponents();

        this.ejbJarProperties = ejbJarProperties;
        this.wsCompileEditor = null;
        this.serviceSettings = serviceSettings;
        
        initValues();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());

    }
    // </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public void addNotify() {
        super.addNotify();

        JPanel component = wsCompileEditor.getComponent();

        removeAll(); // !PW is this necessary?
        add(component);

        component.addPropertyChangeListener(WsCompileClientEditorSupport.PROP_FEATURES_CHANGED, this);
    }

    public void removeNotify() {
        super.removeNotify();

        JPanel component = wsCompileEditor.getComponent();
        component.removePropertyChangeListener(WsCompileClientEditorSupport.PROP_FEATURES_CHANGED, this);
    }

    public void initValues() {
        if(wsCompileEditor == null) {
            WsCompileClientEditorSupport editorSupport = (WsCompileClientEditorSupport) Lookup.getDefault().lookup(WsCompileClientEditorSupport.class);
            wsCompileEditor = editorSupport.getWsCompileSupport();
        }

        wsCompileEditor.initValues(serviceSettings);
    }   

    /*public void validatePanel() throws WizardValidationException {
        if(wsCompileEditor != null) {
            wsCompileEditor.validatePanel();
        }
    }*/

    public void propertyChange(PropertyChangeEvent evt) {
        WsCompileClientEditorSupport.FeatureDescriptor newFeatureDesc = (WsCompileClientEditorSupport.FeatureDescriptor) evt.getNewValue();
        String propertyName = "wscompile.client." + newFeatureDesc.getServiceName() + ".features";
        ejbJarProperties.putAdditionalProperty(propertyName, newFeatureDesc.getFeatures());
    }
    
    public HelpCtx getHelpCtx() {
        return new HelpCtx(CustomizerWSClientHost.class);
    }
}
