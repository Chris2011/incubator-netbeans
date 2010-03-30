/***** BEGIN LICENSE BLOCK *****
 * Version: CPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Common Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/cpl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2002-2004 Anders Bengtsson <ndrsbngtssn@yahoo.se>
 * Copyright (C) 2002-2004 Jan Arne Petersen <jpetersen@uni-bonn.de>
 * Copyright (C) 2004 Thomas E Enebo <enebo@acm.org>
 * Copyright (C) 2004-2005 Charles O Nutter <headius@headius.com>
 * Copyright (C) 2004 Stefan Matthias Aust <sma@3plus4.de>
 * Copyright (C) 2006 Ola Bini <ola@ologix.com>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the CPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the CPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby.nb.runtime.load;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.zip.ZipException;
import org.jruby.nb.CompatVersion;
import org.jruby.nb.Ruby;
import org.jruby.nb.RubyArray;
import org.jruby.nb.RubyFile;
import org.jruby.nb.RubyHash;
import org.jruby.nb.RubyString;
import org.jruby.nb.ast.executable.Script;
import org.jruby.nb.exceptions.RaiseException;
import org.jruby.nb.runtime.Constants;
import org.jruby.nb.runtime.builtin.IRubyObject;
import org.jruby.nb.util.JRubyFile;

/**
 * <b>How require works in JRuby</b>
 * When requiring a name from Ruby, JRuby will first remove any file extension it knows about,
 * thereby making it possible to use this string to see if JRuby has already loaded
 * the name in question. If a .rb extension is specified, JRuby will only try
 * those extensions when searching. If a .so, .o, .dll, or .jar extension is specified, JRuby
 * will only try .so or .jar when searching. Otherwise, JRuby goes through the known suffixes
 * (.rb, .rb.ast.ser, .so, and .jar) and tries to find a library with this name. The process for finding a library follows this order
 * for all searchable extensions:
 * <ol>
 * <li>First, check if the name starts with 'jar:', then the path points to a jar-file resource which is returned.</li>
 * <li>Second, try searching for the file in the current dir</li>
 * <li>Then JRuby looks through the load path trying these variants:
 *   <ol>
 *     <li>See if the current load path entry starts with 'jar:', if so check if this jar-file contains the name</li>
 *     <li>Otherwise JRuby tries to construct a path by combining the entry and the current working directy, and then see if 
 *         a file with the correct name can be reached from this point.</li>
 *   </ol>
 * </li>
 * <li>If all these fail, try to load the name as a resource from classloader resources, using the bare name as
 *     well as the load path entries</li>
 * <li>When we get to this state, the normal JRuby loading has failed. At this stage JRuby tries to load 
 *     Java native extensions, by following this process:
 *   <ol>
 *     <li>First it checks that we haven't already found a library. If we found a library of type JarredScript, the method continues.</li>
 *     <li>The first step is translating the name given into a valid Java Extension class name. First it splits the string into 
 *     each path segment, and then makes all but the last downcased. After this it takes the last entry, removes all underscores
 *     and capitalizes each part separated by underscores. It then joins everything together and tacks on a 'Service' at the end.
 *     Lastly, it removes all leading dots, to make it a valid Java FWCN.</li>
 *     <li>If the previous library was of type JarredScript, we try to add the jar-file to the classpath</li>
 *     <li>Now JRuby tries to instantiate the class with the name constructed. If this works, we return a ClassExtensionLibrary. Otherwise,
 *     the old library is put back in place, if there was one.
 *   </ol>
 * </li>
 * <li>When all separate methods have been tried and there was no result, a LoadError will be raised.</li>
 * <li>Otherwise, the name will be added to the loaded features, and the library loaded</li>
 * </ol>
 *
 * <b>How to make a class that can get required by JRuby</b>
 * <p>First, decide on what name should be used to require the extension.
 * In this purely hypothetical example, this name will be 'active_record/connection_adapters/jdbc_adapter'.
 * Then create the class name for this require-name, by looking at the guidelines above. Our class should
 * be named active_record.connection_adapters.JdbcAdapterService, and implement one of the library-interfaces.
 * The easiest one is BasicLibraryService, where you define the basicLoad-method, which will get called
 * when your library should be loaded.</p>
 * <p>The next step is to either put your compiled class on JRuby's classpath, or package the class/es inside a
 * jar-file. To package into a jar-file, we first create the file, then rename it to jdbc_adapter.jar. Then 
 * we put this jar-file in the directory active_record/connection_adapters somewhere in JRuby's load path. For
 * example, copying jdbc_adapter.jar into JRUBY_HOME/lib/ruby/site_ruby/1.8/active_record/connection_adapters
 * will make everything work. If you've packaged your extension inside a RubyGem, write a setub.rb-script that 
 * copies the jar-file to this place.</p>
 * <p>If you don't want to have the name of your extension-class to be prescribed, you can also put a file called
 * jruby-ext.properties in your jar-files META-INF directory, where you can use the key <full-extension-name>.impl
 * to make the extension library load the correct class. An example for the above would have a jruby-ext.properties
 * that contained a ruby like: "active_record/connection_adapters/jdbc_adapter=org.jruby.nb.ar.JdbcAdapter". (NOTE: THIS
 * FEATURE IS NOT IMPLEMENTED YET.)</p>
 *
 * @author jpetersen
 */
