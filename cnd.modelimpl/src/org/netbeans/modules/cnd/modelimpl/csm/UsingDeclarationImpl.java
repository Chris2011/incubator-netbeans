/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.

 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.cnd.modelimpl.csm;

import org.netbeans.modules.cnd.api.model.*;
import antlr.collections.AST;
import org.netbeans.modules.cnd.modelimpl.antlr2.CsmAST;
import org.netbeans.modules.cnd.modelimpl.antlr2.generated.CPPTokenTypes;
import org.netbeans.modules.cnd.modelimpl.csm.core.*;

/**
 * Implements CsmUsingDeclaration
 * @author Vladimir Kvasihn
 */
public class UsingDeclarationImpl extends OffsetableDeclarationBase implements CsmUsingDeclaration, RawNamable {

    private String name;
    // TODO: don't store declaration here since the instance might change
    private CsmDeclaration referencedDeclaration = null;
    
    public UsingDeclarationImpl(AST ast, CsmFile file) {
        super(ast, file);
        name = ast.getText();
    }
    
    public CsmDeclaration getReferencedDeclaration() {
        // TODO: process preceding aliases
        // TODO: process non-class elements
//        if (!Boolean.getBoolean("cnd.modelimpl.resolver"))
        if (ResolverFactory.resolver != 2)
            return ((ProjectBase) getContainingFile().getProject()).findClassifier(name, true);
        else {
            if (referencedDeclaration == null) {
                referencedDeclaration = (CsmDeclaration)ResolverFactory.createResolver(
                        getContainingFile(),
                        ((CsmAST)getAst().getFirstChild()).getOffset()).
                        resolve(name);
            }
            return referencedDeclaration;
        }
    }
    
    public int getStartOffset() {
        return ((CsmAST)getAst().getFirstChild()).getOffset();
    }
    
    public CsmDeclaration.Kind getKind() {
        return CsmDeclaration.Kind.USING_DECLARATION;
    }
    
    public String getName() {
        return name;
    }
    
    public String getQualifiedName() {
        return getName();
    }
    
    public String[] getRawName() {
        return AstUtil.getRawNameInChildren(getAst());
    }
    
    public String toString() {
        return "" + getKind() + ' ' + name /*+ " rawName=" + Utils.toString(getRawName())*/;
    }
    
    public CsmScope getScope() {
        //TODO: implement!
        return null;
    }
    
//    Moved to OffsetableDeclarationBase
//    public String getUniqueName() {
//        return getQualifiedName();
//    }
    
}
