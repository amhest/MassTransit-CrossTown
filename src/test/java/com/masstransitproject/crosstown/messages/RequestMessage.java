package com.masstransitproject.crosstown.messages;

import java.io.Serializable;
import java.util.UUID;

import com.masstransitproject.crosstown.IMessage;

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

@SuppressWarnings("serial")
public class RequestMessage implements Serializable, IMessage {
	public RequestMessage() {
		CorrelationId = UUID.randomUUID();
	}

	private UUID CorrelationId;

	@Override
	public String getDotNetNamespace() {
		return "MassTransit.Tests.Messages";
	}

	public UUID getCorrelationId() {
		return CorrelationId;
	}

	public void setCorrelationId(UUID correlationId) {
		CorrelationId = correlationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((CorrelationId == null) ? 0 : CorrelationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestMessage other = (RequestMessage) obj;
		if (CorrelationId == null) {
			if (other.CorrelationId != null)
				return false;
		} else if (!CorrelationId.equals(other.CorrelationId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestMessage [CorrelationId=" + CorrelationId + "]";
	}

}