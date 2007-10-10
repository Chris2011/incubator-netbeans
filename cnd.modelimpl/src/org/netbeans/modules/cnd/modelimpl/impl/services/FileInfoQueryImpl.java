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
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */
package org.netbeans.modules.cnd.modelimpl.impl.services;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.netbeans.modules.cnd.api.model.CsmFile;
import org.netbeans.modules.cnd.api.model.CsmOffsetable;
import org.netbeans.modules.cnd.api.model.services.CsmFileInfoQuery;
import org.netbeans.modules.cnd.api.project.NativeFileItem;
import org.netbeans.modules.cnd.apt.structure.APTFile;
import org.netbeans.modules.cnd.apt.support.APTDriver;
import org.netbeans.modules.cnd.modelimpl.csm.core.FileImpl;
import org.netbeans.modules.cnd.modelimpl.csm.core.ProjectBase;
import org.netbeans.modules.cnd.modelimpl.parser.apt.APTFindUnusedBlocksWalker;

/**
 * implementaion of CsmFileInfoQuery
 * @author Vladimir Voskresenskky
 */
public class FileInfoQueryImpl extends CsmFileInfoQuery {

    public List<String> getSystemIncludePaths(CsmFile file) {
        return getIncludePaths(file, true);
    }

    public List<String> getUserIncludePaths(CsmFile file) {
        return getIncludePaths(file, false);
    }
    
    private List<String> getIncludePaths(CsmFile file, boolean system) {
        List<String> out = Collections.<String>emptyList();
        if (file instanceof FileImpl) {
            NativeFileItem item = ProjectBase.getCompiledFileItem((FileImpl)file);
            if (item != null) {
                if (system) {
                    out = item.getSystemIncludePaths();
                } else {
                    out = item.getUserIncludePaths();
                }
            }   
        }
        return out;
    }

    public List<CsmOffsetable> getUnusedCodeBlocks(CsmFile file) {
        List<CsmOffsetable> out = Collections.<CsmOffsetable>emptyList();
        if (file instanceof FileImpl) {
            FileImpl fileImpl = (FileImpl)file;
            try {
                //APTFile apt = APTDriver.getInstance().findAPT(fileImpl.getBuffer());
                APTFile apt = APTDriver.getInstance().findAPTLight(fileImpl.getBuffer());
                if (apt != null) {
                    APTFindUnusedBlocksWalker walker = 
                        new APTFindUnusedBlocksWalker(apt, fileImpl, fileImpl.getPreprocHandler());
                    walker.visit();
                    out = walker.getBlocks();
               }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }
}
