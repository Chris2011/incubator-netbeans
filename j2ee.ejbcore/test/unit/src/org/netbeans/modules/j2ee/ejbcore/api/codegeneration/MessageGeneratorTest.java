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

package org.netbeans.modules.j2ee.ejbcore.api.codegeneration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.modules.j2ee.dd.api.common.VersionNotSupportedException;
import org.netbeans.modules.j2ee.dd.api.ejb.ActivationConfig;
import org.netbeans.modules.j2ee.dd.api.ejb.ActivationConfigProperty;
import org.netbeans.modules.j2ee.dd.api.ejb.AssemblyDescriptor;
import org.netbeans.modules.j2ee.dd.api.ejb.DDProvider;
import org.netbeans.modules.j2ee.dd.api.ejb.EjbJar;
import org.netbeans.modules.j2ee.dd.api.ejb.EnterpriseBeans;
import org.netbeans.modules.j2ee.dd.api.ejb.MessageDriven;
import org.netbeans.modules.j2ee.deployment.common.api.MessageDestination;
import org.netbeans.modules.j2ee.ejbcore.test.TestBase;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author Martin Adamek
 */
public class MessageGeneratorTest extends TestBase {
    
    public MessageGeneratorTest(String testName) {
        super(testName);
    }
    
    public void testGenerateJavaEE14() throws IOException, VersionNotSupportedException {
        TestModule testModule = createEjb21Module();
        FileObject sourceRoot = testModule.getSources()[0];
        FileObject packageFileObject = sourceRoot.getFileObject("testGenerateJavaEE14");
        if (packageFileObject != null) {
            packageFileObject.delete();
        }
        packageFileObject = sourceRoot.createFolder("testGenerateJavaEE14");

        // Queue based MessageDriven EJB in Java EE 1.4
        
        MessageDestination messageDestination = new MessageDestinationImpl("TestMDBQueue", MessageDestination.Type.QUEUE);
        MessageGenerator generator = new MessageGenerator("TestMDBQueueBean", packageFileObject, messageDestination, false, Collections.<String, String>emptyMap(), true);
        generator.generate();
        
        EjbJar ejbJar = DDProvider.getDefault().getDDRoot(testModule.getDeploymentDescriptor());
        EnterpriseBeans enterpriseBeans = ejbJar.getEnterpriseBeans();
        MessageDriven messageDriven = (MessageDriven) enterpriseBeans.findBeanByName(
                EnterpriseBeans.MESSAGE_DRIVEN, MessageDriven.EJB_NAME, "TestMDBQueueBean");
        assertNotNull(messageDriven);
        assertEquals("TestMDBQueueBeanMDB", messageDriven.getDefaultDisplayName());
        assertEquals("TestMDBQueueBean", messageDriven.getEjbName());
        assertEquals("testGenerateJavaEE14.TestMDBQueueBean", messageDriven.getEjbClass());
        assertEquals("Container", messageDriven.getTransactionType());
        assertEquals("javax.jms.Queue", messageDriven.getMessageDestinationType());
        assertEquals("TestMDBQueue", messageDriven.getMessageDestinationLink());
        ActivationConfig activationConfig = messageDriven.getActivationConfig();
        assertEquals(2, activationConfig.getActivationConfigProperty().length);
        ActivationConfigProperty acProperty = activationConfig.getActivationConfigProperty()[0];
        assertEquals("acknowledgeMode", acProperty.getActivationConfigPropertyName());
        assertEquals("Auto-acknowledge", acProperty.getActivationConfigPropertyValue());
        acProperty = activationConfig.getActivationConfigProperty()[1];
        assertEquals("destinationType", acProperty.getActivationConfigPropertyName());
        assertEquals("javax.jms.Queue", acProperty.getActivationConfigPropertyValue());
        assertFile(
                FileUtil.toFile(packageFileObject.getFileObject("TestMDBQueueBean.java")), 
                getGoldenFile("testGenerateJavaEE14/TestMDBQueueBean.java"), 
                FileUtil.toFile(packageFileObject)
                );

        // Topic based MessageDriven EJB in Java EE 1.4
        
        messageDestination = new MessageDestinationImpl("TestMDBTopic", MessageDestination.Type.TOPIC);
        generator = new MessageGenerator("TestMDBTopicBean", packageFileObject, messageDestination, false, Collections.<String, String>emptyMap(), true);
        generator.generate();
        
        messageDriven = (MessageDriven) enterpriseBeans.findBeanByName(
                EnterpriseBeans.MESSAGE_DRIVEN, MessageDriven.EJB_NAME, "TestMDBTopicBean");
        assertNotNull(messageDriven);
        assertEquals("TestMDBTopicBeanMDB", messageDriven.getDefaultDisplayName());
        assertEquals("TestMDBTopicBean", messageDriven.getEjbName());
        assertEquals("testGenerateJavaEE14.TestMDBTopicBean", messageDriven.getEjbClass());
        assertEquals("Container", messageDriven.getTransactionType());
        assertEquals("javax.jms.Topic", messageDriven.getMessageDestinationType());
        assertEquals("TestMDBTopic", messageDriven.getMessageDestinationLink());
        activationConfig = messageDriven.getActivationConfig();
        assertEquals(5, activationConfig.getActivationConfigProperty().length);
        acProperty = activationConfig.getActivationConfigProperty()[0];
        assertEquals("acknowledgeMode", acProperty.getActivationConfigPropertyName());
        assertEquals("Auto-acknowledge", acProperty.getActivationConfigPropertyValue());
        acProperty = activationConfig.getActivationConfigProperty()[1];
        assertEquals("subscriptionDurability", acProperty.getActivationConfigPropertyName());
        assertEquals("Durable", acProperty.getActivationConfigPropertyValue());
        acProperty = activationConfig.getActivationConfigProperty()[2];
        assertEquals("clientId", acProperty.getActivationConfigPropertyName());
        assertEquals("TestMDBTopicBean", acProperty.getActivationConfigPropertyValue());
        acProperty = activationConfig.getActivationConfigProperty()[3];
        assertEquals("subscriptionName", acProperty.getActivationConfigPropertyName());
        assertEquals("TestMDBTopicBean", acProperty.getActivationConfigPropertyValue());
        acProperty = activationConfig.getActivationConfigProperty()[4];
        assertEquals("destinationType", acProperty.getActivationConfigPropertyName());
        assertEquals("javax.jms.Topic", acProperty.getActivationConfigPropertyValue());
        assertFile(
                FileUtil.toFile(packageFileObject.getFileObject("TestMDBTopicBean.java")), 
                getGoldenFile("testGenerateJavaEE14/TestMDBTopicBean.java"), 
                FileUtil.toFile(packageFileObject)
                );

        // added by both previous generators
        
        AssemblyDescriptor assemblyDescriptor = ejbJar.getSingleAssemblyDescriptor();
        List<String> messageDestinationNames = new ArrayList<String>();
        for (org.netbeans.modules.j2ee.dd.api.common.MessageDestination msgDest : assemblyDescriptor.getMessageDestination()) {
            messageDestinationNames.add(msgDest.getMessageDestinationName());
        }
        assertEquals(2, assemblyDescriptor.getMessageDestination().length);
        assertTrue(messageDestinationNames.contains("TestMDBQueue"));
        assertTrue(messageDestinationNames.contains("TestMDBTopic"));
    }
    
