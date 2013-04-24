/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.java.metrics.hints;

import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import java.beans.Expression;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.java.hints.BooleanOption;
import org.netbeans.spi.java.hints.ErrorDescriptionFactory;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.IntegerOption;
import org.netbeans.spi.java.hints.TriggerPattern;
import org.netbeans.spi.java.hints.TriggerPatterns;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.netbeans.spi.java.hints.UseOptions;
import org.openide.util.NbBundle;

import static org.netbeans.modules.java.metrics.hints.Bundle.*;

/**
 * Provides basic class metrics
 * 
 * @author sdedic
 */
@NbBundle.Messages({
    "# {0} - class name",
    "# {1} - complexity",
    "TEXT_ClassTooComplex=Class {0} is too complex. Cyclomatic complexity = {1}",
    "# {0} - complexity",
    "TEXT_ClassAnonymousTooComplex=Anonymous class is too complex. Cyclomatic complexity = {0}",
    "# {0} - class name",
    "# {1} - number of referencies",
    "TEXT_ClassTooCoupled=Class {0} is too coupled. References {1} other types",
    "# {0} - class name",
    "# {1} - method count",
    "TEXT_ClassManyMethods=Class {0} has too many methods: {1}",
    "# {0} - class name",
    "# {1} - constructor count",
    "TEXT_ClassManyConstructors=Class {0} has too many constructors: {1}",
    "# {0} - class name",
    "# {1} - field count",
    "TEXT_ClassManyFields=Class {0} has too many fields: {1}"
})
public class ClassMetrics {
    static final int DEFAULT_ANONYMOUS_COMPLEXITY_LIMIT = 5;
    static final int DEFAULT_COMPLEXITY_LIMIT = 80;
    static final int DEFAULT_COUPLING_LIMIT = 25;
    static final int DEFAULT_CLASS_FIELDS_LIMIT = 10;
    static final int DEFAULT_CLASS_METHODS_LIMIT = 20;
    static final int DEFAULT_CLASS_CONSTRUCTORS_LIMIT = 3;
    static final boolean DEFAULT_COUPLING_IGNORE_JAVA = true;
    static final boolean DEFAULT_CLASS_METHODS_IGNORE_ACCESSORS = true;
    static final boolean DEFAULT_CLASS_FIELDS_IGNORE_CONSTANTS = true;
    static final boolean DEFAULT_CLASS_METHODS_IGNORE_ABSTRACT = true;
    
    @IntegerOption(
        displayName = "#OPTNAME_ClassAnonymousComplexityLimit",
        tooltip = "#OPTDESC_ClassAnonymousComplexityLimit",
        maxValue = 1000,
        step = 1,
        defaultValue = DEFAULT_ANONYMOUS_COMPLEXITY_LIMIT
    )
    public static final String OPTION_ANONYMOUS_COMPLEXITY_LIMIT = "metrics.class.anonymous.complexity.limit"; // NOI18N

    @IntegerOption(
        displayName = "#OPTNAME_ClassComplexityLimit",
        tooltip = "#OPTDESC_ClassComplexityLimit",
        maxValue = 1000,
        step = 1,
        defaultValue = DEFAULT_COMPLEXITY_LIMIT
    )
    public static final String OPTION_COMPLEXITY_LIMIT = "metrics.class.complexity.limit"; // NOI18N
    
    @IntegerOption(
        displayName = "#OPTNAME_ClassCouplingLimit",
        tooltip = "#OPTDESC_ClassCouplingLimit",
        maxValue = 1000,
        step = 1,
        defaultValue = DEFAULT_COUPLING_LIMIT
    )
    public static final String OPTION_COUPLING_LIMIT = "metrics.class.coupling.limit"; // NOI18N
    
