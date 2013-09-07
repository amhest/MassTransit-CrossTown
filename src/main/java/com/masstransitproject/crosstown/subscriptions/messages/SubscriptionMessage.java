package com.masstransitproject.crosstown.subscriptions.messages;

import java.io.Serializable;
import java.net.URI;
import java.util.UUID;

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

	public abstract class SubscriptionMessage implements Serializable
	{
		private UUID subscriptionId;
		private URI endpointUri;
		private String messageName ;
		private String correlationId ;
		public UUID getSubscriptionId() {
			return subscriptionId;
		}
		public void setSubscriptionId(UUID subscriptionId) {
			this.subscriptionId = subscriptionId;
		}
		public URI getEndpointUri() {
			return endpointUri;
		}
		public void setEndpointUri(URI endpointUri) {
			this.endpointUri = endpointUri;
		}
		public String getMessageName() {
			return messageName;
		}
		public void setMessageName(String messageName) {
			this.messageName = messageName;
		}
		public String getCorrelationId() {
			return correlationId;
		}
		public void setCorrelationId(String correlationId) {
			this.correlationId = correlationId;
		}
		
	}
