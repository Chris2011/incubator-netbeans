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

package org.netbeans.modules.j2ee.ddloaders.multiview;

import org.netbeans.modules.j2ee.dd.api.ejb.Entity;
import org.netbeans.modules.xml.multiview.SectionNode;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionNodeView;
import org.openide.filesystems.FileObject;

/**
 * @author pfiala
 */
class SelectMethodsNode extends SectionNode {

    private EntityHelper entityHelper;

    SelectMethodsNode(SectionNodeView sectionNodeView, Entity entity, EntityHelper entityHelper) {
        super(sectionNodeView, true, entity, Utils.getBundleMessage("LBL_CmpSelects"), Utils.ICON_BASE_MISC_NODE);
        this.entityHelper = entityHelper;
    }

    protected SectionInnerPanel createNodeInnerPanel() {
        Entity entity = (Entity) key;
        final FileObject ejbJarFile = getSectionNodeView().getDataObject().getPrimaryFile();
        InnerTablePanel innerTablePanel = new InnerTablePanel(getSectionNodeView(),
                new SelectMethodsTableModel(ejbJarFile, entity, entityHelper));
        innerTablePanel.getEditButton().setVisible(false);
        innerTablePanel.getRemoveButton().setVisible(false);
        return innerTablePanel;

    }
}
