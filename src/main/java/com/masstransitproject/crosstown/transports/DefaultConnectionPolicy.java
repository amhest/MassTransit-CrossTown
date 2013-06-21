package com.masstransitproject.crosstown.transports;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;


import org.slf4j.Logger;

import com.masstransitproject.crosstown.handlers.ConnectionCallback;
import com.masstransitproject.crosstown.handlers.ConnectionPolicyCallback;

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

    public class DefaultConnectionPolicy implements
        ConnectionPolicy
    {
        final ConnectionHandler _connectionHandler;
        final long _reconnectDelay;
        final Logger _log = org.slf4j.LoggerFactory.getLogger(DefaultConnectionPolicy.class);
        final ReentrantReadWriteLock _connectionLock = new ReentrantReadWriteLock();

        public DefaultConnectionPolicy(ConnectionHandler connectionHandler)
        {
            _connectionHandler = connectionHandler;
            _reconnectDelay = 10000;//10 seconds
        }

        public void Execute(ConnectionPolicyCallback callback)
        {
            try
            {
            	ReadLock lock = null;
                try
                {
                    // wait here so we can be sure that there is not a reconnect in progress
                    lock = _connectionLock.readLock();
                    lock.lockInterruptibly();
                    callback.invoke();
                } catch (InterruptedException e) {
    	            _log.warn("Interrupted waiting for Lock.",e);
				}
                finally
                {
                	if (lock != null) {
                		lock.unlock();
                	}
                }
            }
            catch (InvalidConnectionException ex)
            {
                _log.warn("Invalid Connection when executing callback", ex.getCause());

                Reconnect();

                if (_log.isDebugEnabled())
                {
                    _log.debug("Retrying callback after reconnect.");
                }

            	ReadLock lock=null;
                try
                {
                    // wait here so we can be sure that there is not a reconnect in progress
                    lock = _connectionLock.readLock();
                    lock.lock();
                    callback.invoke();
                }
                finally
                {
                    lock.unlock();
                }
            }
        }

        void Reconnect()
        {
        	WriteLock writeLock = _connectionLock.writeLock();
            try {
				if (writeLock.tryLock(((int)_reconnectDelay/2),TimeUnit.MILLISECONDS))
				{
				    try
				    {
				        if (_log.isDebugEnabled())
				        {
				            _log.debug("Disconnecting connection handler.");
				        }
				        _connectionHandler.Disconnect();

				        if (_reconnectDelay > 0)
				            Thread.sleep(_reconnectDelay);

				        if (_log.isDebugEnabled())
				        {
				            _log.debug("Re-connecting connection handler...");
				        }
				        _connectionHandler.Connect();
				    }
				    catch (Exception ex)
				    {
				        _log.warn("Failed to reconnect, deferring to connection policy for reconnection");
				        _connectionHandler.ForceReconnect(_reconnectDelay);
				    }
				    finally
				    {
				    	if (writeLock != null) {
				    		writeLock.unlock();
				    	}
				    }
				}
				else
				{
					ReadLock readLock = null;
				    try
				    {
				    	
				    	readLock = _connectionLock.readLock();
				    	readLock.lock();
				        if (_log.isDebugEnabled())
				        {
				            _log.debug("Waiting for reconnect in another thread.");
				        }
				    }
				    finally
				    {
				    	if (readLock != null) {
				    		readLock.unlock();
				    	}
				    }
				}
			} catch (InterruptedException e) {
	            _log.warn("Interrupted waiting for Lock.",e);
			}
        }
    
}