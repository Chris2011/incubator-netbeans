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
package org.netbeans.modules.templatesui;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.java.html.boot.fx.FXBrowsers;
import net.java.html.js.JavaScriptBody;
import net.java.html.json.Models;
import org.netbeans.api.templates.FileBuilder;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.TemplateWizard;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 */
public final class HTMLWizard 
implements WizardDescriptor.InstantiatingIterator<WizardDescriptor> {
    /** publically known factory method */
    public static WizardDescriptor.InstantiatingIterator<?> create(FileObject data) {
        return new HTMLWizard(data);
    }
    
    private final FileObject def;
    private int index;
    private List<String> steps = Collections.emptyList();
    private List<HTMLPanel> panels;
    private Object data;
    private JFXPanel p;
    private /* final */ WebView v;
    private ChangeListener listener;
    private int errorCode = -1;
    private WizardDescriptor wizard;

    private HTMLWizard(FileObject definition) {
        this.def = definition;
    }

    @Override
    public Set<? extends Object> instantiate() throws IOException {
        try {
            FutureTask<?> t = new FutureTask<>(new Callable<Map<String,Object>>() {
                @Override
                public Map<String,Object> call() throws Exception {
                    Object[] namesAndValues = rawProps(data);
                    Map<String,Object> map = new TreeMap<>();
                    for (int i = 0; i < namesAndValues.length; i += 2) {
                        String name = (String) namesAndValues[i];
                        Object value = namesAndValues[i + 1];
                        map.put(name, value);
                    }
                    return map;
                }
            });
            FXBrowsers.runInBrowser(v, t);
            
            TemplateWizard tw = (TemplateWizard) wizard;
            Map<String, ? extends Object> params = Collections.singletonMap(
                "wizard", t.get()
            );
            DataObject obj = tw.getTemplate().createFromTemplate(tw.getTargetFolder(), tw.getTargetName(), params);
            return Collections.singleton(obj);
        } catch (Exception ex) {
            throw (IOException)new InterruptedIOException().initCause(ex);
        }
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        this.wizard = null;
    }

    
    private List<? extends WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        panels = new ArrayList<>();
        int cnt = steps.size();
        if (cnt == 0) {
            cnt = 1;
        }
        for (int i = 0; i < cnt; i++) {
            final HTMLPanel p = new HTMLPanel(i, this);
            panels.add(p);
        }
        return Collections.unmodifiableList(panels);
    }
    
    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanels().get(index);
    }

    @NbBundle.Messages({
        "# {0} - current index",
        "# {1} - number of panels",
        "MSG_HTMLWizardName={0} of {1}"
    })
    @Override
    public String name() {
        return Bundle.MSG_HTMLWizardName(index + 1, getPanels().size());
    }

    @Override
    public boolean hasNext() {
        return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
        onStepsChange(null);
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
        onStepsChange(null);
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        assert this.listener == null;
        this.listener = l;
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        if (this.listener == l) {
            this.listener = null;
        }
    }
    
    private void fireChange() {
        ChangeListener l = this.listener;
        if (l != null) {
            l.stateChanged(new ChangeEvent(this));
        }
    }

    final JComponent component(final int index) {
        if (p == null) {
            p = new JFXPanel();
            p.setPreferredSize(new Dimension(300, 200));
            p.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
            p.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
            p.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (v == null) {
                        Platform.setImplicitExit(false);
                        try {
                            // workaround for 
                            // https://javafx-jira.kenai.com/browse/RT-38536
                            Class.forName("com.sun.javafx.image.impl.ByteBgra");
                        } catch (ClassNotFoundException ignore) {
                        }
                        v = new WebView();
                        BorderPane bp = new BorderPane();
                        Scene scene = new Scene(bp, Color.ALICEBLUE);
                        bp.setCenter(v);
                        p.setScene(scene);

                        String page = (String) def.getAttribute("page");
                        ClassLoader tmpL = Lookup.getDefault().lookup(ClassLoader.class);
                        if (tmpL == null) {
                            tmpL = Thread.currentThread().getContextClassLoader();
                        }
                        if (tmpL == null) {
                            tmpL = HTMLPanel.class.getClassLoader();
                        }
                        
                        final ClassLoader l = tmpL;
                        URL u = l.getResource(page);
                        
                        FXBrowsers.load(v, u, new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String clazz = (String) def.getAttribute("class");
                                    String method = (String) def.getAttribute("method");
                                    Method m = Class.forName(clazz, true, l).getDeclaredMethod(method);
                                    m.setAccessible(true);
                                    Object ret = m.invoke(null);
                                    
                                    if (ret instanceof String) {
                                        data = v.getEngine().executeScript((String) ret);
                                        if (data == null || "undefined".equals(data)) {
                                            throw new IllegalArgumentException("Executing " + ret + " returned null, that is wrong, should get JSON object with ko bindings");
                                        }
                                    } else {
                                        if (ret != null && Models.isModel(ret.getClass())) {
                                            data = Models.toRaw(ret);
                                        } else {
                                            throw new IllegalStateException("Returned value should be string or class generated by @Model annotation: " + ret);
                                        }
                                    }
                                    
                                    boolean stepsOK = listenOnProp(data, HTMLWizard.this, "steps");
                                    boolean errorCodeOK = listenOnProp(data, HTMLWizard.this, "errorCode");
                                    
                                } catch (NoSuchMethodException ex) {
                                    Exceptions.printStackTrace(ex);
                                } catch (ClassNotFoundException ex) {
                                    Exceptions.printStackTrace(ex);
                                } catch (IllegalAccessException ex) {
                                    Exceptions.printStackTrace(ex);
                                } catch (IllegalArgumentException ex) {
                                    Exceptions.printStackTrace(ex);
                                } catch (InvocationTargetException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            }
                        });
                    }
                }
            });
        }
        return p;
    }
    
    final void onChange(String prop, Object data) {
        if ("steps".equals(prop)) {
            onStepsChange((Object[])data);
        }
        if ("errorCode".equals(prop)) {
            errorCode = ((Number)data).intValue();
            fireChange();
        }
    }

    boolean isValid() {
        return errorCode == 0;
    }
    
    final Object evaluateProp(final String prop) throws InterruptedException, ExecutionException {
        FutureTask<?> t = new FutureTask<Object>(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return getPropertyValue(data, prop);
            }
        });
        FXBrowsers.runInBrowser(v, t);
        return t.get();
    }
    
    final void setProp(final String prop, final Object value) throws InterruptedException, ExecutionException {
        FutureTask<?> t = new FutureTask<Object>(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return changeProperty(data, prop, value);
            }
        });
        FXBrowsers.runInBrowser(v, t);
        t.get();
    }
    
    private void onStepsChange(Object[] obj) {
        if (obj != null) {
            List<String> arr = new ArrayList<>();
            for (Object s : obj) {
                arr.add(s.toString());
            }
            p.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, arr.toArray(new String[arr.size()]));
            if (!arr.equals(steps)) {
                steps = arr;
                fireChange();
            }
        }
        p.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, index);
        if (steps != null && steps.size() > index) {
            final String current = steps.get(index);
            FXBrowsers.runInBrowser(v, new Runnable() {
                @Override
                public void run() {
                    changeProperty(data, "current", current); // NOI18N
                }
            });
        }
    }
    
    @JavaScriptBody(args = {"data", "onChange", "p" }, javacall = true, body = ""
        + "if (typeof data[p] !== 'function') {\n"
        + "  onChange.@org.netbeans.modules.templatesui.HTMLWizard::onChange(Ljava/lang/String;Ljava/lang/Object;)(p, null);\n"
        + "  return false;\n"
        + "}\n"
        + "data[p].subscribe(function(value) {\n"
        + "  onChange.@org.netbeans.modules.templatesui.HTMLWizard::onChange(Ljava/lang/String;Ljava/lang/Object;)(p, value);\n"
        + "});\n"
        + "onChange.@org.netbeans.modules.templatesui.HTMLWizard::onChange(Ljava/lang/String;Ljava/lang/Object;)(p, data[p]());\n"
        + "return true;\n"
    )
    static native boolean listenOnProp(Object raw, HTMLWizard onChange, String propName);
    
    @JavaScriptBody(args = { "raw", "propName", "value" }, body = ""
        + "var fn = raw[propName];\n"
        + "if (typeof fn !== 'function') return false;\n"
        + "fn(value);\n"
        + "return true;\n"
    )
    private static native boolean changeProperty(Object raw, String propName, Object value);
    
    @JavaScriptBody(args = { "raw", "propName" }, body = ""
        + "var fn = raw[propName];\n"
        + "if (typeof fn !== 'function') return null;\n"
        + "return fn();\n"
    )
    static native Object getPropertyValue(Object raw, String propName);
    
    @JavaScriptBody(args = { "raw" }, body = ""
        + "var ret = [];\n"
        + "for (var n in raw) {\n"
        + "  var fn = raw[n];\n"
        + "  ret.push(n);\n"
        + "  if (typeof fn === 'function') ret.push(fn()); else ret.push(fn);\n"
        + "}\n"
        + "return ret;\n"
    )
    static native Object[] rawProps(Object raw);
}
