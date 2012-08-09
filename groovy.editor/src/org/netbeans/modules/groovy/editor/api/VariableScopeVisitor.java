/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */

package org.netbeans.modules.groovy.editor.api;

import java.util.HashSet;
import java.util.Set;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.netbeans.api.lexer.Token;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.groovy.editor.api.AstUtilities.FakeASTNode;
import org.netbeans.modules.groovy.editor.api.lexer.GroovyTokenId;

/**
 * Visitor for finding occurrences of the class types, variables and methods.
 *
 * @author Martin Adamek
 * @author Martin Janicek
 */
public final class VariableScopeVisitor extends TypeVisitor {

    private final Set<ASTNode> occurrences = new HashSet<ASTNode>();
    private final ASTNode leafParent;

    public VariableScopeVisitor(SourceUnit sourceUnit, AstPath path, BaseDocument doc, int cursorOffset) {
        super(sourceUnit, path, doc, cursorOffset, true);
        this.leafParent = path.leafParent();
    }

    public Set<ASTNode> getOccurrences() {
        return occurrences;
    }

    @Override
    public void visitArrayExpression(ArrayExpression visitedArray) {
        String visitedName = removeParentheses(visitedArray.getElementType().getName());

        if (leaf instanceof FieldNode) {
            if (AstUtilities.isCaretOnFieldType(((FieldNode) leaf), doc, cursorOffset)) {
                addArrayExpressionOccurences(visitedArray, ((FieldNode) leaf).getType());
            }
        } else if (leaf instanceof PropertyNode) {
            if (AstUtilities.isCaretOnFieldType(((PropertyNode) leaf).getField(), doc, cursorOffset)) {
                addArrayExpressionOccurences(visitedArray, ((PropertyNode) leaf).getField().getType());
            }
        } else if (leaf instanceof Parameter) {
            if (AstUtilities.isCaretOnParamType(((Parameter) leaf), doc, cursorOffset)) {
                addArrayExpressionOccurences(visitedArray, ((Parameter) leaf).getType());
            }
        } else if (leaf instanceof Variable) {
            String varName = removeParentheses(((Variable) leaf).getName());
            if (varName.equals(visitedName)) {
                occurrences.add(new FakeASTNode(visitedArray, visitedName));
            }
        } else if (leaf instanceof MethodNode) {
            if (AstUtilities.isCaretOnReturnType((MethodNode) leaf, doc, cursorOffset)) {
                addArrayExpressionOccurences(visitedArray, ((MethodNode) leaf).getReturnType());
            }
        } else if (leaf instanceof ConstantExpression && leafParent instanceof PropertyExpression) {
            PropertyExpression property = (PropertyExpression) leafParent;
            if (visitedName.equals(property.getPropertyAsString())) {
                occurrences.add(new FakeASTNode(visitedArray, visitedName));
            }
        } else if (leaf instanceof DeclarationExpression) {
            DeclarationExpression declarationExpression = (DeclarationExpression) leaf;
            if (!declarationExpression.isMultipleAssignmentDeclaration()) {
                addArrayExpressionOccurences(visitedArray, declarationExpression.getVariableExpression().getType());
            }
        } else if (leaf instanceof ClassExpression) {
            addArrayExpressionOccurences(visitedArray, ((ClassExpression) leaf).getType());
        } else if (leaf instanceof ArrayExpression) {
            addArrayExpressionOccurences(visitedArray, ((ArrayExpression) leaf).getElementType());
        } else if (leaf instanceof ForStatement) {
            addArrayExpressionOccurences(visitedArray, ((ForStatement) leaf).getVariableType());
        }
        super.visitArrayExpression(visitedArray);
    }

    private void addArrayExpressionOccurences(ArrayExpression visitedArray, ClassNode findingNode) {
        String visitedTypeName = removeParentheses(visitedArray.getElementType().getName());
        String findingTypeName = removeParentheses(findingNode.getName());

        if (visitedTypeName.equals(findingTypeName)) {
            occurrences.add(new FakeASTNode(visitedArray, findingNode.getNameWithoutPackage()));
        }
    }