    @BooleanOption(
        displayName = "#OPTNAME_ClassCouplingIgnoreJava",
        tooltip = "#OPTDESC_ClassCouplingIgnoreJava",
        defaultValue = DEFAULT_COUPLING_IGNORE_JAVA
    )
    public static final String OPTION_COUPLING_IGNORE_JAVA = "metrics.class.coupling.nojava"; // NOI18N
    
    @IntegerOption(
        displayName = "#OPTNAME_ClassConstructorsLimit",
        tooltip = "#OPTDESC_ClassConstructorsLimit",
        minValue = 1,
        maxValue = 1000,
        step = 1,
        defaultValue = DEFAULT_CLASS_CONSTRUCTORS_LIMIT
    )
    public static final String OPTION_CLASS_CONSTRUCTORS_LIMIT = "metrics.class.constructors.limit"; // NOI18N
    
    @IntegerOption(
        displayName = "#OPTNAME_ClassMethodsLimit",
        tooltip = "#OPTDESC_ClassMethodsLimit",
        minValue = 1,
        maxValue = 1000,
        step = 1,
        defaultValue = DEFAULT_CLASS_METHODS_LIMIT
    )
    public static final String OPTION_CLASS_METHODS_LIMIT = "metrics.class.methods.limit"; // NOI18N

    @BooleanOption(
        displayName = "#OPTNAME_ClassMethodCountIgnoreAccessors",
        tooltip = "#OPTDESC_ClassMethodCountIgnoreAccessors",
        defaultValue = DEFAULT_CLASS_METHODS_IGNORE_ACCESSORS
    )
    public static final String OPTION_CLASS_METHODS_IGNORE_ACCESSORS = "metrics.class.methods.ignoreaccessors"; // NOI18N
    
    @BooleanOption(
        displayName = "#OPTNAME_ClassMethodCountIgnoreAbstract",
        tooltip = "#OPTDESC_ClassMethodCountIgnoreAbstract",
        defaultValue = DEFAULT_CLASS_METHODS_IGNORE_ABSTRACT
    )
    public static final String OPTION_CLASS_METHODS_IGNORE_ABSTRACT = "metrics.class.methods.ignoreabstract"; // NOI18N
    
    @IntegerOption(
        displayName = "#OPTNAME_ClassFieldsLimit",
        tooltip = "#OPTDESC_ClassFieldsLimit",
        minValue = 1,
        maxValue = 1000,
        step = 1,
        defaultValue = DEFAULT_CLASS_METHODS_LIMIT
    )
    public static final String OPTION_CLASS_FIELDS_LIMIT = "metrics.class.fields.limit"; // NOI18N

    @BooleanOption(
        displayName = "#OPTNAME_FieldCountIgnoreConstants",
        tooltip = "#OPTDESC_FieldCountIgnoreConstants",
        defaultValue = DEFAULT_CLASS_FIELDS_IGNORE_CONSTANTS
    )
    public static final String OPTION_CLASS_FIELDS_IGNORE_CONSTANTS = "metrics.class.fields.ignoreconst"; // NOI18N
    
    @Hint(
        displayName = "#DN_ClassAnonymousTooComplex",
        description = "#DESC_ClassAnonymousTooComplex",
        category = "metrics",
        options = { Hint.Options.HEAVY, Hint.Options.QUERY },
        enabled = false
    )
    @UseOptions(OPTION_ANONYMOUS_COMPLEXITY_LIMIT)
    @TriggerPatterns({
        @TriggerPattern("new $classname<$tparams$>($params$) { $members$; }"),
        @TriggerPattern("$expr.new $classname<$tparams$>($params$) { $members$; }"),
        @TriggerPattern("new $classname($params$) { $members$; }"),
        @TriggerPattern("$expr.new $classname($params$) { $members$; }"),
    })
    public static ErrorDescription tooComplexAnonymousClass(HintContext ctx) {
        CyclomaticComplexityVisitor v = new CyclomaticComplexityVisitor();
        v.scan(ctx.getPath(), null);
        
        int complexity = v.getComplexity();
        int limit = ctx.getPreferences().getInt(OPTION_ANONYMOUS_COMPLEXITY_LIMIT, 
                DEFAULT_ANONYMOUS_COMPLEXITY_LIMIT);
        if (complexity > limit) {
            return ErrorDescriptionFactory.forName(ctx, 
                    ctx.getPath(), 
                    TEXT_ClassAnonymousTooComplex(complexity));
        } else {
            return null;
        }
    }
    
