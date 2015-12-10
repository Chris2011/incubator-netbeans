/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2015 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2015 Sun Microsystems, Inc.
 */
package org.netbeans.modules.docker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 *
 * @author Petr Hejl
 */
public final class HttpParsingUtils {

    private static final Pattern HTTP_RESPONSE_PATTERN = Pattern.compile("^HTTP/1\\.1 (\\d\\d\\d) (.*)$");

    private HttpParsingUtils() {
        super();
    }

    public static Response readResponse(InputStream is) throws IOException {
        String response = HttpParsingUtils.readResponseLine(is);
        if (response == null) {
            throw new IOException("No response from server");
        }
        Matcher m = HTTP_RESPONSE_PATTERN.matcher(response);
        if (!m.matches()) {
            throw new IOException("Wrong response from server");
        }

        int responseCode = Integer.parseInt(m.group(1));
        String message = m.group(2);
        Map<String, String> headers = parseHeaders(is);
        return new Response(responseCode, message, headers);
    }

    @CheckForNull
    public static String readContent(InputStream is, Response response) throws IOException {
        Integer length = getLength(response.getHeaders());
        if (length == null || length == 0) {
            return null;
        }
        Charset encoding = getCharset(response.getHeaders().get("CONTENT-TYPE")); // NOI18N
        byte[] content = new byte[length];
        int count = 0;
        do {
             int current = is.read(content, count, length - count);
             if (current < 0 && count < length) {
                 throw new IOException("Stream closed before reading content");
             }
             count += current;
        } while (count < length);
        return new String(content, encoding);
    }

    public static String readError(HttpURLConnection conn) throws IOException {
        InputStream err = conn.getErrorStream();
        if (err == null || err.available() <= 0) {
            return null;
        }
        Charset encoding = getCharset(conn.getContentType());
        ByteArrayOutputStream bos = new ByteArrayOutputStream(200);
        byte[] content = new byte[200];
        int length;
        while((length = err.read(content)) != -1) {
            bos.write(content, 0, length);
        }
        return bos.toString(encoding.name());
    }

    public static InputStream getResponseStream(InputStream is, Response response) {
        if (isChunked(response.getHeaders())) {
            return new ChunkedInputStream(new BufferedInputStream(is));
        } else {
            return new BufferedInputStream(is);
        }
    }

    public static Integer getLength(Map<String, String> headers)  throws IOException {
        String value = headers.get("CONTENT-LENGTH"); // NOI18N
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IOException("Wrong content length: " + value);
        }
    }

    public static Charset getCharset(Response response) {
        return getCharset(response.getHeaders().get("CONTENT-TYPE")); // NOI18N
    }

    public static Charset getCharset(HttpURLConnection connection) {
        return getCharset(connection.getContentType());
    }

    public static String encodeParameter(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    public static String encodeBase64(String value) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(value.getBytes("UTF-8"));
    }

    static String readResponseLine(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int b;
        while ((b = is.read()) != -1) {
            if (b == '\r') {
                int next = is.read();
                if (next == '\n') {
                    return bos.toString("ISO-8859-1"); // NOI18N
                } else if (next == -1) {
                    return null;
                } else {
                    bos.write(b);
                    bos.write(next);
                }
            } else {
                bos.write(b);
            }
        }
        return null;
    }

    private static Map<String, String> parseHeaders(InputStream is) throws IOException {
        Map<String, String> result = new HashMap<>();
        String line;
        for (;;) {
            line = HttpParsingUtils.readResponseLine(is).trim();
            if (line != null && !"".equals(line)) {
                int index = line.indexOf(':'); // NOI18N
                if (index <= 0) {
                    throw new IOException("Invalid header: " + line);
                }
                if (index == line.length() - 1) {
                    // XXX empty header ?
                    continue;
                }
                result.put(line.substring(0, index).toUpperCase(Locale.ENGLISH).trim(), line.substring(index + 1).trim());
            } else {
                break;
            }
        }
        return result;
    }

    private static Charset getCharset(String contentType) {
        // FIXME the spec default is ISO-8859-1
        Charset encoding = Charset.forName("UTF-8"); // NOI18N
        if (contentType != null) {
            String[] parts = contentType.trim().split(";"); // NOI18N
            for (String p : parts) {
                String upper = p.toUpperCase(Locale.ENGLISH);
                if (upper.startsWith("CHARSET")) { // NOI18N
                    int index = upper.indexOf("=", 7); // NOI18N
                    if (index > 0 && index < upper.length() -1) {
                        try {
                            encoding = Charset.forName(upper.substring(index + 1).trim());
                        } catch (UnsupportedCharsetException ex) {
                            // noop using the UTF-8
                        }
                    }
                    break;
                }
            }
        }
        return encoding;
    }

    private static boolean isChunked(Map<String, String> headers) {
        String value = headers.get("TRANSFER-ENCODING"); // NOI18N
        return value != null && value.contains("chunked"); // NOI18N
    }

    public static class Response {

        private final int code;

        private final String message;

        private final Map<String, String> headers;

        Response(int code, String message, Map<String, String> headers) {
            this.code = code;
            this.message = message;
            this.headers = headers;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }
    }
}
