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

package org.netbeans.modules.websvc.rest.codegen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.queries.FileEncodingQuery;
import org.netbeans.modules.j2ee.deployment.devmodules.api.Deployment;
import org.netbeans.modules.j2ee.deployment.devmodules.api.InstanceRemovedException;
import org.netbeans.modules.j2ee.deployment.devmodules.api.ServerInstance;
import org.netbeans.modules.j2ee.deployment.devmodules.api.ServerInstance.Descriptor;
import org.netbeans.modules.j2ee.deployment.devmodules.spi.J2eeModuleProvider;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.netbeans.modules.websvc.rest.RestUtils;
import org.openide.filesystems.FileUtil;
import org.netbeans.modules.websvc.rest.codegen.model.ClientStubModel;
import org.netbeans.modules.websvc.rest.codegen.model.Resource;
import org.netbeans.modules.websvc.rest.codegen.model.ResourceModel;
import org.netbeans.modules.websvc.rest.codegen.model.WadlModeler;
import org.netbeans.modules.websvc.rest.codegen.model.ClientStubModel.*;
import org.netbeans.modules.websvc.rest.spi.RestSupport;
import org.openide.filesystems.FileSystem;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;

/**
 * Code generator for plain REST resource class.
 * The generator takes as paramenters:
 *  - target directory
 *  - REST resource bean meta getModel().
 *
 * @author Ayub Khan
 * @author (rewritten by) ads
 */
public class ClientStubsGenerator extends AbstractGenerator {

    public static final String REST = "rest"; //NOI18N
    public static final String RJS = "rjs"; //NOI18N
    public static final String CSS = "css"; //NOI18N
    public static final String JS = "js"; //NOI18N
    public static final String HTML = "html"; //NOI18N
    public static final String HTM = "htm"; //NOI18N
    public static final String TXT = "txt"; //NOI18N
    public static final String JSON = "json"; //NOI18N
    public static final String GIF = "gif"; //NOI18N
    public static final String IMAGES = "images"; //NOI18N
    public static final String BUNDLE = "Bundle"; //NOI18N
    public static final String PROPERTIES = "properties"; //NOI18N

    public static final String JS_SUPPORT = "Support"; //NOI18N
    public static final String JS_TESTSTUBS = "TestStubs"; //NOI18N
    public static final String JS_README = "Readme"; //NOI18N
    public static final String JS_TESTSTUBS_TEMPLATE = "Templates/WebServices/JsTestStubs.html"; //NOI18N
    public static final String JS_STUBSUPPORT_TEMPLATE = "Templates/WebServices/JsStubSupport.js"; //NOI18N
    public static final String JS_PROJECTSTUB_TEMPLATE = "Templates/WebServices/JsProjectStub.js"; //NOI18N
    public static final String JS_STUB_TEMPLATE = "Templates/WebServices/JsStub.js"; //NOI18N
    public static final String JS_ENTITY_TEMPLATE = "Templates/WebServices/JsEntity.js"; //NOI18N
    public static final String JS_README_TEMPLATE = "Templates/WebServices/JsReadme.html"; //NOI18N

    public static final String PROXY = "RestProxyServlet"; //NOI18N
    public static final String PROXY_URL = "/restproxy";
    public static final String PROXY_TEMPLATE = "Templates/WebServices/RestProxyServlet.txt"; //NOI18N

    public static final String TTL_DojoResources_Stubs = "TTL_DojoResources_Stubs";
    public static final String MSG_Readme = "MSG_Readme";
    public static final String MSG_TestPage = "MSG_TestPage";
    public static final String TTL_RestClient_Stubs = "TTL_RestClient_Stubs";
    public static final String MSG_SelectResource = "MSG_SelectResource";
    public static final String MSG_JS_Readme_Content = "MSG_JS_Readme_Content";

    public static final String DEFAULT_PROTOCOL = "http";
    public static final String DEFAULT_HOST = "localhost";
    public static final String DEFAULT_PORT = "8080";
    public static final String DEFAULT_BASE_URL = DEFAULT_PROTOCOL+"://"+DEFAULT_HOST+":"+DEFAULT_PORT;
    public static final String BASE_URL_TOKEN = "__BASE_URL__";
    public static final String FILE_ENCODING_TOKEN = "__FILE_ENCODING__";