public class LoadService {
    protected static final String[] sourceSuffixes = { ".class", ".rb" };
    protected static final String[] extensionSuffixes = { ".so", ".jar" };
    protected static final String[] allSuffixes = { ".class", ".rb", ".so", ".jar" };
    protected static final Pattern sourcePattern = Pattern.compile("\\.(?:rb)$");
    protected static final Pattern extensionPattern = Pattern.compile("\\.(?:so|o|dll|jar)$");

    protected RubyArray loadPath;
    protected RubyArray loadedFeatures;
    protected List loadedFeaturesInternal;
//    protected final Set firstLineLoadedFeatures = Collections.synchronizedSet(new HashSet());
    protected final Map<String, Library> builtinLibraries = new HashMap<String, Library>();

    protected final Map<String, JarFile> jarFiles = new HashMap<String, JarFile>();

    protected final Map<String, IAutoloadMethod> autoloadMap = new HashMap<String, IAutoloadMethod>();

    protected final Ruby runtime;
    
    public LoadService(Ruby runtime) {
        this.runtime = runtime;
    }

    public void init(List additionalDirectories) {
        loadPath = RubyArray.newArray(runtime);
        loadedFeatures = RubyArray.newArray(runtime);
        loadedFeaturesInternal = Collections.synchronizedList(loadedFeatures);
        
        // add all startup load paths to the list first
        for (Iterator iter = additionalDirectories.iterator(); iter.hasNext();) {
            addPath((String) iter.next());
        }

        // add $RUBYLIB paths
       RubyHash env = (RubyHash) runtime.getObject().fastGetConstant("ENV");
       RubyString env_rubylib = runtime.newString("RUBYLIB");
       if (env.has_key_p(env_rubylib).isTrue()) {
           String rubylib = env.op_aref(runtime.getCurrentContext(), env_rubylib).toString();
           String[] paths = rubylib.split(File.pathSeparator);
           for (int i = 0; i < paths.length; i++) {
               addPath(paths[i]);
           }
       }

        // wrap in try/catch for security exceptions in an applet
        if (!Ruby.isSecurityRestricted()) {
          String jrubyHome = runtime.getJRubyHome();
          if (jrubyHome != null) {
              char sep = '/';
              String rubyDir = jrubyHome + sep + "lib" + sep + "ruby" + sep;

              // If we're running in 1.9 compat mode, add Ruby 1.9 libs to path before 1.8 libs
              if (runtime.getInstanceConfig().getCompatVersion() == CompatVersion.RUBY1_9) {
                  addPath(rubyDir + "site_ruby" + sep + Constants.RUBY1_9_MAJOR_VERSION);
                  addPath(rubyDir + "site_ruby");
                  addPath(rubyDir + Constants.RUBY1_9_MAJOR_VERSION);
                  addPath(rubyDir + Constants.RUBY1_9_MAJOR_VERSION + sep + "java");
              }
              
              // Add 1.8 libs
              addPath(rubyDir + "site_ruby" + sep + Constants.RUBY_MAJOR_VERSION);
              addPath(rubyDir + "site_ruby");
              addPath(rubyDir + "site_ruby" + sep + "shared");
              addPath(rubyDir + Constants.RUBY_MAJOR_VERSION);
              addPath(rubyDir + Constants.RUBY_MAJOR_VERSION + sep + "java");

              // Added to make sure we find default distribution files within jar file.
              // TODO: Either make jrubyHome become the jar file or allow "classpath-only" paths
              addPath("lib" + sep + "ruby" + sep + Constants.RUBY_MAJOR_VERSION);
          }
        }
        
        // "." dir is used for relative path loads from a given file, as in require '../foo/bar'
        if (runtime.getSafeLevel() == 0) {
            addPath(".");
        }
    }

