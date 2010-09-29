/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
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

package org.netbeans.modules.javawebstart.ui.customizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import javax.swing.JToggleButton.ToggleButtonModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.api.java.platform.JavaPlatformManager;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.java.project.classpath.ProjectClassPathModifier;
import org.netbeans.api.java.queries.SourceForBinaryQuery;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.ClassIndex;
import org.netbeans.api.java.source.ClassIndex.SearchKind;
import org.netbeans.api.java.source.ClassIndex.SearchScope;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.modules.java.j2seproject.api.J2SEPropertyEvaluator;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.netbeans.spi.project.support.ant.PropertyEvaluator;
import org.netbeans.spi.project.support.ant.PropertyUtils;
import org.netbeans.spi.project.support.ant.ui.StoreGroup;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.Mutex;
import org.openide.util.MutexException;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;

/**
 *
 * @author Milan Kubec
 */
public class JWSProjectProperties /*implements TableModelListener*/ {
    
    public static final String JNLP_ENABLED      = "jnlp.enabled";
    public static final String JNLP_ICON         = "jnlp.icon";
    public static final String JNLP_OFFLINE      = "jnlp.offline-allowed";
    public static final String JNLP_CBASE_TYPE   = "jnlp.codebase.type";
    public static final String JNLP_CBASE_USER   = "jnlp.codebase.user";
    public static final String JNLP_CBASE_URL    = "jnlp.codebase.url";
    public static final String JNLP_DESCRIPTOR   = "jnlp.descriptor";
    public static final String JNLP_APPLET       = "jnlp.applet.class";
    
    public static final String JNLP_SPEC         = "jnlp.spec";
    public static final String JNLP_INIT_HEAP    = "jnlp.initial-heap-size";
    public static final String JNLP_MAX_HEAP     = "jnlp.max-heap-size";
    
    public static final String JNLP_SIGNED = "jnlp.signed";
    public static final String JNLP_MIXED_CODE = "jnlp.mixed.code";

    public static final String JNLP_SIGNING = "jnlp.signing";
    public static final String JNLP_SIGNING_KEYSTORE = "jnlp.signing.keystore";
    public static final String JNLP_SIGNING_KEY = "jnlp.signing.alias";
    public static final String JNLP_SIGNING_KEYSTORE_PASSWORD = "jnlp.signing.storepass";
    public static final String JNLP_SIGNING_KEY_PASSWORD = "jnlp.signing.keypass";
    public static final String RUN_CP = "run.classpath";    //NOI18N
    public static final String BUILD_CLASSES = "build.classes.dir"; //NOI18N
    public static final String JNLP_LAZY_JARS = "jnlp.lazy.jars";   //NOI18N
    private static final String JNLP_LAZY_JAR = "jnlp.lazy.jar."; //NOI18N
    private static final String JNLP_LAZY_FORMAT = JNLP_LAZY_JAR +"%s"; //NOI18N
    
    static final String SIGNING_GENERATED = "generated";
    static final String SIGNING_KEY = "key";

    public static final String CB_TYPE_LOCAL = "local";
    public static final String CB_TYPE_WEB = "web";
    public static final String CB_TYPE_USER = "user";
    public static final String CB_NO_CODEBASE = "no.codebase";
    
    public static final String DEFAULT_APPLET_WIDTH = "300";
    public static final String DEFAULT_APPLET_HEIGHT = "300";

    private static final String JAR_INDEX = "jar.index";    //NOI18N
    private static final String JAR_ARCHIVE_DISABLED ="jar.archive.disabled";   //NOI18N
    public static final String BUILD_SCRIPT ="buildfile";      //NOI18N


    public enum DescType {
        application, applet, component;
    }
    
    public static final String CB_URL_WEB = "$$codebase";
    
    public static final String JNLP_EXT_RES_PREFIX = "jnlp.ext.resource.";
    public static final String JNLP_APPLET_PARAMS_PREFIX = "jnlp.applet.param.";
    public static final String JNLP_APPLET_WIDTH = "jnlp.applet.width";
    public static final String JNLP_APPLET_HEIGHT = "jnlp.applet.height";
    
    // property to be set when enabling javawebstart to disable Compile on Save feature
    // javawebstart project needs to be built completly before it could be run
    public static final String COS_UNSUPPORTED_PROPNAME = "compile.on.save.unsupported.javawebstart";

    // special value to persist Ant script handling
    public static final String CB_URL_WEB_PROP_VALUE = "$$$$codebase";
    
