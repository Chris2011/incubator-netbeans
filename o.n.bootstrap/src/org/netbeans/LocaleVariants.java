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

package org.netbeans;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.NbBundle;
import org.openide.util.BaseUtilities;

final class LocaleVariants implements Stamps.Updater {
    private static final Logger err = Util.err;
    
    private static final Map<File,List<FileWithSuffix>> map;
    private static Locale mapLocale;
    private static LocaleVariants UPDATER;

    // Prevent accidental subclassing.
    private LocaleVariants() {
    }

    static {
        InputStream stream = Stamps.getModulesJARs().asStream("localeVariants");
        Map<File,List<FileWithSuffix>> tmp = new HashMap<File, List<FileWithSuffix>>();
        if (stream != null) try {
            DataInputStream is = new DataInputStream(stream);
            String locale = is.readUTF();
            mapLocale = Locale.getDefault().toString().equals(locale) ? Locale.getDefault() : null;
            for (;;) {
                String file = Stamps.readRelativePath(is);
                if (file.length() == 0) {
                    break;
                }
                List<FileWithSuffix> arr = new ArrayList<FileWithSuffix>();
                for (;;) {
                    FileWithSuffix fws = FileWithSuffix.read(is);
                    if (fws == null) {
                        break;
                    }
                    arr.add(fws);
                }
                arr = arr.isEmpty() ? Collections.<FileWithSuffix>emptyList() : arr;
                tmp.put(new File(file), arr);
            }
            is.close();
        } catch (IOException ex) {
            err.log(Level.WARNING, "Cannot read localeVariants cache", ex);
            tmp.clear();
        }
        map = tmp;
    }

    @Override
    public void flushCaches(DataOutputStream os) throws IOException {
        synchronized (map) {
            os.writeUTF(mapLocale.toString());
            for (Map.Entry<File, List<FileWithSuffix>> entry : map.entrySet()) {
                Stamps.writeRelativePath(entry.getKey().getPath(), os);
                for (FileWithSuffix fws : entry.getValue()) {
                    fws.write(os);
                }
                Stamps.writeRelativePath("", os);
            }
            Stamps.writeRelativePath("", os);
        }
    }

    @Override
    public void cacheReady() {
    }
    static void clearCaches() {
        synchronized (map) {
            map.clear();
        }
    }

    /**
     * Find existing locale variants of f, in search order.
     */
    static List<File> findLocaleVariantsOf(File f, String codeNameBase) {
        List<FileWithSuffix> result = findLocaleVariantsWithSuffixesOf(f, codeNameBase);
        List<File> l = new ArrayList<File>(result.size());
        for (FileWithSuffix fws : result) {
            l.add(fws.file);
        }
        return l;
    }


    static final class FileWithSuffix {
        public final File file;
        public final String suffix;
        FileWithSuffix(File file, String suffix) {
            this.file = file;
            this.suffix = suffix;
        }

        static FileWithSuffix read(DataInputStream is) throws IOException {
            String path = Stamps.readRelativePath(is);
            if (path.length() == 0) {
                return null;
            }
            String suffix = is.readUTF();
            return new FileWithSuffix(new File(path), suffix);
        }

