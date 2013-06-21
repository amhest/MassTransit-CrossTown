package com.masstransitproject.crosstown.transports.loopback;

import java.nio.channels.UnsupportedAddressTypeException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.masstransitproject.crosstown.IEndpointAddress;
import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.events.ReceiveCompleted;
import com.masstransitproject.crosstown.handlers.AutoResetEvent;
import com.masstransitproject.crosstown.handlers.ReceiveHandler;
import com.masstransitproject.crosstown.transports.IDuplexTransport;
import com.masstransitproject.crosstown.transports.IInboundTransport;
import com.masstransitproject.crosstown.transports.IOutboundTransport;

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
    /// The loopback transport is a built-in transport for MassTransit that 
    /// works on messages in-memory. It is dependent on the <see cref="SubscriptionLoopback"/>
    /// that takes care of subscribing the buses in the process
    /// depending on what subscriptions are made.
    /// </summary>
    public class LoopbackTransport implements
        IDuplexTransport
    {
        final ReentrantLock _messageReadLock = new ReentrantLock();
        final ReentrantLock _messageWriteLock = new ReentrantLock();
        final long _deadlockTimeout = 60000;
        boolean _disposed;
        AutoResetEvent _messageReady = new AutoResetEvent(false);
        LinkedList<LoopbackMessage> _messages = new LinkedList<LoopbackMessage>();

        public LoopbackTransport(IEndpointAddress address)
        {
            setAddress(address);
        }

        public int getCount()
        {
           
                int messageCount;
                try {
					if (!_messageReadLock.tryLock(_deadlockTimeout, TimeUnit.MILLISECONDS))
					    throw new RuntimeException("Deadlock detected!");
				} catch (InterruptedException e) {
				    throw new RuntimeException("Deadlock check Interrupted!");
				}

                try
                {
                    GuardAgainstDisposed();

                    messageCount = _messages.size();
                }
                finally
                {
                	_messageReadLock.unlock();
                }

                return messageCount;
            
        }

        private IEndpointAddress Address;

        public IEndpointAddress getAddress() {
			return Address;
		}

		public void setAddress(IEndpointAddress address) {
			Address = address;
		}

		public IOutboundTransport getOutboundTransport()
        {return this; 
        }

        public IInboundTransport getInboundTransport()
        {
           return this; 
        }

        public void Send(ISendContext context)
        {

        	throw new UnsupportedOperationException("Not Finished");
//            GuardAgainstDisposed();
//
//            LoopbackMessage message = null;
//            try
//            {
//                message = new LoopbackMessage();
//
//                if (context.getExpirationTime() != null)
//                {
//                    message.ExpirationTime = context.getExpirationTime();
//                }
//
//                context.SerializeTo(message.getBody());
//                message.ContentType = context.getContentType();
//                message.OriginalMessageId = context.getOriginalMessageId();
//
//
//                if (!_messageWriteLock.tryLock(_deadlockTimeout, TimeUnit.MILLISECONDS))
//                    throw new Exception("Deadlock detected!");
//
//                try
//                {
//                    GuardAgainstDisposed();
//
//                    _messages.addLast(message);
//                }
//                finally
//                {
//                	_messageWriteLock.unlock();
//                }
//
//                getAddress().LogSent(message.MessageId, context.getMessageType());
//            }
//            catch (RuntimeException re)
//            {
//                if (message != null)
//                    message.Dispose();
//
//                throw re;
//            }
//
//            _messageReady.Set();
        }

        public void Receive(ReceiveHandler callback, long timeout)
        {
        	
        	throw new UnsupportedOperationException("Not Finished");
//            int messageCount = getCount();
//
//            boolean waited = false;
//
//            if (messageCount == 0)
//            {
//                if (!_messageReady.WaitOne(timeout, true))
//                    return;
//
//                waited = true;
//            }
//
//            boolean monitorExitNeeded = true;
//            
//
//            if (!_messageReadLock.tryLock(_deadlockTimeout, TimeUnit.MILLISECONDS))
//                return;
//
//            try
//            {
//                for (LinkedList<LoopbackMessage> iterator = _messages.First;
//                     iterator != null;
//                     iterator = iterator.Next)
//                {
//                    if (iterator.Value != null)
//                    {
//                        LoopbackMessage message = iterator.Value;
//                        if (message.ExpirationTime.HasValue && message.ExpirationTime <= DateTime.UtcNow)
//                        {
//
//                            if (!_messageWriteLock.tryLock(_deadlockTimeout, TimeUnit.MILLISECONDS))
//                                throw new Exception("Deadlock detected!");
//
//                            try
//                            {
//                                _messages.Remove(iterator);
//                            }
//                            finally
//                            {
//                            	_messageWriteLock.unlock();
//                            }
//                            return;
//                        }
//
//                        ReceiveContext context = ReceiveContext.FromBodyStream(message.Body);
//                        context.SetMessageId(message.MessageId);
//                        context.SetContentType(message.ContentType);
//                        context.SetOriginalMessageId(message.OriginalMessageId);
//                        if (message.ExpirationTime.HasValue)
//                            context.SetExpirationTime(message.ExpirationTime.Value);
//
//                        Action<IReceiveContext> receive = callback(context);
//                        if (receive == null)
//                            continue;
//
//                        if (!Monitor.TryEnter(_messageWriteLock, _deadlockTimeout))
//                            throw new Exception("Deadlock detected!");
//
//                        try
//                        {
//                            _messages.Remove(iterator);
//                        }
//                        finally
//                        {
//                        	_messageWriteLock.unlock();
//                        }
//
//                        using (message)
//                        {
//                            Monitor.Exit(_messageReadLock);
//                            monitorExitNeeded = false;
//
//                            receive(context);
//                            return;
//                        }
//                    }
//                }
//
//                if (waited)
//                    return;
//
//                // we read to the end and none were accepted, so we are going to wait until we get another in the queue
//                // make any other potential readers wait as well
//                _messageReady.WaitOne(timeout, true);
//            }
//            finally
//            {
//                if (monitorExitNeeded)
//                	_messageReadLock.unlock();
//            }
        }

        public void Dispose()
        {
            Dispose(true);
        }

        void GuardAgainstDisposed()
        {
            if (_disposed)
                throw new IllegalStateException("The transport has already been disposed: " + Address);
        }

        void Dispose(boolean disposing)
        {
            if (_disposed)
                return;
            if (disposing)
            {
                synchronized (_messageReadLock)
                {
                	synchronized (_messageWriteLock)
                    {
                		for (LoopbackMessage message:_messages) {
                			message.Dispose();
                		}
                        _messages.clear();
                        _messages = null;
                    }
                }

                _messageReady.Close();
//                using (_messageReady)
//                {
//                }
                _messageReady = null;
            }

            _disposed = true;
        }

    }
