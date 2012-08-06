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
package org.netbeans.modules.groovy.refactoring.findusages.impl;

import java.util.List;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.groovy.refactoring.GroovyRefactoringElement;
import org.netbeans.modules.groovy.refactoring.utils.ElementUtils;

/**
 *
 * @author Martin Janicek
 */
public class FindMethodUsages extends AbstractFindUsages {

    public FindMethodUsages(GroovyRefactoringElement element) {
        super(element);
    }

    @Override
    protected AbstractFindUsagesVisitor getVisitor(ModuleNode moduleNode, String defClass) {
        return new FindMethodUsagesVisitor(moduleNode);
    }

    @Override
    protected ElementKind getElementKind() {
        return ElementKind.METHOD;
    }

    private class FindMethodUsagesVisitor extends AbstractFindUsagesVisitor {

        private final String declaringClassName;
        private final String findingMethod;

        public FindMethodUsagesVisitor(ModuleNode moduleNode) {
            super(moduleNode);
            this.declaringClassName = element.getDeclaringClassName();
            this.findingMethod = element.getName();
        }

        @Override
        public void visitMethod(MethodNode node) {
            String className = ElementUtils.getDeclaringClassName(node);
            if (declaringClassName.equals(className) && findingMethod.equals(node.getName())) {
                usages.add(node);
            }
            super.visitMethod(node);
        }

        @Override
        public void visitMethodCallExpression(MethodCallExpression methodCall) {
            Expression expression = methodCall.getObjectExpression();

            if (expression instanceof VariableExpression) {
                VariableExpression variableExpression = ((VariableExpression) expression);
                Variable variable = variableExpression.getAccessedVariable();

                if (variable != null) {
                    addVariableUsages(methodCall, variable);
                } else {
                    addThisUsages(methodCall);
                }

            } else if (expression instanceof ConstructorCallExpression) {
                addConstructorUsages(methodCall, (ConstructorCallExpression) expression);
            }
            super.visitMethodCallExpression(methodCall);
        }

        private void addVariableUsages(MethodCallExpression methodCall, Variable variable) {
            if (declaringClassName.equals(variable.getType().getName())) {
                findAndAdd(variable.getType(), methodCall);
            }
        }

        private void addThisUsages(MethodCallExpression methodCall) {
            List<ClassNode> classes = moduleNode.getClasses(); //classes declared in current file
            for (ClassNode classNode : classes) {
                if (declaringClassName.equals(classNode.getName())) {
                    findAndAdd(element.getDeclaringClass(), methodCall);
                    break;
                }
            }
        }

        private void addConstructorUsages(MethodCallExpression methodCall, ConstructorCallExpression constructorCallExpression) {
            ClassNode type = constructorCallExpression.getType();
            if (declaringClassName.equals(ElementUtils.getDeclaringClassName(type))) {
                findAndAdd(type, methodCall);
            }
        }

        /**
         * Tries to find exact MethodNode in the given ClassNode. The MethodNode
         * is based on MethodCallExpresion. If there is an exact match, the methodCall
         * is added into the usages list.
         *
         * @param type
         * @param methodCall
         */
        private void findAndAdd(ClassNode type, MethodCallExpression methodCall) {
            Expression arguments = methodCall.getArguments();

            if (!type.isResolved()) {
                type = type.redirect();
            }
            if (type.tryFindPossibleMethod(findingMethod, arguments) != null) {
                usages.add(methodCall);
            }
        }
    }
}
