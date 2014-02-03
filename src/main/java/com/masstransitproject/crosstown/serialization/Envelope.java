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

package com.masstransitproject.crosstown.serialization;

import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masstransitproject.crosstown.MassTransitException;
import com.masstransitproject.crosstown.MessageUrn;
import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.context.SendContext;

/**
 * The envelope in use for storing meta-data/out-of-band data and message object
 * data.
 */
public class Envelope {
	private static final Logger _log = LogManager.getLogger(SendContext.class);

	Envelope(Object message, List<Class> messageTypes) {
		Headers = new HashMap<String, String>();
		try {
			MessageType = convertMessageTypeClassesToUrns(messageTypes);
		} catch (URISyntaxException e) {
			throw new MassTransitException("Cannot create MessageUrn for "
					+ messageTypes, e);
		}

		Message = message;
	}

	private List<String> convertMessageTypeClassesToUrns(
			List<Class> messageTypes) throws URISyntaxException {

		List<String> l = new ArrayList<String>();
		for (Class c : messageTypes) {
			_log.debug("Creating urn for class " + c);
			l.add(new MessageUrn(c).toString());
		}
		return l;
		//
		// (messageTypes.Select(type => new MessageUrn(type).ToString()));

	}

	protected Envelope() {
		Headers = new HashMap<String, String>();
		MessageType = new ArrayList<String>();
	}

	private String RequestId;
	private String ConversationId;
	private String CorrelationId;
	private String DestinationAddress;
	private Date ExpirationTime;
	private String FaultAddress;
	private Map<String, String> Headers;
	private Object Message;
	private String MessageId;
	private List<String> MessageType;
	private String Network;
	private String ResponseAddress;
	private int RetryCount;
	private String SourceAddress;

	public String getRequestId() {
		return RequestId;
	}

	public void setRequestId(String requestId) {
		RequestId = requestId;
	}

	public String getConversationId() {
		return ConversationId;
	}

	public void setConversationId(String conversationId) {
		ConversationId = conversationId;
	}

	public String getCorrelationId() {
		return CorrelationId;
	}

	public void setCorrelationId(String correlationId) {
		CorrelationId = correlationId;
	}

	public String getDestinationAddress() {
		return DestinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		DestinationAddress = destinationAddress;
	}

	public Date getExpirationTime() {
		return ExpirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		ExpirationTime = expirationTime;
	}

	public String getFaultAddress() {
		return FaultAddress;
	}

	public void setFaultAddress(String faultAddress) {
		FaultAddress = faultAddress;
	}

	public Map<String, String> getHeaders() {
		return Headers;
	}

	public void setHeaders(Map<String, String> headers) {
		Headers = headers;
	}

	public Object getMessage() {
		return Message;
	}

	public void setMessage(Object message) {
		Message = message;
	}

	public String getMessageId() {
		return MessageId;
	}

	public void setMessageId(String messageId) {
		MessageId = messageId;
	}

	public List<String> getMessageType() {
		return MessageType;
	}

	public void setMessageType(List<String> messageType) {
		MessageType = messageType;
	}

	public String getNetwork() {
		return Network;
	}

	public void setNetwork(String network) {
		Network = network;
	}

	public String getResponseAddress() {
		return ResponseAddress;
	}

	public void setResponseAddress(String responseAddress) {
		ResponseAddress = responseAddress;
	}

	public int getRetryCount() {
		return RetryCount;
	}

	public void setRetryCount(int retryCount) {
		RetryCount = retryCount;
	}

	public String getSourceAddress() {
		return SourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		SourceAddress = sourceAddress;
	}

	/**
	 * Creates a new envelope using the passed send context.
	 * 
	 * @param <T>
	 *            The type of message
	 * @param context
	 *            Context to write to the envelope
	 * @return The constructed envelope
	 */
	public static <T> Envelope Create(ISendContext<T> context) {
		Envelope envelope = new Envelope(context.getMessage(),
				context.GetMessageTypes());
		envelope.setRequestId(context.getRequestId());
		envelope.setConversationId(context.getConversationId());
		envelope.setCorrelationId(context.getCorrelationId());
		envelope.setSourceAddress(context.getSourceAddress() == null ? null
				: context.getSourceAddress().toString());
		envelope.setDestinationAddress(context.getDestinationAddress() == null ? null
				: context.getDestinationAddress().toString());
		envelope.setResponseAddress(context.getResponseAddress() == null ? null
				: context.getResponseAddress().toString());
		envelope.setFaultAddress(context.getFaultAddress() == null ? null
				: context.getFaultAddress().toString());
		envelope.setNetwork(context.getNetwork());
		envelope.setRetryCount(context.getRetryCount());
		envelope.setExpirationTime(context.getExpirationTime());

		for (Entry<String, String> header : context.getHeaders().entrySet()) {
			envelope.Headers.put(header.getKey(), header.getValue());
		}
		return envelope;
	}

	@Override
	public String toString() {
		return "Envelope [RequestId=" + RequestId + ", ConversationId="
				+ ConversationId + ", CorrelationId=" + CorrelationId
				+ ", DestinationAddress=" + DestinationAddress
				+ ", ExpirationTime=" + ExpirationTime + ", FaultAddress="
				+ FaultAddress + ", Headers=" + Headers + ", Message="
				+ Message + ", MessageId=" + MessageId + ", MessageType="
				+ MessageType + ", Network=" + Network + ", ResponseAddress="
				+ ResponseAddress + ", RetryCount=" + RetryCount
				+ ", SourceAddress=" + SourceAddress + "]";
	}

}
