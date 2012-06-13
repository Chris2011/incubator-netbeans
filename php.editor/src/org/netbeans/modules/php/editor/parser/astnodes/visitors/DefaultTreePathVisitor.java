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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.php.editor.parser.astnodes.visitors;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.netbeans.modules.php.editor.parser.astnodes.*;

/**
 *
 * @author Tomasz.Slota@Sun.COM
 */
public class DefaultTreePathVisitor extends DefaultVisitor {

    // @GuardedBy(unmodifiablePath)
    private final List<ASTNode> path = Collections.synchronizedList(new LinkedList<ASTNode>());
    private final List<ASTNode> unmodifiablePath;

    public DefaultTreePathVisitor() {
        unmodifiablePath = Collections.unmodifiableList(path);
    }

    /**
     * Reversed order.
     * <br /><br />
     * You have to <b>synchronize the block</b>, where the iteration is done over this
     * collection. E.g.:
     * <pre>
     * List<ASTNode> path = getPath();
     * synchronized (path) {
     *     for (ASTNode node : path) {
     *         ...
     *     }
     * }
     * </pre>
     *
     * @return
     */
    public List<ASTNode> getPath() {
        return unmodifiablePath;
    }

    @Override
    public void visit(NamespaceDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(UseStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(UseStatementPart node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ArrayAccess node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ArrayCreation node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ArrayElement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Assignment node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ASTError astError) {
        super.visit(astError);
    }

    @Override
    public void visit(BackTickExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Block node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(BreakStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(CastExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(CatchClause node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ConstantDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ClassDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ClassInstanceCreation node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ClassName node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(CloneExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Comment node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ConditionalExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ContinueStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(DeclareStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(DoStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(EchoStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(EmptyStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ExpressionStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(FieldAccess node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(FieldsDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ForEachStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(FormalParameter node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ForStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(FunctionDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(FunctionInvocation node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(FunctionName node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(GlobalStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Identifier node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(IfStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(IgnoreError node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Include node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(InfixExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(InLineHtml node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(InstanceOfExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(InterfaceDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ListVariable node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(MethodDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(MethodInvocation node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ParenthesisExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(PostfixExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(PrefixExpression node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Program node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Quote node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Reference node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ReflectionVariable node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ReturnStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Scalar node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(SingleFieldDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(StaticConstantAccess node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(StaticFieldAccess node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(StaticMethodInvocation node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(StaticStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(SwitchCase node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(SwitchStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ThrowStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(TryStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(UnaryOperation node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(Variable node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(WhileStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(ASTNode node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(PHPDocBlock node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(PHPDocTypeTag node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(PHPDocTag node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(PHPDocVarTypeTag node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(PHPDocMethodTag node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(PHPDocNode node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(TraitDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(TraitMethodAliasDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(TraitConflictResolutionDeclaration node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(UseTraitStatement node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(UseTraitStatementPart node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    @Override
    public void visit(AnonymousObjectVariable node) {
        addToPath(node);
        super.visit(node);
        removeFromPath();
    }

    protected void addToPath(ASTNode node) {
        if (node != null) {
            synchronized (unmodifiablePath) {
                path.add(0, node);
            }
        }
    }

    protected void removeFromPath() {
        synchronized (unmodifiablePath) {
            path.remove(0);
        }
    }
}
