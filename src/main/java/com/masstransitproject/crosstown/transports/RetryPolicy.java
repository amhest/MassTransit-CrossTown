package com.masstransitproject.crosstown.transports;

import com.masstransitproject.crosstown.handlers.ConnectionPolicyCallback;

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

    class RetryPolicy implements
        ConnectionPolicy
    {
        final ConnectionPolicyChain _policyChain;

        public RetryPolicy(ConnectionPolicyChain policyChain)
        {
            _policyChain = policyChain;
        }

        public void Execute(ConnectionPolicyCallback callback)
        {
            callback.invoke();
            _policyChain.Pop(this);
            _policyChain.Next(callback);
        }
    }
