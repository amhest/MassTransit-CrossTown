package com.masstransitproject.crosstown.events;

import java.util.Date;

import com.masstransitproject.crosstown.context.IReceiveContext;

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

	public class MessageReceived
	{
		private long ConsumeDuration ;
		private IReceiveContext Context ;
		private long ReceiveDuration ;

		private Date ReceivedAt;

		public long getConsumeDuration() {
			return ConsumeDuration;
		}

		public void setConsumeDuration(long consumeDuration) {
			ConsumeDuration = consumeDuration;
		}

		public IReceiveContext getContext() {
			return Context;
		}

		public void setContext(IReceiveContext context) {
			Context = context;
		}

		public long getReceiveDuration() {
			return ReceiveDuration;
		}

		public void setReceiveDuration(long receiveDuration) {
			ReceiveDuration = receiveDuration;
		}

		public Date getReceivedAt() {
			return ReceivedAt;
		}

		public void setReceivedAt(Date receivedAt) {
			ReceivedAt = receivedAt;
		}
		
	}
