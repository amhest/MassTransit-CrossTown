package com.masstransitproject.crosstown.transports;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.ConfigurationException;

import org.slf4j.Logger;

import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.IEndpointCache;

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


    public class EndpointCache implements
        IEndpointCache
    {
    	static final Logger _log = org.slf4j.LoggerFactory.getLogger(EndpointCache.class);
        
        final IEndpointFactory _endpointFactory;
        final ConcurrentHashMap<URI, IEndpoint> _endpoints;
        boolean _disposed;

        public EndpointCache(IEndpointFactory endpointFactory)
        {
            _endpointFactory = endpointFactory;

            _endpoints = new ConcurrentHashMap<URI, IEndpoint>();
        }

        public void Dispose()
        {
            Dispose(true);
        }

        public IEndpoint GetEndpoint(URI uri)
        {
            if (_disposed)
                throw new IllegalStateException("The endpoint resolver has been disposed");

//            Guard.AgainstNull(uri, "uri", "Uri cannot be null");

                URI key;
				try {
					key = new URI(uri.toString().toLowerCase());
				
                
                IEndpoint ep = _endpoints.get(key);
                if( ep == null) {
                	
						ep = _endpointFactory.CreateEndpoint(uri);
					
                	_endpoints.put(key,ep);
                }
                return ep;
				} catch (URISyntaxException e) {
					//This really shouldn't happens since we
					//just downcase an existing uri
					throw new RuntimeException(e);
				}
            
        }

//        public void Inspect(DiagnosticsProbe probe)
//        {
//            _endpointFactory.Inspect(probe);
//        }

        public void Clear()
        {
        	Collection<IEndpoint> endpoints =  new ArrayList(_endpoints.values());
            _endpoints.clear();

            for (IEndpoint endpoint : endpoints)
            {
                try
                {
                    endpoint.Dispose();
                }
                catch (Exception ex)
                {
                    _log.error("An exception was thrown while disposing of an endpoint: " + endpoint.getAddress(), ex);
                }
            }

        }

        protected  void Dispose(boolean disposing)
        {
            if (_disposed)
                return;
            if (disposing)
            {
                Clear();

                _endpointFactory.Dispose();
            }

            _disposed = true;
        }
    }
