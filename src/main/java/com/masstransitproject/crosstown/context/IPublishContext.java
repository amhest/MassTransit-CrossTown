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

public interface IPublishContext<T> extends ISendContext<T> {
	// /**
	// * Determines if the endpoint was already sent to during this publish
	// *
	// * @param address">The address of the endpoint to check
	// * @return True if the message was already sent to the specified endpoint
	// address
	// boolean WasEndpointAlreadySent(IEndpointAddress address);
	//
	// /**
	// * Defines an action to be called if there are no subscribers for the
	// message
	// *
	// * @param callback">The action to call if there are no subscribers
	// registered
	// void IfNoSubscribers(SendCallback<T> callback);
	//
	// /**
	// * Defines an action to be called for each subscriber of the message
	// *
	// * @param callback">The action to call for each subscriber, including the
	// endpoint address of the destination endpoint
	// void ForEachSubscriber(SendCallback<T> callback);
}
