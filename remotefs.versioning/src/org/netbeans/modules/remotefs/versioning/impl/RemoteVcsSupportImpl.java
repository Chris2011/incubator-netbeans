/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */
package org.netbeans.modules.remotefs.versioning.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironmentFactory;
import org.netbeans.modules.nativeexecution.api.HostInfo;
import org.netbeans.modules.nativeexecution.api.util.ConnectionManager;
import org.netbeans.modules.nativeexecution.api.util.HostInfoUtils;
import org.netbeans.modules.remote.api.ui.FileChooserBuilder;
import org.netbeans.modules.remote.impl.fileoperations.spi.RemoteVcsSupportUtil;
import org.netbeans.modules.remote.spi.FileSystemProvider;
import org.netbeans.modules.remotefs.versioning.spi.RemoteVcsSupportImplementation;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileSystem;
import org.openide.util.Utilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author vkvashin
 */
@ServiceProvider(service = RemoteVcsSupportImplementation.class)
public class RemoteVcsSupportImpl implements RemoteVcsSupportImplementation {

    private static final Logger LOGGER = Logger.getLogger(RemoteVcsSupportImpl.class.getName());

    public RemoteVcsSupportImpl() {
    }

    
    @Override
    public JFileChooser createFileChooser(VCSFileProxy proxy) {
        FileSystem fs = getFileSystem(proxy);
        FileChooserBuilder fcb = new FileChooserBuilder(FileSystemProvider.getExecutionEnvironment(fs));
        FileChooserBuilder.JFileChooserEx chooser = fcb.createFileChooser(proxy.getPath());
        return chooser;
    }

    @Override
    public VCSFileProxy getSelectedFile(JFileChooser chooser) {
        if (chooser instanceof FileChooserBuilder.JFileChooserEx) {
            FileObject fo = ((FileChooserBuilder.JFileChooserEx) chooser).getSelectedFileObject();
            if (fo != null) {
                return VCSFileProxy.createFileProxy(fo);
            }
        } else {
            File file = chooser.getSelectedFile();
            if (file != null) {
                return VCSFileProxy.createFileProxy(file);
            }
        }
        return null;
    }

