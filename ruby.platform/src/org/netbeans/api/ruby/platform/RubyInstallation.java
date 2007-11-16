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
package org.netbeans.api.ruby.platform;

import org.netbeans.modules.ruby.platform.Util;
import java.awt.Dialog;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.netbeans.api.options.OptionsDisplayer;
import org.netbeans.modules.gsfret.source.usages.ClassIndexManager;
import org.netbeans.napi.gsfret.source.Source;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * Information about a Ruby installation.
 *
 * @author Tor Norbye
 */
public class RubyInstallation {
    
    /** Used by tests. */
    static String TEST_GEM_HOME;
    
    private static final Logger LOGGER = Logger.getLogger(RubyInstallation.class.getName());
    
    private static final boolean PREINDEXING = Boolean.getBoolean("gsf.preindexing");

    /** NOTE: Keep this in sync with ruby/jruby/nbproject/project.properties */
    private static final String JRUBY_RELEASE = "1.0.2"; // NOI18N

    /** NOTE: Keep this in sync with ruby/jruby/nbproject/project.properties */
    public static final String DEFAULT_RUBY_RELEASE = "1.8"; // NOI18N
    private static final String JRUBY_RELEASEDIR = "jruby-" + JRUBY_RELEASE; // NOI18N
    
    /** Version number of the rubystubs */
    private static final String RUBYSTUBS_VERSION = "1.8.6-p110"; // NOI18N

    /**
     * MIME type for Ruby. Don't change this without also consulting the various XML files
     * that cannot reference this value directly, as well as RUBY_MIME_TYPE in the editing plugin
     */
    public static final String RUBY_MIME_TYPE = "text/x-ruby"; // NOI18N
    public static final String RHTML_MIME_TYPE = "application/x-httpd-eruby"; // NOI18N
    private static final String KEY_RUBY = "ruby"; //NOI18N
    private static final RubyInstallation INSTANCE = new RubyInstallation();
    private static boolean SKIP_INDEX_LIBS = System.getProperty("ruby.index.nolibs") != null; // NOI18N
    private static boolean SKIP_INDEX_GEMS = System.getProperty("ruby.index.nogems") != null; // NOI18N

    private Map<String,String> gemVersions;
    private Map<String,URL> gemUrls;
    private Set<URL> nonGemUrls;
    
    /** Regexp for matching version number in gem packages:  name-x.y.z
     * (we need to pull out x,y,z such that we can do numeric comparisons on them)
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(-\\S+)?"); // NOI18N
    private static final String SPECIFICATIONS = "specifications"; // NOI18N
    private static final String DOT_GEM_SPEC = ".gemspec"; // NOI18N
    
    private FileObject rubylibFo;
    private FileObject rubyStubsFo;
    private FileObject jrubyJavaSupport;
    private FileObject gemHomeFo;
    private String ruby;
    private String rubylib;
    private String gem;
    private String rake;
    private String rails;
    private String rdoc;
    private String jrubyHome;
    private String rubyHomeUrl;
    private String gemHomeUrl;
    private PropertyChangeSupport pcs;
    /** Map from gem name to maps from version to File */
    private Map<String, Map<String, File>> gemFiles;

    private final List<InterpreterLiveChangeListener> interpreterLCLs = new CopyOnWriteArrayList<InterpreterLiveChangeListener>();

    private RubyInstallation() {
    }
    
    /** Protected: for test access only */
    RubyInstallation(String initialRuby) {
        this.ruby = initialRuby;
    }

    public static RubyInstallation getInstance() {
        return INSTANCE;
    }

    // Ensure that JRuby can find its libraries etc.
    public void setJRubyLoadPaths() {
        String jh = getJRubyHome();
        if (jh != null) {
            System.setProperty("jruby.home", jh); // NOI18N
        }
    }

    public String getRubyBin() {
        return getRubyBin(true);
    }
    
    public String getRubyBin(boolean canonical) {
        String rubybin = null;
        String r = getRuby(canonical);

        if (r != null) {
            rubybin = new File(r).getParent();
        }
        return rubybin;
    }

    public String getRuby() {
        return getRuby(true);
    }
    
    public String getRuby(boolean canonical) {
        if (ruby == null) {
            // Test and preindexing hook
            ruby = System.getProperty("ruby.interpreter");

            if (ruby == null) { // Usually the case
                ruby = Util.getPreferences().get(KEY_RUBY, null);
                
                if (ruby == null) {
                    ruby = chooseRuby();
                    if (ruby != null) {
                        Util.getPreferences().put(KEY_RUBY, ruby);
                    }
                }

                if (ruby == null || ruby.equals("jruby")) { // NOI18N
                    ruby = getJRuby();
                }
            }

            // Let RepositoryUpdater and friends know where they can root preindexing
            // This should be done in a cleaner way.
            if (ruby != null) {
                org.netbeans.modules.gsfret.source.usages.Index.setPreindexRootUrl(getRubyHomeUrl());
            }
        }

        if (ruby != null && canonical) {
            try {
                return new File(ruby).getCanonicalFile().getAbsolutePath();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Cannot get canonical path", e);
            }
        }
        return ruby;
    }
    
    public String getJRuby() {
        String binDir = getJRubyBin();
        if (binDir == null) {
            return null;
        }

        String binary = Utilities.isWindows() ? "jruby.bat" : "jruby"; // NOI18N
        String jruby = binDir + File.separator + binary;

        // Normalize path
        try {
            jruby = new File(jruby).getCanonicalFile().getAbsolutePath();
        } catch (IOException ioe) {
            Exceptions.printStackTrace(ioe);
        }
        
        return jruby;
    }
    
