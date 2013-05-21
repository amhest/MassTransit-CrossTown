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
public class PongMessage implements Serializable, IMessage {
	private UUID _id;

	public PongMessage() {
		_id = UUID.randomUUID();
	}

	public PongMessage(UUID correlationId) {
		_id = correlationId;
	}

	private UUID CorrelationId;

	public UUID getCorrelationId() {
		return CorrelationId;
	}

	public void setCorrelationId(UUID correlationId) {
		CorrelationId = correlationId;
	}

	@Override
	public String getDotNetNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((CorrelationId == null) ? 0 : CorrelationId.hashCode());
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
		PongMessage other = (PongMessage) obj;
		if (CorrelationId == null) {
			if (other.CorrelationId != null)
				return false;
		} else if (!CorrelationId.equals(other.CorrelationId))
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
		return "PongMessage [_id=" + _id + ", CorrelationId=" + CorrelationId
				+ "]";
	}

}