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

import javax.lang.model.element.TypeElement;
import org.netbeans.api.java.source.ElementHandle;

/**
 *
 * @author Andrei Badea
 */
public abstract class PersistentObject {

    private final AnnotationModelHelper helper;
    private final ElementHandle<TypeElement> sourceElementHandle;

    public PersistentObject(AnnotationModelHelper helper, TypeElement typeElement) {
        this.helper = helper;
        sourceElementHandle = ElementHandle.create(typeElement);
    }

    public AnnotationModelHelper getHelper() {
        return helper;
    }

    public final ElementHandle<TypeElement> getSourceElementHandle() {
        return sourceElementHandle;
    }

    public final TypeElement getSourceElement() {
        return sourceElementHandle.resolve(helper.getCompilationController());
    }

    protected abstract void sourceElementChanged();
}
