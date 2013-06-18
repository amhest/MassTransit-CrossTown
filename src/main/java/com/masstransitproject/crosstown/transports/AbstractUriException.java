package com.masstransitproject.crosstown.transports;

import java.net.URI;

import com.masstransitproject.crosstown.MassTransitException;

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

    public abstract class AbstractUriException extends
        MassTransitException
    {
        protected AbstractUriException()
        {
        }

        protected AbstractUriException(URI uri)
        {
           setUri(uri);
        }

        protected AbstractUriException(URI uri, String message){
            super(uri + " => " + message);

            setUri(uri);
        }

        protected AbstractUriException(URI uri, String message, Exception innerException){
        super(uri + " => " + message, innerException);

            setUri(uri);
        }


        public URI uri;

		public URI getUri() {
			return uri;
		}

		protected void setUri(URI uri) {
			this.uri = uri;
		}
    }
