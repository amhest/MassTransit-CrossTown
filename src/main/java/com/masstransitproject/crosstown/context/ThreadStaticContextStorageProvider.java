package com.masstransitproject.crosstown.context;

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

	public class ThreadStaticContextStorageProvider implements
		ContextStorageProvider
	{
		static ThreadLocal<IConsumeContext> _consumeContext;


		static ThreadLocal<ISendContext> _sendContext;

		public ISendContext getSendContext()
		{ return _sendContext.get(); 

		}

		public IConsumeContext getConsumeContext()
		{
			return _consumeContext.get(); 
		}
			@Override
			public void setConsumeContext(IConsumeContext value) {
				_consumeContext = new ThreadLocal();
				_consumeContext.set(value);
			}
		
	}