    @Override
    protected void visitParameters(Parameter[] parameters, Variable variable) {
        // method is declaring given variable, let's visit only the method,
        // but we need to check also parameters as those are not part of method visit
        for (Parameter parameter : parameters) {
            if (AstUtilities.isCaretOnParamType(parameter, doc, cursorOffset)) {
                occurrences.add(new FakeASTNode(parameter, parameter.getType().getNameWithoutPackage()));
            } else {
                if (parameter.getName().equals(variable.getName())) {
                    occurrences.add(parameter);
                    break;
                }
            }
        }
        super.visitParameters(parameters, variable);
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        if (expression.isParameterSpecified() && (leaf instanceof Variable)) {
            visitParameters(expression.getParameters(), (Variable) leaf);
        }
        super.visitClosureExpression(expression);
    }


    @Override
    protected boolean isValidToken(Token<? extends GroovyTokenId> currentToken, Token<? extends GroovyTokenId> previousToken) {
        // cursor must be positioned on identifier, otherwise occurences doesn't make sense
        // second check is here because we want to have occurences also at the end of the identifier (see issue #155574)
        return currentToken.id() == GroovyTokenId.IDENTIFIER || previousToken.id() == GroovyTokenId.IDENTIFIER;
    }
    
    @Override
    public void visitVariableExpression(VariableExpression variableExpression) {
        if (leaf instanceof FieldNode) {
            addVariableExpressionOccurrences(variableExpression, (FieldNode) leaf);
        } else if (leaf instanceof PropertyNode) {
            addVariableExpressionOccurrences(variableExpression, ((PropertyNode) leaf).getField());
        } else if (leaf instanceof Parameter) {
            if (!AstUtilities.isCaretOnParamType(((Parameter) leaf), doc, cursorOffset) && ((Parameter) leaf).getName().equals(variableExpression.getName())) {
                occurrences.add(variableExpression);
            }
        } else if (leaf instanceof Variable) {
            if (((Variable) leaf).getName().equals(variableExpression.getName())) {
                occurrences.add(variableExpression);
            }
        } else if (leaf instanceof ConstantExpression && leafParent instanceof PropertyExpression) {
            PropertyExpression property = (PropertyExpression) leafParent;
            if (variableExpression.getName().equals(property.getPropertyAsString())) {
                occurrences.add(variableExpression);
                return;
            }
        }
        super.visitVariableExpression(variableExpression);
    }

    private void addVariableExpressionOccurrences(VariableExpression visited, FieldNode findingNode) {
        final String fieldName = removeParentheses(findingNode.getName());
        final String fieldTypeName = removeParentheses(findingNode.getType().getName());

        if (AstUtilities.isCaretOnFieldType(findingNode, doc, cursorOffset)) {
            if (visited.getType().getName().equals(fieldTypeName)) {
                occurrences.add(new FakeASTNode(visited, findingNode.getType().getNameWithoutPackage()));
            }
        } else {
            if (visited.getName().equals(fieldName)) {
                occurrences.add(visited);
            }
        }
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        if (leaf instanceof FieldNode) {
            addDeclarationExpressionOccurrences(expression, (FieldNode) leaf);
        } else if (leaf instanceof PropertyNode) {
            addDeclarationExpressionOccurrences(expression, ((PropertyNode) leaf).getField());
        } else if (leaf instanceof Parameter) {
            if (AstUtilities.isCaretOnParamType(((Parameter) leaf), doc, cursorOffset)) {
                addDeclarationExpressionOccurrences(expression, ((Parameter) leaf).getType());
            }
        } else if (leaf instanceof DeclarationExpression) {
            DeclarationExpression visitedDeclaration = expression;
            DeclarationExpression declaration = (DeclarationExpression) leaf;
            VariableExpression variable = declaration.getVariableExpression();
            VariableExpression visited = visitedDeclaration.getVariableExpression();
            if (!variable.isDynamicTyped() && !visited.isDynamicTyped()) {
                String name = removeParentheses(variable.getType().getNameWithoutPackage());
                if (name.equals(removeParentheses(visited.getType().getNameWithoutPackage()))) {
                    FakeASTNode fakeNode = new FakeASTNode(expression, name);
                    occurrences.add(fakeNode);
                }
            }
        } else if (leaf instanceof ClassExpression) {
            VariableExpression variable = expression.getVariableExpression();
            if (!variable.isDynamicTyped()) {
                addDeclarationExpressionOccurrences(expression, ((ClassExpression) leaf).getType());
            }
        } else if (leaf instanceof ArrayExpression) {
            addDeclarationExpressionOccurrences(expression, ((ArrayExpression) leaf).getElementType());
        } else if (leaf instanceof ClassNode) {
            ClassNode clazz = (ClassNode) leaf;

            if (!expression.isMultipleAssignmentDeclaration()) {
                VariableExpression variable = expression.getVariableExpression();
                if (!variable.isDynamicTyped()) {
                    if (clazz.getName().equals(variable.getType().getName())) {
                        FakeASTNode fakeNode = new FakeASTNode(expression, clazz.getNameWithoutPackage());
                        occurrences.add(fakeNode);
                    }
                }
            }
        } else if (leaf instanceof MethodNode) {
            MethodNode method = (MethodNode) leaf;
            if (AstUtilities.isCaretOnReturnType(method, doc, cursorOffset) && !method.isDynamicReturnType()) {
                addDeclarationExpressionOccurrences(expression, method.getReturnType());
            }
        } else if (leaf instanceof ForStatement) {
            addDeclarationExpressionOccurrences(expression, ((ForStatement) leaf).getVariableType());
        }
        super.visitDeclarationExpression(expression);
    }

