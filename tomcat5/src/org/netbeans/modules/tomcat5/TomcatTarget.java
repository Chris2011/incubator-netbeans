/*
 *                 Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2003 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.tomcat5;

import java.io.InputStream;
import java.io.OutputStream;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.spi.DConfigBeanRoot;
import javax.enterprise.deploy.spi.DeploymentConfiguration;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.exceptions.BeanNotFoundException;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;

/** Dummy implementation of target for Tomcat 5 server
 *
 * @author  Radim Kubacki
 */
public class TomcatTarget implements Target {
    
    private String name;
    
    private String desc;
    
    public TomcatTarget (String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
    
    public String getName () {
        return name;
    }
    
    public String getDescription () {
        return desc;
    }
    
}
