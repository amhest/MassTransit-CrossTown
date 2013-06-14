// Copyright 2007-2008 The Apache Software Foundation.
//  
// Licensed under the Apache License, Version 2.0 (the "License"); you may not use 
// this file except in compliance with the License. You may obtain a copy of the 
// License at 
// 
//     http://www.apache.org/licenses/LICENSE-2.0 
// 
// Unless required by applicable law or agreed to in writing, software distributed 
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
// CONDITIONS OF ANY KIND, either express or implied. See the License for the 
// specific language governing permissions and limitations under the License.
package com.masstransitproject.crosstown;

import java.net.URI;


	/// <summary>
	/// Identifies an endpoint
	/// </summary>
    public interface IEndpointAddress
    {
        /// <summary>
        /// The URI used to access the endpoint
        /// </summary>
        URI getUri();

        /// <summary>
        /// True if the endpoint is local to this machine
        /// </summary>
        boolean IsLocal();

        /// <summary>
        /// Was transactional requested by the Uri
        /// </summary>
        boolean IsTransactional();
    }
