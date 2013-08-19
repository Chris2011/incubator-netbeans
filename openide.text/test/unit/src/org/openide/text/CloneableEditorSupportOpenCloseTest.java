/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.openide.text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import org.netbeans.junit.Filter;
import org.netbeans.junit.NbTestCase;
import org.openide.cookies.EditorCookie;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Miloslav Metelka
 */
public class CloneableEditorSupportOpenCloseTest extends NbTestCase
implements CloneableEditorSupport.Env {
    
    private CloneableEditorSupport support;
    /** the content of lookup of support */
    private InstanceContent ic;
    /** Delay in miliseconds to simulate delay between closing stream and file modification time. */
    private long delay = 0L;

    // Env variables
    private String content = "";
    private boolean valid = true;
    private boolean modified = false;
    /** if not null contains message why this document cannot be modified */
    private String cannotBeModified;
    private java.util.Date date = new java.util.Date ();
    private java.util.List<java.beans.PropertyChangeListener> propL = new ArrayList<PropertyChangeListener>();
    private java.beans.VetoableChangeListener vetoL;

    public CloneableEditorSupportOpenCloseTest(String name) {
        super(name);
        List<String> includes = new ArrayList<String>();
//        includes.add("testOpenFromOpenNotification");
//        includes.add("testCloseWhileOpening");
//        filterTests(includes);
    }

    private void filterTests(List<String> includeTestNames) {
        List<Filter.IncludeExclude> includeTests = new ArrayList<Filter.IncludeExclude>();
        for (String testName : includeTestNames) {
            includeTests.add(new Filter.IncludeExclude(testName, ""));
        }
        Filter filter = new Filter();
        filter.setIncludes(includeTests.toArray(new Filter.IncludeExclude[includeTests.size()]));
        setFilter(filter);
    }

    @Override
    protected void setUp () {
        ic = new InstanceContent ();
        support = new CES (this, new AbstractLookup (ic));
    }
    
    public void testOpenFromOpenNotification() throws Exception {
        content = "Ahoj\nMyDoc";
        final CountDownLatch latch1 = new CountDownLatch(1);
        support.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (EditorCookie.Observable.PROP_DOCUMENT.equals(evt.getPropertyName())) {
                    try {
                        if (evt.getNewValue() != null) { // Document opened
                            support.openDocument();
                            latch1.countDown();
                        }
                    } catch (IOException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
            }
        });
        support.openDocument();

        latch1.await();
    }

    public void testCloseWhileOpening() throws Exception {
        content = "Ahoj\nMyDoc";
        final CountDownLatch latch1 = new CountDownLatch(1);
        final CountDownLatch latch2 = new CountDownLatch(2);
        support.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (latch2.getCount() == 0) {
                    return;
                }
                if (EditorCookie.Observable.PROP_DOCUMENT.equals(evt.getPropertyName())) {
                    try {
                        if (evt.getNewValue() != null) {
                            latch1.countDown();
                            Thread.sleep(100);//XXX: hack we need the support.openDocument to start waiting
                            support.openDocument();
                            latch2.countDown();
                        } else {
                            support.openDocument();
                            latch2.countDown();
                        }
                    } catch (IOException ex) {
                        throw new IllegalStateException(ex);
                    } catch (InterruptedException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
            }
        });
        support.openDocument();

        latch1.await();
        support.close();
        latch2.await();
        Thread.sleep(5);
    }

    //
    // Implementation of the CloneableEditorSupport.Env
    //
    
    public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener l) {
        propL.add (l);
    }    
    public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener l) {
        propL.remove (l);
    }
    
    public synchronized void addVetoableChangeListener(java.beans.VetoableChangeListener l) {
        assertNull ("This is the first veto listener", vetoL);
        vetoL = l;
    }
    public void removeVetoableChangeListener(java.beans.VetoableChangeListener l) {
        assertEquals ("Removing the right veto one", vetoL, l);
        vetoL = null;
    }
    
    public org.openide.windows.CloneableOpenSupport findCloneableOpenSupport() {
        return support;
    }
    
    public String getMimeType() {
        return "text/plain";
    }
    
    public java.util.Date getTime() {
        return date;
    }
    
    public java.io.InputStream inputStream() throws java.io.IOException {
        return new java.io.ByteArrayInputStream (content.getBytes ());
    }
    public java.io.OutputStream outputStream() throws java.io.IOException {
        class ContentStream extends java.io.ByteArrayOutputStream {
            @Override
            public void close() throws java.io.IOException {
                super.close ();
                content = new String (toByteArray ());
                date = new Date(System.currentTimeMillis() + delay);
            }
        }
        
        return new ContentStream ();
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public boolean isModified() {
        return modified;
    }

    public void markModified() throws java.io.IOException {
        if (cannotBeModified != null) {
            final String notify = cannotBeModified;
            IOException e = new IOException () {
                public String getLocalizedMessage () {
                    return notify;
                }
            };
            Exceptions.attachLocalizedMessage(e, cannotBeModified);
            throw e;
        }
        
        modified = true;
    }
    
    public void unmarkModified() {
        modified = false;
    }

    /** Implementation of the CES */
    private static final class CES extends CloneableEditorSupport {
        public CES (CloneableEditorSupport.Env env, Lookup l) {
            super (env, l);
        }
        
        protected String messageName() {
            return "Name";
        }
        
        protected String messageOpened() {
            return "Opened";
        }
        
        protected String messageOpening() {
            return "Opening";
        }
        
        protected String messageSave() {
            return "Save";
        }
        
        protected String messageToolTip() {
            return "ToolTip";
        }
        
    }
}