    private void addPath(String path) {
        // Empty paths do not need to be added
        if (path == null || path.length() == 0) return;
        
        synchronized(loadPath) {
            loadPath.append(runtime.newString(path.replace('\\', '/')));
        }
    }

    public void load(String file, boolean wrap) {
        if(!runtime.getProfile().allowLoad(file)) {
            throw runtime.newLoadError("No such file to load -- " + file);
        }

        Library library = findLibrary(file, false);

        if (library == null) {
            library = findLibraryWithClassloaders(file);
            if (library == null) {
                throw runtime.newLoadError("No such file to load -- " + file);
            }
        }
        try {
            library.load(runtime, wrap);
        } catch (IOException e) {
            if (runtime.getDebug().isTrue()) e.printStackTrace();
            throw runtime.newLoadError("IO error -- " + file);
        }
    }

    public boolean smartLoad(String file) {
        if (file.equals("")) {
            throw runtime.newLoadError("No such file to load -- " + file);
        }
        
        Library library = null;
        String loadName = file;
        String[] extensionsToSearch = null;
        
        // if an extension is specified, try more targetted searches
        if (file.lastIndexOf('.') > file.lastIndexOf('/')) {
            Matcher matcher = null;
            if ((matcher = sourcePattern.matcher(file)).find()) {
                // source extensions
                extensionsToSearch = sourceSuffixes;
                
                // trim extension to try other options
                file = file.substring(0,matcher.start());
            } else if ((matcher = extensionPattern.matcher(file)).find()) {
                // extension extensions
                extensionsToSearch = extensionSuffixes;
                
                // trim extension to try other options
                file = file.substring(0,matcher.start());
            } else {
                // unknown extension, fall back to search with extensions
                extensionsToSearch = allSuffixes;
            }
        } else {
            // try all extensions
            extensionsToSearch = allSuffixes;
        }
        
        // First try suffixes with normal loading
        for (int i = 0; i < extensionsToSearch.length; i++) {
            if (Ruby.isSecurityRestricted()) {
                // search in CWD only in if no security restrictions
                library = findLibrary(file + extensionsToSearch[i], false);
            } else {
                library = findLibrary(file + extensionsToSearch[i], true);
            }

            if (library != null) {
                loadName = file + extensionsToSearch[i];
                break;
            }
        }

        // Then try suffixes with classloader loading
        if (library == null) {
            for (int i = 0; i < extensionsToSearch.length; i++) {
                library = findLibraryWithClassloaders(file + extensionsToSearch[i]);
                if (library != null) {
                    loadName = file + extensionsToSearch[i];
                    break;
                }
            }
        }

        library = tryLoadExtension(library,file);

        // no library or extension found, try to load directly as a class
        Script script = null;
        if (library == null) {
            String className = file;
            // Remove any relative prefix, e.g. "./foo/bar" becomes "foo/bar".
            className = className.replaceFirst("^\\.\\/", "");
            if (className.lastIndexOf(".") != -1) className = className.substring(0, className.lastIndexOf("."));
            className = className.replace("-", "_minus_").replace('.', '_');
            int lastSlashIndex = className.lastIndexOf('/');
            if (lastSlashIndex > -1 &&
                    lastSlashIndex < className.length() - 1 &&
                    !Character.isJavaIdentifierStart(className.charAt(lastSlashIndex + 1))) {
                if (lastSlashIndex == -1) {
                    className = "_" + className;
                } else {
                    className = className.substring(0, lastSlashIndex + 1) + "_" + className.substring(lastSlashIndex + 1);
                }
            }
            className = className.replace('/', '.');
            try {
                Class scriptClass = Class.forName(className);
                script = (Script)scriptClass.newInstance();
            } catch (Exception cnfe) {
                throw runtime.newLoadError("no such file to load -- " + file);
            }
        }
        
        RubyString loadNameRubyString = runtime.newString(loadName);
        if (loadedFeaturesInternal.contains(loadNameRubyString)) {
            return false;
        }
        
        // attempt to load the found library
        try {
            loadedFeaturesInternal.add(loadNameRubyString);
//            firstLineLoadedFeatures.add(file);

            if (script != null) {
                runtime.loadScript(script);
                return true;
            }
            
            library.load(runtime, false);
            return true;
        } catch (Throwable e) {
            if(library instanceof JarredScript && file.endsWith(".jar")) {
                return true;
            }

            loadedFeaturesInternal.remove(loadNameRubyString);
            //firstLineLoadedFeatures.remove(file);
//            synchronized(loadedFeatures) {
//                loadedFeatures.remove(runtime.newString(loadName));
//            }
            if (e instanceof RaiseException) throw (RaiseException) e;

            if(runtime.getDebug().isTrue()) e.printStackTrace();
            
            RaiseException re = runtime.newLoadError("IO error -- " + file);
            re.initCause(e);
            throw re;
        }
    }

