package com.masstransitproject.crosstown.messages;

import java.io.Serializable;
import java.util.UUID;

import com.masstransitproject.crosstown.ExternallyNamespaced;

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
public class PingMessage implements Serializable, ExternallyNamespaced {
	private UUID _id = UUID.fromString("D62C9B1C-8E31-4D54-ADD7-C624D56085A4");

	public PingMessage() {
	}

	public PingMessage(UUID id) {
		_id = id;
	}

	private UUID correlationId;

	public UUID getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(UUID correlationId) {
		this.correlationId = correlationId;
	}

	@Override
	public String getExternalNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
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
		PingMessage other = (PingMessage) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PingMessage [_id=" + _id + ", CorrelationId=" + correlationId
				+ "]";
	}

}