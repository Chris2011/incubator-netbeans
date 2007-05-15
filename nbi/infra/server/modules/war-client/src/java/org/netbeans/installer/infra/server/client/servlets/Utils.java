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

package org.netbeans.installer.infra.server.client.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.netbeans.installer.utils.StreamUtils;

public final class Utils {
    public static void transfer(
            HttpServletRequest request,
            HttpServletResponse response,
            OutputStream output,
            File file) throws IOException {
        RandomAccessFile input = null;
        try {
            input = new RandomAccessFile(file, "r");
            
            final String range = request.getHeader("Range");
            if (range == null) {
                response.setStatus(HttpServletResponse.SC_OK);
                
                input.seek(0);
                
                response.setHeader(
                        "Content-Length",
                        Long.toString(file.length()));
                
                StreamUtils.transferData(input, output);
            } else {
                Matcher matcher = Pattern.compile("^bytes=([0-9]*)-([0-9]*)$").matcher(range);
                
                if (!matcher.find()) {
                    response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                    return;
                } else {
                    long start = -1;
                    long finish = -1;
                    
                    if (!matcher.group(1).equals("")) {
                        start = Long.parseLong(matcher.group(1));
                    }
                    
                    if (!matcher.group(2).equals("")) {
                        finish = Long.parseLong(matcher.group(2));
                    }
                    
                    if ((start != -1) &&
                            (finish != -1) &&
                            ((start > finish) || (finish > file.length()))) {
                        response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                        return;
                    }
                    
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    
                    if (start == -1) {
                        start = file.length() - finish;
                        finish = -1;
                    }
                    
                    input.seek(start);
                    
                    long length = (finish == -1 ? file.length() - start : finish - start) + 1;
                    
                    response.setHeader("Content-Length", Long.toString(length));
                    response.setHeader("Content-Range", "bytes " + start + "-" + (finish == -1 ? file.length() - 1 : finish) + "/" + file.length());
                    
                    if (finish == -1) {
                        StreamUtils.transferData(input, output);
                    } else {
                        StreamUtils.transferData(input, output, finish - start + 1);
                    }
                }
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            e.printStackTrace(new PrintStream(output));
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
    
}
