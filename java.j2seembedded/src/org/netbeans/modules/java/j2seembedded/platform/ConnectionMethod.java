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
package org.netbeans.modules.java.j2seembedded.platform;

import java.io.File;
import java.util.Map;
import org.netbeans.api.annotations.common.NonNull;
import org.openide.util.Parameters;


/**
 *
 * @author Tomas Zezula
 */
public final class ConnectionMethod {

    private final static String PLAT_PROP_HOST = "platform.host";                       //NOI18N
    private final static String PLAT_PROP_PORT = "platform.port";                       //NOI18N

    public static abstract class Authentification {

        private final static String PLAT_PROP_AUTH_KIND = "platform.auth.kind";         //NOI18N
        private final static String PLAT_PROP_AUTH_USER = "platform.auth.username";     //NOI18N


        public static enum Kind {
            PASSWORD {
                @NonNull
                @Override
                Authentification create(@NonNull final Map<String,String> props) {
                    String user = props.get(PLAT_PROP_AUTH_USER);
                    if (user == null) {
                        throw new IllegalStateException("No user"); //NOI18N
                    }
                    String passwd = props.get(Password.PLAT_PROP_AUTH_PASSWD);
                    if (passwd == null) {
                        throw new IllegalStateException("No password"); //NOI18N
                    }
                    return new Password(user, passwd);
                }
            },
            KEY {
                @NonNull
                @Override
                Authentification create(@NonNull final Map<String,String> props) {
                    String user = props.get(PLAT_PROP_AUTH_USER);
                    if (user == null) {
                        throw new IllegalStateException("No user"); //NOI18N
                    }
                    final String keyStore = props.get(Key.PLAT_PROP_AUTH_KEYSTORE);
                    if (keyStore == null) {
                        throw new IllegalStateException("No key store");    //NOI18N
                    }
                    final String passPhrase = props.get(Key.PLAT_PROP_AUTH_PASSPHRASE);
                    if (keyStore == null) {
                        throw new IllegalStateException("No pass phrase");    //NOI18N
                    }
                    return new Key(user, new File(keyStore), passPhrase);
                }
            };

            @NonNull
            abstract Authentification create(@NonNull final Map<String,String> props);
        }

        private final Kind kind;
        private final String userName;

        private Authentification(
            @NonNull final Kind kind,
            @NonNull final String userName) {
            Parameters.notNull("kind", kind);   //NOI18N
            Parameters.notNull("userName", userName);   //NOI18N
            this.kind = kind;
            this.userName = userName;
        }

        public Kind getKind() {
            return kind;
        }

        public String getUserName() {
            return userName;
        }

        void store(@NonNull final Map<String,String> props) {
            props.put(PLAT_PROP_AUTH_KIND, getKind().toString());
            props.put(PLAT_PROP_AUTH_USER, getUserName());
        }

        @NonNull
        static Authentification load(@NonNull final Map<String,String> props) {
            final String _kind = props.get(PLAT_PROP_AUTH_KIND);
            if (_kind == null) {
                throw new IllegalStateException("No authentification kind");
            }
            final Kind kind = Kind.valueOf(_kind);
            return kind.create(props);
        }

        public static final class Password extends Authentification {

            private final static String PLAT_PROP_AUTH_PASSWD = "platform.auth.passwd";         //NOI18N

            private final String password;  //TODO: Store in keystore

            private Password(
                @NonNull final String userName,
                @NonNull final String password) {
                super(Kind.PASSWORD, userName);
                Parameters.notNull("password", password);   //NOI18N
                this.password = password;
            }

            public String getPassword() {
                return password;
            }

            @Override
            void store(@NonNull final Map<String,String> props) {
                super.store(props);
                props.put(PLAT_PROP_AUTH_PASSWD, getPassword());
            }

        }

        public static final class Key extends Authentification {

            private final static String PLAT_PROP_AUTH_KEYSTORE = "platform.auth.keystore";         //NOI18N
            private final static String PLAT_PROP_AUTH_PASSPHRASE = "platform.auth.passphrase";     //NOI18N

            private final File keyStore;
            private final String passPhrase;    //TODO: Store in keystore

            private Key(
                @NonNull final String userName,
                @NonNull final File keyStore,
                @NonNull final String passPhrase) {
                super(Kind.KEY, userName);
                Parameters.notNull("keyStore", keyStore);   //NOI18N
                Parameters.notNull("passphrase", passPhrase);   //NOI18N
                this.keyStore = keyStore;
                this.passPhrase = passPhrase;
            }

            public File getKeyStore() {
                return keyStore;
            }

            public String getPassPhrase() {
                return passPhrase;
            }

            @Override
            void store(@NonNull final Map<String,String> props) {
                super.store(props);
                props.put(PLAT_PROP_AUTH_KEYSTORE, getKeyStore().getAbsolutePath());
                props.put(PLAT_PROP_AUTH_PASSPHRASE, getPassPhrase());
            }
        }
    }
    
    private final String host;
    private final int port;
    private final Authentification auth;

    private ConnectionMethod(
        @NonNull final String host,
        final int port,
        final Authentification auth) {
        Parameters.notNull("host", host);   //NOI18N
        Parameters.notNull("auth", auth);   //NOI18N
        this.host = host;
        this.port = port;
        this.auth = auth;

    }


    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Authentification getAuthentification() {
        return auth;
    }

    void store(@NonNull final Map<String,String> props) {
        props.put(PLAT_PROP_HOST,getHost());
        props.put(PLAT_PROP_PORT,Integer.toString(getPort()));
        auth.store(props);
    }

    public static ConnectionMethod sshPassword(
        @NonNull final String host,
        @NonNull final int port,
        @NonNull final String username,
        @NonNull final String password) {
        return new ConnectionMethod(host, port, new Authentification.Password(username, password));
    }

    public static ConnectionMethod sshKey(
       @NonNull final String host,
       @NonNull final int port,
       @NonNull final String username,
       @NonNull final File keyFile,
       @NonNull final String passphrase) {
        return new ConnectionMethod(host, port, new Authentification.Key(username, keyFile, passphrase));
    }

    @NonNull
    public static ConnectionMethod load(@NonNull final Map<String,String> props) {
        final String host = props.get(PLAT_PROP_HOST);
        if (host == null) {
            throw new IllegalStateException("No platform host");    //NOI18N
        }
        final String _port = props.get(PLAT_PROP_PORT);
        if (_port == null) {
            throw new IllegalStateException("No platfrom port");    //NOI18N
        }
        try {
            final int port = Integer.parseInt(_port);
            return new ConnectionMethod(host, port, Authentification.load(props));
        } catch (NumberFormatException nfe) {
            throw new IllegalStateException("Invalid platfrom port: " + _port);    //NOI18N
        }
    }

}
