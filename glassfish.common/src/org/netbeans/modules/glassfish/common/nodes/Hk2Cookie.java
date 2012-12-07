/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.glassfish.common.nodes;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.glassfish.tools.ide.admin.*;
import org.netbeans.modules.glassfish.common.CommonServerSupport;
import org.netbeans.modules.glassfish.common.GlassfishInstance;
import org.netbeans.modules.glassfish.common.PartialCompletionException;
import org.netbeans.modules.glassfish.common.Util;
import org.netbeans.modules.glassfish.common.nodes.actions.*;
import org.netbeans.modules.glassfish.common.ui.BasePanel;
import org.netbeans.modules.glassfish.spi.GlassfishModule;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.windows.WindowManager;

/**
 * HK2 nodes cookies.
 * <p/>
 * @author Tomas Kraus
 */
public class Hk2Cookie {
    
    ////////////////////////////////////////////////////////////////////////////
    // Inner classes                                                          //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Common node cookie.
     */
    private static abstract class Cookie {

        /** Task status. */
        volatile WeakReference<Future<ResultString>> status;

        /** GlassFish server instance. */
        final GlassfishInstance instance;

        /** Resource name. */
        final String name;

        /**
         * Creates an instance of cookie.
         * <p/>
         * @param lookup Lookup containing {@see CommonServerSupport}.
         * @param name   Name of resource to be enabled.
         */
        Cookie(final Lookup lookup, final String name) {
            this.instance = getGlassFishInstance(lookup);
            this.name = name;
        }

        /**
         * Returns <code>true</code> if this task is still running.
         * <p/>
         * @return Value of <code>true</code> if this task is still running
         *         or <code>false</code> otherwise.
         */
        public boolean isRunning() {
            WeakReference<Future<ResultString>> localref = status;
            if (localref == null) {
                return false;
            }
            Future<ResultString> future = localref.get();
            return future != null && !future.isDone();
        }
    }

    /**
     * Enable node cookie.
     */
    static class Enable
            extends Cookie implements EnableModulesCookie {

        /**
         * Creates an instance of cookie for enabling module.
         * <p/>
         * @param lookup Lookup containing {@see CommonServerSupport}.
         * @param name   Name of resource to be enabled.
         */
        Enable(final Lookup lookup, final String name) {
            super(lookup, name);
        }

        /**
         * Enable module on GlassFish server.
         * <p/>
         * @return Result of enable task execution.
         */
        @Override
        public Future<ResultString> enableModule() {
            if (instance != null) {
                Future<ResultString> future = ServerAdmin.<ResultString>exec(
                        instance, new CommandEnable(name, Util.computeTarget(
                        instance.getProperties())), null);
                status = new WeakReference<Future<ResultString>>(future);
                return future;
            } else {
                return null;
            }
        }
    }

    /**
     * Disable node cookie.
     */
    static class Disable
            extends Cookie implements DisableModulesCookie {

        /**
         * Creates an instance of cookie for disabling module.
         * <p/>
         * @param lookup Lookup containing {@see CommonServerSupport}.
         * @param name   Name of resource to be enabled.
         */
        Disable(final Lookup lookup, final String name) {
            super(lookup, name);
        }

        /**
         * Disable module on GlassFish server.
         * <p/>
         * @return Result of disable task execution.
         */
        @Override
        public Future<ResultString> disableModule() {
            if (instance != null) {
                Future<ResultString> future = ServerAdmin.<ResultString>exec(
                        instance, new CommandDisable(name, Util.computeTarget(
                        instance.getProperties())), null);
                status = new WeakReference<Future<ResultString>>(future);
                return future;
            } else {
                return null;
            }
        }
    }

    /**
     * Undeploy node cookie.
     */
    static class Undeploy
            extends Cookie implements UndeployModuleCookie {

        /**
         * Creates an instance of cookie for disabling module.
         * <p/>
         * @param lookup Lookup containing {@see CommonServerSupport}.
         * @param name   Name of resource to undeploy.
         */
        Undeploy(final Lookup lookup, final String name) {
            super(lookup, name);
        }

        /**
         * Undeploy module on GlassFish server.
         * <p/>
         * @return Result of undeploy task execution.
         */
        @Override
        public Future<ResultString> undeploy() {
            if (instance != null) {
                Future<ResultString> future = ServerAdmin.<ResultString>exec(
                        instance, new CommandUndeploy(name, Util.computeTarget(
                        instance.getProperties())), null);
                status = new WeakReference<Future<ResultString>>(future);
                return future;
            } else {
                return null;
            }
        }
    }

