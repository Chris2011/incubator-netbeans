/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */

package org.netbeans.modules.bugtracking.vcs;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.netbeans.modules.bugtracking.TestIssue;
import org.netbeans.modules.bugtracking.spi.BugtrackingController;
import org.netbeans.modules.bugtracking.spi.IssueStatusProvider;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;

/**
 *
 * @author tomas
 */
public class HookIssue extends TestIssue {
    static HookIssue instance;

    boolean closed;
    String comment;
    private BugtrackingController controller;

    static HookIssue getInstance() {
        if(instance == null) {
            instance = new HookIssue();
        }
        return instance;
    }

    void reset() {
        comment = null;
        closed = false;
    }
    @Override
    public String getDisplayName() {
        return "HookIssue";
    }

    @Override
    public String getTooltip() {
        return "HookIssue";
    }

    @Override
    public String getID() {
        return "1";
    }

    @Override
    public String getSummary() {
        return "HookIssue";
    }

    @Override
    public boolean isNew() {
        return false;
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean refresh() {
        return true;
    }

    @Override
    public void addComment(String comment, boolean closeAsFixed) {
        this.comment = comment;
        closed = closeAsFixed;
    }

    @Override
    public void attachPatch(File file, String description) {
        // do nothing
    }

    @Override
    public BugtrackingController getController() {
        if(controller == null) {        
            controller = new BugtrackingController() {
                private JComponent panel = new JPanel();                
                @Override
                public JComponent getComponent() {
                    return panel;
                }
                @Override
                public HelpCtx getHelpCtx() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
                @Override
                public boolean isValid() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
                @Override
                public void applyChanges() throws IOException {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        }
        return controller;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) { }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { }

    // NB-Core-Build #9753: Still Failing - @Override
    public TestIssue createFor(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String[] getSubtasks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }    

    @Override
    public IssueStatusProvider.Status getStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSeen(boolean seen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
