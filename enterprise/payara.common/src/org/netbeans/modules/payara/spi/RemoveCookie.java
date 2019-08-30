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

package org.netbeans.modules.payara.spi;

/**
 *
 * @author Peter Williams
 */
public interface RemoveCookie {
    
    /**
     * Called when the associated server instance is being removed from the IDE.
     * 
     * Addon modules should implement this if they need notification when the
     * server instance is removed from the IDE (e.g. JavaEE module needs to
     * remove corresponding JavaEE server instance.)
     * 
     * @param serverUri uri of server instance being removed although the addon
     *   module should already have this information.
     */
    public void removeInstance(String serverUri);

}
