package com.masstransitproject.crosstown.transports;

import com.masstransitproject.crosstown.IEndpointAddress;

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


	public class TransportSettings implements
		ITransportSettings
	{
		public TransportSettings(IEndpointAddress address)
		{
//			Guard.AgainstNull(address, "address");

			setAddress( address);

			setTransactional ( getAddress().IsTransactional());
			setRequireTransactional ( false);
			setTransactionTimeout ( 30000);
//			IsolationLevel = IsolationLevel.Serializable;

			setCreateIfMissing(true);
			setPurgeExistingMessages (false);
		}

		public TransportSettings(IEndpointAddress address, ITransportSettings source)
		{
//			Guard.AgainstNull(address, "address");
//			Guard.AgainstNull(source, "source");

			setAddress(address);

			setTransactional(source.isTransactional());
			setRequireTransactional(source.isRequireTransactional());
			setTransactionTimeout(source.getTransactionTimeout());
//			IsolationLevel(source.IsolationLevel;

			setCreateIfMissing(source.isCreateIfMissing());
			setPurgeExistingMessages(source.isPurgeExistingMessages());
		}

		/// <summary>
		/// The address of the endpoint
		/// </summary>
		private IEndpointAddress Address;

		/// <summary>
		/// True if the endpoint should be transactional. If Transactional is true and the endpoint already
		/// exists and is not transactional, an exception will be thrown.
		/// </summary>
		private boolean Transactional;

		/// <summary>
		/// if the transactional queue is requested and required it will throw an exception if the queue 
		/// exists and is not transactional
		/// </summary>
		private boolean RequireTransactional;

		/// <summary>
		/// The timeout for the transaction if System.Transactions is supported
		/// </summary>
		private long TransactionTimeout;

	    /// <summary>
	    /// The isolation level to use with the transaction if a transactional transport is used
	    /// </summary>
//		private  IsolationLevel IsolationLevel;

	    /// <summary>
		/// The transport should be created if it was not found
		/// </summary>
	    private boolean CreateIfMissing ;

	    /// <summary>
		/// If the transport should purge any existing messages before reading from the queue
		/// </summary>
		private boolean PurgeExistingMessages ;

		public IEndpointAddress getAddress() {
			return Address;
		}

		private void setAddress(IEndpointAddress address) {
			Address = address;
		}

		public boolean isTransactional() {
			return Transactional;
		}

		public void setTransactional(boolean transactional) {
			Transactional = transactional;
		}

		public boolean isRequireTransactional() {
			return RequireTransactional;
		}

		public void setRequireTransactional(boolean requireTransactional) {
			RequireTransactional = requireTransactional;
		}

		public long getTransactionTimeout() {
			return TransactionTimeout;
		}

		public void setTransactionTimeout(long transactionTimeout) {
			TransactionTimeout = transactionTimeout;
		}

		public boolean isCreateIfMissing() {
			return CreateIfMissing;
		}

		public void setCreateIfMissing(boolean createIfMissing) {
			CreateIfMissing = createIfMissing;
		}

		public boolean isPurgeExistingMessages() {
			return PurgeExistingMessages;
		}

		public void setPurgeExistingMessages(boolean purgeExistingMessages) {
			PurgeExistingMessages = purgeExistingMessages;
		}
		
		
		
	}