    public void testGenerateJavaEE50() throws IOException {
        TestModule testModule = createEjb30Module();
        FileObject sourceRoot = testModule.getSources()[0];
        FileObject packageFileObject = sourceRoot.getFileObject("testGenerateJavaEE50");
        if (packageFileObject != null) {
            packageFileObject.delete();
        }
        packageFileObject = sourceRoot.createFolder("testGenerateJavaEE50");
        
        // Queue based MessageDriven EJB in Java EE 5 defined in annotation
        
        MessageDestination messageDestination = new MessageDestinationImpl("TestMessageDestination", MessageDestination.Type.QUEUE);
        MessageGenerator generator = new MessageGenerator("TestMDBQueueBean", packageFileObject, messageDestination, true, Collections.<String, String>emptyMap(), true);
        generator.generate();
        
        assertFile(
                FileUtil.toFile(packageFileObject.getFileObject("TestMDBQueueBean.java")), 
                getGoldenFile("testGenerateJavaEE50/TestMDBQueueBean.java"), 
                FileUtil.toFile(packageFileObject)
                );

        // Topic based MessageDriven EJB in Java EE 5 defined in annotation
        
        messageDestination = new MessageDestinationImpl("TestMessageDestination", MessageDestination.Type.TOPIC);
        generator = new MessageGenerator("TestMDBTopic", packageFileObject, messageDestination, true, Collections.<String, String>emptyMap(), true);
        generator.generate();
        
        assertFile(
                FileUtil.toFile(packageFileObject.getFileObject("TestMDBQueueBean.java")), 
                getGoldenFile("testGenerateJavaEE50/TestMDBQueueBean.java"), 
                FileUtil.toFile(packageFileObject)
                );
    }

    private static final class MessageDestinationImpl implements MessageDestination {

        private final String name;
        private final MessageDestination.Type type;
        
        public MessageDestinationImpl(String name, MessageDestination.Type type) {
            this.name = name;
            this.type = type;
        }
        
        public String getName() {
            return name;
        }

        public Type getType() {
            return type;
        }
        
    }
    
}
