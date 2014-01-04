package com.masstransitproject.crosstown;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

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


    public class EndpointAddress  implements
        IEndpointAddress
    {
        protected static final String LocalMachineName;
        static {
        	String t = "localhost";
        	try {
				t = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
			}
        	LocalMachineName = t;
        }
        static IEndpointAddress _null;
        boolean _isTransactional;
        URI _uri;

        public EndpointAddress(URI uri)
        {

            _uri = uri;


            _isTransactional = CheckForTransactionalHint(uri, false);
        }

        public EndpointAddress(String uriString)
        {
//            Guard.AgainstEmpty(uriString, "uriString");

//            try
//            {
                try {
					_uri = new URI(uriString);
				} catch (URISyntaxException ex) {
	                throw new IllegalArgumentException("The URI is invalid: " + uriString, ex);
				}
//            }
//            catch (UriFormatException ex)
//            {
//                throw new ArgumentException("The URI is invalid: " + uriString, ex);
//            }

//            _isLocal = () => DetermineIfEndpointIsLocal(_uri);

            _isTransactional = CheckForTransactionalHint(_uri, false);
        }
//
//        public static IEndpointAddress Null
//        {
//            get { return _null ?? (_null = new EndpointAddress(new Uri("null://null/null"))); }
//        }

        public URI getUri()
        {
 return _uri; }
        
            protected void setUri(URI value) {
            	_uri = value; }
        

       

        public String getPath()
        {
           return getUri().getRawPath().substring(1); 
        }

        public boolean isTransactional()
        {
             return _isTransactional; 
             }
       public void setIsTransactional(boolean value) { 
           _isTransactional = value; 
        }

    @Override
        public  String toString()
        {
            return _uri.toString();
        }

        public boolean isLocal() {
        	return isLocal(getUri());
        }
    
        protected boolean isLocal(URI uri)
        {
            String hostName = uri.getHost();
            boolean local = (".".equals(hostName) ||
            		"localhost".equals(hostName) ||
            				LocalMachineName.equals(hostName));
            		

            return local;
        }

        protected static boolean CheckForTransactionalHint(URI uri, boolean defaultTransactional)
        {
        	
        	List<NameValuePair> params = URLEncodedUtils.parse(uri, null);
        	for (NameValuePair nvp : params) {
        		if ("tx".equals(nvp.getName())) {
        			return String.valueOf(defaultTransactional).equalsIgnoreCase(nvp.getValue());
        		}
        	}
        	return false;
        }

    }
