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
package org.netbeans.modules.javascript2.nodejs.editor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.modules.csl.api.Documentation;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.javascript2.editor.model.DeclarationScope;
import org.netbeans.modules.javascript2.editor.model.JsObject;
import org.netbeans.modules.javascript2.editor.spi.model.ModelElementFactory;
import static org.netbeans.modules.javascript2.nodejs.editor.NodeJsUtils.loadFileContent;
import org.openide.filesystems.FileUtil;
import org.openide.modules.Places;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Petr Pisl
 */
@NbBundle.Messages({
    "doc.building=Loading NodeJS Documentation",
    "# {0} - the documentation URL",
    "doc.cannotGet=Cannot load NodeJS documentation from \"{0}\".",
    "doc.notFound=Documentation not found."
})
public class NodeJsDataProvider {

    private static final Logger LOG = Logger.getLogger(NodeJsDataProvider.class.getSimpleName());

    private static RequestProcessor RP = new RequestProcessor(NodeJsDataProvider.class);
    private static NodeJsDataProvider INSTANCE;
    private boolean loadingStarted;
    private ProgressHandle progress;

    private static final String DOC_VERSION = "0.10.31";    //NOI18N
    private static final String DOC_URL = "http://nodejs.org/docs/v" + DOC_VERSION + "/api/"; //NOI18N

    private static final String API_ALL_HTML_FILE = "all.html";
    private static final String CACHE_FOLDER_NAME = "nodejs-doc"; //NOI18N
    private static final String API_ALL_JSON_FILE = "all.json"; //NOI18N

    private static final int URL_CONNECTION_TIMEOUT = 1000; //ms
    private static final int URL_READ_TIMEOUT = URL_CONNECTION_TIMEOUT * 3; //ms

    private static final String AP_STRING = "&#39;"; //NOI18N
    private static final String REQUIRE_STRING = "= require(" + AP_STRING;     //NOI18N

    // name of the json fields in api file
    private static final String MODULES = "modules"; //NOI18N
    private static final String NAME = "name"; //NOI18N
    private static final String DESCRIPTION = "desc"; //NOI18N

    private NodeJsDataProvider() {
        this.loadingStarted = false;
    }

    public static synchronized NodeJsDataProvider getDefault() {
        if (INSTANCE == null) {
            INSTANCE = new NodeJsDataProvider();
        }
        return INSTANCE;
    }

