/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
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

package org.netbeans.modules.websvc.api.jaxws.project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.modules.j2ee.deployment.devmodules.api.Deployment;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eePlatform;
import org.netbeans.modules.websvc.jaxwsmodel.project.UserQuestionHandler;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.netbeans.spi.project.support.ant.PropertyEvaluator;
import org.openide.ErrorManager;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.util.Mutex;
import org.openide.util.MutexException;
import org.openide.util.NbBundle;
import org.openide.util.UserQuestionException;
import org.openide.util.Utilities;

/**
 * Helps a project type (re-)generate, and manage the state and versioning of,
 * <code>build.xml</code> and <code>build-impl.xml</code>.
 * @author Jesse Glick
 */
public final class GeneratedFilesHelper {
    
    /**
     * Relative path from project directory to the user build script,
     * <code>build.xml</code>.
     */
    public static final String BUILD_XML_PATH = "build.xml"; // NOI18N
    
    /**
     * Relative path from project directory to the implementation build script,
     * <code>build-impl.xml</code>.
     */
    public static final String BUILD_IMPL_XML_PATH = "nbproject/build-impl.xml"; // NOI18N
    
    /**
     * Relative path from project directory to the jax-ws configuration file
     * <code>jax-ws.xml</code>.
     */
    public static final String JAX_WS_XML_PATH = "nbproject/jax-ws.xml"; // NOI18N
    
    /**
     * Path to file storing information about generated files.
     * It should be kept in version control, since it applies equally after a fresh
     * project checkout; it does not apply to running Ant, so should not be in
     * <code>project.properties</code>; and it includes a CRC of <code>project.xml</code>
     * so it cannot be in that file either. It could be stored in some special
     * comment at the end of the build script (e.g.) but many users would just
     * compulsively delete it in this case since it looks weird.
     */
    static final String GENFILES_PROPERTIES_PATH = "nbproject/genfiles.properties"; // NOI18N
    
    /** Suffix (after script path) of {@link #GENFILES_PROPERTIES_PATH} key for <code>project.xml</code> CRC. */
    private static final String KEY_SUFFIX_DATA_CRC = ".data.CRC32"; // NOI18N
    
    /** Suffix (after script path) of {@link #GENFILES_PROPERTIES_PATH} key for stylesheet CRC. */
    private static final String KEY_SUFFIX_STYLESHEET_CRC = ".stylesheet.CRC32"; // NOI18N
    
    /** Suffix (after script path) of {@link #GENFILES_PROPERTIES_PATH} key for CRC of the script itself. */
    private static final String KEY_SUFFIX_SCRIPT_CRC = ".script.CRC32"; // NOI18N
    
    /** Suffix (after script path) of {@link #GENFILES_PROPERTIES_PATH} key for <code>jax-ws.xml</code> CRC. */
    private static final String KEY_SUFFIX_JAX_WS_CRC = ".jax-ws.CRC32"; // NOI18N
    
    /**
     * A build script is missing from disk.
     * This is mutually exclusive with the other flags.
     * @see #getBuildScriptState
     */
    public static final int FLAG_MISSING = 2 << 0;
    
    static final String JAXWS_20_LIB = "jaxws20lib";
    static final String JAXWS_21_LIB = "jaxws21lib";
    static final String JAXWS_VERSION = "jaxwsversion";
    
    /**
     * A build script has been modified since last generated by
     * {@link #generateBuildScriptFromStylesheet}.
     * <p class="nonnormative">
     * Probably this means it was edited by the user.
     * </p>
     * @see #getBuildScriptState
     */
    public static final int FLAG_MODIFIED = 2 << 1;
    
    /**
     * A build script was generated from an older version of <code>project.xml</code>.
     * It was last generated using a different version of <code>project.xml</code>,
     * so using the current <code>project.xml</code> might produce a different
     * result.
     * <p class="nonnormative">
     * This is quite likely in the case of
     * <code>build.xml</code>; in the case of <code>build-impl.xml</code>, it
     * probably means that the user edited <code>project.xml</code> manually,
     * since if that were modified from {@link AntProjectHelper} methods and
     * the project were saved, the script would have been regenerated
     * already.
     * </p>
     * @see #getBuildScriptState
     */
    public static final int FLAG_OLD_PROJECT_XML = 2 << 2;
    
