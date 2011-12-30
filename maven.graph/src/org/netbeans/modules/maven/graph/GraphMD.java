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

package org.netbeans.modules.maven.graph;

import java.awt.Image;
import java.io.Serializable;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import static org.netbeans.modules.maven.graph.Bundle.*;
import org.netbeans.modules.maven.indexer.api.ui.ArtifactViewer;
import org.netbeans.modules.maven.indexer.spi.ui.ArtifactViewerPanelProvider;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.TopComponent;

/**
 *
 * @author mkleint
 */
public class GraphMD implements MultiViewDescription, Serializable {

    private Lookup lookup;

    GraphMD(Lookup lkp) {
        lookup = lkp;
    }


    @Override public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override public String getDisplayName() {
        return TAB_Graph();
    }

    @Override public Image getIcon() {
        return ImageUtilities.loadImage(DependencyGraphTopComponent.DEPENDENCY_JAR, true);
    }

    @Override public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override public String preferredID() {
        return ArtifactViewer.HINT_GRAPH;
    }

    @Override public MultiViewElement createElement() {
        return new DependencyGraphTopComponent(lookup);
    }

    @ServiceProvider(service=ArtifactViewerPanelProvider.class, position=400)
    public static class Factory implements ArtifactViewerPanelProvider {

        @Override public MultiViewDescription createPanel(Lookup content) {
            return new GraphMD(content);
        }
    }

}
