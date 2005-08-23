/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2005 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.apisupport.project.ui.customizer;

import java.io.IOException;
import java.lang.IllegalStateException;
import org.netbeans.modules.apisupport.project.suite.BrandingSupport;
import org.netbeans.modules.apisupport.project.suite.SuiteProject;
import org.openide.ErrorManager;
import org.openide.util.NbBundle;

/**
 *
 * @author Radek Matous
 */
public class BasicBrandingModel {
    private String name;
    private String title;
    private boolean brandingEnabled;
    private BrandingSupport branding;
    private SuiteProperties suiteProps;
    
    private BrandingSupport.BundleKey productInformation = null;
    private BrandingSupport.BundleKey mainWindowTitle = null;
    private BrandingSupport.BundleKey mainWindowTitleNoProject = null;
    private BrandingSupport.BundleKey currentVersion = null;
    
    public static final String NAME_PROPERTY = "app.name";//NOI18N
    public static final String TITLE_PROPERTY = "app.title";//NOI18N
    public static final String BRANDING_TOKEN_PROPERTY = "branding.token";//NOI18N
    
    /** Creates a new instance of ApplicationDetails */
    public BasicBrandingModel(final SuiteProperties suiteProps) {
        this.suiteProps = suiteProps;
        init();
    }
    
    public boolean isBrandingEnabled() {
        return brandingEnabled;
    }
    
    public void setBrandingEnabled(boolean brandingEnabled) {
        this.brandingEnabled = brandingEnabled;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) /*throws IllegalArgumentException*/ {
        /*if (name != null && !name.matches("[a-z][a-z0-9]*(_[a-z][a-z0-9]*)*")) { // NOI18N
            throw new IllegalArgumentException("Malformed name: " + name); // NOI18N
        }*/
        
        this.name = name;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
        productInformation.setValue(title);
        mainWindowTitle.setValue(title+ " {0}");//NOI18N
        mainWindowTitleNoProject.setValue(title+ " {0}");//NOI18N
        currentVersion.setValue(title+ " {0}");//NOI18N
    }
    
    
    public void store() throws IOException {
        if (brandingEnabled) {
            suiteProps.setProperty(NAME_PROPERTY, getName());
            suiteProps.setProperty(TITLE_PROPERTY, getTitle());
            suiteProps.setProperty(BRANDING_TOKEN_PROPERTY, "${" + NAME_PROPERTY + "}");
            
            getBranding().brandBundleKey(productInformation);
            getBranding().brandBundleKey(mainWindowTitle);
            getBranding().brandBundleKey(mainWindowTitleNoProject);
            getBranding().brandBundleKey(currentVersion);
        } else {
            suiteProps.removeProperty(BRANDING_TOKEN_PROPERTY);
        }
    }
    
    private BrandingSupport getBranding() {
        if (branding == null) {
            try {
                branding = BrandingSupport.getInstance((SuiteProject)suiteProps.getProject());
            } catch (IOException ex) {
                ErrorManager.getDefault().notify(ex);
                throw new IllegalStateException(ex.getLocalizedMessage());
            }
        }
        return branding;
    }
    
    private void init() {
        initBundleKeys();
        initName();
        initTitle();
        brandingEnabled = (suiteProps.getProperty(BRANDING_TOKEN_PROPERTY) != null);
        
        /*try {
            store();
        } catch (IOException ex) {
            ErrorManager.getDefault().notify(ex);
        }*/
    }
    
    private void initName()  {
        if (name == null) {
            name = suiteProps.getProperty(NAME_PROPERTY);
        }
        
        if (name == null) {
            name = NbBundle.getBundle(getClass()).getString("APP_DefaultName");
        }
        
        assert name != null;
    }
    
    private void initTitle()  {
        if (title == null) {
            String initTitle = suiteProps.getProperty(TITLE_PROPERTY);
            
            if (initTitle == null) {
                initTitle = NbBundle.getBundle(getClass()).getString("APP_Title");
            }
            assert initTitle != null;
            setTitle(initTitle);
        }
    }
    
    private void initBundleKeys() {
        productInformation = getBranding().getBundleKey(
                "org.netbeans.core",
                "org/netbeans/core/ui/Bundle.properties" ,
                "LBL_ProductInformation");//NOI18N
        assert productInformation != null;
        
        mainWindowTitle = getBranding().getBundleKey(
                "org.netbeans.core.windows",
                "org/netbeans/core/windows/view/ui/Bundle.properties",
                "CTL_MainWindow_Title");//NOI18N
        assert mainWindowTitle != null;
        
        mainWindowTitleNoProject = getBranding().getBundleKey(
                "org.netbeans.core.windows",
                "org/netbeans/core/windows/view/ui/Bundle.properties",
                "CTL_MainWindow_Title_No_Project");//NOI18N
        assert mainWindowTitleNoProject != null;
        
        currentVersion = getBranding().getBundleKey(
                "org.netbeans.core.startup",
                "org/netbeans/core/startup/Bundle.properties",
                "currentVersion");//NOI18N
        assert currentVersion != null;
    }
}