    public boolean require(String file) {
        if(!runtime.getProfile().allowRequire(file)) {
            throw runtime.newLoadError("No such file to load -- " + file);
        }
        return smartLoad(file);
    }

    public IRubyObject getLoadPath() {
        return loadPath;
    }

    public IRubyObject getLoadedFeatures() {
        return loadedFeatures;
    }

    public IAutoloadMethod autoloadFor(String name) {
        return autoloadMap.get(name);
    }
    
    public void removeAutoLoadFor(String name) {
        autoloadMap.remove(name);
    }

    public IRubyObject autoload(String name) {
        IAutoloadMethod loadMethod = autoloadMap.remove(name);
        if (loadMethod != null) {
            return loadMethod.load(runtime, name);
        }
        return null;
    }

    public void addAutoload(String name, IAutoloadMethod loadMethod) {
        autoloadMap.put(name, loadMethod);
    }

    public void addBuiltinLibrary(String name, Library library) {
        builtinLibraries.put(name, library);
    }

    public void removeBuiltinLibrary(String name) {
        builtinLibraries.remove(name);
    }

    public void removeInternalLoadedFeature(String name) {
        loadedFeaturesInternal.remove(name);
    }

    private Library findLibrary(String file, boolean checkCWD) {
        if (builtinLibraries.containsKey(file)) return builtinLibraries.get(file);
        return createLibrary(file, findFile(file, checkCWD));
    }

    private Library findLibraryWithClassloaders(String file) {
        return createLibrary(file, findFileInClasspath(file));
    }

    private Library createLibrary(String file, LoadServiceResource resource) {
        if (resource == null) {
            return null;
        }
        if (file.endsWith(".jar")) {
            return new JarredScript(resource);
        } else if (file.endsWith(".class")) {
            return new JavaCompiledScript(resource);
        } else {
            return new ExternalScript(resource, file);
        }      
    }

