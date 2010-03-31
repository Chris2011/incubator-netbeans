/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.php.editor.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.netbeans.modules.php.editor.parser.astnodes.BodyDeclaration.Modifier;

/**
 * @author Radek Matous
 */
public final class PhpModifiers extends Modifier {
    private int mod;
    public static int NO_FLAGS = 0;
    public static int ALL_FLAGS = 0xFFFFFFFF;

    private static PhpModifiers EMPTY = new PhpModifiers(NO_FLAGS);
    private static PhpModifiers ANY = new PhpModifiers(ALL_FLAGS);

    public static PhpModifiers noModifiers() {
        return fromBitMask(new int[]{});
    }
    // usage: bitMasks(Modifier.PUBLIC | Modifier.FINAL) or bitMasks(Modifier.PUBLIC, Modifier.FINAL)
    public static PhpModifiers fromBitMask(int... bitmasks) {
        return bitmasks.length == 0 ? EMPTY : new PhpModifiers(bitmasks);
    }
    public PhpModifiers setPublic() {
        mod |= Modifier.PUBLIC;
        return this;
    }

    public PhpModifiers setPrivate(){
        mod |= Modifier.PRIVATE;
        return this;
    }

    public PhpModifiers setProtected() {
        mod |= Modifier.PROTECTED;
        return this;
    }

    public PhpModifiers setStatic() {
        mod |= Modifier.STATIC;
        return this;
    }

    public PhpModifiers setFinal() {
        mod |= Modifier.FINAL;
        return this;
    }

    public PhpModifiers setAbstract() {
        mod |= Modifier.ABSTRACT;
        return this;
    }
    private PhpModifiers(int... bitmask) {
        for (int mode : bitmask) {
            this.mod |= mode;
        }
    }
    public Set<org.netbeans.modules.csl.api.Modifier> toModifiers() {
        Set<org.netbeans.modules.csl.api.Modifier> retval = new HashSet<org.netbeans.modules.csl.api.Modifier>();
        if (isPublic() && !isStatic()) {
            retval.add(org.netbeans.modules.csl.api.Modifier.PUBLIC);
        }
        if (isProtected()) {
            retval.add(org.netbeans.modules.csl.api.Modifier.PROTECTED);
        }
        if (isPrivate()) {
            retval.add(org.netbeans.modules.csl.api.Modifier.PRIVATE);
        }
        if (isStatic()) {
            retval.add(org.netbeans.modules.csl.api.Modifier.STATIC);
        }
        return retval;
    }    
    public int toFlags() {
        return mod;
    }

    public boolean isPublic() {
        return Modifier.isPublic(mod) || (!Modifier.isPrivate(mod) && !Modifier.isProtected(mod));
    }

    public boolean isPrivate(){
        return Modifier.isPrivate(mod);
    }

    public boolean isProtected() {
        return Modifier.isProtected(mod);
    }

    public boolean isStatic() {
        return Modifier.isStatic(mod);
    }

    public boolean isFinal() {
        return Modifier.isFinal(mod);
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(mod);
    }

    @Override
    public String toString() {
        return Modifier.toString(mod);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PhpModifiers) ?
            ((PhpModifiers)obj).mod == mod : false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.mod;
        return hash;
    }

    private static int[] convertStringToBitmask(Set<org.netbeans.modules.csl.api.Modifier> modifiers) {
        ArrayList<Integer> mods = new ArrayList<Integer>();
        for (org.netbeans.modules.csl.api.Modifier modifier : modifiers) {
            switch (modifier) {
                case PRIVATE:
                    mods.add(PhpModifiers.PRIVATE);
                    break;
                case PROTECTED:
                    mods.add(PhpModifiers.PROTECTED);
                    break;
                case PUBLIC:
                    mods.add(PhpModifiers.PUBLIC);
                    break;
                case STATIC:
                    mods.add(PhpModifiers.STATIC);
                    break;
            }
        }
        int[] retval = new int[mods.size()];
        for (int i = 0; i < mods.size(); i++) {
            retval[i] = mods.get(i);
        }
        return retval;
    }
    /*
     * not used
     public static PhpModifiers create(Set<org.netbeans.modules.csl.api.Modifier> modifiers) {
        return bitMasks(convertStringToBitmask(modifiers));
    }*/
}
