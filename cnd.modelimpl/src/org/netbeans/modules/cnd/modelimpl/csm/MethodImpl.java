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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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

package org.netbeans.modules.cnd.modelimpl.csm;

import java.io.IOException;
import java.util.Objects;
import org.netbeans.modules.cnd.antlr.collections.AST;
import org.netbeans.modules.cnd.api.model.CsmClass;
import org.netbeans.modules.cnd.api.model.CsmFile;
import org.netbeans.modules.cnd.api.model.CsmMethod;
import org.netbeans.modules.cnd.api.model.CsmScope;
import org.netbeans.modules.cnd.api.model.CsmVisibility;
import org.netbeans.modules.cnd.api.model.util.CsmKindUtilities;
import org.netbeans.modules.cnd.modelimpl.content.file.FileContent;
import org.netbeans.modules.cnd.modelimpl.csm.ClassImpl.MemberBuilder;
import org.netbeans.modules.cnd.modelimpl.csm.FunctionParameterListImpl.FunctionParameterListBuilder;
import org.netbeans.modules.cnd.modelimpl.csm.core.AstRenderer;
import org.netbeans.modules.cnd.modelimpl.csm.core.FileImpl;
import org.netbeans.modules.cnd.modelimpl.parser.generated.CPPTokenTypes;
import org.netbeans.modules.cnd.modelimpl.parser.spi.CsmParserProvider;
import org.netbeans.modules.cnd.modelimpl.repository.KeyUtilities;
import static org.netbeans.modules.cnd.modelimpl.repository.KeyUtilities.UID_INTERNAL_DATA_PREFIX;
import org.netbeans.modules.cnd.modelimpl.repository.PersistentUtils;
import org.netbeans.modules.cnd.modelimpl.textcache.NameCache;
import org.netbeans.modules.cnd.modelimpl.textcache.QualifiedNameCache;
import org.netbeans.modules.cnd.repository.spi.RepositoryDataInput;
import org.netbeans.modules.cnd.repository.spi.RepositoryDataOutput;
import org.openide.util.CharSequences;

/**
 * CsmFunction + CsmMember implementation
 * @param T
 * @author Vladimir Kvashin
 */
public class MethodImpl<T> extends FunctionImpl<T> implements CsmMethod {

    private final CsmVisibility visibility;
    private static final byte ABSTRACT = 1 << (FunctionImpl.LAST_USED_FLAG_INDEX+1);
    private static final byte VIRTUAL = 1 << (FunctionImpl.LAST_USED_FLAG_INDEX+2);
    private static final byte EXPLICIT = (byte)(1 << (FunctionImpl.LAST_USED_FLAG_INDEX+3));

    protected MethodImpl(CharSequence name, CharSequence rawName, CsmClass cls, CsmVisibility visibility, boolean _virtual, boolean _explicit, boolean _static, boolean _const, boolean _abstract, CsmFile file, int startOffset, int endOffset, boolean global) {
        super(name, rawName, cls, _static, _const, file, startOffset, endOffset, global);
        this.visibility = visibility;
        setVirtual(_virtual);
        setExplicit(_explicit);
        setAbstract(_abstract);
    }

    public static <T> MethodImpl<T> create(AST ast, final CsmFile file, FileContent fileContent, ClassImpl cls, CsmVisibility visibility, boolean global) throws AstRendererException {
        CsmScope scope = cls;
        
        int startOffset = getStartOffset(ast);
        int endOffset = getEndOffset(ast);
        
        NameHolder nameHolder = NameHolder.createFunctionName(ast);
        CharSequence name = QualifiedNameCache.getManager().getString(nameHolder.getName());
        if (name.length() == 0) {
            AstRendererException.throwAstRendererException((FileImpl) file, ast, startOffset, "Empty function name."); // NOI18N
        }
        CharSequence rawName = initRawName(ast);
        
        boolean _static = AstRenderer.FunctionRenderer.isStatic(ast, file, fileContent, name);
        boolean _const = AstRenderer.FunctionRenderer.isConst(ast);
        boolean _virtual = false;
        boolean _explicit = false;
        boolean afterParen = false;
        boolean _abstract = false;
        for( AST token = ast.getFirstChild(); token != null; token = token.getNextSibling() ) {
            switch( token.getType() ) {
                case CPPTokenTypes.LITERAL_static:
                    _static = true;
                    break;
                case CPPTokenTypes.LITERAL_virtual:
                    _virtual = true;
                    break;
                case CPPTokenTypes.LITERAL_explicit:
                    _explicit = true;
                    break;
                case CPPTokenTypes.RPAREN:
                    afterParen = true;
                    break;
                case CPPTokenTypes.ASSIGNEQUAL:
                    if (afterParen) {
                        _abstract = true;
                    }
                    break;
            }
        }
        
        scope = AstRenderer.FunctionRenderer.getScope(scope, file, _static, false);

        MethodImpl<T> methodImpl = new MethodImpl<>(name, rawName, cls, visibility, _virtual, _explicit, _static, _const, _abstract, file, startOffset, endOffset, global);
        temporaryRepositoryRegistration(ast, global, methodImpl);
        
        StringBuilder clsTemplateSuffix = new StringBuilder();
        TemplateDescriptor templateDescriptor = createTemplateDescriptor(ast, file, methodImpl, clsTemplateSuffix, global);
        CharSequence classTemplateSuffix = NameCache.getManager().getString(clsTemplateSuffix);
        
        methodImpl.setTemplateDescriptor(templateDescriptor, classTemplateSuffix);
        methodImpl.setReturnType(AstRenderer.FunctionRenderer.createReturnType(ast, methodImpl, file));
        methodImpl.setParameters(AstRenderer.FunctionRenderer.createParameters(ast, methodImpl, file, fileContent), 
                AstRenderer.FunctionRenderer.isVoidParameter(ast));
        
        postObjectCreateRegistration(global, methodImpl);
        nameHolder.addReference(fileContent, methodImpl);
        return methodImpl;
    }

