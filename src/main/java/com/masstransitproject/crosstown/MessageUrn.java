package com.masstransitproject.crosstown;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    
// Copyright 2010 Chris Patterson
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


	public class MessageUrn 
	{

        private static final Logger  _log = LoggerFactory.getLogger(MessageUrn.class);
        
		private URI myUri;

		//May have both java and .Net entries
		static ThreadLocal<Map<String,Class>> _nameToClassCache;  
		static ThreadLocal<Map<Class, String>> _typeToUrnCache;

		public MessageUrn(Class type) throws URISyntaxException
			{
			myUri = new URI(GetUrnForType(type));
		
		}

		public MessageUrn(String uriString) throws URISyntaxException
		{
			myUri =  new URI(uriString);
		
		}

//		protected MessageUrn(SerializationInfo serializationInfo, StreamingContext streamingContext)
//			: base(serializationInfo, streamingContext)
//		{
//		}

		public Class getMessageClass()
		{
			return this.getMessageClass(true,true);
		}
		
		public Class getMessageClass(boolean throwOnError, boolean ignoreCase)
		{
			if (myUri == null || myUri.getPath()== null || myUri.getPath().isEmpty()) //Was segments.lenght==0
				return null;

			String[] names = myUri.getPath().split(":");
			if (names[0] != "message")
				return null;

			String typeName;

			if (names.length == 2)
				typeName = names[1];
			else if (names.length == 3)
				typeName = names[1] + "." + names[2] + ", " + names[1];
			else if (names.length >= 4)
				typeName = names[1] + "." + names[2] + ", " + names[3];
			else
				return null;

			//This might be a .Net name rather than a Java one so 
			// we must check cache before instantiating
			
			if (_nameToClassCache.get() == null) {
				_nameToClassCache.set(new HashMap<String,Class>());
			}
			
			Class messageType = _nameToClassCache.get().get(typeName);
		    if (messageType == null) {
					 
				try {
					messageType = Class.forName(typeName);
					_nameToClassCache.get().put(typeName,messageType);

//					//Cache dotNet if possible
//					if (messageType.isAssignableFrom(ExternallyNamespaced.class)) {
//						//Use dotNet Namespaces
//						_nameToClassCache.get().put(
//									((ExternallyNamespaced) messageType.newInstance()).getExternalNamespace()+
//									"." + messageType.getSimpleName(),messageType);
//					}
				} catch (ClassNotFoundException e) {
					_log.error("Unable to locate class for " + myUri, e);
				}
		    }
			return messageType;
		}

		@Override
		public String toString() {
			return myUri.toString();
		}

		static String IsInCache(Class type)
		{
			if (_typeToUrnCache == null)
				{
					_typeToUrnCache = new ThreadLocal<Map<Class, String>>();
					_typeToUrnCache.set(new HashMap<Class, String>());
				}

			String urn = _typeToUrnCache.get().get(type);
			if (urn == null) {

				StringBuilder sb = new StringBuilder("urn:message:");

				urn =  GetMessageName(sb, type, true);
				_typeToUrnCache.get().put(type, urn);
			}
			return urn;
		}

		static String GetUrnForType(Class type)
		{
			return IsInCache(type);
		}

		static String GetMessageName(StringBuilder sb, Class type, boolean includeScope)
		{
			
			//Crosstown does not support complex generated names since they won't translate to 
			//dotNet
			if (includeScope) {
				String scope = null;
				if (type.isAssignableFrom(ExternallyNamespaced.class)) {
					//Use dotNet Namespaces
					
					try {
						scope = ((ExternallyNamespaced) type.newInstance()).getExternalNamespace();
					} catch (Exception e) {
						_log.error("Unable to obtain namespace for " + type + 
								".  ExternallyNamespaced classes must have a default constructor.", e);
					}
				} else {
					//Use Java Packages
					scope = type.getPackage().getName();
				}
				if (scope != null && !scope.isEmpty()) {
					sb.append(scope);
					sb.append(":");
				}
			}

			sb.append(type.getSimpleName());

		return sb.toString();
			
			
//            if (type.IsGenericParameter)
//                return "";
//
//			if (includeScope && type.Namespace != null)
//			{
//				String ns = type.Namespace;
//				sb.Append(ns);
//
//				sb.Append(':');
//			}
//
//			if (type.IsNested)
//			{
//				GetMessageName(sb, type.DeclaringType, false);
//				sb.Append('+');
//			}
//
//			if (type.IsGenericType)
//			{
//			    var name = type.GetGenericTypeDefinition().Name;
//
//                //remove `1
//			    int index = name.IndexOf('`');
//                if(index > 0)
//			        name = name.Remove(index);
//                //
//
//			    sb.Append(name);
//				sb.Append('[');
//
//				Type[] arguments = type.GetGenericArguments();
//				for (int i = 0; i < arguments.Length; i++)
//				{
//					if (i > 0)
//						sb.Append(',');
//
//					sb.Append('[');
//					GetMessageName(sb, arguments[i], true);
//					sb.Append(']');
//				}
//
//				sb.Append(']');
//			}
//			else
//				sb.Append(type.Name);
//
//			return sb.ToString();
		}
	}
