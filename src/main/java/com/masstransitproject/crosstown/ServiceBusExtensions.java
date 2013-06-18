package com.masstransitproject.crosstown;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    /// <summary>
    /// Extension methods pertinent to service bus logic, but on
    /// type <see cref="Type"/> - handles different sorts of reflection
    /// logic.
    /// </summary>
    public  class ServiceBusExtensions
    {
        /// <summary>
        /// Transforms the type of message to a normalized string which can be used
        /// for naming a queue on a transport.
        /// </summary>
        /// <param name="messageType">The message class/interface type</param>
        /// <returns>The normalized name for this type</returns>
        public static String ToMessageName( Class messageType)
        {
          String messageName;
          
          //TODO See TypeUtils
          
          
//            if (messageType.IsGenericType)
//            {
//                messageName = messageType.GetGenericTypeDefinition().FullName;
//                messageName += "[";
//                string prefix = "";
//                foreach (Type argument in messageType.GetGenericArguments())
//                {
//                    messageName += prefix + "[" + argument.ToMessageName() + "]";
//                    prefix = ",";
//                }
//                messageName += "]";
//            }
//            else
//            {
                messageName = messageType.getName();
//            }
        	
        	

//            String assembly = messageType.Assembly.FullName;
//            if (assembly != null)
//            {
//                assembly = ", " + assembly.Substring(0, assembly.IndexOf(','));
//            }
//            else
//            {
//                assembly = "";
//            }
//            return ( messageName+ assembly);

            return messageName;
        }

        /// <summary>
        /// Returns true if the specified type is an allowed message type, i.e.
        /// that it doesn't come from the .Net core assemblies or is without a namespace,
        /// amongst others.
        /// </summary>
        /// <param name="type">The type to inspect</param>
        /// <returns>True if the message can be sent, otherwise false</returns>
        public static boolean IsAllowedMessageType( Class type)
        {
            if (type.getPackage() == null)
                return false;

//            if (type.Assembly == typeof(object).Assembly)
//                return false;
//
//            if (type.Namespace == "System")
//                return false;

            if (type.getPackage().getName().startsWith("java"))
                return false;

//            if (type.IsGenericType && type.GetGenericTypeDefinition() == typeof(CorrelatedBy<>))
//                return false;

            return true;
        }

        /// <summary>
        /// Returns all the message types that are available for the specified type. This will
        /// return any base classes or interfaces implemented by the type that are allowed
        /// message types.
        /// </summary>
        /// <param name="type">The type to inspect</param>
        /// <returns>An enumeration of valid message types implemented by the specified type</returns>
        public static List<Class> GetMessageTypes(Class type)
        {
        	
        	List<Class> clazzs = new ArrayList<Class>();
           clazzs.add( type);

            Class baseType = type.getSuperclass();
            while ((baseType != null) && IsAllowedMessageType(baseType))
            {
            	clazzs.add(  baseType);

                baseType = baseType.getSuperclass();
            }


            for (Class interfaceType : type.getInterfaces())
            {
            	if (IsAllowedMessageType(interfaceType))
            		clazzs.add( interfaceType);
            }      
            return clazzs;
           }

        /// <summary>
        /// Determines whether the given <see cref="IEndpointAddress"/> is a control bus by examining the uri.
        /// </summary>
        /// <param name="address">The address.</param>
        /// <returns>
        ///   <c>true</c> if the URI of of the given <see cref="IEndpointAddress"/> end with '_control'; otherwise, <c>false</c>.
        /// </returns>
        public static boolean IsControlAddress( URI address)
        {
            return address.toString().endsWith("_control");
        }
    }