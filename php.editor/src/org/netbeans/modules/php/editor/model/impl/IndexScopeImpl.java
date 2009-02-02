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
package org.netbeans.modules.php.editor.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.netbeans.modules.gsf.api.CompilationInfo;
import org.netbeans.modules.gsf.api.NameKind;
import org.netbeans.modules.gsf.api.OffsetRange;
import org.netbeans.modules.php.editor.PHPLanguage;
import org.netbeans.modules.php.editor.index.IndexedClass;
import org.netbeans.modules.php.editor.index.IndexedConstant;
import org.netbeans.modules.php.editor.index.IndexedFunction;
import org.netbeans.modules.php.editor.index.IndexedInterface;
import org.netbeans.modules.php.editor.index.IndexedVariable;
import org.netbeans.modules.php.editor.index.PHPIndex;
import org.netbeans.modules.php.editor.model.ClassConstantElement;
import org.netbeans.modules.php.editor.model.ClassScope;
import org.netbeans.modules.php.editor.model.FieldElement;
import org.netbeans.modules.php.editor.model.IndexScope;
import org.netbeans.modules.php.editor.model.InterfaceScope;
import org.netbeans.modules.php.editor.model.MethodScope;
import org.netbeans.modules.php.editor.model.PhpKind;
import org.netbeans.modules.php.editor.model.PhpModifiers;
import org.netbeans.modules.php.editor.model.TypeScope;
import org.netbeans.modules.php.editor.model.VariableName;
import org.netbeans.modules.php.editor.parser.astnodes.BodyDeclaration.Modifier;
import org.openide.filesystems.FileObject;
import org.openide.util.Union2;

/**
 *
 * @author Radek Matous
 */
class IndexScopeImpl extends ScopeImpl implements IndexScope {
    private PHPIndex index;

    IndexScopeImpl(CompilationInfo info) {
        this(info, "index", PhpKind.INDEX);//NOI18N
        this.index = PHPIndex.get(info.getIndex(PHPLanguage.PHP_MIME_TYPE));
    }

    IndexScopeImpl(PHPIndex idx) {
        this(null, "index", PhpKind.INDEX);//NOI18N
        this.index = idx;
    }

    private IndexScopeImpl(CompilationInfo info, String name, PhpKind kind) {
        super(null, name, Union2.<String, FileObject>createSecond(info != null ? info.getFileObject() : null), new OffsetRange(0, 0), kind);//NOI18N
    }

    public List<? extends TypeScopeImpl> findTypes(String... queryName) {
        return findTypes(NameKind.EXACT_NAME, queryName);
    }

    public List<? extends TypeScopeImpl> findTypes(NameKind nameKind, String... queryName) {
        List<TypeScopeImpl> retval = new ArrayList<TypeScopeImpl>();
        for (String name : queryName) {
            assert name != null && name.trim().length() > 0;
            Collection<IndexedClass> classes = getIndex().getClasses(null, name, nameKind);
            for (IndexedClass indexedClass : classes) {
                retval.add(new ClassScopeImpl(this, indexedClass));
            }
            Collection<IndexedInterface> interfaces = getIndex().getInterfaces(null, name, nameKind);
            for (IndexedInterface indexedInterface : interfaces) {
                retval.add(new InterfaceScopeImpl(this, indexedInterface));
            }
        }
        return retval;
    }

    public List<? extends ClassScope> findClasses(String... queryName) {
        return findClasses(NameKind.EXACT_NAME, queryName);
    }

    public List<? extends ClassScope> findClasses(NameKind nameKind, String... queryName) {
        List<ClassScope> retval = new ArrayList<ClassScope>();
        for (String name : queryName) {
            assert name != null && name.trim().length() > 0;
            Collection<IndexedClass> classes = getIndex().getClasses(null, name, nameKind);
            for (IndexedClass indexedClass : classes) {
                retval.add(new ClassScopeImpl(this, indexedClass));
            }
        }
        return retval;
    }


    public List<? extends InterfaceScopeImpl> findInterfaces(String... queryName) {
        return findInterfaces(NameKind.EXACT_NAME, queryName);
    }

