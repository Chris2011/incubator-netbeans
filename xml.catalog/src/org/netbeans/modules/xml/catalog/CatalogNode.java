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

package org.netbeans.modules.xml.catalog;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.Action;
import org.netbeans.modules.xml.catalog.settings.CatalogSettings;
import org.netbeans.modules.xml.catalog.spi.CatalogDescriptor;
import org.netbeans.modules.xml.catalog.spi.CatalogListener;
import org.netbeans.modules.xml.catalog.spi.CatalogReader;
import org.netbeans.modules.xml.catalog.spi.CatalogWriter;
import org.openide.actions.PropertiesAction;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.WeakListeners;
import org.openide.util.actions.NodeAction;
import org.openide.util.actions.SystemAction;

/**
 * Node representing a catalog.
 * Every catalog reader is considered to be a bean.
 * Information about catalog instance are obtained using CatalogDescriptor interface
 * if passed instance implements it.
 *
 * @author  Petr Kuzel
 * @version 1.0
 */
final class CatalogNode extends BeanNode implements Refreshable, PropertyChangeListener, Node.Cookie {
    
    private CatalogReader catalog;
    /** Creates new CatalogNode */
    public CatalogNode(CatalogReader catalog) throws IntrospectionException {        
        super(catalog, new CatalogChildren(catalog));
        this.catalog=catalog;
        getCookieSet().add(this);
        
        if (catalog instanceof CatalogDescriptor) {
            
            // set node properties acording to descriptor
            
            CatalogDescriptor desc = (CatalogDescriptor) catalog;            
            setSynchronizeName(false);
            setName(desc.getDisplayName());
            String bundleString = catalog instanceof CatalogWriter ?"LBL_catalogReadWrite":"LBL_catalogReadOnly"; //NOI18N
            setDisplayName(Util.THIS.getString(bundleString, desc.getDisplayName()));
            setShortDescription(desc.getShortDescription());
            fireIconChange();  

            // listen on it
            
            desc.addPropertyChangeListener(WeakListeners.propertyChange(this, desc));
        }
    }
    
    CatalogReader getCatalogReader() {
        return catalog;
    }

    public Action[] getActions(boolean context) {
        if (catalog instanceof CatalogWriter)
            return new Action[] {
                SystemAction.get(AddCatalogEntryAction.class),
                SystemAction.get(RefreshAction.class),
                SystemAction.get(CatalogNode.UnmountAction.class),
                null,
                //??? #24349 CustimizeAction sometimes added by BeanNode here
                SystemAction.get(PropertiesAction.class)
            };
        else
            return new Action[] {
                SystemAction.get(RefreshAction.class),
                SystemAction.get(CatalogNode.UnmountAction.class),
                null,
                //??? #24349 CustimizeAction sometimes added by BeanNode here
                SystemAction.get(PropertiesAction.class)
            };
    }

    /**
     * @return icon regurned by CatalogDescriptor if instance of it
     */
    public Image getIcon(int type) {
        if (catalog instanceof CatalogDescriptor) {
            Image icon = ((CatalogDescriptor)catalog).getIcon(type);
            if (icon != null) return icon;
        }
        
        return super.getIcon(type);        
    }
    
    public HelpCtx getHelpCtx() {
        //return new HelpCtx(CatalogNode.class);
        return HelpCtx.DEFAULT_HELP;
    }
    
    /**
     * Refresh catalog provider and then refresh children.
     */
    public void refresh() {
        catalog.refresh();
        ((CatalogChildren)getChildren()).reload();  // may be double reload
    }

    /** This node cannot be destroyed, just unmount.
     * @return always <CODE>false</CODE>
     */
    public boolean canDestroy () {
        return false;
    }
    
    public boolean canRename() {
        return false;
    }

    /**
     * Remove itseld from CatalogSettings,
     */
    public void destroy() throws IOException {
        CatalogSettings mounted = CatalogSettings.getDefault();
        mounted.removeCatalog(catalog);
        super.destroy();
    }

