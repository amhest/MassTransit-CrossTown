    
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
package com.masstransitproject.crosstown;

import java.util.HashMap;
import java.util.Map;

	public class MessageUrn 
	{
		
		static ThreadLocal<Map<Class, String>> _cache;


		private static String IsInCache(Class type)
		{
			
			if (_cache == null) {
				_cache = new ThreadLocal<Map<Class, String>>();
				_cache.set(new HashMap<Class, String>());
			}
			return _cache.get().get(type);
		}

		private static String AddToCache(Class type,String urn)
		{
			
			if (_cache == null) {
				_cache = new ThreadLocal<Map<Class, String>>();
				_cache.set(new HashMap<Class, String>());
			}
			return _cache.get().put(type,urn);
		}

		public static String GetUrnForClass(Class type)
		{
			String s = IsInCache(type);
			if (s == null) {
					
					
					s = GetMessageName(new StringBuilder("urn:message:"), type, true);
					AddToCache(type, s);
			}
			return s;
		}

		private static String GetMessageName(StringBuilder sb, Class type, boolean includeScope)
		{
            //if (type.IsGenericParameter)
			if (type.isAnonymousClass() || type.isLocalClass())
                return "";

			if (includeScope && type.getPackage() != null)
			{
				String ns = type.getPackage().getName();
				sb.append(ns);

				sb.append(':');
			}

			if (type.isMemberClass())
			{
				GetMessageName(sb, type.getDeclaringClass(), false);
				sb.append('+');
			}

//			if (type.IsGenericClass)
//			{
//			    var name = type.GetGenericClassDefinition().Name;
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
//				Class[] arguments = type.GetGenericArguments();
//				for (int i = 0; i < arguments.Length; i++)
//				{
//					if (i > 0)
//						sb.append(',');
//
//					sb.append('[');
//					GetMessageName(sb, arguments[i], true);
//					sb.append(']');
//				}
//
//				sb.append(']');
//			}
//			else
				sb.append(type.getName());

			return sb.toString();
		}
	}
