package com.masstransitproject.crosstown.transports;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.masstransitproject.crosstown.IEndpoint;
import com.masstransitproject.crosstown.configuration.builders.EndpointBuilder;
import com.masstransitproject.crosstown.configuration.endpointconfigurators.IEndpointFactoryDefaultSettings;

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


    public class EndpointFactory 

    	
    	implements 
        IEndpointFactory
    {
        final IEndpointFactoryDefaultSettings _defaults;
        final ConcurrentHashMap<URI, EndpointBuilder> _endpointBuilders;
        final ConcurrentHashMap<String, ITransportFactory> _transportFactories;
        boolean _disposed;

        /// <summary>
        /// Creates a new endpoint factory instance
        /// </summary>
        /// <param name="transportFactories">Dictionary + contents owned by the EndpointFactory instance.</param>
        /// <param name="endpointBuilders"></param>
        /// <param name="defaults"></param>
        public EndpointFactory(Map<String, ITransportFactory> transportFactories,
             Map<URI, EndpointBuilder> endpointBuilders,
            IEndpointFactoryDefaultSettings defaults)
        {
            if (transportFactories == null)
                throw new IllegalArgumentException("transportFactories is null");
            if (endpointBuilders == null)
                throw new IllegalArgumentException("endpointBuilders is null");
            if (defaults == null)
                throw new IllegalArgumentException("defaults");
            _transportFactories = new ConcurrentHashMap<String, ITransportFactory>(transportFactories);
            _defaults = defaults;
            _endpointBuilders = new ConcurrentHashMap<URI, EndpointBuilder>(endpointBuilders);
        }

        public IEndpoint CreateEndpoint(URI uri) throws ConfigurationException
        {
            String scheme = uri.getScheme().toLowerCase();

            if (_transportFactories.contains(scheme))
            {
                ITransportFactory transportFactory = _transportFactories.get(scheme);
                try
                {
                    EndpointBuilder builder = _endpointBuilders.get(uri);
                    if (builder == null) {
                    	throw new UnsupportedOperationException("Fix me ");
                    	
//                    			EndpointConfiguratorImpl configurator = new EndpointConfiguratorImpl(uri, _defaults);
//                    			builder =  configurator.CreateBuilder();
                    }
                    

                    return builder.CreateEndpoint(transportFactory);
                }
                catch (Exception ex)
                {
                    throw new EndpointException(uri, "Failed to create endpoint", ex);
                }
            }

            throw new ConfigurationException(uri,
                "The " + uri.getScheme() + " scheme was not handled by any registered transport.");
        }

        public void AddTransportFactory(ITransportFactory factory)
        {
            String scheme = factory.getScheme().toLowerCase();

            _transportFactories.put(scheme,factory);
        }

//        public void Inspect(DiagnosticsProbe probe)
//        {
//            probe.Add("mt.default_serializer", _defaults.Serializer.GetType().ToShortTypeName());
//            _transportFactories.Each(
//                (scheme, factory) =>
//                    {
//                        probe.Add("mt.transport",
//                            String.Format("[{0}] {1}", scheme, factory.GetType().ToShortTypeName()));
//                    });
//        }

        public void Dispose()
        {
            if (_disposed)
                return;

            for (ITransportFactory factory : _transportFactories.values()) {
            	factory.Dispose();
            }
            _disposed = true;
        }
    }