    /**
     * A build script was generated from an older version of a stylesheet.
     * It was last generated using a different (probably older) version of the
     * XSLT stylesheet, so using the current stylesheet might produce a different
     * result.
     * <p class="nonnormative">
     * Probably this means the project type
     * provider module has been upgraded since the project was last saved (in
     * the case of <code>build-impl.xml</code>) or created (in the case of
     * <code>build.xml</code>).
     * </p>
     * @see #getBuildScriptState
     */
    public static final int FLAG_OLD_STYLESHEET = 2 << 3;
    public static final int FLAG_OLD_JAX_WS = 2 << 5;
    
    /**
     * The build script exists, but nothing else is known about it.
     * This flag is mutually exclusive with {@link #FLAG_MISSING} but
     * when set also sets {@link #FLAG_MODIFIED}, {@link #FLAG_OLD_STYLESHEET},
     * and {@link #FLAG_OLD_PROJECT_XML} - since it is not known whether these
     * conditions might obtain, it is safest to assume they do.
     * <p class="nonnormative">
     * Probably this means that <code>nbproject/genfiles.properties</code> was
     * deleted by the user.
     * </p>
     * @see #getBuildScriptState
     */
    public static final int FLAG_UNKNOWN = 2 << 4;
    
    /** Associated project helper, or null if using only a directory. */
    private final AntProjectHelper h;
    
    /** Project directory. */
    private final FileObject dir;
    
    /**
     * Create a helper based on the supplied project helper handle.
     * @param h an Ant-based project helper supplied to the project type provider
     */
    public GeneratedFilesHelper(AntProjectHelper h) {
        this.h = h;
        dir = h.getProjectDirectory();
    }
    
    /**
     * Create a helper based only on a project directory.
     * This can be used to perform the basic refresh functionality
     * without being the owner of the project.
     * It is only intended for use from the offline Ant task to
     * refresh a project, and similar special situations.
     * For normal circumstances please use only
     * {@link GeneratedFilesHelper#GeneratedFilesHelper(AntProjectHelper)}.
     * @param d the project directory
     * @throws IllegalArgumentException if the supplied directory has no <code>project.xml</code>
     */
    public GeneratedFilesHelper(FileObject d) {
        if (d == null || !d.isFolder() || d.getFileObject(AntProjectHelper.PROJECT_XML_PATH) == null) {
            throw new IllegalArgumentException("Does not look like an Ant-based project: " + d); // NOI18N
        }
        h = null;
        dir = d;
    }
    
