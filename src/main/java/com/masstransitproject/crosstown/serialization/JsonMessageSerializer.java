package com.masstransitproject.crosstown.serialization;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.masstransitproject.crosstown.context.SendContext;

public class JsonMessageSerializer<T extends Object> implements
		MessageSerializer<T> {
	private static final String CONTENT_TYPE_HEADER_VALUE = "application/vnd.masstransit+json";

	@Override
	public String getContentType() {
		return CONTENT_TYPE_HEADER_VALUE;
	}

	private static final Logger _log = LogManager
			.getLogger(JsonMessageSerializer.class.getName());

	@Override
	public void serialize(OutputStream stream, T message, SendContext<T> ctx)
			throws IOException {

		_log.info("Serializing object " + message + "of type "
				+ message.getClass());
		Envelope evp = new Envelope(message, ctx.getMessageTypes());
		evp.setMessageId(ctx.getMessageId());

		ObjectMapper ObjectMapper = new ObjectMapper();
		ObjectMapper.setSerializationInclusion(Include.NON_NULL);
		ObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		ObjectMapper.writeValue(stream, evp);

	}

	@Override
	@SuppressWarnings("unchecked") 
	public T deserialize(InputStream stream) throws IOException {

		ObjectMapper ObjectMapper = new ObjectMapper();
		Envelope evp = ObjectMapper.readValue(new InputStreamReader(stream),
				Envelope.class);
		return (T) evp.getMessage();
	}

	// public static JsonSerializer Deserializer
	// {
	// get
	// {
	// return _deserializer ?? (_deserializer = JsonSerializer.Create(new
	// JsonSerializerSettings
	// {
	// NullValueHandling = NullValueHandling.Ignore,
	// DefaultValueHandling = DefaultValueHandling.Ignore,
	// MissingMemberHandling = MissingMemberHandling.Ignore,
	// ObjectCreationHandling = ObjectCreationHandling.Auto,
	// ConstructorHandling =
	// ConstructorHandling.AllowNonPublicDefaultConstructor,
	// ContractResolver = new JsonContractResolver(),
	// Converters = new List<JsonConverter>(new JsonConverter[]
	// {
	// new ByteArrayConverter(),
	// new ListJsonConverter(),
	// new InterfaceProxyConverter(),
	// new StringDecimalConverter(),
	// new IsoDateTimeConverter{DateTimeStyles = DateTimeStyles.RoundtripKind},
	// })
	// }));
	// }
	// }

	// public static JsonSerializer Serializer
	// {
	// get
	// {
	// return _serializer ?? (_serializer = JsonSerializer.Create(new
	// JsonSerializerSettings
	// {
	// NullValueHandling = NullValueHandling.Ignore,
	// DefaultValueHandling = DefaultValueHandling.Ignore,
	// MissingMemberHandling = MissingMemberHandling.Ignore,
	// ObjectCreationHandling = ObjectCreationHandling.Auto,
	// ConstructorHandling =
	// ConstructorHandling.AllowNonPublicDefaultConstructor,
	// ContractResolver = new JsonContractResolver(),
	// Converters = new List<JsonConverter>(new JsonConverter[]
	// {
	// new ByteArrayConverter(),
	// new IsoDateTimeConverter{DateTimeStyles = DateTimeStyles.RoundtripKind},
	// }),
	// }));
	// }
	// }

}