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
 * Software is Sun Microsystems, Inc.
 *
 * Portions Copyrighted 2006 Sun Microsystems, Inc.
 */

package org.openide.util.lookup.implspi;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/** Infrastructure provider interface for those who control the overall
 * registration of services in the system. The first instance of this interface
 * found in {@link Lookup#getDefault()} is consulted when providing answers
 * to {@link Lookups#forPath(java.lang.String)} queries. Current implementation
 * is not ready for multiple instances of this interface (the first one wins)
 * and also changing the instances during runtime.
 *
 * <div class="nonnormative">
 * The basic implementation of this interface is provided in
 * <a href="@org-openide-filesystems@/overview-summary.html">Filesystem API</a>
 * and recognizes the 
 * <a href="@org-openide-util@/org/openide/util/doc-files/api.html#instance-folders">.instance files</a>
 * registered in XML layers. As such one can rely on
 * <a href="@org-openide-util@/org/openide/util/doc-files/api.html#instance-folders">.instance files</a>
 * being recognized in unit tests, if the
 * <a href="@org-openide-filesystems@/overview-summary.html">Filesystem API</a>
 * is included.
 * The implementation
 * is then refined in
 * <a href="@org-netbeans-modules-settings@/overview-summary.html">Settings API</a>
 * to handle also <a href="@org-openide-util@/org/openide/util/doc-files/api.html#settings">.settings files</a>.
 * Again, including this module in unit tests
 * ensures 
 * <a href="@org-openide-util@/org/openide/util/doc-files/api.html#settings">.settings files</a>
 * files are recognized.
 * </div>
 *
 * @author Jaroslav Tulach
 * @since 8.1
 */
public abstract class NamedServicesProvider {
    private static final Map<String,Reference<Lookup>> namedServicesProviders = Collections.synchronizedMap(new HashMap<String,Reference<Lookup>>());

    public static Lookup forPath(String path) {

        Reference<Lookup> ref = namedServicesProviders.get(path);
        Lookup lkp = ref == null ? null : ref.get();
        if (lkp != null) {
            return lkp;
        }
        NamedServicesProvider prov = Lookup.getDefault().lookup(NamedServicesProvider.class);
        if (prov != null &&
            /* avoid stack overflow during initialization */
            !path.startsWith(
                "URLStreamHandler/"
                /*URLStreamHandlerRegistrationProcessor.REGISTRATION_PREFIX*/
            )
        ) {
            lkp = prov.create(path);
        } else {
            ClassLoader l = Lookup.getDefault().lookup(ClassLoader.class);
            if (l == null) {
                l = Thread.currentThread().getContextClassLoader();
                if (l == null) {
                    l = NamedServicesProvider.class.getClassLoader();
                }
            }
            lkp = Lookups.metaInfServices(l, "META-INF/namedservices/" + path);
        }

        namedServicesProviders.put(path, new WeakReference<Lookup>(lkp));
        return lkp;
    }
    
    /** Finds a config object under given path.
     * @param filePath path to .instance or .settings file
     * @param type the requested type for given object
     * @return either null or instance of requested type
     * @since 8.10 
     */
    public static <T> T getConfigObject(String filePath, Class<T> type) {
        NamedServicesProvider prov = Lookup.getDefault().lookup(NamedServicesProvider.class);
        return prov != null ? prov.lookupObject(filePath, type) : null;
    }
    
    /** Allows the providers to donate a special lookup for a given object.
     * Right now it is used only for obtaining <code>FileObject.getLookup</code>.
     * 
     * @param obj the object to find lookup for
     * @return <code>null</code> or new lookup to be associated with the <code>obj</code>
     * @since 8.17
     */
    public static Lookup createLookupFor(Object obj) {
        NamedServicesProvider prov = Lookup.getDefault().lookup(NamedServicesProvider.class);
        return prov != null ? prov.lookupFor(obj) : null;
    }

    static void clearCache() {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            throw new IllegalStateException();
        }
        namedServicesProviders.clear();
    }

    /** Throws an exception. Prevents unwanted instantiation of this class
     * by unknown subclasses.
     */
    protected NamedServicesProvider() {
        if (getClass().getName().equals("org.openide.util.lookup.PathInLookupTest$P")) { // NOI18N
            // OK for tests
            return;
        }
        if (getClass().getName().equals("org.openide.util.UtilitiesTest$NamedServicesProviderImpl")) { // NOI18N
            // OK for tests
            return;
        }
        if (getClass().getName().equals("org.netbeans.modules.openide.filesystems.RecognizeInstanceFiles")) { // NOI18N
            // OK for openide.filesystems
            return;
        }
        if (getClass().getName().equals("org.netbeans.modules.settings.RecognizeInstanceObjects")) { // NOI18N
            // OK for settings
            return;
        }
        throw new IllegalStateException();
    }

    /** Create the lookup for given path. Called as a result of query to
     * {@link Lookups#forPath(java.lang.String)}.
     *
     * @param path the identification of the path
     * @return the lookup representing objects in this path.
     */
    protected abstract Lookup create(String path);
    
    /** Finds a config object under given path. Called from {@link FileUtil#getConfigObject}.
     * @param filePath path to .instance or .settings file
     * @param type the requested type for given object
     * @return either null or instance of requested type
     * @since 8.10 
     */
    protected <T> T lookupObject(String path, Class<T> type) {
        return create(path).lookup(type);
    }

    /** Method for providers to work in orchestration with {@link #createLookupFor(java.lang.Object)}.
     * By default return <code>null</code>.
     * 
     * @param obj the object to find lookup for
     * @return <code>null</code> or new lookup to be associated with the <code>obj</code>
     * @since 8.17
     */
    protected Lookup lookupFor(Object obj) {
        return null;
    }
}
