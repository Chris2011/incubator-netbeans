/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance
 * with the License.
 * 
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html or
 * http://www.netbeans.org/cddl.txt.
 * 
 * When distributing Covered Code, include this CDDL Header Notice in each file and
 * include the License file at http://www.netbeans.org/cddl.txt. If applicable, add
 * the following below the CDDL Header, with the fields enclosed by brackets []
 * replaced by your own identifying information:
 * 
 *     "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original Software
 * is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun Microsystems, Inc. All
 * Rights Reserved.
 */

package org.netbeans.installer.downloader.queue;

import org.netbeans.installer.downloader.Pumping;
import org.netbeans.installer.downloader.PumpingsQueue;
import org.netbeans.installer.downloader.DownloadListener;
import org.netbeans.installer.downloader.impl.PumpingImpl;
import org.netbeans.installer.utils.LogManager;
import org.netbeans.installer.utils.exceptions.ParseException;
import org.netbeans.installer.utils.exceptions.UnexpectedExceptionError;
import org.netbeans.installer.utils.xml.DomUtil;
import org.netbeans.installer.utils.xml.visitors.DomVisitor;
import org.netbeans.installer.utils.xml.visitors.RecursiveDomVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Danila_Dugurov
 */

public abstract class QueueBase implements PumpingsQueue {
    
    /////////////////////////////////////////////////////////////////////////////////
    // Static
    /**
     * @noinspection unchecked
     */
    private static final WeakReference<DownloadListener>[] EMPTY_ARRAY = new WeakReference[0];
    
    /////////////////////////////////////////////////////////////////////////////////
    // Instance
    private final List<WeakReference<DownloadListener>> listeners;
    
    protected final Map<String, PumpingImpl> id2Pumping = new HashMap<String, PumpingImpl>();
    
    protected File stateFile;
    
    protected QueueBase(File stateFile) {
        this.stateFile = stateFile;
        if (stateFile.exists()) {
            load();
            LogManager.log("queue state was load from file: " + stateFile);
        } else LogManager.log("file not exist, queue is empty!");
        listeners = new ArrayList<WeakReference<DownloadListener>>(3);
    }
    
    public synchronized void addListener(DownloadListener listener) {
        if (!contains(listener)) {
            listeners.add(new WeakReference<DownloadListener>(listener));
        }
    }
    
    private boolean contains(DownloadListener listener) {
        for (WeakReference<DownloadListener> weak : listeners) {
            final DownloadListener listen = weak.get();
            if (listen != null && listen.equals(listener)) return true;
        }
        return false;
    }
    
    public Pumping getById(String id) {
        return id2Pumping.get(id);
    }
    
    public Pumping[] toArray() {
        return id2Pumping.values().toArray(new Pumping[0]);
    }
    
    public void fire(String methodName, Object... args) {
        final List<Class> argsClasses = new ArrayList<Class>(args.length);
        for (Object arg : args) {
            argsClasses.add(arg.getClass());
        }
        try {
            final Method method = DownloadListener.class.getMethod(methodName, argsClasses.toArray(new Class[0]));
            notifyListeners(method, args);
        } catch (NoSuchMethodException ex) {
            throw new UnexpectedExceptionError("Listener contract was changed", ex);
        }
    }
    
    private synchronized void notifyListeners(Method mehtod, Object... args) {
        WeakReference<DownloadListener>[] stub = listeners.toArray(EMPTY_ARRAY);
        for (WeakReference<DownloadListener> ref : stub) {
            final DownloadListener listener = ref.get();
            if (listener == null) continue;
            try {
                mehtod.invoke(listener, args);
            } catch (IllegalAccessException ignored) {
                LogManager.log(ignored);
            } catch (InvocationTargetException ignored) {
                //undeline throw an exception.
                //It's not headache of queue if listener throws exceptions - just log it for debug purpose
                LogManager.log(ignored);
            }
        }
    }
    
    protected void load() {
        try {
            Document queueState = DomUtil.parseXmlFile(stateFile);
            final DomVisitor visitor = new RecursiveDomVisitor() {
                public void visit(Element element) {
                    if ("pumping".equals(element.getNodeName())) {
                        final PumpingImpl newOne = new PumpingImpl(QueueBase.this);
                        newOne.readXML(element);
                        id2Pumping.put(newOne.getId(), newOne);
                    } else
                        super.visit(element);
                }
            };
            visitor.visit(queueState);
        } catch (ParseException ex) {
            LogManager.log("fail to load queue state - parsing error occurs");
        }  catch (IOException ex) {
            LogManager.log("I/O error during loading. queue is empty now");
        }
    }
    
    public synchronized void dump() {
        try {
            final Document document = DomUtil.parseXmlFile("<queueState/>");
            final Element root = document.getDocumentElement();
            for (Pumping puming : toArray()) {
                DomUtil.addChild(root, (PumpingImpl) puming);
            }
            DomUtil.writeXmlFile(document, stateFile);
        } catch (ParseException wontHappend) {
            LogManager.log(wontHappend);
        } catch (IOException io) {
            LogManager.log("fail to dump - i/o error occures");
            LogManager.log(io);
        }
    }
}
