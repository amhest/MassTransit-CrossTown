package com.masstransitproject.crosstown.context;

import java.net.URI;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.masstransitproject.crosstown.CorrelatedBy;
import com.masstransitproject.crosstown.CorrelatedFault;
import com.masstransitproject.crosstown.Fault;
import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.IServiceBus;
import com.masstransitproject.crosstown.ServiceBusExtensions;
import com.masstransitproject.crosstown.handlers.FaultSender;
import com.masstransitproject.crosstown.handlers.SendCallback;

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


    public class ConsumeContext<T extends Object> implements
        IConsumeContext<T>
    {
        static final Logger _log = LoggerFactory.getLogger(ConsumeContext.class);

        final IReceiveContext _context;
        final T _message;
        final URI _responseAddress;

        public ConsumeContext(IReceiveContext context, T message)
        {
            _context = context;
            _message = message;
            _responseAddress = context.getResponseAddress();
        }

        public IMessageHeaders getHeaders()
        {
            return _context.getHeaders(); 
        }

        public String getRequestId()
        {
            return _context.getRequestId(); 
        }

        public String getConversationId()
        {
            return _context.getConversationId(); 
        }

        public String getCorrelationId()
        {
             return _context.getCorrelationId(); 
        }

        public String getMessageId()
        {
            return _context.getMessageId(); 
        }

        public  String getMessageType()
        {
             return ServiceBusExtensions.ToMessageName(this.getMessage().getClass());  //TODO Is this right 
        }

        public String getContentType()
        {
            return _context.getContentType(); 
        }

        public URI getSourceAddress()
        {
             return _context.getSourceAddress(); 
        }

        public URI getDestinationAddress()
        {
             return _context.getDestinationAddress(); 
        }

        public URI getResponseAddress()
        {
             return _responseAddress; 
        }

        public URI getFaultAddress()
        {
             return _context.getFaultAddress(); 
        }

        public String getNetwork()
        {
             return _context.getNetwork(); 
        }

        public Date getExpirationTime() {
             return _context.getExpirationTime(); 
        }

        public int getRetryCount()
        {
             return _context.getRetryCount(); 
        }

        public IServiceBus getBus()
        {
             return _context.getBus(); 
        }

        public IEndpoint getEndpoint()
        {
             return _context.getEndpoint(); 
        }

        public URI getInputAddress()
        {
             return _context.getInputAddress(); 
        }

        public T getMessage()
        {
             return _message; 
        }

        public boolean IsContextAvailable(Class messageType)
        {
            if (messageType.isInstance(getMessage().getClass()))
                return true;

            if (_context != null)
                return _context.IsContextAvailable(messageType);

            return false;
        }

        public <T extends Object> IConsumeContext<T> GetContext( )
        {
        	IConsumeContext<T> context;
            T messageOfT = (T) getMessage();
            if (messageOfT != null)
            {
                return new ConsumeContext<T>(_context, messageOfT);
                
            }
            if (_context != null)
                return _context.getContext();

            return null;
        }

        public IReceiveContext getBaseContext()
        { return _context; 
        }

        public void RetryLater()
        {
            if (_log.isDebugEnabled())
                _log.debug("Retrying message of type " +getMessage().getClass().getSimpleName() + " later");

            
            SendCallback retryLaterCallback = new SendCallback();
            retryLaterCallback.setRetryCount(getRetryCount() + 1);
            getBus().getEndpoint().Send(getMessage(), retryLaterCallback);
               
        }

		@Override
		public void Respond(T message, SendCallback<T> contextCallback) {
			
            _context.Respond(message, contextCallback);
        }

        public void GenerateFault(Exception exception)
        {
            if (getMessage() == null)
                throw new IllegalStateException("A fault cannot be generated when no message is present");

            if (getMessage().getClass().isAssignableFrom(CorrelatedBy.class)) {
            	CreateAndSendCorrelatedFault((CorrelatedBy) getMessage(),  exception);
            }
            else {
            	CreateAndSendFault(getMessage(),  exception);
            }
//            	
//            }
//
//            if (correlationType != null)
//            {
//                this.FastInvoke(new[] {typeof(T), correlationType}, "CreateAndSendCorrelatedFault", Message, ex);
//            }
//            else
//            {
//                this.FastInvoke(new[] {typeof(T)}, "CreateAndSendFault", Message, ex);
//            }
        }

        <T extends Object> void CreateAndSendFault(T message, Exception exception)
        {
        	Fault fault = new Fault(message, exception);
//        	Bus bus = Bus;
//        	FaultAddress faultAddress = FaultAddress;
//            var responseAddress = ResponseAddress;
//            var requestId = RequestId;

            _context.NotifyFault(new FaultSender(getBus(), getFaultAddress(), 
            		getResponseAddress(), getRequestId(), fault));
        }

        <T extends Object & CorrelatedBy<TKey>, TKey> void CreateAndSendCorrelatedFault(T message, Exception exception)
           
        {
        	CorrelatedFault fault = new CorrelatedFault<T, TKey>(message, exception);
//            var bus = Bus;
//            var faultAddress = FaultAddress;
//            var responseAddress = ResponseAddress;
//            var requestId = RequestId;

            _context.NotifyFault(new FaultSender(getBus(), getFaultAddress(), 
            		getResponseAddress(), getRequestId(), fault));
        }


		@Override
		public IConsumeContext<T> getContext() {
			return _context;
		}

        

    }
