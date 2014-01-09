package com.masstransitproject.crosstown.context;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public interface ISendContext<T extends Object> extends IMessageContext<T> {

	public abstract UUID getId();

	@SuppressWarnings("rawtypes")
	public abstract void setDeclaringMessageType(Class declaringMessageType);
	
	
	@SuppressWarnings("rawtypes")
	public Class getDeclaringMessageType();
	
	public abstract void SerializeTo(OutputStream stream) throws IOException;

	public abstract T getMessage();
	
	@SuppressWarnings("rawtypes")
	public abstract List<Class> GetMessageTypes();
	
	public void SetReceiveContext(IReceiveContext<T> receiveContext);
	
	
	public abstract String getOriginalMessageId();

	public abstract void setRetryCount(int value);
}