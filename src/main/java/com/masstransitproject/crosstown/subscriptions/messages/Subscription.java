package com.masstransitproject.crosstown.subscriptions.messages;

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

    /// <summary>
    /// Describes a subscription of an <see cref="Endpoint"/> to a particular message.
    /// </summary>
	public interface Subscription
	{
        /// <summary>
        /// Gets the subscription id.
        /// </summary>
		UUID getSubscriptionId();

        /// <summary>
        /// Gets the endpoint URI.
        /// </summary>
        URI getEndpointUri();

        /// <summary>
        /// Gets the name of the subscription message.
        /// </summary>
        /// <value>
        /// The name of the message.
        /// </value>
		String getMessageName();

        /// <summary>
        /// Gets the correlation id.
        /// </summary>
		String getCorrelationId();
	}
