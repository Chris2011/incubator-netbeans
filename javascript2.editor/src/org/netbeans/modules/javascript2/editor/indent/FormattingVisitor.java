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
package org.netbeans.modules.javascript2.editor.indent;

import com.oracle.nashorn.ir.*;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.javascript2.editor.lexer.JsTokenId;

/**
 *
 * @author Petr Hejl
 */
public class FormattingVisitor extends NodeVisitor {

    private final TokenSequence<? extends JsTokenId> ts;
    
    private final List<FormattingToken> tokens = new ArrayList<FormattingToken>();

    private Token nextToken;

    private int indentationLevel = 0;

    public FormattingVisitor(TokenSequence<? extends JsTokenId> ts) {
        this.ts = ts;
    }

    public List<FormattingToken> getTokens() {
        return tokens;
    }

    @Override
    public Node visit(AccessNode accessNode, boolean onset) {
        return super.visit(accessNode, onset);
    }

    @Override
    public Node visit(Block block, boolean onset) {
        if (onset) {
            if (block instanceof FunctionNode) {
                List<FunctionNode> functions = ((FunctionNode) block).getFunctions();
                for (int i = 0, count = functions.size(); i < count; i++) {
                    functions.set(i, (FunctionNode)functions.get(i).accept(this));
                }
            }
            
            List<Node> statements = block.getStatements();
            for (int i = 0, count = statements.size(); i < count; i++) {
                statements.set(i, statements.get(i).accept(this));
            }
        }
        return null;
    }

    @Override
    public Node visit(BinaryNode binaryNode, boolean onset) {
        return super.visit(binaryNode, onset);
    }

    @Override
    public Node visit(BreakNode breakNode, boolean onset) {
        return super.visit(breakNode, onset);
    }

    @Override
    public Node visit(CallNode callNode, boolean onset) {
        return super.visit(callNode, onset);
    }

    @Override
    public Node visit(CaseNode caseNode, boolean onset) {
        return super.visit(caseNode, onset);
    }

    @Override
    public Node visit(CatchNode catchNode, boolean onset) {
        return super.visit(catchNode, onset);
    }

    @Override
    public Node visit(ContinueNode continueNode, boolean onset) {
        return super.visit(continueNode, onset);
    }

    @Override
    public Node visit(DoWhileNode doWhileNode, boolean onset) {
        return super.visit(doWhileNode, onset);
    }

    @Override
    public Node visit(ExecuteNode executeNode, boolean onset) {
        return super.visit(executeNode, onset);
    }

    @Override
    public Node visit(ForNode forNode, boolean onset) {
        return super.visit(forNode, onset);
    }

    @Override
    public Node visit(FunctionNode functionNode, boolean onset) {
        if (onset && functionNode.getKind() != FunctionNode.Kind.SCRIPT) {
            int position = functionNode.position();
            Token token = getToken(position, JsTokenId.KEYWORD_FUNCTION);
            
            tokens.add(FormattingToken.create(FormattingToken.Kind.TEXT, ts.offset(),
                    token.text()));
            tokens.add(FormattingToken.create(FormattingToken.Kind.AFTER_FUNCTION_KEYWORD,
                    ts.offset() + token.length(), null));
            
            // identifier
            IdentNode node = functionNode.getIdent();
            if (node != null) {
                token = nextToken(JsTokenId.IDENTIFIER);
                if (token != null) {
                    // XXX node.getName()
                    tokens.add(FormattingToken.create(FormattingToken.Kind.TEXT, ts.offset(),
                            token.text()));
                    tokens.add(FormattingToken.create(FormattingToken.Kind.AFTER_FUNCTION_NAME,
                            ts.offset() + token.length(), null));
                }
            }

            // left bracket
            token = nextToken(JsTokenId.BRACKET_LEFT_PAREN);
            tokens.add(FormattingToken.create(FormattingToken.Kind.TEXT, ts.offset(),
                        token.text()));
            
            // parameters
            List<IdentNode> params = functionNode.getParameters();
            if (!params.isEmpty()) {
                for(int i = 0; i < params.size(); i++) {
                    token = nextToken(JsTokenId.IDENTIFIER);
                    tokens.add(FormattingToken.create(FormattingToken.Kind.TEXT, ts.offset(),
                            token.text()));
                    if (i < params.size() - 1) {
                        // comma
                        token = nextToken(JsTokenId.OPERATOR_COMMA);
                        tokens.add(FormattingToken.create(FormattingToken.Kind.TEXT, ts.offset(),
                                token.text()));
                        tokens.add(FormattingToken.create(FormattingToken.Kind.AFTER_FUNCTION_PARAMETER,
                                ts.offset() + 1, null));
                   }
                }
            }
            
            // right bracket
            token = nextToken(JsTokenId.BRACKET_RIGHT_PAREN);
            tokens.add(FormattingToken.create(FormattingToken.Kind.TEXT, ts.offset(),
                    token.text()));
            tokens.add(FormattingToken.create(FormattingToken.Kind.AFTER_FUNCTION,
                    ts.offset() + token.length(), null));
        }

        indentationLevel++;
        visit((Block) functionNode, onset);
        indentationLevel--;
        return null;
    }

