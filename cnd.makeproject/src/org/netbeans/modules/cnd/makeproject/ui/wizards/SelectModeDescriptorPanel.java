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
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */
package org.netbeans.modules.cnd.makeproject.ui.wizards;

import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 *
 * @author Alexander Simon
 */
public class SelectModeDescriptorPanel implements WizardDescriptor.FinishablePanel, ChangeListener {
    
    private WizardDescriptor wizardDescriptor;
    private SelectModePanel component;
    private String name;
    private WizardStorage wizardStorage= new WizardStorage();
    private boolean isValid = false;;

    public SelectModeDescriptorPanel(){
        name = NbBundle.getMessage(SelectModePanel.class, "SelectModeName"); // NOI18N
    }
    
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new SelectModePanel(this);
      	    component.setName(name);
        }
        return component;
    }
    
    public HelpCtx getHelp() {
        return new HelpCtx("NewMakeWizardP0"); // NOI18N
    }
    
    public boolean isValid() {
        return isValid;
    }

    private void validate(){
        isValid = component.valid();
        fireChangeEvent();
    }

    private void setMode(boolean isSimple) {
        if (isSimple) {
            wizardDescriptor.putProperty("simpleMode", Boolean.TRUE); // NOI18N
        } else {
            wizardDescriptor.putProperty("simpleMode", Boolean.FALSE); // NOI18N
        }
        validate();
    }

    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    protected final void fireChangeEvent() {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }
    
    WizardDescriptor getWizardDescriptor(){
        return wizardDescriptor;
    }

    public void stateChanged(ChangeEvent e) {
        String[] res;
        Object o = component.getClientProperty(WizardDescriptor.PROP_CONTENT_DATA);
        String[] names = (String[]) o;
        if (Boolean.TRUE.equals(wizardDescriptor.getProperty("simpleMode"))){
            res = new String[]{names[0]};
        } else {
            res = new String[]{names[0], "..."}; // NOI18N
        }
        component.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, res);
      	fireChangeEvent();
    }

    public boolean isFinishPanel() {
        return  Boolean.TRUE.equals(wizardDescriptor.getProperty("simpleMode")); // NOI18N
    }

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(Object settings) {
        wizardDescriptor = (WizardDescriptor)settings;
        if (wizardDescriptor.getProperty("simpleMode") == null) {
            wizardDescriptor.putProperty("simpleMode", Boolean.TRUE); // NOI18N
        }

    }
    
    public void storeSettings(Object settings) {
        component.store((WizardDescriptor)settings);
    }

    public WizardStorage getWizardStorage(){
        return wizardStorage;
    }

    public class WizardStorage {
        private String path = ""; // NOI18N
        private static final String PREDEFINED_FLAGS_GNU = "\"-g3 -gdwarf-2\""; // NOI18N
        private static final String PREDEFINED_FLAGS_SUN = "-g"; // NOI18N
        private String flags = ""; // NOI18N
        private boolean setMain = true;
        private boolean buildProject = true;
        public WizardStorage(){
        }

        /**
         * @return the path
         */
        public void setMode(boolean isSimple) {
            SelectModeDescriptorPanel.this.setMode(isSimple);
        }

        /**
         * @return the path
         */
        public String getPath() {
            return path;
        }

        /**
         * @param path the path to set
         */
        public void setPath(String path) {
            this.path = path.trim();
            validate();
        }

        public String getConfigure(){
            if (path.length() == 0) {
                return null;
            }
            return ConfigureUtils.findConfigureScript(path);
        }

        public String getMake(){
            if (path.length() == 0) {
                return null;
            }
            return ConfigureUtils.findMakefile(path);
        }

        /**
         * @return the flags
         */
        public String getFlags() {
            return flags;
        }

        /**
         * @return the flags
         */
        public String getRealFlags() {
            String configure = getConfigure();
            String cCompiler = ConfigureUtils.getDefaultC();
            String cppCompiler = ConfigureUtils.getDefaultCpp();
            StringBuilder buf = new StringBuilder(flags);
            String cCompilerFlags;
            if ("cc".equals(cCompiler)) { // NOI18N
                cCompilerFlags = PREDEFINED_FLAGS_SUN;
            } else {
                cCompilerFlags = PREDEFINED_FLAGS_GNU;
            }
            String cppCompilerFlags;
            if ("CC".equals(cppCompiler)) { // NOI18N
                cppCompilerFlags = PREDEFINED_FLAGS_SUN;
            } else {
                cppCompilerFlags = PREDEFINED_FLAGS_GNU;
            }
            if (configure.endsWith("CMakeLists.txt")){ // NOI18N
                if (flags.indexOf("-G ") < 0 ){ // NOI18N
                    if (buf.length() > 0) {
                        buf.append(' '); // NOI18N
                    }
                    buf.append("-G \"Unix Makefiles\""); // NOI18N
                }
                appendIfNeed("-DCMAKE_BUILD_TYPE=", flags, buf, "Debug"); // NOI18N
                if ("cc".equals(cCompiler)) { // NOI18N
                    appendIfNeed("-DCMAKE_C_COMPILER=", flags, buf, "cc"); // NOI18N
                }
                if ("CC".equals(cppCompiler)) { // NOI18N
                    appendIfNeed("-DCMAKE_CXX_COMPILER=", flags, buf, "CC"); // NOI18N
                }
                appendIfNeed("-DCMAKE_C_FLAGS=", flags, buf, cCompilerFlags); // NOI18N
                appendIfNeed("-DCMAKE_CXX_FLAGS=", flags, buf, cppCompilerFlags); // NOI18N
            } else if (configure.endsWith(".pro")){ // NOI18N
                if ("CC".equals(cppCompiler) && Utilities.getOperatingSystem() == Utilities.OS_SOLARIS) { // NOI18N
                    if (flags.indexOf("-spec ") < 0 ){ // NOI18N
                        if (buf.length() > 0) {
                            buf.append(' '); // NOI18N
                        }
                        buf.append("-spec solaris-cc"); // NOI18N
                    }
                    appendIfNeed("QMAKE_CC=", flags, buf, "cc"); // NOI18N
                } else {
                    appendIfNeed("QMAKE_CFLAGS=", flags, buf, cCompilerFlags); // NOI18N
                    appendIfNeed("QMAKE_CXXFLAGS=", flags, buf, cppCompilerFlags); // NOI18N
                }
            } else {
                if ("cc".equals(cCompiler)) { // NOI18N
                    appendIfNeed("CC=", flags, buf, "cc"); // NOI18N
                }
                if ("CC".equals(cppCompiler)) { // NOI18N
                    appendIfNeed("CXX=", flags, buf, "CC"); // NOI18N
                }
                appendIfNeed("CFLAGS=", flags, buf, cCompilerFlags); // NOI18N
                appendIfNeed("CXXFLAGS=", flags, buf, cppCompilerFlags); // NOI18N
            }
            return buf.toString();
        }

        private void appendIfNeed(String key, String flags, StringBuilder buf, String flag){
            if (flags.indexOf(key) < 0 ){
                if (buf.length() > 0) {
                    buf.append(' '); // NOI18N
                }
                buf.append(key + flag);
            }
        }

        /**
         * @param flags the flags to set
         */
        public void setFlags(String flags) {
            this.flags = flags;
            validate();
        }

        /**
         * @return the setMain
         */
        public boolean isSetMain() {
            return setMain;
        }

        /**
         * @param setMain the setMain to set
         */
        public void setSetMain(boolean setMain) {
            this.setMain = setMain;
            validate();
        }

        /**
         * @return the buildProject
         */
        public boolean isBuildProject() {
            return buildProject;
        }

        /**
         * @param buildProject the buildProject to set
         */
        public void setBuildProject(boolean buildProject) {
            this.buildProject = buildProject;
            validate();
        }
    }

    public static class WizardDescriptorAdapter extends WizardDescriptor{
        private WizardStorage storage;
        public WizardDescriptorAdapter(WizardStorage storage) {
            this.storage = storage;
        }
        @Override
        public synchronized Object getProperty(String name) {
            if ("path".equals(name)) { // NOI18N
                return storage.getPath();
            } else if ("realFlags".equals(name)) { // NOI18N
                return storage.getRealFlags();
            } else if ("buildProject".equals(name)) { // NOI18N
                if (storage.isBuildProject()) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            } else if ("setMain".equals(name)) { // NOI18N
                if (storage.isSetMain()) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            } else if ("simpleMode".equals(name)) { // NOI18N
                return Boolean.TRUE;
            } else if ("makefileName".equals(name)) { // NOI18N
                return storage.getMake();
            } else if ("configureName".equals(name)) { // NOI18N
                return storage.getConfigure();
            }
            return super.getProperty(name);
        }
    }
}

