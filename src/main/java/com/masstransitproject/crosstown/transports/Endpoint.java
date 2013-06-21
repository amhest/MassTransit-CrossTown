package com.masstransitproject.crosstown.transports;

import org.slf4j.Logger;

import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.IEndpointAddress;
import com.masstransitproject.crosstown.context.ContextStorage;
import com.masstransitproject.crosstown.context.IReceiveContext;
import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.handlers.ReceiveHandler;
import com.masstransitproject.crosstown.handlers.SendCallback;
import com.masstransitproject.crosstown.serialization.IMessageSerializer;

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

    /// <summary>
    /// See <see cref="IEndpoint"/> for docs.
    /// </summary>
    
public class Endpoint<T extends Object> implements
        IEndpoint
    {
        @Override
	public void Dispose() {
		// TODO Auto-generated method stub
		
	}

		final Logger _log = org.slf4j.LoggerFactory.getLogger(DefaultConnectionPolicy.class);
        final IEndpointAddress _address;
        final IMessageSerializer _serializer;
//        final IInboundMessageTracker _tracker;
        boolean _disposed;
        String _disposedMessage;
        IOutboundTransport _errorTransport;
        IDuplexTransport _transport;

        public Endpoint( IEndpointAddress address,
             IMessageSerializer serializer,
             IDuplexTransport transport,
             IOutboundTransport errorTransport,null)
//             IInboundMessageTracker messageTracker)
        {
            if (address == null)
                throw new IllegalArgumentException("address is null");
            if (serializer == null)
                throw new IllegalArgumentException("serializer is null");
            if (transport == null)
                throw new IllegalArgumentException("transport is null");
            if (errorTransport == null)
                throw new IllegalArgumentException("errorTransport is null");
            if (messageTracker == null)
                throw new IllegalArgumentException("messageTracker is null");

            _address = address;
            _errorTransport = errorTransport;
            _serializer = serializer;
            _tracker = messageTracker;
            _transport = transport;

            _disposedMessage = "The endpoint has already been disposed: " +getAddress();
        }

        public IOutboundTransport getErrorTransport()
        {
             return _errorTransport; 
        }

        public IMessageSerializer getSerializer()
        {
            return _serializer;
        }

        public IEndpointAddress getAddress()
        {
             return _address; 
        }

        public IInboundTransport getInboundTransport()
        {
           return _transport.getInboundTransport(); 
        }

        public IOutboundTransport getOutboundTransport()
        {
            return _transport.getOutboundTransport(); 
        }


        @Override
		public void Send(ISendContext context) {
        	

        	//TODO FIXME
        	throw new UnsupportedOperationException("NOT Implemented");
//            if (_disposed)
//                throw new IllegalStateException(_disposedMessage);
//
//            try
//            {
//                context.SetDestinationAddress(getAddress().getUri);
//                context.SetBodyWriter(stream => _serializer.Serialize(stream, context));
//
//                _transport.Send(context);
//
//                context.NotifySend(_address);
//            }
//            catch (Exception ex)
//            {
//                throw new SendException(typeof(T), _address.Uri, "An exception was thrown during Send", ex);
//            }
        }
//
        public void Send(Object message)
        {
        	//TODO FIXME
        	throw new UnsupportedOperationException("NOT Implemented");
//            ISendContext<T> context = ContextStorage.CreateSendContext(message);
//
//            Send(context);
        }

//        public void Send<T>(T message, sendCallback contextCallback)
//        {
//            ISendContext<T> context = ContextStorage.CreateSendContext(message);
//
//            contextCallback(context);
//
//            Send(context);
//        }
//
//        
//		@Override
//		public void Send(Object message) {
//            if (message == null)
//                throw new IllegalArgumentException("message is null");
//
//            EndpointObjectSenderCache.Instance[message.GetType()].Send(this, message);
//        }

//        public void Send(Object message, Class messageType)
//        {
//            if (message == null)
//                throw new IllegalArgumentException("message is null");
//            if (messageType == null)
//                throw new IllegalArgumentException("messageType is null");
//
//            EndpointObjectSenderCache.Instance[messageType].Send(this, message);
//        }
//
//        
		@Override
		public void Send(Object message, SendCallback contextCallback) {
            if (message == null)
                throw new IllegalArgumentException("message is null");
            if (contextCallback == null)
                throw new IllegalArgumentException("contextCallback is null");

            Class messageType = message.getClass();

        	//TODO FIXME
        	throw new UnsupportedOperationException("NOT Implemented");
            //EndpointObjectSenderCache.Instance[messageType].Send(this, message, contextCallback);
        }
//

		@Override
		public void Send(Object message, Class messageType,
				SendCallback contextCallback) {
			
            if (message == null)
                throw new IllegalArgumentException("message is null");
            if (messageType == null)
                throw new IllegalArgumentException("messageType is null");
            if (contextCallback == null)
                throw new IllegalArgumentException("contextCallback is null");

        	//TODO FIXME
        	throw new UnsupportedOperationException("NOT Implemented");
            //EndpointObjectSenderCache.Instance[messageType].Send(this, message, contextCallback);
        }

//        /// <summary>
//        /// Sends an interface message, initializing the properties of the interface using the anonymous
//        /// object specified
//        /// </summary>
//        /// <typeparam name="T">The interface type to send</typeparam>
//        /// <param name="endpoint">The destination endpoint</param>
//        /// <param name="values">The property values to initialize on the interface</param>
//        public void Send<T>(object values) 
//        {
//            var message = InterfaceImplementationExtensions.InitializeProxy<T>(values);
//
//            Send(message, x => { });
//        }

//        /// <summary>
//        /// Sends an interface message, initializing the properties of the interface using the anonymous
//        /// object specified
//        /// </summary>
//        /// <typeparam name="T">The interface type to send</typeparam>
//        /// <param name="endpoint">The destination endpoint</param>
//        /// <param name="values">The property values to initialize on the interface</param>
//        /// <param name="contextCallback">A callback method to modify the send context for the message</param>
//        public void Send<T>(object values, Action<ISendContext<T>> contextCallback)
//            where T : class
//        {
//            var message = InterfaceImplementationExtensions.InitializeProxy<T>(values);
//
//            Send(message, contextCallback);
//        }

//        public void Dispose()
//        {
//            Dispose(true);
//            GC.SuppressFinalize(this);
//        }

//       
		@Override
		public void Receive(ReceiveHandler receiver, long timeout) {
			throw new UnsupportedOperationException("Not Implemented");
//            if (_disposed)
//                throw new ObjectDisposedException(_disposedMessage);
//
//            string successfulMessageId = null;
//
//            try
//            {
//                Exception failedMessageException = null;
//
//                _transport.Receive(acceptContext =>
//                    {
//                        failedMessageException = null;
//
//                        if (successfulMessageId != null)
//                        {
//                            _log.DebugFormat("Received Successfully: {0}", successfulMessageId);
//
//                            _tracker.MessageWasReceivedSuccessfully(successfulMessageId);
//                            successfulMessageId = null;
//                        }
//
//                        Exception retryException;
//                        string acceptMessageId = acceptContext.OriginalMessageId ?? acceptContext.MessageId;
//                        IEnumerable<Action> faultActions;
//                        if (_tracker.IsRetryLimitExceeded(acceptMessageId, out retryException, out faultActions))
//                        {
//                            if (_log.IsErrorEnabled)
//                                _log.ErrorFormat("Message retry limit exceeded {0}:{1}", Address,
//                                    acceptMessageId);
//
//                            failedMessageException = retryException;
//
//                            acceptContext.ExecuteFaultActions(faultActions);
//
//                            return MoveMessageToErrorTransport;
//                        }
//
//                        if (acceptContext.MessageId != acceptMessageId)
//                        {
//                            if (_log.IsErrorEnabled)
//                                _log.DebugFormat("Message {0} original message id {1}", acceptContext.MessageId,
//                                    acceptContext.OriginalMessageId);
//                        }
//
//                        Action<IReceiveContext> receive;
//                        try
//                        {
//                            acceptContext.SetEndpoint(this);
//                            _serializer.Deserialize(acceptContext);
//
//                            receive = receiver(acceptContext);
//                            if (receive == null)
//                            {
//                                Address.LogSkipped(acceptMessageId);
//
//                                _tracker.IncrementRetryCount(acceptMessageId);
//                                return null;
//                            }
//                        }
//                        catch (SerializationException sex)
//                        {
//                            if (_log.IsErrorEnabled)
//                                _log.Error("Unrecognized message " + Address + ":" + acceptMessageId, sex);
//
//                            _tracker.IncrementRetryCount(acceptMessageId, sex);
//                            return MoveMessageToErrorTransport;
//                        }
//                        catch (Exception ex)
//                        {
//                            if (_log.IsErrorEnabled)
//                                _log.Error("An exception was thrown preparing the message consumers", ex);
//
//                            if(_tracker.IncrementRetryCount(acceptMessageId, ex))
//                            {
//                                acceptContext.ExecuteFaultActions(acceptContext.GetFaultActions());
//                            }
//                            return null;
//                        }
//
//                        return receiveContext =>
//                            {
//                                string receiveMessageId = receiveContext.OriginalMessageId ?? receiveContext.MessageId;
//                                try
//                                {
//                                    receive(receiveContext);
//
//                                    successfulMessageId = receiveMessageId;
//                                }
//                                catch (Exception ex)
//                                {
//                                    if (_log.IsErrorEnabled)
//                                        _log.Error("An exception was thrown by a message consumer", ex);
//
//                                    faultActions = receiveContext.GetFaultActions();
//                                    if(_tracker.IncrementRetryCount(receiveMessageId, ex, faultActions))
//                                    {
//                                        // seems like this might be unnecessary if we are going to reprocess the message
//                                        receiveContext.ExecuteFaultActions(faultActions);
//                                    }
//
//                                    if(!receiveContext.IsTransactional)
//                                    {
//                                        SaveMessageToInboundTransport(receiveContext);
//                                    }
//
//                                    throw;
//                                }
//                            };
//                    }, timeout);
//
//                if (failedMessageException != null)
//                {
//                    if(_log.IsErrorEnabled)
//                        _log.ErrorFormat("Throwing Original Exception: {0}", failedMessageException.GetType());
//
//                    throw failedMessageException;
//                }
//            }
//            catch (Exception ex)
//            {
//                if (successfulMessageId != null)
//                {
//                    _log.DebugFormat("Increment Retry Count: {0}", successfulMessageId);
//
//                    _tracker.IncrementRetryCount(successfulMessageId, ex);
//                    successfulMessageId = null;
//                }
//                throw;
//            }
//            finally
//            {
//                if (successfulMessageId != null)
//                {
//                    _log.DebugFormat("Received Successfully: {0}", successfulMessageId);
//
//                    _tracker.MessageWasReceivedSuccessfully(successfulMessageId);
//                    successfulMessageId = null;
//                }
//            }
       }

        void Dispose(boolean disposing)
        {
            if (_disposed)
                return;
            if (disposing)
            {
                _transport.Dispose();
                _transport = null;

                _errorTransport.Dispose();
                _errorTransport = null;
            }

            _disposed = true;
        }



		void MoveMessageToErrorTransport(IReceiveContext context)
        {

        	//TODO FIXME
        	throw new UnsupportedOperationException("NOT Implemented");
//        	MoveMessageSendContext moveContext = new MoveMessageSendContext(context);
//
//            _errorTransport.Send(moveContext);
//
//            string messageId = context.OriginalMessageId != null?context.OriginalMessageId: context.MessageId;
//            _tracker.MessageWasMovedToErrorQueue(messageId);
//
//            Address.LogMoved(_errorTransport.Address, context.MessageId, "");
        }

        void SaveMessageToInboundTransport(IReceiveContext context)
        {
        	
        	//TODO FIXME
        	throw new UnsupportedOperationException("NOT Implemented");
//        	MoveMessageSendContext moveContext = new MoveMessageSendContext(context);
//
//            _transport.Send(moveContext);
//
//            getAddress().LogReQueued(_transport.getAddress(), 
//            		context.getMessageId(), "");
        }

        
    }
