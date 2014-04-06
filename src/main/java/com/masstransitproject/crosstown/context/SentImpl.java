package com.masstransitproject.crosstown.context;

import com.masstransitproject.crosstown.EndpointAddress;

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

public class SentImpl<T> implements Sent<T> {
	EndpointAddress address;

	SendContext<T> context;

	long timestamp;

	
	
	public SentImpl(SendContext<T> context,EndpointAddress address, 
			long timestamp) {
		super();
		this.address = address;
		this.context = context;
		this.timestamp = timestamp;
	}


	public EndpointAddress getAddress() {
		return address;
	}


	public SendContext<T> getContext() {
		return context;
	}

	public long getTimestamp() {
		return timestamp;
	}

	
}
