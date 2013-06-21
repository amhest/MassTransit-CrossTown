package com.masstransitproject.crosstown.events;

// Copyright 2007-2008 The Apache Software Foundation.
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

	public class MessagePublished
	{
		private Class MessageType;

		private int ConsumerCount;

		private long Duration ;

		public Class getMessageType() {
			return MessageType;
		}

		public void setMessageType(Class messageType) {
			MessageType = messageType;
		}

		public int getConsumerCount() {
			return ConsumerCount;
		}

		public void setConsumerCount(int consumerCount) {
			ConsumerCount = consumerCount;
		}

		public long getDuration() {
			return Duration;
		}

		public void setDuration(long duration) {
			Duration = duration;
		}
		
	}