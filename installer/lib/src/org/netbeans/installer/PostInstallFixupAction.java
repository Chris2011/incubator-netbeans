/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2004 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.installer;

import com.installshield.product.ProductAction;
import com.installshield.product.ProductActionSupport;
import com.installshield.product.ProductBuilderSupport;
import com.installshield.product.ProductException;
import com.installshield.product.service.product.ProductService;
import com.installshield.util.Log;
import com.installshield.util.ProcessExec;
import com.installshield.util.ProcessExecException;
import com.installshield.util.ProcessOutputHandler;
import com.installshield.wizard.platform.win32.Win32RegistryService;
import com.installshield.wizard.service.ServiceException;
import com.installshield.wizard.service.file.FileService;
import com.installshield.wizard.service.system.SystemUtilService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PostInstallFixupAction extends ProductAction {
    
    private String productURL = ProductService.DEFAULT_PRODUCT_SOURCE;
    
    private String nbInstallDir = null;
    private String rootInstallDir = null;
    private String binDir = null;
    private String configDir = null;
    private String uninstallDir = null;
    
    private String jdkHome = null;
    
    private FileService fileService;
    private String psep;
    private String sep;
    
    
    public void build(ProductBuilderSupport support) {
        try {
            support.putClass(Util.class.getName());
            support.putClass("org.netbeans.installer.PostInstallFixupAction$LnHandleOutput");
            support.putRequiredService(FileService.NAME);
            support.putRequiredService(ProductService.NAME);
            support.putRequiredService(Win32RegistryService.NAME);
        }
        catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    public void init(ProductActionSupport support) throws ProductException {
        try {
            // need to get absoluteInstallLocation because uninstaller doesn't know the system properties.
            fileService = (FileService)getServices().getService(FileService.NAME);
            ProductService pservice = (ProductService)getService(ProductService.NAME);
            
            psep = fileService.getPathSeparator();
            sep = fileService.getSeparator();
            rootInstallDir = resolveString((String)pservice.getProductBeanProperty(productURL,null,"absoluteInstallLocation"));
            if (Util.isMacOSX()) {
                nbInstallDir = rootInstallDir + sep 
                + resolveString("$L(org.netbeans.installer.Bundle,Product.nbLocationBelowInstallRoot)");
            } else {
                nbInstallDir = rootInstallDir;
            }
        }
        catch (Exception e) {
            logEvent(this, Log.ERROR, e);
            nbInstallDir = Util.getNbInstallDir();
        }
        
        logEvent(this, Log.DBG,"nbInstallDir is " + nbInstallDir);
        
        binDir = nbInstallDir + sep + "bin";
        configDir = nbInstallDir + sep + "etc";
        uninstallDir = rootInstallDir + sep + "_uninst";
    }
    
    public void install(ProductActionSupport support) throws ProductException {
        logEvent(this, Log.DBG,"install, support is " + support +" ...");
        
        init(support);
        // XXX - Watch out for this with detecting previous jdk on Windows
        // only needed for installs, not uninstalls
        String tmpJdkHome = Util.getInstalledJdk();
        if (tmpJdkHome != null && tmpJdkHome.length() > 0) {
            jdkHome = tmpJdkHome;
        } else {
            jdkHome = Util.getJdkHome();
        }
        logEvent(this, Log.DBG, "jdkHome = " + jdkHome);
        
        //This file is used by IDE Update Center.
        String productID = Util.getStringPropertyValue("ProductID");
        if (productID != null) {
            try {
                String fileName = nbInstallDir + sep + "nb4.1" + sep + "config" + sep + "productid";
                logEvent(this, Log.DBG, "create file: " + fileName + " content: '" + productID + "'");
                fileService.createAsciiFile(fileName,new String[] { productID });
            }
            catch (Exception ex) {
                logEvent(this, Log.ERROR, ex);
            }
        }
        
        installIDEConfigFile();
        
        if (Util.isMacOSX()) {
            createSymbolicLink();
        }
        if (Util.isUnixOS()) {
            installGnomeIcon();
        }
        
        deleteUnusedFiles();
    }
    
    public void uninstall(ProductActionSupport support) throws ProductException {
        logEvent(this, Log.DBG,"uninstall, support is " + support +" ...");
        
        try {
            init(support);
            
            deleteFiles(nbInstallDir, new String[] {"etc" + sep + "netbeans.conf"});
            deleteFiles(uninstallDir, new String[] {"install.log"});
            deleteFiles(nbInstallDir, new String[] {"nb4.1" + sep + "config" + sep + "productid" });
            
            if (Util.isMacOSX()) {
                deleteSymbolicLink();
            }
            if (Util.isUnixOS()) {
                uninstallGnomeIcon();
            }
            
            logEvent(this, Log.DBG, "uninstall Delete install dir on exit: " + rootInstallDir);
            SystemUtilService systemUtilService = (SystemUtilService) getServices().getService(SystemUtilService.NAME);
            systemUtilService.deleteDirectoryOnExit(rootInstallDir,false);
        }
        catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    public void replace(ProductAction oldAction, ProductActionSupport support) throws ProductException {
        logEvent(this, Log.DBG,"replace, oldAction is " + oldAction +", support is " + support + " ...");
        
        // TODO might be modify config file
    }

    /** Create symbolic link for NetBeans start script on Mac OS X. */
    private void createSymbolicLink () {
        // Create the command to exec by looking for ln in /bin and /usr/bin
        String target = "../Resources/NetBeans/bin/netbeans";
        String linkName = rootInstallDir + sep + "Contents/MacOS/netbeans";
        try {
            String command = null;
            String[] args = { "-s", target, linkName };
            if (fileService.fileExists("/bin/ln")) {
                command = "/bin/ln";
            } else if (fileService.fileExists("/usr/bin/ln")) {
                command = "/usr/bin/ln";
            } else {
                logEvent(this, Log.ERROR, "Cannot find 'ln' command. => Cannot create symbolic link.");
                return;
            }

            logEvent(this, Log.DBG, "Running Symbolic Link Command: " + command);

            // exec the process
            ProcessExec ps = new ProcessExec(command, args);
            ps.setProcessOutputHandler(new LnHandleOutput());
            try {
                ps.executeProcess();
            } catch (ProcessExecException pe) {
                logEvent(this, Log.ERROR, "Could not exec ln process.");
            }
            logEvent(this, Log.DBG, "Symbolic link created Target: " + target
            + " Link: " + linkName);
        } catch (ServiceException se) {
            logEvent(this, Log.ERROR, se);
        }
    }
    
    private void deleteSymbolicLink () {
        String linkName = rootInstallDir + sep + "Contents/MacOS/netbeans";
        try {
            if (fileService.fileExists(linkName)) {
                fileService.deleteFile(linkName);
            }
        } catch (ServiceException se) {
            logEvent(this, Log.ERROR, "Cannot delete symbolic link: " + linkName);
            logEvent(this, Log.ERROR, se);
        }
        logEvent(this, Log.DBG, "Symbolic link deleted Link: " + linkName);
    }
    
    public void installIDEConfigFile() {
        
        try {
            String configFilename = configDir + sep + "netbeans.conf";
            logEvent(this, Log.DBG, "patching " + configFilename);
            
            int whereIsComment = -1;
            int whereToReplace = -1;
            String newLine = "netbeans_jdkhome=\"" + jdkHome + "\"";
            
            String[] content = fileService.readAsciiFile(configFilename);
            if (content != null ) {
                for (int i = 0; i < content.length; i++) {
                    if (content[i].trim().startsWith("netbeans_jdkhome")) {
                        whereToReplace = i;
                        break;
                    }
                    if (content[i].trim().startsWith("#netbeans_jdkhome")) {
                        whereIsComment = i;
                    }
                }
            }
            
            if (whereToReplace < 0) {
                logEvent(this, Log.DBG, "not found netbeans_jdkhome");
                
                if (whereIsComment >= 0) {
                    fileService.updateAsciiFile(configFilename, new String[] {newLine}, whereIsComment);
                }
                else {
                    fileService.appendToAsciiFile(configFilename, new String[] {newLine});
                }
                return;
            }
            
            String line = content[whereToReplace].trim();
            logEvent(this, Log.DBG, "replace line "+line);
            fileService.updateAsciiFile(configFilename, new String[] {newLine}, whereToReplace);
        }
        catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    /** Delete unneeded files after installation.
     */
    public void deleteUnusedFiles() {
        try {
            fileService.deleteFile(rootInstallDir + sep + "netbeans.desktop");
            
            String[] binFiles = new String [20];
            int i = -1;
            if (!Util.isWindowsOS()) {
                binFiles[++i] = "nb.exe";
                binFiles[++i] = "netbeans.exe";
            }
            if (!Util.isOS2OS()) {
                binFiles[++i] = "netbeans.cmd";
            }
            if (!Util.isOpenVMSOS())
                binFiles[++i] = "runideopenvms.com";
            if (!Util.isMacOSX())
                binFiles[++i] = "macosx_launcher.dmg";
            if (!Util.isOS2OS())
                binFiles[++i] = "runideos2.cmd";
            if (Util.isWindowsOS() || Util.isOpenVMSOS() || Util.isOS2OS()) {
                binFiles[++i] = "netbeans";
            }
            binFiles[++i] = null;
            deleteFiles(binDir, binFiles);
            
            if (Util.isWindowsOS()) {
                deleteFiles(nbInstallDir + sep + "_uninst", new String[] {"nb-uninstall.template"});
            }
        }
        catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    public void deleteFiles(String dir, String[] fileNames) {
        for (int i=0; i< fileNames.length; i++) {
            if (fileNames[i] == null)  //array bigger than num objs in array.
                return;
            String filename =dir + sep + fileNames[i];
            try {
                if (fileService.fileExists(filename)) {
                    logEvent(this, Log.DBG, "deleting " + filename);
                    fileService.deleteFile(filename);
                }
                else {
                    logEvent(this, Log.DBG, "cannot find " + filename);
                }
            }
            catch (ServiceException ex) {
                logEvent(this, Log.ERROR, ex);
            }
        }
    }
    
    private static final String GNOMEAPPDIR = "/usr/share/applications";
    
    void installGnomeIcon() {
        java.io.File appdir = new java.io.File(GNOMEAPPDIR);
        
        if (!appdir.exists())
            return;
        
        try {
            String icondir = null;
            
            if (appdir.canWrite()) {
                icondir = GNOMEAPPDIR;
            } else {
                String desktopdir = resolveString("$D(home)$J(file.separator)Desktop");
                if (! fileService.fileExists(desktopdir)) {
                    fileService.createDirectory(desktopdir);
                }
                icondir = desktopdir;
            }
            
            String iconfile = icondir + sep + "netbeans.desktop";
            
            fileService.copyFile(nbInstallDir + sep + "netbeans.desktop", iconfile, true);
            
            String[] content = fileService.readAsciiFile(iconfile);
            if (content == null )
                return;
            
            for (int i = 0; i < content.length; i++) {
                content[i] = content[i].replaceAll("@absoluteInstallLocation@", nbInstallDir);
            }
            
            fileService.updateAsciiFile(iconfile, content, 0);
        } catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
        }
    }

    void uninstallGnomeIcon() {
        java.io.File appdir = new java.io.File(GNOMEAPPDIR);
        
        if (!appdir.exists())
            return;
        
        try {
            String icondir = null;
            
            if (appdir.canWrite()) {
                icondir = GNOMEAPPDIR;
            } else {
                icondir = resolveString("$D(home)$J(file.separator)Desktop");
            }
            
            String iconfile = icondir + sep + "netbeans.desktop";
            
            if (fileService.fileExists(iconfile)) {
                fileService.deleteFile(iconfile);
            }
        } catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    /**
     * Inner helper class which performs output handling for the ISMP ProcessExec Class
     */
    private class LnHandleOutput implements ProcessOutputHandler {
        public void processOutputData(java.io.InputStream ipStream) {

        }
        public void processErrorData(java.io.InputStream ipStream){
            try {

                InputStreamReader isr = new InputStreamReader(ipStream);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    logEvent(this, Log.WARNING, line);
                }

            } catch (IOException ioe) {
                PostInstallFixupAction.this.logEvent(this, Log.ERROR, "Reading of ln output failed");
            }

        }
    }
}