    /**
     * Create <code>build.xml</code> or <code>nbproject/build-impl.xml</code>
     * from <code>project.xml</code> plus a supplied XSLT stylesheet.
     * This is the recommended way to create the build scripts from
     * project metadata.
     * <p class="nonnormative">
     * You may wish to first check {@link #getBuildScriptState} to decide whether
     * you really want to overwrite an existing build script. Typically if you
     * find {@link #FLAG_MODIFIED} you should not overwrite it; or ask for confirmation
     * first; or make a backup. Of course if you find neither of {@link #FLAG_OLD_STYLESHEET}
     * nor {@link #FLAG_OLD_PROJECT_XML} then there is no reason to overwrite the
     * script to begin with.
     * </p>
     * <p>
     * Acquires write access.
     * </p>
     * @param path a project-relative file path such as {@link #BUILD_XML_PATH} or {@link #BUILD_IMPL_XML_PATH}
     * @param stylesheet a URL to an XSLT stylesheet accepting <code>project.xml</code>
     *                   as input and producing the build script as output
     * @throws IOException if transforming or writing the output failed
     * @throws IllegalStateException if the project was modified (and not being saved)
     */
    public void generateBuildScriptFromStylesheet(final String path, final URL stylesheet, final FileObject jaxWsFo) throws IOException, IllegalStateException {
        if (path == null) {
            throw new IllegalArgumentException("Null path"); // NOI18N
        }
        if (stylesheet == null) {
            throw new IllegalArgumentException("Null stylesheet"); // NOI18N
        }
        try {
            ProjectManager.mutex().writeAccess(new Mutex.ExceptionAction<Object>() {
                public Object run() throws IOException {
                    //                    if (h != null && h.isProjectXmlModified()) {
                    //                        throw new IllegalStateException("Cannot generate build scripts from a modified project"); // NOI18N
                    //                    }
                    // Need to use an atomic action since otherwise creating new build scripts might
                    // cause them to not be recognized as Ant scripts.
                    dir.getFileSystem().runAtomicAction(new FileSystem.AtomicAction() {
                        public void run() throws IOException {
                            
                            FileObject projectXml = dir.getFileObject(AntProjectHelper.PROJECT_XML_PATH);
                            final FileObject buildScriptXml = FileUtil.createData(dir, path);
                            byte[] projectXmlData;
                            InputStream is = projectXml.getInputStream();
                            try {
                                projectXmlData = load(is);
                            } finally {
                                is.close();
                            }
                            byte[] stylesheetData;
                            is = stylesheet.openStream();
                            try {
                                stylesheetData = load(is);
                            } finally {
                                is.close();
                            }
                            final byte[] resultData;
                            FileObject jaxWsFileObject=jaxWsFo;
                            if (jaxWsFo==null || !jaxWsFo.isValid()) {
                                jaxWsFileObject = findJaxWsFileObject(dir);
                            }
                            
                            TransformerFactory tf = TransformerFactory.newInstance();
                            try {
                                StreamSource stylesheetSource = new StreamSource(
                                        new ByteArrayInputStream(stylesheetData), stylesheet.toExternalForm());
                                Transformer t = tf.newTransformer(stylesheetSource);
                                if (jaxWsFileObject!=null) t.setParameter("jax_ws_uri",jaxWsFileObject.getURL().toURI().toASCIIString()); //NOI18N
                                if(isJAXWS21()){
                                    t.setParameter(JAXWS_VERSION, JAXWS_21_LIB );
                                }else{
                                    t.setParameter(JAXWS_VERSION, JAXWS_20_LIB );
                                }   
                                File projectXmlF = FileUtil.toFile(projectXml);
                                assert projectXmlF != null;
                                StreamSource projectXmlSource = new StreamSource(
                                        new ByteArrayInputStream(projectXmlData), projectXmlF.toURI().toString());
                                ByteArrayOutputStream result = new ByteArrayOutputStream();
                                t.transform(projectXmlSource, new StreamResult(result));
                                resultData = result.toByteArray();
                            } catch (TransformerException e) {
                                throw (IOException)new IOException(e.toString()).initCause(e);
                            } catch (URISyntaxException e) {
                                throw (IOException)new IOException(e.toString()).initCause(e);
                            }
                            // Update genfiles.properties too.
                            final EditableProperties p = new EditableProperties(true);
                            FileObject genfiles = dir.getFileObject(GENFILES_PROPERTIES_PATH);
                            if (genfiles != null && genfiles.isVirtual()) {
                                // #55164: deleted from CVS?
                                genfiles = null;
                            }
                            if (genfiles != null) {
                                is = genfiles.getInputStream();
                                try {
                                    p.load(is);
                                } finally {
                                    is.close();
                                }
                            }
                            p.setProperty(path + KEY_SUFFIX_DATA_CRC,
                                    getCrc32(new ByteArrayInputStream(projectXmlData), projectXml));
                            if (genfiles == null) {
                                // New file, set a comment on it. XXX this puts comment in middle if write build-impl.xml before build.xml
                                p.setComment(path + KEY_SUFFIX_DATA_CRC, new String[] {
                                    "# " + NbBundle.getMessage(GeneratedFilesHelper.class, "COMMENT_genfiles.properties_1"), // NOI18N
                                    "# " + NbBundle.getMessage(GeneratedFilesHelper.class, "COMMENT_genfiles.properties_2"), // NOI18N
                                }, false);
                            }
                            p.setProperty(path + KEY_SUFFIX_STYLESHEET_CRC,
                                    getCrc32(new ByteArrayInputStream(stylesheetData), stylesheet));
                            p.setProperty(path + KEY_SUFFIX_SCRIPT_CRC,
                                    computeCrc32(new ByteArrayInputStream(resultData)));
                            if (jaxWsFileObject!=null) {
                                byte[] jaxWsData;
                                InputStream jaxWsIs = jaxWsFileObject.getInputStream();
                                try {
                                    jaxWsData = load(jaxWsIs);
                                } finally {
                                    jaxWsIs.close();
                                }
                                p.setProperty(path + KEY_SUFFIX_JAX_WS_CRC,
                                        getCrc32(new ByteArrayInputStream(jaxWsData),jaxWsFileObject));
                            }
                            if (genfiles == null) {
                                genfiles = FileUtil.createData(dir, GENFILES_PROPERTIES_PATH);
                            }
                            final FileObject _genfiles = genfiles;
                            // You get the Spaghetti Code Award if you can follow the control flow in this method!
                            // Now is the time when you wish Java implemented call/cc.
                            // If you didn't understand that last comment, you don't get the Spaghetti Code Award.
                            final FileSystem.AtomicAction body = new FileSystem.AtomicAction() {
                                public void run() throws IOException {
                                    // Try to acquire both locks together, since we need them both written.
                                    FileLock lock1 = buildScriptXml.lock();
                                    try {
                                        FileLock lock2 = _genfiles.lock();
                                        try {
                                            OutputStream os1 = new EolFilterOutputStream(buildScriptXml.getOutputStream(lock1));
                                            try {
                                                OutputStream os2 = _genfiles.getOutputStream(lock2);
                                                try {
                                                    os1.write(resultData);
                                                    p.store(os2);
                                                } finally {
                                                    os2.close();
                                                }
                                            } finally {
                                                os1.close();
                                            }
                                        } finally {
                                            lock2.releaseLock();
                                        }
                                    } finally {
                                        lock1.releaseLock();
                                    }
                                }
                            };
                            try {
                                body.run();
                            } catch (UserQuestionException uqe) {
                                // #57480: need to regenerate build-impl.xml, really.
                                UserQuestionHandler.handle(uqe, new UserQuestionHandler.Callback() {
                                    public void accepted() {
                                        // Try again.
                                        try {
                                            body.run();
                                        } catch (UserQuestionException uqe2) {
                                            // Need to try one more time - may have locked bSX but not yet gf.
                                            UserQuestionHandler.handle(uqe2, new UserQuestionHandler.Callback() {
                                                public void accepted() {
                                                    try {
                                                        body.run();
                                                    } catch (IOException e) {
                                                        ErrorManager.getDefault().notify(e);
                                                    }
                                                }
                                                public void denied() {}
                                                public void error(IOException e) {
                                                    ErrorManager.getDefault().notify(e);
                                                }
                                            });
                                        } catch (IOException e) {
                                            // Oh well.
                                            ErrorManager.getDefault().notify(e);
                                        }
                                    }
                                    public void denied() {
                                        // OK, skip it.
                                    }
                                    public void error(IOException e) {
                                        ErrorManager.getDefault().notify(e);
                                        // Never mind.
                                    }
                                });
                            }
                        }
                    });
                    return null;
                }
            });
        } catch (MutexException e) {
            throw (IOException)e.getException();
        }
    }
    
