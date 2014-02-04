package com.masstransitproject.crosstown;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Copyright 2010 Chris Patterson, Evan Schnell
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

public class MessageUrn {

	private static final Logger _log = LogManager.getLogger(MessageUrn.class);

	private URI myUri;

	// May have both java and .Net entries
	private static Map<String, Class<?>> _nameToClassCache = new HashMap<String, Class<?>>();
	private static Map<Class<?>, String> _typeToUrnCache = new HashMap<Class<?>, String>();

	public MessageUrn(Class<?> type) throws URISyntaxException {
		myUri = new URI(getUrnForType(type));

	}

	public static void registerMessageType(
			Class<?> messageType) {

		try {
			_nameToClassCache.put(messageType.getName(), messageType);

			// Cache dotNet if possible
			if (ExternallyNamespaced.class.isAssignableFrom(messageType)) {
				// Use dotNet Namespaces
				_nameToClassCache.put(
						((ExternallyNamespaced) messageType.newInstance())
								.getExternalNamespace()
								+ "."
								+ messageType.getSimpleName(), messageType);
			}
		} catch (InstantiationException e) {
			_log.error("Unable to create class for " + messageType, e);
		} catch (IllegalAccessException e) {
			_log.error("Unable to access class for " + messageType, e);
		}

	}

	public MessageUrn(String uriString) throws URISyntaxException {
		myUri = new URI(uriString);

	}

	public Class<?> getMessageClass() {
		return this.getMessageClass(true, true);
	}

	public Class<?> getMessageClass(boolean throwOnError, boolean ignoreCase) {
		if (myUri == null || myUri.getSchemeSpecificPart() == null
				|| myUri.getSchemeSpecificPart().isEmpty()) // Was
															// segments.lenght==0
			return null;

		String[] names = myUri.getSchemeSpecificPart().split(":");
		if (!"message".equals(names[0]))
			return null;

		String typeName;

		if (names.length == 2)
			typeName = names[1];
		else if (names.length == 3)
			typeName = names[1] + "." + names[2];// + ", " + names[1];
		else if (names.length >= 4)
			typeName = names[1] + "." + names[2];// + ", " + names[3];
		else
			return null;

		// This might be a .Net name rather than a Java one so
		// we must check cache before instantiating
		Class<?> messageType = _nameToClassCache.get(typeName);
		if (messageType == null) {

			try {
				messageType = Class.forName(typeName);
			} catch (ClassNotFoundException e) {
				_log.info("Unable to create class for " + messageType +".  This may be because we are using a .Net name", e);
			}
			if (messageType != null) {
				registerMessageType(messageType);
			}
		}
		return messageType;
	}

	@Override
	public String toString() {
		return myUri.toString();
	}

	static String isInCache(Class<?> type) {

		_log.trace("Getting type " + type + " from cache " + _typeToUrnCache);
		String urn = _typeToUrnCache.get(type);
		if (urn == null) {

			StringBuilder sb = new StringBuilder("urn:message:");

			urn = getMessageName(sb, type, true);
			_typeToUrnCache.put(type, urn);
		}
		return urn;
	}

	static String getUrnForType(Class<?> type) {
		return isInCache(type);
	}

	static String getMessageName(StringBuilder sb, Class<?> type,
			boolean includeScope) {

		// Crosstown does not support complex generated names since they won't
		// translate to
		// dotNet
		if (includeScope) {
			String scope = null;
			if (ExternallyNamespaced.class.isAssignableFrom(type)) {
				// Use dotNet Namespaces

				try {
					scope = ((ExternallyNamespaced) type.newInstance())
							.getExternalNamespace();
				} catch (Exception e) {
					_log.error(
							"Unable to obtain namespace for "
									+ type
									+ ".  ExternallyNamespaced classes must have a default constructor.",
							e);
				}
			} else {
				// Use Java Packages
				scope = type.getPackage().getName();
			}
			if (scope != null && !scope.isEmpty()) {
				sb.append(scope);
				sb.append(":");
			}
		}

		sb.append(type.getSimpleName());

		return sb.toString();

	}
}
