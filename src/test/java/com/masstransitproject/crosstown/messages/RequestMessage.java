package com.masstransitproject.crosstown.messages;

import java.io.Serializable;
import java.util.UUID;

import com.masstransitproject.crosstown.ExternallyNamespaced;
import com.masstransitproject.crosstown.context.Identifiable;

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
public class RequestMessage implements Serializable, ExternallyNamespaced, Identifiable {
	public RequestMessage() {
		correlationId = UUID.randomUUID();
	}

	private UUID correlationId;

	@Override
	public String getExternalNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";
	}

	public UUID getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(UUID correlationId) {
		this.correlationId = correlationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((correlationId == null) ? 0 : correlationId.hashCode());
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
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestMessage [CorrelationId=" + correlationId + "]";
	}

	@Override
	public UUID getId() {
		return getCorrelationId();
	}

	@Override
	public void setId(UUID id) {
		setCorrelationId(id);
		
	}

}