    private boolean isJAXWS21(){

        Map properties = h.getStandardPropertyEvaluator().getProperties();
        String serverInstance = (String)properties.get("j2ee.server.instance"); //NOI18N
        if (serverInstance != null) {    
            J2eePlatform j2eePlatform = Deployment.getDefault().getJ2eePlatform(serverInstance);
            if (j2eePlatform != null && j2eePlatform.isToolSupported(J2eePlatform.TOOL_JSR109)) {
               File[] roots = j2eePlatform.getPlatformRoots();
               if(roots.length > 0){
                   File appSvrRoot = roots[0];
                   File dtdFile = new File(appSvrRoot, "lib" + 
                           File.separator + "dtds" + 
                           File.separator + "sun-domain_1_3.dtd");
                   if(dtdFile.exists()){
                       return true;
                   }
               }
            }
        }
        return false;
    }
    
    /**
     * Load data from a stream into a buffer.
     */
    private static byte[] load(InputStream is) throws IOException {
        int size = Math.max(1024, is.available()); // #46235
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        byte[] buf = new byte[size];
        int read;
        while ((read = is.read(buf)) != -1) {
            baos.write(buf, 0, read);
        }
        return baos.toByteArray();
    }
    
    /**
     * Find what state a build script is in.
     * This may be used by a project type provider to decide whether to create
     * or overwrite it, and whether to produce a backup in the latter case.
     * Various abnormal conditions are detected:
     * {@link #FLAG_MISSING}, {@link #FLAG_MODIFIED}, {@link #FLAG_OLD_PROJECT_XML},
     * {@link #FLAG_OLD_STYLESHEET}, and {@link #FLAG_UNKNOWN}.
     * <p class="nonnormative">
     * Currently {@link #FLAG_MODIFIED}, {@link #FLAG_OLD_STYLESHEET}, and
     * {@link #FLAG_OLD_PROJECT_XML} are detected by computing a CRC-32
     * of the script when it is created, as well as the CRC-32s of the
     * stylesheet and <code>project.xml</code>. These CRCs are stored
     * in a special file <code>nbproject/genfiles.properties</code>.
     * The CRCs are based on the textual
     * contents of the files (so even changed to whitespace etc. are considered
     * changes), but are independent of platform newline conventions (since e.g.
     * CVS will by default replace \n with \r\n when checking out on Windows).
     * Changes to external files included into <code>project.xml</code> or the
     * stylesheet (e.g. using XSLT's import facility) are <em>not</em> detected.
     * </p>
     * <p>
     * If there is some kind of I/O error reading any files, {@link #FLAG_UNKNOWN}
     * is returned (in conjunction with {@link #FLAG_MODIFIED},
     * {@link #FLAG_OLD_STYLESHEET}, and {@link #FLAG_OLD_PROJECT_XML} to be safe).
     * </p>
     * <p>
     * Acquires read access.
     * </p>
     * @param path a project-relative path such as {@link #BUILD_XML_PATH} or {@link #BUILD_IMPL_XML_PATH}
     * @param stylesheet a URL to an XSLT stylesheet accepting <code>project.xml</code>
     *                   as input and producing the build script as output
     *                   (should match that given to {@link #generateBuildScriptFromStylesheet})
     * @return a bitwise OR of various flags, or <code>0</code> if the script
     *         is present on disk and fully up-to-date
     * @throws IllegalStateException if the project was modified
     */
    
