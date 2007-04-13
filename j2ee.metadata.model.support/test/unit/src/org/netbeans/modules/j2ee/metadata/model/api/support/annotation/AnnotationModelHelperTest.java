/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.j2ee.metadata.model.api.support.annotation;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Callable;
import javax.lang.model.element.TypeElement;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.modules.j2ee.metadata.model.support.TestUtilities;
import org.netbeans.modules.j2ee.metadata.model.support.PersistenceTestCase;
import org.netbeans.modules.java.source.usages.RepositoryUpdater;

/**
 *
 * @author Andrei Badea
 */
public class AnnotationModelHelperTest extends PersistenceTestCase {

    public AnnotationModelHelperTest(String testName) {
        super(testName);
    }

    public void testUserActionTask() throws Exception {
        RepositoryUpdater.getDefault().scheduleCompilationAndWait(srcFO, srcFO).await();
        ClasspathInfo cpi = ClasspathInfo.create(srcFO);
        final AnnotationModelHelper helper = AnnotationModelHelper.create(cpi);
        final String expected = "foo";
        String returned = helper.userActionTask(new Callable<String>() {
            public String call() throws Exception {
                assertNotNull(helper.getCompilationController());
                return expected;
            }
        });
        assertEquals(expected, returned);
    }

    public void testGetSuperclasses() throws Exception {
        TestUtilities.copyStringToFileObject(srcFO, "Person.java",
                "public interface Person {" +
                "   String getName();" +
                "}");
        TestUtilities.copyStringToFileObject(srcFO, "Employee.java",
                "@javax.persistence.MappedSuperclass()" +
                "public class Employee implements Person {" +
                "   public String getName() {" +
                "       return null;" +
                "   }" +
                "}");
        TestUtilities.copyStringToFileObject(srcFO, "PartTimeEmployee.java",
                "@javax.persistence.Entity()" +
                "public class PartTimeEmployee extends Employee {" +
                "   public int getHoursPerDay() {" +
                "       return 0;" +
                "   }" +
                "}");
        RepositoryUpdater.getDefault().scheduleCompilationAndWait(srcFO, srcFO).await();
        ClasspathInfo cpi = ClasspathInfo.create(srcFO);
        final AnnotationModelHelper helper = AnnotationModelHelper.create(cpi);
        helper.userActionTask(new Runnable() {
            public void run() {
                TypeElement pte = helper.getCompilationController().getElements().getTypeElement("PartTimeEmployee");
                List<? extends TypeElement> superclasses = helper.getSuperclasses(pte);
                assertEquals(1, superclasses.size());
                assertTrue(superclasses.get(0).getQualifiedName().contentEquals("Employee"));
            }
        });
    }

    public void testJavaContextListener() throws Exception {
        RepositoryUpdater.getDefault().scheduleCompilationAndWait(srcFO, srcFO).await();
        ClasspathInfo cpi = ClasspathInfo.create(srcFO);
        final AnnotationModelHelper helper = AnnotationModelHelper.create(cpi);
        final boolean[] contextLeft = { false };
        JavaContextListener listener = new JavaContextListener() {
            public void javaContextLeft() {
                contextLeft[0] = true;
            }
        };
        helper.addJavaContextListener(listener);
        Runnable empty = new Runnable() {
            public void run() {}
        };
        helper.userActionTask(empty);
        assertTrue(contextLeft[0]);
        contextLeft[0] = false;
        helper.userActionTask(empty);
        assertTrue(contextLeft[0]);
        WeakReference<JavaContextListener> listenerRef = new WeakReference<JavaContextListener>(listener);
        listener = null;
        assertGC("Should be possible to GC listener", listenerRef);
    }
}