    @Hint(
        displayName = "#DN_ClassTooComplex",
        description = "#DESC_ClassTooComplex",
        category = "metrics",
        options = { Hint.Options.HEAVY, Hint.Options.QUERY },
        enabled = false
    )
    @UseOptions(OPTION_COMPLEXITY_LIMIT)
    @TriggerTreeKind(Tree.Kind.CLASS)
    public static ErrorDescription tooComplexClass(HintContext ctx) {
        ClassTree clazz = (ClassTree)ctx.getPath().getLeaf();
        TypeElement e = (TypeElement)ctx.getInfo().getTrees().getElement(ctx.getPath());
        if (e.getNestingKind() == NestingKind.ANONYMOUS) {
            return null;
        }
        CyclomaticComplexityVisitor v = new CyclomaticComplexityVisitor();
        v.scan(ctx.getPath(), null);
        
        int complexity = v.getComplexity();
        int limit = ctx.getPreferences().getInt(OPTION_COMPLEXITY_LIMIT, DEFAULT_COMPLEXITY_LIMIT);
        if (complexity > limit) {
            return ErrorDescriptionFactory.forName(ctx, 
                    ctx.getPath(), 
                    TEXT_ClassTooComplex(clazz.getSimpleName().toString(), complexity));
        } else {
            return null;
        }
    }
    
    @Hint(
        displayName = "#DN_ClassTooCoupled",
        description = "#DESC_ClassTooCoupled",
        category = "metrics",
        options = { Hint.Options.HEAVY, Hint.Options.QUERY },
        enabled = false
    )
    @UseOptions({ OPTION_COUPLING_LIMIT, OPTION_COUPLING_IGNORE_JAVA })
    @TriggerTreeKind(Tree.Kind.CLASS)
    public static ErrorDescription tooCoupledClass(HintContext ctx) {
        ClassTree clazz = (ClassTree)ctx.getPath().getLeaf();
        DependencyCollector col = new DependencyCollector(ctx.getInfo());
        boolean ignoreJava = ctx.getPreferences().getBoolean(OPTION_COUPLING_IGNORE_JAVA, DEFAULT_COUPLING_IGNORE_JAVA);
        col.setIgnoreJavaLibraries(ignoreJava);
        col.scan(ctx.getPath(), null);
        
        int coupling = col.getSeenQNames().size();
        int limit = ctx.getPreferences().getInt(OPTION_COUPLING_LIMIT, DEFAULT_COUPLING_LIMIT);
        if (coupling > limit) {
            return ErrorDescriptionFactory.forName(ctx, 
                    ctx.getPath(), 
                    TEXT_ClassTooCoupled(clazz.getSimpleName().toString(), coupling));
        } else {
            return null;
        }
    }
    
    @Hint(
        displayName = "#DN_ClassConstructorCount",
        description = "#DESC_ClassConstructorCount",
        category = "metrics",
        options = { Hint.Options.HEAVY, Hint.Options.QUERY },
        enabled = false
    )
    @UseOptions({OPTION_CLASS_CONSTRUCTORS_LIMIT})
    @TriggerTreeKind(Tree.Kind.CLASS)
    public static ErrorDescription tooManyConstructors(HintContext ctx) {
        ClassTree clazz = (ClassTree)ctx.getPath().getLeaf();
        int methodCount = 0;
        for (Tree member : clazz.getMembers()) {
            if (member.getKind() != Tree.Kind.METHOD) {
                continue;
            }
            MethodTree method = (MethodTree)member;
            if (method.getReturnType() != null) {
                // a constructor ?
                continue;
            }
            methodCount++;
        }
        
        int limit = ctx.getPreferences().getInt(OPTION_CLASS_CONSTRUCTORS_LIMIT, DEFAULT_CLASS_CONSTRUCTORS_LIMIT);
        if (methodCount <= limit) {
            return null;
        }
        return ErrorDescriptionFactory.forName(ctx, 
                    ctx.getPath(), 
                    TEXT_ClassManyConstructors(clazz.getSimpleName().toString(), methodCount));
    }
    
