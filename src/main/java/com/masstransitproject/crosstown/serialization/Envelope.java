package com.masstransitproject.crosstown.serialization;

import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;

import com.masstransitproject.crosstown.MassTransitException;
import com.masstransitproject.crosstown.MessageUrn;
import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.context.MessageHeaders;

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
	/// The envelope in use for storing meta-data/out-of-band data and message object data.
	/// </summary>
    public class Envelope
    {
        Envelope(Object message, List<Class> messageTypes)
        {
            Headers = new HashMap<String, String>();
            	try {
					MessageType = convertMessageTypeClassesToUrns(messageTypes);
				} catch (URISyntaxException e) {
					throw new MassTransitException("Cannot create MessageUrn for " + messageTypes,e);
				}
           
            Message = message;
        }
        
        private List<String> convertMessageTypeClassesToUrns(List<Class> messageTypes) throws URISyntaxException {
        	
        	
        	
        	List<String> l = new ArrayList<String>();
        	for (Class c : messageTypes) {
        		l.add(new MessageUrn(c).toString());
        	}
        	return l;
//        	
//        	(messageTypes.Select(type => new MessageUrn(type).ToString()));
            
        
        }

        protected Envelope()
        {
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

		/// <summary>
		/// Creates a new envelope using the passed send context.
		/// </summary>
		/// <typeparam name="T">The type of message</typeparam>
		/// <param name="context">Context to write to the envelope</param>
		/// <returns>The constructed envelope</returns>
        public static <T> Envelope Create(ISendContext<T> context)
        {
        	Envelope envelope = new Envelope(context.getMessage(), 
        			
        			
        			context.GetMessageTypes());
            envelope.SetUsingContext(context);

            return envelope;
        }
        
    	

		/// <summary>
		/// Transfers all contextual data to the envelop. 
		/// As such it 'sets the envelope data to that of the context'. Used by the outbound
		/// transports as the envelope needs to be hydrated from the meta-data and message object
		/// that is being passed down the outbound pipeline to the transport.
		/// </summary>
		/// <param name="envelope">Envelope instance to hydrate with context data.</param>
		/// <param name="context">The context to take the contextual data from.</param>
        public static void SetUsingContext(ISendContext context)
        {
        	Envelope envelope = new Envelope();
            envelope.setRequestId(context.getRequestId());
            envelope.setConversationId(context.getConversationId());
            envelope.setCorrelationId(context.getCorrelationId());
            envelope.setSourceAddress(context.getSourceAddress()==null?null:context.getSourceAddress().toString());
            envelope.setDestinationAddress(context.getDestinationAddress()==null?null: context.getDestinationAddress().toString());
            envelope.setResponseAddress(context.getResponseAddress()==null?null: context.getResponseAddress().toString());
            envelope.setFaultAddress(context.getFaultAddress()==null?null: context.getFaultAddress().toString());
            envelope.setNetwork(context.getNetwork());
            envelope.setRetryCount(context.getRetryCount());
                envelope.setExpirationTime(context.getExpirationTime());

            for (Entry<String, String> header : context.getHeaders().entrySet())
            {
                envelope.Headers.put(header.getKey(),header.getValue());
            }
        }
    }
