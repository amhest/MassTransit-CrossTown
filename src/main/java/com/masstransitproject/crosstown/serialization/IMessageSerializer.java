package com.masstransitproject.crosstown.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.masstransitproject.crosstown.context.ISendContext;

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
/// Message Serialization implementors should handle the nitty-gritty
/// details of writing Object instances to streams and reading them back
/// up from streams.
/// </summary>
public interface IMessageSerializer<T> {
	// / <summary>
	// / The content type that identifies the message serializer
	// / </summary>
	String getContentType();

	// / <summary>
	// / Serialize the message to the stream
	// / </summary>
	// / <typeparam name="T">The implicit type of the message to
	// serialize</typeparam>
	// / <param name="stream">The stream to write the context to</param>
	// / <param name="context">The context to send</param>
	public void Serialize(OutputStream stream, T message,
			ISendContext<T> ctx) throws IOException;

	// / <summary>
	// / Deserialize a message from the stream by reading the
	// / </summary>
	// / <param name="context">The context to deserialize</param>
	// / <returns>An Object that was deserialized</returns>

	/**
	 * Since we don't receive messages I short-circuited the original signature
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	T Deserialize(InputStream stream) throws IOException;
}