    @Hint(
        displayName = "#DN_ClassMethodCount",
        description = "#DESC_ClassMethodCount",
        category = "metrics",
        options = { Hint.Options.HEAVY, Hint.Options.QUERY },
        enabled = false
    )
    @UseOptions({ OPTION_CLASS_METHODS_IGNORE_ABSTRACT, OPTION_CLASS_METHODS_IGNORE_ACCESSORS,
        OPTION_CLASS_METHODS_LIMIT})
    @TriggerTreeKind(Tree.Kind.CLASS)
    public static ErrorDescription tooManyMethods(HintContext ctx) {
        ClassTree clazz = (ClassTree)ctx.getPath().getLeaf();
        boolean ignoreAccessors = ctx.getPreferences().getBoolean(OPTION_CLASS_METHODS_IGNORE_ACCESSORS, DEFAULT_CLASS_METHODS_IGNORE_ACCESSORS);
        boolean ignoreAbstract = ctx.getPreferences().getBoolean(OPTION_CLASS_METHODS_IGNORE_ABSTRACT, DEFAULT_CLASS_METHODS_IGNORE_ABSTRACT);
        int methodCount = 0;
        for (Tree member : clazz.getMembers()) {
            if (member.getKind() != Tree.Kind.METHOD) {
                continue;
            }
            MethodTree method = (MethodTree)member;
            if (method.getReturnType() == null) {
                // a constructor ?
                continue;
            }
            TreePath methodPath = new TreePath(ctx.getPath(), method);
            if (ignoreAccessors && (isSimpleGetter(ctx.getInfo(), methodPath) ||
                    isSimpleSetter(ctx.getInfo(), methodPath))) {
                continue;
            }
            if (ignoreAbstract) {
                ExecutableElement mel = (ExecutableElement)ctx.getInfo().getTrees().getElement(methodPath);
                ExecutableElement overriden = ctx.getInfo().getElementUtilities().getOverriddenMethod(mel);
                if (overriden != null && overriden.getModifiers().contains(Modifier.ABSTRACT)) {
                    continue;
                }
            }
            methodCount++;
        }
        
        int limit = ctx.getPreferences().getInt(OPTION_CLASS_METHODS_LIMIT, DEFAULT_CLASS_METHODS_LIMIT);
        if (methodCount <= limit) {
            return null;
        }
        return ErrorDescriptionFactory.forName(ctx, 
                    ctx.getPath(), 
                    TEXT_ClassManyMethods(clazz.getSimpleName().toString(), methodCount));
    }
    
    @Hint(
        displayName = "#DN_ClassFieldCount",
        description = "#DESC_ClassFieldCount",
        category = "metrics",
        options = { Hint.Options.HEAVY, Hint.Options.QUERY },
        enabled = false
    )
    @UseOptions({OPTION_CLASS_FIELDS_IGNORE_CONSTANTS, OPTION_CLASS_FIELDS_LIMIT})
    @TriggerTreeKind(Tree.Kind.CLASS)
    public static ErrorDescription tooManyFields(HintContext ctx) {
        ClassTree clazz = (ClassTree)ctx.getPath().getLeaf();
        boolean ignoreConstants = ctx.getPreferences().getBoolean(OPTION_CLASS_FIELDS_IGNORE_CONSTANTS, DEFAULT_CLASS_FIELDS_IGNORE_CONSTANTS);
        int fieldCount = 0;
        for (Tree member : clazz.getMembers()) {
            if (member.getKind() != Tree.Kind.VARIABLE) {
                continue;
            }
            if (ignoreConstants) {
                TreePath fieldPath = new TreePath(ctx.getPath(), member);
                if (isConstant(ctx.getInfo(), fieldPath)) {
                    continue;
                }
            }
            fieldCount++;
        }
        
        int limit = ctx.getPreferences().getInt(OPTION_CLASS_FIELDS_LIMIT, DEFAULT_CLASS_FIELDS_LIMIT);
        if (fieldCount > limit) {
            return ErrorDescriptionFactory.forName(ctx, 
                        ctx.getPath(), 
                        TEXT_ClassManyFields(clazz.getSimpleName().toString(), fieldCount));
        } else {
            return null;
        }
    }
    
