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

package org.netbeans.modules.j2ee.persistence.spi.entitymanagergenerator;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.TypeParameterTree;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;

/**
 * Generates the code needed for invoking an <code>EntityManager</code> in Java EE 5
 * web components with a container-managed persistence unit.
 *
 * @author Erno Mononen
 */
public final class ContainerManagedJTAInjectableInWeb extends EntityManagerGenerationStrategySupport {
    
    
    public ClassTree generate() {
        
        ClassTree modifiedClazz = getClassTree();
        
        FieldInfo em = getEntityManagerFieldInfo();
        
        if(!em.isExisting()){
            List<ExpressionTree> attribs = new ArrayList<ExpressionTree>();
            attribs.add(getGenUtils().createAnnotationArgument("name", "persistence/LogicalName")); //NO18N
            attribs.add(getGenUtils().createAnnotationArgument("unitName", getPersistenceUnitName())); //NO18N
            modifiedClazz = getGenUtils().addAnnotation(modifiedClazz, getGenUtils().createAnnotation(PERSISTENCE_CONTEXT_FQN, attribs));
        }

        modifiedClazz = getTreeMaker().insertClassMember(modifiedClazz, getIndexForField(modifiedClazz), createUserTransaction());
        
        ModifiersTree methodModifiers = getTreeMaker().Modifiers(
                Collections.<Modifier>singleton(Modifier.PROTECTED),
                Collections.<AnnotationTree>emptyList()
                );
        MethodTree newMethod = getTreeMaker().Method(
                methodModifiers,
                computeMethodName(),
                getTreeMaker().PrimitiveType(TypeKind.VOID),
                Collections.<TypeParameterTree>emptyList(),
                getParameterList(),
                Collections.<ExpressionTree>emptyList(),
                "{ " + getMethodBody(em) + "}",
                null
                );
        
        return getTreeMaker().addClassMember(modifiedClazz, importFQNs(newMethod));
    }
    

    private String getMethodBody(FieldInfo em){
        String ctxInit = em.isExisting() ? "" : "    javax.naming.Context ctx = (javax.naming.Context) new javax.naming.InitialContext().lookup(\"java:comp/env\");\n";
        String emInit = em.isExisting() ? "    {0}.joinTransaction();\n" :"    javax.persistence.EntityManager {0} =  (javax.persistence.EntityManager) ctx.lookup(\"persistence/LogicalName\");\n";
        
        String text =
                "try '{'\n" +
                ctxInit +
                "    utx.begin();\n" +
                emInit +
                generateCallLines(em.getName()) +
                "    utx.commit();\n" +
                "} catch(Exception e) '{'\n" +
                "    java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,\"exception caught\", e);\n" +
                "    throw new RuntimeException(e);\n" +
                "}";
        return MessageFormat.format(text, em.getName());
    }
    
}
