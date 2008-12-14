/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.python.editor.hints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import org.netbeans.modules.gsf.api.CompilationInfo;
import org.netbeans.modules.gsf.api.Hint;
import org.netbeans.modules.gsf.api.HintFix;
import org.netbeans.modules.gsf.api.HintSeverity;
import org.netbeans.modules.gsf.api.NameKind;
import org.netbeans.modules.gsf.api.OffsetRange;
import org.netbeans.modules.gsf.api.RuleContext;
import org.netbeans.modules.python.editor.PythonAstUtils;
import org.netbeans.modules.python.editor.PythonIndex;
import org.netbeans.modules.python.editor.PythonParserResult;
import org.netbeans.modules.python.editor.elements.IndexedElement;
import org.netbeans.modules.python.editor.imports.ImportManager;
import org.netbeans.modules.python.editor.lexer.PythonLexerUtils;
import org.netbeans.modules.python.editor.lexer.PythonTokenId;
import org.netbeans.modules.python.editor.scopes.SymbolTable;
import org.openide.util.NbBundle;
import org.python.antlr.PythonTree;
import org.python.antlr.ast.Attribute;
import org.python.antlr.ast.Call;
import org.python.antlr.ast.Module;

/**
 * Detect Unresolved variables
 *
 * @author Tor Norbye
 */
public class UnresolvedDetector extends PythonAstRule {
    public UnresolvedDetector() {
    }

    public boolean appliesTo(RuleContext context) {
        return true;
    }

    public Set<Class> getKinds() {
        return Collections.<Class>singleton(Module.class);
    }

    public void run(PythonRuleContext context, List<Hint> result) {
        CompilationInfo info = context.compilationInfo;
        PythonParserResult pr = PythonAstUtils.getParseResult(info);
        SymbolTable symbolTable = pr.getSymbolTable();

        List<PythonTree> unresolvedNames = symbolTable.getUnresolved(info);
        if (unresolvedNames.size() > 0) {
            PythonIndex index = PythonIndex.get(info.getIndex(PythonTokenId.PYTHON_MIME_TYPE), info.getFileObject());

            for (PythonTree node : unresolvedNames) {
                // Compute suggestions
                String name = PythonAstUtils.getName(node);
                if (name == null) {
                    name = "";
                }
                List<HintFix> fixList = new ArrayList<HintFix>(3);
                // Is is a reference to a module?
                boolean tryModule = false;
                if (node.parent instanceof Call) {
                    Call call = (Call)node.parent;
                    PythonTree t = call.func;
                    if (t instanceof Attribute) {
                        tryModule = true;
                    }
                }
                String message = NbBundle.getMessage(NameRule.class, "UnresolvedVariable", name);
                if (name.equals("true")) { // NOI18N
                    // Help for new language converts...
                    message = NbBundle.getMessage(NameRule.class, "UnresolvedVariableMaybe", name, "True"); // NOI18N
                } else if (name.equals("false")) {
                    message = NbBundle.getMessage(NameRule.class, "UnresolvedVariableMaybe", name, "False"); // NOI18N
                } else if (name.equals("nil") || name.equals("null")) {
                    message = NbBundle.getMessage(NameRule.class, "UnresolvedVariableMaybe", name, "None"); // NOI18N
                } else if (name.equals("this")) {
                    message = NbBundle.getMessage(NameRule.class, "UnresolvedVariableMaybe", name, "self"); // NOI18N
                } else if (tryModule) {
                    Set<IndexedElement> moduleElements = index.getModules(name, NameKind.EXACT_NAME);
                    if (moduleElements.size() > 0) {
                        fixList.add(new ImportFix(context, node, name));
                    }
                } else {
                    Set<String> modules = index.getImportsFor(name, true);
                    if (modules.size() > 0) {
                        for (String module : modules) {
                            fixList.add(new ImportFix(context, node, module));
                        }
                    }
                }

                OffsetRange range = PythonAstUtils.getNameRange(info, node);
                range = PythonLexerUtils.getLexerOffsets(info, range);
                if (range != OffsetRange.NONE) {
                    Hint desc = new Hint(this, message, info.getFileObject(), range, fixList, 2305);
                    result.add(desc);
                }
            }
        }
    }

    public String getId() {
        return "Unresolved"; // NOI18N
    }

    public String getDisplayName() {
        return NbBundle.getMessage(NameRule.class, "Unresolved");
    }

    public String getDescription() {
        return NbBundle.getMessage(NameRule.class, "UnresolvedDesc");
    }

    public boolean getDefaultEnabled() {
        return false;
    }

    public boolean showInTasklist() {
        return true;
    }

    public HintSeverity getDefaultSeverity() {
        return HintSeverity.ERROR;
    }

    public JComponent getCustomizer(Preferences node) {
        return null;
    }

    private static class ImportFix implements HintFix {
        private final PythonRuleContext context;
        private final PythonTree node;
        private final String module;

        private ImportFix(PythonRuleContext context, PythonTree node, String module) {
            this.context = context;
            this.node = node;
            this.module = module;
        }

        public String getDescription() {
            return NbBundle.getMessage(CreateDocString.class, "FixImport", module);
        }

        public void implement() throws Exception {
            String mod = this.module;
            String symbol = null;
            int colon = mod.indexOf(':');
            if (colon != -1) {
                int end = mod.indexOf('(', colon + 1);
                if (end == -1) {
                    end = mod.indexOf(';', colon + 1);
                    if (end == -1) {
                        end = mod.length();
                    }
                }
                symbol = mod.substring(colon + 1, end).trim();
                mod = mod.substring(0, colon).trim();
            }
            new ImportManager(context.compilationInfo).ensureImported(mod, symbol, false, false, true);
        }

        public boolean isSafe() {
            return true;
        }

        public boolean isInteractive() {
            return false;
        }
    }
}
