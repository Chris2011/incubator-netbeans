<?php
//START
class UnusedVariableClassName {
    public static $staticField;
    public function __construct() {}
    function functionName() {$this->functionName();}
    function formalParams($first, $second) {}
}
$GLOBALS["a"];
$_SERVER["a"];
$_GET["a"];
$_POST["a"];
$_FILES["a"];
$_COOKIE["a"];
$_SESSION["a"];
$_REQUEST["a"];
$_ENV["a"];
echo $echoUsed;

$simpleUnused;

include  $incUsed . '/foo.php';

$funcName = "myFunc";
$funcName();

$c = new UnusedVariableClassName();
$methodName = "functionName";
$c->$methodName();

if ($ifUsed) {}

$result = ($instanceUsed instanceof Foo);

$postfixUsed++;
++$prefixUsed;

$cloned = clone $c;

$casted = (int) $flt;

$assign = $rightUsed;

$condRes = $cond ? $true : $false;

function functionName() {
    return $retUsed;
}

switch ($swiUsed) {
    default:
        break;
}

throw $ex;

$cls = new $clsName($prm);

do {
} while ($doUsed);

foreach ($arrayUsed as $key => $value) {
}

for ($indexUsed = 0; $indexUsed < 5; $indexUsed++) {
}

$staticClassUsed::method();

while ($whileUsed) {
}

$fnc = function($formUsed) use($lexUsed) {};

$staticAnotherClass::$staticField;

//END
?>