package com.masstransitproject.crosstown;

// Copyright 2007-2012 Chris Patterson, Dru Sellers, Travis Smith, et. al.
//
// Licensed under the Apache License, Version 2.0 (the "License"); you may not use
// this file except in compliance with the License. You may obtain a copy of the 
// License at 
//
// http://www.apache.org/licenses/LICENSE-2.0 
//
// Unless required by applicable law or agreed to in writing, software distributed
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
// CONDITIONS OF ANY KIND, either express or implied. See the License for the 
// specific language governing permissions and limitations under the License.

import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.serialization.IMessageSerializer;

/**
 * IEndpoint is implemented by an endpoint. An endpoint is an addressable
 * location on the network. In MassTransit, the endpoint ties together the
 * inbound transport, the outbound transport, the error transport that ships
 * problematic messages to the error queue, mesage retry trackers and
 * serialization. It is up to the transports themselves to implement the correct
 * connection handling and to to create the <see cref="IReceiveContext"/> from
 * the bytes on the wire, which hands the message over to MassTransit's
 * internals.
 * 
 **/
public interface IEndpoint<T extends Object> {
	/**
	 * The address of the endpoint
	 **/
	IEndpointAddress getAddress();

	void Dispose();

	/**
	 * The message serializer being used by the endpoint
	 **/
	IMessageSerializer<T> getSerializer();

	/**
	 * Send to the endpoint
	 * 
	 * @param <T>
	 *            The type of the message to send
	 * @param context
	 *            Send context to generate the in-transport message from.
	 *            Contains out-of-band data such as message ids, correlation
	 *            ids, headers, and in-band data such as the actual data of the
	 *            message to send.
	 */
	void send(ISendContext<T> context);

	/**
	 * Receive from the endpoint by passing a function that can preview the
	 * message: if the caller chooses to accept it, return a method that will
	 * consume the message. The first argument of the Func is created by the
	 * transport and this is what callers of receive must inspect to find what
	 * receivers (the return value Action{IReceiveContext}) are interested in
	 * the received data. Returns after the specified timeout if no message is
	 * available.
	 * 
	 * @param receiver
	 *            ">The function to preview/consume the message
	 * @param timeout
	 *            ">The time to wait for a message to be available
	 **/
	void receive(IReceiveHandler<T> receiver, long timeout);
}