    private String chooseRuby() {
        // Check the path to see if we find any other Ruby installations
        String path = System.getenv("PATH"); // NOI18N
        if (path == null) {
            path = System.getenv("Path"); // NOI18N
        }
        
        if (path != null) {
            final Set<String> rubies = new TreeSet<String>();
            Set<String> dirs = new TreeSet<String>(Arrays.asList(path.split(File.pathSeparator)));
            for (String dir : dirs) {
                File f = null;
                if (Utilities.isWindows()) {
                    f = new File(dir, "ruby.exe"); // NOI18N
                } else {
                    f = new File(dir, "ruby"); // NOI18N
                    // Don't include /usr/bin/ruby on the Mac - it's no good
                    // Source: http://developer.apple.com/tools/rubyonrails.html
                    //   "The version of Ruby that shipped on Mac OS X Tiger prior to 
                    //    v10.4.6 did not work well with Rails.   If you're running 
                    //    an earlier version of Tiger, you'll need to either upgrade 
                    //    to 10.4.6 or upgrade your copy of Ruby to version 1.8.4 or 
                    //    later using the open source distribution."
                    if (Utilities.isMac() && "/usr/bin/ruby".equals(f.getPath())) { // NOI18N
                        String version = System.getProperty("os.version"); // NOI18N
                        if (version == null || version.startsWith("10.4")) { // Only a problem on Tiger // NOI18N
                            continue;
                        }
                    }
                }
                if (f.exists()) {
                    try {
                        rubies.add(f.getCanonicalPath());
                    } catch (IOException e) {
                        LOGGER.log(Level.WARNING, "Cannot resolve cannonical path for: " + f, e);
                        rubies.add(f.getPath());
                    }
                }
            }
            
            if (rubies.size() > 0) {
                if (SwingUtilities.isEventDispatchThread()) {
                    return askForRuby(rubies);
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            String chosen = askForRuby(rubies);
                            if (chosen != null && !chosen.equals("jruby") && !chosen.equals(ruby)) { // NOI18N
                                setRuby(chosen);
                            }
                        }
                    });
                }
                
            } else {
                // No rubies found - just default to using the bundled JRuby
                return "jruby"; // NOI18N
            }
        }
            
        return "jruby"; // NOI18N
    }
    
    private String askForRuby(final Set<String> rubies) {
        // Ruby found in the path -- offer to use it
        String jrubyLabel = NbBundle.getMessage(RubyInstallation.class, "JRubyBundled");
        String nativeRubyLabel = NbBundle.getMessage(RubyInstallation.class, "NativeRuby") + " "; 

        List<String> displayList = new ArrayList<String>();
        String jruby = getJRuby();
        if (jruby != null) {
            displayList.add(jrubyLabel);
        }
        for (String r : rubies) {
            displayList.add(nativeRubyLabel + r);
        }

        ChooseRubyPanel panel = new ChooseRubyPanel(displayList);

        DialogDescriptor descriptor = new DialogDescriptor(panel,
                NbBundle.getMessage(RubyInstallation.class, "ChooseRuby"), true,
                DialogDescriptor.OK_CANCEL_OPTION, DialogDescriptor.OK_OPTION,
                DialogDescriptor.DEFAULT_ALIGN, new HelpCtx(RubyInstallation.class), null);
        descriptor.setMessageType(NotifyDescriptor.Message.INFORMATION_MESSAGE);

        Dialog dlg = null;
        descriptor.setModal(true);

        try {
            dlg = DialogDisplayer.getDefault().createDialog(descriptor);
            dlg.setVisible(true);
        } finally {
            if (dlg != null) {
                dlg.dispose();
            }
        }

        if (descriptor.getValue() != DialogDescriptor.OK_OPTION) {
            return "jruby"; // NOI18N
        }
        String displayItem = panel.getChosenInterpreter();
        if (displayItem == null) {
            // Force user to choose
            displayRubyOptions();
        } else {
            if (displayItem.equals(jrubyLabel)) {
                return "jruby"; // NOI18N
            } else {
                assert displayItem.startsWith(nativeRubyLabel);
                String path = displayItem.substring(nativeRubyLabel.length());
                
                try {
                    path = new File(path).getCanonicalPath();
                } catch (IOException ioe) {
                    Exceptions.printStackTrace(ioe);
                }

                return path;
            }
        }

        return "jruby"; // NOI18N
    }
    
    /**
     * Returns whether the currently set interpreter is the JRuby
     * implementation.
     * 
     * @return <tt>true</tt> if JRuby is set; <tt>false</tt> otherwise.
     */
    public boolean isJRubySet() {
        String interpreter = getRuby();
        return interpreter != null ? RubyInstallation.isJRuby(interpreter) : false;
    }

    public static boolean isJRuby(final String pathToInterpreter) {
        File rubyF = new File(pathToInterpreter);
        return rubyF.getName().startsWith("jruby"); // NOI18N
    }

    /** Find the Ruby-specific library directory for the chosen Ruby.
     * This is usually something like /foo/bar/lib/1.8/ (where Ruby was /foo/bar/bin/ruby)
     * but it tries to work with other versions of Ruby as well (such as 1.9).
     */
    public String getRubyLibRubyDir() {
        if (rubylib == null) {
            File rubyHome = getRubyHome();

            if (rubyHome == null) {
                String jrubyLib = getJRubyLib();
                if (jrubyLib != null) {
                    return jrubyLib + File.separator + "ruby" + File.separator + // NOI18N
                        DEFAULT_RUBY_RELEASE;
                } else {
                    return null;
                }
            }

            File lib = new File(rubyHome, "lib" + File.separator + "ruby"); // NOI18N

            if (!lib.exists()) {
                return null;
            }

            File f = new File(lib, DEFAULT_RUBY_RELEASE); // NOI18N

            if (f.exists()) {
                rubylib = f.getAbsolutePath();
            } else {
                // Search for a numbered directory
                File[] children = lib.listFiles();

                for (File c : children) {
                    if (!c.isDirectory()) {
                        continue;
                    }

                    String name = c.getName();

                    if (name.matches("\\d+\\.\\d+")) { // NOI18N
                        rubylib = c.getAbsolutePath();

                        break;
                    }
                }
            }

            if (rubylib == null) {
                rubylib = getJRubyLib();
            }
        }

        return rubylib;
    }
    
    public String getRubyLibGemDir() {
        return getRubyLibGemDir(true);
    }
    
    /**
     * Return the gem directory for the current ruby installation.
     * Returns the gem root, not the gem subdirectory.
     * Not cached.
     */
    public String getRubyLibGemDir(boolean canonical) {
        String gemdir = null;
        
        String gemHome = TEST_GEM_HOME; // test hook
        if (gemHome == null) {
            gemHome = System.getenv().get("GEM_HOME"); // NOI18N
        }
        if (gemHome != null) {
            File lib = new File(gemHome); // NOI18N
            if (!lib.isDirectory()) {
                LOGGER.finest("Cannot find Gems repository. \"" + lib + "\" does not exist or is not a directory."); // NOI18N
                // Fall through and try the Ruby interpreter's area
            } else {
                gemHomeFo = FileUtil.toFileObject(lib);
                return lib.getAbsolutePath();
            }
        }
        
        File rubyHome = getRubyHome(canonical);

        if (rubyHome == null) {
            File gemDir =
                new File(getJRubyLib() + File.separator + "ruby" + File.separator + "gems" + // NOI18N
                    File.separator + DEFAULT_RUBY_RELEASE);

            if (gemDir.isDirectory()) {
                gemHomeFo = FileUtil.toFileObject(gemDir);
                return gemDir.getAbsolutePath();
            } else {
                LOGGER.finest("Cannot find Gems repository. \"" + gemDir + "\" does not exist or is not a directory."); // NOI18N
                return null;
            }
        }
        
        File lib = new File(rubyHome, "lib" + File.separator + "ruby" + File.separator + "gems"); // NOI18N

        if (!lib.isDirectory()) {
            // Special case for Debian: /usr/share/doc/rubygems/README.Debian documents
            // a special location for gems
            if ("/usr".equals(rubyHome.getPath())) { // NOI18N
                File varGem = new File("/var/lib/gems/1.8"); // NOI18N
                if (varGem.exists()) {
                    gemHomeFo = FileUtil.toFileObject(varGem);
                    return varGem.getPath();
                }
            }

            LOGGER.finest("Cannot find Gems repository. No GEM_HOME set."); // NOI18N
            return null;
        }
        
        File f = new File(lib, DEFAULT_RUBY_RELEASE); // NOI18N
        
        if (f.exists()) {
            gemdir = f.getAbsolutePath();
        } else {
            // Search for a numbered directory
            File[] children = lib.listFiles();
            if (children != null) {
                for (File c : children) {
                    if (!c.isDirectory()) {
                        continue;
                    }

                    if (c.getName().matches("\\d+\\.\\d+")) { // NOI18N
                        gemHomeFo = FileUtil.toFileObject(c);
                        gemdir = c.getAbsolutePath();
                        break;
                    }
                }
            }

            if ((gemdir == null) && (children != null) && (children.length > 0)) {
                gemHomeFo = FileUtil.toFileObject(children[0]);
                gemdir = children[0].getAbsolutePath();
            }
        }

        return gemdir;
    }

    /** Return the site_ruby directory for the current ruby installation. Not cached. */
    public String getRubyLibSiteDir() {
        String sitedir = null;
        File rubyHome = getRubyHome();

        if (rubyHome == null) {
            File siteDir =
                new File(getJRubyLib() + File.separator + "ruby" + File.separator + "site_ruby" + // NOI18N
                    File.separator + DEFAULT_RUBY_RELEASE);

            if (siteDir.exists()) {
                return siteDir.getAbsolutePath();
            } else {
                return null;
            }
        }

        File lib =
            new File(rubyHome, "lib" + File.separator + "ruby" + File.separator + "site_ruby"); // NOI18N

        if (!lib.exists()) {
            return null;
        }

        File f = new File(lib, DEFAULT_RUBY_RELEASE); // NOI18N

        if (f.exists()) {
            sitedir = f.getAbsolutePath();
        } else {
            // Search for a numbered directory
            File[] children = lib.listFiles();

            for (File c : children) {
                if (!c.isDirectory()) {
                    continue;
                }

                String name = c.getName();

                if (name.matches("\\d+\\.\\d+")) { // NOI18N
                    sitedir = c.getAbsolutePath();

                    break;
                }
            }

            if ((sitedir == null) && (children.length > 0)) {
                sitedir = children[0].getAbsolutePath();
            }
        }

        return sitedir;
    }

    public File getRubyHome() {
        return getRubyHome(true);
    }
    
    public File getRubyHome(boolean canonical) {
        try {
            String rp = getRuby(canonical);
            if (rp == null) {
               return null;
            }
            File r = new File(rp);

            // Handle bogus paths like "/" which cannot possibly point to a valid ruby installation
            File p = r.getParentFile();
            if (p == null) {
                return null;
            }

            p = p.getParentFile();
            if (p == null) {
                return null;
            }
            
            return p.getCanonicalFile();
        } catch (IOException ioe) {
            Exceptions.printStackTrace(ioe);

            return null;
        }
    }

    public String getRubyHomeUrl() {
        if (rubyHomeUrl == null) {
            try {
                File r = getRubyHome();
                if (r != null) {
                    rubyHomeUrl = r.toURI().toURL().toExternalForm();
                }
            } catch (MalformedURLException mue) {
                Exceptions.printStackTrace(mue);
            }
        }

        return rubyHomeUrl;
    }

    public String getGemHomeUrl() {
        if (gemHomeUrl == null) {
            String libGemDir = getRubyLibGemDir();
            if (libGemDir != null) {
                try {
                    File r = new File(libGemDir);
                    if (r != null) {
                        gemHomeUrl = r.toURI().toURL().toExternalForm();
                    }
                } catch (MalformedURLException mue) {
                    Exceptions.printStackTrace(mue);
                }
            }
        }

        return gemHomeUrl;
    }


    public boolean isValidRuby(boolean warn) {
        String rp = getRuby();
        boolean valid = false;
        if (rp != null) {
            File file = new File(rp);
            valid = file.exists() && getRubyHome() != null;
        }

        if (warn && !valid) {
            String msg =
                NbBundle.getMessage(RubyInstallation.class, "NotInstalledRuby");
            javax.swing.JButton closeButton =
                new javax.swing.JButton(NbBundle.getMessage(RubyInstallation.class, "CTL_Close"));
            closeButton.getAccessibleContext()
                       .setAccessibleDescription(NbBundle.getMessage(RubyInstallation.class,
                    "AD_Close")); // NOI18N

            final JButton optionsButton =
                new JButton(NbBundle.getMessage(RubyInstallation.class, "EditOptions"));
            Object[] options = new Object[] { optionsButton, closeButton };
            DialogDescriptor descriptor =
                new DialogDescriptor(msg,
                    NbBundle.getMessage(RubyInstallation.class, "MissingRuby"), true, options,
                    optionsButton, // XXX TODO i18n
                    DialogDescriptor.DEFAULT_ALIGN, new HelpCtx(RubyInstallation.class), null);
            descriptor.setMessageType(NotifyDescriptor.Message.ERROR_MESSAGE);

            Dialog dlg = null;
            descriptor.setModal(true);

            try {
                dlg = DialogDisplayer.getDefault().createDialog(descriptor);
                dlg.setVisible(true);
            } finally {
                if (dlg != null) {
                    dlg.dispose();
                }
            }

            if (descriptor.getValue() == optionsButton) {
                displayRubyOptions();
            }
        }

        return valid;
    }
    
    private void displayRubyOptions() {
        OptionsDisplayer.getDefault().open("RubyOptions"); // NOI18N
    }
    
    /**
     * Try to find a path to the <tt>toFind</tt> executable in the "Ruby
     * specific" manner.
     *
     * @param toFind executable to be find, e.g. rails, rake, ...
     * @return path to the found executable; might be <tt>null</tt> if not
     *         found.
     */
    public String findGemExecutable(final String toFind) {
        String exec = null;
        boolean canonical = true; // default
        do {
            String binDir = getRubyBin(canonical);
            if (binDir != null) {
                LOGGER.finest("Looking for '" + toFind + "' gem executable; used intepreter: '" + getRuby() + "'"); // NOI18N
                exec = findExecutable(binDir, toFind);
            } else {
                LOGGER.warning("Could not find Ruby interpreter executable when searching for '" + toFind + "'"); // NOI18N
            }
            if (exec == null) {
                String libGemBinDir = getRubyLibGemDir(canonical) + File.separator + "bin"; // NOI18N
                exec = findExecutable(libGemBinDir, toFind);
            }
            canonical ^= true;
        } while (!canonical && exec == null);
        // try to find a gem on system path - see issue 116219
        if (exec == null) {
            exec = findOnPath(toFind);
        }
        // try *.bat commands on Windows
        if (exec == null && !toFind.endsWith(".bat") && Utilities.isWindows()) { // NOI18N
            exec = findGemExecutable(toFind + ".bat"); // NOI18N
        }
        return exec;
    }
    
    private static String findExecutable(final String dir, final String toFind) {
        String exec = dir + File.separator + toFind;
        if (!new File(exec).isFile()) {
            LOGGER.finest("'" + exec + "' is not a file."); // NOI18N
            exec = null;
        }
        return exec;
    }
    
    /**
     * Return path to the <em>gem</em> tool if it does exist.
     *
     * @return path to the <em>gem</em> tool; might be <tt>null</tt> if not
     *         found.
     */
    public String getGem() {
        if (gem == null) {
            String bin = getRubyBin();
            if (bin != null) {
                gem = bin + File.separator + "gem"; // NOI18N
                if (!new File(gem).isFile()) {
                    gem = null;
                }
            }
        }
        if (gem == null) {
            gem = RubyInstallation.findOnPath("gem"); // NOI18N
        }
        return gem;
    }
    
    private static String findOnPath(final String toFind) {
        String rubyLib = System.getenv("PATH"); // NOI18N
        if (rubyLib != null) {
            String[] paths = rubyLib.split("[:;]"); // NOI18N
            for (String path : paths) {
                String result = path + File.separator + toFind;
                if (new File(result).isFile()) {
                    return result;
                }
            }
        }
        return null;
    }
    
    /** Return non-null iff the given file is a "system" file - e.g. it's part
     * of the Ruby 1.8 libraries, or the Java support libraries, or the user's rubygems,
     * or something under $GEM_HOME...
     * @return The actual root the in the system the file is under.
     */
    public FileObject getSystemRoot(FileObject file) {
        // See if the file is under the Ruby libraries
        FileObject rubyLibFo = getRubyLibFo();
        FileObject rubyStubs = getRubyStubs();
        FileObject gemHome = getRubyLibGemDirFo();

        //        FileObject jar = FileUtil.getArchiveFile(file);
        //        if (jar != null) {
        //            file = jar;
        //        }

        while (file != null) {
            if (file == rubyLibFo || file == rubyStubs || file == gemHome) {
                return file;
            }
            
            file = file.getParent();
        }

        return null;
    }
    
    public String getRake() {
        if (rake == null) {
            rake = findGemExecutable("rake"); // NOI18N

            if (rake != null && !(new File(rake).exists()) && getVersion("rake") != null) { // NOI18N
                // On Windows, rake does funny things - you may only get a rake.bat
                InstalledFileLocator locator = InstalledFileLocator.getDefault();
                File f =
                    locator.locate("modules/org-netbeans-modules-ruby-project.jar", // NOI18N
                        null, false); // NOI18N

                if (f == null) {
                    throw new RuntimeException("Can't find cluster"); // NOI18N
                }

                f = new File(f.getParentFile().getParentFile().getAbsolutePath() + File.separator +
                        "rake"); // NOI18N

                try {
                    rake = f.getCanonicalPath();
                } catch (IOException ioe) {
                    Exceptions.printStackTrace(ioe);
                }
            }
        }

        return rake;
    }

    public boolean isValidRake(boolean warn) {
        String rakePath = getRake();
        boolean valid = (rakePath != null) && new File(rakePath).exists();

        if (warn && !valid) {
            String msg = NbBundle.getMessage(RubyInstallation.class, "NotInstalledCmd", "rake"); // NOI18N
            NotifyDescriptor nd =
                new NotifyDescriptor.Message(msg, NotifyDescriptor.Message.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(nd);
        }

        return valid;
    }

    public String getAutoTest() {
        return findGemExecutable("autotest"); // NOI18N
    }

    public boolean isValidAutoTest(boolean warn) {
        String autoTest = getAutoTest();
        boolean valid = (autoTest != null) && new File(autoTest).exists();

        if (warn && !valid) {
            String msg = NbBundle.getMessage(RubyInstallation.class, "NotInstalledCmd", "autotest"); // NOI18N
            NotifyDescriptor nd =
                new NotifyDescriptor.Message(msg, NotifyDescriptor.Message.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(nd);
        }

        return valid;
    }

    public String getJRubyHome() {
        if (jrubyHome == null) {
            File jrubyDir =
                InstalledFileLocator.getDefault()
                                    .locate(JRUBY_RELEASEDIR, "org.netbeans.modules.ruby.project", // NOI18N
                    false); // NOI18N

            if ((jrubyDir == null) || !jrubyDir.isDirectory()) {
                // The JRuby distribution may not be installed
                return null;
            }

            jrubyHome = jrubyDir.getPath();
        }

        return jrubyHome;
    }

    private String getJRubyBin() {
        String jh = getJRubyHome();
        if (jh != null) {
            return jh + File.separator + "bin"; // NOI18N
        } else {
            return null;
        }
    }

    private String getJRubyLib() {
        String jh = getJRubyHome();
        if (jh != null) {
            return jh + File.separator + "lib"; // NOI18N
        } else {
            return null;
        }
    }

    /** Return the lib directory for the currently chosen Ruby interpreter */
    public String getRubyLib() {
        File rubyHome = getRubyHome();
        if (rubyHome == null) {
            return null;
        }
        File rubyLib = new File(rubyHome, "lib"); // NOI18N

        if (rubyLib.exists() && new File(rubyLib, "ruby").exists()) { // NOI18N
            try {
                return rubyLib.getCanonicalPath();
            } catch (IOException ioe) {
                Exceptions.printStackTrace(ioe);
            }
        }

        return getJRubyLib();
    }

    public FileObject getRubyLibFo() {
        if (rubylibFo == null) {
            String lib = getRubyLib();

            if (lib != null) {
                rubylibFo = FileUtil.toFileObject(new File(lib));
            }
        }

        return rubylibFo;
    }
    
    public FileObject getRubyStubs() {
        if (rubyStubsFo == null) {
            // Core classes: Stubs generated for the "builtin" Ruby libraries.
            File clusterFile =
                InstalledFileLocator.getDefault()
                                    .locate("modules/org-netbeans-modules-ruby-project.jar", null, // NOI18N
                    false); // NOI18N

            if (clusterFile != null) {
                File rubyStubs =
                    new File(clusterFile.getParentFile().getParentFile().getAbsoluteFile(),
                        // JRUBY_RELEASEDIR + File.separator + 
                    "rubystubs" + File.separator + RUBYSTUBS_VERSION); // NOI18N
                assert rubyStubs.exists() && rubyStubs.isDirectory();
                rubyStubsFo = FileUtil.toFileObject(rubyStubs);
            } else {
                // During test?
                String r = getRuby();
                if (r != null) {
                    FileObject fo = FileUtil.toFileObject(new File(r));
                    if (fo != null) {
                        rubyStubsFo = fo.getParent().getParent().getParent().getFileObject("rubystubs/" + RUBYSTUBS_VERSION); // NOI18N
                    }
                }
            }
        }

        return rubyStubsFo;
    }

    public String getRDoc() {
        if (rdoc == null) {
            rdoc = findGemExecutable("rdoc"); // NOI18N
            if (rdoc == null && !isJRubySet()) {
                String name = new File(getRuby(true)).getName();
                if (name.startsWith("ruby")) { // NOI18N
                    String suffix = name.substring(4);
                    // Try to find with suffix (#120441)
                    rdoc = findGemExecutable("rdoc" + suffix); 
                }
            }
        }
        return rdoc;
    }

    public String getRails() {
        if (rails == null) {
            rails = findGemExecutable("rails"); // NOI18N
        }
        return rails;
    }
    
    public boolean isValidRails(boolean warn) {
        String railsPath = getRails();
        boolean valid = (railsPath != null) && new File(railsPath).exists();

        if (warn && !valid) {
            String msg = NbBundle.getMessage(RubyInstallation.class, "NotInstalledCmd", "rails"); // NOI18N
            NotifyDescriptor nd =
                new NotifyDescriptor.Message(msg, NotifyDescriptor.Message.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(nd);
        }

        return valid;
    }

    public void setRuby(String ruby) {
        File rubyF = new File(ruby);
        ruby = rubyF.getAbsolutePath();
        if (!ruby.equals(getRuby())) {
            Util.getPreferences().put(KEY_RUBY, ruby);
        }
        if (!FileUtil.normalizeFile(rubyF).equals(FileUtil.normalizeFile(new File(getRuby())))) {
            // reset only in case it is not a link to the same file
            this.ruby = ruby;
            // Recompute lazily:
            this.gem = null;
            this.rubylib = null;
            this.rubylibFo = null;
            this.rubyStubsFo = null;
            this.rake = null;
            this.rdoc = null;
            this.rails = null;
            this.rubyHomeUrl = null;
            this.gemHomeUrl = null;
            this.jrubyJavaSupport = null;
            this.gemHomeFo = null;
            //this.irb = null;
            if (isValidRuby(false)) {
                recomputeRoots();
            }
        }
    }

    /**
     * AutoUpdate may not set execute permissions on the bundled JRuby files,
     * so try to fix that here
     * @todo Do this lazily before trying to actually execute any of these bits?
     */
    public void ensureExecutable() {
        // No excute permissions on Windows. On Unix and Mac, try.
        if (Utilities.isWindows()) {
            return;
        }

        String binDirPath = getJRubyBin();
        if (binDirPath == null) {
            return;
        }

        File binDir = new File(binDirPath);
        if (!binDir.exists()) {
            // No JRuby bundled installation?
            return;
        }

        // Ensure that the binaries are installed as expected
        // The following logic is from CLIHandler in core/bootstrap:
        File chmod = new File("/bin/chmod"); // NOI18N

        if (!chmod.isFile()) {
            // Linux uses /bin, Solaris /usr/bin, others hopefully one of those
            chmod = new File("/usr/bin/chmod"); // NOI18N
        }

        if (chmod.isFile()) {
            try {
                List<String> argv = new ArrayList<String>();
                argv.add(chmod.getAbsolutePath());
                argv.add("u+rx"); // NOI18N

                String[] files = binDir.list();

                for (String file : files) {
                    argv.add(file);
                }

                ProcessBuilder pb = new ProcessBuilder(argv);
                pb.directory(binDir);
                Util.adjustProxy(pb);

                Process process = pb.start();

                int chmoded = process.waitFor();

                if (chmoded != 0) {
                    throw new IOException("could not run " + argv + " : Exit value=" + chmoded); // NOI18N
                }
            } catch (Throwable e) {
                // 108252 - no loud complaints
                LOGGER.log(Level.INFO, "Can't chmod+x JRuby bits", e);
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (pcs == null) {
            pcs = new PropertyChangeSupport(this);
        }

        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (pcs != null) {
            pcs.removePropertyChangeListener(listener);
        }
    }
    
    /** The gems installed have changed, or the installed ruby has changed etc. --
     * force a recomputation of the installed classpath roots */
    public void recomputeRoots() {
        this.gemFiles = null;
        this.gemVersions = null;
        this.gemUrls = null;
        this.nonGemUrls = null;

        // Let RepositoryUpdater and friends know where they can root preindexing
        // This should be done in a cleaner way.
        org.netbeans.modules.gsfret.source.usages.Index.setPreindexRootUrl(getRubyHomeUrl());

        // Ensure that source cache is wiped and classpaths recomputed for existing files
        Source.clearSourceCache();
        
        if (pcs != null) {
            pcs.firePropertyChange("roots", null, null); // NOI18N
        }

        //        // Force ClassIndex registration
        //        // Dummy class path provider just to trigger recomputation
        //        ClassPathProviderImpl cpProvider = new ClassPathProviderImpl(null, null, null, null);
        //        ClassPath[] cps = cpProvider.getProjectClassPaths(ClassPath.BOOT);
        //        GlobalPathRegistry.getDefault().register(ClassPath.BOOT, cps);
        //        GlobalPathRegistry.getDefault().unregister(ClassPath.BOOT, cps);

        // Possibly clean up index from old ruby root as well?
    }

    /** Return > 0 if version1 is greater than version 2, 0 if equal and -1 otherwise */
    public static int compareGemVersions(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }

        Matcher matcher1 = VERSION_PATTERN.matcher(version1);

        if (matcher1.matches()) {
            int major1 = Integer.parseInt(matcher1.group(1));
            int minor1 = Integer.parseInt(matcher1.group(2));
            int micro1 = Integer.parseInt(matcher1.group(3));

            Matcher matcher2 = VERSION_PATTERN.matcher(version2);

            if (matcher2.matches()) {
                int major2 = Integer.parseInt(matcher2.group(1));
                int minor2 = Integer.parseInt(matcher2.group(2));
                int micro2 = Integer.parseInt(matcher2.group(3));

                if (major1 != major2) {
                    return major1 - major2;
                }

                if (minor1 != minor2) {
                    return minor1 - minor2;
                }

                if (micro1 != micro2) {
                    return micro1 - micro2;
                }
            } else {
                // TODO uh oh
                //assert false : "no version match on " + version2;
            }
        } else {
            // TODO assert false : "no version match on " + version1;
        }

        // Just do silly alphabetical comparison
        return version1.compareTo(version2);
    }

    public Set<String> getInstalledGems() {
        initGemList();

        if (gemFiles == null) {
            return Collections.emptySet();
        }

        return gemFiles.keySet();
    }

    private FileObject getRubyLibGemDirFo() {
        initGemList(); // Ensure getRubyLibGemDir has been called, which initialized gemHomeFo
        return gemHomeFo;
    }

    private void initGemList() {
        if (gemFiles == null) {
            // Initialize lazily
            String gemDir = getRubyLibGemDir();
            if (gemDir == null) {
                return;
            }
            File specDir = new File(gemDir, SPECIFICATIONS);

            if (specDir.exists()) {
                LOGGER.finest("Initializing \"" + gemDir + "\" repository");
                // Add each of */lib/
                File[] gems = specDir.listFiles();
                gems = chooseGems(gems);
            } else {
                LOGGER.finest("Cannot find Gems repository. \"" + gemDir + "\" does not exist or is not a directory."); // NOI18N
            }
        }
    }

    public String getVersion(String gemName) {
        // TODO - use gemVersions map instead!
        initGemList();

        if (gemFiles == null) {
            return null;
        }

        Map<String, File> highestVersion = gemFiles.get(gemName);

        if ((highestVersion == null) || (highestVersion.size() == 0)) {
            return null;
        }

        return highestVersion.keySet().iterator().next();
    }

    /** Given a list of files that may represent gems, choose the most recent version
     * of each */
    private File[] chooseGems(File[] gems) {
        gemFiles = new HashMap<String, Map<String, File>>();

        for (File f : gems) {
            // See if it looks like a gem
            String n = f.getName();
            if (!n.endsWith(DOT_GEM_SPEC)) {
                continue;
            }

            n = n.substring(0, n.length()-DOT_GEM_SPEC.length());
            
            int dashIndex = n.lastIndexOf('-');
            
            if (dashIndex == -1) {
                // Probably not a gem
                continue;
            }
            
            String name;
            String version;

            if (dashIndex < n.length()-1 && Character.isDigit(n.charAt(dashIndex+1))) {
                // It's a gem without a platform suffix such as -mswin or -ruby
                name = n.substring(0, dashIndex);
                version = n.substring(dashIndex + 1);
            } else {
                String nosuffix = n.substring(0, dashIndex);
                int versionIndex = nosuffix.lastIndexOf('-');
                if (versionIndex != -1) {
                    name = n.substring(0, versionIndex);
                    version = n.substring(versionIndex+1, dashIndex);
                } else {
                    name = n.substring(0, dashIndex);
                    version = n.substring(dashIndex + 1);
                }
            }

            Map<String, File> nameMap = gemFiles.get(name);

            if (nameMap == null) {
                nameMap = new HashMap<String, File>();
                gemFiles.put(name, nameMap);
                nameMap.put(version, f);
            } else {
                // Decide whether this version is more recent than the one already there
                String oldVersion = nameMap.keySet().iterator().next();

                if (compareGemVersions(version, oldVersion) > 0) {
                    // New version is higher
                    nameMap.clear();
                    nameMap.put(version, f);
                }
            }
        }

        List<File> result = new ArrayList<File>();

        for (Map<String, File> map : gemFiles.values()) {
            for (File f : map.values()) {
                result.add(f);
            }
        }

        return result.toArray(new File[result.size()]);
    }
    
    /** Return other load path URLs (than the gem ones returned by {@link #getGemUrls} to add for the platform
     * such as the basic ruby 1.8 libraries, the site_ruby libraries, and the stub libraries for 
     * the core/builtin classes.
     * 
     * @return a set of URLs
     */
    public Set<URL> getNonGemLoadPath() {
        if (nonGemUrls == null) {
            initializeUrlMaps();
        }
        
        return nonGemUrls;
    }
    
    /** 
     * Return a map from gem name to the version string, which is of the form
     * {@code <major>.<minor>.<tiny>[-<platform>]}, such as 1.2.3 and 1.13.5-ruby
     */
    public Map<String,String> getGemVersions() {
        if (gemVersions == null) {
            initializeUrlMaps();
        }
        
        return gemVersions;
    }

    /** 
     * Return a map from gem name to the URL for the lib root of the current gems
     */
    public Map<String,URL> getGemUrls() {
        if (gemUrls == null) {
            initializeUrlMaps();
        }
        
        return gemUrls;
    }
    
    private void initializeUrlMaps() {
        File rubyHome = getRubyHome();

        if (rubyHome == null || !rubyHome.exists()) {
            gemVersions = Collections.emptyMap();
            gemUrls = Collections.emptyMap();
            nonGemUrls = Collections.emptySet();
            return;
        }
        try {
            gemUrls = new HashMap<String,URL>(60);
            gemVersions = new HashMap<String,String>(60);
            nonGemUrls = new HashSet<URL>(12);

            FileObject rubyStubs = getRubyStubs();

            if (rubyStubs != null) {
                try {
                    nonGemUrls.add(rubyStubs.getURL());
                } catch (FileStateInvalidException fsie) {
                    Exceptions.printStackTrace(fsie);
                }
            }

            // Install standard libraries
            // lib/ruby/1.8/ 
            if (!SKIP_INDEX_LIBS) {
                String rubyLibDir = getRubyLibRubyDir();
                if (rubyLibDir != null) {
                    File libs = new File(rubyLibDir);
                    assert libs.exists() && libs.isDirectory();
                    nonGemUrls.add(libs.toURI().toURL());
                }
            }

            // Install gems.
            if (!SKIP_INDEX_GEMS) {
                initGemList();
                if (PREINDEXING) {
                    String gemDir = getRubyLibGemDir();
                    File specDir = new File(gemDir, "gems"); // NOI18N

                    if (specDir.exists()) {
                        File[] gems = specDir.listFiles();
                        for (File f : gems) {
                            if (f.getName().indexOf('-') != -1) {
                                File lib = new File(f, "lib"); // NOI18N

                                if (lib.exists() && lib.isDirectory()) {
                                    URL url = lib.toURI().toURL();
                                    nonGemUrls.add(url);
                                }
                            }
                        }
                    }
                } else if (gemFiles != null) {
                    Set<String> gems = gemFiles.keySet();
                    for (String name : gems) {
                        Map<String,File> m = gemFiles.get(name);
                        assert m.keySet().size() == 1;
                        File f = m.values().iterator().next();
                        // Points to the specification file
                        assert f.getName().endsWith(DOT_GEM_SPEC);
                        String filename = f.getName().substring(0, f.getName().length()-DOT_GEM_SPEC.length());
                        File lib = new File(f.getParentFile().getParentFile(), "gems" + // NOI18N
                                File.separator + filename + File.separator + "lib"); // NOI18N

                        if (lib.exists() && lib.isDirectory()) {
                            URL url = lib.toURI().toURL();
                            gemUrls.put(name, url);
                            String version = m.keySet().iterator().next();
                            gemVersions.put(name, version);
                        }
                    }
                }
            }

            // Install site ruby - this is where rubygems lives for example
            if (!SKIP_INDEX_LIBS) {
                String rubyLibSiteDir = getRubyLibSiteDir();

                if (rubyLibSiteDir != null) {
                    File siteruby = new File(rubyLibSiteDir);

                    if (siteruby.exists() && siteruby.isDirectory()) {
                        nonGemUrls.add(siteruby.toURI().toURL());
                    }
                }
            }

            // During development only:
            gemUrls = Collections.unmodifiableMap(gemUrls);
            gemVersions = Collections.unmodifiableMap(gemVersions);
            nonGemUrls = Collections.unmodifiableSet(nonGemUrls);
            
            // Register boot roots. This is a bit of a hack.
            // I need to find a better way to distinguish source directories
            // from boot (library, gems, etc.) directories at the scanning and indexing end.
            ClassIndexManager mgr = ClassIndexManager.getDefault();
            List<URL> roots = new ArrayList<URL>(gemUrls.size() + nonGemUrls.size());
            roots.addAll(gemUrls.values());
            roots.addAll(nonGemUrls);
            mgr.setBootRoots(roots);
        } catch (MalformedURLException mue) {
            Exceptions.printStackTrace(mue);
        }
    }

    public static interface InterpreterLiveChangeListener extends EventListener {
        void interpreterChanged(String interpreter);
    }

    public void fireInterpreterLiveChange(final String interpreter) {
        for (InterpreterLiveChangeListener listener : interpreterLCLs) {
            listener.interpreterChanged(interpreter);
        }
    }
    
    public void addInterpreterLiveChangeListener(final InterpreterLiveChangeListener ilcl) {
        interpreterLCLs.add(ilcl);
    }
    
    public void removeInterpreterLiveChangeListener(final InterpreterLiveChangeListener ilcl) {
        interpreterLCLs.remove(ilcl);
    }
    
    public String getShortName() {
        String r = Util.getPreferences().get(KEY_RUBY, null);
        final String BUILTIN_JRUBY = NbBundle.getMessage(RubyInstallation.class, "BuiltinRuby");
        if (r == null) {
            return BUILTIN_JRUBY;
        } else {
            r = getRuby();
            if (r == null) {
                return "";
            }

            final String jh = getJRubyHome();
            if (jh != null & r.startsWith(jh)) {
                return BUILTIN_JRUBY;
            }
            if (isJRubySet()) {
                return NbBundle.getMessage(RubyInstallation.class, "JRuby"); // TODO version
            }
        }

        // How do I summary other interpreters?? For now, just use path
        final File rubyFile = new File(r);
        String basename = rubyFile.getName();
        return NbBundle.getMessage(RubyInstallation.class, "RubyInPath", basename, rubyFile.getParent());
    }

}