    private void addDeclarationExpressionOccurrences(DeclarationExpression visited, FieldNode findingNode) {
        final String fieldTypeName = removeParentheses(findingNode.getType().getName());
        if (AstUtilities.isCaretOnFieldType(findingNode, doc, cursorOffset)) {
            final String variableName;
            if (!visited.isMultipleAssignmentDeclaration()) {
                variableName = removeParentheses(visited.getVariableExpression().getType().getName());
            } else {
                variableName = removeParentheses(visited.getTupleExpression().getType().getName());
            }

            if (variableName.equals(fieldTypeName)) {
                occurrences.add(new FakeASTNode(visited, findingNode.getType().getNameWithoutPackage()));
            }
        }
    }

    private void addDeclarationExpressionOccurrences(DeclarationExpression visited, ClassNode type) {
        final String fieldTypeName = removeParentheses(type.getName());
        final String variableName;
        if (!visited.isMultipleAssignmentDeclaration()) {
            variableName = removeParentheses(visited.getVariableExpression().getType().getName());
        } else {
            variableName = removeParentheses(visited.getTupleExpression().getType().getName());
        }
        
        if (variableName.equals(fieldTypeName)) {
            occurrences.add(new FakeASTNode(visited, type.getNameWithoutPackage()));
        }
    }

    @Override
    public void visitField(FieldNode fieldNode) {
        final String fieldName = removeParentheses(fieldNode.getType().getName());
        final String fieldShortName = removeParentheses(fieldNode.getType().getNameWithoutPackage());

        if (leaf instanceof FieldNode) {
            addFieldOccurrences(fieldNode, (FieldNode) leaf);
        } else if (leaf instanceof PropertyNode) {
            addFieldOccurrences(fieldNode, ((PropertyNode) leaf).getField());
        } else if (leaf instanceof Parameter) {
            if (AstUtilities.isCaretOnParamType(((Parameter) leaf), doc, cursorOffset)) {
                addFieldOccurrences(fieldNode, ((Parameter) leaf).getType());
            }
        } else if (leaf instanceof Variable && ((Variable) leaf).getName().equals(fieldNode.getName())) {
            occurrences.add(fieldNode);
        } else if (leaf instanceof MethodNode) {
            if (AstUtilities.isCaretOnReturnType((MethodNode) leaf, doc, cursorOffset)) {
                String methodReturnTypeName = removeParentheses(((MethodNode) leaf).getReturnType().getName());
                if (fieldName.equals(methodReturnTypeName)) {
                    occurrences.add(new FakeASTNode(fieldNode, fieldShortName));
                }
            }
        } else if (leaf instanceof ConstantExpression && leafParent instanceof PropertyExpression) {
            PropertyExpression property = (PropertyExpression) leafParent;
            if (fieldNode.getName().equals(property.getPropertyAsString())) {
                occurrences.add(fieldNode);
            }
        } else if (leaf instanceof DeclarationExpression) {
            DeclarationExpression declarationExpression = (DeclarationExpression) leaf;
            if (!declarationExpression.isMultipleAssignmentDeclaration()) {
                VariableExpression variableExpression = declarationExpression.getVariableExpression();
                if (fieldName.equals(removeParentheses(variableExpression.getType().getName()))) {
                    occurrences.add(new FakeASTNode(fieldNode, fieldShortName));
                }
            }
        } else if (leaf instanceof ClassExpression) {
            addFieldOccurrences(fieldNode, ((ClassExpression) leaf).getType());
        } else if (leaf instanceof ArrayExpression) {
            addFieldOccurrences(fieldNode, ((ArrayExpression) leaf).getElementType());
        } else if (leaf instanceof ForStatement) {
            addFieldOccurrences(fieldNode, ((ForStatement) leaf).getVariableType());
        }
        super.visitField(fieldNode);
    }

