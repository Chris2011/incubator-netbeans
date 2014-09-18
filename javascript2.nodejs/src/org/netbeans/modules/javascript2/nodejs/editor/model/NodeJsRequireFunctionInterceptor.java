/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */
package org.netbeans.modules.javascript2.nodejs.editor.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.javascript2.editor.api.lexer.JsTokenId;
import org.netbeans.modules.javascript2.editor.api.lexer.LexUtilities;
import org.netbeans.modules.javascript2.editor.model.DeclarationScope;
import org.netbeans.modules.javascript2.editor.model.JsObject;
import org.netbeans.modules.javascript2.editor.spi.model.FunctionArgument;
import org.netbeans.modules.javascript2.editor.spi.model.FunctionInterceptor;
import org.netbeans.modules.javascript2.editor.spi.model.ModelElementFactory;
import org.netbeans.modules.javascript2.nodejs.editor.NodeJsUtils;
import org.netbeans.modules.parsing.api.Source;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Petr Pisl
 */
@FunctionInterceptor.Registration(priority = 370)
public class NodeJsRequireFunctionInterceptor implements FunctionInterceptor {

    private static Pattern METHOD_NAME = Pattern.compile(NodeJsUtils.REQUIRE_METHOD_NAME); //NOI18N

    @Override
    public Pattern getNamePattern() {
        return METHOD_NAME;
    }

    @Override
    public void intercept(String name, JsObject globalObject, DeclarationScope scope, ModelElementFactory factory, Collection<FunctionArgument> args) {
        FileObject fo = globalObject.getFileObject();
        if (fo == null) {
            // no action
            return;
        }

        if (args.size() == 1) {
            FunctionArgument theFirst = args.iterator().next();
            if (theFirst.getKind() == FunctionArgument.Kind.STRING) {
                String module = (String)theFirst.getValue();
                Source source = Source.create(fo);
                TokenSequence<? extends JsTokenId> ts = LexUtilities.getJsTokenSequence(source.createSnapshot().getTokenHierarchy(), theFirst.getOffset());
                if (ts == null) {
                    return;
                }
                ts.move(theFirst.getOffset());
                if (ts.moveNext()) {
                    
                    Token<? extends JsTokenId> token = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.IDENTIFIER, JsTokenId.OPERATOR_SEMICOLON));
                    if (token != null && token.id() == JsTokenId.IDENTIFIER && NodeJsUtils.REQUIRE_METHOD_NAME.equals(token.text().toString())) {
                        token = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.OPERATOR_ASSIGNMENT, JsTokenId.OPERATOR_SEMICOLON));
                        if (token != null && token.id() == JsTokenId.OPERATOR_ASSIGNMENT) {
                            token = LexUtilities.findPreviousIncluding(ts, Arrays.asList(JsTokenId.IDENTIFIER, JsTokenId.OPERATOR_SEMICOLON,
                                    JsTokenId.BRACKET_LEFT_BRACKET, JsTokenId.BRACKET_LEFT_CURLY, JsTokenId.BRACKET_LEFT_PAREN));
                            if (token != null && token.id() == JsTokenId.IDENTIFIER) {
                                String objectName = token.text().toString();
                                JsObject jsObject = ((JsObject)scope).getProperty(objectName);
                                if (jsObject != null) {
                                    int assignmentOffset =  ts.offset() + token.length();
                                    jsObject.addAssignment(new NodeJsType(NodeJsUtils.getModuleName(module), assignmentOffset) , assignmentOffset);
                                }
                            }
                        }
                    }    
                }
            }
        }
    }

}