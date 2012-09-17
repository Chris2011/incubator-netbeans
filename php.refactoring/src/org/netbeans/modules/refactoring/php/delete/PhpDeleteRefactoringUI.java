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
package org.netbeans.modules.refactoring.php.delete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.refactoring.api.AbstractRefactoring;
import org.netbeans.modules.refactoring.api.Problem;
import org.netbeans.modules.refactoring.api.SafeDeleteRefactoring;
import org.netbeans.modules.refactoring.spi.ui.CustomRefactoringPanel;
import org.netbeans.modules.refactoring.spi.ui.RefactoringUI;
import org.netbeans.modules.refactoring.spi.ui.RefactoringUIBypass;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Radek Matous
 */
public class PhpDeleteRefactoringUI implements RefactoringUI, RefactoringUIBypass {
    private static final RequestProcessor RP = new RequestProcessor(PhpDeleteRefactoringUI.class);
    private static final Logger LOGGER = Logger.getLogger(PhpDeleteRefactoringUI.class.getName());
    private final SafeDeleteRefactoring refactoring;
    private final boolean regulardelete;
    private final FileObject file;
    private SafeDeletePanel panel;

    public PhpDeleteRefactoringUI(SafeDeleteSupport support, boolean regularDelete) {
        Collection<Object> lookupContent = new ArrayList<Object>();
        lookupContent.add(support);
        this.refactoring = new SafeDeleteRefactoring(new ProxyLookup(Lookups.fixed(support.getFile()), Lookups.fixed(lookupContent.toArray())));
        this.file = support.getFile();
        this.regulardelete = regularDelete;
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(PhpDeleteRefactoringUI.class, "LBL_SafeDel"); //NOI18N
    }

    @Override
    public String getDescription() {
        return NbBundle.getMessage(PhpDeleteRefactoringUI.class, "LBL_SafeDel_Descr"); //NOI18N
    }

    @Override
    public boolean isQuery() {
        return false;
    }

    @Override
    public CustomRefactoringPanel getPanel(ChangeListener parent) {
        if (panel == null) {
            panel = new SafeDeletePanel(refactoring, regulardelete, parent);
        }

        return panel;
    }

    @Override
    public Problem setParameters() {
        return refactoring.checkParameters();
    }

    @Override
    public Problem checkParameters() {
        return refactoring.checkParameters();
    }

    @Override
    public boolean hasParameters() {
        return true;
    }

    @Override
    public AbstractRefactoring getRefactoring() {
        return this.refactoring;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("org.netbeans.modules.refactoring.php.delete.PhpDeleteRefactoringUI"); //NOI18N
    }

    @Override
    public boolean isRefactoringBypassRequired() {
        return panel.isRegularDelete();
    }

    @Override
    public void doRefactoringBypass() throws IOException {
        RP.post(new Runnable() {

            @Override
            public void run() {
                try {
                    // #172199
                    FileUtil.runAtomicAction(new FileSystem.AtomicAction() {
                        @Override
                        public void run() throws IOException {
                            file.delete();
                        }
                    });
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }

        });
    }
}
