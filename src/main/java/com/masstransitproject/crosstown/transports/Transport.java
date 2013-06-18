package com.masstransitproject.crosstown.transports;

import java.nio.channels.IllegalSelectorException;

import com.masstransitproject.crosstown.IEndpointAddress;
import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.handlers.ReceiveHandler;

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


	public class Transport implements
		IDuplexTransport
	{
		final TransportFactory _inboundFactory;
		final Object _lock = new Object();
		final TransportFactory _outboundFactory;
		boolean _disposed;
		IInboundTransport _inbound;
		IOutboundTransport _outbound;
		final IEndpointAddress _address;

		public Transport(IEndpointAddress address, TransportFactory inboundFactory, TransportFactory outboundFactory)
		{
			_inboundFactory = inboundFactory;
			_outboundFactory = outboundFactory;
			_address = address;
		}

		public void Dispose()
		{
			Dispose(true);
//			GC.SuppressFinalize(this);
		}

		public IEndpointAddress getAddress()
		{
			return _address; 
		}

		public void Send(ISendContext context)
		{
			if (_disposed)
				throw new IllegalStateException("The transport has already been disposed: " + getAddress());

			getOutboundTransport().Send(context);
		}

		public void Receive(ReceiveHandler callback, long timeout)
		{
			if (_disposed)
				throw new IllegalStateException("The transport has already been disposed: " + getAddress());

			getInboundTransport().Receive(callback, timeout);
		}

		public IOutboundTransport getOutboundTransport()
		{

			 if (_outbound == null) {
				synchronized (_lock)
				{
					 if (_outbound == null) {
						 _outbound = _outboundFactory.createOutbound();
					 }
				}
			 }
			return _outbound;
		}

		public IInboundTransport getInboundTransport()
		{


			 if (_inbound == null) {
				synchronized (_lock)
				{
					 if (_inbound == null) {
						 _inbound = _inboundFactory.createInbound();
					 }
				}
			 }
			return _inbound;
		}

		void Dispose(boolean disposing)
		{
			if (_disposed) return;
			if (disposing)
			{
				if (_inbound != null)
				{
					_inbound.Dispose();
					_inbound = null;
				}

				if (_outbound != null)
				{
					_outbound.Dispose();
					_outbound = null;
				}
			}

			_disposed = true;
		}

		@Override
		protected void finalize() 
		{
			Dispose(false);
		}
	}