    /**
     * Deploy node cookie.
     */
    static class Deploy
            extends Cookie implements DeployDirectoryCookie {

        /**
         * Creates an instance of cookie for disabling module.
         * <p/>
         * @param lookup Lookup containing {@see CommonServerSupport}.
         */
        Deploy(final Lookup lookup) {
            super(lookup, null);
        }

        /**
         * Deploy module from directory on GlassFish server.
         * <p/>
         * @return Result of undeploy task execution.
         */
        @Override
        public Future<ResultString> deployDirectory() {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle(NbBundle.getMessage(Hk2ItemNode.class,
                    "LBL_ChooseButton"));
            chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setMultiSelectionEnabled(false);

            int returnValue = chooser.showDialog(WindowManager.getDefault()
                    .getMainWindow(), NbBundle.getMessage(
                    Hk2ItemNode.class, "LBL_ChooseButton"));
            if (instance != null
                    || returnValue != JFileChooser.APPROVE_OPTION) {
                return null;
            }

            final File dir
                    = new File(chooser.getSelectedFile().getAbsolutePath());

            Future<ResultString> future = ServerAdmin.<ResultString>exec(
                    instance, new CommandDeploy(dir.getParentFile().getName(),
                    Util.computeTarget(instance.getProperties()),
                    dir, null, null, null), null);
            status = new WeakReference<Future<ResultString>>(future);
            return future;
        }
    }

    /**
     * Unregister node cookie.
     */
    static class Unregister
            extends Cookie implements UnregisterResourceCookie {

        /** Command suffix. */
        final String cmdSuffix;

        /** Name of query property which contains resource name. */
        final String cmdPropertyName;

        /** Delete also dependent resources when <code>true</code>. */
        final boolean cascadeDelete;

        /**
         * Creates an instance of cookie for unregistering module.
         * <p/>
         * @param lookup          Lookup containing {@see CommonServerSupport}.
         * @param name            Name of resource to unregister.
         * @param cmdSuffix       Resource related command suffix.
         * @param cmdPropertyName Name of query property which contains
         *                        resource name.
         * @param cascadeDelete   Delete also dependent resources
         *                        when <code>true</code>.
         */
        Unregister(final Lookup lookup, final String name,
                final String cmdSuffix, final String cmdPropertyName,
                final boolean cascadeDelete) {
            super(lookup, name);
            this.cmdSuffix = cmdSuffix;
            this.cmdPropertyName = cmdPropertyName;
            this.cascadeDelete = cascadeDelete;
        }

        @Override
        public Future<ResultString> unregister() {
            if (instance != null) {
                Future<ResultString> future = ServerAdmin.<ResultString>exec(
                        instance, new CommandDeleteResource(
                        name, cmdSuffix, cmdPropertyName, cascadeDelete), null);
                status = new WeakReference<Future<ResultString>>(future);
                return future;
            } else {
                return null;
            }
        }
    }

    /**
     * Refresh node cookie.
     */
    static class Refresh implements RefreshModulesCookie {

        /** Child nodes to be refreshed. */
        private final Children children;

        /**
         * Creates an instance of cookie for refreshing nodes.
         * <p/>
         * @param children Child nodes to be refreshed.
         */
        Refresh(Children children) {
            this.children = children;
        }

        /**
         * Refresh child nodes.
         */
        @Override
        public void refresh() {
            refresh(null, null);
        }

        /**
         * Refresh child nodes.
         * <p/>
         * @param expected   Expected node display name.
         * @param unexpected Unexpected node display name.
         */
        @Override
        public void refresh(String expected, String unexpected) {
            if (children instanceof Refreshable) {
                ((Refreshable) children).updateKeys();
                boolean foundExpected = expected == null ? true : false;
                boolean foundUnexpected = false;
                for (Node node : children.getNodes()) {
                    if (!foundExpected
                            && node.getDisplayName().equals(expected)) {
                        foundExpected = true;
                    }
                    if (!foundUnexpected
                            && node.getDisplayName().equals(unexpected)) {
                        foundUnexpected = true;
                    }
                }
                if (!foundExpected) {
                    Logger.getLogger("glassfish").log(Level.WARNING, null,
                            new IllegalStateException(
                            "did not find a child node, named " + expected));
                }
                if (foundUnexpected) {
                    Logger.getLogger("glassfish").log(Level.WARNING, null,
                            new IllegalStateException(
                            "found unexpected child node, named "
                            + unexpected));
                }
            }
        }
    }