    public int getBuildScriptState(final String path, final URL stylesheet, final FileObject jaxWsFo) throws IllegalStateException {
        try {
            return ProjectManager.mutex().readAccess(new Mutex.ExceptionAction<Integer>() {
                public Integer run() throws IOException {
                    //                    if (h != null && h.isProjectXmlModified()) {
                    //                        throw new IllegalStateException("Cannot generate build scripts from a modified project"); // NOI18N
                    //                    }
                    FileObject script = dir.getFileObject(path);
                    if (script == null || /* #55164 */script.isVirtual()) {
                        return new Integer(FLAG_MISSING);
                    }
                    int flags = 0;
                    Properties p = new Properties();
                    FileObject genfiles = dir.getFileObject(GENFILES_PROPERTIES_PATH);
                    if (genfiles == null || /* #55164 */genfiles.isVirtual()) {
                        // Who knows? User deleted it; anything might be wrong. Safest to assume
                        // that everything is.
                        return new Integer(FLAG_UNKNOWN | FLAG_MODIFIED |
                                FLAG_OLD_PROJECT_XML | FLAG_OLD_STYLESHEET | FLAG_OLD_JAX_WS);
                    }
                    InputStream is = new BufferedInputStream(genfiles.getInputStream());
                    try {
                        p.load(is);
                    } finally {
                        is.close();
                    }
                    FileObject projectXml = dir.getFileObject(AntProjectHelper.PROJECT_XML_PATH);
                    if (projectXml != null && /* #55164 */!projectXml.isVirtual()) {
                        String crc = getCrc32(projectXml);
                        if (!crc.equals(p.getProperty(path + KEY_SUFFIX_DATA_CRC))) {
                            flags |= FLAG_OLD_PROJECT_XML;
                        }
                    } else {
                        // Broken project?!
                        flags |= FLAG_OLD_PROJECT_XML;
                    }
                    FileObject jaxWsFileObject=findJaxWsFileObject(dir);
                    if (jaxWsFileObject!=null) {
                        String crc = getCrc32(jaxWsFileObject);
                        if (!crc.equals(p.getProperty(path + KEY_SUFFIX_JAX_WS_CRC))) {
                            flags |= FLAG_OLD_JAX_WS;
                        }
                    }
                    String crc = getCrc32(stylesheet);
                    if (!crc.equals(p.getProperty(path + KEY_SUFFIX_STYLESHEET_CRC))) {
                        flags |= FLAG_OLD_STYLESHEET;
                    }
                    crc = getCrc32(script);
                    if (!crc.equals(p.getProperty(path + KEY_SUFFIX_SCRIPT_CRC))) {
                        flags |= FLAG_MODIFIED;
                    }
                    return new Integer(flags);
                }
            }).intValue();
        } catch (MutexException e) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, (IOException)e.getException());
            return FLAG_UNKNOWN | FLAG_MODIFIED | FLAG_OLD_PROJECT_XML | FLAG_OLD_STYLESHEET | FLAG_OLD_JAX_WS;
        }
    }
    
    /**
     * Compute the CRC-32 of the contents of a stream.
     * \r\n and \r are both normalized to \n for purposes of the calculation.
     */
    static String computeCrc32(InputStream is) throws IOException {
        Checksum crc = new CRC32();
        int last = -1;
        int curr;
        while ((curr = is.read()) != -1) {
            if (curr != '\n' && last == '\r') {
                crc.update('\n');
            }
            if (curr != '\r') {
                crc.update(curr);
            }
            last = curr;
        }
        if (last == '\r') {
            crc.update('\n');
        }
        int val = (int)crc.getValue();
        String hex = Integer.toHexString(val);
        while (hex.length() < 8) {
            hex = "0" + hex; // NOI18N
        }
        return hex;
    }
    
    // #50440 - cache CRC32's for various files to save time esp. during startup.
    
    private static final Map<URL, String> crcCache = new HashMap<URL, String>();
    private static final Map<URL, Long> crcCacheTimestampsXorSizes = new HashMap<URL, Long>();
    
    /** Try to find a CRC in the cache according to location of file and last mod time xor size. */
    private static synchronized String findCachedCrc32(URL u, long footprint) {
        String crc = crcCache.get(u);
        if (crc != null) {
            Long l = crcCacheTimestampsXorSizes.get(u);
            assert l != null;
            if (l.longValue() == footprint) {
                // Cache hit.
                return crc;
            }
        }
        // Cache miss - missing or old.
        return null;
    }
    
    /** Cache a known CRC for a file, using current last mod time xor size. */
    private static synchronized void cacheCrc32(String crc, URL u, long footprint) {
        crcCache.put(u, crc);
        crcCacheTimestampsXorSizes.put(u, new Long(footprint));
    }
    
    /** Find (maybe cached) CRC for a file, using a preexisting input stream (not closed by this method). */
    private static String getCrc32(InputStream is, FileObject fo) throws IOException {
        URL u = fo.getURL();
        fo.refresh(); // in case was written on disk and we did not notice yet...
        long footprint = fo.lastModified().getTime() ^ fo.getSize();
        String crc = findCachedCrc32(u, footprint);
        if (crc == null) {
            crc = computeCrc32(is);
            cacheCrc32(crc, u, footprint);
        }
        return crc;
    }
    
    /** Find the time the file this URL represents was last modified xor its size, if possible. */
    private static long checkFootprint(URL u) {
        URL nested = FileUtil.getArchiveFile(u);
        if (nested != null) {
            u = nested;
        }
        if (u.getProtocol().equals("file")) { // NOI18N
            File f = new File(URI.create(u.toExternalForm()));
            return f.lastModified() ^ f.length();
        } else {
            return 0L;
        }
    }
    
    /** Find (maybe cached) CRC for a URL, using a preexisting input stream (not closed by this method). */
    private static String getCrc32(InputStream is, URL u) throws IOException {
        long footprint = checkFootprint(u);
        String crc = null;
        if (footprint != 0L) {
            crc = findCachedCrc32(u, footprint);
        }
        if (crc == null) {
            crc = computeCrc32(is);
            if (footprint != 0L) {
                cacheCrc32(crc, u, footprint);
            }
        }
        return crc;
    }
    
    /** Find (maybe cached) CRC for a file. Will open its own input stream. */
    private static String getCrc32(FileObject fo) throws IOException {
        URL u = fo.getURL();
        fo.refresh();
        long footprint = fo.lastModified().getTime() ^ fo.getSize();
        String crc = findCachedCrc32(u, footprint);
        if (crc == null) {
            InputStream is = fo.getInputStream();
            try {
                crc = computeCrc32(new BufferedInputStream(is));
                cacheCrc32(crc, u, footprint);
            } finally {
                is.close();
            }
        }
        return crc;
    }
    
    /** Find (maybe cached) CRC for a URL. Will open its own input stream. */
    private static String getCrc32(URL u) throws IOException {
        long footprint = checkFootprint(u);
        String crc = null;
        if (footprint != 0L) {
            crc = findCachedCrc32(u, footprint);
        }
        if (crc == null) {
            InputStream is = u.openStream();
            try {
                crc = computeCrc32(new BufferedInputStream(is));
                if (footprint != 0L) {
                    cacheCrc32(crc, u, footprint);
                }
            } finally {
                is.close();
            }
        }
        return crc;
    }
    
    /**
     * Convenience method to refresh a build script if it can and should be.
     * <p>
     * If the script is not modified, and it is either missing, or the flag
     * <code>checkForProjectXmlModified</code> is false, or it is out of date with
     * respect to either <code>project.xml</code> or the stylesheet (or both),
     * it is (re-)generated.
     * </p>
     * <p>
     * Acquires write access.
     * </p>
     * <p class="nonnormative">
     * Typical usage from {@link ProjectXmlSavedHook#projectXmlSaved} is to call
     * this method for both {@link #BUILD_XML_PATH} and {@link #BUILD_IMPL_XML_PATH}
     * with the appropriate stylesheets and with <code>checkForProjectXmlModified</code>
     * false (the script is certainly out of date relative to <code>project.xml</code>).
     * Typical usage from {@link org.netbeans.spi.project.ui.ProjectOpenedHook#projectOpened} is to call
     * this method for both scripts with the appropriate stylesheets and with
     * <code>checkForProjectXmlModified</code> true.
     * </p>
     * @param path a project-relative path such as {@link #BUILD_XML_PATH} or {@link #BUILD_IMPL_XML_PATH}
     * @param stylesheet a URL to an XSLT stylesheet accepting <code>project.xml</code>
     *                   as input and producing the build script as output
     * @param checkForProjectXmlModified true if it is necessary to check whether the
     *                                script is out of date with respect to
     *                                <code>project.xml</code> and/or the stylesheet
     * @return true if the script was in fact regenerated
     * @throws IOException if transforming or writing the output failed
     * @throws IllegalStateException if the project was modified
     */
    public boolean refreshBuildScript(final String path, final URL stylesheet, final FileObject jaxWsFo, final boolean checkForProjectXmlModified) throws IOException, IllegalStateException {
        try {
            return ProjectManager.mutex().writeAccess(new Mutex.ExceptionAction<Boolean>() {
                public Boolean run() throws IOException {
                    int flags = getBuildScriptState(path, stylesheet, jaxWsFo);
                    if (shouldGenerateBuildScript(flags, checkForProjectXmlModified)) {
                        generateBuildScriptFromStylesheet(path, stylesheet, jaxWsFo);
                        return Boolean.TRUE;
                    } else {
                        return Boolean.FALSE;
                    }
                }
            }).booleanValue();
        } catch (MutexException e) {
            throw (IOException)e.getException();
        }
    }
    
    private static boolean shouldGenerateBuildScript(int flags, boolean checkForProjectXmlModified) {
        if ((flags & GeneratedFilesHelper.FLAG_MISSING) != 0) {
            // Yes, need it.
            return true;
        }
        if ((flags & GeneratedFilesHelper.FLAG_MODIFIED) != 0) {
            // No, don't overwrite a user build script.
            // XXX modified build-impl.xml probably counts as a serious condition
            // to warn the user about...
            // Modified build.xml is no big deal.
            return false;
        }
        if (!checkForProjectXmlModified) {
            // OK, assume it is out of date.
            return true;
        }
        // Check whether it is in fact out of date.
        return (flags & (GeneratedFilesHelper.FLAG_OLD_PROJECT_XML |
                GeneratedFilesHelper.FLAG_OLD_STYLESHEET | FLAG_OLD_JAX_WS)) != 0;
    }
    
    // #45373 - workaround: on Windows make sure that all lines end with CRLF.
    // marcow: Use at least some buffered output!
    private static class EolFilterOutputStream extends BufferedOutputStream {
        
        private boolean isActive = Utilities.isWindows();
        private int last = -1;
        
        public EolFilterOutputStream(OutputStream os) {
            super(os, 4096);
        }
        
        public void write(byte[] b, int off, int len) throws IOException {
            if (isActive) {
                for (int i = off; i < off + len; i++) {
                    write(b[i]);
                }
            } else {
                super.write(b, off, len);
            }
        }
        
        public void write(int b) throws IOException {
            if (isActive) {
                if (b == '\n' && last != '\r') {
                    super.write('\r');
                }
                last = b;
            }
            super.write(b);
        }
        
    }
    
    private FileObject findJaxWsFileObject(FileObject projectDir) {
        return projectDir.getFileObject(JAX_WS_XML_PATH);
    }
    
}