    private void addFieldOccurrences(FieldNode visitedField, FieldNode findingNode) {
        final String fieldName = findingNode.getName();
        final String fieldTypeName = removeParentheses(findingNode.getType().getName());

        if (AstUtilities.isCaretOnFieldType(findingNode, doc, cursorOffset)) {
            String visitedFieldName = removeParentheses(visitedField.getType().getName());
            if (visitedFieldName.equals(fieldTypeName)) {
                occurrences.add(new FakeASTNode(visitedField, findingNode.getType().getNameWithoutPackage()));
            }
        } else {
            if (visitedField.getName().equals(fieldName)) {
                occurrences.add(visitedField);
            }
        }
    }

    private void addFieldOccurrences(FieldNode visitedField, ClassNode findingNode) {
        final String fieldTypeName = removeParentheses(findingNode.getName());
        final String visitedFieldName = removeParentheses(visitedField.getType().getName());
        if (visitedFieldName.equals(fieldTypeName)) {
            occurrences.add(new FakeASTNode(visitedField, findingNode.getNameWithoutPackage()));
        }
    }

    @Override
    public void visitMethod(MethodNode methodNode) {
        VariableScope variableScope = methodNode.getVariableScope();
        if (leaf instanceof Parameter) {
            if (AstUtilities.isCaretOnParamType(((Parameter) leaf), doc, cursorOffset)) {
                addMethodOccurrences(methodNode, ((Parameter) leaf).getType());
            } else {
                String name = ((Variable) leaf).getName();
                // This check is here because we can have method parameter with the same
                // name hidding property/field and we don't want to show occurences of these
                if (variableScope != null && variableScope.getDeclaredVariable(name) != null) {
                    return;

                }
            }
        } else  if (leaf instanceof Variable) {
            String name = ((Variable) leaf).getName();
            // This check is here because we can have method parameter with the same
            // name hidding property/field and we don't want to show occurences of these
            if (variableScope != null && variableScope.getDeclaredVariable(name) != null) {
                return;
            }
        }

        if (leaf instanceof FieldNode) {
            if (AstUtilities.isCaretOnFieldType((FieldNode) leaf, doc, cursorOffset)) {
                addMethodOccurrences(methodNode, ((FieldNode) leaf).getType());
            }
        } else if (leaf instanceof PropertyNode) {
            FieldNode field = ((PropertyNode) leaf).getField();
            if (AstUtilities.isCaretOnFieldType(field, doc, cursorOffset)) {
                addMethodOccurrences(methodNode, field.getType());
            }
        } else if (leaf instanceof ConstantExpression && leafParent instanceof MethodCallExpression) {
            MethodCallExpression methodCallExpression = (MethodCallExpression) leafParent;
            if (Methods.isSameMethod(methodNode, methodCallExpression)) {
                occurrences.add(methodNode);
            }
        } else if (leaf instanceof MethodNode) {
            MethodNode method = (MethodNode) leaf;

            if (AstUtilities.isCaretOnReturnType(method, doc, cursorOffset)) {
                addMethodOccurrences(methodNode, method.getReturnType());   // We have caret on the return type and we want to add all occurrences
            } else if (Methods.isSameMethod(methodNode, method)) {
                occurrences.add(methodNode);    // We are on method name looking for method calls
            }
        } else if (leaf instanceof ClassExpression) {
            addMethodOccurrences(methodNode, ((ClassExpression) leaf).getType());
        } else if (leaf instanceof DeclarationExpression) {
            VariableExpression variable = ((DeclarationExpression) leaf).getVariableExpression();
            if (!variable.isDynamicTyped() && !methodNode.isDynamicReturnType()) {
                addMethodOccurrences(methodNode, variable.getType());
            }
        } else if (leaf instanceof ArrayExpression) {
            addMethodOccurrences(methodNode, ((ArrayExpression) leaf).getElementType());
        } else if (leaf instanceof ClassNode) {
            if (!methodNode.isDynamicReturnType()) {
                addMethodOccurrences(methodNode, (ClassNode) leaf);
            }
        } else if (leaf instanceof ForStatement) {
            addMethodOccurrences(methodNode, ((ForStatement) leaf).getVariableType());
        }
        super.visitMethod(methodNode);
    }