    private StoreGroup jnlpPropGroup = new StoreGroup();
    
    private J2SEPropertyEvaluator j2sePropEval;
    private PropertyEvaluator evaluator;
    private Project project;
    
    private List<Map<String,String>> extResProperties;
    private List<Map<String,String>> appletParamsProperties;

    public static final String extResSuffixes[] = new String[] { "href", "name", "version" };
    public static final String appletParamsSuffixes[] = new String[] { "name", "value" };

    public static final String CONFIG_LABEL_PROPNAME = "$label";
    public static final String CONFIG_TARGET_RUN_PROPNAME = "$target.run";
    public static final String CONFIG_TARGET_DEBUG_PROPNAME = "$target.debug";

    public static final String CONFIG_TARGET_RUN = "jws-run";
    public static final String CONFIG_TARGET_DEBUG = "jws-debug";

    private static final String LIB_JAVAWS = "javaws.jar";  //NOI18N
    private static final String LIB_PLUGIN = "plugin.jar";  //NOI18N

    private DescType selectedDescType = null;

    boolean jnlpImplOldOrModified = false;

    // signing
    String signing;
    String signingKeyStore;
    String signingKeyAlias;
    char [] signingKeyStorePassword;
    char [] signingKeyPassword;
    
    // resources
    List<? extends File> runtimeCP;
    List<? extends File> lazyJars;
    boolean lazyJarsChanged;

    // Models 
    JToggleButton.ToggleButtonModel enabledModel;
    JToggleButton.ToggleButtonModel allowOfflineModel;
    
    ComboBoxModel codebaseModel;
    ComboBoxModel appletClassModel;
    ComboBoxModel mixedCodeModel;
    
    ButtonModel applicationDescButtonModel;
    ButtonModel appletDescButtonModel;
    ButtonModel compDescButtonModel;
    private ButtonGroup bg;
    
    PropertiesTableModel extResTableModel;
    PropertiesTableModel appletParamsTableModel;
    
    // and Documents
    Document iconDocument;
    Document codebaseURLDocument;
    Document appletWidthDocument;
    Document appletHeightDocument;
    
    /** Creates a new instance of JWSProjectProperties */
    public JWSProjectProperties(Lookup context) {
        
        project = context.lookup(Project.class);
        
        if (project != null) {
            
            j2sePropEval = project.getLookup().lookup(J2SEPropertyEvaluator.class);
            
            evaluator = j2sePropEval.evaluator();
        
            enabledModel = jnlpPropGroup.createToggleButtonModel(evaluator, JNLP_ENABLED);
            allowOfflineModel = jnlpPropGroup.createToggleButtonModel(evaluator, JNLP_OFFLINE);
            iconDocument = jnlpPropGroup.createStringDocument(evaluator, JNLP_ICON);
            appletWidthDocument = jnlpPropGroup.createStringDocument(evaluator, JNLP_APPLET_WIDTH);
            appletHeightDocument = jnlpPropGroup.createStringDocument(evaluator, JNLP_APPLET_HEIGHT);

            codebaseModel = new CodebaseComboBoxModel();
            codebaseURLDocument = createCBTextFieldDocument();

            appletClassModel = new AppletClassComboBoxModel(project);
            mixedCodeModel = createMixedCodeModel(j2sePropEval.evaluator());
            initRadioButtons();

            initSigning(evaluator);

            extResProperties = readProperties(evaluator, JNLP_EXT_RES_PREFIX, extResSuffixes);
            appletParamsProperties = readProperties(evaluator, JNLP_APPLET_PARAMS_PREFIX, appletParamsSuffixes);
            
            initResources(evaluator, project);            
            // check if the jnlp-impl.xml script is of previous version -> should be upgraded
            FileObject jnlpImlpFO = project.getProjectDirectory().getFileObject("nbproject/jnlp-impl.xml");
            if (jnlpImlpFO != null) {
                try {
                    String crc = JWSCompositeCategoryProvider.computeCrc32(jnlpImlpFO.getInputStream());
                    jnlpImplOldOrModified = !JWSCompositeCategoryProvider.isJnlpImplCurrentVer(crc);
                } catch (IOException ex) {
                    // nothing to do really
                }
            }

        } 
        
    }
    
    boolean isJWSEnabled() {
        return enabledModel.isSelected();
    }
    
