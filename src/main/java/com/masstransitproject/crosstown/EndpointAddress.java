package com.masstransitproject.crosstown;

//Copyright 2007-2012 Chris Patterson, Dru Sellers, Travis Smith, et. al.
//
//Licensed under the Apache License, Version 2.0 (the "License"); you may not use
//this file except in compliance with the License. You may obtain a copy of the 
//License at 
//
//http://www.apache.org/licenses/LICENSE-2.0 
//
//Unless required by applicable law or agreed to in writing, software distributed
//under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
//CONDITIONS OF ANY KIND, either express or implied. See the License for the 
//specific language governing permissions and limitations under the License.

import java.net.URI;

/**
 * The address of a crosstown endpoint
 * 
 */
public interface EndpointAddress {

	/**
	 * The internal representation for the address
	 * 
	 * @return
	 */
	URI getUri();

	/**
	 * Indicate if a URI is the local machine
	 * 
	 * @return true if a URI refers to localhost or the local system
	 */
	boolean isLocal();

	/**
	 * Indicate if a URI refers to a transactional queue
	 * 
	 * @return true if the address is transactional
	 */
	boolean isTransactional();
}
