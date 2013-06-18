package com.masstransitproject.crosstown;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;

import com.masstransitproject.crosstown.context.ISendContext;
import com.masstransitproject.crosstown.serialization.IMessageSerializer;

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

public abstract class SerializationTest  {
	protected abstract IMessageSerializer getSerializer();

	@Before
	public void setUp() {
		// _serializer = new TSerializer();
	}

	protected IMessage SerializeAndReturn(IMessage message, ISendContext ctx)
			throws IOException {
		byte[] serializedMessageData;
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		getSerializer().Serialize(output, message, ctx);

		serializedMessageData = output.toByteArray();

		// Trace.WriteLine(Encoding.UTF8.GetString(serializedMessageData));

		ByteArrayInputStream input = new ByteArrayInputStream(
				serializedMessageData);

		return getSerializer().Deserialize(input);

	}

}