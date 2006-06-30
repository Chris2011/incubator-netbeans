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
package org.netbeans.tax;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This tree object own a list of its children accessible by getChildNodes().
 *
 * @author  Libor Kramolis
 * @version 0.1
 */
public abstract class TreeParentNode extends TreeChild {

    /** */
    public static final String PROP_CHILD_LIST = "childList"; // NOI18N

    /** */
    private TreeObjectList childList;


    //
    // init
    //

    /** Creates new TreeParentNode. */
    protected TreeParentNode () {
        super ();
        
        this.childList = new TreeObjectList (createChildListContentManager ());
    }
    
    /** Creates new TreeParentNode -- copy constructor. */
    protected TreeParentNode (TreeParentNode parentNode, boolean deep) {
        super (parentNode);
        
        this.childList = new TreeObjectList (createChildListContentManager ());
        if (deep) {
            this.childList.addAll ((TreeObjectList)parentNode.childList.clone ());
        }
    }
    
    
    //
    // from TreeObject
    //
    
    /** Clone depply tree object.
     * @return deep clone of this node
     */
    public abstract Object clone (boolean deep);
    
    
    /** Call clone (true).
     * @return deep clone of this node.
     */
    public final Object clone () {
        return clone (true);
    }
    
    /**
     */
    public boolean equals (Object object, boolean deep) {
        if (!!! super.equals (object, deep))
            return false;
        
        TreeParentNode peer = (TreeParentNode) object;
        if (!!! Util.equals (this.childList, peer.childList)) {
            return false;
        }
        
        return true;
    }
    
    /*
     * Merges childlist.
     */
    public void merge (TreeObject treeObject) throws CannotMergeException {
        super.merge (treeObject);
        
        TreeParentNode peer = (TreeParentNode) treeObject;
        
        childList.merge (peer.childList);
    }
    
    
    //
    // itself
    //
    
    /**
     */
    public boolean isAssignableChild (TreeChild child) {
        return childList.isAssignableObject (child);
    }
    
    /**
     * @return <b>reference</b> to kids
     */
    public final TreeObjectList getChildNodes () {
        return childList;
    }
    
    
    //
    // read only
    //
    
    
    /**
     */
    protected void setReadOnly (boolean newReadOnly) {
        super.setReadOnly (newReadOnly);
        
        childList.setReadOnly (newReadOnly);
    }
    
    
    //
    // Children manipulation
    //
    
    /**
     */
    public final TreeChild getFirstChild () {
        if ( childList.size () == 0 ) {
            return null;
        }
        return (TreeChild)childList.get (0);
    }
    
    /**
     */
    public final TreeChild getLastChild () {
        if ( childList.size () == 0 ) {
            return null;
        }
        return (TreeChild)childList.get (childList.size () - 1);
    }
    
    
    /**
     * @throws ReadOnlyException
     */
    public final void insertBefore (TreeChild newChild, TreeChild refChild) throws ReadOnlyException {
        /*
        if (refChild == null) {
            // For semantic compatibility with DOM.
            appendChild(newChild);
            return;
        }
         */
        childList.checkReadOnly ();
        int index = childList.indexOf (refChild);
        if (index < 0) {
            return;
        }
        childList.add (index, newChild);
    }
    
    /**
     * @throws ReadOnlyException
     */
    public final void replaceChild (TreeChild oldChild, TreeChild newChild) throws ReadOnlyException {
        if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug ("\nTreeParentNode::replaceChild: oldChild = " + oldChild); // NOI18N
        if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug ("              ::replaceChild: newChild = " + newChild); // NOI18N
        
        childList.checkReadOnly ();
        int index = childList.indexOf (oldChild);
        
        if ( Util.THIS.isLoggable() ) /* then */ Util.THIS.debug ("              ::replaceChild: childList [oldChild]  = " + index); // NOI18N
        
        if (index < 0) {
            return;
        }
        childList.set (index, newChild);
    }
    