        void write(DataOutputStream os) throws IOException {
            Stamps.writeRelativePath(file.getPath(), os);
            os.writeUTF(suffix);
        }
    }
    /**
     * Find existing locale variants of f, in search order.
     */
    static List<FileWithSuffix> findLocaleVariantsWithSuffixesOf(File f, String codeNameBase) {
        List<FileWithSuffix> res;
        synchronized (map) {
            if (mapLocale != Locale.getDefault()) {
                map.clear();
                mapLocale = Locale.getDefault();
            }
            res = map.get(f);
            if (res != null) {
                return res;
            }
        }

        if (! f.isFile()) {
            res = Collections.emptyList();
        } else {
            String logicalDir = null;
            {
                // #34069: we have to consider that e.g. modules/locale/foo_branding.jar might be
                // located in a different root of ${netbeans.dirs}, so need to use IFL. Here the
                // logical path would be "modules/foo.jar" for the base module.
                String logicalPath = findLogicalPath(f, codeNameBase);
                if (logicalPath != null) {
                    int slash = logicalPath.lastIndexOf('/');
                    if (slash != -1) {
                        logicalDir = logicalPath.substring(0, slash + 1) + "locale/"; // NOI18N
                    } else {
                        logicalDir = "locale/"; // NOI18N
                    }
                }
            }
            List<FileWithSuffix> l = new ArrayList<FileWithSuffix>(7);
            String nameExt = f.getName();
            int idx = nameExt.lastIndexOf('.'); // NOI18N
            String name, ext;
            if (idx != -1) {
                name = nameExt.substring(0, idx);
                ext = nameExt.substring(idx);
            } else {
                name = nameExt;
                ext = ""; // NOI18N
            }
            if (logicalDir != null) {
                for (String suffix : getLocalizingSuffixesFast()) {
                    String path = logicalDir + name + suffix + ext;
                    File v = InstalledFileLocator.getDefault().locate(path, codeNameBase, false);
                    if (v != null) {
                        l.add(new FileWithSuffix(v, suffix));
                    }
                }
            } else {
                File dir = new File(f.getParentFile(), "locale"); // NOI18N
                if (dir.exists()) {
                    for (String suffix : getLocalizingSuffixesFast()) {
                        File v = new File(dir, name + suffix + ext);
                        if (v.isFile()) {
                            l.add(new FileWithSuffix(v, suffix));
                        }
                    }
                }
            }
            res = l;
        }
        res = res.isEmpty() ? Collections.<FileWithSuffix>emptyList() : res;
        synchronized (map) {
            map.put(f, res);
        }
        if (UPDATER == null) {
            UPDATER = new LocaleVariants();
            Stamps.getModulesJARs().scheduleSave(UPDATER, "localeVariants", false);
        }
        return res;
    }
    
    /** Similar to {@link NbBundle#getLocalizingSuffixes} but optimized.
     * @since JST-PENDING: Called from InstalledFileLocatorImpl
     */
    static synchronized String[] getLocalizingSuffixesFast() {
        if (suffixes == null ||
                Locale.getDefault() != lastLocale ||
                !BaseUtilities.compareObjects(NbBundle.getBranding(), lastBranding)) {
            List<String> _suffixes = new ArrayList<String>();
            Iterator<String> it = NbBundle.getLocalizingSuffixes();
            while (it.hasNext()) {
                _suffixes.add(it.next());
            }
            suffixes = _suffixes.toArray(new String[_suffixes.size()]);
            lastLocale = Locale.getDefault();
            lastBranding = NbBundle.getBranding();
        }
        return suffixes;
    }
    private static String[] suffixes = null;
    private static Locale lastLocale = null;
    private static String lastBranding = null;

    /**
     * Find a path such that InstalledFileLocator.getDefault().locate(path, null, false)
     * yields the given file. Only guaranteed to work in case the logical path is a suffix of
     * the file's absolute path (after converting path separators); otherwise there is no
     * general way to invert locate(...) so this heuristic may fail. However for the IFL
     * implementation used in a plain NB installation (i.e.
     * org.netbeans.core.modules.InstalledFileLocatorImpl), this condition will in fact hold.
     * @return the inverse of locate(...), or null if there is no such path
     * @see "#34069"
     */
    private static String findLogicalPath(File f, String codeNameBase) {
        InstalledFileLocator l = InstalledFileLocator.getDefault();
        String path = f.getName();
        File parent = f.getParentFile();
        while (parent != null) {
            File probe = l.locate(path, codeNameBase, false);
            //System.err.println("Util.fLP: f=" + f + " parent=" + parent + " probe=" + probe + " f.equals(probe)=" + f.equals(probe));
            if (f.equals(probe)) {
                return path;
            }
            path = parent.getName() + '/' + path;
            parent = parent.getParentFile();
        }
        return null;
    }

}
