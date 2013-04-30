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
package org.netbeans.modules.web.common.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.project.Project;
import org.netbeans.modules.web.common.cssprep.CssPreprocessorAccessor;
import org.netbeans.modules.web.common.cssprep.CssPreprocessorsAccessor;
import org.netbeans.modules.web.common.cssprep.CssPreprocessorsCustomizer;
import org.netbeans.modules.web.common.cssprep.CssPreprocessorsProblemProvider;
import org.netbeans.modules.web.common.spi.CssPreprocessorImplementation;
import org.netbeans.modules.web.common.spi.CssPreprocessorImplementationListener;
import org.netbeans.spi.project.ui.ProjectProblemsProvider;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Parameters;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.Lookups;

/**
 * This class provides access to the list of registered CSS preprocessors. The path
 * for registration is "{@value #PREPROCESSORS_PATH}" on SFS.
 * <p>
 * This class is thread safe.
 * @since 1.37
 */
public final class CssPreprocessors {

    /**
     * Path on SFS for CSS preprocessors registrations.
     */
    public static final String PREPROCESSORS_PATH = "CSS/PreProcessors"; // NOI18N
    /**
     * Category name in Project Customizer.
     * @see #createCustomizer()
     * @since 1.41
     */
    public static final String CUSTOMIZER_IDENT = "CssPreprocessors"; // NOI18N
    /**
     * Top level category name in IDE Options.
     * @since 1.43
     */
    public static final String OPTIONS_CATEGORY = "Advanced"; // NOI18N
    /**
     * Subcategory name in IDE Options.
     * @since 1.43
     */
    public static final String OPTIONS_SUBCATEGORY = "CssPreprocessors"; // NOI18N
    /**
     * Full path in IDE Options.
     * @since 1.43
     */
    public static final String OPTIONS_PATH = OPTIONS_CATEGORY + "/" + OPTIONS_SUBCATEGORY; // NOI18N


    private static final RequestProcessor RP = new RequestProcessor(CssPreprocessors.class.getName(), 2);
    private static final Lookup.Result<CssPreprocessorImplementation> PREPROCESSORS = Lookups.forPath(PREPROCESSORS_PATH).lookupResult(CssPreprocessorImplementation.class);
    private static final CssPreprocessors INSTANCE = new CssPreprocessors();

    private final List<CssPreprocessor> preprocessors = new CopyOnWriteArrayList<>();
    final CssPreprocessorsListener.Support listenersSupport = new CssPreprocessorsListener.Support();
    private final PreprocessorImplementationsListener preprocessorImplementationsListener = new PreprocessorImplementationsListener();


