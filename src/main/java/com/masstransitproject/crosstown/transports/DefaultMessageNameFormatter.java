package com.masstransitproject.crosstown.transports;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.masstransitproject.crosstown.ExternallyNamespaced;
import com.masstransitproject.crosstown.MessageUrn;

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


    public class DefaultMessageNameFormatter implements
        IMessageNameFormatter
    {
    	

        private static final Logger  _log = LoggerFactory.getLogger(DefaultMessageNameFormatter.class);
        
        final Map<Class, String> _cache;
        final String _genericArgumentSeparator;
        final String _genericTypeSeparator;
        final String _namespaceSeparator;
        final String _nestedTypeSeparator;

        public DefaultMessageNameFormatter(String genericArgumentSeparator, String genericTypeSeparator,
                                           String namespaceSeparator, String nestedTypeSeparator)
        {
            _genericArgumentSeparator = genericArgumentSeparator;
            _genericTypeSeparator = genericTypeSeparator;
            _namespaceSeparator = namespaceSeparator;
            _nestedTypeSeparator = nestedTypeSeparator;

            _cache = new ConcurrentHashMap<Class, String>();
        }

        public MessageName getMessageName(Class type)
        {
            return new MessageName(_cache.get(type));
        }

        String CreateMessageName(Class type)
        {
            if (ParameterizedType.class.isAssignableFrom(type))
                throw new IllegalArgumentException("An open generic type cannot be used as a message name");

            StringBuilder sb = new StringBuilder("");

            return GetMessageName(sb, type, null);
        }

        String GetMessageName(StringBuilder sb, Class type, String scope)
        {
            

			//Crosstown does not support complex generated names since they won't translate to 
			//dotNet
        
				String ns = null;
				if (ExternallyNamespaced.class.isAssignableFrom(type)) {
					//Use dotNet Namespaces
					
					try {
						ns = ((ExternallyNamespaced) type.newInstance()).getExternalNamespace();
					} catch (Exception e) {
						_log.error("Unable to obtain namespace for " + type + 
								".  ExternallyNamespaced classes must have a default constructor.", e);
					}
				} else {
					//Use Java Packages
					ns = type.getPackage().getName();
				}
				if (ns != null && !ns.isEmpty() 
						&&  (!ns.equals(scope))
		                ) {
					sb.append(ns);
					sb.append(":");
				}
			

			sb.append(type.getSimpleName());

		return sb.toString();
			
        }
    }
