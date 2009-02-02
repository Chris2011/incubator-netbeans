/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.netbeans.api.debugger.providers;

import java.util.HashSet;
import java.util.Set;
import org.netbeans.spi.debugger.DebuggerServiceRegistration;
import org.netbeans.spi.viewmodel.ModelListener;
import org.netbeans.spi.viewmodel.NodeModel;
import org.netbeans.spi.viewmodel.TableModel;
import org.netbeans.spi.viewmodel.TreeModel;
import org.netbeans.spi.viewmodel.UnknownTypeException;

/**
 *
 * @author Martin Entlicher
 */
@DebuggerServiceRegistration(path="unittest/annotated", types={TreeModel.class,NodeModel.class, TableModel.class})
public class TestThreeModels implements TreeModel, NodeModel, TableModel {
    
    public static Set<TestThreeModels> INSTANCES = new HashSet<TestThreeModels>();

    public TestThreeModels() {
        INSTANCES.add(this);
    }

    public Object getRoot() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] getChildren(Object parent, int from, int to) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLeaf(Object node) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getChildrenCount(Object node) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addModelListener(ModelListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeModelListener(ModelListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDisplayName(Object node) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getIconBase(Object node) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getShortDescription(Object node) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getValueAt(Object node, String columnID) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isReadOnly(Object node, String columnID) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValueAt(Object node, String columnID, Object value) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
