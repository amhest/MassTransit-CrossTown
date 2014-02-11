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
import com.masstransitproject.crosstown.context.SendContext;
import com.masstransitproject.crosstown.context.SendContextImpl;

/**
 * The envelope in use for storing meta-data/out-of-band data and message object
 * data.
 */
public class Envelope {
	private static final Logger _log = LogManager.getLogger(SendContextImpl.class);

	Envelope(Object message, List<Class<?>> messageTypes) {
		headers = new HashMap<String, String>();
		try {
			messageType = convertMessageTypeClassesToUrns(messageTypes);
		} catch (URISyntaxException e) {
			throw new MassTransitException("Cannot create MessageUrn for "
					+ messageTypes, e);
		}

		this.message = message;
	}

	private List<String> convertMessageTypeClassesToUrns(
			List<Class<?>> messageTypes) throws URISyntaxException {

		List<String> l = new ArrayList<String>();
		for (Class<?> c : messageTypes) {
			_log.debug("Creating urn for class " + c);
			l.add(new MessageUrn(c).toString());
		}
		return l;
		//
		// (messageTypes.Select(type => new MessageUrn(type).ToString()));

	}

	protected Envelope() {
		headers = new HashMap<String, String>();
		messageType = new ArrayList<String>();
	}

	private String requestId;
	private String conversationId;
	private String correlationId;
	private String destinationAddress;
	private Date expirationTime;
	private String faultAddress;
	private Map<String, String> headers;
	private Object message;
	private String messageId;
	private List<String> messageType;
	private String network;
	private String responseAddress;
	private int retryCount;
	private String sourceAddress;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getDestinationAddress() {
		return this.destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public Date getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getFaultAddress() {
		return this.faultAddress;
	}

	public void setFaultAddress(String faultAddress) {
		this.faultAddress = faultAddress;
	}

	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Object getMessage() {
		return this.message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public List<String> getMessageType() {
		return this.messageType;
	}

	public void setMessageType(List<String> messageType) {
		this.messageType = messageType;
	}

	public String getNetwork() {
		return this.network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getResponseAddress() {
		return this.responseAddress;
	}

	public void setResponseAddress(String responseAddress) {
		this.responseAddress = responseAddress;
	}

	public int getRetryCount() {
		return this.retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public String getSourceAddress() {
		return this.sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
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
	public static <T> Envelope create(SendContext<T> context) {
		Envelope envelope = new Envelope(context.getMessage(),
				context.getMessageTypes());
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
			envelope.getHeaders().put(header.getKey(), header.getValue());
		}
		return envelope;
	}

	@Override
	public String toString() {
		return "Envelope [requestId=" + requestId + ", conversationId="
				+ conversationId + ", correlationId=" + correlationId
				+ ", destinationAddress=" + destinationAddress
				+ ", expirationTime=" + expirationTime + ", faultAddress="
				+ faultAddress + ", headers=" + headers + ", message="
				+ message + ", messageId=" + messageId + ", messageType="
				+ messageType + ", network=" + network + ", responseAddress="
				+ responseAddress + ", retryCount=" + retryCount
				+ ", sourceAddress=" + sourceAddress + "]";
	}

	

}
