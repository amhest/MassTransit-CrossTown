package com.masstransitproject.crosstown.transports;

import java.net.URI;

import com.masstransitproject.crosstown.EndpointAddress;
import com.masstransitproject.crosstown.IEndpointAddress;
import com.masstransitproject.crosstown.serialization.IMessageSerializer;

// Copyright 2007-2012 Chris Patterson, Dru Sellers, Travis Smith, et. al.
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


    public class EndpointSettings extends
        TransportSettings implements  IEndpointSettings
    {
        public EndpointSettings( String uri)
            {this(new EndpointAddress(uri));
        
        }

        public EndpointSettings( URI uri)
            { this(new EndpointAddress(uri));
        
        }

        EndpointSettings(IEndpointAddress address)
            {super(address);
        
            ErrorAddress = GetErrorEndpointAddress();
        }

        public EndpointSettings(IEndpointAddress address, IEndpointSettings source)
        { super(address, source);
        
            //Guard.AgainstNull(source, "source");

            setSerializer(source.getSerializer());
            if (!source.getErrorAddress().equals(address))
                setErrorAddress(source.getErrorAddress());
            setRetryLimit(source.getRetryLimit());
//            TrackerFactory = source.TrackerFactory;
        }

        public EndpointSettings(IEndpointAddress address, IMessageSerializer serializer, ITransportSettings source)
            {super(address, source);
        
            //Guard.AgainstNull(source, "source");

            Serializer = serializer;
            ErrorAddress = GetErrorEndpointAddress();
        }

        private IEndpointAddress ErrorAddress;
        private IMessageSerializer Serializer;
        private int RetryLimit;
        
        
//        public MessageTrackerFactory TrackerFactory { get; set; }

        public IEndpointAddress getErrorAddress() {
			return ErrorAddress;
		}

		public void setErrorAddress(IEndpointAddress errorAddress) {
			ErrorAddress = errorAddress;
		}

		public IMessageSerializer getSerializer() {
			return Serializer;
		}

		public void setSerializer(IMessageSerializer serializer) {
			Serializer = serializer;
		}

		public int getRetryLimit() {
			return RetryLimit;
		}

		public void setRetryLimit(int retryLimit) {
			RetryLimit = retryLimit;
		}

		EndpointAddress GetErrorEndpointAddress()
        {
            return new EndpointAddress(URI.create(getAddress().getUri().toString() + "_error"));
        }
    }
