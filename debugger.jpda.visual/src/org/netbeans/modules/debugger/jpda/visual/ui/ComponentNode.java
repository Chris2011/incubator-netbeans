/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
package org.netbeans.modules.debugger.jpda.visual.ui;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import org.netbeans.modules.debugger.jpda.visual.RemoteScreenshot.ComponentInfo;
import org.netbeans.modules.debugger.jpda.visual.actions.GoToSourceAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;

/**
 * The node representation of a remote component.
 * 
 * @author Martin Entlicher
 */
public class ComponentNode extends AbstractNode {
    
    private ComponentInfo ci;
    
    public ComponentNode(ComponentInfo ci) {
        super(ci.getSubComponents().length > 0 ? new ComponentChildren(ci) : Children.LEAF,
              Lookups.singleton(ci));
        this.ci = ci;
        ci.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
            }
        });
        
    }

    @Override
    public String getDisplayName() {
        String type = ci.getType();
        int d = type.lastIndexOf('.');
        if (d > 0) {
            type = type.substring(d + 1);
        }
        return "["+type+"]";//ci.getName();
    }

    @Override
    public SystemAction[] getActions() {
        //return super.getActions();
        return new SystemAction[] { GoToSourceAction.get(GoToSourceAction.class) };
    }

    @Override
    public PropertySet[] getPropertySets() {
        return ci.getPropertySets();
        /*
        return new PropertySet[] { new PropertySet("main", "Main", "The main properties") {

            @Override
            public Property<?>[] getProperties() {
                return new Property[] {
                    new Property(String.class) {

                        @Override
                        public String getName() {
                            return "name";
                        }

                        @Override
                        public String getDisplayName() {
                            return "Component Name";
                        }

                        @Override
                        public boolean canRead() {
                            return true;
                        }

                        @Override
                        public Object getValue() throws IllegalAccessException, InvocationTargetException {
                            return ci.getName();
                        }

                        @Override
                        public boolean canWrite() {
                            return false;
                        }

                        @Override
                        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            throw new UnsupportedOperationException("Not supported.");
                        }
                    },
                    new Property(String.class) {

                        @Override
                        public String getName() {
                            return "type";
                        }

                        @Override
                        public String getDisplayName() {
                            return "Component Type";
                        }

                        @Override
                        public boolean canRead() {
                            return true;
                        }

                        @Override
                        public Object getValue() throws IllegalAccessException, InvocationTargetException {
                            return ci.getType();
                        }

                        @Override
                        public boolean canWrite() {
                            return false;
                        }

                        @Override
                        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            throw new UnsupportedOperationException("Not supported.");
                        }
                    },
                    new Property(String.class) {

                        @Override
                        public String getName() {
                            return "bounds";
                        }

                        @Override
                        public String getDisplayName() {
                            return "Component Bounds";
                        }

                        @Override
                        public boolean canRead() {
                            return true;
                        }

                        @Override
                        public Object getValue() throws IllegalAccessException, InvocationTargetException {
                            Rectangle r = ci.getWindowBounds();
                            return "[x=" + r.x + ",y=" + r.y + ",width=" + r.width + ",height=" + r.height + "]";
                        }

                        @Override
                        public boolean canWrite() {
                            return false;
                        }

                        @Override
                        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            throw new UnsupportedOperationException("Not supported.");
                        }
                    },
                };
            }
        }};
         */
    }
    
    ComponentNode findNodeFor(ComponentInfo ci) {
        if (ci.equals(this.ci)) {
            return this;
        }
        Children ch = getChildren();
        Node[] subNodes = ch.getNodes();
        for (Node n : subNodes) {
            ComponentNode cn = (ComponentNode) n;
            ComponentNode node = cn.findNodeFor(ci);
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString()+", node's ci = "+ci;
    }
    
    private static class ComponentChildren extends Children.Keys<ComponentInfo> {
        
        private ComponentInfo ci;
        
        public ComponentChildren(ComponentInfo ci) {
            this.ci = ci;
        }

        @Override
        protected void addNotify() {
            super.addNotify();
            setKeys(ci.getSubComponents());
        }
        
        @Override
        protected Node[] createNodes(ComponentInfo key) {
            return new Node[] { new ComponentNode(key) };
        }
    }
}
