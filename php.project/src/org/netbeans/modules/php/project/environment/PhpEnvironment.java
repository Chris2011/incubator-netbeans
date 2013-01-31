/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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

package org.netbeans.modules.php.project.environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.php.api.util.FileUtils;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;

/**
 * Class for getting all the possible PHP environment, e.g. document roots, PHP interpreters.
 * This class is OS dependent.
 * <p>
 * Documents roots could be "/var/www" for Linux, "C:\Program Files\Apache\htdocs" for Windows etc.
 * @author Tomas Mysik
 */
public abstract class PhpEnvironment {
    public static final DocumentRoot PENDING_DOCUMENT_ROOT = new DocumentRoot(NbBundle.getMessage(PhpEnvironment.class, "LBL_PleaseWait"), null, null, true);
    protected static final Logger LOGGER = Logger.getLogger(PhpEnvironment.class.getName());

    static final String HTDOCS = "htdocs"; //NOI18N
    static final FilenameFilter APACHE_FILENAME_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().startsWith("apache"); // NOI18N
        }
    };
    private static final PhpEnvironment UNKNOWN_PHP_ENVIRONMENT = new UnknownPhpEnvironment();

    private static final RequestProcessor READ_DOCUMENT_ROOTS_THREAD = new RequestProcessor("Read document roots", 3); // NOI18N

    PhpEnvironment() {
    }

    /**
     * Get the OS dependent PHP environment.
     * @return the OS dependent PHP environment.
     */
    public static PhpEnvironment get() {
        if (isSolaris()) {
            return new SolarisPhpEnvironment();
        } else if (Utilities.isWindows()) {
            return new WindowsPhpEnvironment();
        } else if (Utilities.isMac()) {
            return new MacPhpEnvironment();
        } else if (Utilities.isUnix()) {
            return new UnixPhpEnvironment();
        }
        return UNKNOWN_PHP_ENVIRONMENT;
    }

    /**
     * Asynchronously get the OS dependent {@link DocumentRoot roots} of document roots and their URLs for the given project name.
     * @param notifier code that is called after document roots are read.
     * @param projectName project name for which the directory in the document root is searched.
     *                    Can be <code>null</code> if it is not needed.
     * @return "pending" {@link DocumentRoot document root}, see {@link #PENDING_DOCUMENT_ROOT}.
     */
    public DocumentRoot readDocumentRoots(final ReadDocumentRootsNotifier notifier, final String projectName) {
        assert notifier != null;
        RequestProcessor.Task readDocumentRootsTask = READ_DOCUMENT_ROOTS_THREAD.create(new Runnable() {
            @Override
            public void run() {
                List<DocumentRoot> documentRoots = getDocumentRoots(projectName);
                assert documentRoots != null;
                notifier.finished(documentRoots);
            }
        });
        readDocumentRootsTask.schedule(0);
        return PENDING_DOCUMENT_ROOT;
    }

    /**
     * Asynchronously get the OS dependent {@link DocumentRoot roots} of document roots and their URLs.
     * @param notifier code that is called after document roots are read.
     * @return "pending" {@link DocumentRoot document root}, see {@link #PENDING_DOCUMENT_ROOT}.
     * @see #readDocumentRoots(ReadDocumentRootsNotifier, String)
     */
    public DocumentRoot readDocumentRoots(ReadDocumentRootsNotifier notifier) {
        return readDocumentRoots(notifier, null);
    }


    /**
     * Get the list of all found PHP command line interpreters. The list can be empty.
     * @return list of all found PHP CLIs, never <code>null</code>.
     * @see #getAnyPhpInterpreter()
     */
    public abstract List<String> getAllPhpInterpreters();

    /**
     * Get any PHP command line interpreter.
     * @return PHP CLI or <code>null</code> if none found.
     */
    public String getAnyPhpInterpreter() {
        List<String> allPhpInterpreters = getAllPhpInterpreters();
        if (allPhpInterpreters.isEmpty()) {
            return null;
        }
        // return the first one
        return allPhpInterpreters.get(0);
    }

    protected abstract List<DocumentRoot> getDocumentRoots(String projectName);

    /**
     * Document root and its URL. It also contains flag whether this pair is preferred or not
     * (e.g. "~/public_html" is preferred to "/var/www" on Linux). Only writable directories can be preferred.
     */
    public static final class DocumentRoot {
        private final String documentRoot;
        private final String url;
        private final String hint;
        private final boolean preferred;

        public DocumentRoot(String documentRoot, String url, String hint, boolean preferred) {
            this.documentRoot = documentRoot;
            this.url = url;
            this.hint = hint;
            this.preferred = preferred;
        }

        public String getDocumentRoot() {
            return documentRoot;
        }

        public String getUrl() {
            return url;
        }

        public String getHint() {
            return hint;
        }

        public boolean isPreferred() {
            return preferred;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(200);
            sb.append(getClass().getName());
            sb.append(" { documentRoot : "); // NOI18N
            sb.append(documentRoot);
            sb.append(" , url : "); // NOI18N
            sb.append(url);
            sb.append(" , hint : "); // NOI18N
            sb.append(hint);
            sb.append(" , preferred : "); // NOI18N
            sb.append(preferred);
            sb.append(" }"); // NOI18N
            return sb.toString();
        }
    }

    static boolean isSolaris() {
        return (Utilities.getOperatingSystem() & Utilities.OS_SOLARIS) != 0
                || (Utilities.getOperatingSystem() & Utilities.OS_SUNOS) != 0;
    }

    static String getFolderName(File location, String name) {
        if (name == null) {
            return location.getAbsolutePath();
        }
        return new File(location, name).getAbsolutePath();
    }

    static String getDefaultUrl(String urlPart) {
        return getDefaultUrl(urlPart, null);
    }

    static String getDefaultUrl(String urlPart, Integer port) {
        StringBuilder url = new StringBuilder(100);
        url.append("http://localhost"); // NOI18N
        if (port != null) {
            url.append(":"); // NOI18N
            url.append(port);
        }
        url.append("/"); // NOI18N
        if (urlPart != null) {
            url.append(urlPart);
            url.append("/"); // NOI18N
        }
        return url.toString();
    }

    /**
     * Return "htdocs" directory or null.
     */
    static File findHtDocsDirectory(File startDir, FilenameFilter filenameFilter) {
        LOGGER.log(Level.FINE, "Searching for htdocs in {0}", startDir);
        String[] subDirNames = startDir.list(filenameFilter);
        if (subDirNames == null || subDirNames.length == 0) {
            return null;
        }
        for (String subDirName : subDirNames) {
            File subDir = new File(startDir, subDirName);
            File htDocs = new File(subDir, HTDOCS);
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.fine(String.format("\t%s - exists: %s, directory: %s, file: %s", htDocs, htDocs.exists(), htDocs.isDirectory(), htDocs.isFile()));
            }
            if (htDocs.isDirectory()) {
                return htDocs;
            }
            htDocs = findHtDocsDirectory(subDir, filenameFilter);
            if (htDocs != null && htDocs.isDirectory()) {
                return htDocs;
            }
        }
        return null;
    }

    /**
     * Return {@link DocumentRoot document root} for ~/public_html or <code>null</code>.
     * @param projectName project name for which the directory in the document root is searched.
     *                    Can be <code>null</code> if it is not needed.
     * @return {@link DocumentRoot document root} for ~/public_html or <code>null</code>.
     * @see #getDocumentRoots(java.lang.String)
     */
    static DocumentRoot getUserPublicHtmlDocumentRoot(String projectName) {
        DocumentRoot docRoot = null;
        // ~/public_html
        File userDir = new File(System.getProperty("user.home"), "public_html"); // NOI18N
        if (userDir.isDirectory()) {
            String documentRoot = getFolderName(userDir, projectName);
            String user = System.getProperty("user.name"); // NOI18N
            String urlSuffix = projectName != null ? "/" + projectName : ""; // NOI18N
            String url = getDefaultUrl("~" + user + urlSuffix); // NOI18N
            String hint = NbBundle.getMessage(PhpEnvironment.class, "TXT_UserDir");
            docRoot = new DocumentRoot(documentRoot, url, hint, FileUtils.isDirectoryWritable(userDir));
        }
        return docRoot;
    }

    static List<String> getAllPhpInterpreters(String phpFilename) {
        return FileUtils.findFileOnUsersPath(phpFilename);
    }

    public static interface ReadDocumentRootsNotifier {
        void finished(final List<DocumentRoot> documentRoots);
    }
}
