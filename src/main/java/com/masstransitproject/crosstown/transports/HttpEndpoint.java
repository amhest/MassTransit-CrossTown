package com.masstransitproject.crosstown.transports;

import java.net.URI;

import org.slf4j.Logger;

import com.masstransitproject.crosstown.IEndpoint;

/// Copyright 2007-2008 The Apache Software Foundation.
/// 
/// Licensed under the Apache License, Version 2.0 (the "License"); you may not use 
/// this file except in compliance with the License. You may obtain a copy of the 
/// License at 
/// 
///   http://www.apache.org/licenses/LICENSE-2.0 
/// 
/// Unless required by applicable law or agreed to in writing, software distributed 
/// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
/// CONDITIONS OF ANY KIND, either express or implied. See the License for the 
/// specific language governing permissions and limitations under the License.


	/// <summary>
	/// An HttpEndpoint is designed to allow applications to participate in a remote service bus instance
	/// via HTTP instead of MSMQ. The messages are delivered through an ASHX handler within IIS to leverage
	/// the already available web serving platform and to integrate with other application concerns, such as
	/// security and logging. 
	/// 
	/// Example URI structure
	/// 
	///		http://localhost/bus/endpoint_name
	/// 
	///	When a subscription is obtained by the remote service bus that is within the network, it would
	/// publish using a mapped address:
	/// 
	///		http://localhost/bus/endpoint_name/proxy/identifier
	/// 
	/// </summary>

	public class HttpEndpoint 
//	implements
//		IEndpoint
	{

    	static final Logger _log = org.slf4j.LoggerFactory.getLogger(HttpEndpoint.class);
    	static final Logger _messageLog = org.slf4j.LoggerFactory.getLogger("MassTransit.Messages");
    	

//		private final BinaryFormatter _formatter = new BinaryFormatter();
		private final URI _uri;

		public HttpEndpoint(URI uri)
		{
			_uri = uri;
		}

		public static String getScheme()
		{return "http"; 
		}

		
		public URI getUri()
		{ return _uri; 
		}

		public <T extends Object> void Send(T message) 
		{
			throw new UnsupportedOperationException("NotImplementedException()");
		}

		public <T extends Object> void Send(T message, long timeToLive) 
		{
			throw new UnsupportedOperationException("Finish coding");
//			WebRequest client = HttpWebRequest.Create(_uri);
//			client.Method = "PUT";
//			client.Timeout = (int) timeToLive.TotalMilliseconds;
//		
//			using(Stream s = client.GetRequestStream())
//			{
//				_formatter.Serialize(s, message);
//			}
//
//			using ( WebResponse response = client.GetResponse() )
//			{
//				response.Close();
//			}
		}

	    public Object Receive(long timeout)
		{
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				//Ignore
			}

			return null;
		}

//	    public object Receive(long timeout, Predicate<object> accept)
//		{
//			Thread.Sleep(timeout);
//
//			return null;
//		}

	    public void Dispose()
		{
		}
	}
