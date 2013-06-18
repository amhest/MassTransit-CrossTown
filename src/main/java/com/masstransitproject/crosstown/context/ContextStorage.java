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


    /// <summary>
    /// The default context provider using thread local storage
    /// </summary>
    public  class ContextStorage
    {
        static ContextStorageProvider _provider;

        public static IConsumeContext getCurrentConsumeContext() { 
        	return  getProvider().getConsumeContext(); }
        public static void   setCurrentConsumeContext(IConsumeContext value) { 
            {
                if (value == null || value instanceof InvalidConsumeContext)
                    getProvider().setConsumeContext(null);
                else
                	 getProvider().setConsumeContext(value);
            }
        }

        static ContextStorageProvider getProvider()
        {
        	if (_provider == null) {
        		_provider =  GetDefaultProvider();
        	}
            return _provider;
        }

        public static <T extends Object> IConsumeContext<?>  createMessageContext()
        {
        	IConsumeContext context = getCurrentConsumeContext();
            if (context == null)
                throw new IllegalStateException("The specified consumer context type was not found");

            return context;
        }

        public static <T extends Object> SendContext<T> CreateSendContext(T message)
        {
        	SendContext context = new SendContext<T>(message);

            SetReceiveContextForSend(context);

            return context;
        }

        public static <T extends Object> PublishContext<T> CreatePublishContext(T message)
        {
            PublishContext<T> publishContext = PublishContext.FromMessage(message);

            SetReceiveContextForSend(publishContext);

            return publishContext;
        }

         static <T extends Object> void SetReceiveContextForSend(ISendContext<T> context)
        {
            IConsumeContext currentConsumeContext = getCurrentConsumeContext();
            if (currentConsumeContext != null)
            {
            	if (currentConsumeContext instanceof IReceiveContext) {
                    context.SetReceiveContext((IReceiveContext<T>) currentConsumeContext);
            	} else if (currentConsumeContext.getBaseContext() != null)
                {
                    context.SetReceiveContext(currentConsumeContext.getBaseContext());
                }
            }
        }
        public static IConsumeContext getContext()
        {
            IConsumeContext context = getCurrentConsumeContext();
            if (context == null)
                throw new IllegalStateException("No consumer context was found");

            return context;
        }

//        public static void Context(ConsumeCallback contextCallback)
//        {
//            IConsumeContext context = getCurrentConsumeContext();
//            if (context == null)
//                throw new IllegalStateException("No consumer context was found");
//
//            contextCallback.invoke(context);
//        }

//        public static <TResult extends IConsumeContext> TResult getContext<TResult>(ConsumeCallback<IConsumeContext, TResult> contextCallback)
//        {
//            IConsumeContext context = CurrentConsumeContext;
//            if (context == null)
//                throw new InvalidOperationException("No consumer context was found");
//
//            return contextCallback.invoke(context);
//        }

        static ContextStorageProvider GetDefaultProvider()
        {
//            if (HttpContext.Current != null)
//                return new HttpContextContextStorageProvider();

            return new ThreadStaticContextStorageProvider();
        }
    }