    static boolean isSimpleSetter(CompilationInfo info, TreePath methodPath) {
        MethodTree method = (MethodTree)methodPath.getLeaf();
        Name mn = method.getName();
        if (mn.length() < 4 || !mn.subSequence(0, 3).toString().equals("set")) { // NOI18N
            return false;
        }
        if (method.getParameters().size() != 1) {
            return false;
        }
        TypeMirror retType = info.getTrees().getTypeMirror(new TreePath(methodPath, method.getReturnType()));
        if (retType.getKind() != TypeKind.VOID) {
            return false;
        }
        if (method.getBody() == null || method.getBody().getStatements().size() != 1) {
            return false;
        }
        StatementTree st = method.getBody().getStatements().get(0);
        if (st.getKind() != Tree.Kind.EXPRESSION_STATEMENT) {
            return false;
        }
        ExpressionTree stEx = ((ExpressionStatementTree)st).getExpression();
        if (stEx.getKind() != Tree.Kind.ASSIGNMENT) {
            return false;
        }
        Element e = info.getTrees().getElement(
                new TreePath(new TreePath(new TreePath(
                    new TreePath(methodPath, method.getBody()),
                    st), stEx), 
                    ((AssignmentTree)stEx).getVariable()));
        if (!isFieldOfThis(info, e, methodPath)) {
            return false;
        }
        // ensure that the expression is just parenthesized / typecasted the parameter
        ExpressionTree expr = ((AssignmentTree)stEx).getExpression();
        TreePath exprPath = new TreePath(new TreePath(
                new TreePath(methodPath, method.getBody()),
                st), expr);
        boolean unwrap = true;
        
        while (unwrap) {
            switch (expr.getKind()) {
                case PARENTHESIZED:
                    expr = ((ParenthesizedTree)expr).getExpression();
                    break;
                case TYPE_CAST:
                    expr = ((TypeCastTree)expr).getExpression();
                    break;
                default:
                    unwrap = false;
                    break;
            }
            if (unwrap) {
                exprPath = new TreePath(exprPath, expr);
            }
        }
        Name paramName = method.getParameters().get(0).getName();
        if (expr.getKind() != Tree.Kind.IDENTIFIER ||
            !((IdentifierTree)expr).getName().equals(paramName)) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks whether the expression at tree path resolves to a field
     * on this type or a supertype.
     * 
     * @param info
     * @param e
     * @param methodPath
     * @return 
     */
    private static boolean isFieldOfThis(CompilationInfo info, Element e, TreePath methodPath) {
        if (e == null || e.getKind() != ElementKind.FIELD) {
            return false;
        }
        if (e.getEnclosingElement() == null || e.getEnclosingElement().getKind() != ElementKind.CLASS) {
            return false;
        }
        TypeMirror methodDeclaringType = info.getTrees().getElement(methodPath).getEnclosingElement().asType();
        TypeMirror fieldParent = e.getEnclosingElement().asType();
        return info.getTypes().isSubtype(methodDeclaringType, fieldParent);
    }
    
    /**
     * Determines if the method tree represents a simple getter. That is, a no-parameter method, whose sole
     * command is return of an instance variable.
     */
    static boolean isSimpleGetter(CompilationInfo info, TreePath methodPath) {
        MethodTree method = (MethodTree)methodPath.getLeaf();
        Name mn = method.getName();
        boolean boolProp = false;
        String propName;
        
        if (mn.length() < 3) {
            return false;
        }
        if (mn.subSequence(0, 2).equals("is")) { // NOI18N
            boolProp = true;
            propName = mn.subSequence(2, mn.length()).toString();
        } else {
            if (mn.length() < 4 || !mn.subSequence(0, 3).toString().equals("get")) { // NOI18N
                return false;
            }
        }
        if (!method.getParameters().isEmpty()) {
            return false;
        }
        if (method.getBody() == null) {
            return false;
        }
        List<? extends StatementTree> stmts = method.getBody().getStatements();
        if (stmts.size() != 1) {
            return false;
        }
        StatementTree ret = stmts.get(0);
        if (ret.getKind() != Tree.Kind.RETURN) {
            return false;
        }
        ExpressionTree expr = ((ReturnTree)ret).getExpression();
        TreePath exprPath = new TreePath(new TreePath(
                new TreePath(methodPath, method.getBody()),
                ret), expr);
        boolean unwrap = true;
        
        while (unwrap) {
            switch (expr.getKind()) {
                case PARENTHESIZED:
                    expr = ((ParenthesizedTree)expr).getExpression();
                    break;
                case TYPE_CAST:
                    expr = ((TypeCastTree)expr).getExpression();
                    break;
                default:
                    unwrap = false;
                    break;
            }
            if (unwrap) {
                exprPath = new TreePath(exprPath, expr);
            }
        }
        
        // the field can be accessed either as this.field or field
        Name fieldName;
        
        if (expr.getKind() == Tree.Kind.MEMBER_SELECT) {
            ExpressionTree selector = ((MemberSelectTree)expr).getExpression();
            if (selector.getKind() != Tree.Kind.IDENTIFIER) {
                return false;
            } else if (!((IdentifierTree)selector).getName().contentEquals("this")) {
                return false;
            }
            fieldName = ((MemberSelectTree)expr).getIdentifier();
        } else if (expr.getKind() == Tree.Kind.IDENTIFIER) {
            fieldName = ((IdentifierTree)expr).getName();
        }
        Element e = info.getTrees().getElement(exprPath);
        return isFieldOfThis(info, e, methodPath);
    }
    
    private static final Collection<Modifier> CONSTANT_MODS = EnumSet.of(
            Modifier.STATIC, Modifier.FINAL);
    
    /**
     * Determines whether a field is 'constant'. The field must be static final,
     * and its type must be primitive, wrapper, String or Class.
     */
    static boolean isConstant(CompilationInfo info, TreePath fieldPath) {
        VariableTree var = (VariableTree)fieldPath.getLeaf();
        if (!var.getModifiers().getFlags().containsAll(CONSTANT_MODS)) {
            return false;
        }
        TypeMirror tm = info.getTrees().getTypeMirror(fieldPath);
        switch (tm.getKind()) {
            case BOOLEAN:
            case BYTE: case CHAR: case DOUBLE: case FLOAT: 
            case INT: case LONG: case SHORT: 
                return true;
            case DECLARED:
                break;
            default:
                return false;
        }
        Element e = info.getTypes().asElement(tm);
        if (!(e instanceof TypeElement)) {
            return false;
        }
        String fqn = ((TypeElement)e).getQualifiedName().toString();
        if (!fqn.startsWith("java.lang.")) {
            return false;
        }
        String sn = ((TypeElement)e).getSimpleName().toString();
        switch (sn) {
            case "Boolean": case "Byte": case "Char": case "Double": case "Float":
            case "Integer": case "Long": case "Short": 
            case "String": 
            case "Class":
                return true;
        }
        return false;
    }
}
