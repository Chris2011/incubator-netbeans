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

package org.netbeans.modules.php.spi.phpmodule;

import java.util.List;
import javax.swing.Action;
import org.netbeans.modules.php.spi.actions.GoToActionAction;
import org.netbeans.modules.php.spi.actions.GoToViewAction;
import org.openide.filesystems.FileObject;

/**
 * Provides support for extending a PHP module with a PHP framework's actions, that is,
 * it allows to add actions to the PHP module.
 *
 * @author Tomas Mysik
 */
public abstract class PhpModuleActionsExtender {

    /**
     * Get the name of the menu, typically the name of the framework.
     * @return the name of the menu, typically the name of the framework.
     */
    public abstract String getMenuName();

    /**
     * Get the list of actions for the given framework that will be displayed in the project's menu.
     * All <code>null</code> values are replaced by a separator.
     * @return list of actions, can be empty but never <code>null</code>.
     */
    public abstract List<? extends Action> getActions();

    /**
     * Return {@code true} if the given file object is a <em>view</em> (or a <em>template</em>).
     * <p>
     * If {@code true} is returned then {@link #getGoToActionAction(FileObject, int) getGoToActionAction()} cannot return {@code null}
     * ({@link IllegalStateException} is thrown in such case).
     * <p>
     * The default implementation returns {@code false}.
     * @param fo file object to check (the currently opened file in editor)
     * @return {@code true} if the given FileObject is a <em>view</em> (or a <em>template</em>)
     * @see #getGoToActionAction(FileObject, int)
     * @since 1.29
     */
    public boolean isViewWithAction(FileObject fo) {
        return false;
    }

    /**
     * Return {@code true} if the given file object is an <em>action</em> (or a <em>controller</em>).
     * <p>
     * If {@code true} is returned then {@link #getGoToViewAction(FileObject, int) getGoToViewAction()} cannot return {@code null}
     * ({@link IllegalStateException} is thrown in such case).
     * <p>
     * The default implementation returns {@code false}.
     * @param fo file object to check (the currently opened file in editor)
     * @return {@code true} if the given FileObject is an <em>action</em> (or a <em>controller</em>)
     * @see #getGoToViewAction(FileObject, int)
     * @since 1.29
     */
    public boolean isActionWithView(FileObject fo) {
        return false;
    }

    /**
     * Get instance of framework specific Go To Action action. It can return {@code null}
     * only if the given file object is not a {@link #isViewWithAction(FileObject) <em>view</em>}.
     * <p>
     * The default implementation returns {@code null}.
     * @param fo file object to get action for (the currently opened file in editor)
     * @param offset current offset in the file object
     * @return instance of framework specific Go To Action action or {@code null}
     * @see #isViewWithAction(FileObject)
     * @since 1.29
     */
    public GoToActionAction getGoToActionAction(FileObject fo, int offset) {
        return null;
    }

    /**
     * Get instance of framework specific Go To View action. It can return {@code null}
     * only if the given file object is not an {@link #isActionWithView(FileObject) <em>action</em>}.
     * <p>
     * The default implementation returns {@code null}.
     * @param fo file object to get action for (the currently opened file in editor)
     * @param offset current offset in the file object
     * @return instance of framework specific Go To View action or {@code null}
     * @see #isActionWithView(FileObject)
     * @since 1.29
     */
    public GoToViewAction getGoToViewAction(FileObject fo, int offset) {
        return null;
    }
}
