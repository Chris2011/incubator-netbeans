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

package org.netbeans.modules.java.navigation;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.java.source.ui.ElementJavadoc;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 * 
 * @author Sandip V. Chitale (Sandip.Chitale@Sun.Com)
 */
public final class JavadocTopComponent extends TopComponent {
    
    private static final Logger LOGGER = Logger.getLogger(JavadocTopComponent.class.getName());
    
    private static JavadocTopComponent instance;
    /** path to the icon used by the component and its open action */
    public static final String ICON_PATH = "org/netbeans/modules/java/navigation/resources/javadoc_action.png";
    
    private static final String PREFERRED_ID = "JavadocTopComponent";
    
    private DocumentationScrollPane documentationPane;
    
    private JavadocTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(JavadocTopComponent.class, "CTL_JavadocTopComponent"));
        setToolTipText(NbBundle.getMessage(JavadocTopComponent.class, "HINT_JavadocTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        
        documentationPane = new DocumentationScrollPane( false );
        add( documentationPane, BorderLayout.CENTER );
    }
    
    void setJavadoc( ElementJavadoc doc ){    
        documentationPane.setData( doc );
    }
    
    public static boolean shouldUpdate() {
        if ( instance == null ) {
            return false;
        }
        else  {
            return instance.isShowing();
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized JavadocTopComponent getDefault() {
        if (instance == null) {
            instance = new JavadocTopComponent();
        }
        return instance;
    }
    
    /**
     * Obtain the JavadocTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized JavadocTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            LOGGER.log(Level.WARNING, 
                       "Cannot find MyWindow component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof JavadocTopComponent) {
            return (JavadocTopComponent)win;
        }
        LOGGER.log(Level./* Shut up! Logged dozens of times in every session. */FINE,
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
   
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    protected void componentActivated() {
        super.componentActivated();
        // System.out.println("JDW Activated");
        documentationPane.getViewport().getView().requestFocusInWindow();
    }
    
    @Override
    public void componentOpened() {
        // System.out.println("JDW Opened ");
        documentationPane.getViewport().getView().requestFocusInWindow();
    }

    @Override
    protected void componentShowing() {
        super.componentShowing();
        // System.out.println("JDW Showing");
        CaretListeningFactory.runAgain();
    }
    
    
    
    public void componentClosed() {
    }
    
    /** replaces this in object stream */
    public Object writeReplace() {
        return new ResolvableHelper();
    }
    
    protected String preferredID() {
        return PREFERRED_ID;
    }
    
    final static class ResolvableHelper implements Serializable {
        private static final long serialVersionUID = 1L;
        public Object readResolve() {
            return JavadocTopComponent.getDefault();
        }
    }
    
}
