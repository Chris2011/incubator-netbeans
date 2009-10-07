/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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
package org.netbeans.modules.j2ee.ejbverification;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.SourcePositions;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.java.lexer.JavaTokenId;
import org.netbeans.spi.editor.hints.Severity;

/**
 *
 * @author Tomasz.Slota@Sun.COM
 */
public class HintsUtils {
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo,
            String description){
        return createProblem(subject, cinfo, description, Severity.ERROR, Collections.<Fix>emptyList());
    }
    
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo,
            String description, Severity severity){
        return createProblem(subject, cinfo, description, severity, Collections.<Fix>emptyList());
    }
    
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo, String description,
            Severity severity, Fix fix){
        return createProblem(subject, cinfo, description, severity, Collections.singletonList(fix));
    }
    
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo, String description, Fix fix){
        return createProblem(subject, cinfo, description, Severity.ERROR, Collections.singletonList(fix));
    }
    
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo,
            String description, Severity severity, List<Fix> fixes){
        ErrorDescription err = null;
        List<Fix> fixList = fixes == null ? Collections.<Fix>emptyList() : fixes;
        
        // by default place error annotation on the element being checked
        Tree elementTree = cinfo.getTrees().getTree(subject);
        
        if (elementTree != null){
            TextSpan underlineSpan = getUnderlineSpan(cinfo, elementTree);
            
            err = ErrorDescriptionFactory.createErrorDescription(
                    severity, description, fixList, cinfo.getFileObject(),
                    underlineSpan.getStartOffset(), underlineSpan.getEndOffset());
            
        } else{
            // report problem
        }
        
        return err;
    }
    
    /**
     * This method returns the part of the syntax tree to be highlighted.
     * It will be usually the class/method/variable identifier.
     */
    public static TextSpan getUnderlineSpan(CompilationInfo info, Tree tree){
        SourcePositions srcPos = info.getTrees().getSourcePositions();
        
        int startOffset = (int) srcPos.getStartPosition(info.getCompilationUnit(), tree);
        int endOffset = (int) srcPos.getEndPosition(info.getCompilationUnit(), tree);
        
        Tree startSearchingForNameIndentifierBehindThisTree = null;
        
        if (tree.getKind() == Tree.Kind.CLASS){
            startSearchingForNameIndentifierBehindThisTree = ((ClassTree)tree).getModifiers();
            
        } else if (tree.getKind() == Tree.Kind.METHOD){
            startSearchingForNameIndentifierBehindThisTree = ((MethodTree)tree).getReturnType();
        } else if (tree.getKind() == Tree.Kind.VARIABLE){
            startSearchingForNameIndentifierBehindThisTree = ((VariableTree)tree).getType();
        }
        
        if (startSearchingForNameIndentifierBehindThisTree != null){
            int searchStart = (int) srcPos.getEndPosition(info.getCompilationUnit(),
                    startSearchingForNameIndentifierBehindThisTree);
            
            TokenSequence tokenSequence = info.getTreeUtilities().tokensFor(tree);
            
            if (tokenSequence != null){
                boolean eob = false;
                tokenSequence.move(searchStart);
                
                do{
                    eob = !tokenSequence.moveNext();
                }
                while (!eob && tokenSequence.token().id() != JavaTokenId.IDENTIFIER);
                
                if (!eob){
                    Token identifier = tokenSequence.token();
                    startOffset = identifier.offset(info.getTokenHierarchy());
                    endOffset = startOffset + identifier.length();
                }
            }
        }
        
        return new TextSpan(startOffset, endOffset);
    }
    
    /**
     * Represents a span of text
     */
    public static class TextSpan{
        private int startOffset;
        private int endOffset;
        
        public TextSpan(int startOffset, int endOffset){
            this.startOffset = startOffset;
            this.endOffset = endOffset;
        }
        
        public int getStartOffset(){
            return startOffset;
        }
        
        public int getEndOffset(){
            return endOffset;
        }
    }
}
