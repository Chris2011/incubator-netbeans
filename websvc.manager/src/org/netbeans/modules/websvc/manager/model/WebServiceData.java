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

package org.netbeans.modules.websvc.manager.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.websvc.manager.api.WebServiceDescriptor;
import org.netbeans.modules.websvc.api.jaxws.wsdlmodel.WsdlService;

/**
 * A webservice meta data. Holds the URL location, package name for code generation
 * When the WSDL is parsed for each service a WebServiceData is created and added to
 * the WebServiceListModel.
 * @author Winston Prakash, David Botterill, cao
 */
public class WebServiceData {
    public static final String JAX_WS = "jaxws";
    public static final String JAX_RPC = "jaxrpc";
    
    /** Unique Web service id*/
    private String websvcId;
    
    /** Web service URL (File) */
    private String wsdlUrl;
    private String originalWsdl;
    private String catalog;
    
    /** Group ID to which this Web Service belogs */
    private String groupId;
    private String packageName;
    
    /** WSDL Service Model this meta model wraps */
    WsdlService wsdlService;
    
    /* Default package name for generated source */
    public final static String DEFAULT_PACKAGE_NAME = "webservice";
    
    /** This is the name of WsdlService WsdlService.getName()
     * Used to find the corresponding WsdlService during loading from
     * persstence
     */
    private String wsName;
    private boolean jaxWsEnabled;
    private boolean jaxRpcEnabled;
    private boolean compiled;
    
    private boolean resolved;
    
    // File descriptors for each web service type
    private WebServiceDescriptor jaxWsDescriptor;
    private WebServiceDescriptor jaxRpcDescriptor;
    
    private String jaxWsDescriptorPath;
    private String jaxRpcDescriptorPath;
    
    private List<WebServiceDataListener> listeners = new ArrayList<WebServiceDataListener>();
    private List<PropertyChangeListener> propertyListeners = new ArrayList<PropertyChangeListener>();
    
    /** Default constructor needed for persistence*/
    public WebServiceData() {
        this.resolved = true;
    }
    
    public WebServiceData(String url, String originalWsdl, String groupId) {
        websvcId = WebServiceListModel.getInstance().getUniqueWebServiceId();
        wsdlUrl = url;
        this.packageName = derivePackageName(originalWsdl, null);
        this.groupId = groupId;
        this.compiled = false;
        this.originalWsdl = originalWsdl;
        this.resolved = true;
    }
    
    public WebServiceData(WsdlService service, String url, String originalWsdl, String groupId){
        this(url, originalWsdl, groupId);
        wsdlService = service;
        wsName = service.getName();
    }
    
    public WebServiceData(WebServiceData that) {
        this(that.getURL(), that.getOriginalWsdl(), that.getGroupId());
        this.packageName = that.packageName;
        this.jaxWsDescriptor = that.jaxWsDescriptor;
        this.jaxWsDescriptorPath = that.jaxWsDescriptorPath;
        this.jaxWsEnabled = that.jaxWsEnabled;
        this.jaxRpcDescriptor = that.jaxRpcDescriptor;
        this.jaxRpcDescriptorPath = that.jaxRpcDescriptorPath;
        this.jaxRpcEnabled = that.jaxRpcEnabled;
        this.catalog = that.catalog;
        this.wsdlService = that.wsdlService;
        this.wsName = that.wsName;
    }
    
    public boolean isReady() {
        if (! new File(getURL()).isFile() || getCatalog() == null || ! new File(getCatalog()).isFile()) {
            return false;
        }
        
        if (getName() == null || getWsdlService() == null) {
            return false;
        }
        
        if (getJaxWsDescriptor() == null || getJaxWsDescriptor().getJars().isEmpty()) {
            return false;
        }
        
        return true;
    }
    
    public void setResolved(boolean resolved) {
        boolean oldValue = this.resolved;
        this.resolved = resolved;
        PropertyChangeEvent evt = 
                new PropertyChangeEvent(this, "resolved", Boolean.valueOf(oldValue), Boolean.valueOf(this.resolved)); // NOI18N
        
        for (PropertyChangeListener listener : propertyListeners) {
            listener.propertyChange(evt);
        }
        
    }
    
    public boolean isResolved() {
        return resolved;
    }
    