    @Override
    public Node visit(IdentNode identNode, boolean onset) {
        return super.visit(identNode, onset);
    }

    @Override
    public Node visit(IfNode ifNode, boolean onset) {
        return super.visit(ifNode, onset);
    }

    @Override
    public Node visit(IndexNode indexNode, boolean onset) {
        return super.visit(indexNode, onset);
    }

    @Override
    public Node visit(LabelNode labeledNode, boolean onset) {
        return super.visit(labeledNode, onset);
    }

    @Override
    public Node visit(LineNumberNode lineNumberNode, boolean onset) {
        return super.visit(lineNumberNode, onset);
    }

    @Override
    public Node visit(LiteralNode literalNode, boolean onset) {
        return super.visit(literalNode, onset);
    }

    @Override
    public Node visit(ObjectNode objectNode, boolean onset) {
        return super.visit(objectNode, onset);
    }

    @Override
    public Node visit(PhiNode phiNode, boolean onset) {
        return super.visit(phiNode, onset);
    }

    @Override
    public Node visit(PropertyNode propertyNode, boolean onset) {
        return super.visit(propertyNode, onset);
    }

    @Override
    public Node visit(ReferenceNode referenceNode, boolean onset) {
        return super.visit(referenceNode, onset);
    }

    @Override
    public Node visit(ReturnNode returnNode, boolean onset) {
        return super.visit(returnNode, onset);
    }

    @Override
    public Node visit(RuntimeNode runtimeNode, boolean onset) {
        return super.visit(runtimeNode, onset);
    }

    @Override
    public Node visit(SwitchNode switchNode, boolean onset) {
        return super.visit(switchNode, onset);
    }

    @Override
    public Node visit(TernaryNode ternaryNode, boolean onset) {
        return super.visit(ternaryNode, onset);
    }

    @Override
    public Node visit(ThrowNode throwNode, boolean onset) {
        return super.visit(throwNode, onset);
    }

    @Override
    public Node visit(TryNode tryNode, boolean onset) {
        return super.visit(tryNode, onset);
    }

    @Override
    public Node visit(UnaryNode unaryNode, boolean onset) {
        return super.visit(unaryNode, onset);
    }

    @Override
    public Node visit(VarNode varNode, boolean onset) {
        return super.visit(varNode, onset);
    }

    @Override
    public Node visit(WhileNode whileNode, boolean onset) {
        return super.visit(whileNode, onset);
    }

    @Override
    public Node visit(WithNode withNode, boolean onset) {
        return super.visit(withNode, onset);
    }

    private Token getToken(int offset, JsTokenId expected) {
        ts.move(offset);

        if (!ts.moveNext() && !ts.movePrevious()) {
            return null;
        }
        
        Token<?extends JsTokenId> token = ts.token();
        if (expected != null) {
            while (expected != token.id() && ts.movePrevious()) {
                token = ts.token();
            }
        }
        return token;
    }
    
    private Token nextToken(JsTokenId expected) {
        if (nextToken != null) {
            Token token = nextToken;
            nextToken = null;
            return token;
        }

        while (ts.moveNext() && (ts.token().id() == JsTokenId.WHITESPACE
                || ts.token().id() == JsTokenId.BLOCK_COMMENT
                || ts.token().id() == JsTokenId.LINE_COMMENT
                || ts.token().id() == JsTokenId.DOC_COMMENT
                || ts.token().id() == JsTokenId.EOL));
        Token token = ts.token();
        if (expected != token.id()) {
            nextToken = token;
            return null;
        }
        return token;
    }
}
