/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
package org.netbeans.modules.java.hints.jackpot.spi;

import com.sun.source.tree.Tree.Kind;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import org.openide.util.Parameters;

/**
 *
 * @author lahvac
 */
public abstract class Trigger {

    public static final class Kinds extends Trigger {
        private final Set<Kind> kinds;

        public Kinds(Set<Kind> kinds) {
            this.kinds = kinds;
        }

        public Iterable<? extends Kind> getKinds() {
            return kinds;
        }

        @Override
        public String toString() {
            return kinds.toString();
        }

    }
    public static final class PatternDescription extends Trigger {

        private final String pattern;
        private final Map<String, String> constraints;
        private final Iterable<? extends String> imports;

        private PatternDescription(String pattern, Map<String, String> constraints, String... imports) {
            this.pattern = pattern;
            this.constraints = constraints;
            this.imports = Arrays.asList(imports);
        }

        public static PatternDescription create(String pattern, Map<String, String> constraints, String... imports) {
            Parameters.notNull("pattern", pattern);
            Parameters.notNull("constraints", constraints);
            Parameters.notNull("imports", imports);

            return new PatternDescription(pattern, constraints, imports);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final PatternDescription other = (PatternDescription) obj;
            if ((this.pattern == null) ? (other.pattern != null) : !this.pattern.equals(other.pattern)) {
                return false;
            }
            if (this.constraints != other.constraints && (this.constraints == null || !this.constraints.equals(other.constraints))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + (this.pattern != null ? this.pattern.hashCode() : 0);
            hash = 71 * hash + (this.constraints != null ? this.constraints.hashCode() : 0);
            return hash;
        }

        //XXX: should not be public:
        public String getPattern() {
            return pattern;
        }

        //XXX: should not be public:
        public Map<String, String> getConstraints() {
            return constraints;
        }

        //XXX: should not be public:
        public Iterable<? extends String> getImports() {
            return imports;
        }

        @Override
        public String toString() {
            return pattern;
        }

    }

    public static final Iterable<? extends Class<? extends Trigger>> TRIGGER_KINDS = Arrays.asList(Kinds.class, PatternDescription.class);
}