    /**
     *
     * @return URL or null if it's not available.
     */
    public URL getDocumentationURL() {
        URL result = null;
        try {
            result = new URL(DOC_URL);
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return result;
    }

    public Collection<String> getRuntimeModules() {
//        HashSet<String> moduleNames = new HashSet<String>();
//        Object jsonValue;
//        JSONArray modules = getModules();
//        if (modules != null) {
//            for (int i = 0; i < modules.size(); i++) {
//                jsonValue = modules.get(i);
//                if (jsonValue != null && jsonValue instanceof JSONObject) {
//                    JSONObject jsonModule = (JSONObject) jsonValue;
//                    jsonValue = jsonModule.get(NAME);
//                    if (jsonValue != null && jsonValue instanceof String ) {
//                        moduleNames.add((String)jsonValue);
//                    }
//                }
//            }
//        }
        String content = getContentApiFile();
        int index = 0;
        int lenghtOfRequire = REQUIRE_STRING.length();
        index = content.indexOf(REQUIRE_STRING, index);
        HashSet<String> modules = new HashSet<String>();
        while (index != -1) {
            index += lenghtOfRequire;
            if (content.charAt(index) != '.') {
                int end = content.indexOf(AP_STRING, index);
                if (end > -1) {
                    String module = content.substring(index, end);
                    modules.add(module);
                }
            }
            index = content.indexOf(REQUIRE_STRING, index);
        }
        return modules;
    }

    public String getDocForModule(final String moduleName) {
        Object jsonValue;
        JSONArray modules = getModules();
        if (modules != null) {
            for (int i = 0; i < modules.size(); i++) {
                jsonValue = modules.get(i);
                if (jsonValue != null && jsonValue instanceof JSONObject) {
                    JSONObject jsonModule = (JSONObject) jsonValue;
                    jsonValue = jsonModule.get(NAME);
                    if (jsonValue != null && jsonValue instanceof String && moduleName.equals(((String) jsonValue).toLowerCase())) {
                        jsonValue = jsonModule.get(DESCRIPTION);
                        if (jsonValue != null && jsonValue instanceof String) {
                            return (String) jsonValue;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Collection<JsObject> getGlobalObjects(ModelElementFactory factory) {
        String content = getContentApiFile();
        if (content != null && !content.isEmpty()) {
            File apiFile = getCachedAPIFile();
            JsObject globalObject = factory.newGlobalObject(FileUtil.toFileObject(apiFile), (int) apiFile.length());
            JSONObject root = (JSONObject) JSONValue.parse(content);
            if (root != null) {
                JSONArray globals = getJSONArrayProperty(root, "globals");
                if (globals != null) {
                    for (Object jsonValue : globals) {
                        if (jsonValue instanceof JSONObject) {
                            JSONObject global = (JSONObject) jsonValue;
                            String name = getJSONStringProperty(global, NAME);
                            if (name != null) {
                                JsObject property = createProperty(factory, globalObject, global);
                                addProperties(factory, property, (DeclarationScope) globalObject, global);
                                addMethods(factory, property, (DeclarationScope)globalObject, global);
                            }
                        }
                    }
                }
                JSONArray vars = getJSONArrayProperty(root, "vars");
                if (vars != null) {
                    for (Object jsonValue : vars) {
                        if (jsonValue instanceof JSONObject) {
                            JSONObject var = (JSONObject) jsonValue;
                            String name = getJSONStringProperty(var, NAME);
                            if (name != null) {
//                                if (REQUIRE_STRING.equals(name)) {
//
//                                } else {
//                                    
//                                }
                                JsObject property = createProperty(factory, globalObject, var);
                                addProperties(factory, property, (DeclarationScope) globalObject, var);
                                addMethods(factory, property, (DeclarationScope)globalObject, var);
                            }
                        }
                    }
                }
                addMethods(factory, globalObject, (DeclarationScope) globalObject, root);
            }
            return Collections.singletonList(globalObject);
        }
        return Collections.emptyList();
    }

    private void addMethods(final ModelElementFactory factory, final JsObject toObject, final DeclarationScope scope, final JSONObject fromObject) {
        JSONArray methods = getJSONArrayProperty(fromObject, "methods");
        if (methods != null) {
            for (Object methodO : methods) {
                if (methodO instanceof JSONObject) {
                    JSONObject method = (JSONObject) methodO;
                    String methodName = getJSONStringProperty(method, NAME);
                    JSONArray signatures = getJSONArrayProperty(method, "signatures");
                    String doc = getJSONStringProperty(method, "desc");
                    if (methodName != null && signatures != null) {
                        for (Object signature : signatures) {
                            JSONArray params = getJSONArrayProperty((JSONObject) signature, "params");
                            List<String> paramNames = new ArrayList<String>();
                            if (params != null && !params.isEmpty()) {
                                for (Object param : params) {
                                    String paramName = getJSONStringProperty((JSONObject) param, NAME);
                                    if (paramName != null) {
                                        paramNames.add(paramName);
                                    }
                                }
                            }
                            JsObject object = factory.newFunction(scope, toObject, methodName, paramNames, NodeJsUtils.NODEJS_NAME);
                            object.setDocumentation(Documentation.create(doc, getDocumentationURL(methodName, paramNames)));
                            toObject.addProperty(object.getName(), object);
                            addProperties(factory, object, (DeclarationScope) object, method);
                            addMethods(factory, object, (DeclarationScope) object, method);
                        }
                    }
                }
            }
        }
    }

    private JsObject createProperty(final ModelElementFactory factory, final JsObject parent, final JSONObject jsonObject) {
        String propertyName = getJSONStringProperty(jsonObject, NAME);
        if (propertyName != null) {
            JsObject object = factory.newObject(parent, propertyName, OffsetRange.NONE, true, NodeJsUtils.NODEJS_NAME);
            parent.addProperty(object.getName(), object);
            String doc = getJSONStringProperty(jsonObject, "desc");
            object.setDocumentation(Documentation.create(doc, getDocumentationURL(propertyName)));
            return object;
        }
        return null;
    }

    private void addProperties(final ModelElementFactory factory, final JsObject toObject, final DeclarationScope scope, final JSONObject fromObject) {
        JSONArray properties = getJSONArrayProperty(fromObject, "properties");
        if (properties != null) {
            for (Object propertyO : properties) {
                if (propertyO instanceof JSONObject) {
                    JSONObject property = (JSONObject) propertyO;
                    JsObject newProperty = createProperty(factory, toObject, property);
                    if (newProperty != null) {
                        addProperties(factory, newProperty, scope, property);
                        addMethods(factory, newProperty, scope, property);
                    }
                }
            }
        }
    }

    private String getJSONStringProperty(final JSONObject object, final String property) {
        Object value = object.get(property);
        if (value != null && value instanceof String) {
            return (String) value;
        }
        return null;
    }

    private JSONArray getJSONArrayProperty(final JSONObject object, final String property) {
        Object value = object.get(property);
        if (value != null && value instanceof JSONArray) {
            return (JSONArray) value;
        }
        return null;
    }

    private URL getDocumentationURL(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(DOC_URL).append(API_ALL_HTML_FILE).append("#all_");
        String alteredName = name;
        while (alteredName.charAt(0) == '_') {
            alteredName = alteredName.substring(1);
        }
        sb.append(alteredName.toLowerCase());
        URL result = null;
        try {
            result = new URL(sb.toString());
        } catch (MalformedURLException ex) {
            // Do nothing
        }
        return result;
    }

    private URL getDocumentationURL(String name, Collection<String> params) {
        URL result = getDocumentationURL(name);
        if (result != null) {
            StringBuilder sb = new StringBuilder(); 
            sb.append(result.toExternalForm());
            for (String param : params) {
                sb.append('_').append(param);
            }
            result = null;
            try {
                result = new URL(sb.toString());
            } catch (MalformedURLException ex) {
                // Do nothing
            }
        }
        return result;
    }

    private JSONArray getModules() {
        String content = getContentApiFile();
        if (content != null && !content.isEmpty()) {
            JSONObject root = (JSONObject) JSONValue.parse(content);
            if (root != null) {
                Object jsonValue = root.get(MODULES);
                if (jsonValue != null && jsonValue instanceof JSONArray) {
                    return (JSONArray) jsonValue;
                }
            }
        }
        return null;
    }

    private void loadURL(URL url, Writer writer, Charset charset) throws IOException {
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        URLConnection con = url.openConnection();
        con.setConnectTimeout(URL_CONNECTION_TIMEOUT);
        con.setReadTimeout(URL_READ_TIMEOUT);
        con.connect();
        Reader r = new InputStreamReader(new BufferedInputStream(con.getInputStream()), charset);
        char[] buf = new char[2048];
        int read;
        while ((read = r.read(buf)) != -1) {
            writer.write(buf, 0, read);
        }
        r.close();
    }

    private String getFileContent(File file) throws IOException {
        Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8"); // NOI18N
        StringBuilder sb = new StringBuilder();
        try {
            char[] buf = new char[2048];
            int read;
            while ((read = r.read(buf)) != -1) {
                sb.append(buf, 0, read);
            }
        } finally {
            r.close();
        }
        return sb.toString();
    }

    private File getCachedAPIFile() {
        String pathFile = new StringBuilder().append(CACHE_FOLDER_NAME).append('/')
                .append(DOC_VERSION).append('/').append(API_ALL_JSON_FILE).toString();
        File cacheFile = Places.getCacheSubfile(pathFile);
        return cacheFile;
    }

    private String getContentApiFile() {
        String result = null;
        try {
            File cacheFile = getCachedAPIFile();
            if (!cacheFile.exists()) {

                //if any of the files is not loaded yet, start the loading process
                if (!loadingStarted) {
                    loadingStarted = true;
                    startLoading();
                }
                //load from web and cache locally
                loadDoc(cacheFile);
                if (progress != null) {
                    progress.progress(1);
                    progress.finish();
                    progress = null;
                }

                LOG.log(Level.FINE, "Loading doc finished."); //NOI18N
            }
            result = getFileContent(cacheFile);
        } catch (URISyntaxException | IOException ex) {
            loadingStarted = false;
            LOG.log(Level.INFO, "Cannot load NodeJS documentation from \"{0}\".", new Object[]{getDocumentationURL()}); //NOI18N
            LOG.log(Level.INFO, "", ex);
        }
        return result;
    }

    private void startLoading() {
        LOG.fine("start loading doc"); //NOI18N

        progress = ProgressHandleFactory.createHandle(Bundle.doc_building());
        progress.start(1);
    }

    private void loadDoc(File cacheFile) throws URISyntaxException, MalformedURLException, IOException {
        LOG.fine("start loading doc"); //NOI18N
        URL url = new URL(getDocumentationURL().toExternalForm() + API_ALL_JSON_FILE);
        synchronized (cacheFile) {
            String tmpFileName = cacheFile.getAbsolutePath() + ".tmp";  //NOI18N
            File tmpFile = new File(tmpFileName);
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(tmpFile), "UTF-8")) { // NOI18N
                loadURL(url, writer, Charset.forName("UTF-8")); //NOI18N
                writer.close();
                tmpFile.renameTo(cacheFile);
            } finally {
                if (tmpFile.exists()) {
                    tmpFile.delete();
                }
            }

        }
    }
}