package com.masstransitproject.crosstown.context;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

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