    private void addMethodOccurrences(MethodNode visitedMethod, ClassNode findingNode) {
        final String returnTypeName = removeParentheses(visitedMethod.getReturnType().getName());
        final String name = removeParentheses(findingNode.getName());
        final String nameWithoutPackage = removeParentheses(findingNode.getNameWithoutPackage());

        // Check return type
        if (returnTypeName.equals(name)) {
            occurrences.add(new FakeASTNode(visitedMethod, nameWithoutPackage));
        }

        // Check method parameters
        for (Parameter parameter : visitedMethod.getParameters()) {
            String paramName = removeParentheses(parameter.getType().getName());
            if (paramName.equals(name)) {
                occurrences.add(new FakeASTNode(parameter, nameWithoutPackage));
            }
        }
    }

    @Override
    public void visitConstructor(ConstructorNode constructor) {
        VariableScope variableScope = constructor.getVariableScope();
        if (leaf instanceof Parameter) {
            if (AstUtilities.isCaretOnParamType(((Parameter) leaf), doc, cursorOffset)) {
                addConstructorOccurrences(constructor, ((Parameter) leaf).getType());
            } else {
                String name = ((Variable) leaf).getName();
                if (variableScope != null && variableScope.getDeclaredVariable(name) != null) {
                    return;

                }
            }
        } else if (leaf instanceof Variable) {
            String name = ((Variable) leaf).getName();
            if (variableScope != null && variableScope.getDeclaredVariable(name) != null) {
                return;
            }
        } else if (leaf instanceof ConstantExpression && leafParent instanceof PropertyExpression) {
            String name = ((ConstantExpression) leaf).getText();
            if (variableScope != null && variableScope.getDeclaredVariable(name) != null) {
                return;
            }
        }

        if (leaf instanceof FieldNode) {
            if (AstUtilities.isCaretOnFieldType((FieldNode) leaf, doc, cursorOffset)) {
                addMethodOccurrences(constructor, ((FieldNode) leaf).getType());
            }
        } else if (leaf instanceof PropertyNode) {
            FieldNode field = ((PropertyNode) leaf).getField();
            if (AstUtilities.isCaretOnFieldType(field, doc, cursorOffset)) {
                addConstructorOccurrences(constructor, field.getType());
            }
        } else if (leaf instanceof ConstructorNode) {
            if (Methods.isSameConstructor(constructor, (ConstructorNode) leaf)) {
                occurrences.add(constructor);
            }
        } else if (leaf instanceof MethodNode) {
            MethodNode method = (MethodNode) leaf;
            if (AstUtilities.isCaretOnReturnType(method, doc, cursorOffset)) {
                addConstructorOccurrences(constructor, method.getReturnType());   // We have caret on the return type and we want to add all occurrences
            }
        } else if (leaf instanceof ForStatement) {
            addConstructorOccurrences(constructor, ((ForStatement) leaf).getVariableType());
        } else if (leaf instanceof ClassExpression) {
            addConstructorOccurrences(constructor, ((ClassExpression) leaf).getType());
        } else if (leaf instanceof DeclarationExpression) {
            VariableExpression variable = ((DeclarationExpression) leaf).getVariableExpression();
            if (!variable.isDynamicTyped() && !constructor.isDynamicReturnType()) {
                addConstructorOccurrences(constructor, variable.getType());
            }
        } else if (leaf instanceof ConstructorCallExpression) {
            ConstructorCallExpression methodCallExpression = (ConstructorCallExpression) leaf;
            if (Methods.isSameConstructor(constructor, methodCallExpression)) {
                occurrences.add(constructor);
            }
        } else if (leaf instanceof ArrayExpression) {
            addConstructorOccurrences(constructor, ((ArrayExpression) leaf).getElementType());
        }
        super.visitConstructor(constructor);
    }

