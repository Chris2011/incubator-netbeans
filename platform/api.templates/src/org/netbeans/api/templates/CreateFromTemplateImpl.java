/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.api.templates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import org.netbeans.api.queries.FileEncodingQuery;
import org.netbeans.modules.templates.ScriptingCreateFromTemplateHandler;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.MapFormat;
import org.openide.util.Parameters;

/**
 *
 * @author sdedic
 */
final class CreateFromTemplateImpl {
    private static final String PROP_TEMPLATE = "template"; // NOI18N
    private static final String NEWLINE = "\n"; // NOI18N
    
    private final FileBuilder builder;
    private final CreateDescriptor desc;
    private Map<String, ?> originalParams;
    
    private List<CreateFromTemplateDecorator> decorators;
    
    private CreateFromTemplateImpl(FileBuilder builder) {
        this.builder = builder;
        this.desc = builder.getDescriptor();
    }
    
    static List<FileObject> build(FileBuilder flb) throws IOException {
        CreateFromTemplateImpl impl = new CreateFromTemplateImpl(flb);
        return impl.build();
    }
    
    static void collectAttributes(FileBuilder flb) {
        CreateFromTemplateImpl impl = new CreateFromTemplateImpl(flb);
        flb.withParameters(impl.findTemplateParameters());
    }
    
    private void setupDecorators() {
        decorators = new ArrayList<>(Lookup.getDefault().lookupAll(CreateFromTemplateDecorator.class));
        for (Iterator<CreateFromTemplateDecorator> it = decorators.iterator(); it.hasNext(); ) {
            CreateFromTemplateDecorator dec = it.next();
            if (!dec.accept(desc)) {
                it.remove();
            }
        }
    }
    
    List<FileObject> build() throws IOException {
        // side effects: replaces the map in CreateDescriptor
        try {
            FileObject f = desc.getTemplate();
            FileObject folder = desc.getTarget();
            FileBuilder.Mode defaultMode = builder.defaultMode;
            Format frm = builder.format;
            Parameters.notNull("f", f);
            Parameters.notNull("folder", folder);
            assert defaultMode != FileBuilder.Mode.FORMAT || frm != null : "Format must be provided for Mode.FORMAT";

            if (!folder.isFolder()) {
                throw new IllegalArgumentException("Not a folder: "  + folder);
            }
            // also modifies desc.getParameters, result not needed.
            findTemplateParameters();
            setupDecorators();
            computeEffectiveName(desc);

            List<FileObject> initialFiles = callDecorators(true, new ArrayList<>());
            List<FileObject> pf = null;
            for (CreateFromTemplateHandler h : Lookup.getDefault().lookupAll(CreateFromTemplateHandler.class)) {
                if (h.accept(desc)) {
                    pf = h.createFromTemplate(desc);
                    assert pf != null && !pf.isEmpty();
                    break;
                }
            }
            // side effects from findTemplateParameters still in effect...
            if (pf == null && defaultMode != FileBuilder.Mode.FAIL) {
                pf = Collections.singletonList(defaultCreate());
            }
            if (pf == null) {
                return pf;
            }
            List<FileObject> result = new ArrayList<>(pf);
            result.addAll(initialFiles);
            callDecorators(false, result);
            return result;
        } finally {
            // bring back the parameters
            builder.getDescriptor().parameters = (Map<String, Object>)originalParams;
        }
    }
    
    private List<FileObject>    callDecorators(boolean preCreate, List<FileObject> result) throws IOException {
        for (CreateFromTemplateDecorator deco : decorators) {
            if ((preCreate ? deco.isBeforeCreation() : deco.isAfterCreation())) {
                List<FileObject>  preFiles = deco.decorate(desc, result);
                if (preFiles != null) {
                    preFiles.removeAll(result);
                    result.addAll(preFiles);
                }
            }
        }
        return result;
    }
    
    /* package private */ static void computeEffectiveName(CreateDescriptor desc) {
        String name = desc.getName();
        if (name == null) {
            // name is not set - try to check parameters, if some template attribute handler
            // did not supply a suggestion:
            Object o = desc.getParameters().get("name"); // NOi18N
            if (o instanceof String) {
                name = (String)o;
            } else {
                name = FileUtil.findFreeFileName(
                           desc.getTarget(), desc.getTemplate().getName (), desc.getTemplate().getExt ()
                       );
            }
        }
        desc.proposedName = name;
    }
    
