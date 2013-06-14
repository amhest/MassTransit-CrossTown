package com.masstransitproject.crosstown.context;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.masstransitproject.crosstown.newid.NewId;

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


    public class SendContext<T> extends
        MessageContext implements ISendContext<T> 
    {
        final T _message;
 //       Writer _bodyWriter;
        UUID _id;
        //IReceiveContext _receiveContext;

        public SendContext(T message)
        {
            _id = NewId.NextGuid();
            _message = message;

            this.setMessageType(this._message.getClass().getName());
            DeclaringMessageType = this._message.getClass();
        }

        protected SendContext(T message, ISendContext<T> context)
        {
            _id = context.getId();
            _message = message;

            populate(context);

            this.setMessageType(message.getClass().getName());
            DeclaringMessageType = context.getDeclaringMessageType();
        }

        /* (non-Javadoc)
		 * @see com.masstransitproject.crosstown.context.ISendContext#getId()
		 */
        @Override
		public UUID getId()
        {
            return _id; 
        }

        private Class DeclaringMessageType;

        /* (non-Javadoc)
		 * @see com.masstransitproject.crosstown.context.ISendContext#setDeclaringMessageType(java.lang.Class)
		 */
        @Override
		public void setDeclaringMessageType(Class declaringMessageType) {
			DeclaringMessageType = declaringMessageType;
		}

		public Class getDeclaringMessageType() {
			return DeclaringMessageType;
		}

		/* (non-Javadoc)
		 * @see com.masstransitproject.crosstown.context.ISendContext#SerializeTo(java.io.OutputStream)
		 */
		@Override
		public void SerializeTo(OutputStream stream)
        {
//            if (_bodyWriter == null)
//                throw new IllegalStateException("No message body writer was specified");
//
//            _bodyWriter(stream);
        }

        /* (non-Javadoc)
		 * @see com.masstransitproject.crosstown.context.ISendContext#getContext()
		 */
        @Override
		public IBusPublishContext<T> getContext()
        {
        	IBusPublishContext<T> context = PublishContext.FromMessage(_message, this);

            return context;
        }

//        public  void NotifySend(IEndpointAddress address)
//        {
//            if (_receiveContext != null)
//                _receiveContext.NotifySend(this, address);
//        }

        /* (non-Javadoc)
		 * @see com.masstransitproject.crosstown.context.ISendContext#getMessage()
		 */
        @Override
		public T getMessage()
        {
            return _message; 
        }

		@Override
		public List<Class> GetMessageTypes() {

			return Arrays.asList(new Class[]{getMessage().getClass()});
		}
        
        

//        public void SetBodyWriter(Action<Stream> bodyWriter)
//        {
//            _bodyWriter = bodyWriter;
//        }
//
//        public void SetReceiveContext(IReceiveContext receiveContext)
//        {
//            _receiveContext = receiveContext;
//        }
    }
