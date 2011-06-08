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
package org.netbeans.modules.cnd.makeproject.ui;

import java.util.List;
import javax.swing.AbstractAction;
import org.netbeans.api.project.Project;
import org.netbeans.modules.cnd.makeproject.ui.BrokenLinks.BrokenLink;
import org.netbeans.modules.cnd.makeproject.ui.BrokenLinks.Solution;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

/**
 *
 * @author Alexander Simon
 */
public class ResolveReferenceAction extends AbstractAction {
    private Project project;
    
    /** Creates a new instance of BrowserAction */
    public ResolveReferenceAction(Project project) {
        super(NbBundle.getBundle(ResolveReferenceAction.class).getString("CTL_ResolveReferenceAction"), // NOI18N
               null );
	this.project = project;
    }
    
        
    @Override
    public void actionPerformed(java.awt.event.ActionEvent ev) {
        List<BrokenLink> brokenLinks = BrokenLinks.getBrokenLinks(project); // NOI18N
        StringBuilder buf = new StringBuilder();
        for(BrokenLink error : brokenLinks) {
            buf.append(NbBundle.getBundle(ResolveReferenceAction.class).getString("Link_Problem_Text")); // NOI18N
            buf.append('\n'); // NOI18N
            buf.append(error.getProblem());
            buf.append('\n'); // NOI18N
            buf.append(NbBundle.getBundle(ResolveReferenceAction.class).getString("Link_Solution_Text")); // NOI18N
            int i = 1;
            for(Solution solution : error.getSolutions()) {
                buf.append('\n'); // NOI18N
                buf.append(i);
                buf.append(". "); // NOI18N
                buf.append(solution.getDescription());
                i++;
            }
        }
        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(buf.toString(), NotifyDescriptor.ERROR_MESSAGE));
        return;
    }

}