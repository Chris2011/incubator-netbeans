/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */

package org.netbeans.modules.cnd.model.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.cnd.api.model.CsmFile;
import org.netbeans.modules.cnd.api.model.CsmListeners;
import org.netbeans.modules.cnd.api.model.CsmProgressListener;
import org.netbeans.modules.cnd.api.model.CsmProject;
import org.netbeans.modules.cnd.api.model.services.CsmCompilationUnit;
import org.netbeans.modules.cnd.api.model.services.CsmFileInfoQuery;
import org.netbeans.modules.cnd.api.model.xref.CsmIncludeHierarchyResolver;
import org.netbeans.modules.cnd.api.project.CodeAssistance;
import org.netbeans.modules.cnd.api.project.NativeFileItem;
import org.netbeans.modules.cnd.api.project.NativeProject;
import org.netbeans.modules.cnd.modelutil.CsmUtilities;
import org.netbeans.modules.cnd.utils.CndUtils;
import org.openide.filesystems.FileObject;
import org.openide.util.Pair;

/**
 *
 * @author Alexander Simon
 */
@org.openide.util.lookup.ServiceProvider(service=org.netbeans.modules.cnd.api.project.CodeAssistance.class)
public class CsmCodeAssistanceProvider implements CodeAssistance, CsmProgressListener {
    private static final WeakHashMap<ChangeListener,Boolean> listeners = new WeakHashMap<ChangeListener,Boolean>();
    private final static Object lock = new Object();
    
    public CsmCodeAssistanceProvider() {
        CsmListeners.getDefault().addProgressListener(this);
    }

    @Override
    public boolean hasCodeAssistance(NativeFileItem item) {
        CndUtils.assertNonUiThread();
        CsmFile csmFile = CsmUtilities.getCsmFile(item, false, false);
        return csmFile != null;
    }

    @Override
    public CodeAssistance.State getCodeAssistanceState(NativeFileItem item) {
        CsmFile csmFile = CsmUtilities.getCsmFile(item, false, false);
        if (csmFile != null) {
            if (csmFile.isHeaderFile()) {
                if (CsmIncludeHierarchyResolver.getDefault().getFiles(csmFile).isEmpty()) {
                    return State.ParsedOrphanHeader;
                }
                return State.ParsedIncludedHeader;
            } else {
                return State.ParsedSource;
            }
        }
        return State.NotParsed;
    }

    @Override
    public Pair<NativeFileItem.Language, NativeFileItem.LanguageFlavor> getStartFileLanguageFlavour(NativeFileItem item) {
        CsmFile csmFile = CsmUtilities.getCsmFile(item, false, false);
        if (csmFile != null) {
            Collection<CsmCompilationUnit> compilationUnits = CsmFileInfoQuery.getDefault().getCompilationUnits(csmFile, 0);
            if (!compilationUnits.isEmpty()) {
                CsmCompilationUnit firstCU = compilationUnits.iterator().next();
                CsmFile startFile = firstCU.getStartFile();
                Object platformProject = startFile.getProject().getPlatformProject();
                if (platformProject instanceof NativeProject) {
                    NativeProject np = (NativeProject) platformProject;
                    NativeFileItem ni = np.findFileItem(startFile.getFileObject());
                    if (ni != null && ni != item && startFile.isSourceFile()) {
                        return Pair.of(ni.getLanguage(), ni.getLanguageFlavor());
                    }
                }
            }
            if (csmFile.isHeaderFile()) {
                return Pair.of(NativeFileItem.Language.C_HEADER, NativeFileItem.LanguageFlavor.UNKNOWN);
            } else if (csmFile.isSourceFile()) {
                return Pair.of(NativeFileItem.Language.CPP, NativeFileItem.LanguageFlavor.UNKNOWN);
            } 
        }
        return Pair.of(NativeFileItem.Language.OTHER, NativeFileItem.LanguageFlavor.UNKNOWN);
    }
    
    @Override
    public void addChangeListener(ChangeListener listener){
        synchronized(lock) {
            listeners.put(listener,Boolean.TRUE);
        }
    }

    @Override
    public void removeChangeListener(ChangeListener listener){
        synchronized(lock) {
            listeners.remove(listener);
        }
    }
    
    @Override
    public void projectParsingStarted(CsmProject project) {
    }

    @Override
    public void projectFilesCounted(CsmProject project, int filesCount) {
    }

    @Override
    public void projectParsingFinished(CsmProject project) {
        //fireChanges(project);
    }

    @Override
    public void projectParsingCancelled(CsmProject project) {
    }

    @Override
    public void projectLoaded(CsmProject project) {
        fireChanges(project);
    }

    @Override
    public void fileInvalidated(CsmFile file) {
    }

    @Override
    public void fileAddedToParse(CsmFile file) {
    }

    @Override
    public void fileParsingStarted(CsmFile file) {
    }

    @Override
    public void fileParsingFinished(CsmFile file) {
        fireChanges(file);
    }

    private void fireChanges(CsmFile file) {
        if (file == null) {
            return;
        }
        FileObject fileObject = file.getFileObject();
        if (fileObject == null) {
            return;
        }
        ChangeEvent changeEvent = new ChangeEvent(fileObject);
        List<ChangeListener> list;
        synchronized (lock) {
            list = new ArrayList<ChangeListener>(listeners.keySet());
        }
        for (ChangeListener listener : list) {
            listener.stateChanged(changeEvent);
        }
    }

    private void fireChanges(CsmProject project) {
        Object platformProject = project.getPlatformProject();
        if (platformProject instanceof NativeProject) {
            ChangeEvent changeEvent = new ChangeEvent(platformProject);
            List<ChangeListener> list;
            synchronized (lock) {
                list = new ArrayList<ChangeListener>(listeners.keySet());
            }
            for (ChangeListener listener : list) {
                listener.stateChanged(changeEvent);
            }
        }
    }

    @Override
    public void parserIdle() {
    }
}
