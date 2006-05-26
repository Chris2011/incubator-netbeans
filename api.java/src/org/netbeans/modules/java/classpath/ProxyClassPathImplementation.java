/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2003 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.java.classpath;

import org.netbeans.spi.java.classpath.ClassPathImplementation;
import org.netbeans.spi.java.classpath.PathResourceImplementation;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import org.openide.util.WeakListeners;

/** ProxyClassPathImplementation provides read only proxy for ClassPathImplementations.
 *  The order of the resources is given by the order of its delegates.
 *  The proxy is designed to be used as a union of class paths.
 *  E.g. to be able to easily iterate or listen on all design resources = sources + compile resources
 */
public class ProxyClassPathImplementation implements ClassPathImplementation {

    private ClassPathImplementation[] classPaths;
    private List<PathResourceImplementation> resourcesCache;
    private ArrayList<PropertyChangeListener> listeners;
    private PropertyChangeListener classPathsListener;

    public ProxyClassPathImplementation (ClassPathImplementation[] classPaths) {
        if (classPaths == null)
            throw new IllegalArgumentException ();
        List<ClassPathImplementation> impls = new ArrayList<ClassPathImplementation> ();
        classPathsListener = new DelegatesListener ();
        for (ClassPathImplementation cpImpl : classPaths) {
            if (cpImpl == null)
                continue;
            cpImpl.addPropertyChangeListener (WeakListeners.propertyChange(classPathsListener,cpImpl));
            impls.add (cpImpl);
        }
        this.classPaths = impls.toArray(new ClassPathImplementation[impls.size()]);
    }



    public synchronized List <? extends PathResourceImplementation> getResources() {
        if (this.resourcesCache == null) {
            ArrayList<PathResourceImplementation> result = new ArrayList<PathResourceImplementation> (classPaths.length*10);
            for (ClassPathImplementation cpImpl : classPaths) {
                List<? extends PathResourceImplementation> subPath = cpImpl.getResources();
                assert subPath != null : "ClassPathImplementation.getResources() returned null. ClassPathImplementation.class: " 
                       + cpImpl.getClass().toString() + " ClassPathImplementation: " + cpImpl.toString();
                result.addAll (subPath);
            }
            resourcesCache = Collections.unmodifiableList (result);
        }
        return this.resourcesCache;
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        if (this.listeners == null)
            this.listeners = new ArrayList<PropertyChangeListener> ();
        this.listeners.add (listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        if (this.listeners == null)
            return;
        this.listeners.remove (listener);
    }
    
    public String toString () {
        StringBuilder builder = new StringBuilder("[");   //NOI18N
        for (ClassPathImplementation cpImpl : this.classPaths) {
            builder.append (cpImpl.toString());
            builder.append(", ");   //NOI18N
        }
        builder.append ("]");   //NOI18N
        return builder.toString ();
    }


    private class DelegatesListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            PropertyChangeListener[] _listeners;
            synchronized (ProxyClassPathImplementation.this) {
                ProxyClassPathImplementation.this.resourcesCache = null;    //Clean the cache
                if (ProxyClassPathImplementation.this.listeners == null)
                    return;
                _listeners = ProxyClassPathImplementation.this.listeners.toArray(new PropertyChangeListener[ProxyClassPathImplementation.this.listeners.size()]);
            }
            PropertyChangeEvent event = new PropertyChangeEvent (ProxyClassPathImplementation.this, evt.getPropertyName(),null,null);
            for (PropertyChangeListener l : _listeners) {
                l.propertyChange (event);
            }
        }
    }

}
