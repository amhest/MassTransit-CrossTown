package com.masstransitproject.crosstown.context;

import java.net.URI;
import java.util.Date;

import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.IServiceBus;
import com.masstransitproject.crosstown.handlers.SendCallback;

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



    public class InvalidConsumeContext<T extends Object> implements
        IConsumeContext<T>
    {


        static ContextException CreateException()
        {
            return new ContextException("Consume context is only available when consuming a message");
        }

		@Override
		public IMessageHeaders getHeaders() {
			throw CreateException();
	
		}

		@Override
		public String getMessageId() {
			throw CreateException();
			
		}

		@Override
		public String getMessageType() {
			throw CreateException();
			
		}

		@Override
		public String getContentType() {
			throw CreateException();
			
		}

		@Override
		public String getRequestId() {
			throw CreateException();
			
		}

		@Override
		public String getConversationId() {
			throw CreateException();
			
		}

		@Override
		public String getCorrelationId() {
			throw CreateException();
			
		}

		@Override
		public URI getSourceAddress() {
			throw CreateException();
			
		}

		@Override
		public URI getInputAddress() {
			throw CreateException();
			
		}

		@Override
		public URI getDestinationAddress() {
			throw CreateException();
			
		}

		@Override
		public URI getResponseAddress() {
			throw CreateException();
			
		}

		@Override
		public URI getFaultAddress() {
			throw CreateException();
			
		}

		@Override
		public String getNetwork() {
			throw CreateException();
			
		}

		@Override
		public Date getExpirationTime() {
			throw CreateException();
			
		}

		@Override
		public int getRetryCount() {
			throw CreateException();
		}


		@Override
		public void RetryLater() {
			throw CreateException();
			
		}

		@Override
		public void GenerateFault(Exception ex) {
			throw CreateException();
			
		}

		@Override
		public IReceiveContext getBaseContext() {
			throw CreateException();
			
		}

		@Override
		public IServiceBus getBus() {
			throw CreateException();
			
		}

		@Override
		public IEndpoint getEndpoint() {
			throw CreateException();
			
		}

		@Override
		public boolean IsContextAvailable(Class messageType) {
			throw CreateException();
			
		}

		@Override
		public IConsumeContext getContext() {
			throw CreateException();
			
		}
	

		@Override
		public void Respond(T message, SendCallback<T> contextCallback) {
			throw CreateException();
			
		}

		@Override
		public Object getMessage() {
			// TODO Auto-generated method stub
			return null;
		}
		
    }
