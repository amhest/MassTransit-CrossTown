package com.masstransitproject.crosstown.transports.loopback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

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


    public class LoopbackMessage 
    {
        OutputStream _body;
        boolean _disposed;

        public LoopbackMessage()
        {
            setBody(new ByteArrayOutputStream());
            setMessageId(NewId.Next().toString());
        }

        public OutputStream getBody()
        {
            
          //      _body.Seek(0, SeekOrigin.Begin);
                return _body;
        }
        
        private void setBody(OutputStream value) {
            _body = value; 
        }

        public String OriginalMessageId;
        public String ContentType;
        public Date ExpirationTime; 
        public String MessageId ;
        
        

        

		public String getOriginalMessageId() {
			return OriginalMessageId;
		}

		public void setOriginalMessageId(String originalMessageId) {
			OriginalMessageId = originalMessageId;
		}

		public String getContentType() {
			return ContentType;
		}

		public void setContentType(String contentType) {
			ContentType = contentType;
		}

		public Date getExpirationTime() {
			return ExpirationTime;
		}

		public void setExpirationTime(Date expirationTime) {
			ExpirationTime = expirationTime;
		}

		public String getMessageId() {
			return MessageId;
		}

		private void setMessageId(String messageId) {
			MessageId = messageId;
		}

		public void Dispose()
        {
            Dispose(true);
//            GC.SuppressFinalize(this);
        }

        void Dispose(boolean disposing)
        {
            if (_disposed) return;
            if (disposing)
            {
                try {
					getBody().close();
				} catch (IOException e) {
					//Ignore
				}
            }

            _disposed = true;
        }

    
}