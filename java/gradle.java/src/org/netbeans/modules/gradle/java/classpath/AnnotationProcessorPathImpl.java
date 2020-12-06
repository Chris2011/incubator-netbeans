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
package org.netbeans.modules.gradle.java.classpath;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gradle.java.api.GradleJavaSourceSet;
import static org.netbeans.modules.gradle.java.classpath.AbstractGradleClassPathImpl.addAllFile;

/**
 *
 * @author lkishalmi
 */
public final class AnnotationProcessorPathImpl extends AbstractSourceSetClassPathImpl {

    public AnnotationProcessorPathImpl(Project proj, String sourceSetName) {
        super(proj, sourceSetName);
    }

    @Override
    protected List<URL> createPath() {
        List<URL> ret = new ArrayList<>();
        GradleJavaSourceSet ss = getSourceSet();
        if (ss != null) {
            addAllFile(ret, ss.getAnnotationProcessorPath());
        }
        return ret;
    }

}
