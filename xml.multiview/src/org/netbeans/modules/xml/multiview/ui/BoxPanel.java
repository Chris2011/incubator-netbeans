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
package org.netbeans.modules.xml.multiview.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author pfiala
 */
public class BoxPanel extends SectionNodeInnerPanel {

    /**
     * Creates new form BoxPanel
     *
     * @param sectionNodeView
     */
    public BoxPanel(SectionNodeView sectionNodeView) {
        super(sectionNodeView);
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public JComponent getErrorComponent(String errorId) {
        final Component[] components = getComponents();
        for (int i = 0; i < components.length; i++) {
            final Component component = components[i];
            if (component instanceof SectionInnerPanel) {
                SectionInnerPanel panel = (SectionInnerPanel) component;
                final JComponent errorComponent = panel.getErrorComponent(errorId);
                if (errorComponent != null) {
                    return errorComponent;
                }
            }
        }
        return null;
    }

    public void setValue(JComponent source, Object value) {

    }

    public void linkButtonPressed(Object ddBean, String ddProperty) {
        final Component[] components = getComponents();
        for (int i = 0; i < components.length; i++) {
            final Component component = components[i];
            if (component instanceof SectionInnerPanel) {
                SectionInnerPanel panel = (SectionInnerPanel) component;
                panel.linkButtonPressed(ddBean, ddProperty);
            }
        }
    }

    public void setComponents(Component[] components) {
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            if (i >= getComponentCount() || component != getComponent(i)) {
                add(component, i);
            }
        }
        int n = components.length;
        while (getComponentCount() > n) {
            remove(n);
        }
    }
    /** This will be called after model is changed from this panel
     */
    protected void signalUIChange() {
    }
}
