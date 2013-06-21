package com.masstransitproject.crosstown.pipeline.sinks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.masstransitproject.crosstown.handlers.SinkAction;
import com.masstransitproject.crosstown.handlers.UnsubscribeAction;
import com.masstransitproject.crosstown.pipeline.IPipelineInspector;
import com.masstransitproject.crosstown.pipeline.IPipelineSink;

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


	/// <summary>
	/// Routes a message to all of the connected message sinks without modification
	/// </summary>
	public class MessageRouter<T> implements
		IPipelineSink<T>
	{
		final AtomicReference<List<IPipelineSink<T>>> _output;

		public MessageRouter()
		{
			_output = new AtomicReference(new ArrayList<IPipelineSink<T>>());
		}

		public MessageRouter(List<IPipelineSink<T>> sinks)
		{
			_output = new AtomicReference(new ArrayList<IPipelineSink<T>>(sinks));
		}

		/// <summary>
		/// Gets the number of pipelink sinks this message router
		/// has.
		/// </summary>
		public int getSinkCount()
		{
			return _output.get().size(); 
		}

		public List<IPipelineSink<T>> getSinks()
		{
			return _output.get(); 
		}

		public Iterable<SinkAction<T>> Enumerate(T context)
		{
			List<SinkAction<T>> l = new ArrayList<SinkAction<T>>(); 
			for (IPipelineSink sink:_output.get()) {
 				Iterator<SinkAction<T>> itr= sink.Enumerate(context).iterator();
				while (itr.hasNext()) {
					l.add(itr.next());
				}
			}
			//return _output.get.SelectMany(x => x.Enumerate(context));
			return l;
		}

		public boolean Inspect(IPipelineInspector inspector)
		{
			return inspector.Inspect(this);//, () => _output.Value.All(x => x.Inspect(inspector)));
		}

		public UnsubscribeAction Connect(IPipelineSink<T> sink)
		{
			
			//TODO FIX ME
//			_output.Set(sinks => new List<IPipelineSink<T>>(sinks) {sink});
//
//			return () => _output.Set(sinks => sinks.Where(x => x != sink).ToList()) != null;
			return null;
		}
	}