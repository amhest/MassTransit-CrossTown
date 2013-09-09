package com.masstransitproject.crosstown.pipeline.sinks;

import java.util.Collection;
import java.util.HashSet;

import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.context.IBusPublishContext;
import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.handlers.SinkAction;
import com.masstransitproject.crosstown.pipeline.IOutboundPipelineSink;
import com.masstransitproject.crosstown.pipeline.IPipelineInspector;

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
	/// A message sink that sends to an endpoint
	/// </summary>
	/// <typeparam name="TMessage"></typeparam>
	public class EndpointMessageSink<TMessage extends Class> implements
		IOutboundPipelineSink<TMessage>
		
	{
		private final IEndpoint _endpoint;

		public EndpointMessageSink(IEndpoint endpoint)
		{
			_endpoint = endpoint;
		}

		public IEndpoint getEndpoint()
		{
			 return _endpoint; 
		}

		public Collection<SinkAction<IBusPublishContext<TMessage>>> Enumerate(IBusPublishContext<TMessage> context)
		{
			
			throw new UnsupportedOperationException("Not Implemented");
//			Collection<SinkAction<IBusPublishContext<TMessage>>>  result = new
//					HashSet<SinkAction<IBusPublishContext<TMessage>>> ();
//			if (context.WasEndpointAlreadySent(_endpoint.getAddress()))
//						return result;
//
//			result.add(	new SinkAction(	_endpoint.Send((ISendContext<TMessage>) context)));
			
		}

		public boolean Inspect(IPipelineInspector inspector)
		{
			inspector.Inspect(this);
			return true;
		}
	}