    private FileObject stubFolder;
    private Project p;
    private boolean overwrite;
    private String projectName;
    private ResourceModel model;
    private FileObject rjsDir;
    private FileObject wadl;
    private String folderName;
    private String baseUrl;
    private String proxyUrl;
    private Charset baseEncoding;
    private FileObject rootFolder;

    public ClientStubsGenerator(FileObject rootFolder, String folderName, Project p,
            boolean overwrite) throws IOException {
        assert p != null;
        this.rootFolder = rootFolder;
        this.folderName = folderName;
        this.p = p;
        this.overwrite = overwrite;
        this.projectName = ProjectUtils.getInformation(getProject()).getName();
        this.baseEncoding = FileEncodingQuery.getEncoding(rootFolder);
    }

    public ClientStubsGenerator(FileObject rootFolder, String folderName, FileObject wadl,
            boolean overwrite) throws IOException {
        this.rootFolder = rootFolder;
        this.folderName = folderName;
        this.wadl = wadl;
        this.overwrite = overwrite;
        this.projectName = "NewProject";
        this.baseEncoding = FileEncodingQuery.getEncoding(rootFolder);
    }

    public FileObject getRootFolder() {
        return rootFolder;
    }

    public FileObject getStubFolder() {
        if(stubFolder == null) {
            try {
                stubFolder = createFolder(getRootFolder(), getFolderName());
            } catch (IOException ex) {
            }
        }
        return stubFolder;
    }

    public void setStubFolder(FileObject stubFolder) {
        this.stubFolder = stubFolder;
    }

    public String getFolderName() {
        return folderName;
    }

    public Project getProject() {
        return p;
    }

    public boolean canOverwrite() {
        return overwrite;
    }

    public String getProjectName() {
        return projectName;
    }

    public ResourceModel getModel() {
        return model;
    }

