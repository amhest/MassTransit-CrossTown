package com.masstransitproject.crosstown.transports;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.masstransitproject.crosstown.ServiceBus;

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


    public class ConnectionHandlerImpl<T extends Connection> implements
        ConnectionHandler<T>
    {
        private static final Logger  _log = LoggerFactory.getLogger(ConnectionHandlerImpl.class);
        final HashSet<ConnectionBinding<T>> _bindings;
        final T _connection;
        final Object _lock = new Object();
        final ConnectionPolicyChainImpl _policyChain;
        boolean _bound;
        boolean _connected;
        boolean _disposed;

        public ConnectionHandlerImpl(T connection)
        {
            _bindings = new HashSet<ConnectionBinding<T>>();

            _connection = connection;
            _policyChain = new ConnectionPolicyChainImpl(this);

            _policyChain.Push(new ConnectOnFirstUsePolicy(this, _policyChain));
        }

        public void Connect()
        {
            lock (_lock)
            {
                if (!_connected)
                    _connection.Connect();

                _connected = true;

                BindBindings();
            }
        }

        public void Disconnect()
        {
            lock (_lock)
            {
                _connected = false;
                try
                {
                    UnbindBindings();

                    _connection.Disconnect();
                }
                catch (Exception ex)
                {
                    _log.Warn("Disconnect failed, but ignoring", ex);
                }
            }
        }

        public void ForceReconnect(TimeSpan reconnectDelay)
        {
            _policyChain.Push(new ReconnectPolicy(this, _policyChain, reconnectDelay));
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        public void Use(Action<T> callback)
        {
            _policyChain.Execute(() =>
                {
                    if (!_connected)
                        throw new InvalidConnectionException();

                    callback(_connection);
                });
        }


        public void AddBinding(ConnectionBinding<T> binding)
        {
            lock (_lock)
            {
                _bindings.Add(binding);
                if (_bound)
                {
                    binding.Bind(_connection);
                }
            }
        }

        public void RemoveBinding(ConnectionBinding<T> binding)
        {
            lock (_lock)
            {
                if (_bound)
                {
                    binding.Unbind(_connection);
                }
                _bindings.Remove(binding);
            }
        }

        void BindBindings()
        {
            if (_bound)
                return;

            foreach (var binding in _bindings)
            {
                binding.Bind(_connection);
            }
            _bound = true;
        }

        void UnbindBindings()
        {
            foreach (var binding in _bindings)
            {
                try
                {
                    binding.Unbind(_connection);
                }
                catch (Exception ex)
                {
                    _log.Error("An exception occurred while a binding was being unbound", ex);
                }
            }

            _bound = false;
        }


        void Dispose(boolean disposing)
        {
            if (_disposed) return;
            if (disposing)
            {
                UnbindBindings();

                Disconnect();

                _connection.Dispose();

                _policyChain.Set(new DisposedConnectionPolicy());
            }

            _disposed = true;
        }

        @Override
        public void finalize()
        {
            Dispose(false);
        }
    }
}