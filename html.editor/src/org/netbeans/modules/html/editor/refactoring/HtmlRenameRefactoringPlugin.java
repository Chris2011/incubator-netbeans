/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */
package org.netbeans.modules.html.editor.refactoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Position.Bias;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.csl.spi.GsfUtilities;
import org.netbeans.modules.csl.spi.support.ModificationResult;
import org.netbeans.modules.csl.spi.support.ModificationResult.Difference;
import org.netbeans.modules.html.editor.indexing.HtmlFileModel;
import org.netbeans.modules.html.editor.indexing.HtmlFileModel.Entry;
import org.netbeans.modules.web.common.api.DependenciesGraph;
import org.netbeans.modules.web.common.api.DependenciesGraph.Node;
import org.netbeans.modules.html.editor.indexing.HtmlIndex;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.refactoring.api.Problem;
import org.netbeans.modules.refactoring.api.RenameRefactoring;
import org.netbeans.modules.refactoring.spi.RefactoringElementsBag;
import org.netbeans.modules.refactoring.spi.RefactoringPlugin;
import org.netbeans.modules.web.common.api.WebUtils;
import org.openide.filesystems.FileObject;
import org.openide.text.CloneableEditorSupport;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author marekfukala
 */
public class HtmlRenameRefactoringPlugin implements RefactoringPlugin {

    private static final Logger LOGGER = Logger.getLogger(HtmlRenameRefactoringPlugin.class.getSimpleName());
    private static final boolean LOG = LOGGER.isLoggable(Level.FINE);
    private RenameRefactoring refactoring;

    public HtmlRenameRefactoringPlugin(RenameRefactoring refactoring) {
        this.refactoring = refactoring;
    }

    @Override
    public Problem preCheck() {
        return null;
    }

    @Override
    public Problem checkParameters() {
        return null;
    }

    @Override
    public Problem fastCheckParameters() {
        return null;
    }

    @Override
    public void cancelRequest() {
        //no-op
    }

    @Override
    public Problem prepare(final RefactoringElementsBag refactoringElements) {
        //rename refactoring of files refered from html pages
        Lookup lookup = refactoring.getRefactoringSource();
        FileObject file = lookup.lookup(FileObject.class);
        if (file == null) {
            return null;
        }
        Project project = FileOwnerQuery.getOwner(file);
        if (project == null) {
            return null;
        }
        HtmlIndex index;
        try {
            index = HtmlIndex.get(project);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }

        //refactor a file in explorer
        LOGGER.fine("refactor file " + file.getPath()); //NOI18N
        String newName = refactoring.getNewName();

        //a. get all importing files
        //b. rename the references
        //c. rename the file itself - done via default rename plugin
        DependenciesGraph deps = index.getDependencies(file);
        Collection<Node> allRefering = deps.getSourceNode().getReferingNodes();
        ModificationResult modificationResult = new ModificationResult();
        for (Node ref : allRefering) {
            FileObject refering = ref.getFile();
            try {
                Source source;
                CloneableEditorSupport editor = GsfUtilities.findCloneableEditorSupport(refering);
                //prefer using editor
                //XXX this approach doesn't match the dependencies graph
                //which is made strictly upon the index data
                if (editor != null && editor.isModified()) {
                    source = Source.create(editor.getDocument());
                } else {
                    source = Source.create(refering);
                }

                HtmlFileModel model = new HtmlFileModel(source);

                List<Difference> diffs = new ArrayList<Difference>();
                for (Entry entry : model.getReferences()) {
                    String imp = entry.getName(); //unquoted
                    FileObject resolvedFileObject = WebUtils.resolve(refering, imp);
                    if (resolvedFileObject != null && resolvedFileObject.equals(file)) {
                        //the import refers to me - lets refactor it
                        if (entry.isValidInSourceDocument()) {
                            //new relative path creation
                            String newImport;
                            String extension = file.getExt(); //use the same extension as source file
                            int slashIndex = imp.lastIndexOf('/'); //NOI18N
                            if (slashIndex != -1) {
                                newImport = imp.substring(0, slashIndex) + "/" + newName + "." + extension; //NOI18N
                            } else {
                                newImport = newName + "." + extension; //NOI18N
                            }

                            diffs.add(new Difference(Difference.Kind.CHANGE,
                                    editor.createPositionRef(entry.getDocumentRange().getStart(), Bias.Forward),
                                    editor.createPositionRef(entry.getDocumentRange().getEnd(), Bias.Backward),
                                    entry.getName(),
                                    newImport,
                                    NbBundle.getMessage(HtmlRenameRefactoringPlugin.class, "MSG_Modify_File_Import"))); //NOI18N
                        }
                    }
                }

                modificationResult.addDifferences(refering, diffs);

            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

        }

        refactoringElements.registerTransaction(new RetoucheCommit(Collections.singletonList(modificationResult)));

        for (FileObject fo : modificationResult.getModifiedFileObjects()) {
            for (Difference diff : modificationResult.getDifferences(fo)) {
                refactoringElements.add(refactoring, DiffElement.create(diff, fo, modificationResult));

            }
        }

        return null;

    }
}
