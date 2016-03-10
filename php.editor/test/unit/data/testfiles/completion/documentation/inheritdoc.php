<?php

/**
 * The summary of BaseClass.
 *
 * Description of BaseClass.
 * @author junichi11
 */
class BaseClass {

    /**
     * testOnlyTag method of BaseClass.
     *
     * testOnlyTag method description of BaseClass.
     * @param string $param1 param1 description of BaseClass
     * @param int $param2 param2 description of BaseClass
     */
    public function testOnlyTag($param1, $param2) {
    }

    /**
     * testInline method of BaseClass.
     *
     * testInline method description of BaseClass.
     * @param string $param1 param1 description of BaseClass
     * @param int $param2 param2 description of BaseClass
     */
    public function testInline($param1, $param2) {
    }

    /**
     * testMissingParam method of BaseClass.
     *
     * testMissingParam method description of BaseClass.
     * @param string $param1 param1 description of BaseClass
     */
    public function testMissingParam($param1) {
    }

    /**
     * testNoInheritdoc method of BaseClass.
     *
     * testNoInheritdoc method description of BaseClass.
     * @param string $param1 param1 description of BaseClass
     * @return $this Description of BaseClass
     */
    public function testNoInheritdoc($param1){
    }

    /**
     * testInvalidTag method of BaseClass.
     *
     * testInvalidTag method description of BaseClass.
     */
    public function testInvalidTag() {
    }

    /**
     * testNoDoc method of BaseClass.
     *
     * testNoDoc method description of BaseClass.
     */
    public function testNoDoc() {
    }

}

/**
 * {@inheritdoc}
 */
class ChildClass extends BaseClass {

    /**
     * {@inheritdoc }
     */
    public function testOnlyTag($param1, $param2) {
    }

    /**
     * testMissingParam method of ChildClass.
     *
     * testMissingParam method description of ChildClass.
     */
    public function testMissingParam($param1) {
    }

    /**
     * @inheritdoc
     */
    public function testInvalidTag() {
    }

    /**
     * testNoInheritdoc method of ChildClass.
     *
     * testNoInheritdoc method description of ChildClass.
     * @param string $param1 param1 description of ChildClass
     * @return $this Description of ChildClass
     */
    public function testNoInheritdoc($param1){
    }

    public function testNoDoc() {
    }

}

/**
 * The summary of ChildInlineTagClass.
 *
 * {@inheritdoc } Description of ChildInlineTagClass.
 */
class ChildInlineTagClass extends BaseClass {
}

class GrandchildInlineTagClass extends ChildInlineTagClass {
}

/**
 * The summary of BaseInterface.
 *
 * Description of BaseInterface.
 * @author junichi11
 */
interface BaseInterface {
}

/**
 * {@inheritdoc}
 */
interface ChildInterface extends BaseInterface {

    /**
     * childInterfaceMethod.
     *
     * Description of childInterfaceMethod.
     */
    public function childInterfaceMethod();
}

/**
 * The summary of ChildInlineTagInterface.
 *
 * {@inheritdoc} Description of ChildInlineTagInterface.
 */
interface ChildInlineTagInterface extends BaseInterface {
}

interface GrandchildInlineTagInterface extends ChildInlineTagInterface {
}

/**
 * {@inheritDoc}
 */
class GrandchildClass extends ChildClass implements ChildInterface {

    /**
     * {@inheritDoc}
     */
    public function testOnlyTag($param1, $param2) {
    }

    /**
     * The summary of GrandChildClass.
     *
     * {@inheritdoc} Description of GrandChildClass.
     * @param type $param1 {@inheritDoc} param1 description of GrandchildClass
     * @param type $param2 {@inheritDoc} param2 description of GrandchildClass
     */
    public function testInline($param1, $param2) {
    }

    /**
     * 
     * 
     *    {@inheritDoc }
     */
    public function childInterfaceMethod() {
    }

}

$childClass = new ChildClass();
$childClass->testOnlyTag($param1, $param2);
$childClass->testMissingParam($param1);
$childClass->testNoDoc();
$childClass->testNoInheritdoc();
$childClass->testInvalidTag();

$grandchildClass = New GrandchildClass();
$grandchildClass->testOnlyTag($param1, $param2);
$grandchildClass->testInline($param1, $param2);
$grandchildClass->childInterfaceMethod();
