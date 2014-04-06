package com.masstransitproject.crosstown.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import com.masstransitproject.crosstown.Endpoint;
import com.masstransitproject.crosstown.EndpointAddress;
import com.masstransitproject.crosstown.FaultAction;
import com.masstransitproject.crosstown.serialization.MessageTypeConverter;

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
public interface ReceiveContext<T extends Object> extends ConsumeContext<T> {
	InputStream getBodyStream();

	Iterator<Sent<T>> getSent();

	Iterator<Received<T>> getReceived();

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

	void setEndpoint(Endpoint<T> endpoint);

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
	

	void copyBodyTo(OutputStream stream) throws IOException;

	void setMessageTypeConverter(MessageTypeConverter messageTypeConverter);

 /**
 * Notify that a fault needs to be sent, so that the endpoint can send it
 when
 * appropriate.
 *
 * @param faultAction
 */
 void notifyFault(FaultAction<T> faultAction);

 void notifySend(SendContext<T> context, EndpointAddress address);


 void notifyPublish(PublishContext<T> publishContext);

 void notifyConsume(ConsumeContext<T> consumeContext, String
 consumerType, String correlationId);

 /**
 * Publish any pending faults for the context
 */
 public void ExecuteFaultActions(Iterator<FaultAction<T>> faultActions);

 /**
 * Returns the fault actions that were added to the context dURIng message
 processing
 *
 * @return
 */ 
 public Iterator<FaultAction<T>> getFaultActions();


}
