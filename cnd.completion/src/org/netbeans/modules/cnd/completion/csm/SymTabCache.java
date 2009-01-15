/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.netbeans.modules.cnd.completion.csm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.netbeans.modules.cnd.api.model.CsmUID;
import org.netbeans.modules.cnd.completion.csm.CompletionResolver.Result;

/**
 *
 * @author Alexander Simon
 */
public class SymTabCache {
    private static ThreadLocal<Map<CacheEntry, Result>> threadCache = new ThreadLocal<Map<CacheEntry, Result>>();

    private SymTabCache() {
    }

    private static synchronized Map<CacheEntry, Result> getCache(){
        Map<CacheEntry, Result> cache = threadCache.get();
        if (cache == null) {
            cache = new HashMap<CacheEntry, Result>();
            threadCache.set(cache);
        }
        return cache;
    }

    /*package-local*/ static Result get(CacheEntry key){
        return getCache().get(key);
    }

    /*package-local*/ static Result put(CacheEntry key, Result value){
        return getCache().put(key, value);
    }

    /*package-local*/ static void setScope(CsmUID uid){
        Iterator<CacheEntry> it = SymTabCache.getCache().keySet().iterator();
        if (it.hasNext()) {
            CacheEntry entry = it.next();
            if (!entry.getScope().equals(uid)){
                //System.err.println("Clear cache:"+entry.getScope());
                //System.err.println("          ->"+uid);
                SymTabCache.getCache().clear();
            }
        }
    }

    /*package-local*/ static class CacheEntry {
        private int resolve;
        private int hide;
        private String name;
        private CsmUID scope;

        /*package-local*/ CacheEntry(int resolve, int hide, String name, CsmUID scope){
            this.resolve = resolve;
            this.hide = hide;
            this.name = name;
            this.scope = scope;
        }

        /*package-local*/ CsmUID getScope(){
            return scope;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof CacheEntry)){
                return false;
            }
            CacheEntry o = (CacheEntry) obj;
            return resolve == o.resolve && hide == o.hide &&
                   name.equals(o.name) && scope.equals(o.scope);
        }

        @Override
        public int hashCode() {
            return resolve + 17*(hide + 17*(name.hashCode()+17*scope.hashCode()));
        }
    }
}
