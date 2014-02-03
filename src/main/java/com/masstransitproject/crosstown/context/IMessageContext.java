package com.masstransitproject.crosstown.context;

import java.net.URI;
import java.util.Date;

//Copyright 2007-2011 Chris Patterson, Dru Sellers, Travis Smith, et. al.
//
//Licensed under the Apache License, Version 2.0 (the "License"); you may not use 
//this file except in compliance with the License. You may obtain a copy of the 
//License at 
//
// http://www.apache.org/licenses/LICENSE-2.0 
//
//Unless required by applicable law or agreed to in writing, software distributed 
//under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
//CONDITIONS OF ANY KIND, either express or implied. See the License for the 
//specific language governing permissions and limitations under the License.

public interface IMessageContext<T extends Object> {

	public abstract T getMessage();

	public abstract IMessageHeaders getHeaders();

	public abstract String getMessageId();

	public abstract String getMessageType();

	public abstract String getContentType();

	public abstract String getRequestId();

	public abstract String getConversationId();

	public abstract String getCorrelationId();

	public abstract URI getSourceAddress();

	public abstract URI getInputAddress();

	public abstract URI getDestinationAddress();

	public abstract URI getResponseAddress();

	public abstract URI getFaultAddress();

	public abstract String getNetwork();

	public abstract Date getExpirationTime();

	public abstract int getRetryCount();

}