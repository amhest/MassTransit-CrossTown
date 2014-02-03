package com.masstransitproject.crosstown.context;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masstransitproject.crosstown.context.IMessageHeaders;
import com.masstransitproject.crosstown.context.IReceiveContext;
import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.context.MessageHeaders;
import com.masstransitproject.crosstown.newid.NewId;
import com.masstransitproject.crosstown.serialization.JsonMessageSerializer;

public class SendContext<T extends Object> implements ISendContext<T> {

	private static final Logger _log = LogManager.getLogger(SendContext.class);

	private String conversationId;
	private String correlationId;
	private Class<T> declaringType;
	private URI destinationAddress;

	private Date expirationTime;

	private URI faultAddress;

	private final MessageHeaders headers = new MessageHeaders();

	private final UUID id;

	private URI inputAddress;

	private final T message;

	private String network;

	private IReceiveContext<T> receiveContext;

	private String requestId;

	private URI responseAddress;

	private int retryCount = 0;

	private URI sourceAddress;

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
		_log.debug("Created new SendContext for " + this.declaringType);
	}

	@Override
	public String getContentType() {

		return "application/vnd.masstransit+json";
	}

	@Override
	public String getConversationId() {
		return conversationId;
	}

	@Override
	public String getCorrelationId() {
		return correlationId;
	}

	@Override
	public Class<T> getDeclaringMessageType() {

		return this.declaringType;
	}

	@Override
	public URI getDestinationAddress() {
		return destinationAddress;
	}

	@Override
	public Date getExpirationTime() {
		return expirationTime;
	}

	@Override
	public URI getFaultAddress() {

		return faultAddress;
	}

	@Override
	public IMessageHeaders getHeaders() {

		return headers;
	}

	@Override
	public UUID getId() {

		return this.id;
	}

	@Override
	public URI getInputAddress() {

		return inputAddress;
	}

	@Override
	public T getMessage() {
		return this.message;
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
	public List<Class> GetMessageTypes() {

		List<Class> l = new ArrayList<Class>();
		l.add(this.getDeclaringMessageType());
		return l;
	}

	@Override
	public String getNetwork() {
		return network;
	}

	@Override
	public String getOriginalMessageId() {
		return null;
	}

	@Override
	public String getRequestId() {
		return requestId;
	}

	@Override
	public URI getResponseAddress() {
		return responseAddress;
	}

	@Override
	public int getRetryCount() {
		return retryCount;
	}

	@Override
	public URI getSourceAddress() {
		return sourceAddress;
	}

	@Override
	public void SerializeTo(OutputStream stream) throws IOException {
		JsonMessageSerializer<T> s = new JsonMessageSerializer<T>();
		s.Serialize(stream, getMessage(), this);

	}

	@Override
	public void setDeclaringMessageType(Class<T> declaringType) {
		this.declaringType = declaringType;
	}

	@Override
	public void SetReceiveContext(IReceiveContext<T> receiveContext) {
		this.receiveContext = receiveContext;
	}

	@Override
	public void setRetryCount(int value) {
		this.retryCount = value;
	}

	@Override
	public String toString() {
		return "SendContext [declaringType=" + declaringType + ", id=" + id
				+ ", message=" + message + ", headers=" + headers
				+ ", retryCount=" + retryCount + "]";
	}

}
