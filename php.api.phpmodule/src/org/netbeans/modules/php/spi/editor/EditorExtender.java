/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.netbeans.modules.php.spi.editor;

import java.util.List;
import org.netbeans.modules.php.api.editor.PhpClass;
import org.netbeans.modules.php.api.editor.PhpBaseElement;
import org.openide.filesystems.FileObject;

/**
 * SPI for extending PHP editor.
 * <p>
 * <i>All the methods are called only for the {@link FileObject}
 * that is currently opened in the editor.</i>
 * @since 1.13
 * @author Tomas Mysik
 */
public abstract class EditorExtender {

    /**
     * Get the list of {@link PhpElement PHP elements} to be added to the code completion.
     * <p>
     * <i>Notice:</i> This method is currently optimized for Symfony PHP Framework only.
     * Future changes to be more general are probable.
     * @param fo {@link FileObject file object} in which the code completion is invoked
     * @return list of {@link PhpElement PHP elements} to be added to the code completion.
     */
    public abstract List<PhpBaseElement> getElementsForCodeCompletion(FileObject fo);

    /**
     * Get the {@link PhpClass PHP class} of the variable, returns <code>null</code> if not known.
     * <p>
     * <i>Notice:</i> This method is currently optimized for Symfony PHP Framework only.
     * Future changes to be more general are probable.
     * @param fo {@link FileObject file object} in which the code completion is invoked
     * @param variableName the name of a variable
     * @return the {@link PhpClass PHP class} of the variable, returns <code>null</code> if not known
     */
    public abstract PhpClass getClass(FileObject fo, String variableName);
}
