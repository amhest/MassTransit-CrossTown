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
import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.uuid.impl.UUIDUtil;
import com.masstransitproject.crosstown.newid.formatters.DashedHexFormatter;
import com.masstransitproject.crosstown.newid.formatters.HexFormatter;
import com.masstransitproject.crosstown.newid.providers.NetworkAddressWorkerIdProvider;
import com.masstransitproject.crosstown.newid.providers.StopwatchTickProvider;
import com.masstransitproject.crosstown.util.FineGrainTimestamp;

/// <summary>
/// A NewId is a type that fits into the same space as a Guid/Uuid/uniqueidentifier,
/// but is guaranteed to be both unique and ordered, assuming it is generated using
/// a single instance of the generator for each network address used.
/// </summary>
public class NewId extends Object implements

Comparable<NewId> {
	public static final NewId Empty = new NewId(0, 0, 0, 0);
	static INewIdFormatter _braceFormatter = new DashedHexFormatter('{', '}',
			false);
	static INewIdFormatter _dashedHexFormatter = new DashedHexFormatter();

	static NewIdGenerator _generator;

	static INewIdFormatter _hexFormatter = new HexFormatter();
	static INewIdFormatter _parenFormatter = new DashedHexFormatter('(', ')',
			false);
	static ITickProvider _tickProvider;
	static IWorkerIdProvider _workerIdProvider;

	private final long _a;
	private final long _b;
	private final long _c;
	private final long _d;

	// / <summary>
	// / Creates a NewId using the specified byte array.
	// / </summary>
	// / <param name="bytes"></param>
	public NewId(byte[] bytes) {
		if (bytes == null)
			throw new IllegalArgumentException("bytes are null");
		if (bytes.length != 16)
			throw new IllegalArgumentException("Exactly 16 bytes expected");
		ABCD holder = FromByteArray(bytes);
		_a = holder.a;
		_b = holder.b;
		_c = holder.c;
		_d = holder.d;
	}

	public NewId() {
		this(0, 0, 0, 0);
	}

	public NewId(String value) {
		if (value == null || value.length() == 0)
			throw new IllegalArgumentException(
					"value must not be null or empty");

		UUID guid = UUID.fromString(value);

		byte[] bytes = convertToBytes(guid);
		ABCD holder = FromByteArray(bytes);
		_a = holder.a;
		_b = holder.b;
		_c = holder.c;
		_d = holder.d;
	}

	private byte[] convertToBytes(UUID uuid) {
//		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
//		bb.putLong(uuid.getMostSignificantBits());
//		bb.putLong(uuid.getLeastSignificantBits());
//		return bb.array();
		
		return  UUIDUtil.asByteArray(uuid);
	}
	public NewId(long a, long b, long c, long d) {
		_a = a;
		_b = b;
		_c = c;
		_d = d;
	}

	public NewId(int a, short b, short c, byte d, byte e, byte f, byte g,
			byte h, byte i, byte j, byte k) {
		_a = (f << 24) | (g << 16) | (h << 8) | i;
		_b = (j << 24) | (k << 16) | (d << 8) | e;
		_c = (c << 16) | b;
		_d = a;
	}

	static NewIdGenerator getGenerator() {
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

	static IWorkerIdProvider getWorkerIdProvider() {
		if (_workerIdProvider == null) {
			_workerIdProvider = new NetworkAddressWorkerIdProvider();
		}
		return _workerIdProvider;
	}

	static ITickProvider getTickProvider() {
		if (_tickProvider == null) {
			_tickProvider = new StopwatchTickProvider();
		}
		return _tickProvider;
	}

	public Timestamp getTimestamp() {

		long ticks = (((long)_a)  << 32) | ((long)_b);
		return  FineGrainTimestamp.fromNanos(ticks);

	}

	// public int compareTo(Object obj)
	// {
	// if (obj == null)
	// return 1;
	// if (!(obj instanceof NewId))
	// throw new IllegalArgumentException("Argument must be a NewId");
	//
	// return compareTo((NewId)obj);
	// }

	@Override
	public int compareTo(NewId other) {
		if (_a != other._a)
			return ((long) _a < (long) other._a) ? -1 : 1;
		if (_b != other._b)
			return ((long) _b < (long) other._b) ? -1 : 1;
		if (_c != other._c)
			return ((long) _c < (long) other._c) ? -1 : 1;
		if (_d != other._d)
			return ((long) _d < (long) other._d) ? -1 : 1;

		return 0;
	}

	public boolean Equals(NewId other) {
		return other._a == _a && other._b == _b && other._c == _c
				&& other._d == _d;
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
		byte[] bytes = sequential ? GetSequentialFormatteryArray()
				: GetFormatteryArray();

		if (formatCh == 'B' || formatCh == 'b')
			return _braceFormatter.Format(bytes);
		if (formatCh == 'P' || formatCh == 'p')
			return _parenFormatter.Format(bytes);
		if (formatCh == 'D' || formatCh == 'd')
			return _dashedHexFormatter.Format(bytes);
		if (formatCh == 'N' || formatCh == 'n')
			return _hexFormatter.Format(bytes);

		throw new RuntimeException("The format String was not valid");
	}

	public String toString(INewIdFormatter formatter, boolean sequential) {
		byte[] bytes = sequential ? GetSequentialFormatteryArray()
				: GetFormatteryArray();

		return formatter.Format(bytes);
	}

	byte[] GetFormatteryArray() {
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

	byte[] GetSequentialFormatteryArray() {
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

	public UUID ToGuid() {
		ByteBuffer bb = ByteBuffer.allocate(16);
		bb.putInt((int)_d);
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

	public UUID ToSequentialGuid() {
		ByteBuffer bb = ByteBuffer.allocate(16);
		bb.putInt((int)_a);
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

	public byte[] ToByteArray() {
		byte[] bytes = new byte[16];

		bytes[0] = (byte) (_d);
		bytes[1] = (byte) (_d >> 8);
		bytes[2] = (byte) (_d >> 16);
		bytes[3] = (byte) (_d >> 24);
		bytes[4] = (byte) (_c);
		bytes[5] = (byte) (_c >> 8);
		bytes[6] = (byte) (_c >> 16);
		bytes[7] = (byte) (_c >> 24);
		bytes[8] = (byte) (_b >> 8);
		bytes[9] = (byte) (_b);
		bytes[10] = (byte) (_a >> 24);
		bytes[11] = (byte) (_a >> 16);
		bytes[12] = (byte) (_a >> 8);
		bytes[13] = (byte) (_a);
		bytes[14] = (byte) (_b >> 24);
		bytes[15] = (byte) (_b >> 16);

		return bytes;
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
		long result = 1;
		result = prime * result + _a;
		result = prime * result + _b;
		result = prime * result + _c;
		result = prime * result + _d;
		return (int) result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewId other = (NewId) obj;
		if (_a != other._a)
			return false;
		if (_b != other._b)
			return false;
		if (_c != other._c)
			return false;
		if (_d != other._d)
			return false;
		return true;
	}

	public static void SetGenerator(NewIdGenerator generator) {
		_generator = generator;
	}

	public static void SetWorkerIdProvider(IWorkerIdProvider provider) {
		_workerIdProvider = provider;
	}

	public static void SetTickProvider(ITickProvider provider) {
		_tickProvider = provider;
	}

	public static NewId Next() {
		return getGenerator().Next();
	}

	public static UUID NextGuid() {
		return getGenerator().Next().ToGuid();
	}

	static ABCD FromByteArray(byte[] bytes) {
		ABCD holder = new ABCD();
		holder.a = bytes[10] << 24 | bytes[11] << 16 | bytes[12] << 8
				| bytes[13];
		holder.b = bytes[14] << 24 | bytes[15] << 16 | bytes[8] << 8 | bytes[9];
		holder.c = bytes[7] << 24 | bytes[6] << 16 | bytes[5] << 8 | bytes[4];
		holder.d = bytes[3] << 24 | bytes[2] << 16 | bytes[1] << 8 | bytes[0];

		return holder;
	}

	private static class ABCD {

		public int a;
		public int b;
		public int c;
		public int d;

	}
}
