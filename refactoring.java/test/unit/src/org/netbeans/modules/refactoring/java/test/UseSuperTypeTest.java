/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development and
 * Distribution License("CDDL") (collectively, the "License"). You may not use
 * this file except in compliance with the License. You can obtain a copy of
 * the License at http://www.netbeans.org/cddl-gplv2.html or
 * nbbuild/licenses/CDDL-GPL-2-CP. See the License for the specific language
 * governing permissions and limitations under the License. When distributing
 * the software, include this License Header Notice in each file and include
 * the License file at nbbuild/licenses/CDDL-GPL-2-CP. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided by
 * Oracle in the GPL Version 2 section of the License file that accompanied
 * this code. If applicable, add the following below the License Header, with
 * the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license." If you do not indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to its
 * licensees as provided above. However, if you add GPL Version 2 code and
 * therefore, elected the GPL Version 2 license, then the option applies only
 * if the new code is made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
package org.netbeans.modules.refactoring.java.test;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TreePath;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.Task;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.modules.refactoring.api.Problem;
import org.netbeans.modules.refactoring.api.RefactoringSession;
import org.netbeans.modules.refactoring.java.api.UseSuperTypeRefactoring;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Ralph Ruijs
 */
public class UseSuperTypeTest extends RefactoringTestBase {

    public UseSuperTypeTest(String name) {
        super(name);
    }
    