    public DescType getDescTypeProp() {
        DescType toReturn;
        if (selectedDescType != null) {
            return selectedDescType;
        }
        String desc = evaluator.getProperty(JNLP_DESCRIPTOR);
        if (desc != null) {
            toReturn = DescType.valueOf(desc);
        } else {
            toReturn = DescType.application;
        }
        return toReturn;
    }
    
    public void updateDescType() {
        selectedDescType = getSelectedDescType();
    }
    
    public List<Map<String,String>> getExtResProperties() {
        return extResProperties;
    }
    
    public void setExtResProperties(List<Map<String,String>> props) {
        extResProperties = props;
    }
    
    public List<Map<String,String>> getAppletParamsProperties() {
        return appletParamsProperties;
    }
    
    public void setAppletParamsProperties(List<Map<String,String>> props) {
        appletParamsProperties = props;
    }
    
    private void initRadioButtons() {
        
        applicationDescButtonModel = new ToggleButtonModel();
        appletDescButtonModel = new ToggleButtonModel();
        compDescButtonModel = new ToggleButtonModel();
        bg = new ButtonGroup();
        applicationDescButtonModel.setGroup(bg);
        appletDescButtonModel.setGroup(bg);
        compDescButtonModel.setGroup(bg);
        
        String desc = evaluator.getProperty(JNLP_DESCRIPTOR);
        if (desc != null) {
            if (desc.equals(DescType.application.toString())) {
                applicationDescButtonModel.setSelected(true);
            } else if (desc.equals(DescType.applet.toString())) {
                appletDescButtonModel.setSelected(true);
            } else if (desc.equals(DescType.component.toString())) {
                compDescButtonModel.setSelected(true);
            }
        } else {
            applicationDescButtonModel.setSelected(true);
        }

    }
    
    private void storeRest(EditableProperties editableProps, EditableProperties privProps) {
        // store codebase type
        String selItem = ((CodebaseComboBoxModel) codebaseModel).getSelectedCodebaseItem();
        String propName = null;
        String propValue = null;
        if (CB_TYPE_USER.equals(selItem)) {
            propName = JNLP_CBASE_USER;
            try {
                propValue = codebaseURLDocument.getText(0, codebaseURLDocument.getLength());
            } catch (BadLocationException ex) {
                // do not store anything
                // XXX log the exc
                return;
            }
        } else if (CB_TYPE_LOCAL.equals(selItem)) {
            // #161919: local codebase will be computed
            //propName = JNLP_CBASE_URL;
            //propValue = getProjectDistDir();
        } else if (CB_TYPE_WEB.equals(selItem))  {
            propName = JNLP_CBASE_URL;
            propValue = CB_URL_WEB_PROP_VALUE;
        }
        editableProps.setProperty(JNLP_CBASE_TYPE, selItem);
        if (propName != null && propValue != null) {
            editableProps.setProperty(propName, propValue);
        }
        // store applet class name and default applet size
        String appletClassName = (String) appletClassModel.getSelectedItem();
        if (appletClassName != null && !appletClassName.equals("")) {
            editableProps.setProperty(JNLP_APPLET, appletClassName);
            String appletWidth = null;
            try {
                appletWidth = appletWidthDocument.getText(0, appletWidthDocument.getLength());
            } catch (BadLocationException ex) {
                // appletWidth will be null
            }
            if (appletWidth == null || "".equals(appletWidth)) {
                editableProps.setProperty(JNLP_APPLET_WIDTH, DEFAULT_APPLET_WIDTH);
            }
            String appletHeight = null;
            try {
                appletHeight = appletHeightDocument.getText(0, appletHeightDocument.getLength());
            } catch (BadLocationException ex) {
                // appletHeight will be null
            }
            if (appletHeight == null || "".equals(appletHeight)) {
                editableProps.setProperty(JNLP_APPLET_HEIGHT, DEFAULT_APPLET_HEIGHT);
            }
        }
        // store descriptor type
        DescType descType = getSelectedDescType();
        if (descType != null) {
            editableProps.setProperty(JNLP_DESCRIPTOR, descType.toString());
        }

        //Store Mixed Code
        final MixedCodeOptions option = (MixedCodeOptions) mixedCodeModel.getSelectedItem();
        editableProps.setProperty(JNLP_MIXED_CODE, option.getPropertyValue());
        //Store jar indexing
        if (editableProps.getProperty(JAR_INDEX) == null) {
            editableProps.setProperty(JAR_INDEX, String.format("${%s}", JNLP_ENABLED));   //NOI18N
        }
        if (editableProps.getProperty(JAR_ARCHIVE_DISABLED) == null) {
            editableProps.setProperty(JAR_ARCHIVE_DISABLED, String.format("${%s}", JNLP_ENABLED));  //NOI18N
        }
        // store signing info
        editableProps.setProperty(JNLP_SIGNING, signing);
        editableProps.setProperty(JNLP_SIGNED, "".equals(signing) ? "false" : "true");
        setOrRemove(editableProps, JNLP_SIGNING_KEY, signingKeyAlias);
        setOrRemove(editableProps, JNLP_SIGNING_KEYSTORE, signingKeyStore);
        setOrRemove(privProps, JNLP_SIGNING_KEYSTORE_PASSWORD, signingKeyStorePassword);
        setOrRemove(privProps, JNLP_SIGNING_KEY_PASSWORD, signingKeyPassword);
        
        // store resources
        storeResources(editableProps);

        // store properties
        storeProperties(editableProps, extResProperties, JNLP_EXT_RES_PREFIX);
        storeProperties(editableProps, appletParamsProperties, JNLP_APPLET_PARAMS_PREFIX);
    }

