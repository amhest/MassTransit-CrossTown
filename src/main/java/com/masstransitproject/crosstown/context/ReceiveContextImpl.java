package com.masstransitproject.crosstown.context;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masstransitproject.crosstown.Endpoint;
import com.masstransitproject.crosstown.EndpointAddress;
import com.masstransitproject.crosstown.FaultAction;
import com.masstransitproject.crosstown.SendHandler;
import com.masstransitproject.crosstown.newid.NewId;
import com.masstransitproject.crosstown.serialization.Envelope;
import com.masstransitproject.crosstown.serialization.JsonMessageSerializer;
import com.masstransitproject.crosstown.serialization.MessageTypeConverter;
import com.masstransitproject.crosstown.util.StopWatch;

// Copyright 2007-2012 Chris Patterson, Dru Sellers, Travis Smith, et. al.
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

public class ReceiveContextImpl<T extends Object> implements ReceiveContext<T> {
	private static final Logger _log = LogManager
			.getLogger(JsonMessageSerializer.class.getName());

	private final List<Published<T>> _published;
	private final List<Received<T>> _received;
	private final List<Sent<T>> _sent;
	private boolean _transactional;
	private InputStream _bodyStream;
	StopWatch _timer;
	MessageTypeConverter _typeConverter;
	private final List<FaultAction<T>> _faultActions;

	private Endpoint<T> _endpoint;

	public ReceiveContextImpl() {
		_id = NewId.nextGuid();

		_faultActions = new ArrayList<FaultAction<T>>();
		_timer = new StopWatch();
		_sent = new ArrayList<Sent<T>>();
		_published = new ArrayList<Published<T>>();
		_received = new ArrayList<Received<T>>();
		_headers = new MessageHeadersImpl();
	}

	public ReceiveContextImpl(InputStream bodyStream, boolean transactional) {
		this();
		_bodyStream = bodyStream;
		_transactional = transactional;
	}

	public ReceiveContextImpl(byte[] bytes, boolean transactional) {
		this(new ByteArrayInputStream(bytes),transactional);
	}

	@Override
	public Endpoint getEndpoint() {
		return _endpoint;
	}

	@Override
	public void setEndpoint(Endpoint<T> endpoint) {
		this._endpoint = endpoint;
	}

	@Override
	public ReceiveContext getBaseContext() {
		return this;
	}

	@Override
	public void setBodyStream(InputStream stream) {
		if (stream == null)
			throw new IllegalArgumentException("stream is null");
		_bodyStream = stream;
	}

