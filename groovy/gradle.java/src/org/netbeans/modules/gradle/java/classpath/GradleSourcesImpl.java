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

import org.netbeans.modules.gradle.api.GradleBaseProject;
import org.netbeans.modules.gradle.java.api.GradleJavaSourceSet;
import static org.netbeans.modules.gradle.java.api.GradleJavaSourceSet.SourceType;
import org.netbeans.modules.gradle.api.NbGradleProject;
import org.netbeans.modules.gradle.java.api.GradleJavaProject;
import static org.netbeans.modules.gradle.java.api.GradleJavaSourceSet.MAIN_SOURCESET_NAME;
import org.netbeans.modules.gradle.spi.Utils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.project.ProjectServiceProvider;
import org.netbeans.spi.project.SourceGroupModifierImplementation;
import org.netbeans.spi.project.support.GenericSources;
import org.openide.filesystems.FileUtil;
import org.openide.util.ChangeSupport;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

/**
 *
 * @author Laszlo Kishalmi
 */
@ProjectServiceProvider(service = {Sources.class, SourceGroupModifierImplementation.class}, projectType = NbGradleProject.GRADLE_PLUGIN_TYPE + "/java-base")
@NbBundle.Messages({
    "# {0} - source group name",
    "# {1} - language",
    "# {2} - directory name",
    "MAIN_LANG_UNIQUE=Source Packages [{1}]",
    "# {0} - source group name",
    "# {1} - language",
    "# {2} - directory name",
    "MAIN_RESOURCES_UNIQUE=Resources",
    "# {0} - source group name",
    "# {1} - language",
    "# {2} - directory name",
    "MAIN_LANG=Source Packages [{1}][{2}]",
    "# {0} - source group name",
    "# {1} - language",
    "# {2} - directory name",
    "MAIN_RESOURCES=Resources [{2}]",
    "# {0} - source group name",
    "# {1} - language",
    "# {2} - directory name",
    "OTHER_LANG_UNIQUE={0} Packages [{1}]",
    "# {0} - source group name",
    "# {1} - language",
    "# {2} - directory name",
    "OTHER_RESOURCES_UNIQUE={0} Resources",
    "# {0} - source group name",
    "# {1} - language",
    "# {2} - directory name",
    "OTHER_LANG={0} Packages [{1}][{2}]",
    "# {0} - source group name",
    "# {1} - language",
    "# {2} - directory name",
    "OTHER_RESOURCES={0} Resources [{2}]",

    "# {0} - directory name",
    "GATLING_SCENARIOES=Gatling Scenarioes [0]",
    "# {0} - directory name",
    "GATLING_SCENARIOES_UNIQUE=Gatling Scenarioes",
    "GATLING_DATA=Gatling Test Data",
    "GATLING_BODIES=Gatling Request Bodies",
    "# {0} - directory name",
    "GATLING_OTHER=Gatling Resources [{0}]",
}
)
public class GradleSourcesImpl implements Sources, SourceGroupModifierImplementation {

    private static final Map<String, String> COMMON_NAMES = new HashMap<>();

    static {
        COMMON_NAMES.put("main.JAVA", "01main.java");
        COMMON_NAMES.put("main.GROOVY", "02main.groovy");
        COMMON_NAMES.put("main.SCALA", "03main.scala");
        COMMON_NAMES.put("main.RESOURCES", "09main.resources");
        COMMON_NAMES.put("test.JAVA", "11test.java");
        COMMON_NAMES.put("test.GROOVY", "12test.groovy");
        COMMON_NAMES.put("test.SCALA", "13test.scala");
        COMMON_NAMES.put("test.RESOURCES", "19test.resources");
        COMMON_NAMES.put("gatling.SCALA", "41gatling.scala");
        COMMON_NAMES.put("gatling.RESOURCES.data", "42gatling.data");
        COMMON_NAMES.put("gatling.RESOURCES.bodies", "43gatling.bodies");
        COMMON_NAMES.put("gatling.RESOURCES", "49gatling.resources");
    }

