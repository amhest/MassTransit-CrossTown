package com.masstransitproject.crosstown.context;

import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public interface ISendContext<T extends Object> extends IMessageContext {

	public abstract UUID getId();

	public abstract void setDeclaringMessageType(Class declaringMessageType);
	public Class getDeclaringMessageType();
	
	public abstract void SerializeTo(OutputStream stream);

	public abstract IBusPublishContext<T> getContext();

	public abstract T getMessage();
	
	public abstract List<Class> GetMessageTypes();
	
	public void SetReceiveContext(IReceiveContext<T> receiveContext);
	

	public abstract String getOriginalMessageId();
	
}