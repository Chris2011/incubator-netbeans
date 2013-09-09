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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.team.commons.treelist;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.MissingResourceException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.openide.util.Cancellable;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 * Node which creates its renderer component asynchronously.
 *
 * @param <T> The type of data displayed in this Node.
 *
 * @author S. Aubrecht
 */
public abstract class AsynchronousNode<T> extends TreeListNode {

    private JComponent inner = null;
    private JPanel panel;
    private JLabel lblTitle;
    private ProgressLabel lblLoading;
    private JLabel lblError;
    private LinkButton btnRetry;
    private boolean loaded = false;
    private Loader loader;
    private final Object LOCK = new Object();
    private JLabel lblFill;
    private boolean expandAfterRefresh;;
    private final String title;
    private static final RequestProcessor RP = new RequestProcessor("Asynchronous Tree List Node - Loader", 5); // NOI18N

    /**
     * C'tor
     *
     * @param expandable True if the node provides some children
     * @param parent Node parent or null.
     * @param title Title to show in node's renderer while its actual content is
     * getting created, can be null.
     */
    public AsynchronousNode(boolean expandable, TreeListNode parent, String title) {
        super(expandable, parent);
        this.title = title;
    }

    @Override
    protected final JComponent getComponent(Color foreground, Color background, boolean isSelected, boolean hasFocus, int rowWidth) {
        synchronized (LOCK) {
            if(panel == null) {
                getPanel(); // init
            }
            if (null != inner) {
                configure(inner, foreground, background, isSelected, hasFocus, rowWidth);
            } else {
                if (!loaded) {
                    if (null == loader) {
                        startLoading();
                    }
                }
                if (isSelected) {
                    lblLoading.setForeground(foreground);
                    lblError.setForeground(foreground);
                    btnRetry.setForeground(foreground);
                } else {
                    lblLoading.setForeground(ColorManager.getDefault().getDisabledColor());
                    lblError.setForeground(ColorManager.getDefault().getErrorColor());
                }
                lblTitle.setForeground(foreground);
            }
        }
        return panel;
    }

    /**
     * Configure renderer component's colors.
     *
     * @param component Component return from createComponent() call.
     * @param foreground
     * @param background
     * @param isSelected
     * @param hasFocus
     * @param rowWidth
     */
    protected abstract void configure(JComponent component, Color foreground, Color background, boolean isSelected, boolean hasFocus, int rowWidth);

    /**
     * Creates node's renderer component. The method is always invoked from AWT
     * thread.
     *
     * @param data
     * @return Renderer component, never null.
     */
    protected abstract JComponent createComponent(T data);

    /**
     * Retrieve data to display in this node. The method is called outside AWT
     * thread and can block indefinetely.
     *
     * @return Node's data.
     */
    protected abstract T load();

    /**
     * Invoke this method to recreate node's renderer component.
     */
    protected final void refresh() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    expandAfterRefresh = isExpandable() && isExpanded();
                    if (expandAfterRefresh) {
                        setExpanded(false);
                    }
                    loaded = false;
                    JPanel p = getPanel();
                    if (null != inner) {
                        p.remove(inner);
                        lblTitle.setText(title);
                        p.add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                        p.add(lblFill, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                    }
                    inner = null;
                    startLoading();
                }
            }
        });
    }

    private void startLoading() {
        synchronized (LOCK) {
            loaded = false;
            lblLoading.setVisible(true);
            lblError.setVisible(false);
            btnRetry.setVisible(false);
        }
        if (null != loader) {
            loader.cancel();
        }
        loader = new Loader();
        RP.post(loader);
    }

    private void timedout() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    lblError.setVisible(true);
                    btnRetry.setVisible(true);
                    lblLoading.setVisible(false);
                    loaded = true;
                    loader = null;
                }
                fireContentChanged();
            }
        });
    }

    private void loaded(final T data) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    JComponent c = createComponent(data);
                    loaded = true;
                    JPanel p = getPanel();
                    if (null == c) {
                        lblLoading.setVisible(false);
                        lblError.setVisible(true);
                        btnRetry.setVisible(true);
                    } else {
                        lblLoading.setVisible(false);
                        lblError.setVisible(false);
                        btnRetry.setVisible(false);
                        if (null != inner) {
                            p.remove(inner);
                        }
                        inner = c;
                        p.remove(lblTitle);
                        lblTitle.setText("");
                        p.remove(lblFill);
                        p.add(inner, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
                    }
                    p.invalidate();
                    p.revalidate();
                    p.repaint();
                    loader = null;
                    if (expandAfterRefresh) {
                        setExpanded(true);
                    }
                }
                fireContentChanged();
            }
        });
    }

    private JPanel getPanel() throws MissingResourceException {
        if (panel == null) {
            panel = new JPanel(new GridBagLayout());
            panel.setOpaque(false);
            lblTitle = new TreeLabel(this.title);
            lblLoading = createProgressLabel(""); //NOI18N
            lblLoading.setForeground(ColorManager.getDefault().getDisabledColor());
            lblError = new TreeLabel(NbBundle.getMessage(AsynchronousNode.class, "LBL_NotResponding")); //NOI18N
            lblError.setForeground(ColorManager.getDefault().getErrorColor());
            Image img = ImageUtilities.loadImage("org/netbeans/modules/team/commons/resources/error.png"); //NOI18N
            lblError.setIcon(new ImageIcon(img));
            lblFill = new JLabel();
            btnRetry = new LinkButton(NbBundle.getMessage(AsynchronousNode.class, "LBL_Retry"), new AbstractAction() { //NOI18N
                @Override
                public void actionPerformed(ActionEvent e) {
                    refresh();
                }
            });
            
            panel.add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            panel.add(lblFill, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            panel.add(lblLoading, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 4, 0, 0), 0, 0));
            panel.add(lblError, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 4, 0, 0), 0, 0));
            panel.add(btnRetry, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        }
        return panel;
    }

    private class Loader implements Runnable, Cancellable {

        private boolean cancelled = false;
        private Thread t = null;

        @Override
        public void run() {
            final Object[] res = new Object[1];
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    res[0] = load();
                }
            };
            t = new Thread(r);
            t.start();
            try {
                t.join(TreeListNode.TIMEOUT_INTERVAL_MILLIS);
                if (null == res[0]) {
                    timedout();
                    return;
                }
                if (cancelled) {
                    return;
                }

                loaded((T) res[0]);
            } catch (InterruptedException iE) {
                if (!cancelled) {
                    timedout();
                }
            }
        }

        @Override
        public boolean cancel() {
            cancelled = true;
            if (null != t) {
                t.interrupt();
            }
            return true;
        }
    }
}