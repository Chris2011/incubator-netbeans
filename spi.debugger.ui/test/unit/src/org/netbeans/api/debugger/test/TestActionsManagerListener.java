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

package org.netbeans.api.debugger.test;

import org.netbeans.api.debugger.ActionsManagerListener;

import java.util.*;

/**
 * A test actions manager listener.
 * 
 * @author Maros Sandor
 */
public class TestActionsManagerListener implements ActionsManagerListener {

    private List performed = new ArrayList();

    public void actionPerformed(Object action) {
        performed.add(action);
    }

    public void actionStateChanged(Object action, boolean enabled) {
    }

    public List getPerformedActions() {
        List listCopy = new ArrayList(performed);
        performed.clear();
        return listCopy;
    }
}
