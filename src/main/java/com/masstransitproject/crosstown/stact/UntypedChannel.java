package com.masstransitproject.crosstown.stact;

/// <summary>
	/// A channel to which any message type can be sent
	/// </summary>
	public interface UntypedChannel 
	{
		/// <summary>
		///   Send a message to an untyped channel
		/// </summary>
		/// <typeparam name = "T">The message type</typeparam>
		/// <param name = "message">The message</param>
		<T> void Send(T message);
	}