    public void unfinished228636a() throws Exception { // #228636 - UseSupertypeWherePossible ignores use of generic type
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/A2.java", "package u; public class A2 { }"),
                new File("u/A1.java", "package u; public class A1 extends A2 { void m(A1 a) { } void n() { } }"),
                new File("u/C.java", "package u; public class C extends A1 { public void m(A1 a) { a.n(); } }"));
        performUseSuperType(src.getFileObject("u/A1.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/A2.java", "package u; public class A2 { }"),
                new File("u/A1.java", "package u; public class A1 extends A2 { void m(A1 a) { } void n() { } }"),
                new File("u/C.java", "package u; public class C extends A1 { public void m(A1 a) { a.n(); } }"));
    }
    
    public void unfinished228636b() throws Exception { // #228636 - UseSupertypeWherePossible ignores use of generic type
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/A1.java", "package u; public interface A1<T> { T m(T a); }"),
                new File("u/C.java", "package u; public class C implements A1<C> { public C m(C a) { return null; } }"));
        performUseSuperType(src.getFileObject("u/C.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/A1.java", "package u; public interface A1<T> { T m(T a); }"),
                new File("u/C.java", "package u; public class C implements A1<C> { public C m(C a) { return null; } }"));
    }
    
    public void test229635a() throws Exception { // #229635 - UseSupertypeWherePossible causes ambiguous references
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/A1.java", "package u; public class A1 { }"),
                new File("u/C.java", "package u; public class C extends A1 { public void method(IV a) { a.v(this); } }"),
                new File("u/IV.java", "package u; public interface IV { void v(A1 i); void v(C i); }"));
        performUseSuperType(src.getFileObject("u/C.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/A1.java", "package u; public class A1 { }"),
                new File("u/C.java", "package u; public class C extends A1 { public void method(IV a) { a.v(this); } }"),
                new File("u/IV.java", "package u; public interface IV { void v(A1 i); void v(C i); }"));
    }
    
    public void test229635b() throws Exception { // #229635 - UseSupertypeWherePossible causes ambiguous references
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/A1.java", "package u; public class A1 { }"),
                new File("u/A2.java", "package u; public interface A2 { }"),
                new File("u/C.java", "package u; public class C extends A1 implements A2 { public void method(IV a) { a.v(this); } }"),
                new File("u/IV.java", "package u; public interface IV { void v(A1 i); void v(C i); }"));
        performUseSuperType(src.getFileObject("u/C.java"), 2);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/A1.java", "package u; public class A1 { }"),
                new File("u/A2.java", "package u; public interface A2 { }"),
                new File("u/C.java", "package u; public class C extends A1 implements A2 { public void method(IV a) { a.v(this); } }"),
                new File("u/IV.java", "package u; public interface IV { void v(A1 i); void v(C i); }"));
    }
    
    public void test174431() throws Exception { // #174431 - [Use Supertype] where possible cannot handle exceptions
        writeFilesAndWaitForScan(src, new File("t/Main.java", "package t;\n"
                + "import java.io.IOException;\nimport java.util.logging.Level;\nimport java.util.logging.Logger;\n"
                + "public class Main {\n"
                + "    public static void main(String[] args) {\n"
                + "        try {\n"
                + "            a();\n"
                + "        } catch (FileSystemException ex) {\n"
                + "            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);\n"
                + "        } catch (IOException ex) {\n"
                + "            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);\n"
                + "        }\n"
                + "    }\n"
                + "    private static void a() throws IOException {\n"
                + "    }\n"
                + "}\n"),
                new File("t/FileSystemException.java", "package t;\n import java.io.IOException;\n"
                + "/** * * @author lebedkov */\n"
                + "public class FileSystemException extends IOException {\n"
                + "    public FileSystemException() {\n"
                + "    }\n"
                + "    public FileSystemException(String msg) {\n"
                + "        super(msg);\n"
                + "    }\n"
                + "}\n"));
        performUseSuperType(src.getFileObject("t/FileSystemException.java"), 0);
        verifyContent(src, new File("t/Main.java", "package t;\n"
                + "import java.io.IOException;\nimport java.util.logging.Level;\nimport java.util.logging.Logger;\n"
                + "public class Main {\n"
                + "    public static void main(String[] args) {\n"
                + "        try {\n"
                + "            a();\n"
                + "        } catch (FileSystemException ex) {\n"
                + "            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);\n"
                + "        } catch (IOException ex) {\n"
                + "            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);\n"
                + "        }\n"
                + "    }\n"
                + "    private static void a() throws IOException {\n"
                + "    }\n"
                + "}\n"),
                new File("t/FileSystemException.java", "package t;\n import java.io.IOException;\n"
                + "/** * * @author lebedkov */\n"
                + "public class FileSystemException extends IOException {\n"
                + "    public FileSystemException() {\n"
                + "    }\n"
                + "    public FileSystemException(String msg) {\n"
                + "        super(msg);\n"
                + "    }\n"
                + "}\n"));
    }

    public void test131406a() throws Exception { // #131406 - [Use Supertype] Refactoring does not check method return type
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { static Main instance; static Main getDefault() { return instance; } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
        performUseSuperType(src.getFileObject("u/Main.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { static Main instance; static Main getDefault() { return instance; } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
    }
    
    public void test131406b() throws Exception { // #131406 - [Use Supertype] Refactoring does not check method return type
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { static Main instance; static Iface getDefault() { return instance; } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
        performUseSuperType(src.getFileObject("u/Main.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { static Iface instance; static Iface getDefault() { return instance; } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
    }

    public void test131406c() throws Exception { // #131406 - [Use Supertype] Refactoring does not check method return type
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { static Main instance; static Main getDefault() { return instance == null ? new Main() : instance; } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
        performUseSuperType(src.getFileObject("u/Main.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { static Main instance; static Main getDefault() { return instance == null ? new Main() : instance; } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
    }
     
    public void test131406d() throws Exception { // #131406 - [Use Supertype] Refactoring does not check method return type
        writeFilesAndWaitForScan(src,
                new File("t/B.java", "package t; interface B { public B m(); }"),
                new File("t/C.java", "package t; interface C { public C m(); }"),
                new File("t/A.java", "package t; class A implements C, B { public A m(){ A a = null; return a; } }"));
        performUseSuperType(src.getFileObject("t/A.java"), 1);
        verifyContent(src,
                new File("t/B.java", "package t; interface B { public B m(); }"),
                new File("t/C.java", "package t; interface C { public C m(); }"),
                new File("t/A.java", "package t; class A implements C, B { public A m(){ A a = null; return a; } }"));
    }

    public void test128676a() throws Exception { // #128676 - [Use Supertype] Refactoring does not respect bound generic type
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Main sub = new Main(); action(sub); } public void subMethod() { } public <T extends Main> void action(T input) { input.subMethod(); } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
        performUseSuperType(src.getFileObject("u/Main.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Main sub = new Main(); action(sub); } public void subMethod() { } public <T extends Main> void action(T input) { input.subMethod(); } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
    }

    public void test128676b() throws Exception { // #128676 - [Use Supertype] Refactoring does not respect bound generic type
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Main sub = new Main(); action(sub); } public void subMethod() { } public <T extends Iface> void action(T input) { input.subMethod(); } }"),
                new File("u/Iface.java", "package u; public interface Iface { public void subMethod(); }"));
        performUseSuperType(src.getFileObject("u/Main.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Iface sub = new Main(); action(sub); } public void subMethod() { } public <T extends Iface> void action(T input) { input.subMethod(); } }"),
                new File("u/Iface.java", "package u; public interface Iface { public void subMethod(); }"));
    }

    public void test128676c() throws Exception { // #128676 - [Use Supertype] Refactoring does not respect bound generic type
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Main sub = new Main(); action(sub); } public void subMethod() { } public void action(Main input) { input.subMethod(); } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
        performUseSuperType(src.getFileObject("u/Main.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Main sub = new Main(); action(sub); } public void subMethod() { } public void action(Main input) { input.subMethod(); } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
    }
    
    public void test128674a() throws Exception { // #128674 - [Use Supertype] refactoring can produce duplicate method declaration
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Main sub = new Main(); action(sub); } public void subMethod() { } public void action(Main input) { System.out.println(input.toString()); } public void action(Iface input) { System.out.println(input.toString()); } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
        performUseSuperType(src.getFileObject("u/Main.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Main sub = new Main(); action(sub); } public void subMethod() { } public void action(Main input) { System.out.println(input.toString()); } public void action(Iface input) { System.out.println(input.toString()); } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
    }
     
    public void test128674b() throws Exception { // #128674 - [Use Supertype] refactoring can produce duplicate method declaration
        writeFilesAndWaitForScan(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Main sub = new Main(); action(sub); } public void subMethod() { } public void action(Iface input) { System.out.println(input.toString()); } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
        performUseSuperType(src.getFileObject("u/Main.java"), 1);
        verifyContent(src,
                new File("t/package-info.java", "package t;"),
                new File("u/Main.java", "package u; public class Main implements Iface { public void method() { Iface sub = new Main(); action(sub); } public void subMethod() { } public void action(Iface input) { System.out.println(input.toString()); } }"),
                new File("u/Iface.java", "package u; public interface Iface { }"));
    }

    private void performUseSuperType(FileObject source, final int position, Problem... expectedProblems) throws Exception {
        final UseSuperTypeRefactoring[] r = new UseSuperTypeRefactoring[1];

        JavaSource.forFileObject(source).runUserActionTask(new Task<CompilationController>() {

            @Override
            public void run(CompilationController parameter) throws Exception {
                parameter.toPhase(JavaSource.Phase.RESOLVED);
                CompilationUnitTree cut = parameter.getCompilationUnit();

                ClassTree classTree = (ClassTree) cut.getTypeDecls().get(0);
                TreePath tp = TreePath.getPath(cut, classTree);
                r[0] = new UseSuperTypeRefactoring(TreePathHandle.create(tp, parameter));
                r[0].setTargetSuperType(r[0].getCandidateSuperTypes()[position]);
            }
        }, true);

        RefactoringSession rs = RefactoringSession.create("Session");
        List<Problem> problems = new LinkedList<Problem>();

        addAllProblems(problems, r[0].preCheck());
        addAllProblems(problems, r[0].prepare(rs));
        addAllProblems(problems, rs.doRefactoring(true));

        assertProblems(Arrays.asList(expectedProblems), problems);
    }
}
