package org.netbeans.nbbuild;

import java.io.File;
import java.util.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.*;
import org.apache.tools.ant.types.*;

/**
 * @author  Michal Zlamal
 * @version
 * This class generating javadoc
 */
public class GenerateJavadoc extends Task
{
    private File dest;
    private Vector modules = new Vector ();
    private List topdirs = new ArrayList ();

    /** Target directory to unpack to (top of IDE installation). */
    public void setDestdir(File f) {
        dest = f;
    }

    /** Comma-separated list of modules to include. */
    public void setModules (String s) {
        StringTokenizer tok = new StringTokenizer (s, ",");
        modules = new Vector ();
        while (tok.hasMoreTokens ())
            modules.addElement (tok.nextToken ());
    }

    /** Set the top directory.
     * There should be subdirectories under this for each named module.
     */
    public void setTopdir (File t) {
        topdirs.add (t);
    }

    /** Nested topdir addition. */
    public class Topdir {
        /** Path to an extra topdir. */
        public void setPath (File t) {
            topdirs.add (t);
        }
    }

    /** Add a nested topdir.
     * If there is more than one topdir total, build products
     * may be taken from any of them, including from multiple places
     * for the same module. (Later topdirs may override build
     * products in earlier topdirs.)
     */
    public Topdir createTopdir () {
        return new Topdir ();
    }

    public void execute () throws BuildException {

        if (topdirs.isEmpty ()) {
            throw new BuildException ("You must set at least one topdir attribute", location);
        }

/*        Delete delete = (Delete) project.createTask ("delete");

        delete.setDir (dest);
        delete.init ();
        delete.setLocation (location);
        delete.execute ();*/
        Path path = new Path( project );
        for (int j = 0; j < topdirs.size (); j++) {
            File topdir = (File) topdirs.get (j);
            for (int i = 0; i < modules.size (); i++) {
                String module = (String) modules.elementAt (i);
                File sources = new File (new File (topdir, module), "javadoc-temp/");
                if (! sources.exists ()) { //testing existination of 'javadoc-temp' dir if existing - skiping dafult source dirs...
                    sources = new File (new File (topdir, module), "src/");
                    path.append(new Path( project, sources.getPath()));
                    sources = new File (new File (topdir, module), "libsrc/");
                    path.append(new Path( project, sources.getPath()));
                }
                else {
                    path.append(new Path( project, sources.getPath()));
                }
            }
        }
        Javadoc javaDoc = (Javadoc) project.createTask("javadoc");
        javaDoc.setSourcepath( path );
        javaDoc.setDestdir( dest );
        javaDoc.setPackagenames("com.*,org.*");
        javaDoc.setMaxmemory("256M");
        javaDoc.execute();

    }
}