    private void addConstructorOccurrences(ConstructorNode constructor, ClassNode findingNode) {
        final String name = removeParentheses(findingNode.getName());
        final String nameWithoutPackage = removeParentheses(findingNode.getNameWithoutPackage());

        // Check constructor parameters
        for (Parameter parameter : constructor.getParameters()) {
            String paramName = removeParentheses(parameter.getType().getName());
            if (paramName.equals(name)) {
                occurrences.add(new FakeASTNode(parameter, nameWithoutPackage));
            }
        }
    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression methodCall) {
        if (leaf instanceof MethodNode) {
            MethodNode method = (MethodNode) leaf;
            if (Methods.isSameMethod(method, methodCall) && !AstUtilities.isCaretOnReturnType(method, doc, cursorOffset)) {
                occurrences.add(methodCall);
            }
        } else if (leaf instanceof ConstantExpression && leafParent instanceof MethodCallExpression) {
            if (Methods.isSameMethod(methodCall, (MethodCallExpression) leafParent)) {
                occurrences.add(methodCall);
            }
        }
        super.visitMethodCallExpression(methodCall);
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression call) {
        if (leaf instanceof ConstructorNode) {
            ConstructorNode constructor = (ConstructorNode) leaf;
            if (Methods.isSameConstructor(constructor, call)) {
                occurrences.add(call);
            }
        } else if (leaf instanceof ConstructorCallExpression) {
            if (Methods.isSameConstuctor(call, (ConstructorCallExpression) leaf)) {
                occurrences.add(call);
            }
        }
        super.visitConstructorCallExpression(call);
    }

    @Override
    public void visitClassExpression(ClassExpression clazz) {
        if (leaf instanceof FieldNode) {
            if (AstUtilities.isCaretOnFieldType((FieldNode) leaf, doc, cursorOffset)) {
                addClassExpressionOccurrences(clazz, ((FieldNode) leaf).getType());
            }
        } else if (leaf instanceof PropertyNode) {
            FieldNode field = ((PropertyNode) leaf).getField();
            if (AstUtilities.isCaretOnFieldType(field, doc, cursorOffset)) {
                addClassExpressionOccurrences(clazz, field.getType());
            }
        } else if (leaf instanceof Parameter) {
            if (AstUtilities.isCaretOnParamType(((Parameter) leaf), doc, cursorOffset)) {
                addClassExpressionOccurrences(clazz, ((Parameter) leaf).getType());
            }
        } else if (leaf instanceof ClassNode) {
            addClassExpressionOccurrences(clazz, (ClassNode) leaf);
        } else if (leaf instanceof ClassExpression) {
            addClassExpressionOccurrences(clazz, ((ClassExpression) leaf).getType());
        } else if (leaf instanceof ArrayExpression) {
            addClassExpressionOccurrences(clazz, ((ArrayExpression) leaf).getElementType());
        } else if (leaf instanceof DeclarationExpression) {
            DeclarationExpression declaration = (DeclarationExpression) leaf;
            VariableExpression variable = declaration.getVariableExpression();
            if (!variable.isDynamicTyped()) {
                addClassExpressionOccurrences(clazz, variable.getType());
            }
        } else if (leaf instanceof MethodNode) {
            MethodNode method = (MethodNode) leaf;
            if (AstUtilities.isCaretOnReturnType(method, doc, cursorOffset)) {
                addClassExpressionOccurrences(clazz, method.getReturnType());
            }
        } else if (leaf instanceof ForStatement) {
            addClassExpressionOccurrences(clazz, ((ForStatement) leaf).getVariableType());
        }
        super.visitClassExpression(clazz);
    }

    private void addClassExpressionOccurrences(ClassExpression clazz, ClassNode findingNode) {
        final String visitedName = removeParentheses(clazz.getType().getName());
        final String findingName = removeParentheses(findingNode.getName());
        if (visitedName.equals(findingName)) {
            occurrences.add(clazz);
        }
    }