    /**
     * Populates default values for template parameters. Each template can have its specific parameters and their default values
     * are defined by {@link CreateFromTemplateAttributes} SPI registered in the Lookup. The 'param' provides user-defined values,
     * which always take precedence over the defaults.
     * <p/>
     * Certain values are always filled in, if not specified:
     * <ul>
     * <li>{@code user} the invoking user name
     * <li>{@code date} system date : String
     * <li>{@code time} system time : String
     * <li>{@code dateTime} java.util.Date representation of the current system time
     * </ul>
     * 
     * @param template
     * @param folder
     * @param name
     * @param param
     * @return completed parameters
     */
    public Map<String,Object> findTemplateParameters() {
        // IMPORTANT: all map is exposed through CreateDescriptor !
        HashMap<String,Object> all = new HashMap<String,Object>();
        all.putAll(desc.getParameters());
        originalParams = desc.parameters;
        desc.parameters = all;
        for (CreateFromTemplateAttributes provider : Lookup.getDefault().lookupAll(CreateFromTemplateAttributes.class)) {
            Map<String,? extends Object> map = provider.attributesFor(desc);
            if (map != null) {
                for (Map.Entry<String,? extends Object> e : map.entrySet()) {
                    // allow each CFTA override previous CFTAs, but not user-provided params
                    if (originalParams == null || !originalParams.containsKey(e.getKey())) {
                        all.put(e.getKey(), e.getValue());
                    }
                }
            }
        }
        String name = desc.getName();
        if (!all.containsKey("name") && name != null) { // NOI18N
            String n = name;
            if (desc.hasFreeExtension()) {
                n = name.replaceFirst("[.].*", "");
            }
            all.put("name", n); // NOI18N
            //desc.name = name;
        }
        Date d = new Date();
        if (!all.containsKey("dateTime")) { // NOI18N
            all.put("dateTime", d); // NOI18N
        }
        String ext = desc.getTemplate().getExt();
        if (!all.containsKey("nameAndExt") && name != null) { // NOI18N
            if (ext != null && ext.length() > 0 && originalParams != null &&
                    (!desc.hasFreeExtension() || name.indexOf('.') == -1)) {
                all.put("nameAndExt", name + '.' + ext); // NOI18N
            } else {
                all.put("nameAndExt", name); // NOI18N
            }
        }
        return all;
    }
    
    /**
     * Creates the file using the default algorithm - no handler is willing to participate
     * @return created file
     * @throws IOException 
     */
    private FileObject defaultCreate() throws IOException {
        Map<String, ?> params = desc.getParameters();
        FileBuilder.Mode defaultMode = builder.defaultMode;
        Format frm = builder.format;
        
        if (defaultMode != FileBuilder.Mode.COPY && frm instanceof MapFormat) {
            MapFormat mf = (MapFormat)frm;
            Map<String, Object> m = mf.getMap();
            Map x = null;
            for (String s: params.keySet()) {
                if (m.containsKey(s)) {
                    continue;
                }
                if (x == null) {
                    x = new HashMap<>(m);
                }
                x.put(s, params.get(s));
            }
            if (x != null) {
                mf.setMap(x);
            }
        }
        FileObject f = desc.getTemplate();
        String ext = desc.getTemplate().getExt();
        FileObject fo = desc.getTarget().createData (desc.getProposedName(), ext);
        boolean preformatted = false;
        Charset encoding = FileEncodingQuery.getEncoding(f);
        boolean success = false;
        FileLock lock = fo.lock ();
        try (InputStream is= f.getInputStream ();
            Reader reader = new InputStreamReader(is,encoding);
            BufferedReader r = new BufferedReader (reader)) {

            preformatted = desc.isPreformatted();
            encoding = FileEncodingQuery.getEncoding(fo);
            
            //Document doc = ScriptingCreateFromTemplateHandler.createDocument(f.getMIMEType());
            ScriptEngine en = desc.isPreformatted() ? null : ScriptingCreateFromTemplateHandler.indentEngine();
            // PENDING: originally, preformatted meant that only changed
            // lines were formatted. Now preformatted is not formatted at all
            StringWriter sw = new StringWriter();
            try (
                OutputStream os=fo.getOutputStream(lock);
                OutputStreamWriter w = new OutputStreamWriter(os, encoding);
                Writer iw = preformatted || en == null ? w : sw) {

                String line = null;
                String current;

                while ((current = r.readLine ()) != null) {
                    if (line != null) {
                        // newline between lines
                        iw.append(NEWLINE);
                    }
                    if (frm != null) {
                        line = frm.format (current);
                    } else {
                        line = current;
                    }
                    iw.append(line);
                }
                iw.append(NEWLINE);
                iw.flush();
                
                if (en != null) {
                    en.getContext().setAttribute("mimeType", f.getMIMEType(), ScriptContext.ENGINE_SCOPE);
                    en.getContext().setWriter(w);
                    en.eval(new StringReader(sw.toString()));
                }
            }
            // copy attributes
            // hack to overcome package-private modifier in setTemplate(fo, boolean)
            FileUtil.copyAttributes(f, fo);
            fo.setAttribute(PROP_TEMPLATE, null);
            success = true;
        } catch (IOException ex) {
            try {
                fo.delete(lock);
            } catch (IOException ex2) {
            }
            throw ex;
        } catch (ScriptException ex) {
            IOException io = ex.getCause() instanceof IOException ? (IOException)ex.getCause() : null;
            try {
                fo.delete(lock);
            } catch (IOException ex2) {
            }
            throw io == null ? new IOException(ex) : io;
        } finally {
            if (!success) {
                // try to delete the malformed file:
                fo.delete(lock);
            }
            lock.releaseLock();
        }
        return fo;
    }

}
