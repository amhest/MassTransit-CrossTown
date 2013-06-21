package com.masstransitproject.crosstown.pipeline;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.handlers.SendAction;
import com.masstransitproject.crosstown.handlers.SinkAction;

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


    public class OutboundMessagePipeline<T> implements
        IOutboundMessagePipeline<T>
    {
        final AtomicReference<IPipelineSink<ISendContext<T>>> _output;

        public OutboundMessagePipeline(IPipelineSink<ISendContext<T>> output)
        {
            _output = new AtomicReference(output);
        }

        public Iterable<SinkAction<ISendContext<T>>>  Enumerate(ISendContext<T> context)
        {
            return _output.get().Enumerate(context);
        }

        public boolean Inspect(IPipelineInspector inspector)
        {
            return inspector.Inspect(this, false);//() => _output.Value.Inspect(inspector));
        }

        public IPipelineSink<ISendContext<T>> ReplaceOutputSink(IPipelineSink<ISendContext<T>> sink)
        {
             
             return _output.getAndSet(sink);
        }
    }