    private void setOrRemove(EditableProperties props, String name, char [] value) {
        setOrRemove(props, name, value != null ? new String(value) : null);
    }

    private void setOrRemove(EditableProperties props, String name, String value) {
        if (value != null) {
            props.setProperty(name, value);
        } else {
            props.remove(name);
        }
    }
    
    public void store() throws IOException {
        
        final EditableProperties ep = new EditableProperties(true);
        final FileObject projPropsFO = project.getProjectDirectory().getFileObject(AntProjectHelper.PROJECT_PROPERTIES_PATH);
        final EditableProperties pep = new EditableProperties(true);
        final FileObject privPropsFO = project.getProjectDirectory().getFileObject(AntProjectHelper.PRIVATE_PROPERTIES_PATH);
        
        try {
            final InputStream is = projPropsFO.getInputStream();
            final InputStream pis = privPropsFO.getInputStream();
            ProjectManager.mutex().writeAccess(new Mutex.ExceptionAction<Void>() {
                @Override
                public Void run() throws Exception {
                    try {
                        ep.load(is);
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                    }
                    try {
                        pep.load(pis);
                    } finally {
                        if (pis != null) {
                            pis.close();
                        }
                    }
                    jnlpPropGroup.store(ep);
                    storeRest(ep, pep);
                    OutputStream os = null;
                    FileLock lock = null;
                    try {
                        lock = projPropsFO.lock();
                        os = projPropsFO.getOutputStream(lock);
                        ep.store(os);
                    } finally {
                        if (lock != null) {
                            lock.releaseLock();
                        }
                        if (os != null) {
                            os.close();
                        }
                    }
                    try {
                        lock = privPropsFO.lock();
                        os = privPropsFO.getOutputStream(lock);
                        pep.store(os);
                    } finally {
                        if (lock != null) {
                            lock.releaseLock();
                        }
                        if (os != null) {
                            os.close();
                        }
                    }
                    updateWebStartJars(project, evaluator);
                    return null;
                }
            });
        } catch (MutexException mux) {
            throw (IOException) mux.getException();
        } 
        
    }
    
    private DescType getSelectedDescType() {
        DescType toReturn = null;
        if (applicationDescButtonModel.isSelected()) {
            toReturn = DescType.application;
        } else if (appletDescButtonModel.isSelected()) {
            toReturn = DescType.applet;
        } else if (compDescButtonModel.isSelected()) {
            toReturn = DescType.component;
        }
        return toReturn;
    }
    
    private Document createCBTextFieldDocument() {
        Document doc = new PlainDocument();
        String valueType = evaluator.getProperty(JNLP_CBASE_TYPE);
        String docString = "";
        if (CB_TYPE_LOCAL.equals(valueType)) {
            docString = getProjectDistDir();
        } else if (CB_TYPE_WEB.equals(valueType)) {
            docString = CB_URL_WEB;
        } else if (CB_TYPE_USER.equals(valueType)) {
            docString = getCodebaseLocation();
        }
        try {
            doc.insertString(0, docString, null);
        } catch (BadLocationException ex) {
            // do nothing, just return PlainDocument
            // XXX log the exc
        }
        return doc;
    }
    
    public String getCodebaseLocation() {
        return evaluator.getProperty(JNLP_CBASE_USER);
    }
        
