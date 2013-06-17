package com.masstransitproject.crosstown.stact;




		/// <summary>
		/// A one-way communication containing messages of the specified type
		/// </summary>
		/// <typeparam name="T"></typeparam>
		public interface Channel< T> 
		{
			/// <summary>
			/// Send a message to the channel
			/// </summary>
			/// <param name="message">The message to send</param>
			void Send(T message);
		}