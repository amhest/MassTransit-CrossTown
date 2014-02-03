package com.masstransitproject.crosstown.context;

import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.ISendHandler;

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

/**
 * Typed consumer context that can be used by message consumers to retrieve
 * out-of-band information related to a message. This consumer context can also
 * be used for explicitly telling the service bus to place the message at the
 * end of the input-queue (by calling <see cref="RetryLater"/>) or send the
 * message to the poison-letter queue (by calling @see GenerateFault
 * 
 * @param <T>
 *            Incoming message type.
 */
public interface IConsumeContext<T extends Object> extends IMessageContext<T> {
	/**
	 * Send the message to the end of the input queue so that it can be
	 * processed again later
	 */
	void RetryLater();

	/**
	 * Generates a fault for this message, which will be published once the
	 * message is moved
	 */
	void GenerateFault(Exception ex);

	/**
	 * Gets the base context of this consume context.
	 */
	IReceiveContext<T> getBaseContext();

	/**
	 * The endpoint from which the message was received
	 */
	IEndpoint<T> getEndpoint();

	/**
	 * Determines if the specified message type is available in the consumer
	 * context
	 * 
	 * @param <T>
	 */
	boolean IsContextAvailable(@SuppressWarnings("rawtypes") Class messageType);

	/**
	 * Retrieves a specified message type from the consumer context, if
	 * available.
	 * 
	 * @param <T>The message type requested
	 * @param context
	 *            ">The message context for the requested message type
	 * @return True if the message type is available, otherwise false.
	 */
	IConsumeContext<T> getContext();

	/**
	 * Respond to the current message, sending directly to the ResponseAddress
	 * if specified otherwise publishing the message
	 * 
	 * @param <T>The type of the message to respond with.
	 * @param message
	 *            ">The message to send in response
	 * @param contextCallback
	 *            ">The context action for specifying additional context
	 *            information
	 */
	void Respond(T message, ISendHandler<T> contextCallback);

}