    public String getDefaultBaseUrl() {
        return DEFAULT_BASE_URL;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public Charset getBaseEncoding() {
        return baseEncoding;
    }

    private String getApplicationNameFromUrl(String url) {
        String appName = url.replaceAll(DEFAULT_PROTOCOL+"://", "");
        if(appName.endsWith("/"))
            appName = appName.substring(0, appName.length()-1);
        String[] paths = appName.split("/");
        if(paths != null && paths.length > 0) {
            for(int i=0;i<paths.length;i++) {
                String path = paths[i];
                if(path != null && path.startsWith(DEFAULT_HOST) &&
                        i+1 < paths.length && paths[i+1] != null &&
                        paths[i+1].trim().length() > 0) {
                    return ClientStubModel.normalizeName(paths[i+1]);
                }
            }
        }
        return ClientStubModel.normalizeName(appName);
    }

    private String findBaseUrl(Project p) {
        String url = null;
        J2eeModuleProvider provider = p.getLookup().lookup(J2eeModuleProvider.class);
        if (provider != null) {
            String sID = provider.getServerInstanceID();
            ServerInstance si = Deployment.getDefault().getServerInstance(sID);
            if (si != null) {
                try {
                    Descriptor descriptor = si.getDescriptor();
                    url = (descriptor.getHttpPort() == 80)
                            ? descriptor.getHostname()
                            : descriptor.getHostname() + ":" + descriptor.getHttpPort(); //NOI18N
                    //XXX - should somehow support/expect HTTPS too?
                    url = "http://" + url; //NOI18N
                } catch (InstanceRemovedException ex) {
                    url = null;
                }
            }
        }
        return url;
    }

    private String findAppContext(Project p) {
        String cPath = WebModule.getWebModule(p.getProjectDirectory()).getContextPath();
        if (cPath != null) {
            cPath = cPath.substring(1);
        } else {
            cPath = ProjectUtils.getInformation(p).getName();
        }
        return cPath;
    }

    public Set<FileObject> generate(ProgressHandle pHandle) throws IOException {
        if(pHandle != null)
            initProgressReporting(pHandle, false);
        Project targetPrj = FileOwnerQuery.getOwner(getRootFolder());
        if(p != null) {
            this.model = new ClientStubModel().createModel(p);
            this.model.build();
            String url = findBaseUrl(p);
            if(url == null)
                url = getDefaultBaseUrl();
            String proxyUrl2 = findBaseUrl(targetPrj);
            if(proxyUrl2 == null){
                proxyUrl2 = url;
            }
            RestSupport restSupport = p.getLookup().lookup(RestSupport.class);
            String path = restSupport.getApplicationPath();
            setBaseUrl((url.endsWith("/")?url:url+"/") + findAppContext(getProject()) + (path.startsWith("/")?path:"/"+path));
            setProxyUrl((proxyUrl2.endsWith("/")?proxyUrl2:proxyUrl2+"/") + findAppContext(targetPrj) + PROXY_URL);
        } else if(wadl != null) {
            this.model = new ClientStubModel().createModel(wadl);
            this.model.build();
            String url = ((WadlModeler)this.model).getBaseUrl();
            if(url == null) {
                url = getDefaultBaseUrl();
            }
            setBaseUrl(url);
            setProxyUrl(url+".."+PROXY_URL);
            this.projectName = getApplicationNameFromUrl(url);
        }
        List<Resource> resourceList = getModel().getResources();

        rjsDir = getStubFolder();
        initJs(p);

        FileObject prjStubDir = createFolder(rjsDir, getProjectName().toLowerCase());
        createDataObjectFromTemplate(JS_PROJECTSTUB_TEMPLATE, prjStubDir, 
                getProjectName(), JS, canOverwrite());
        Set<String> entities = new HashSet<String>();
        for (Resource r : resourceList) {
            if(pHandle != null) {
                reportProgress(NbBundle.getMessage(ClientStubsGenerator.class,
                    "MSG_GeneratingClass", r.getName(), JS));
            }
            ResourceJavaScript js = new ResourceJavaScript( this , r, prjStubDir,
                    entities );
            js.generate();
            r.getEntities();
            Set<String> generated = r.getEntities();
            entities.addAll( generated );
        }
        updateProjectStub(prjStubDir.getFileObject(getProjectName(), JS), 
                getProjectName());
        updateRestStub(rjsDir.getFileObject(JS_TESTSTUBS, HTML), resourceList, "");

        Set<FileObject> files = new HashSet<FileObject>();
        FileObject rjsTest = rjsDir.getFileObject(JS_TESTSTUBS, HTML);
        if(rjsTest != null)
            files.add(rjsTest);
        FileObject readme = rjsDir.getFileObject(JS_README, TXT);
        if(readme != null)
            files.add(readme);
        return files;
    }

    protected FileObject createDataObjectFromTemplate(final String template, final FileObject dir,
            final String fileName, final String ext, final boolean overwrite) throws IOException {
        FileObject rF0 = dir.getFileObject(fileName, ext);
        if(rF0 != null) {
            if(overwrite) {
                DataObject d = DataObject.find(rF0);
                if(d != null)
                    d.delete();
            } else {
                return rF0;
            }
        }
        DataObject d0 = RestUtils.createDataObjectFromTemplate(template, dir, fileName);
        return d0.getPrimaryFile();
    }

    protected void copyDirectory(final FileSystem fs, final File src, final File dst)
            throws IOException {
        if (src.isDirectory()) {
            if (!dst.exists()) {
                dst.mkdir();
            }
            String files[] = src.list();
            for (int i = 0; i < files.length; i++) {
                copyDirectory(fs, new File(src, files[i]),
                        new File(dst, files[i]));
            }
        } else {
            if (!src.exists()) {
                throw new IOException("File or directory does not exist.");
            } else {
                fs.runAtomicAction(new FileSystem.AtomicAction() {
                    public void run() throws IOException {
                        InputStream in = new FileInputStream(src);
                        OutputStream out = new FileOutputStream(dst);
                        try {
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                        } finally {
                            in.close();
                            out.close();
                        }
                    }
                });
            }
        }
    }

    private void initJs(Project p) throws IOException {
        TokenReplacer tr = new TokenReplacer(this);
        tr.addToken(TTL_RestClient_Stubs, NbBundle.getMessage(ClientStubsGenerator.class, TTL_RestClient_Stubs));
        tr.addToken(MSG_Readme, NbBundle.getMessage(ClientStubsGenerator.class, MSG_Readme));
        tr.addToken(MSG_TestPage, NbBundle.getMessage(ClientStubsGenerator.class, MSG_TestPage));
        tr.addToken(MSG_JS_Readme_Content, NbBundle.getMessage(ClientStubsGenerator.class, MSG_JS_Readme_Content));
        tr.addToken(FILE_ENCODING_TOKEN, getBaseEncoding().name());

        FileObject fo = createDataObjectFromTemplate(JS_TESTSTUBS_TEMPLATE, rjsDir, JS_TESTSTUBS, HTML, false);
        tr.replaceTokens(fo);

        createDataObjectFromTemplate(JS_STUBSUPPORT_TEMPLATE, rjsDir, JS_SUPPORT, JS, false);

        fo = createDataObjectFromTemplate(JS_README_TEMPLATE, rjsDir, JS_README, HTML, false);
        tr.replaceTokens(fo);

        fo = createDataObjectFromTemplate(PROXY_TEMPLATE, rjsDir, PROXY, TXT, false);

        File cssDir = new File(FileUtil.toFile(rjsDir), "css");
        cssDir.mkdirs();
        copySupportFiles(cssDir);
    }

    private void copySupportFiles(File cssDir) throws IOException {
        String[] fileNames = {
            "clientstubs.css",
            "css_master-all.css",
            "images/background_border_bottom.gif",
            "images/pbsel.png",
            "images/bg_gradient.gif",
            "images/pname-clientstubs.png",
            "images/level1_selected-1lvl.jpg",
            "images/primary-enabled.gif",
            "images/masthead.png",
            "images/primary-roll.gif",
            "images/pbdis.png",
            "images/secondary-enabled.gif",
            "images/pbena.png",
            "images/tbsel.png",
            "images/pbmou.png",
            "images/tbuns.png"
        };
        File imagesDir = new File(cssDir, "images");
        imagesDir.mkdirs();
        for(String file: fileNames) {
            RestSupport.copyFile(cssDir, file);
        }
    }

    protected void copyFile(String resourceName, File destFile) throws IOException {
        String path = "resources/"+resourceName;
        if(!destFile.exists()) {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = ClientStubsGenerator.class.getResourceAsStream(path);
                os = new FileOutputStream(destFile);
                int c;
                while ((c = is.read()) != -1) {
                    os.write(c);
                }
            } finally {
                if(os != null) {
                    os.flush();
                    os.close();
                }
                if(is != null)
                    is.close();
            }
        }
    }

