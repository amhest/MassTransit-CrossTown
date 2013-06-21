package com.masstransitproject.crosstown.pipeline;

import com.masstransitproject.crosstown.context.IConsumeContext;
import com.masstransitproject.crosstown.handlers.ConfigureCallback;
import com.masstransitproject.crosstown.handlers.UnsubscribeAction;

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
	/// Implemented by classes that can be placed in the inbound message pipeline. See
	/// <see cref="IPipelineSink{IConsumeContext}"/> for sink documentation.
	/// </summary>
	public interface IInboundMessagePipeline extends
		IPipelineSink<IConsumeContext>
	{
		/// <summary>
		/// Called when the pipeline part is being configured, during the initial setup
		/// of the message bus, allowing the pipeline part to provide a configurator
		/// implementation/instance of its own.
		/// </summary>
		/// <param name="configureCallback">A callback that may be called (directly) from
		/// the implementing method. The pipeline sink may choose the configurator instance to
		/// pass to this action.</param>
		ConfigureCallback Configure(ConfigureCallback configureCallback);

		/// <summary>
		/// <see cref="Configure"/>
		/// </summary>
		//<TResult> TResult Configure(ConfigureCallback<TResult> configureCallback);
	}
