package com.masstransitproject.crosstown.context;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.serialization.IMessageTypeConverter;

// Copyright 2007-2012 Chris Patterson, Dru Sellers, Travis Smith, et. al.
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
 * Receive context that allows receiving sinks to
 */
public interface IReceiveContext<T extends Object> extends IConsumeContext<T> {
	InputStream getBodyStream();

	Collection<ISent<T>> getSent();

	Collection<IReceived> getReceived();

	UUID getId();

	/**
	 * True if the transport is transactional and will leave the message on the
	 * queue if an exception is thrown
	 */
	boolean isTransactional();

	/**
	 * The original message id that was consumed
	 */
	String getOriginalMessageId();

	/**
	 * Set the content type that was indicated by the transport message header
	 * 
	 * @param value
	 */
	void setContentType(String value);

	void setMessageId(String value);

	void setInputAddress(URI URI);

	void setEndpoint(IEndpoint<T> endpoint);

	void setRequestId(String value);

	void setConversationId(String value);

	void setCorrelationId(String value);

	void setOriginalMessageId(String value);

	void setSourceAddress(URI URI);

	void setDestinationAddress(URI URI);

	void setResponseAddress(URI URI);

	void setFaultAddress(URI URI);

	void setNetwork(String value);

	void setRetryCount(int retryCount);

	void setExpirationTime(Date value);

	void setMessageType(String messageType);

	void setHeader(String key, String value);

	/**
	 * Sets the context's body stream; useful for wrapped serializers such as
	 * encrypting serializers and for testing.
	 * 
	 * @param stream
	 *            Stream to replace the previous stream with
	 **/
	void setBodyStream(InputStream stream);
	

	void copyBodyTo(OutputStream stream);

	void setMessageTypeConverter(IMessageTypeConverter messageTypeConverter);
	//
	// /**
	// * Notify that a fault needs to be sent, so that the endpoint can send it
	// when
	// * appropriate.
	// *
	// * @param faultAction
	// */
	// void NotifyFault(FaultSender<T> faultAction);
	//
	// void NotifySend(ISendContext<T> context, IEndpointAddress address);
	//
	//
	// void NotifyPublish(IPublishContext<T> publishContext);
	//
	// void NotifyConsume(IConsumeContext<T> consumeContext, String
	// consumerType, String correlationId);
	//
	// /**
	// * Publish any pending faults for the context
	// *
	// void ExecuteFaultActions(Collection<FaultSender> faultActions);
	//
	// /**
	// * Returns the fault actions that were added to the context dURIng message
	// processing
	// *
	// * @return
	// Collection<FaultSender<T>> GetFaultActions();
	//
	// /**
	// * Sets the contextual data based on what was found in the envelope. Used
	// by the inbound
	// * transports as the receive context needs to be hydrated from the actual
	// data that was
	// * transferred through the transport as payload.
	// *
	// * @param context">The context to write data to, from the envelope
	// * @param envelope">The envelope that contains the data to read into the
	// context
	// public static void SetUsingEnvelope(this IReceiveContext context,
	// Envelope envelope);
	// {
	// context.SetRequestId(envelope.RequestId);
	// context.SetConversationId(envelope.ConversationId);
	// context.SetCorrelationId(envelope.CorrelationId);
	// context.SetSourceAddress(envelope.SourceAddress.ToURIOrNull());
	// context.SetDestinationAddress(envelope.DestinationAddress.ToURIOrNull());
	// context.SetResponseAddress(envelope.ResponseAddress.ToURIOrNull());
	// context.SetFaultAddress(envelope.FaultAddress.ToURIOrNull());
	// context.SetNetwork(envelope.Network);
	// context.SetRetryCount(envelope.RetryCount);
	// if (envelope.ExpirationTime.HasValue)
	// context.SetExpirationTime(envelope.ExpirationTime.Value);
	//
	// foreach (var header in envelope.Headers)
	// {
	// context.SetHeader(header.Key, header.Value);
	// }
	// }
}