	@Override
	public void copyBodyTo(OutputStream stream) throws IOException {
		ReadableByteChannel source = Channels.newChannel(_bodyStream);
		WritableByteChannel target = Channels.newChannel(stream);

		ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);
		while (source.read(buffer) != -1) {
			buffer.flip(); // Prepare the buffer to be drained
			while (buffer.hasRemaining()) {
				target.write(buffer);
			}
			buffer.clear(); // Empty buffer to get ready for filling
		}
		source.close();
		target.close();
	}

	@Override
	public InputStream getBodyStream() {
		return _bodyStream;

	}

	@Override
	public void setMessageTypeConverter(MessageTypeConverter serializer) {
		_typeConverter = serializer;
	}

	@Override
	public void notifyFault(FaultAction<T> faultAction) {
		_faultActions.add(faultAction);
	}

	@Override
	public void notifySend(SendContext<T> context, EndpointAddress address) {
		_sent.add(new SentImpl(context, address, _timer.elapsedMilliseconds()));
	}

	@Override
	public void notifyPublish(PublishContext<T> publishContext) {
		_published.add(new PublishedImpl<T>(publishContext, _timer
				.elapsedMilliseconds()));
	}

	@Override
	public void notifyConsume(ConsumeContext<T> consumeContext,
			String consumerType, String correlationId) {
		// if (getEndpoint() != null)
		// getEndpoint().getAddress().LogReceived(consumeContext.getMessageId(),
		// typeof(T).ToMessageName());

		_received.add(new ReceivedImpl(consumeContext, consumerType, _timer
				.elapsedMilliseconds()));
	}

	@Override
	public Iterator<Sent<T>> getSent() {
		return _sent.iterator();
	}

	@Override
	public Iterator<Received<T>> getReceived() {
		return _received.iterator();
	}

	public UUID _id;

	@Override
	public UUID getId() {
		return _id;
	}

	@Override
	public boolean isTransactional() {
		return _transactional;
	}

	@Override
	public void ExecuteFaultActions(Iterator<FaultAction<T>> faultActions) {
		try {
			while (faultActions.hasNext()) {
				FaultAction<T> callback = faultActions.next();
				callback.doAction();
			}
		} catch (Exception ex) {
			_log.error("Failed to execute pending fault", ex);
		}
	}

	public Iterator<FaultAction<T>> getFaultAsssctions() {
		return _faultActions.iterator();
	}

	@Override
	public boolean isContextAvailable(Class<T> messageType) {
		return _typeConverter.contains(messageType);
	}

	// @Override
	@Override
	public ConsumeContext<T> getContext() {
		// try
		// {
		// T message;
		// if (_typeConverter != null && _typeConverter.convert( message))
		// {
		// context = new ConsumeContext<T>(this, message);
		// return true;
		// }
		//
		// }
		// catch (Exception ex)
		// {
		// Exception exception = new
		// SerializationException("Failed to deserialize the message", ex);
		//
		// throw exception;
		// }
		return this;
	}

	// / <summary>
	// / Respond to the current inbound message with either a send to the
	// ResponseAddress or a
	// / Publish on the bus that received the message
	// / </summary>
	// / <typeparam name="T">Message type</typeparam>
	// / <param name="message">The message to send/publish</param>
	// / <param name="contextCallback">The action to setup the context on the
	// outbound message</param>
	@Override
	public void respond(T message, SendHandler<T> contextCallback) {

		// if (contextCallback == null)
		// throw new ArgumentNullException("contextCallback");
		// if (ResponseAddress != null)
		// {
		// Bus.getEndpoint(ResponseAddress).Send(message, context =>
		// {
		// context.setSourceAddress(Bus.Endpoint.Address.Uri);
		// context.setRequestId(RequestId);
		// contextCallback(context);
		// });
		// }
		// else
		// {
		// Bus.Publish(message, context =>
		// {
		// context.setRequestId(RequestId);
		// contextCallback(context);
		// });
		// }
	}

	// / <summary>
	// / Create a new empty receive context
	// / </summary>
	// / <returns></returns>

	public static ReceiveContext Empty() {
		return new ReceiveContextImpl(new byte[0], false);
	}

	/**
	 * Sets the contextual data based on what was found in the envelope. Used by
	 * the inbound transports as the receive context needs to be hydrated from
	 * the actual data that was transferred through the transport as payload.
	 * 
	 * @param context
	 *            ">The context to write data to, from the envelope
	 * @param envelope
	 *            ">The envelope that contains the data to read into the context
	 * @throws URISyntaxException
	 */
	public static void populateFromEnvelope(Envelope envelope)
			throws URISyntaxException {
		ReceiveContextImpl context = new ReceiveContextImpl();
		context.setRequestId(envelope.getRequestId());
		context.setConversationId(envelope.getConversationId());
		context.setCorrelationId(envelope.getCorrelationId());
		if (envelope.getSourceAddress() != null)
			context.setSourceAddress(new URI(envelope.getSourceAddress()));
		if (envelope.getDestinationAddress() != null)
			context.setDestinationAddress(new URI(envelope
					.getDestinationAddress()));
		if (envelope.getResponseAddress() != null)
			context.setResponseAddress(new URI(envelope.getResponseAddress()));
		if (envelope.getFaultAddress() != null)
			context.setFaultAddress(new URI(envelope.getFaultAddress()));
		context.setNetwork(envelope.getNetwork());
		context.setRetryCount(envelope.getRetryCount());
		if (envelope.getExpirationTime() != null)
			context.setExpirationTime(envelope.getExpirationTime());

		for (String key : envelope.getHeaders().keySet()) {
			context.setHeader(key, envelope.getHeaders().get(key));
		}
	}

	@Override
	public void retryLater() {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateFault(Exception ex) {
		// TODO Auto-generated method stub

	}

	public T _message;

	@Override
	public T getMessage() {
		return _message;
	}

	public void setMessage(T _message) {
		this._message = _message;
	}

	private MessageHeaders _headers;

	@Override
	public MessageHeaders getHeaders() {

		return _headers;
	}

	private String messageId;

	private String messageType;

	private String contentType;

	private String requestId;

	private String conversationId;

	private String correlationId;

	private URI sourceAddress;

	private URI inputAddress;

	private URI destinationAddress;

	private URI responseAddress;

	private URI faultAddress;

	private String network;

	private Date expirationTime;

	private int retryCount;

	private String originalMessageId;

	@Override
	public String getMessageId() {
		return messageId;
	}

	@Override
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String getMessageType() {
		return messageType;
	}

	@Override
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getRequestId() {
		return requestId;
	}

	@Override
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String getConversationId() {
		return conversationId;
	}

	@Override
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	@Override
	public String getCorrelationId() {
		return correlationId;
	}

	@Override
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	@Override
	public URI getSourceAddress() {
		return sourceAddress;
	}

	@Override
	public void setSourceAddress(URI sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	@Override
	public URI getInputAddress() {
		return inputAddress;
	}

	@Override
	public void setInputAddress(URI inputAddress) {
		this.inputAddress = inputAddress;
	}

	@Override
	public URI getDestinationAddress() {
		return destinationAddress;
	}

	@Override
	public void setDestinationAddress(URI destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	@Override
	public URI getResponseAddress() {
		return responseAddress;
	}

	@Override
	public void setResponseAddress(URI responseAddress) {
		this.responseAddress = responseAddress;
	}

	@Override
	public URI getFaultAddress() {
		return faultAddress;
	}

	@Override
	public void setFaultAddress(URI faultAddress) {
		this.faultAddress = faultAddress;
	}

	@Override
	public String getNetwork() {
		return network;
	}

	@Override
	public void setNetwork(String network) {
		this.network = network;
	}

	@Override
	public Date getExpirationTime() {
		return expirationTime;
	}

	@Override
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public int getRetryCount() {
		return retryCount;
	}

	@Override
	public void setRetryCount(int getRetryCount) {
		this.retryCount = getRetryCount;
	}

	@Override
	public String getOriginalMessageId() {
		return originalMessageId;
	}

	@Override
	public void setOriginalMessageId(String originalMessageId) {
		this.originalMessageId = originalMessageId;
	}

	@Override
	public void setHeader(String key, String value) {

		_headers.put(key, value);

	}

	@Override
	public Iterator<FaultAction<T>> getFaultActions() {

		return _faultActions.iterator();
	}

}