    /**
     * this method uses the appropriate lookup strategy to find a file.
     * It is used by Kernel#require.
     *
     * @mri rb_find_file
     * @param name the file to find, this is a path name
     * @return the correct file
     */
    private LoadServiceResource findFile(String name, boolean checkCWD) {
        // if a jar URL, return load service resource directly without further searching
        if (name.startsWith("jar:")) {
            try {
                return new LoadServiceResource(new URL(name), name);
            } catch (MalformedURLException e) {
                throw runtime.newIOErrorFromException(e);
            }
        } else if(name.startsWith("file:") && name.indexOf("!/") != -1) {
            try {
                JarFile file = new JarFile(name.substring(5, name.indexOf("!/")));
                String filename = name.substring(name.indexOf("!/") + 2);
                if(file.getJarEntry(filename) != null) {
                    return new LoadServiceResource(new URL("jar:" + name), name);
                }
            } catch(Exception e) {}
        }

        for (Iterator pathIter = loadPath.getList().iterator(); pathIter.hasNext();) {
            // TODO this is really ineffient, ant potentially a problem everytime anyone require's something.
            // we should try to make LoadPath a special array object.
            String entry = ((IRubyObject)pathIter.next()).toString();
            if (entry.startsWith("jar:") || entry.endsWith(".jar") || (entry.startsWith("file:") && entry.indexOf("!/") != -1)) {
                JarFile current = jarFiles.get(entry);
                String after = (entry.startsWith("file:") && entry.indexOf("!/") != -1) ? entry.substring(entry.indexOf("!/") + 2) + "/" : "";
                String before = (entry.startsWith("file:") && entry.indexOf("!/") != -1) ? entry.substring(0, entry.indexOf("!/")) : entry;

                if(null == current) {
                    try {
                        if(entry.startsWith("jar:")) {
                            current = new JarFile(entry.substring(4));
                        } else if (entry.endsWith(".jar")) {
                            current = new JarFile(entry);
                        } else {
                            current = new JarFile(entry.substring(5,entry.indexOf("!/")));
                        }
                        jarFiles.put(entry,current);
                    } catch (ZipException ignored) {
                        if (runtime.getInstanceConfig().isVerbose()) {
                            runtime.getErr().println("ZipException trying to access " + entry + ", stack trace follows:");
                            ignored.printStackTrace(runtime.getErr());
                        }
                    } catch (FileNotFoundException ignored) {
                    } catch (IOException e) {
                        throw runtime.newIOErrorFromException(e);
                    }
                }
                String canonicalEntry = after+name;
                if(after.length()>0) {
                    try {
                        canonicalEntry = new File(after+name).getCanonicalPath().substring(new File(".")
                                                             .getCanonicalPath().length()+1).replaceAll("\\\\","/");
                    } catch(Exception e) {}
                }
                if (current != null && current.getJarEntry(canonicalEntry) != null) {
                    try {
                        if (entry.endsWith(".jar")) {
                            return new LoadServiceResource(new URL("jar:file:" + entry + "!/" + canonicalEntry), "/" + name);
                        } else if (entry.startsWith("file:")) {
                            return new LoadServiceResource(new URL("jar:" + before + "!/" + canonicalEntry), entry + "/" + name);
                        } else {
                            return new LoadServiceResource(new URL("jar:file:" + entry.substring(4) + "!/" + name), entry + name);
                        }
                    } catch (MalformedURLException e) {
                        throw runtime.newIOErrorFromException(e);
                    }
                }
            } 
            try {
                if (!Ruby.isSecurityRestricted()) {
                    JRubyFile current = JRubyFile.create(
                            JRubyFile.create(runtime.getCurrentDirectory(),entry).getAbsolutePath(),
                            RubyFile.expandUserPath(runtime.getCurrentContext(), name));
                    if (current.isFile()) {
                        try {
                            // relative paths without ./ on front get absolute path
                            String resourcePath = name.startsWith("./") ? name : current.getPath();
                            
                            return new LoadServiceResource(current.toURI().toURL(), resourcePath);
                        } catch (MalformedURLException e) {
                            throw runtime.newIOErrorFromException(e);
                        }
                    }
                }
            } catch (SecurityException secEx) { }
        }

        if (checkCWD) {
            // check current directory; if file exists, retrieve URL and return resource
            try {
                JRubyFile file = JRubyFile.create(
                        runtime.getCurrentDirectory(),
                        RubyFile.expandUserPath(runtime.getCurrentContext(), name));
                if (file.isFile() && file.isAbsolute()) {
                    try {
                        return new LoadServiceResource(file.toURI().toURL(), name);
                    } catch (MalformedURLException e) {
                        throw runtime.newIOErrorFromException(e);
                    }
                }
            } catch (IllegalArgumentException illArgEx) {
            } catch (SecurityException secEx) {
            }
        }


        return null;
    }

