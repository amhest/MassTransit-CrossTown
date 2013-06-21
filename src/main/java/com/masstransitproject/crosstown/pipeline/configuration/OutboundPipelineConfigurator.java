package com.masstransitproject.crosstown.pipeline.configuration;

import com.masstransitproject.crosstown.IServiceBus;
import com.masstransitproject.crosstown.pipeline.IOutboundMessagePipeline;
import com.masstransitproject.crosstown.pipeline.IOutboundPipelineConfigurator;
import com.masstransitproject.crosstown.pipeline.OutboundMessagePipeline;

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

	public class OutboundPipelineConfigurator implements
		IOutboundPipelineConfigurator
	{
		final IServiceBus _bus;
		final IOutboundMessagePipeline _pipeline;

		public OutboundPipelineConfigurator(IServiceBus bus)
		{
			_bus = bus;

//			MessageRouter router = new MessageRouter<ISendContext>();

			_pipeline = new OutboundMessagePipeline(null);
//			_pipeline = new OutboundMessagePipeline(router);
		}

		public IOutboundMessagePipeline getPipeline()
		{return _pipeline; }
		

		public IServiceBus getBus()
		{ return _bus; }
		
	}