    /**
     * The node listens on some changes
     */
    public void propertyChange(PropertyChangeEvent e) {
        if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug(e.toString());
        if (CatalogDescriptor.PROP_CATALOG_NAME.equals(e.getPropertyName())) {
            if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug(" Setting name: " + (String) e.getNewValue()); // NOI18N

            setName((String) e.getNewValue());
            setDisplayName((String) e.getNewValue());
        } else if (CatalogDescriptor.PROP_CATALOG_DESC.equals(e.getPropertyName())) {
            if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug(" Setting desc: " + (String) e.getNewValue()); // NOI18N

            setShortDescription((String) e.getNewValue());
        } else if (CatalogDescriptor.PROP_CATALOG_ICON.equals(e.getPropertyName())) { 
            if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug(" Updating icon"); // NOI18N

            fireIconChange();
        }
    }
    
    
    // ~~~~~~~~~~~~~~~~~~~~~~ Serialization stuff ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug("Reading Catalog node " + this); // NOI18N

        in.defaultReadObject();        
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug("Writing " + this); // NOI18N

        out.defaultWriteObject();        
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Kids have to listen at Catalog
     */
    public static class CatalogChildren extends Children.Keys {
        
        private CatalogReader peer;
        private CatalogListener catalogListener;
        
        public CatalogChildren(CatalogReader catalog) {
            peer = catalog;
            
        }
                
        /** Contains public ID (String) instances. */
        private final TreeSet keys = new TreeSet();
        
        public void addNotify() {            
            catalogListener = new Lis();
            try {
                peer.addCatalogListener(catalogListener);
            } catch (UnsupportedOperationException ex) {
                // User must use explicit refresh
            }            
            reload();
        }

        public void removeNotify() {
            try {
                peer.removeCatalogListener(catalogListener);
            } catch (UnsupportedOperationException ex) {
                // does not matter
            }
            keys.clear();
            setKeys(keys);
        }
        
        public Node[] createNodes(Object key) {        
            try {
                CatalogEntry catalogEntry = new CatalogEntry((String) key, peer);
                return new Node[] { 
                    new CatalogEntryNode(catalogEntry)
                };
            } catch (IntrospectionException ex) {
                return null;
            }
        }

        /**
          * Reloads catalog content
          */
        public void reload() {
            if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug(" Reloading kids of " + peer + "..."); // NOI18N

            Set previous = new HashSet(keys);
            keys.clear();
            Iterator it = peer.getPublicIDs();
            if (it != null) {
                while (it.hasNext()) {
                    String publicID = (String) it.next();
                    keys.add(publicID);
                    if (previous.contains(publicID)) {
                        refreshKey(publicID);  // recreate node, the systemId may have changed
                    }
                }
            }    
            setKeys(keys);
        }
        
        private class Lis implements CatalogListener {
            
            /** Given public ID has changed - created.  */
            public void notifyNew(String publicID) {
                keys.add(publicID);
                setKeys(keys);
            }
            
            /** Given public ID has changed - disappeared.  */
            public void notifyRemoved(String publicID) {
                keys.remove(publicID);
                setKeys(keys);
            }
            
            /** Given public ID has changed.  */
            public void notifyUpdate(String publicID) {
                refreshKey(publicID);
            }
            
            /** All entries are invalidated.  */
            public void notifyInvalidate() {
                reload();
            }
            
        }
        
    }

    /**
     * Give to the action your own name
     */
    private static final class UnmountAction extends NodeAction {
        /** Serial Version UID */
        private static final long serialVersionUID = 3556006276357785484L;
        
        public UnmountAction() {
        }
        
        public String getName() {
            return Util.THIS.getString("LBL_unmount");
        }
        
        public HelpCtx getHelpCtx() {
            return new HelpCtx(UnmountAction.class);
        }
        
        protected boolean enable(Node[] activatedNodes) {
            if (activatedNodes.length > 0) {
                for (int i = 0; i<activatedNodes.length; i++) {
                    Node me = activatedNodes[i];
                    Object self = me.getCookie(CatalogNode.class);
                    if (self instanceof CatalogNode) {
                        CatalogReader reader = (CatalogReader) ((CatalogNode)self).getBean();
                        if (CatalogSettings.getDefault().isRemovable(reader)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
        protected void performAction(Node[] activatedNodes) {
            if (enable(activatedNodes) == false) return;
            for (int i = 0; i<activatedNodes.length; i++) {
                try {
                    Node me = activatedNodes[i];
                    CatalogNode self = (CatalogNode) me.getCookie(CatalogNode.class);
                    self.destroy();
                } catch (IOException ex) {
                    Util.THIS.debug("Cannot unmount XML entity catalog!", ex);
                }
            }
        }
        
        protected boolean asynchronous() {
            return false;
        }
        
    }
    
}