    static {
        PREPROCESSORS.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent ev) {
                INSTANCE.reinitProcessors();
            }
        });
        CssPreprocessorsAccessor.setDefault(new CssPreprocessorsAccessor() {
            @Override
            public List<CssPreprocessor> getPreprocessors() {
                return INSTANCE.getPreprocessors();
            }
        });
    }

    private CssPreprocessors() {
        initProcessors();
    }

    /**
     * Get CssPreprocessors instance.
     * @return CssPreprocessors instance
     * @since 1.40
     */
    public static CssPreprocessors getDefault() {
        return INSTANCE;
    }

    List<CssPreprocessor> getPreprocessors() {
        return new ArrayList<>(preprocessors);
    }

    /**
     * Create project customizer for CSS preprocessors.
     * <p>
     * Category name is {@link #CUSTOMIZER_IDENT} ({@value #CUSTOMIZER_IDENT}).
     * <p>
     * Instance of this class can be registered for any project in its project customizer SFS folder.
     * @see ProjectCustomizer.CompositeCategoryProvider.Registration
     * @since 1.40
     */
    public ProjectCustomizer.CompositeCategoryProvider createCustomizer() {
        return new CssPreprocessorsCustomizer();
    }

    /**
     * Create provider of CSS preprocessors problems.
     * @param support support for creating and solving problems
     * @return provider of CSS preprocessors problems
     * @since 1.41
     */
    public ProjectProblemsProvider createProjectProblemsProvider(CssPreprocessor.ProjectProblemsProviderSupport support) {
        return CssPreprocessorsProblemProvider.create(support);
    }

    /**
     * Attach a listener that is to be notified of changes
     * in CSS preprocessors.
     * @param listener a listener, can be {@code null}
     * @since 1.44
     */
    public void addCssPreprocessorsListener(@NullAllowed CssPreprocessorsListener listener) {
        listenersSupport.addCssPreprocessorListener(listener);
    }

    /**
     * Removes a change listener.
     * @param listener a listener, can be {@code null}
     * @since 1.44
     */
    public void removeCssPreprocessorsListener(@NullAllowed CssPreprocessorsListener listener) {
        listenersSupport.removeCssPreprocessorListener(listener);
    }

    /**
     * Process given file (can be a folder as well) by {@link #getPreprocessors() all CSS preprocessors}.
     * <b>The project must have {@link org.netbeans.modules.web.common.spi.ProjectWebRootProvider}
     * in its lookup.</b>
     * <p>
     * For detailed information see {@link CssPreprocessorImplementation#process(Project, FileObject)}.
     * @param project project where the file belongs, can be {@code null} for file without a project
     * @param fileObject valid or even invalid file (or folder) to be processed
     * @since 1.42
     */
    public void process(@NullAllowed final Project project, @NonNull final FileObject fileObject) {
        processInternal(getPreprocessors(), project, fileObject);
    }

    /**
     * Process given file (can be a folder as well) by the given CSS preprocessor.
     * <p>
     * For detailed information see {@link CssPreprocessorImplementation#process(Project, FileObject)}.
     * @param cssPreprocessor CSS preprocesor
     * @param project project where the file belongs, can be {@code null} for file without a project
     * @param fileObject valid or even invalid file (or folder) to be processed
     * @since 1.42
     */
    public void process(@NonNull CssPreprocessor cssPreprocessor, @NullAllowed final Project project, @NonNull final FileObject fileObject) {
        Parameters.notNull("cssPreprocessor", cssPreprocessor); // NOI18N
        Parameters.notNull("fileObject", fileObject); // NOI18N
        processInternal(Collections.singletonList(cssPreprocessor), project, fileObject);
    }

    void processInternal(final List<CssPreprocessor> preprocessors, final Project project, final FileObject fileObject) {
        RP.post(new Runnable() {
            @Override
            public void run() {
                for (CssPreprocessor cssPreprocessor : preprocessors) {
                    cssPreprocessor.getDelegate().process(project, fileObject);
                }
            }
        });
    }

    private void initProcessors() {
        assert preprocessors.isEmpty() : "Empty preprocessors expected but: " + preprocessors;
        preprocessors.addAll(map(PREPROCESSORS.allInstances()));
        for (CssPreprocessor cssPreprocessor : preprocessors) {
            cssPreprocessor.getDelegate().addCssPreprocessorListener(preprocessorImplementationsListener);
        }
    }

    void reinitProcessors() {
        synchronized (preprocessors) {
            clearProcessors();
            initProcessors();
        }
        listenersSupport.firePreprocessorsChanged();
    }

    private void clearProcessors() {
        for (CssPreprocessor cssPreprocessor : preprocessors) {
            cssPreprocessor.getDelegate().removeCssPreprocessorListener(preprocessorImplementationsListener);
        }
        preprocessors.clear();
    }

    @CheckForNull
    CssPreprocessor findCssPreprocessor(CssPreprocessorImplementation cssPreprocessorImplementation) {
        assert cssPreprocessorImplementation != null;
        for (CssPreprocessor cssPreprocessor : preprocessors) {
            if (cssPreprocessor.getDelegate() == cssPreprocessorImplementation) {
                return cssPreprocessor;
            }
        }
        assert false : "Cannot find CSS preprocessor for implementation: " + cssPreprocessorImplementation.getIdentifier();
        return null;
    }

    //~ Mappers

    private List<CssPreprocessor> map(Collection<? extends CssPreprocessorImplementation> preprocessors) {
        List<CssPreprocessor> result = new ArrayList<>();
        for (CssPreprocessorImplementation cssPreprocessor : preprocessors) {
            result.add(CssPreprocessorAccessor.getDefault().create(cssPreprocessor));
        }
        return result;
    }

    //~ Inner classes

    private final class PreprocessorImplementationsListener implements CssPreprocessorImplementationListener {

        @Override
        public void optionsChanged(CssPreprocessorImplementation cssPreprocessor) {
            Parameters.notNull("cssPreprocessor", cssPreprocessor); // NOI18N
            CssPreprocessor preprocessor = findCssPreprocessor(cssPreprocessor);
            if (preprocessor != null) {
                listenersSupport.fireOptionsChanged(preprocessor);
            }
        }

        @Override
        public void customizerChanged(Project project, CssPreprocessorImplementation cssPreprocessor) {
            Parameters.notNull("project", project); // NOI18N
            Parameters.notNull("cssPreprocessor", cssPreprocessor); // NOI18N
            CssPreprocessor preprocessor = findCssPreprocessor(cssPreprocessor);
            if (preprocessor != null) {
                listenersSupport.fireCustomizerChanged(project, preprocessor);
            }
        }

        @Override
        public void processingErrorOccured(Project project, CssPreprocessorImplementation cssPreprocessor, String error) {
            Parameters.notNull("project", project); // NOI18N
            Parameters.notNull("cssPreprocessor", cssPreprocessor); // NOI18N
            Parameters.notNull("error", error); // NOI18N
            CssPreprocessor preprocessor = findCssPreprocessor(cssPreprocessor);
            if (preprocessor != null) {
                listenersSupport.fireProcessingErrorOccured(project, preprocessor, error);
            }
        }

    }

}
