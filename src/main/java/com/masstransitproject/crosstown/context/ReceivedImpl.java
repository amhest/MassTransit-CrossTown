package com.masstransitproject.crosstown.context;

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

public class ReceivedImpl<T> implements Received<T> {
	
	ConsumeContext<T> _consumeContext;
	String receiverType;

	long timestamp;


	public ReceivedImpl(ConsumeContext<T> consumeContext, String receiverType,
			long timestamp) {
		super();
		this._consumeContext = consumeContext;
		this.receiverType = receiverType;
		this.timestamp = timestamp;
	}


	public String getMessageType() {
		return _consumeContext.getMessageType();
	}


	public String getReceiverType() {
		return receiverType;
	}


	public long getTimestamp() {
		return timestamp;
	}


	public String getCorrelationId() {
		return _consumeContext.getCorrelationId();
	}

	
}
