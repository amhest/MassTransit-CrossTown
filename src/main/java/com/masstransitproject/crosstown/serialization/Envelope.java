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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.masstransitproject.crosstown.IMessage;
import com.masstransitproject.crosstown.MessageUrn;

/// <summary>
/// The envelope in use for storing meta-data/out-of-band data and message Object data.
/// </summary>
public class Envelope {
	Envelope(IMessage message) {
		Headers = new HashMap<String, String>();

		Message = message;
		MessageType = MessageUrn.GetUrn(message);
	}

	protected Envelope() {
		Headers = new HashMap<String, String>();

	}

	private String RequestId;
	private String ConversationId;
	private String CorrelationId;
	private String DestinationAddress;
	private Timestamp ExpirationTime;
	private String FaultAddress;
	private Map<String, String> Headers;
	private IMessage Message;
	private String MessageId;
	private String MessageType;
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

	public Timestamp getExpirationTime() {
		return ExpirationTime;
	}

	public void setExpirationTime(Timestamp expirationTime) {
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

	public IMessage getMessage() {
		return Message;
	}

	public void setMessage(IMessage message) {
		Message = message;
	}

	public String getMessageId() {
		return MessageId;
	}

	public void setMessageId(String messageId) {
		MessageId = messageId;
	}

	public String getMessageType() {
		return MessageType;
	}

	public void setMessageType(String messageType) {
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

	// /// <summary>
	// /// Creates a new envelope using the passed send context.
	// /// </summary>
	// /// <typeparam name="T">The type of message</typeparam>
	// /// <param name="context">Context to write to the envelope</param>
	// /// <returns>The constructed envelope</returns>
	// private static Envelope Create<T>(ISendContext<T> context)
	// where T : class
	// {
	// var envelope = new Envelope(context.Message,
	// context.Message.GetType().GetMessageTypes());
	// envelope.SetUsingContext(context);
	//
	// return envelope;
	// }
}