    @Override
    public void visitClass(ClassNode classNode) {
        if (leaf instanceof ClassExpression) {
            if (classNode.getName().equals(((ClassExpression) leaf).getText())) {
                occurrences.add(classNode);
            }
        } else if (leaf instanceof ClassNode) {
            if (classNode.getName().equals(((ClassNode) leaf).getName())) {
                occurrences.add(classNode);
            }
        } else if (leaf instanceof DeclarationExpression) {
            DeclarationExpression declaration = (DeclarationExpression) leaf;
            VariableExpression variable = declaration.getVariableExpression();
            if (!variable.isDynamicTyped() && classNode.getName().equals(variable.getType().getName())) {
                occurrences.add(classNode);
            }
        } else if (leaf instanceof MethodNode) {
            MethodNode method = (MethodNode) leaf;
            OffsetRange range = AstUtilities.getMethodReturnType(method, doc, cursorOffset);
            if (range != OffsetRange.NONE && classNode.getName().equals(method.getReturnType().getName())) {
                occurrences.add(classNode);
            }
        }
        super.visitClass(classNode);
    }

    @Override
    public void visitPropertyExpression(PropertyExpression node) {
        Expression property = node.getProperty();
        if (leaf instanceof Variable && ((Variable) leaf).getName().equals(node.getPropertyAsString())) {
            occurrences.add(property);
        } else if (leaf instanceof ConstantExpression && leafParent instanceof PropertyExpression) {
            PropertyExpression propertyUnderCursor = (PropertyExpression) leafParent;
            String nodeAsString = node.getPropertyAsString();
            if (nodeAsString != null && nodeAsString.equals(propertyUnderCursor.getPropertyAsString())) {
                occurrences.add(property);
            }
        }
        super.visitPropertyExpression(node);
    }

    @Override
    public void visitForLoop(ForStatement forLoop) {
        if (leaf instanceof FieldNode) {
            if (AstUtilities.isCaretOnFieldType((FieldNode) leaf, doc, cursorOffset)) {
                addForLoopOccurrences(forLoop, ((FieldNode) leaf).getType());
            }
        } else if (leaf instanceof PropertyNode) {
            FieldNode field = ((PropertyNode) leaf).getField();
            if (AstUtilities.isCaretOnFieldType(field, doc, cursorOffset)) {
                addForLoopOccurrences(forLoop, field.getType());
            }
        } else if (leaf instanceof MethodNode) {
            MethodNode method = (MethodNode) leaf;
            if (AstUtilities.isCaretOnReturnType(method, doc, cursorOffset)) {
                addForLoopOccurrences(forLoop, method.getReturnType()); // We have caret on the return type and we want to add all occurrences
            }
        } else if (leaf instanceof ClassExpression) {
            addForLoopOccurrences(forLoop, ((ClassExpression) leaf).getType());
        } else if (leaf instanceof DeclarationExpression) {
            VariableExpression variable = ((DeclarationExpression) leaf).getVariableExpression();
            if (!variable.isDynamicTyped()) {
                addForLoopOccurrences(forLoop, variable.getType());
            }
        } else if (leaf instanceof ArrayExpression) {
            addForLoopOccurrences(forLoop, ((ArrayExpression) leaf).getElementType());
        } else if (leaf instanceof ForStatement) {
            addForLoopOccurrences(forLoop, ((ForStatement) leaf).getVariableType());
        } else if (leaf instanceof Parameter) {
            if (AstUtilities.isCaretOnParamType(((Parameter) leaf), doc, cursorOffset)) {
                addForLoopOccurrences(forLoop, ((Parameter) leaf).getType());
            }
        }
        super.visitForLoop(forLoop);
    }

    private void addForLoopOccurrences(ForStatement visitedLoop, ClassNode findingNode) {
        final String returnTypeName = removeParentheses(visitedLoop.getVariableType().getName());
        final String name = removeParentheses(findingNode.getName());
        final String nameWithoutPackage = removeParentheses(findingNode.getNameWithoutPackage());
        if (returnTypeName.equals(name)) {
            occurrences.add(new FakeASTNode(visitedLoop, nameWithoutPackage));
        }
    }

    /**
     * Removes [] parentheses.
     *
     * @param name where we want to strip parentheses off
     * @return name without [] parentheses
     */
    private String removeParentheses(String name) {
        if (name.endsWith("[]")) { // NOI18N
            name = name.substring(0, name.length() - 2);
        }
        return name;
    }
}