    public String getProjectDistDir() {
        String dD = evaluator.getProperty("dist.dir"); // NOI18N
        File distDir = new File(FileUtil.toFile(project.getProjectDirectory()), dD != null ? dD : ""); // NOI18N
        return distDir.toURI().toString();
    }
    
    // only should return JNLP properties
    public String getProperty(String propName) {
        return evaluator.getProperty(propName);
    }

    public static void updateWebStartJars(
            final Project project,
            final PropertyEvaluator eval) throws IOException {
        FileObject srcRoot = null;
        for (SourceGroup sg : ProjectUtils.getSources(project).getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA)) {
            if (!isTest(sg.getRootFolder(),project)) {
                srcRoot = sg.getRootFolder();
                break;
            }
        }
        if (srcRoot != null) {
            final Collection<? extends URL> toAdd  = isWebStart(eval) ? findWebStartJars(eval, isApplet(eval)) : new LinkedList<URL>();
            final ClassPath bootCp = ClassPath.getClassPath(srcRoot, "classpath/endorsed"); //NOI18N
            final Collection<? extends URL> included = findWebStartJars(bootCp);
            final Collection<? extends URL> toRemove = new ArrayList<URL>(included);
            toRemove.removeAll(toAdd);
            toAdd.removeAll(included);
            if (!toRemove.isEmpty()) {
                ProjectClassPathModifier.removeRoots(toRemove.toArray(new URL[toRemove.size()]), srcRoot, "classpath/endorsed");    //NOI18N Todo: fix ClassPath constants
            }
            if (!toAdd.isEmpty()) {
                ProjectClassPathModifier.addRoots(toAdd.toArray(new URL[toAdd.size()]), srcRoot, "classpath/endorsed");    //NOI18N Todo: fix ClassPath constants
            }
        }
    }
    
    // ----------
    
    public class CodebaseComboBoxModel extends DefaultComboBoxModel {
        
        final String localLabel = NbBundle.getBundle(JWSProjectProperties.class).getString("LBL_CB_Combo_Local");
        final String webLabel = NbBundle.getBundle(JWSProjectProperties.class).getString("LBL_CB_Combo_Web");
        final String userLabel = NbBundle.getBundle(JWSProjectProperties.class).getString("LBL_CB_Combo_User");
        final String noCodeBaseLabel = NbBundle.getMessage(JWSProjectProperties.class, "LBL_CB_No_Codebase");
        final String visItems[] = new String[] { noCodeBaseLabel, localLabel, webLabel, userLabel};
        final String cbItems[] = new String[] { CB_NO_CODEBASE, CB_TYPE_LOCAL, CB_TYPE_WEB, CB_TYPE_USER};
        
        public CodebaseComboBoxModel() {
            super();
            for (String visItem : visItems) {
                addElement(visItem);
            }
            String propValue = evaluator.getProperty(JNLP_CBASE_TYPE);
            for (int i=0; i<cbItems.length; i++) {
                if (cbItems[i].equals(propValue)) {
                    setSelectedItem(visItems[i]);
                    break;
                }
            }
        }
        
        public String getSelectedCodebaseItem() {
            return cbItems[getIndexOf(getSelectedItem())];
        }
        
    }

    public class AppletClassComboBoxModel extends DefaultComboBoxModel {
        
        Set<SearchKind> kinds = new HashSet<SearchKind>(Arrays.asList(SearchKind.IMPLEMENTORS));
        Set<SearchScope> scopes = new HashSet<SearchScope>(Arrays.asList(SearchScope.SOURCE));
        
        public AppletClassComboBoxModel(final Project proj) {
            
            Sources sources = ProjectUtils.getSources(proj);
            SourceGroup[] srcGroups = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
            final Map<FileObject,List<ClassPath>> classpathMap = new HashMap<FileObject,List<ClassPath>>();
            
            for (SourceGroup srcGroup : srcGroups) {
                FileObject srcRoot = srcGroup.getRootFolder();
                ClassPath bootCP = ClassPath.getClassPath(srcRoot, ClassPath.BOOT);
                ClassPath executeCP = ClassPath.getClassPath(srcRoot, ClassPath.EXECUTE);
                ClassPath sourceCP = ClassPath.getClassPath(srcRoot, ClassPath.SOURCE);
                List<ClassPath> cpList = new ArrayList<ClassPath>();
                if (bootCP != null) {
                    cpList.add(bootCP);
                }
                if (executeCP != null) {
                    cpList.add(executeCP);
                }
                if (sourceCP != null) {
                    cpList.add(sourceCP);
                }
                if (cpList.size() == 3) {
                    classpathMap.put(srcRoot, cpList);
                }
            }
            
            final Set<String> appletNames = new HashSet<String>();
            
            RequestProcessor.getDefault().post(new Runnable() {
                public void run() {
                    for (FileObject fo : classpathMap.keySet()) {
                        List<ClassPath> paths = classpathMap.get(fo);
                        ClasspathInfo cpInfo = ClasspathInfo.create(paths.get(0), paths.get(1), paths.get(2));
                        final ClassIndex classIndex = cpInfo.getClassIndex();
                        final JavaSource js = JavaSource.create(cpInfo);
                        try {
                            js.runUserActionTask(new CancellableTask<CompilationController>() {
                                public void run(CompilationController controller) throws Exception {
                                    Elements elems = controller.getElements();
                                    TypeElement appletElement = elems.getTypeElement("java.applet.Applet");
                                    ElementHandle<TypeElement> appletHandle = ElementHandle.create(appletElement);
                                    TypeElement jappletElement = elems.getTypeElement("javax.swing.JApplet");
                                    ElementHandle<TypeElement> jappletHandle = ElementHandle.create(jappletElement);
                                    Set<ElementHandle<TypeElement>> appletHandles = classIndex.getElements(appletHandle, kinds, scopes);
                                    for (ElementHandle<TypeElement> elemHandle : appletHandles) {
                                        appletNames.add(elemHandle.getQualifiedName());
                                    }
                                    Set<ElementHandle<TypeElement>> jappletElemHandles = classIndex.getElements(jappletHandle, kinds, scopes);
                                    for (ElementHandle<TypeElement> elemHandle : jappletElemHandles) {
                                        appletNames.add(elemHandle.getQualifiedName());
                                    }
                                }
                                public void cancel() {
                                    
                                }
                            }, true);
                        } catch (Exception e) {
                            
                        }

                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            addElements(appletNames);
                            String appletClassName = evaluator.getProperty(JNLP_APPLET);
                            if (appletClassName != null && appletNames.contains(appletClassName)) {
                                setSelectedItem(appletClassName);
                            }
                        }
                    });
                }
            });
        }
        
        private void addElements(Set<String> elems) {
            for (String elem : elems) {
                addElement(elem);
            }
        }
        
    }
    
    public static class PropertiesTableModel extends AbstractTableModel {
        
        private List<Map<String,String>> properties;
        private String propSuffixes[];
        private String columnNames[];
        
        public PropertiesTableModel(List<Map<String,String>> props, String sfxs[], String clmns[]) {
            if (sfxs.length != clmns.length) {
                throw new IllegalArgumentException();
            }
            properties = props;
            propSuffixes = sfxs;
            columnNames = clmns;
        }
        
        public int getRowCount() {
            return properties.size();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
        
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            properties.get(rowIndex).put(propSuffixes[columnIndex], (String) aValue);
        }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
            return properties.get(rowIndex).get(propSuffixes[columnIndex]);
        }
        
        public void addRow() {
            Map<String,String> emptyMap = new HashMap<String,String>();
            for (String  suffix : propSuffixes) {
                emptyMap.put(suffix, "");
            }
            properties.add(emptyMap);
        }
        
        public void removeRow(int index) {
            properties.remove(index);
        }

    }
    
    // ----------
    
    private static List<Map<String,String>> readProperties(PropertyEvaluator evaluator, String propPrefix, String[] propSuffixes) {
        
        ArrayList<Map<String,String>> listToReturn = new ArrayList<Map<String,String>>();
        int index = 0;
        while (true) {
            HashMap<String,String> map = new HashMap<String,String>();
            int numProps = 0;
            for (String propSuffix : propSuffixes) {
                String propValue = evaluator.getProperty(propPrefix + index + "." + propSuffix);
                if (propValue != null) {
                    map.put(propSuffix, propValue);
                    numProps++;
                }
            }
            if (numProps == 0) {
                break;
            }
            listToReturn.add(map);
            index++;
        }
        return listToReturn;
        
    }
    
    private static void storeProperties(EditableProperties editableProps, List<Map<String,String>> newProps, String prefix) {
        
        int propGroupIndex = 0;
        // find all properties with the prefix
        Set<String> keys = editableProps.keySet();
        Set<String> keys2Remove = new HashSet<String>();
        for (String key : keys) {
            if (key.startsWith(prefix)) {
                keys2Remove.add(key);
            }
        }
        // remove all props with the prefix first
        for (String key2Remove : keys2Remove) {
            editableProps.remove(key2Remove);
        }
        // and now save passed list
        for (Map<String,String> map : newProps) {
            // if all values in the map are empty do not store
            boolean allEmpty = true;
            for (String val : map.values()) {
                if (val != null && !val.equals("")) {
                    allEmpty = false;
                    break;
                }
            }
            if (!allEmpty) {
                for (String key : map.keySet()) {
                    String value = map.get(key);
                    String propName = prefix + propGroupIndex + "." + key;
                    editableProps.setProperty(propName, value);
                }
            }
            propGroupIndex++;
        }
    }

    private static enum MixedCodeOptions {
        DEFAULT("default"),  //NOI18N
        TRUSTED_ONLY("trusted_only"),   //NOI18N
        TRUSTED_LIBRARY("trusted_library"); //NOI18N

        private final String propValue;

        private MixedCodeOptions(final String propValue) {
            this.propValue = propValue;
        }

        public String getDisplayName() {
            return NbBundle.getMessage(JWSCustomizerPanel.class, String.format("TXT_MIXED_MODE_%s",name()));
        }

        public String getPropertyValue() {
            return this.propValue;
        }

        @Override
        public String toString() {
            return getDisplayName();
        }

        static MixedCodeOptions fromPropertyValue(final String propValue) {
            assert propValue != null;
            for (MixedCodeOptions option : MixedCodeOptions.values()) {
                if (propValue.equals(option.getPropertyValue())) {
                    return option;
                }
            }
            return null;
        }
    }

    private void initSigning(PropertyEvaluator eval) {
        signing = eval.getProperty(JNLP_SIGNING);
        if (signing == null) signing = "";
        signingKeyStore = eval.getProperty(JNLP_SIGNING_KEYSTORE);
        if (signingKeyStore == null) signingKeyStore = "";
        signingKeyAlias = eval.getProperty(JNLP_SIGNING_KEY);
        if (signingKeyAlias == null) signingKeyAlias = "";
        if (eval.getProperty(JNLP_SIGNING_KEYSTORE_PASSWORD) != null) {
            signingKeyStorePassword = eval.getProperty(JNLP_SIGNING_KEYSTORE_PASSWORD).toCharArray();
        }
        if (eval.getProperty(JNLP_SIGNING_KEY_PASSWORD) != null) {
            signingKeyPassword = eval.getProperty(JNLP_SIGNING_KEY_PASSWORD).toCharArray();
        }
        // compatibility
        if ("".equals(signing) && "true".equals(eval.getProperty(JNLP_SIGNED))) {
            signing = SIGNING_GENERATED;
        }
    }
    
    private void initResources (final PropertyEvaluator eval, final Project prj) {
        final String lz = eval.getProperty(JNLP_LAZY_JARS); //old way, when changed rewritten to new
        final String rcp = eval.getProperty(RUN_CP);        
        final String bc = eval.getProperty(BUILD_CLASSES);        
        final File prjDir = FileUtil.toFile(prj.getProjectDirectory());
        final File bcDir = PropertyUtils.resolveFile(prjDir, bc);
        final List<File> lazyFileList = new ArrayList<File>();
        String[] paths;
        if (lz != null) {
            paths = PropertyUtils.tokenizePath(lz);            
            for (String p : paths) {
                lazyFileList.add(PropertyUtils.resolveFile(prjDir, p));
            }
        }
        paths = PropertyUtils.tokenizePath(rcp);
        final List<File> resFileList = new ArrayList<File>(paths.length);
        for (String p : paths) {
            final File f = PropertyUtils.resolveFile(prjDir, p);
            if (!bcDir.equals(f)) {
                resFileList.add(f);
                if (isTrue(eval.getProperty(String.format(JNLP_LAZY_FORMAT, f.getName())))) {
                    lazyFileList.add(f);
                }
            }
        }
        lazyJars = lazyFileList;
        runtimeCP = resFileList;
        lazyJarsChanged = false;
    }
    
    private void storeResources(final EditableProperties props) {
        if (lazyJarsChanged) {
            //Remove old way if exists
            props.remove(JNLP_LAZY_JARS);
            final Iterator<Map.Entry<String,String>> it = props.entrySet().iterator();
            while (it.hasNext()) {
                if (it.next().getKey().startsWith(JNLP_LAZY_JAR)) {
                    it.remove();
                }
            }
            for (File lazyJar : lazyJars) {
                props.setProperty(String.format(JNLP_LAZY_FORMAT, lazyJar.getName()), "true");  //NOI18N
            }
        }
    }

    private static ComboBoxModel createMixedCodeModel (final PropertyEvaluator eval) {
        assert eval != null;
        final DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (MixedCodeOptions option : MixedCodeOptions.values()) {
            model.addElement(option);
        }
        final String strValue = eval.getProperty(JNLP_MIXED_CODE);
        final MixedCodeOptions value = strValue == null ? null : MixedCodeOptions.fromPropertyValue(strValue);
        if (value != null) {
            model.setSelectedItem(value);
        }
        return model;
    }

    private static Collection<? extends URL> findWebStartJars(
            final PropertyEvaluator evaluator,
            final boolean applet) throws IOException {
        final List<URL> result = new ArrayList<URL>(2);
        final String platformName = evaluator.getProperty("platform.active");   //NOI18N
        if (platformName != null) {
            JavaPlatform active = null;
            for (JavaPlatform platform : JavaPlatformManager.getDefault().getInstalledPlatforms()) {
                if (platformName.equals(platform.getProperties().get("platform.ant.name"))) {   //NOI18N
                    active = platform;
                    break;
                }
            }
            if (active != null) {
                URL lib = findLib(LIB_JAVAWS,active.getInstallFolders());
                if (lib != null) {
                    result.add(lib);
                }
                if (applet) {
                    lib = findLib(LIB_PLUGIN,active.getInstallFolders());
                    if (lib != null) {
                        result.add(lib);
                    }
                }
            }
        }
        return result;
    }

    private static Collection<? extends URL> findWebStartJars(final ClassPath cp) throws IOException {
        final List<URL> result = new ArrayList<URL>(2);
        Pattern pattern = Pattern.compile(
                ".*/("+Pattern.quote(LIB_JAVAWS)+"|"+Pattern.quote(LIB_PLUGIN)+")!/",Pattern.CASE_INSENSITIVE); //NOI18N
        for (ClassPath.Entry entry : cp.entries()) {
            final URL url = entry.getURL();
            if (pattern.matcher(url.toString()).matches()) {
                result.add(url);
            }
        }
        return result;
    }

    private static URL findLib(final String name, final Iterable<? extends FileObject> installFolders) throws IOException {
        if (Utilities.isMac()) {
            //On Mac deploy is fixed in /System/Library/Frameworks/JavaVM.framework/Resources/Deploy.bundle/Contents/Home/lib/
            final File deployFramework = new File("/System/Library/Frameworks/JavaVM.framework/Resources/Deploy.bundle/Contents/Home/lib/");
            final File lib = FileUtil.normalizeFile(new File(deployFramework,name));    //NOI18N
            if (lib.exists()) {
                return FileUtil.getArchiveRoot(lib.toURI().toURL());
            }
        } else {
            for (FileObject installFolder : installFolders) {
                FileObject lib = installFolder.getFileObject(String.format("jre/lib/%s",name)); //NOI18N
                if (lib != null) {
                    return FileUtil.getArchiveRoot(lib.getURL());
                }
            }
        }
        return null;
    }

    private static boolean isTest(final FileObject root, final Project project) {
        assert root != null;
        assert project != null;
        final ClassPath cp = ClassPath.getClassPath(root, ClassPath.COMPILE);
        for (ClassPath.Entry entry : cp.entries()) {
            final FileObject[] srcRoots = SourceForBinaryQuery.findSourceRoots(entry.getURL()).getRoots();
            for (FileObject srcRoot : srcRoots) {
                if (project.equals(FileOwnerQuery.getOwner(srcRoot))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isWebStart (final PropertyEvaluator eval) {
        assert eval != null;
        return isTrue(eval.getProperty(JNLP_ENABLED));
    }

    private static boolean isApplet(final PropertyEvaluator eval) {
        return DescType.applet.toString().equals(eval.getProperty(JNLP_DESCRIPTOR));
    }

    public static boolean isTrue(final String value) {
        return value != null &&
                (value.equalsIgnoreCase("true") ||  //NOI18N
                 value.equalsIgnoreCase("yes") ||   //NOI18N
                 value.equalsIgnoreCase("on"));     //NOI18N
    }
}
