package com.masstransitproject.crosstown.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// Copyright 2007-2011 Chris Patterson, Dru Sellers, Travis Smith, et. al.
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

class MessageHeadersImpl implements MessageHeaders {
	final Map<String, String> _headers;

	public MessageHeadersImpl() {
		_headers = new HashMap<String, String>();
	}

	public MessageHeadersImpl(Set<Entry<String, String>> headers) {
		this._headers = new HashMap<String, String>();
		for (Entry<String, String> entry : headers) {
			this._headers.put(entry.getKey(), entry.getValue());
		}

	}

	@Override
	public String get(Object key) {
		return _headers.get(key);
	}

	@Override
	public String put(String key, String value) {
		if (value == null) {
			this._headers.remove(key);
			return null;
		}
		return _headers.put(key, value);
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		return _headers.entrySet();
	}

	@Override
	public Set<String> keySet() {
		return _headers.keySet();
	}

}