    static class EditDetails
            extends Cookie implements EditDetailsCookie {

        /** Resources properties query prefix */
        private static final String QUERY_PREFIX = "resources.*";

        /** Properties query common item element. */
        private static final String QUERY_ITEM = ".*";

        /** Properties query common item separator. */
        private static final String QUERY_SEPARATOR = ".";

        /** Resource properties query. */
        private final String query;

        /** Properties customizer. */
        final Class customizer;

        /**
         * Creates an instance of cookie for editing details of module.
         * <p/>
         * @param lookup    Lookup containing {@see CommonServerSupport}.
         * @param name      Name of resource to undeploy.
         * @param cmdSuffix Resource related command suffix.
         */
        EditDetails(final Lookup lookup, final String name,
                final String cmdSuffix, final Class customizer) {
            super(lookup, name);
            final int nameLen = name != null ? name.length() : 0;
            StringBuilder sb = new StringBuilder(QUERY_PREFIX.length()
                    + QUERY_SEPARATOR.length() + nameLen + QUERY_ITEM.length());
            sb.append(QUERY_PREFIX);
            if (nameLen > 0
                    && !GlassfishModule.JDBC_RESOURCE.equals(cmdSuffix)) {
                sb.append(QUERY_SEPARATOR);
                sb.append(name);
                sb.append(QUERY_ITEM);
            }
            query = sb.toString();
            this.customizer = customizer;
        }

        @Override
        public void openCustomizer() {
            final BasePanel retVal = getBasePanel();
            RequestProcessor.getDefault().post(new Runnable() {

                // fetch the data for the BasePanel
                @Override
                public void run() {
                    if (instance != null) {
                        Future<ResultMap> future = ServerAdmin.<ResultMap>exec(
                        instance, new CommandGetProperty(query), null);
                        Map<String, String> value;
                        try {
                            ResultMap result = future.get();
                            value = result.getValue();
                        } catch (InterruptedException ie) {
                            Logger.getLogger("glassfish")
                                    .log(Level.INFO, ie.getMessage(), ie);
                            value = new HashMap<String,String>();
                        } catch (ExecutionException ee) {
                            Logger.getLogger("glassfish")
                                    .log(Level.INFO, ee.getMessage(), ee);
                            value = new HashMap<String,String>();
                        }
                        retVal.initializeData(name, value);
                    }
                }
            });
            DialogDescriptor dd = new DialogDescriptor(retVal,
                    NbBundle.getMessage(this.getClass(), "TITLE_RESOURCE_EDIT", name),
                    false,
                    new ActionListener() {

                        private void appendErrorReport(StringBuilder sb, final String key, final String value) {
                            if (sb.length() > 0) {
                                sb.append(", ");
                            }
                            sb.append(key);
                            sb.append("=");
                            sb.append(value != null ? value : "<null>");
                        }

                        @Override
                        public void actionPerformed(ActionEvent event) {
                            if (event.getSource().equals(NotifyDescriptor.OK_OPTION)) {
                                if (instance != null) {
                                    Map<String, String> properties = retVal.getData();
                                    Set<String> keys = properties.keySet();
                                    StringBuilder sb = new StringBuilder();
                                    for (String key : keys) {
                                        String value = properties.get(key);
                                        Future<ResultString> future = ServerAdmin.<ResultString>exec(
                                                instance, new CommandSetProperty(key, value), null);
                                        try {
                                            ResultString result = future.get();
                                        } catch (InterruptedException ie) {
                                            appendErrorReport(sb, key, value);
                                        } catch (ExecutionException ee) {
                                            appendErrorReport(sb, key, value);
                                        }
                                    }
                                    if (sb.length() > 0) {
                                        Exceptions.printStackTrace(new PartialCompletionException(sb.toString()));
                                    }
                                }
                            }
                        }
                    });
            Dialog d = DialogDisplayer.getDefault().createDialog(dd);
            d.setVisible(true);
        }

        private BasePanel getBasePanel() {
            BasePanel temp;
            try {
                temp = (BasePanel) customizer.getConstructor().newInstance();
            } catch (InstantiationException ex) {
                temp = new BasePanel.Error();
                Exceptions.printStackTrace(ex);
            } catch (IllegalAccessException ex) {
                temp = new BasePanel.Error();
                Exceptions.printStackTrace(ex);
            } catch (IllegalArgumentException ex) {
                temp = new BasePanel.Error();
                Exceptions.printStackTrace(ex);
            } catch (InvocationTargetException ex) {
                temp = new BasePanel.Error();
                Exceptions.printStackTrace(ex);
            } catch (NoSuchMethodException ex) {
                temp = new BasePanel.Error();
                Exceptions.printStackTrace(ex);
            } catch (SecurityException ex) {
                temp = new BasePanel.Error();
                Exceptions.printStackTrace(ex);
            }
            return temp;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Retrieve GlassFish instance from {@see Lookup} object.
     * <p/>
     * @param lookup Lookup containing {@see CommonServerSupport}.
     * @return GlassFish instance retrieved from lookup object.
     */
    private static GlassfishInstance getGlassFishInstance(final Lookup lookup) {
        CommonServerSupport commonModule = lookup.lookup(
                CommonServerSupport.class);
        return commonModule != null ? commonModule.getInstance() : null;
    }

}