    private String derivePackageName(String wsdlURL, String subPackage) {
        if (wsdlURL.startsWith("file:")) {
            return DEFAULT_PACKAGE_NAME + ((subPackage != null) ? subPackage : "");
        }
        int iStart = wsdlURL.indexOf("://") + 3;
        int iEnd = wsdlURL.indexOf('/', iStart);
        String pakName = wsdlURL.substring(iStart, iEnd);
        String[] segments = pakName.split("\\.");
        StringBuilder sb = new StringBuilder(segments[segments.length - 1]);
        sb.append('.');
        sb.append(segments[segments.length - 2]);
        if (subPackage != null) {
            sb.append(".");
            sb.append(subPackage.toLowerCase());
        }
        return sb.toString();
    }
    
    public void setWsdlService(WsdlService svc){
        wsdlService = svc;

        if (jaxRpcDescriptor != null) {
            jaxRpcDescriptor.setModel(wsdlService);
        }

        if (jaxWsDescriptor != null) {
            jaxWsDescriptor.setModel(wsdlService);
        }
    }
    
    public WsdlService getWsdlService( ) {
        return wsdlService;
    }
    
    public void setId(String id){
        websvcId = id;
    }
    
    public String getId(){
        return websvcId;
    }
    
    
    public void setName(String name) {
        wsName = name;
    }
    
    public String getName() {
        return wsName;
    }
    
    public String getGroupId(){
        return groupId;
    }
    
    public void setGroupId(String id){
        setModelDirty();
        groupId = id;
    }
    
    public String getURL() {
        return wsdlUrl;
    }
    
    public void setURL(String url) {
        setModelDirty();
        wsdlUrl = url;
    }
    
    public String getOriginalWsdl() {
        return originalWsdl;
    }
    
    public void setOriginalWsdl(String originalWsdl) {
        this.originalWsdl = originalWsdl;
    }
    
    public void setPackageName(String inPackageName){
        setModelDirty();
        packageName = inPackageName;
    }
    
    public String getPackageName(){
        if(null == packageName) {
            packageName = DEFAULT_PACKAGE_NAME;
        }
        return packageName;
    }

    public WebServiceDescriptor getJaxWsDescriptor() {
        return jaxWsDescriptor;
    }

    public void setJaxWsDescriptor(WebServiceDescriptor jaxWsDescriptor) {
        this.jaxWsDescriptor = jaxWsDescriptor;
    }

    public WebServiceDescriptor getJaxRpcDescriptor() {
        return jaxRpcDescriptor;
    }

    public void setJaxRpcDescriptor(WebServiceDescriptor jaxRpcDescriptor) {
        this.jaxRpcDescriptor = jaxRpcDescriptor;
    }

    public String getJaxWsDescriptorPath() {
        return jaxWsDescriptorPath;
    }

    public void setJaxWsDescriptorPath(String jaxWsDescriptorPath) {
        this.jaxWsDescriptorPath = jaxWsDescriptorPath;
    }

    public String getJaxRpcDescriptorPath() {
        return jaxRpcDescriptorPath;
    }

    public void setJaxRpcDescriptorPath(String jaxRpcDescriptorPath) {
        this.jaxRpcDescriptorPath = jaxRpcDescriptorPath;
    }
    
    /**
     * Partial Fix for Bug: 5107518
     * Changed so the web services will only be persisted if there is a change.
     * - David Botterill 9/29/2004
     */
    private void setModelDirty() {
        WebServiceListModel.setDirty(true);
    }
    
    public boolean isJaxRpcEnabled() {
        return jaxRpcEnabled;
    }
    
    public boolean isJaxWsEnabled() {
        return jaxWsEnabled;
    }
    
    public void setJaxRpcEnabled(boolean b) {
        jaxRpcEnabled = b;
    }
    
    public void setJaxWsEnabled(boolean b) {
        jaxWsEnabled = b;
    }
    
    public boolean isCompiled() {
        return compiled;
    }

    public void setCompiled(boolean compiled) {
        boolean fireEvent = false;
        if (this.compiled == false && compiled == true) fireEvent = true;
        this.compiled = compiled;
        
        if (fireEvent) {
            for (WebServiceDataListener listener : listeners) {
                listener.webServiceCompiled(new WebServiceDataEvent(this));
            }
        }
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void addWebServiceDataListener(WebServiceDataListener listener) {
        listeners.add(listener);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyListeners.add(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyListeners.remove(listener);
    }
}
