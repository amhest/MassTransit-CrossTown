package com.masstransitproject.crosstown;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.masstransitproject.crosstown.configuration.BusServiceLayer;

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

    public class ServiceContainer implements
        IServiceContainer
    {
        static final Logger _log = LoggerFactory.getLogger(ServiceContainer.class);
        final IServiceBus _bus;
        final ServiceCatalog _catalog;
        boolean _disposed;

        public ServiceContainer(IServiceBus bus)
        {
            _bus = bus;
            _catalog = new ServiceCatalog();
        }

        public void AddService(BusServiceLayer layer, IBusService service)
        {
            _catalog.Add(layer, service);
        }

        public IBusService GetService(Class type)
        {
            return _catalog.Get(type);
        }

        public void Start() throws MassTransitException
        {
            List<IBusService> started = new ArrayList<IBusService>();

            for (IBusService service : _catalog.getServices())
            {
                try
                {
                    _log.debug("Starting bus service: " + service.getClass().getName());

                    service.Start(_bus);
                    started.add(service);
                }
                catch (Exception ex)
                {
                    _log.error("Failed to start bus service: " + service.getClass().getName(), ex);

                    for (IBusService stopService : started)
                    {
                        try
                        {
                            stopService.Stop();
                        }
                        catch (Exception stopEx)
                        {
                            _log.warn("Failed to stop a service that was started during a failed bus startup: " +
                                      stopService.getClass().getName(), stopEx);
                        }
                    }

                    throw new MassTransitException("Failed to start bus services", ex);
                }
            }
        }

        public void Stop()
        {
            for (IBusService service : _catalog.getServices())  //Not reversing order on shutdown
            {
                try
                {
                    service.Stop();
                }
                catch (Exception ex)
                {
                    _log.error("Failed to stop service: " + service.getClass().getName(), ex);
                }
            }
        }

//        public void Inspect(DiagnosticsProbe probe)
//        {
//            probe.Add("mt.service_count", _catalog.NumberOfServices);
//            _catalog.Services
//                .Where(svc => svc.Implements<DiagnosticsSource>())
//                .Cast<DiagnosticsSource>()
//                .Each(src => src.Inspect(probe));
//        }

        public void Dispose()
        {
            Dispose(true);
//            GC.SuppressFinalize(this);
        }

        protected  void Dispose(boolean disposing)
        {
            if (_disposed)
                return;
            if (disposing)
            {
                for (IBusService service : _catalog.getServices())
                {
                    service.Dispose();
                }
            }
            _disposed = true;
        }

        
        
        @Override
		protected void finalize() throws Throwable {
			super.finalize();
            Dispose(false);
        }
    }
