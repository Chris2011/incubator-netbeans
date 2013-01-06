/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.javascript2.editor.hints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.netbeans.modules.csl.api.Hint;
import org.netbeans.modules.csl.api.HintSeverity;
import org.netbeans.modules.csl.api.HintsProvider;
import org.netbeans.modules.javascript2.editor.hints.JsHintsProvider.JsRuleContext;
import org.netbeans.modules.javascript2.editor.model.DeclarationScope;
import org.netbeans.modules.javascript2.editor.model.JsElement;
import org.netbeans.modules.javascript2.editor.model.JsObject;
import org.netbeans.modules.javascript2.editor.model.Occurrence;
import org.netbeans.modules.javascript2.editor.model.Type;
import org.netbeans.modules.javascript2.editor.model.impl.ModelUtils;
import org.openide.util.NbBundle;

/**
 *
 * @author Petr Pisl
 */
public class GlobalIsNotDefined extends JsAstRule {

    private static List<String> knownGlobalObjects = Arrays.asList("window", "document", "console",
            "clearInterval", "clearTimeout", "event", "frames", "history",
            "Image", "location", "name", "navigator", "Option", "parent", "screen", "setInterval", "setTimeout",
            "XMLHttpRequest", "JSON",  //NOI18N
            Type.ARRAY, Type.OBJECT, Type.BOOLEAN, Type.NULL, Type.NUMBER, Type.REGEXP, Type.STRING, Type.UNDEFINED, Type.UNRESOLVED);
    
    @Override
    void computeHints(JsRuleContext context, List<Hint> hints, HintsProvider.HintsManager manager) {
        JsObject globalObject = context.getJsParserResult().getModel().getGlobalObject();
        Collection<? extends JsObject> variables = ModelUtils.getVariables((DeclarationScope)globalObject);
        for (JsObject variable : variables) {
            if(!variable.isDeclared() 
                    && !knownGlobalObjects.contains(variable.getName()) 
                    && (variable.getJSKind() == JsElement.Kind.VARIABLE
                    || variable.getJSKind() == JsElement.Kind.OBJECT)) {
                if (variable.getOccurrences().isEmpty()) {
                    hints.add(new Hint(this, Bundle.JsGlobalIsNotDefinedDN(),
                            context.getJsParserResult().getSnapshot().getSource().getFileObject(),
                            ModelUtils.documentOffsetRange(context.getJsParserResult(), 
                            variable.getDeclarationName().getOffsetRange().getStart(), 
                            variable.getDeclarationName().getOffsetRange().getEnd()), null, 500));
                } else {
                    for(Occurrence occurrence : variable.getOccurrences()) {
                        hints.add(new Hint(this, Bundle.JsGlobalIsNotDefinedDN(),
                            context.getJsParserResult().getSnapshot().getSource().getFileObject(),
                            ModelUtils.documentOffsetRange(context.getJsParserResult(), 
                            occurrence.getOffsetRange().getStart(), 
                            occurrence.getOffsetRange().getEnd()), null, 500));
                    }
                }
            }
        }
    }

    @Override
    public Set<?> getKinds() {
        return Collections.singleton(JsAstRule.JS_OTHER_HINTS);
    }

    @Override
    public String getId() {
        return "jsglobalisnotdefined.hint";
    }

    @NbBundle.Messages("JsGlobalIsNotDefinedDesc=The global variable is not declared.")
    @Override
    public String getDescription() {
        return Bundle.JsGlobalIsNotDefinedDesc();
    }

    @NbBundle.Messages("JsGlobalIsNotDefinedDN=The global variable is not declared")
    @Override
    public String getDisplayName() {
        return Bundle.JsGlobalIsNotDefinedDN();
    }

    @Override
    public HintSeverity getDefaultSeverity() {
        return HintSeverity.CURRENT_LINE_WARNING;
    }
   
    @Override
    public boolean getDefaultEnabled() {
        return true;
    }
    
}
