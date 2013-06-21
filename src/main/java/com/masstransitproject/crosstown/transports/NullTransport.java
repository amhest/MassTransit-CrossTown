package com.masstransitproject.crosstown.transports;

import org.slf4j.Logger;

import com.masstransitproject.crosstown.IEndpointAddress;
import com.masstransitproject.crosstown.context.ISendContext;

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

    public class NullTransport implements
		IOutboundTransport
	{
    	static final Logger _log = org.slf4j.LoggerFactory.getLogger(NullTransport.class);
    
		final IEndpointAddress _address;

		public NullTransport(IEndpointAddress address)
		{
			_address = address;
		}

		public void Send(ISendContext context)
		{
			_log.debug("Discarding message on "+  _address + ":" + context.getMessageType());
		}

		public void Dispose()
		{
		}

		public IEndpointAddress getAddress()
		{return _address; 
		}
	}