/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
package org.netbeans.modules.languages.features;

import java.util.List;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;

import org.netbeans.api.languages.ASTEvaluator;
import org.netbeans.api.languages.ASTItem;
import org.netbeans.api.languages.ASTPath;
import org.netbeans.api.languages.ParserResult;
import org.netbeans.api.languages.SyntaxContext;
import org.netbeans.modules.languages.ASTEvaluatorFactory;
import org.netbeans.modules.languages.Feature;


/**
 *
 * @author hanz
 */
public class ColorsASTEvaluator extends ASTEvaluator {

    
    private Document document;

    private ColorsASTEvaluator (Document document) {
        this.document = document;
    }

    public String getFeatureName () {
        return "COLOR";
    }

    public void beforeEvaluation (ParserResult parserResult) {
    }

    public void afterEvaluation (ParserResult parserResult) {
    }

    public void evaluate (List<ASTItem> path, Feature feature) {
        AttributeSet attributeSet = null;
        ASTItem leaf = path.get (path.size () - 1);
        SyntaxContext context = SyntaxContext.create (document, ASTPath.create (path));
        if (feature.getBoolean ("condition", context, true)) {
            attributeSet = ColorsManager.createColoring (feature, null);
            SemanticHighlightsLayer.addHighlight (document, leaf.getOffset (), leaf.getEndOffset (), attributeSet);
        }
    }

    
    // innerclasses ............................................................
    
    public static class AASTEvaluatorFactory implements ASTEvaluatorFactory {
        public ASTEvaluator create (Document document) {
            return new ColorsASTEvaluator (document);
        }
    }
}
