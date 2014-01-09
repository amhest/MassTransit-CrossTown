package com.masstransitproject.crosstown.context;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.masstransitproject.crosstown.context.IMessageHeaders;
import com.masstransitproject.crosstown.context.IReceiveContext;
import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.context.MessageHeaders;
import com.masstransitproject.crosstown.newid.NewId;
import com.masstransitproject.crosstown.serialization.JsonMessageSerializer;

public class SendContext<T extends Object> implements
		ISendContext<T> {

	private Class declaringType;
	private final UUID id;
	private final T message;
	private final MessageHeaders headers = new MessageHeaders();

	public SendContext(Class declaringMessageType) {
		this.declaringType = declaringMessageType;
		try {
			this.message = (T) declaringMessageType.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		this.id = NewId.NextGuid();
	}

	@Override
	public String getContentType() {

		return "application/vnd.masstransit+json";
	}

	@Override
	public String getConversationId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCorrelationId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getDestinationAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getExpirationTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getFaultAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMessageHeaders getHeaders() {

		return headers;
	}

	@Override
	public URI getInputAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessageId() {
		return this.id.toString();
	}

	@Override
	public String getMessageType() {

		return getDeclaringMessageType().toString();
	}

	@Override
	public String getNetwork() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getResponseAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	private int retryCount = 0;

	@Override
	public int getRetryCount() {
		return retryCount;
	}

	@Override
	public void setRetryCount(int value) {
		this.retryCount = value;
	}

	@Override
	public URI getSourceAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class> GetMessageTypes() {

		return Arrays.asList(new Class[] { this.declaringType });
	}

	@Override
	public void SerializeTo(OutputStream stream) throws IOException {
		JsonMessageSerializer<T> s = new JsonMessageSerializer<T>();
		s.Serialize(stream, getMessage(), this);

	}

	@Override
	public void SetReceiveContext(IReceiveContext<T> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class getDeclaringMessageType() {

		return this.declaringType;
	}

	@Override
	public UUID getId() {

		return this.id;
	}

	@Override
	public T getMessage() {
		return this.message;
	}

	@Override
	public String getOriginalMessageId() {
		return null;
	}

	@Override
	public void setDeclaringMessageType(Class declaringType) {
		this.declaringType = declaringType;
	}

}
