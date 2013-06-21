package com.masstransitproject.crosstown.pipeline.configuration;

import java.util.concurrent.CopyOnWriteArrayList;

import com.masstransitproject.crosstown.IServiceBus;
import com.masstransitproject.crosstown.context.IConsumeContext;
import com.masstransitproject.crosstown.handlers.UnregisterAction;
import com.masstransitproject.crosstown.handlers.UnsubscribeAction;
import com.masstransitproject.crosstown.pipeline.IInboundMessagePipeline;
import com.masstransitproject.crosstown.pipeline.IInboundPipelineConfigurator;
import com.masstransitproject.crosstown.pipeline.ISubscriptionEvent;
import com.masstransitproject.crosstown.pipeline.sinks.MessageRouter;

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


	/// <summary><see cref="IInboundPipelineConfigurator"/>.</summary>
	public class InboundPipelineConfigurator implements
		IInboundPipelineConfigurator
	{
		final IServiceBus _bus;
		final IInboundMessagePipeline _pipeline;
		final CopyOnWriteArrayList<ISubscriptionEvent> _subscriptionEventHandlers;

		InboundPipelineConfigurator(IServiceBus bus)
		{
			_subscriptionEventHandlers = new CopyOnWriteArrayList<ISubscriptionEvent>();
			_bus = bus;

			MessageRouter router = new MessageRouter<IConsumeContext>();

			//TODO FIXME
			_pipeline = null;//new InboundMessagePipeline(router, this);
		}

		@Override
		public void Register(ISubscriptionEvent subscriptionEventHandler)
		{
			 _subscriptionEventHandlers.addIfAbsent(subscriptionEventHandler);
		}

		@Override
		public void Unregister(ISubscriptionEvent subscriptionEventHandler)
		{
			 _subscriptionEventHandlers.remove(subscriptionEventHandler);
		}
		
		public IInboundMessagePipeline getPipeline() {
		return _pipeline; 
		}

		public IServiceBus getBus() {
		return _bus; 
		}

		public UnsubscribeAction SubscribedTo() {
			UnsubscribeAction result = null;//() => true;

//			_subscriptionEventHandlers.Each(x => { result += x.SubscribedTo<TMessage>(); });

			return result;
		}

		public <TKey>  UnsubscribeAction CorrelationSubscribedTo(TKey correlationId){
			UnsubscribeAction result = null;//() => true;

//			_subscriptionEventHandlers.Each(x => { result += x.SubscribedTo<TMessage, TKey>(correlationId); });

			return result;
		}

		public static IInboundMessagePipeline CreateDefault(IServiceBus bus)
		{
			return new InboundPipelineConfigurator(bus)._pipeline;
		}

	}
