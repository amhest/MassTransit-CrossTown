package com.masstransitproject.crosstown.context;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Timestamp;
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

public interface ISendContext extends IMessageContext {
	// / <summary>
	// / The identifier for this message publish/send
	// / </summary>
	UUID getId();

	// / <summary>
	// / The original message type that was sent/published
	// / </summary>
	Class getDeclaringMessageType();

	String getOriginalMessageId();

	void setMessageType(String messageType);

	void setRequestId(String value);

	void setConversationId(String value);

	void setCorrelationId(String value);

	void setSourceAddress(URI uri);

	void setDestinationAddress(URI uri);

	void setResponseAddress(URI uri);

	void setFaultAddress(URI uri);

	void setNetwork(String network);

	void setExpirationTime(Timestamp value);

	void setRetryCount(int retryCount);

	void setUsing(IMessageContext context);

	void setHeader(String key, String value);

	// / <summary>
	// / Serializes the message to the stream
	// / </summary>
	// / <param name="stream">The target stream for the serialized
	// message</param>
	void serializeTo(OutputStream stream) throws IOException;

}