    protected FileObject createFolder(FileObject parent, String folderName) throws IOException {
        FileObject folder = parent.getFileObject(folderName);
        if(folder == null)
            folder = parent.createFolder(folderName);
        return folder;
    }

    private void updateProjectStub(FileObject projectStub, String prjName) 
        throws IOException 
    {
        FileLock lock = projectStub.lock();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    FileUtil.toFile(projectStub)));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains(BASE_URL_TOKEN)) {
                    String url = getBaseUrl();
                    if ( url.endsWith("/")){
                        url = url.substring(0, url.length()-1);
                    }
                    sb.append(line.replaceAll(BASE_URL_TOKEN, url));
                } else if (line.contains("__PROJECT_NAME__")) {
                    sb.append(line.replaceAll("__PROJECT_NAME__", prjName));
                } else if (line.contains("__PROJECT_INIT_BODY__")) {
                    StringBuilder initBody = new StringBuilder();
                    int count = 0;
                    List<Resource> resourceList = getModel().getResources();
                    for (Resource resource : resourceList) {
                        if (resource.hasDefaultGet()) {
                            String path = resource.getPath();
                            if (!path.startsWith("/")) {
                                path = "/"+path;
                            }
                            initBody.append( "      this.resources[");
                            initBody.append( count++ );
                            initBody.append( "] = new " ); 
                            initBody.append( resource.getName());
                            initBody.append("(this.uri+'" );
                            initBody.append( path );
                            initBody.append("');\n");
                        }
                    }
                    sb.append(initBody);
                } else {
                    sb.append(line);
                }
                sb.append("\n");
            }
            OutputStreamWriter writer = new OutputStreamWriter(projectStub.getOutputStream(lock), getBaseEncoding());
            try {
                writer.write(sb.toString());
            } finally {
                writer.close();
            }
        } finally {
            lock.releaseLock();
        }
    }

    private void updateRestStub(FileObject restStub, List<Resource> resourceList, String pkg) throws IOException {
        String prjName = getProjectName();
        String prjStubDir = prjName.toLowerCase();
        StringBuilder sb1 = new StringBuilder();
        sb1.append("\t<script type='text/javascript' src='./" );
        sb1.append(prjStubDir); 
        sb1.append( '/');
        sb1.append(prjName );
        sb1.append('.');
        sb1.append(JS ); 
        sb1.append("'></script>\n");
        for (Resource r : resourceList) {
            sb1.append("\t<script type='text/javascript' src='./" );
            sb1.append(prjStubDir); 
            sb1.append( '/');
            sb1.append(r.getName() );
            sb1.append('.');
            sb1.append(JS ); 
            sb1.append("'></script>\n");
            Iterator<String> iterator = r.getEntities().iterator();
            while ( iterator.hasNext() ){
                sb1.append("\t<script type='text/javascript' src='./" );
                sb1.append(prjStubDir); 
                sb1.append( '/');
                sb1.append(iterator.next() );
                sb1.append('.');
                sb1.append(JS ); 
                sb1.append("'></script>\n");
            }
        }
        StringBuilder sb2 = new StringBuilder();
        String url = getBaseUrl();
        if ( url.endsWith("/")) {
            url = url.substring(0, url.length()-1);
        }
        sb2.append("\n\t<!-- Using JavaScript files for project " + prjName + "-->\n");
        sb2.append("\t<script language='Javascript'>\n");
        sb2.append("\t\tvar str = '';\n");
        sb2.append("\t\t//Example test code for " + prjName + "\n");
        sb2.append("\t\tstr = '<h2>Resources for " + prjName + ":</h2><br><table border=\"1\">';\n");
        sb2.append("\t\tvar app = new " + pkg+prjName + "('"+url+"');\n");
        sb2.append("\t\t//Uncomment below if using proxy for javascript cross-domain.\n");
        sb2.append("\t\t//app.setProxy(\""+getProxyUrl()+"\");\n");
        sb2.append("\t\tvar resources = app.getResources();\n");
        sb2.append("\t\tfor(i=0;i<resources.length;i++) {\n");
        sb2.append("\t\t  var resource = resources[i];\n");
        sb2.append("\t\t  var uri = resource.getUri();\n");
        sb2.append("\t\t  str += '<tr><td valign=\"top\"><a href=\"'+uri+'\" target=\"_blank\">'+uri+'</a></td><td>';\n");
        sb2.append("\t\t  var items  = resource.getEntities();\n");
        sb2.append("\t\t  if (items != undefined) {\n");
        sb2.append("\t\t    if (items.length > 0) {\n");
        sb2.append("\t\t      for(j=0;j<items.length;j++) {\n");
        sb2.append("\t\t        var item = items[j];\n");
        sb2.append("\t\t        var uri2 = item.getUri();\n");
        sb2.append("\t\t        if ( uri2 != null && uri2 != undefined ){\n");
        sb2.append("\t\t            str += '<a href=\"'+uri2+'\" target=\"_blank\">'+uri2+'</a><br/>';\n");
        sb2.append("\t\t        }\n");
        sb2.append("\t\t        str += '&nbsp;&nbsp;<font size=\"-3\">'+item.toString()+'</font><br/>';\n");
        sb2.append("\t\t      }\n");
        sb2.append("\t\t    } else {\n");
        sb2.append("\t\t      str += 'No items detected';\n");
        sb2.append("\t\t    }\n");
        sb2.append("\t\t  } else {\n");
        sb2.append("\t\t    str += 'No items, please check the url: <a href=\"'+uri+'\" target=\"_blank\">'+uri+'</a>.<br/>" +
                "Set proxy if RESTful web service is not running on the same domain as this application.';\n");
        sb2.append("\t\t  }\n");
        sb2.append("\t\t  str += '</td></tr>';\n");
        sb2.append("\t\t}\n");
        sb2.append("\t\tstr += '</table><br>';\n");
        sb2.append("\t\tvar n = document.getElementById('containerContent');\n");
        sb2.append("\t\tn.innerHTML = n.innerHTML + str;\n\n");
        sb2.append("\t</script>\n");
        FileLock lock = restStub.lock();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FileUtil.toFile(restStub)));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains("JS_DECLARE_END")) {
                    sb.append(sb1.toString());
                } else if (line.contains("JS_USAGE_END")) {
                    sb.append(sb2.toString());
                }
                sb.append(line);
                sb.append("\n");
            }
            OutputStreamWriter writer = new OutputStreamWriter(restStub.getOutputStream(lock), getBaseEncoding());
            try {
                writer.write(sb.toString());
            } finally {
                writer.close();
            }
        } finally {
            lock.releaseLock();
        }
    }
}
