package com.masstransitproject.crosstown;

// Copyright 2013-2014 Evan Schnell
//
// Licensed under the Apache License, Version 2.0 (the "License"); you may not use 
// this file except in compliance with the License. You may obtain a copy of the 
// License at 
//
// http://www.apache.org/licenses/LICENSE-2.0 
//
// Unless required by applicable law or agreed to in writing, software distributed 
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
// CONDITIONS OF ANY KIND, either express or implied. See the License for the 
// specific language governing permissions and limitations under the License.

/**
 * This is an interface which may be implemented by a message class to bridge
 * the namespace differences between Java and .Net
 * 
 */
public interface ExternallyNamespaced {

	/**
	 * Return the .Net namespace for a Java class
	 * 
	 * @return The .Net namespace for a system that may consume our messages
	 */
	public String getExternalNamespace();
}
