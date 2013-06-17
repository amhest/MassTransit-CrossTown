package com.masstransitproject.crosstown.context;

import java.net.URI;
import java.util.HashSet;

import com.masstransitproject.crosstown.IEndpointAddress;
import com.masstransitproject.crosstown.handlers.SendCallback;

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


    public class PublishContext<T> extends
        SendContext<T> implements IBusPublishContext<T>
    {
//        final IPublishContext _notifySend;
        final HashSet<URI> _endpoints = new HashSet<URI>();
        SendCallback _eachSubscriberAction = null;
        SendCallback _noSubscribersAction = null;
        long startTimerTime;
        long stopTimerTime = -1;
        //Func<Uri, bool> _wasEndpointAlreadySent;

        public PublishContext(T message)
            
        {super(message);
           // _wasEndpointAlreadySent = DefaultEndpointSent;
            startTimerTime=System.nanoTime();
//            _notifySend = null;
        }

        public PublishContext(T message, ISendContext<T> context)
            
        {
        	super(message, context);
//            _notifySend = context as IPublishContext;
//    
//            _wasEndpointAlreadySent = DefaultEndpointSent;
            startTimerTime=System.nanoTime();
        }

        public long getDurationNanos()
        {
        	if (stopTimerTime < 0) {
            return (System.nanoTime() - startTimerTime);
        	}
        	else 
        	{
        		return stopTimerTime-startTimerTime;
        	}
        }

        public IBusPublishContext<T> getContext()
        {
        	IBusPublishContext<T> context = null;
//
//            if (getMessage().IsAssignableFrom(typeof (T)))
//            {
        	IBusPublishContext<T>  busPublishContext = new PublishContext<T>(getMessage(), this);
//                busPublishContext._wasEndpointAlreadySent = _wasEndpointAlreadySent;
//                busPublishContext.IfNoSubscribers(_noSubscribersAction);
//                busPublishContext.ForEachSubscriber(_eachSubscriberAction);

                context = busPublishContext;
//            }
//
            return context; //!= null;
        }

//        public void NotifySend(IEndpointAddress address)
//        {
//            if (_notifySend != null)
//            {
//                _notifySend.NotifySend(address);
//                return;
//            }
//
//            _endpoints.Add(address.Uri);
//
//            base.NotifySend(address);
//
//            _eachSubscriberAction(address);
//        }

        public boolean WasEndpointAlreadySent(IEndpointAddress address)
        {
            // _wasEndpointAlreadySent(address.getUri());
        	
        	//TODO need to unravel
        	return false;
        }

        public void NotifyNoSubscribers()
        {
            _noSubscribersAction.invoke();
        }

        public void IfNoSubscribers(SendCallback<T> action)
        {
            _noSubscribersAction = action;
        }

        public void ForEachSubscriber(SendCallback<T> action)
        {
            _eachSubscriberAction = action;
        }

        public void Complete()
        {
            stopTimerTime = System.nanoTime();
        }

        boolean DefaultEndpointSent(URI uri)
        {
            return _endpoints.contains(uri);
        }

        public static <T> PublishContext<T> FromMessage(T message)
        {
            return new PublishContext<T>(message);
        }

        public static  <T,TMessage 
        	extends Object> PublishContext<T> FromMessage(TMessage message, 
        		ISendContext context)
           
        {
//            if (message.getClass().isAssignableFrom(T))
//            {
                return new PublishContext(message, context);
//            }

//            return null;
        }

        static void Ignore()
        {
        }

        static void Ignore(IEndpointAddress endpoint)
        {
        }

        public void SentTo(URI uri)
        {
            _endpoints.add(uri);
        }
    }
