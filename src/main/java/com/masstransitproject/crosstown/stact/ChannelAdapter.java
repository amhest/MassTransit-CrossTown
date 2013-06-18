package com.masstransitproject.crosstown.stact;


	/// <summary>
		/// A channel adapter is a mutable segment in a channel network. The output channel can
		/// be replaced, allowing a new channel network (built via a ChannelVisitor) to be installed
		/// in response to changes (attachments, detachments, etc.) to the network.
		/// 
		/// This particular version handles typed channels
		/// </summary>
		public class ChannelAdapter<T> implements
			Channel<T>
		{
			Channel<T> _output;

			public ChannelAdapter() {
				this(new ShuntChannel<T>());
			
			}

			public ChannelAdapter(Channel<T> output)
			{
				_output = output;
			}

			public Channel<T> getOutput()
			{
				 return _output; 
			}

			public void Send(T message)
			{
				getOutput().Send(message);
			}

//			public void ChangeOutputChannel(Channel<T> old, Channel<T> mutator)
//			{
//				for (;;)
//				{
//					Channel<T> originalValue = _output;
//
//					Channel<T> changedValue = mutator(originalValue);
//
//					Channel<T> previousValue = Interlocked.CompareExchange(ref _output, changedValue, originalValue);
//
//					// if the value returned is equal to the original value, we made the change
//					if (previousValue == originalValue)
//						return;
//				}
//			}
		}