    /**
     * @throws ReadOnlyException
     */
    public final void removeChild (TreeChild oldChild) throws ReadOnlyException {
        childList.checkReadOnly ();
        childList.remove (oldChild);
    }
    
    /**
     * @throws ReadOnlyException
     */
    public final void appendChild (TreeChild newChild) throws ReadOnlyException {
        childList.checkReadOnly ();
        childList.add (newChild);
    }
    
    
    /**
     * Insert child at specified position and set its parent and owner document.
     * @throws ReadOnlyException
     */
    public final void insertChildAt (TreeChild child, int index) throws ReadOnlyException {
        childList.checkReadOnly ();
        childList.add (index, child);
    }
    
    
    /**
     */
    public final int indexOf (TreeChild node) {
        return childList.indexOf (node);
    }
    
    
    /**
     */
    public final TreeChild item (int index) {
        return (TreeChild)childList.get (index);
    }
    
    /**
     */
    public final int getChildrenNumber () {
        return (childList.size ());
    }
    
    /**
     */
    public final boolean hasChildNodes () {
        return (!!! childList.isEmpty ());
    }
    
    /**
     */
    public final boolean hasChildNodes (Class childClass) {
        return hasChildNodes (childClass, false);
    }
    
    /**
     */
    public boolean hasChildNodes (Class childClass, boolean recursive) {
        Iterator it = getChildNodes ().iterator ();
        while (it.hasNext ()) {
            TreeChild child = (TreeChild)it.next ();
            
            // add matching leaf node
            
            if (childClass == null || childClass.isAssignableFrom (child.getClass ())) {
                return true;
            }
            
            // do recursive descent into kids
            
            if ( recursive && (child instanceof TreeParentNode) ) {
                if ( ((TreeParentNode)child).hasChildNodes (childClass, true) == true ) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * @return copy collection containing references
     */
    public final Collection getChildNodes (Class childClass) {
        return getChildNodes (childClass, false);
    }
    
    /**
     * @return copy collection containing references
     */
    public Collection getChildNodes (Class childClass, boolean recursive) {
        
        //        new RuntimeException(getClass().toString() + ".getChildNodes(" + childClass.toString() + "," + recursive + ")").printStackTrace(); // NOI18N
        
        Collection allChildNodes = new LinkedList ();
        Iterator it = getChildNodes ().iterator ();
        while (it.hasNext ()) {
            TreeChild child = (TreeChild)it.next ();
            
            // add matching leaf node
            
            if (childClass == null || childClass.isAssignableFrom (child.getClass ())) {
                allChildNodes.add (child);
            }
            
            // do recursive descent into kids
            
            if ( recursive && (child instanceof TreeParentNode) ) {
                allChildNodes.addAll (((TreeParentNode)child).getChildNodes (childClass, true));
            }
        }
        return allChildNodes;
    }
    
    
    //
    // TreeObjectList.ContentManager
    //
    
    /**
     */
    protected abstract TreeObjectList.ContentManager createChildListContentManager ();
    
    /**
     *
     */
    protected abstract class ChildListContentManager extends TreeObjectList.ContentManager {
        
        /**
         */
        public void checkAssignableObject (Object obj) {
            super.checkAssignableObject (obj);
            checkAssignableClass (TreeChild.class, obj);
        }
        
        /**
         */
        public void objectInserted (TreeObject obj) {
            ((TreeChild)obj).setParentNode (TreeParentNode.this);
            TreeParentNode.this.firePropertyChange (TreeParentNode.PROP_CHILD_LIST, TreeParentNode.this.childList, obj);
        }
        
        /**
         */
        public void objectRemoved (TreeObject obj) {
            ((TreeChild)obj).setParentNode (null);
            TreeParentNode.this.firePropertyChange (TreeParentNode.PROP_CHILD_LIST, TreeParentNode.this.childList, obj);
        }
        
        /**
         */
        public void orderChanged (int[] permutation) {
            TreeParentNode.this.firePropertyChange (TreeParentNode.PROP_CHILD_LIST, TreeParentNode.this.childList, permutation);
        }
        
    } // end: class ChildListContentManager
    
}
