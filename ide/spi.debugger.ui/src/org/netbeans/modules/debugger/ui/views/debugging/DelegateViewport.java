/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.debugger.ui.views.debugging;

import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JViewport;

/**
 * Delegate scrolling from this viewport to the parent viewport.
 * 
 * @author Martin
 */
public class DelegateViewport extends JViewport {

    @Override
    public void scrollRectToVisible(Rectangle contentRect) {
        Container parent;
        int dx = getX(), dy = getY();

        for (parent = getParent();
                 !(parent == null) &&
                 !(parent instanceof JComponent) &&
                 !(parent instanceof CellRendererPane);
             parent = parent.getParent()) {
             Rectangle bounds = parent.getBounds();

             dx += bounds.x;
             dy += bounds.y;
        }

        if (!(parent == null) && !(parent instanceof CellRendererPane)) {
            contentRect.x += dx;
            contentRect.y += dy;

            ((JComponent) parent).scrollRectToVisible(contentRect);
            contentRect.x -= dx;
            contentRect.y -= dy;
        }
        
    }
    
}
