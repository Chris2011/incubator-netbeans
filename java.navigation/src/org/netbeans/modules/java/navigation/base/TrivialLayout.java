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
/*
 * TrivialLayout.java
 *
 * Created on 20. srpen 2003, 18:33
 */

package org.netbeans.modules.java.navigation.base;

import java.awt.*;

/**
 * Trivial layout manager class used by the panels for selecting look and filter.  Simply uses the preferred size of the
 * first compnent and fills the rest of the space with the second, to the height of the tallest.
 *
 * @author Tim Boudreau
 */
final class TrivialLayout implements LayoutManager {
    public void addLayoutComponent (String name, Component comp) {
        //do nothing
    }

    public void removeLayoutComponent (Component comp) {
        //do nothing
    }

    public void layoutContainer (Container parent) {
        if ( parent instanceof TapPanel ) {
            layoutTapPanel ( (TapPanel) parent );
        } else {
            layoutComp ( parent );
        }
    }

    /**
     * Standard layout for any container
     */
    private void layoutComp (Container parent) {
        Component[] c = parent.getComponents ();
        if ( c.length > 0 ) {
            c[ 0 ].setBounds ( 0, 0, parent.getWidth (), parent.getHeight () );
        }
    }

    /**
     * Layout for TapPanel, taking into account its minimumHeight
     */
    private void layoutTapPanel (TapPanel tp) {
        Component[] c = tp.getComponents ();
        if ( c.length > 0 ) {
            Dimension d2 = c[ 0 ].getPreferredSize ();
            if ( tp.isExpanded () ) {
                int top = tp.getOrientation () == tp.UP ? 0 : tp.getMinimumHeight ();
                int height = Math.min ( tp.getHeight () - tp.getMinimumHeight (), d2.height );
                c[ 0 ].setBounds ( 0, top, tp.getWidth (), height );
            } else {
                c[ 0 ].setBounds ( 0, 0, 0, 0 );
            }
        }
    }


    public Dimension minimumLayoutSize (Container parent) {
        Dimension result = new Dimension ( 20, 10 );
        Component[] c = parent.getComponents ();
        TapPanel tp = (TapPanel) parent;
        if ( c.length > 0 ) {
            Dimension d2 = c[ 0 ].getPreferredSize ();
            result.width = d2.width;
            result.height = tp.isExpanded () ? d2.height + tp.getMinimumHeight () : tp.getMinimumHeight ();
        }
        return result;
    }

    public Dimension preferredLayoutSize (Container parent) {
        return minimumLayoutSize ( parent );
    }
}