    @Override
    public CsmClass getContainingClass() {
        return (CsmClass) getScope();
    }

    @Override
    public CsmVisibility getVisibility() {
        return visibility;
    }

    @Override
    public boolean isAbstract() {
        return hasFlags(ABSTRACT);
    }

    private void setAbstract(boolean _abstract) {
        setFlags(ABSTRACT, _abstract);
    }

    private void setVirtual(boolean _virtual) {
        setFlags(VIRTUAL, _virtual);
    }

    private void setExplicit(boolean _explicit) {
        setFlags(EXPLICIT, _explicit);
    }

    @Override
    public boolean isExplicit() {
        return hasFlags(EXPLICIT);
    }

    @Override
    public boolean isVirtual() {
        //TODO: implement!
        // returns direct "virtual" keyword presence
        return hasFlags(VIRTUAL);
    }

    @Override
    public boolean isConst() {
        return super.isConst();
    }

    @Override
    protected CharSequence createUIDExtraSuffix(AST ast) {
        CharSequence funSuffix = super.createUIDExtraSuffix(ast);
        if (CsmKindUtilities.isMethodDeclaration(this)) {
            // Check if this is method declaration which is added in class via #include directive:
            // struct AAA {
            //  #include "body.inc"
            // };
            CsmClass cls = getContainingClass();
            CsmFile clsFile = cls != null ? cls.getContainingFile() : null;
            if (cls != null && clsFile != null && !Objects.equals(clsFile, getContainingFile())) {
                StringBuilder sb = new StringBuilder(UID_INTERNAL_DATA_PREFIX);
                sb.append(cls.getName());
                sb.append("_"); // NOI18N
                if (clsFile instanceof FileImpl) {
                    sb.append(((FileImpl) clsFile).getFileId());
                } else {
                    // Absolute path shouldn't be used here
                    sb.append(clsFile.getName());
                }
                if (funSuffix != null) {
                    sb.append(funSuffix);
                }
                return sb.toString();
            }
        }
        return funSuffix;
    }

    public static class MethodBuilder extends FunctionBuilder implements MemberBuilder {
        
        private final boolean _virtual = false;
        private final boolean _explicit = false;
        private CsmVisibility visibility = CsmVisibility.PUBLIC;

        protected MethodBuilder() {
        }
        
        public MethodBuilder(SimpleDeclarationBuilder builder) {
            super(builder);
        }

        public CsmVisibility getVisibility() {
            return visibility;
        }

        @Override
        public void setVisibility(CsmVisibility visibility) {
            this.visibility = visibility;
        }

        public boolean isVirtual() {
            return _virtual;
        }

        public boolean isExplicit() {
            return _explicit;
        }
        
        @Override
        public MethodImpl create(CsmParserProvider.ParserErrorDelegate delegate) {
            final FunctionParameterListBuilder parameters = (FunctionParameterListBuilder)getParametersListBuilder();
            if (parameters == null) {
                return null;
            }
            CsmClass cls = (CsmClass) getScope();

            MethodImpl method = new MethodImpl(getName(), getRawName(), cls, getVisibility(), isVirtual(), isExplicit(), isStatic(), isConst(), false, getFile(), getStartOffset(), getEndOffset(), true);
            temporaryRepositoryRegistration(true, method);

            StringBuilder clsTemplateSuffix = new StringBuilder();
            //TemplateDescriptor templateDescriptor = createTemplateDescriptor(ast, file, functionImpl, clsTemplateSuffix, global);
            //CharSequence classTemplateSuffix = NameCache.getManager().getString(clsTemplateSuffix);

            //functionImpl.setTemplateDescriptor(templateDescriptor, classTemplateSuffix);
            if(getTemplateDescriptorBuilder() != null) {
                method.setTemplateDescriptor(getTemplateDescriptor(), NameCache.getManager().getString(CharSequences.create(""))); // NOI18N
            }

            method.setReturnType(getType());
            parameters.setScope(method);
            method.setParameters(parameters.create(), true);

            postObjectCreateRegistration(true, method);
            getNameHolder().addReference(getFileContent(), method);

//            addMember(method);
            return method;
        }
        
//        protected void addMember(CsmMember member) {
//            if (getParent() instanceof ClassImpl.ClassBuilder) {
//                ((ClassImpl.ClassBuilder) getParent()).addMember(member);
//            }
//        }
        
    }          
    
    
////////////////////////////////////////////////////////////////////////////
    // iml of SelfPersistent

    @Override
    public void write(RepositoryDataOutput output) throws IOException {
        super.write(output);
        PersistentUtils.writeVisibility(this.visibility, output);
    }

    public MethodImpl(RepositoryDataInput input) throws IOException {
        super(input);
        this.visibility = PersistentUtils.readVisibility(input);
    }
}

