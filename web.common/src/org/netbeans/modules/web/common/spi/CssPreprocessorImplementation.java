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
package org.netbeans.modules.web.common.spi;

import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;

/**
 * Encapsulates a CSS preprocessor.
 * <p>
 * Instances of this class are {@link org.openide.util.lookup.ServiceProvider registered}
 * in the <code>{@value org.netbeans.modules.web.common.api.CssPreprocessors#PREPROCESSORS_PATH}</code> folder
 * in the module layer.
 * @see CssPreprocessorImplementationListener.Support
 * @since 1.39
 */
public interface CssPreprocessorImplementation {

    /**
     * Return the <b>non-localized (usually english)</b> identifier of this CSS preprocessor.
     * @return the <b>non-localized (usually english)</b> identifier; never {@code null}
     */
    @NonNull
    String getIdentifier();

    /**
     * Return the display name of this CSS preprocessor. The display name is used
     * in the UI.
     * @return the display name; never {@code null}
     */
    @NonNull
    String getDisplayName();

    /**
     * Process given file (can be a folder as well).
     * <p>
     * For folder, it usually means that all children should be processed.
     * <p>
     * <b>Warning:</b> The given file can be {@link FileObject#isValid() invalid}, it means deleted.
     * <p>
     * It usually means that if the given file can be processed by this CSS preprocessor, some action is done
     * (usually compiling).
     * @param project project where the file belongs, can be {@code null} for file without a project
     * @param fileObject valid or even invalid file (or folder) to be processed
     * @param originalName original file name (typically for rename), can be {@code null}
     * @param originalExtension original file extension (typically for rename), can be {@code null}
     * @since 1.52
     */
    void process(@NullAllowed Project project, @NonNull FileObject fileObject, @NullAllowed String originalName, @NullAllowed String originalExtension);

    /**
     * Attach a listener that is to be notified of changes
     * in this CSS peprocessor.
     * @param listener a listener, can be {@code null}
     * @since 1.44
     */
    void addCssPreprocessorListener(@NullAllowed CssPreprocessorImplementationListener listener);

    /**
     * Removes a listener.
     * @param listener a listener, can be {@code null}
     * @since 1.44
     */
    void removeCssPreprocessorListener(@NullAllowed CssPreprocessorImplementationListener listener);

    //~ Inner classes

}