    /**
     * this method uses the appropriate lookup strategy to find a file.
     * It is used by Kernel#require.
     *
     * @mri rb_find_file
     * @param name the file to find, this is a path name
     * @return the correct file
     */
    private LoadServiceResource findFileInClasspath(String name) {
        // Look in classpath next (we do not use File as a test since UNC names will match)
        // Note: Jar resources must NEVER begin with an '/'. (previous code said "always begin with a /")
        ClassLoader classLoader = runtime.getJRubyClassLoader();

        // handle security-sensitive case
        if (Ruby.isSecurityRestricted() && classLoader == null) {
            classLoader = runtime.getInstanceConfig().getLoader();
        }

        for (Iterator pathIter = loadPath.getList().iterator(); pathIter.hasNext();) {
            String entry = pathIter.next().toString();

            // if entry starts with a slash, skip it since classloader resources never start with a /
            if (entry.charAt(0) == '/' || (entry.length() > 1 && entry.charAt(1) == ':')) continue;
            
            // otherwise, try to load from classpath (Note: Jar resources always uses '/')
            URL loc = classLoader.getResource(entry + "/" + name);

            // Make sure this is not a directory or unavailable in some way
            if (isRequireable(loc)) {
                return new LoadServiceResource(loc, loc.getPath());
            }
        }

        // if name starts with a / we're done (classloader resources won't load with an initial /)
        if (name.charAt(0) == '/' || (name.length() > 1 && name.charAt(1) == ':')) return null;
        
        // Try to load from classpath without prefix. "A/b.rb" will not load as 
        // "./A/b.rb" in a jar file.
        URL loc = classLoader.getResource(name);

        return isRequireable(loc) ? new LoadServiceResource(loc, loc.getPath()) : null;
    }

    private Library tryLoadExtension(Library library, String file) {
        // This code exploits the fact that all .jar files will be found for the JarredScript feature.
        // This is where the basic extension mechanism gets fixed
        Library oldLibrary = library;
        
        if((library == null || library instanceof JarredScript) && !file.equalsIgnoreCase("")) {
            // Create package name, by splitting on / and joining all but the last elements with a ".", and downcasing them.
            String[] all = file.split("/");

            StringBuilder finName = new StringBuilder();
            for(int i=0, j=(all.length-1); i<j; i++) {
                finName.append(all[i].toLowerCase()).append(".");
                
            }
            
            try {
                // Make the class name look nice, by splitting on _ and capitalize each segment, then joining
                // the, together without anything separating them, and last put on "Service" at the end.
                String[] last = all[all.length-1].split("_");
                for(int i=0, j=last.length; i<j; i++) {
                    finName.append(Character.toUpperCase(last[i].charAt(0))).append(last[i].substring(1));
                }
                finName.append("Service");

                // We don't want a package name beginning with dots, so we remove them
                String className = finName.toString().replaceAll("^\\.*","");

                // If there is a jar-file with the required name, we add this to the class path.
                if(library instanceof JarredScript) {
                    // It's _really_ expensive to check that the class actually exists in the Jar, so
                    // we don't do that now.
                    runtime.getJRubyClassLoader().addURL(((JarredScript)library).getResource().getURL());
                }

                // quietly try to load the class
                Class theClass = runtime.getJavaSupport().loadJavaClassQuiet(className);
                library = new ClassExtensionLibrary(theClass);
            } catch (Exception ee) {
                library = null;
                runtime.getGlobalVariables().set("$!", runtime.getNil());
            }
        }
        
        // If there was a good library before, we go back to that
        if(library == null && oldLibrary != null) {
            library = oldLibrary;
        }
        return library;
    }
    
    /* Directories and unavailable resources are not able to open a stream. */
    private boolean isRequireable(URL loc) {
        if (loc != null) {
        	if (loc.getProtocol().equals("file") && new java.io.File(loc.getFile()).isDirectory()) {
        		return false;
        	}
        	
        	try {
                loc.openConnection();
                return true;
            } catch (Exception e) {}
        }
        return false;
    }
}
