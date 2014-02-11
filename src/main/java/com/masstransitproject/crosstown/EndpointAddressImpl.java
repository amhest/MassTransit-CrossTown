package com.masstransitproject.crosstown;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

// Copyright 2007-2011 Evan Schnell, Chris Patterson, Dru Sellers, Travis Smith, et. al.
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

public class EndpointAddressImpl implements EndpointAddress {
	protected static final String LOCAL_MACHINE_NAME;
	static {
		String t = "localhost";
		try {
			t = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
		}
		LOCAL_MACHINE_NAME = t;
	}

	private boolean _isTransactional;
	private URI _uri;

	public EndpointAddressImpl(URI uri) {
		if (uri == null) {
			throw new IllegalArgumentException(
					"The URI argument was not specified");
		}
		_uri = uri;

		_isTransactional = checkForTransactionalHint(uri, false);
	}

	/**
	 * Build an endpoint address around a properly formatted URI String
	 * 
	 * @param uriString
	 */
	public EndpointAddressImpl(String uriString) {

		if (uriString == null || uriString.isEmpty()) {
			throw new IllegalArgumentException(
					"The URI argument was not specified");
		}
		try {
			_uri = new URI(uriString);
		} catch (URISyntaxException ex) {
			throw new IllegalArgumentException("The URI is invalid: "
					+ uriString, ex);
		}

		_isTransactional = checkForTransactionalHint(_uri, false);
	}

	@Override
	public URI getUri() {
		return _uri;
	}

	protected void setUri(URI value) {
		_uri = value;
	}

	public String getPath() {
		return getUri().getRawPath().substring(1);
	}

	@Override
	public boolean isTransactional() {
		return _isTransactional;
	}

	public void setIsTransactional(boolean value) {
		_isTransactional = value;
	}

	@Override
	public String toString() {
		return "[EndPoint Address: " + _uri.toString() + "]";
	}

	@Override
	public boolean isLocal() {
		return isLocal(getUri());
	}

	protected boolean isLocal(URI uri) {
		String hostName = uri.getHost();
		boolean local = (".".equals(hostName) || "localhost".equals(hostName) || LOCAL_MACHINE_NAME
				.equals(hostName));

		return local;
	}

	/**
	 * Check whether the URI has a transactional hint specified by an argument
	 * named "tx"
	 * 
	 * @param uri
	 * @param defaultTransactional
	 * @return
	 */
	protected static boolean checkForTransactionalHint(URI uri,
			boolean defaultTransactional) {

		List<NameValuePair> params = URLEncodedUtils.parse(uri, null);
		for (NameValuePair nvp : params) {
			if ("tx".equals(nvp.getName())) {
				return "true".equalsIgnoreCase(
						nvp.getValue());
			}
		}
		return false;
	}

}