    private final Project proj;
    private final ChangeSupport cs = new ChangeSupport(this);
    private final PropertyChangeListener pcl = new PropertyChangeListener() {
        public @Override
        void propertyChange(PropertyChangeEvent evt) {
            if (NbGradleProject.get(proj).isUnloadable()) {
                return; //let's just continue with the old value, stripping classpath for broken project and re-creating it later serves no greater good.
            }
            if (NbGradleProject.PROP_PROJECT_INFO.equals(evt.getPropertyName())
                    || NbGradleProject.PROP_RESOURCES.equals(evt.getPropertyName())) {
                checkChanges(true);
            }
        }
    };

    private Map<String, GradleJavaSourceSet> gradleSources = Collections.emptyMap();
    private Map<String, Collection<File>> sourceGroups;
    private final Map<Pair<SourceType, File>, SourceGroup> cache = new HashMap<>();

    public GradleSourcesImpl(Project proj) {
        this.proj = proj;
    }

    @Override
    public synchronized SourceGroup[] getSourceGroups(String type) {
        if (Sources.TYPE_GENERIC.equals(type)) {
            return new SourceGroup[]{GenericSources.group(proj, proj.getProjectDirectory(), "ProjectRoot", //NOI18N
                ProjectUtils.getInformation(proj).getDisplayName(), null, null)};
        } else {
            checkChanges(false);
            ArrayList<SourceGroup> ret = new ArrayList<>();
            for (String group : gradleSources.keySet()) {
                for (SourceType langType : SourceType.values()) {
                    Set<File> dirs = gradleSources.get(group).getSourceDirs(langType);
                    boolean unique = dirs.size() == 1;
                    for (File dir : dirs) {
                        if (dir.isDirectory()) {
                            ret.add(createSourceGroup(unique, group, dir, langType));
                        }
                    }
                }
            }
            Collections.sort(ret, new Comparator<SourceGroup>() {

                @Override
                public int compare(SourceGroup o1, SourceGroup o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            return ret.toArray(new SourceGroup[ret.size()]);
        }
    }

    SourceGroup createSourceGroup(boolean unique, String group, File dir,
            SourceType lang) {
        SourceGroup ret = cache.get(Pair.of(lang, dir));
        if (ret == null) {
            String msgKey = group + "." + lang.name();
            String groupKey = sourceGroupName(msgKey, dir);
            String sgDisplayName = !"gatling".equals(group) //NOI18N
                    ? sourceGroupDisplayName(unique, group, dir, lang)
                    : gatlingSourceGroupDisplayName(unique, dir, lang);
            ret = GenericSources.group(proj,
                    FileUtil.toFileObject(dir), groupKey, sgDisplayName, null, null);
            cache.put(Pair.of(lang, dir), ret);
        }
        return ret;
    }

    static String sourceGroupName(String key, File dir) {
        String ret = COMMON_NAMES.get(key + "." + dir.getName()); //NOI18N
        ret = (ret == null) ? COMMON_NAMES.get(key) : ret;
        ret = (ret == null) ? "99" + key : ret; //NOI18N
        return ret;
    }

    static String sourceGroupDisplayName(boolean unique, String group, File dir,
            SourceType lang) {
        StringBuilder key = new StringBuilder();
        key.append(MAIN_SOURCESET_NAME.equals(group) ? "MAIN_" : "OTHER_"); //NOI18N
        key.append(lang != SourceType.RESOURCES ? "LANG" : "RESOURCES"); //NOI18N
        if (unique) {
            key.append("_UNIQUE"); //NOI18N
        }
        String llang = lang.name().toLowerCase();
        String ggroup = Utils.camelCaseToTitle(group);
        if (!unique && llang.equals(dir.getName())) {
            dir = dir.getParentFile() != null ? dir.getParentFile() : dir;
        }

        return NbBundle.getMessage(GradleSourcesImpl.class, key.toString(),
                ggroup, llang, dir.getName());
    }

    static String gatlingSourceGroupDisplayName(boolean unique, File dir,
            SourceType lang) {
        StringBuilder key = new StringBuilder("GATLING_"); //NOI18N
        String dirName = dir.getName();
        if (lang == GradleJavaSourceSet.SourceType.RESOURCES) {
            switch(dirName) {
                case "bodies":            //NOI18N
                    key.append("BODIES"); //NOI18N
                    break;
                case "data":              //NOI18N
                    key.append("DATA");   //NOI18N
                    break;
                default:
                    key.append("OTHER");  //NOI18N
            }
        }
        if (lang == GradleJavaSourceSet.SourceType.SCALA){
            key.append("SCENARIOES");  //NOI18N
            if (unique) {
                key.append("_UNIQUE"); //NOI18N
            }
        }
        return NbBundle.getMessage(GradleSourcesImpl.class, key.toString(),
                dir.getName());
    }

    private void checkChanges(boolean fireChanges) {
        boolean changed = sourceGroups == null;

        if (GradleJavaProject.get(proj) != null) {
            Map<String, GradleJavaSourceSet> newSources = GradleJavaProject.get(proj).getSourceSets();
            if (sourceGroups != null) {
                Set<String> enteringGroups = new HashSet<>(newSources.keySet());
                Set<String> leavingGroups = new HashSet<>(sourceGroups.keySet());
                Set<String> remainingGroups = new HashSet<>(newSources.keySet());
                remainingGroups.retainAll(sourceGroups.keySet());
                enteringGroups.removeAll(remainingGroups);
                leavingGroups.removeAll(remainingGroups);
                changed = !leavingGroups.isEmpty() || !enteringGroups.isEmpty();
                if (!changed) {
                    for (String rg : remainingGroups) {
                        if (!sourceGroups.get(rg).equals(newSources.get(rg).getAvailableDirs(false))) {
                            changed = true;
                            break;
                        }
                    }
                }
            }
            sourceGroups = new HashMap<>();
            for (Map.Entry<String, GradleJavaSourceSet> entry : newSources.entrySet()) {
                sourceGroups.put(entry.getKey(), entry.getValue().getAvailableDirs(false));
            }
            gradleSources = newSources;
            cache.clear();
        }
        if (changed && fireChanges) {
            cs.fireChange();
        }
    }

    public @Override
    void addChangeListener(ChangeListener changeListener) {
        if (!cs.hasListeners()) {
            NbGradleProject.addPropertyChangeListener(proj, pcl);
        }
        cs.addChangeListener(changeListener);
    }

    public @Override
    void removeChangeListener(ChangeListener changeListener) {
        cs.removeChangeListener(changeListener);
        if (!cs.hasListeners()) {
            NbGradleProject.removePropertyChangeListener(proj, pcl);
        }
    }

    @Override
    public SourceGroup createSourceGroup(String type, String hint) {
        SourceGroup ret = null;
        GradleJavaProject gp = GradleJavaProject.get(proj);
        GradleJavaSourceSet ss = gp.getSourceSets().get(hint);
        if (ss != null) {
            try {
                SourceType st = SourceType.valueOf(type.toUpperCase());
                if (ss.getSourceDirs(st).size() == 1) {
                    File sgroot = ss.getSourceDirs(st).iterator().next();
                    if (!sgroot.exists()) {
                        FileUtil.createFolder(sgroot);
                        ret = createSourceGroup(true, hint, sgroot, st);
                    }
                }
            } catch (IllegalArgumentException | IOException ex) {
                // Nothing to do just return null silently.
            }
        }
        return ret;
    }

    @Override
    public boolean canCreateSourceGroup(String type, String hint) {
        //TODO: Revalidate with new declarative Gradle java-lang plugin
        boolean ret = JavaProjectConstants.SOURCES_TYPE_RESOURCES.equals(type);
        GradleJavaProject gp = GradleJavaProject.get(proj);
        ret = ret || GradleBaseProject.get(proj).hasPlugins(type);
        return ret && gp.getSourceSets().containsKey(hint);
    }

}
