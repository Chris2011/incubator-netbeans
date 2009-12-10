/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */
package org.netbeans.modules.web.beans.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.netbeans.modules.j2ee.metadata.model.api.MetadataModel;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModelAction;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModelException;
import org.netbeans.modules.j2ee.metadata.model.support.TestUtilities;
import org.netbeans.modules.web.beans.api.model.Result;
import org.netbeans.modules.web.beans.api.model.WebBeansModel;
import org.netbeans.modules.web.beans.impl.model.results.ResultImpl;


/**
 * @author ads
 *
 */
public class ModelTest extends CommonTestCase {

    public ModelTest( String testName ) {
        super(testName);
    }
    
    
    public void testCommon() throws MetadataModelException, IOException,
        InterruptedException 
    {
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/CustomBinding.java",
                "package foo; " +
                "import static java.lang.annotation.ElementType.METHOD; "+
                "import static java.lang.annotation.ElementType.FIELD; "+
                "import static java.lang.annotation.ElementType.PARAMETER; "+
                "import static java.lang.annotation.ElementType.TYPE; "+
                "import static java.lang.annotation.RetentionPolicy.RUNTIME; "+
                "import javax.enterprise.inject.*; "+
                "import javax.inject.*; "+
                "import java.lang.annotation.*; "+
                "@Qualifier " +
                "@Retention(RUNTIME) "+
                "@Target({METHOD, FIELD, PARAMETER, TYPE}) "+
                "@Inherited "+
                "public @interface CustomBinding  {" +
                "    String value(); "+
                "    @Nonbinding String comment() default \"\"; "+
                "}");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/CustomClass.java",
                "package foo; " +
                "import javax.enterprise.inject.*; "+
                "import javax.inject.*; "+
                "public class CustomClass  {" +
                " @Inject @foo.CustomBinding(value=\"a\") Object myFieldA;  "+
                " String myText[]; "+
                " @Inject @foo.CustomBinding(value=\"d\", comment=\"c\")  int myIndex; "+
                " Class<String> myClass; "+
                " @Inject @foo.CustomBinding(value=\"b\", comment=\"comment\")" +
                " foo.Generic<? extends Thread> myThread; "+
                " @Inject @foo.CustomBinding(value=\"c\")" +
                " foo.Generic<MyThread> myGen; "+
                " @Inject @foo.CustomBinding(value=\"e\") Thread myFieldB; "+
                " void method( Object param ){}"+
                "}");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/One.java",
                "package foo; " +
                "@foo.CustomBinding(value=\"a\") " +
                "public class One  {}" );
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Two.java",
                "package foo; " +
                "public class Two  extends One {}" );
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Three.java",
                "package foo; " +
                "import javax.enterprise.inject.*; "+
                "import javax.inject.*; "+
                "@foo.CustomBinding() " +
                "public class Three  { " +
                " @Produces @foo.CustomBinding(value=\"d\") " +
                "int productionField =1; " +
                "}" );
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Generic.java",
                "package foo; " +
                "import javax.enterprise.inject.*; "+
                "@foo.CustomBinding(\"c\") "+
                "public class Generic<T extends foo.MyThread>  {" +
                " @Produces @foo.CustomBinding(value=\"e\") foo.MyThread getThread(){" +
                " return null; } "+
                "}" );
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Generic1.java",
                "package foo; " +
                "import javax.enterprise.inject.*; "+
                "@foo.CustomBinding(\"b\") "+
                "public class Generic1<T extends foo.MyThread>  extends " +
                "Generic<T>{}");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/MyThread.java",
                "package foo; " +
                "public class MyThread extends Thread  {}" );
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/MyClass.java",
                "package foo; " +
                "public class MyClass extends Class<String>  {}" );
        
        inform("start common test");
        
        TestWebBeansModelImpl modelImpl = createModelImpl();
        final TestWebBeansModelProviderImpl provider = modelImpl.getProvider();
        MetadataModel<WebBeansModel> testModel = modelImpl.createTestModel();
        
        testModel.runReadAction( new MetadataModelAction<WebBeansModel,Void>(){

            public Void run( WebBeansModel model ) throws Exception {
                TypeMirror mirror = model.resolveType( "foo.CustomClass" );
                Element clazz = ((DeclaredType)mirror).asElement();
                List<? extends Element> children = clazz.getEnclosedElements();
                List<VariableElement> injectionPoints = 
                    new ArrayList<VariableElement>( children.size());
                for (Element element : children) {
                    if ( element instanceof VariableElement ){
                        injectionPoints.add( (VariableElement)element);
                    }
                    else if ( element instanceof ExecutableElement ){
                        List<? extends VariableElement> params = 
                            ((ExecutableElement)element).getParameters();
                        for (VariableElement variableElement : params) {
                            injectionPoints.add( variableElement );
                        }
                    }
                }
                
                Set<String> names = new HashSet<String>(); 
                for( VariableElement element : injectionPoints ){
                    names.add( element.getSimpleName().toString() );
                    if ( element.getSimpleName().contentEquals("myFieldA")){
                        checkA( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myGen")){
                        checkGen( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myIndex")){
                        checkIndex( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myFieldB")){
                        checkB( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myThread")){
                        checkThread( element , provider);
                    }
                }
                
                assert names.contains("myFieldA");
                assert names.contains("myGen");
                assert names.contains("myIndex");
                assert names.contains("myFieldB");
                assert names.contains("myThread");
                
                return null;
            }

        });
    }
    
    public void testMixedBindings() throws MetadataModelException, IOException, 
        InterruptedException 
    {
        TestUtilities.copyStringToFileObject(srcFO, "foo/Binding1.java",
                "package foo; " +
                "import static java.lang.annotation.ElementType.METHOD; "+
                "import static java.lang.annotation.ElementType.FIELD; "+
                "import static java.lang.annotation.ElementType.PARAMETER; "+
                "import static java.lang.annotation.ElementType.TYPE; "+
                "import static java.lang.annotation.RetentionPolicy.RUNTIME; "+
                "import javax.enterprise.inject.*; "+
                "import java.lang.annotation.*; "+
                "import javax.inject.*; "+
                "@Qualifier " +
                "@Retention(RUNTIME) "+
                "@Target({METHOD, FIELD, PARAMETER, TYPE}) "+
                "public @interface Binding1  {" +
                "    String value(); "+
                "    @Nonbinding String comment() default \"\"; "+
                "}");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Binding2.java",
                "package foo; " +
                "import static java.lang.annotation.ElementType.METHOD; "+
                "import static java.lang.annotation.ElementType.FIELD; "+
                "import static java.lang.annotation.ElementType.PARAMETER; "+
                "import static java.lang.annotation.ElementType.TYPE; "+
                "import static java.lang.annotation.RetentionPolicy.RUNTIME; "+
                "import javax.enterprise.inject.*; "+
                "import javax.inject.*; "+
                "import java.lang.annotation.*; "+
                "@Qualifier " +
                "@Retention(RUNTIME) "+
                "@Target({METHOD, FIELD, PARAMETER, TYPE}) "+
                "public @interface Binding2  {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Binding3.java",
                "package foo; " +
                "import static java.lang.annotation.ElementType.METHOD; "+
                "import static java.lang.annotation.ElementType.FIELD; "+
                "import static java.lang.annotation.ElementType.PARAMETER; "+
                "import static java.lang.annotation.ElementType.TYPE; "+
                "import static java.lang.annotation.RetentionPolicy.RUNTIME; "+
                "import javax.enterprise.inject.*; "+
                "import javax.inject.*; "+
                "import java.lang.annotation.*; "+
                "@Qualifier " +
                "@Retention(RUNTIME) "+
                "@Target({METHOD, FIELD, PARAMETER, TYPE}) "+
                "public @interface Binding3  {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Binding4.java",
                "package foo; " +
                "import static java.lang.annotation.ElementType.METHOD; "+
                "import static java.lang.annotation.ElementType.FIELD; "+
                "import static java.lang.annotation.ElementType.PARAMETER; "+
                "import static java.lang.annotation.ElementType.TYPE; "+
                "import static java.lang.annotation.RetentionPolicy.RUNTIME; "+
                "import javax.enterprise.inject.*; "+
                "import java.lang.annotation.*; "+
                "import javax.inject.*; "+
                "@Qualifier " +
                "@Retention(RUNTIME) "+
                "@Target({METHOD, FIELD, PARAMETER, TYPE}) "+
                "public @interface Binding4  {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Iface1.java",
                "package foo; " +
                "public interface Iface1 {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Iface2.java",
                "package foo; " +
                "public interface Iface2 {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Iface3.java",
                "package foo; " +
                "public interface Iface3 extends Iface1 {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Clazz1.java",
                "package foo; " +
                "import javax.enterprise.inject.*; "+
                "public class Clazz1 {" +
                " @Produces @foo.Binding1(\"b\") int productionField1 = 1; " +
                " @Produces @foo.Binding3 @foo.Binding2 String[] productionField2 = new String[0]; " +
                " @Produces @foo.Binding1(\"c\") @foo.Binding4 Clazz3 productionMethod() " +
                "{ return null; } " +
                "} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Clazz2.java",
                "package foo; " +
                "@foo.Binding1(\"a\") "+
                "public class Clazz2 extends Clazz1 {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Clazz3.java",
                "package foo; " +
                "@foo.Binding3 @foo.Binding2 "+
                "public class Clazz3 extends Clazz2 implements Iface1 {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Clazz4.java",
                "package foo; " +
                "@foo.Binding3 @foo.Binding1(\"a\") "+
                "public class Clazz4 implements Iface2, Iface3 {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Clazz5.java",
                "package foo; " +
                "@foo.Binding3 @foo.Binding1(\"b\") "+
                "public class Clazz5 implements Iface3 {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/Clazz6.java",
                "package foo; " +
                "@foo.Binding1(\"a\") @foo.Binding2 @foo.Binding4 "+
                "public class Clazz6 extends Clazz2 {} ");
        
        TestUtilities.copyStringToFileObject(srcFO, "foo/TestClass.java",
                "package foo; " +
                "import javax.inject.*; "+
                "public class TestClass  { " +
                " @Inject @foo.Binding3 @foo.Binding1(\"a\") Iface1 myFieldA; "+
                " @Inject @foo.Binding2 Iface1 myFieldB; "+
                " @Inject @foo.Binding3 Clazz1 myFieldC; "+
                " @Inject @foo.Binding2 @foo.Binding4  Clazz2 myFieldD; "+
                " @Inject @foo.Binding1(\"b\") Integer myFieldE; "+
                " @Inject @foo.Binding3 @foo.Binding2 String[] myFieldF; "+
                " @Inject @foo.Binding1(\"c\") @foo.Binding4 Clazz1 myFieldG; "+
                "} ");
        
        inform("start mixed binding test");
        
        TestWebBeansModelImpl modelImpl = createModelImpl();
        final TestWebBeansModelProviderImpl provider = modelImpl.getProvider();
        MetadataModel<WebBeansModel> testModel = modelImpl.createTestModel();
        
        testModel.runReadAction( new MetadataModelAction<WebBeansModel,Void>(){

            public Void run( WebBeansModel model ) throws Exception {
                TypeMirror mirror = model.resolveType( "foo.TestClass" );
                Element clazz = ((DeclaredType)mirror).asElement();
                List<? extends Element> children = clazz.getEnclosedElements();
                List<VariableElement> injectionPoints = 
                    new ArrayList<VariableElement>( children.size());
                for (Element element : children) {
                    if ( element instanceof VariableElement ){
                        injectionPoints.add( (VariableElement)element);
                    }
                }
                
                Set<String> names = new HashSet<String>(); 
                for( VariableElement element : injectionPoints ){
                    names.add( element.getSimpleName().toString() );
                    if ( element.getSimpleName().contentEquals("myFieldA")){
                        checkMixedA( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myFieldB")){
                        checkMixedB( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myFieldC")){
                        checkMixedC( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myFieldD")){
                        checkMixedD( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myFieldE")){
                        checkMixedE( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myFieldF")){
                        checkMixedF( element , provider);
                    }
                    else if ( element.getSimpleName().contentEquals("myFieldG")){
                        checkMixedG( element , provider);
                    }
                }
                
                assert names.contains("myFieldA");
                assert names.contains("myFieldB");
                assert names.contains("myFieldC");
                assert names.contains("myFieldD");
                assert names.contains("myFieldE");
                assert names.contains("myFieldF");
                assert names.contains("myFieldG");
                return null;
            }
        });
        
    }
    
    protected void checkMixedG( VariableElement element, 
            TestWebBeansModelProviderImpl provider) 
    {
        inform("test field myFieldG");

        Result result = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue(result instanceof ResultImpl);
        Set<TypeElement> typeElements = ((ResultImpl) result).getTypeElements();
        Set<Element> productions = ((ResultImpl) result).getProductions();

        assertEquals(0, typeElements.size());
        assertEquals(1, productions.size());

        Element injectable = productions.iterator().next();
        
        assertTrue("Expect production method as injectable for 'myFieldG' "
                + "field, but found :" + injectable.getKind(),
                injectable instanceof ExecutableElement);
        assertEquals("productionMethod", injectable.getSimpleName().toString());
    }

    protected void checkMixedF( VariableElement element, 
            TestWebBeansModelProviderImpl provider) 
    {
        inform("test field myFieldF");
        
        Result result = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue(result instanceof ResultImpl);
        Set<TypeElement> typeElements = ((ResultImpl) result).getTypeElements();
        Set<Element> productions = ((ResultImpl) result).getProductions();

        assertEquals(0, typeElements.size());
        assertEquals(1, productions.size());

        Element injectable = productions.iterator().next();

        assertTrue("Expect production field as injectable for 'myFieldF' "
                + "field, but found :" + injectable.getKind(),
                injectable instanceof VariableElement);
        assertEquals("productionField2", injectable.getSimpleName().toString());
    }

    protected void checkMixedE( VariableElement element, 
            TestWebBeansModelProviderImpl provider) 
    {
        inform("test field myFieldE");
        
        Result result = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue(result instanceof ResultImpl);
        Set<TypeElement> typeElements = ((ResultImpl) result).getTypeElements();
        Set<Element> productions = ((ResultImpl) result).getProductions();

        assertEquals(0, typeElements.size());
        assertEquals(1, productions.size());

        Element injectable = productions.iterator().next();

        assertTrue("Expect production field as injectable for 'myFieldE' "
                + "field, but found :" + injectable.getKind(),
                injectable instanceof VariableElement);
        assertEquals("productionField1", injectable.getSimpleName().toString());
    }

    protected void checkMixedD( VariableElement element, 
            TestWebBeansModelProviderImpl provider )
    {
        inform("test field myFieldD");
        
        Result result = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue(result instanceof ResultImpl);
        Set<TypeElement> typeElements = ((ResultImpl) result).getTypeElements();
        Set<Element> productions = ((ResultImpl) result).getProductions();

        assertEquals(1, typeElements.size());
        assertEquals(0, productions.size());

        TypeElement injectable = typeElements.iterator().next();

        assertEquals("foo.Clazz6", injectable.getQualifiedName().toString());
    }

    protected void checkMixedC( VariableElement element, 
            TestWebBeansModelProviderImpl provider) 
    {
        inform("test field myFieldC");

        Result result = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue(result instanceof ResultImpl);
        Set<TypeElement> typeElements = ((ResultImpl) result).getTypeElements();
        Set<Element> productions = ((ResultImpl) result).getProductions();

        assertEquals(1, typeElements.size());
        assertEquals(0, productions.size());

        TypeElement injectable = typeElements.iterator().next();

        assertEquals("foo.Clazz3", injectable.getQualifiedName().toString());
    }

    protected void checkMixedB( VariableElement element, 
            TestWebBeansModelProviderImpl provider ) 
    {
        inform("test field myFieldB");
        
        Result result = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue( result instanceof ResultImpl );
        Set<TypeElement> typeElements = ((ResultImpl)result).getTypeElements();
        Set<Element> productions = ((ResultImpl)result).getProductions();
        
        assertEquals(1 , typeElements.size());
        assertEquals(0 , productions.size());
        
        TypeElement injectable = typeElements.iterator().next();

        assertEquals("foo.Clazz3", injectable.getQualifiedName().toString());
    }

    protected void checkMixedA( VariableElement element,
            TestWebBeansModelProviderImpl provider )
    {
        inform("test field myFieldA");
        Result result = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue( result instanceof ResultImpl );
        Set<TypeElement> typeElements = ((ResultImpl)result).getTypeElements();
        Set<Element> productions = ((ResultImpl)result).getProductions();
        
        assertEquals(1 , typeElements.size());
        assertEquals(0 , productions.size());
        
        TypeElement injectable = typeElements.iterator().next();
        
        assertNotNull(injectable);
        assertEquals("foo.Clazz4", injectable.getQualifiedName().toString());
    }

    protected void checkB( VariableElement element, 
            TestWebBeansModelProviderImpl provider  ) 
    {
        inform("test field myFieldB");
        Result result  = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue( result instanceof ResultImpl );
        Set<TypeElement> typeElements = ((ResultImpl)result).getTypeElements();
        Set<Element> productions = ((ResultImpl)result).getProductions();
        
        assertEquals(0 , typeElements.size());
        assertEquals(1 , productions.size());
        Element production = productions.iterator().next();
        assertTrue("Found injectable should be a method, but found "
                + production.getKind(), production instanceof ExecutableElement);
        assertEquals("getThread", ((ExecutableElement) production).getSimpleName()
                .toString());
    }

    protected void checkThread( VariableElement element, 
            TestWebBeansModelProviderImpl provider  ) 
    {
        inform("test field myThread");
        Result result = provider.findVariableInjectable(element, null);

        assertNotNull(result);
        assertTrue( result instanceof ResultImpl );
        Set<TypeElement> typeElements = ((ResultImpl)result).getTypeElements();
        Set<Element> productions = ((ResultImpl)result).getProductions();
        
        assertEquals(1 , typeElements.size());
        assertEquals(0 , productions.size());
        
        TypeElement injectable = typeElements.iterator().next();
        
        assertNotNull(injectable);
        assertEquals("foo.Generic1", injectable.getQualifiedName().toString());
    }

    private void checkA( VariableElement element, 
            TestWebBeansModelProviderImpl provider ) 
    {
        inform("test field myFieldA");
        Result result = provider.findVariableInjectable(element, null);
        
        assertNotNull(result);
        assertTrue( result instanceof ResultImpl );
        Set<TypeElement> typeElements = ((ResultImpl)result).getTypeElements();
        Set<Element> productions = ((ResultImpl)result).getProductions();

        assertEquals(2, typeElements.size());
        assertEquals(0, productions.size());
        
        Set<String> set = new HashSet<String>();
        for (TypeElement injactable : typeElements) {
            set.add(injactable.getQualifiedName().toString());
        }

        assertTrue("Result of typesafe resolution should contains foo.One"
                + " class definition", set.contains("foo.One"));
        assertTrue("Result of typesafe resolution should contains foo.Two"
                + " class definition", set.contains("foo.Two"));
    }

    private void checkGen( VariableElement element, 
            TestWebBeansModelProviderImpl provider  ) 
    {
        inform("test field myGen");
        Result result = provider.findVariableInjectable(element, null);
        
        assertNotNull(result);
        assertTrue( result instanceof ResultImpl );
        Set<TypeElement> typeElements = ((ResultImpl)result).getTypeElements();
        Set<Element> productions = ((ResultImpl)result).getProductions();
        
        assertEquals(1, typeElements.size());
        assertEquals(0, productions.size());
        
        TypeElement injactable = typeElements.iterator().next();
        assertNotNull( injactable );
        
        assertEquals("foo.Generic", injactable.getQualifiedName().toString());
    }

    private void checkIndex( VariableElement element, 
            TestWebBeansModelProviderImpl provider  ) 
    {
        inform("test field myIndex");
        Result result = provider.findVariableInjectable(element, null);
        
        assertNotNull(result);
        assertTrue( result instanceof ResultImpl );
        Set<TypeElement> typeElements = ((ResultImpl)result).getTypeElements();
        Set<Element> productions = ((ResultImpl)result).getProductions();
        
        assertEquals(0, typeElements.size());
        assertEquals(1, productions.size());

        Element injectable = productions.iterator().next();
        
        assertNotNull(injectable);

        assertEquals("productionField", injectable.getSimpleName().toString());
    }

}
