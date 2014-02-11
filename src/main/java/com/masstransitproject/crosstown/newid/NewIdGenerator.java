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
package com.masstransitproject.crosstown.newid;

import java.io.IOException;

public class NewIdGenerator {
	private final long _c;
	private final long _d;

	private final Object _sync = new Object();
	private final TickProvider _tickProvider;
	private final byte[] _workerId;
	private final int _workerIndex;
	private long _a;
	private long _b;
	private long _lastTick;

	private int _sequence;

	public NewIdGenerator(TickProvider tickProvider,
			WorkerIdProvider workerIdProvider) throws IOException {
		this(tickProvider, workerIdProvider, 0);

	}

	public NewIdGenerator(TickProvider tickProvider,
			WorkerIdProvider workerIdProvider, int workerIndex)
			throws IOException {
		_workerIndex = workerIndex;
		_workerId = workerIdProvider.getWorkerId(_workerIndex);
		_tickProvider = tickProvider;

		_c = _workerId[0] << 24 | _workerId[1] << 16 | _workerId[2] << 8
				| _workerId[3];
		_d = _workerId[4] << 24 | _workerId[5] << 16;
	}

	public NewId next() {
		long sequence;

		long ticks = _tickProvider.getTicks();
		synchronized (_sync) {
			if (ticks > _lastTick)
				updateTimestamp(ticks);

			if (_sequence == 65535) // we are about to rollover, so we need to
									// increment ticks
				updateTimestamp(_lastTick + 1);

			sequence = _sequence++;
		}

		return new NewId(_a, _b, _c, (_d | sequence));
	}

	private void updateTimestamp(long tick) {

		// ByteBuffer buffer = ByteBuffer.allocate(8);
		// buffer.putLong(tick);
		//
		// buffer.flip();
		//
		//
		// _a = buffer.getInt();
		// _b = buffer.getInt();

		_lastTick = tick;
		_sequence = 0;

		// Java requires this be an unsigned shift in order to be consistent
		// with the c#
		_a = tick >>> 32;

		// Java requires this be stored as a long so the shifting isn't lossy
		_b = tick << 32 >>> 32;
	}
}
