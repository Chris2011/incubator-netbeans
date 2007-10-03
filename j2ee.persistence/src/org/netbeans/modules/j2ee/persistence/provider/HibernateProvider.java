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

package org.netbeans.modules.j2ee.persistence.provider;

import java.util.Collections;
import java.util.Map;
import org.openide.util.NbBundle;

/**
 * This class represents Hibernate provider.
 *
 * @author Erno Mononen
 */
class HibernateProvider extends Provider{
    
    protected HibernateProvider(){
        super("org.hibernate.ejb.HibernatePersistence");
    }

    public String getDisplayName() {
        return NbBundle.getMessage(HibernateProvider.class, "LBL_Hibernate"); //NOI18N
    }
    
    public String getJdbcUrl() {
        return "hibernate.connection.url";
    }
    
    public String getJdbcDriver() {
        return "hibernate.connection.driver_class";
    }
    
    public String getJdbcUsername() {
        return "hibernate.connection.username";
    }
    
    public String getJdbcPassword() {
        return "hibernate.connection.password";
    }
    
    public String getTableGenerationPropertyName() {
        return "hibernate.hbm2ddl.auto";
    }
    
    public String getTableGenerationDropCreateValue() {
        return "create-drop";
    }
    
    public String getTableGenerationCreateValue() {
        return "update";
    }

    public Map getUnresolvedVendorSpecificProperties() {
        return Collections.EMPTY_MAP;
    }
    
    public Map getDefaultVendorSpecificProperties() {
        return Collections.singletonMap(
                "hibernate.cache.provider_class",
                "org.hibernate.cache.NoCacheProvider");
    }
}