    public List<? extends InterfaceScopeImpl> findInterfaces(NameKind nameKind, String... queryName) {
        List<InterfaceScopeImpl> retval = new ArrayList<InterfaceScopeImpl>();
        for (String name : queryName) {
            assert name != null && name.trim().length() > 0;
            Collection<IndexedInterface> interfaces = getIndex().getInterfaces(null, name, nameKind);
            for (IndexedInterface indexedInterface : interfaces) {
                retval.add(new InterfaceScopeImpl(this, indexedInterface));
            }
        }
        return retval;
    }

    public List<? extends ConstantElementImpl> findConstants(String... queryName) {
        return findConstants(NameKind.EXACT_NAME, queryName);
    }

    public List<? extends ConstantElementImpl> findConstants(NameKind nameKind, String... queryName) {
        List<ConstantElementImpl> retval = new ArrayList<ConstantElementImpl>();
        for (String name : queryName) {
            assert name != null && name.trim().length() > 0;
            Collection<IndexedConstant> constants = getIndex().getConstants(null, name, nameKind);
            for (IndexedConstant indexedConstant : constants) {
                retval.add(new ConstantElementImpl(this, indexedConstant));
            }
        }
        return retval;
    }


    public List<? extends FunctionScopeImpl> findFunctions(String... queryName) {
        return findFunctions(NameKind.EXACT_NAME, queryName);
    }

    public List<? extends FunctionScopeImpl> findFunctions(NameKind nameKind, String... queryName) {
        List<FunctionScopeImpl> retval = new ArrayList<FunctionScopeImpl>();
        for (String name : queryName) {
            assert name != null && name.trim().length() > 0;
            Collection<IndexedFunction> functions = getIndex().getFunctions(null, name, nameKind);
            for (IndexedFunction indexedFunction : functions) {
                retval.add(new FunctionScopeImpl(this, indexedFunction));
            }
        }
        return retval;
    }

    public List<? extends VariableName> findVariables(String... queryName) {
        return findVariables(NameKind.EXACT_NAME, queryName);
    }

    public List<? extends VariableName> findVariables(NameKind nameKind, String... queryName) {
        List<VariableNameImpl> retval = new ArrayList<VariableNameImpl>();
        for (String name : queryName) {
            assert name != null && name.trim().length() > 0;
            Collection<IndexedVariable> vars = getIndex().getTopLevelVariables(null, name, nameKind);
            for (IndexedVariable indexedVariable : vars) {
                retval.add(new VariableNameImpl(this, indexedVariable));
            }
        }
        return retval;
    }

    @Override
    public OffsetRange getBlockRange() {
        return getNameRange();
    }

    @Override
    public List<? extends ModelElementImpl> getElements() {
        throw new IllegalStateException();
    }

    /**
     * @return the index
     */
    public PHPIndex getIndex() {
        return index;
    }


    public List<? extends ClassConstantElementImpl> getConstants(final NameKind nameKind, TypeScope cls, final String... queryName) {
        List<ClassConstantElementImpl> retval = new ArrayList<ClassConstantElementImpl>();
        for (String name : queryName) {
            Collection<IndexedConstant> constants = getIndex().getClassConstants(null, cls.getName(), name, nameKind);
            for (IndexedConstant idxConst : constants) {
                ClassConstantElementImpl elementImpl = new ClassConstantElementImpl(cls, idxConst);
                retval.add(elementImpl);
            }

        }
        return retval;
    }


    public List<? extends FieldElementImpl> getFields(final NameKind nameKind, ClassScopeImpl cls, final String queryName, final int... modifiers) {
        List<FieldElementImpl> retval = new ArrayList<FieldElementImpl>();
        PhpModifiers attribs = new PhpModifiers(modifiers);
        String name = (queryName.startsWith("$")) //NOI18N
                    ? queryName.substring(1) : queryName;

        Collection<IndexedConstant> constants = getIndex().getFields(null, cls.getName(), name, nameKind, modifiers.length == 0 ? PHPIndex.ANY_ATTR : attribs.toBitmask());
        for (IndexedConstant idxConst : constants) {
            FieldElementImpl elementImpl = new FieldElementImpl(cls, idxConst);
            retval.add(elementImpl);
        }
        return retval;
    }

    public List<? extends FieldElementImpl> getFields(ClassScopeImpl cls, final int... modifiers) {
        return getFields(NameKind.PREFIX, cls, "", modifiers);
    }

