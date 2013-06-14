package com.masstransitproject.crosstown.context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.masstransitproject.crosstown.serialization.JsonMessageSerializer;

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

	public class TransportMessageHeaders
	{
		static ObjectMapper _serializer;
		final Map<String, String> _headers;

		public TransportMessageHeaders()
		{
			_headers = new HashMap<String, String>();
		}

		TransportMessageHeaders(Map<String, String> dictionary)
		{
			_headers = dictionary;
		}

		static ObjectMapper getSerializer()
		{
				if (_serializer != null)
					return _serializer;


				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(Include.NON_NULL);
//				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);

				_serializer = objectMapper;

				return _serializer;
			}
		

		public String getHeader(String key) 
		{
				
			return _headers.get(key);
		}

		public void Add(String name, String value)
		{
			if (value== null || value.length() == 0)
			{
				_headers.remove(name);
				return;
			}

			_headers.put(name,value);
		}

		public byte[] GetBytes() throws IOException
		{
			if (_headers.size() == 0)
				return new byte[0];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 _serializer.writeValue(baos, _headers);
			 baos.close();
			 return baos.toByteArray();
		}

		public static TransportMessageHeaders Create(byte[] bytes) throws IOException
		{
			if (bytes.length == 0)
				return new TransportMessageHeaders();

			Map<String, String> headers=
					getSerializer().readValue(bytes,Map.class);

				return new TransportMessageHeaders(headers);
			
		}
	
}