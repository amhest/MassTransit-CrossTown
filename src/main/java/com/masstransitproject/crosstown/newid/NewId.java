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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

import com.masstransitproject.crosstown.newid.formatters.DashedHexFormatter;
import com.masstransitproject.crosstown.newid.formatters.HexFormatter;
import com.masstransitproject.crosstown.newid.providers.NetworkAddressWorkerIdProvider;
import com.masstransitproject.crosstown.newid.providers.StopwatchTickProvider;
import com.masstransitproject.crosstown.util.FineGrainTimestamp;

/**
 * A NewId is a type that fits into the same space as a
 * Guid/Uuid/uniqueidentifier, but is guaranteed to be both unique and ordered,
 * assuming it is generated using a single instance of the generator for each
 * network address used.
 */
public class NewId extends Object implements

Comparable<NewId> {
	public static final NewId EMPTY = new NewId(0, 0, 0, 0);
	private static INewIdFormatter _braceFormatter = new DashedHexFormatter('{', '}',
			false);
	private static INewIdFormatter _dashedHexFormatter = new DashedHexFormatter();

	private static NewIdGenerator _generator;

	private static INewIdFormatter _hexFormatter = new HexFormatter();
	private static INewIdFormatter _parenFormatter = new DashedHexFormatter('(', ')',
			false);
	private static ITickProvider _tickProvider;
	private static IWorkerIdProvider _workerIdProvider;

	private final long _a;
	private final long _b;
	private final long _c;
	private final long _d;

	/**
	 * Creates a NewId using the specified byte array.
	 * 
	 * @param bytes
	 */
	public NewId(byte[] bytes) {
		if (bytes == null)
			throw new IllegalArgumentException("bytes are null");
		if (bytes.length != 16)
			throw new IllegalArgumentException("Exactly 16 bytes expected");
		ABCD holder = fromByteArray(bytes);
		_a = holder.a;
		_b = holder.b;
		_c = holder.c;
		_d = holder.d;
	}

	public NewId() {
		this(0, 0, 0, 0);
	}

	public NewId(String value) {
		this(UUID.fromString(value));
	}

	public NewId(UUID guid) {

		byte[] bytes = convertToNewIdBytes(guid);

		ABCD holder = fromByteArray(bytes);
		_a = holder.a;
		_b = holder.b;
		_c = holder.c;
		_d = holder.d;
	}

	private byte[] convertToNewIdBytes(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		bb.flip();
		byte[] guidBytes = bb.array();
		byte[] newIdBytes = Arrays.copyOf(guidBytes, guidBytes.length);

		
			// Reverse part a
			newIdBytes[0] = guidBytes[3];
			newIdBytes[1] = guidBytes[2];
			newIdBytes[2] = guidBytes[1];
			newIdBytes[3] = guidBytes[0];

			// Flip each int in b
			newIdBytes[4] = guidBytes[5];
			newIdBytes[5] = guidBytes[4];
			newIdBytes[6] = guidBytes[7];
			newIdBytes[7] = guidBytes[6];
		
		return newIdBytes;
	}

	public NewId(long a, long b, long c, long d) {
		_a = a;
		_b = b;
		_c = c;
		_d = d;
	}

	
	private static NewIdGenerator getGenerator() {
		if (_generator == null) {
			try {
				_generator = new NewIdGenerator(getTickProvider(),
						getWorkerIdProvider());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return _generator;
	}

	private static IWorkerIdProvider getWorkerIdProvider() {
		if (_workerIdProvider == null) {
			_workerIdProvider = new NetworkAddressWorkerIdProvider();
		}
		return _workerIdProvider;
	}

	private static ITickProvider getTickProvider() {
		if (_tickProvider == null) {
			_tickProvider = new StopwatchTickProvider();
		}
		return _tickProvider;
	}

	public Timestamp getTimestamp() {

		long ticks = ((_a) << 32) | (_b);
		return FineGrainTimestamp.fromNanos(ticks);

	}


	@Override
	public int compareTo(NewId other) {
		if (_a != other._a)
			return (_a < other._a) ? -1 : 1;
		if (_b != other._b)
			return (_b < other._b) ? -1 : 1;
		if (_c != other._c)
			return (_c < other._c) ? -1 : 1;
		if (_d != other._d)
			return (_d < other._d) ? -1 : 1;

		return 0;
	}

	public boolean equals(NewId other) {
		return ((int) other._a) == ((int) _a) && ((int) other._b) == ((int) _b)
				&& ((int) other._c) == ((int) _c)
				&& ((int) other._d) == ((int) _d);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof NewId) {
			return this.equals((NewId) other);
		}
		return false;
	}

	public String toString(String format, Object formatProvider) {
		if (format == null || format.isEmpty())
			format = "D";

		boolean sequential = false;
		if (format.length() == 2
				&& (format.charAt(1) == 'S' || format.charAt(1) == 's'))
			sequential = true;
		else if (format.length() != 1)
			throw new RuntimeException(
					"The format String must be exactly one character or null");

		char formatCh = format.charAt(0);
		byte[] bytes = sequential ? getSequentialFormatteryArray()
				: getFormatteryArray();

		if (formatCh == 'B' || formatCh == 'b')
			return _braceFormatter.format(bytes);
		if (formatCh == 'P' || formatCh == 'p')
			return _parenFormatter.format(bytes);
		if (formatCh == 'D' || formatCh == 'd')
			return _dashedHexFormatter.format(bytes);
		if (formatCh == 'N' || formatCh == 'n')
			return _hexFormatter.format(bytes);

		throw new RuntimeException("The format String was not valid");
	}

	public String toString(INewIdFormatter formatter, boolean sequential) {
		byte[] bytes = sequential ? getSequentialFormatteryArray()
				: getFormatteryArray();

		return formatter.format(bytes);
	}

	private byte[] getFormatteryArray() {
		byte[] bytes = new byte[16];
		bytes[0] = (byte) (_d >> 24);
		bytes[1] = (byte) (_d >> 16);
		bytes[2] = (byte) (_d >> 8);
		bytes[3] = (byte) _d;
		bytes[4] = (byte) (_c >> 8);
		bytes[5] = (byte) _c;
		bytes[6] = (byte) (_c >> 24);
		bytes[7] = (byte) (_c >> 16);
		bytes[8] = (byte) (_b >> 8);
		bytes[9] = (byte) _b;
		bytes[10] = (byte) (_a >> 24);
		bytes[11] = (byte) (_a >> 16);
		bytes[12] = (byte) (_a >> 8);
		bytes[13] = (byte) _a;
		bytes[14] = (byte) (_b >> 24);
		bytes[15] = (byte) (_b >> 16);

		return bytes;
	}

	private byte[] getSequentialFormatteryArray() {
		byte[] bytes = new byte[16];
		bytes[0] = (byte) (_a >> 24);
		bytes[1] = (byte) (_a >> 16);
		bytes[2] = (byte) (_a >> 8);
		bytes[3] = (byte) _a;
		bytes[4] = (byte) (_b >> 24);
		bytes[5] = (byte) (_b >> 16);
		bytes[6] = (byte) (_b >> 8);
		bytes[7] = (byte) _b;
		bytes[8] = (byte) (_c >> 24);
		bytes[9] = (byte) (_c >> 16);
		bytes[10] = (byte) (_c >> 8);
		bytes[11] = (byte) _c;
		bytes[12] = (byte) (_d >> 24);
		bytes[13] = (byte) (_d >> 16);
		bytes[14] = (byte) (_d >> 8);
		bytes[15] = (byte) _d;

		return bytes;
	}

	public UUID toGuid() {
		ByteBuffer bb = ByteBuffer.allocate(16);
		bb.putInt((int) _d);
		bb.putShort((short) _c);
		bb.putShort((short) (_c >> 16));
		bb.put((byte) (_b >> 8));
		bb.put((byte) _b);
		bb.put((byte) (_a >> 24));
		bb.put((byte) (_a >> 16));
		bb.put((byte) (_a >> 8));
		bb.put((byte) _a);
		bb.put((byte) (_b >> 24));
		bb.put((byte) (_b >> 16));

		// ready to read back
		bb.flip();

		// each get moves the buffer pointer
		return new UUID(bb.getLong(), bb.getLong());
	}

	public UUID toSequentialGuid() {
		ByteBuffer bb = ByteBuffer.allocate(16);
		bb.putInt((int) _a);
		bb.putShort((short) (_b >> 16));
		bb.putShort((short) _b);
		bb.put((byte) (_c >> 24));
		bb.put((byte) (_c >> 16));
		bb.put((byte) (_c >> 8));
		bb.put((byte) (_c));
		bb.put((byte) (_d >> 24));
		bb.put((byte) (_d >> 16));
		bb.put((byte) (_d >> 8));
		bb.put((byte) (_d));

		// ready to read back
		bb.flip();

		// each get moves the buffer pointer
		return new UUID(bb.getLong(), bb.getLong());
	}


	public byte[] toByteArray() {

		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt((int) _d);
		buffer.putInt((int) _c);

		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt((int) _b);
		bb.flip();
		byte[] b = bb.array();
		buffer.order(ByteOrder.BIG_ENDIAN);
		buffer.put(b[2]);
		buffer.put(b[3]);
		buffer.putInt((int) _a);
		buffer.put(b[0]);
		buffer.put(b[1]);

		return buffer.array();
	}

	@Override
	public String toString() {
		return toString("D", null);
	}

	public String toString(String format) {
		return toString(format, null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) _a;
		result = prime * result + (int) _b;
		result = prime * result + (int) _c;
		result = prime * result + (int) _d;
		return result;
	}

	public static void setGenerator(NewIdGenerator generator) {
		_generator = generator;
	}

	public static void setWorkerIdProvider(IWorkerIdProvider provider) {
		_workerIdProvider = provider;
	}

	public static void setTickProvider(ITickProvider provider) {
		_tickProvider = provider;
	}

	public static NewId next() {
		return getGenerator().next();
	}

	public static UUID nextGuid() {
		return getGenerator().next().toGuid();
	}


	private static ABCD fromByteArray(byte[] bytes) {
		ABCD holder = new ABCD();
		holder.a = bytesToInt(bytes[10], bytes[11], bytes[12], bytes[13]);
		holder.b = bytesToInt(bytes[14], bytes[15], bytes[8], bytes[9]);
		holder.c = bytesToInt(bytes[7], bytes[6], bytes[5], bytes[4]);
		holder.d = bytesToInt(bytes[3], bytes[2], bytes[1], bytes[0]);

		return holder;
	}

    static int bytesToInt(byte f, byte g, byte h, byte i) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(f);
		buffer.put(g);
		buffer.put(h);
		buffer.put(i);
		buffer.flip();// need flip before we read back
		return buffer.getInt();
	}

	private static class ABCD {

		public long a;
		public long b;
		public long c;
		public long d;

	}
}