    public List<? extends FieldElementImpl> getFields(ClassScopeImpl aThis, final String queryName, final int... modifiers) {
        return getFields(NameKind.EXACT_NAME, aThis, queryName, modifiers);
    }

    public CachingSupport getCachedModelSupport() {
        return null;
    }


    public List<? extends MethodScope> findMethods( TypeScope cls,final NameKind nameKind, final String queryName, final int... modifiers) {
        List<MethodScopeImpl> retval = new ArrayList<MethodScopeImpl>();
        PhpModifiers attribs = new PhpModifiers(modifiers);
        //ClassScopeImpl cls = ModelUtils.getFirst(getClasses(className));
        //if (cls == null) return Collections.emptyList();
        //assert cls.getName().equals(className);
        Collection<IndexedFunction> methods = null;
        if (cls instanceof InterfaceScope) {
            methods = getIndex().getMethods(null, cls.getName(), queryName, nameKind, PHPIndex.ANY_ATTR);
        } else {
            methods = getIndex().getMethods(null, cls.getName(), queryName, nameKind, modifiers.length == 0 ? PHPIndex.ANY_ATTR : attribs.toBitmask());
        }
        for (IndexedFunction idxFunc : methods) {
            MethodScopeImpl msi = new MethodScopeImpl((TypeScopeImpl)cls, idxFunc, PhpKind.METHOD);
            retval.add(msi);
        }
        return retval;
    }

    public List<? extends MethodScope> findMethods(TypeScope cls, final String queryName, final int... modifiers) {
        return findMethods(cls,NameKind.EXACT_NAME, queryName, modifiers);
    }

    public List<? extends MethodScope> findMethods(TypeScope type, NameKind nameKind, String... queryName) {
        return findMethods(type,nameKind, queryName);
    }

    public List<? extends MethodScope> findMethods(TypeScope cls, final int... modifiers) {
        return findMethods( cls,NameKind.PREFIX, "", modifiers);
    }

    public List<? extends MethodScope> findInheritedMethods(final TypeScope cls, final String queryName) {
        List<MethodScopeImpl> retval = new ArrayList<MethodScopeImpl>();
        //ClassScopeImpl cls = ModelUtils.getFirst(getClasses(className));
        //if (cls == null) return Collections.emptyList();
        //assert cls.getName().equals(className);
        Collection<IndexedFunction> methods = getIndex().getMethods(null, cls.getName(), queryName, NameKind.EXACT_NAME, Modifier.PUBLIC | Modifier.PROTECTED);
        for (IndexedFunction idxFunc : methods) {
            MethodScopeImpl msi = new MethodScopeImpl(cls, idxFunc, PhpKind.METHOD);
            retval.add(msi);
        }
        return retval;
    }


    public List<? extends ClassConstantElement> findInheritedClassConstants(ClassScope clsScope, String constName) {
        List<ClassConstantElementImpl> retval = new ArrayList<ClassConstantElementImpl>();
        Collection<IndexedConstant> flds = getIndex().getClassConstants(null, clsScope.getName(), constName, NameKind.EXACT_NAME);
        for (IndexedConstant idxConst : flds) {
            ClassConstantElementImpl elementImpl = new ClassConstantElementImpl(clsScope, idxConst);
            retval.add(elementImpl);
        }
        return retval;
    }

    public List<? extends FieldElement> findInheritedFields(ClassScope clsScope, String fieldName) {
        List<FieldElement> retval = new ArrayList<FieldElement>();
        //ClassScopeImpl cls = ModelUtils.getFirst(getClasses(className));
        //if (cls == null) return Collections.emptyList();
        //assert cls.getName().equals(className);
        Collection<IndexedConstant> flds = getIndex().getFields(null, clsScope.getName(), fieldName, NameKind.EXACT_NAME, Modifier.PUBLIC | Modifier.PROTECTED);
        for (IndexedConstant idxConst : flds) {
            FieldElement fei = new FieldElementImpl(clsScope, idxConst);
            retval.add(fei);
        }
        return retval;
    }

    public List<? extends ClassConstantElement> findClassConstants(TypeScope aThis, final String... queryName) {
        return getConstants(NameKind.EXACT_NAME, aThis, queryName);
    }



}
