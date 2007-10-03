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

package org.netbeans.modules.j2ee.ejbjarproject.ui.customizer;

import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import org.openide.util.HelpCtx;
import org.netbeans.modules.websvc.api.webservices.WsCompileEditorSupport;
import org.openide.util.Lookup;


/** Host for WsCompile features editor for editing the features enabled for
 *  running WsCompile on a web service or a web service client.
 *
 *  property format: 'webservice.client.[servicename].features=xxx,yyy,zzz
 *
 * @author Peter Williams
 */
public class CustomizerWSServiceHost extends javax.swing.JPanel implements PropertyChangeListener, HelpCtx.Provider {
    
    private EjbJarProjectProperties ejbJarProperties;
    private WsCompileEditorSupport.Panel wsCompileEditor;

    private List serviceSettings;
    
    public CustomizerWSServiceHost(EjbJarProjectProperties ejbJarProperties, List serviceSettings) {
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
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

    }//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public void addNotify() {
        super.addNotify();

        JPanel component = wsCompileEditor.getComponent();

        removeAll(); // !PW is this necessary?
        add(component);

        component.addPropertyChangeListener(WsCompileEditorSupport.PROP_FEATURES_CHANGED, this);
    }

    public void removeNotify() {
        super.removeNotify();

        JPanel component = wsCompileEditor.getComponent();
        component.removePropertyChangeListener(WsCompileEditorSupport.PROP_FEATURES_CHANGED, this);
    }

    public void initValues() {
        if(wsCompileEditor == null) {
            WsCompileEditorSupport editorSupport = (WsCompileEditorSupport) Lookup.getDefault().lookup(WsCompileEditorSupport.class);
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
        WsCompileEditorSupport.FeatureDescriptor newFeatureDesc = (WsCompileEditorSupport.FeatureDescriptor) evt.getNewValue();
        String propertyName = "wscompile.service." + newFeatureDesc.getServiceName() + ".features";
        ejbJarProperties.putAdditionalProperty(propertyName, newFeatureDesc.getFeatures());
    }
    
    public HelpCtx getHelpCtx() {
        return new HelpCtx(CustomizerWSServiceHost.class);
    }
}
