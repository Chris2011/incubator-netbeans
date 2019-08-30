<?php
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

namespace Foo;
class UnpackClass {
    public const CONSTANT = ["1", "2", "3"];
    public const CONSTANT2 = [...self::CONSTANT];
    public const CONSTANT3 = [...self::CONSTANT, "4"];
    public const CONSTANT4 = ["0", ...self::CONSTANT, "4"];
}

class UnpackChildClass extends UnpackClass {
    public const CHILD_CONSTANT = ["0", ...parent::CONSTANT, ];
}

namespace Bar;
use Foo\UnpackClass;
class AnotherNamespaceUnpackClass {
    private const CONST1 = [...UnpackClass::CONSTANT];
    private const CONST2 = [1, ...UnpackClass::CONSTANT];
    private const CONST3 = [1, ...\Foo\UnpackClass::CONSTANT, 4];
}