    @Override
    public FileSystem getFileSystem(VCSFileProxy proxy) {
        File file = proxy.toFile();
        if (file != null) {
            return FileSystemProvider.getFileSystem(ExecutionEnvironmentFactory.getLocal());
        } else {
            VCSFileProxy root = getRootFileProxy(proxy);
            try {
                return root.toFileObject().getFileSystem();
            } catch (FileStateInvalidException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    private VCSFileProxy getRootFileProxy(VCSFileProxy proxy) {
        VCSFileProxy root = proxy;
        while (root.getParentFile() != null) {
            root = root.getParentFile();
        }
        return root;
    }

    @Override
    public FileSystem[] getFileSystems() {
        // TODO: get list from cnd.remote !!!
        List<ExecutionEnvironment> execEnvs = ConnectionManager.getInstance().getRecentConnections();
        List<FileSystem> fileSystems = new ArrayList<FileSystem>(execEnvs.size());
        for (ExecutionEnvironment env : execEnvs) {
            if (env.isRemote()) {
                fileSystems.add(FileSystemProvider.getFileSystem(env));
            }
        }
        return fileSystems.toArray(new FileSystem[fileSystems.size()]);
    }

    @Override
    public FileSystem getDefaultFileSystem() {
        // TODO: get default from cnd.remote !!!
        FileSystem[] fsList = getFileSystems();
        return (fsList.length > 0) ? fsList[0] : null;
    }

    @Override
    public boolean isSymlink(VCSFileProxy proxy) {
        File file = proxy.toFile();
        if (file != null) {
            Path path = file.toPath();
            return Files.isSymbolicLink(path);
        } else {
            return RemoteVcsSupportUtil.isSymbolicLink(getFileSystem(proxy), proxy.getPath());
        }
    }

    @Override
    public boolean canRead(VCSFileProxy proxy) {
        File file = proxy.toFile();
        if (file != null) {
            return file.canRead();
        } else {
            return RemoteVcsSupportUtil.canRead(getFileSystem(proxy), proxy.getPath());
        }
    }

    @Override
    public boolean canRead(VCSFileProxy base, String subdir) {
        File baseFile = base.toFile();
        if (baseFile != null) {
            if (baseFile.isFile()) {
                return false;
            }
            return new File(baseFile, subdir).canRead();
        } else {
            if (base.isFile()) {
                return false;
            }
            String path = base.getPath().trim();
            path += ((path.endsWith("/") || subdir.startsWith("/")) ? "" : "/") + subdir; // NOI18N
            return RemoteVcsSupportUtil.canRead(getFileSystem(base), path);
        }
    }    

    @Override
    public String getCanonicalPath(VCSFileProxy proxy) throws IOException {
        File file = proxy.toFile();
        if (file != null) {
            File canonicalFile = file.getCanonicalFile();
            return canonicalFile.getAbsolutePath();
        } else {
            String canonical = RemoteVcsSupportUtil.getCanonicalPath(getFileSystem(proxy), proxy.getPath());
            return (canonical == null) ? proxy.getPath() : canonical;
        }    
    }

    @Override
    public VCSFileProxy getCanonicalFile(VCSFileProxy proxy) throws IOException {
        File file = proxy.toFile();
        if (file != null) {
            File canonicalFile = file.getCanonicalFile();
            return VCSFileProxy.createFileProxy(canonicalFile);
        } else {
            String canonical = RemoteVcsSupportUtil.getCanonicalPath(getFileSystem(proxy), proxy.getPath());
            if (canonical == null) {
                return proxy;
            } else {
                VCSFileProxy root = getRootFileProxy(proxy);
                return VCSFileProxy.createFileProxy(root, canonical);
            }
        }
    }

    @Override
    public VCSFileProxy getHome(VCSFileProxy proxy) {
        File file = proxy.toFile();
        if (file != null) {
            return VCSFileProxy.createFileProxy(new File(System.getProperty("user.home")));
        } else {
            FileSystem fs = getFileSystem(proxy);
            ExecutionEnvironment env = FileSystemProvider.getExecutionEnvironment(fs);
            if (HostInfoUtils.isHostInfoAvailable(env)) {
                try {
                    String userDir = HostInfoUtils.getHostInfo(env).getUserDir();
                    VCSFileProxy root = getRootFileProxy(proxy);
                    return VCSFileProxy.createFileProxy(root, userDir);
                } catch (IOException ex) {
                    Logger.getLogger(RemoteVcsSupportImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                } catch (ConnectionManager.CancellationException ex) {
                    Logger.getLogger(RemoteVcsSupportImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            } else {
                // TODO: what to return here???
                return null;
            }
        }     
    }

    @Override
    public boolean isMac(VCSFileProxy proxy) {
        File file = proxy.toFile();
        if (file != null) {
            return Utilities.isMac();
        } else {
            FileSystem fs = getFileSystem(proxy);
            ExecutionEnvironment env = FileSystemProvider.getExecutionEnvironment(fs);
            if (HostInfoUtils.isHostInfoAvailable(env)) {
                try {
                    return HostInfoUtils.getHostInfo(env).getOSFamily() == HostInfo.OSFamily.MACOSX;
                } catch (IOException ex) {
                    Logger.getLogger(RemoteVcsSupportImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                } catch (ConnectionManager.CancellationException ex) {
                    Logger.getLogger(RemoteVcsSupportImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            } else {
                // TODO: what to return here???
                return false;
            }
        }     
    }

    @Override
    public boolean isUnix(VCSFileProxy proxy) {
        File file = proxy.toFile();
        if (file != null) {
            return Utilities.isUnix();
        } else {
            FileSystem fs = getFileSystem(proxy);
            ExecutionEnvironment env = FileSystemProvider.getExecutionEnvironment(fs);
            if (HostInfoUtils.isHostInfoAvailable(env)) {
                try {
                    switch (HostInfoUtils.getHostInfo(env).getOSFamily()) {
                        case LINUX:
                        case MACOSX:
                        case SUNOS:
                            return true;
                        case WINDOWS:
                            return false;
                        case UNKNOWN:
                            return false;
                        default:
                            throw new IllegalStateException("Unexpected OSFamily: " + this); //NOI18N
                    }
                } catch (IOException ex) {
                    Logger.getLogger(RemoteVcsSupportImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                } catch (ConnectionManager.CancellationException ex) {
                    Logger.getLogger(RemoteVcsSupportImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            } else {
                // TODO: what to return here???
                return false;
            }
        }     
    }

    @Override
    public long getSize(VCSFileProxy proxy) {
        File file = proxy.toFile();
        if (file != null) {
            return file.length();
        } else {
            return RemoteVcsSupportUtil.getSize(getFileSystem(proxy), proxy.getPath());
        }
    }

    @Override
    public OutputStream getOutputStream(VCSFileProxy proxy) throws IOException {
        File file = proxy.toFile();
        if (file != null) {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            return new FileOutputStream(file);
        } else {
            return RemoteVcsSupportUtil.getOutputStream(getFileSystem(proxy), proxy.getPath());
        }
    }

    @Override
    public String getFileSystemKey(FileSystem fs) {
        final String toUrl = FileSystemProvider.toUrl(fs, "/"); // NOI18N
        return toUrl.substring(0, toUrl.indexOf('/')); //NOI18N
    }

    @Override
    public String toString(VCSFileProxy proxy) {
        return FileSystemProvider.toUrl(getFileSystem(proxy), proxy.getPath());
    }

    @Override
    public VCSFileProxy fromString(String proxyString) {
        FileSystem fs = FileSystemProvider.urlToFileSystem(proxyString);
        FileObject root = fs.getRoot();
        VCSFileProxy rootProxy = VCSFileProxy.createFileProxy(fs.getRoot());
        return VCSFileProxy.createFileProxy(rootProxy, proxyString);
    }

    @Override
    public void delete(VCSFileProxy file) {
        File javaFile = file.toFile();
        if (javaFile != null) {
            deleteRecursively(javaFile);
        } else {
            RemoteVcsSupportUtil.delete(getFileSystem(file), file.getPath());
        }
    }

    private static void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    deleteRecursively(files[i]);
                }
            }
        }
        file.delete();
    }
}
