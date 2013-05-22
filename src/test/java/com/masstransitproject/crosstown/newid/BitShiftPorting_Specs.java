package com.masstransitproject.crosstown.newid;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class BitShiftPorting_Specs {

	@Test
	public void TestRoundTripForNow() {
	
		doRoundTrip(System.currentTimeMillis());
		
	}

	@Test
	public void TestRoundTripForMaxLong() {
	
		doRoundTrip(Long.MAX_VALUE);
		doRoundTrip(Long.MAX_VALUE-1);
		doRoundTrip((long) Math.pow(Long.MAX_VALUE,.5));
		
	}
	@Test
	public void TestRoundTripForMinLong() {
	
		doRoundTrip(Long.MIN_VALUE);
		doRoundTrip(Long.MAX_VALUE+1);
		doRoundTrip((long) -Math.pow(Long.MAX_VALUE,.5)+1);
		
	}

	@Test
	public void TestTwiddleBits() {
	
		long value =0;
		//This is the same as MIN_LONG		
		doRoundTrip(value |= 1L << 63);

		value =0;
		doRoundTrip((value |= 1L << 63) & Long.MAX_VALUE);
		
		value =0;
		doRoundTrip(value |= 1L << 62);
		
		value =0;
		doRoundTrip(value |= 1L << 32);
		
	}
	
	@Test
	public void TestRoundTripIntUpCast() {
	
		doRoundTrip(Integer.MAX_VALUE);
		
	}
	@Test
	public void TestRoundTripForDoubleDownCast() {
	
		doRoundTrip((long) Double.MAX_VALUE);
		
	}
	private void doRoundTrip(long ticks) {
		
		//c# Code
        //_a = (int)(tick >> 32);
        //_b = (int)(tick & 0xFFFFFFFF);
		//long ticks = (long)(((ulong)_a) << 32 | (uint)_b);
		
		System.out.println("Input=" + ticks);
		int _a = (int) (ticks >>> 32);
		System.out.println("a=" +_a);
		long _b = (long) (ticks & 0xFFFFFFFFl) ; 
		System.out.println("b=" +_b);
		long rebuilt = (((long)_a)  << 32) | (_b);

		System.out.println("result=" +rebuilt);
		org.junit.Assert.assertEquals(ticks, rebuilt);
	}
	


//	private byte[] ToByteArray(ABCD abcd) {
//		byte[] bytes = new byte[16];
//
//		bytes[0] = (byte) (abcd.d);
//		bytes[1] = (byte) (abcd.d >> 8);
//		bytes[2] = (byte) (abcd.d >> 16);
//		bytes[3] = (byte) (abcd.d >> 24);
//		bytes[4] = (byte) (abcd.c);
//		bytes[5] = (byte) (abcd.c >> 8);
//		bytes[6] = (byte) (abcd.c >> 16);
//		bytes[7] = (byte) (abcd.c >> 24);
//		bytes[8] = (byte) (abcd.b >> 8);
//		bytes[9] = (byte) (abcd.b);
//		bytes[10] = (byte) (abcd.a >> 24);
//		bytes[11] = (byte) (abcd.a >> 16);
//		bytes[12] = (byte) (abcd.a >> 8);
//		bytes[13] = (byte) (abcd.a);
//		bytes[14] = (byte) (abcd.b >> 24);
//		bytes[15] = (byte) (abcd.b >> 16);
//
//		return bytes;
//	}

	private byte[] ToByteArray(ABCD abcd) {
		byte[] bytes = new byte[16];

		byte[] td = NewId.longToBytes(abcd.d);
		byte[] tc = NewId.longToBytes(abcd.c);
		byte[] tb = NewId.longToBytes(abcd.b);
		byte[] ta = NewId.longToBytes(abcd.a);
		bytes[0] = td[7];
		bytes[1] = td[6];
		bytes[2] = td[5];
		bytes[3] = td[4];
		bytes[4] = tc[7];
		bytes[5] = tc[6];
		bytes[6] = tc[5];
		bytes[7] = tc[4];
		bytes[8] = tb[6];
		bytes[9] = tb[7];
		bytes[10] = ta[4];
		bytes[11] = ta[5];
		bytes[12] = ta[6];
		bytes[13] = ta[7];
		bytes[14] = tb[4];
		bytes[15] = tb[5];

		return bytes;
	}
	
	private static class ABCD {

		public long a;
		public long b;
		public long c;
		public long d;
		@Override
		public String toString() {
			return "ABCD [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
		}

	}
    ABCD FromByteArray(byte[] bytes) {
		ABCD holder = new ABCD();
		holder.a = NewId.bytesToInt(bytes[10],bytes[11],bytes[12] ,bytes[13]);
		holder.b = NewId.bytesToInt(bytes[14],bytes[15],bytes[8],bytes[9]);
		holder.c = NewId.bytesToInt(bytes[7],bytes[6],bytes[5],bytes[4]);
		holder.d = NewId.bytesToInt(bytes[3],bytes[2],bytes[1],bytes[0]);
		
		return holder;
	}
	

//	static ABCD FromByteArray(byte[] bytes) {
//		ABCD holder = new ABCD();
//		holder.a = bytes[10] << 24 | bytes[11] << 16 | bytes[12] << 8
//				| bytes[13];
//		holder.b = bytes[14] << 24 | bytes[15] << 16 | bytes[8] << 8 | bytes[9];
//		holder.c = bytes[7] << 24 | bytes[6] << 16 | bytes[5] << 8 | bytes[4];
//		holder.d = bytes[3] << 24 | bytes[2] << 16 | bytes[1] << 8 | bytes[0];
//		
//		return holder;
//	}
	
	
	@Test
	public void should_convert_from_four_longs_to_four_bytes_and_back() {
		byte[][] byteArrays = new byte[][] {
		{-10, 29, 0, 0, -113, 70, -1, -1, -114, 96, 19, 0, 77, -96, -89, -87},
				{-10, 29, 0, 0, -113, 70, -1, -1, 125, -56, 19, 0, 77, -66, -20, 43},
						{-10, 29, 0, 0, -113, 70, -1, -1, 34, -80, 19, 0, 77, -62, -83, 22},
							{0, 0, 29, -10, 70, -113, -1, -1, -41, 48, 19, 0, 79, 92, -11, -29},
							{0, 0, 29, -10, 70, -113, -1, -1, -39, -112, 19, 0, 80, -15, -59, -74},
								{-10, 29, 0, 0, -113, 70, -1, -1, -9, 96, 19, 0, 77, -59, 120, -92}};
		
		for (int i = 0; i < byteArrays.length; i++) {
			
			byte[] testArr = byteArrays[i];
			ABCD myLongs = FromByteArray(testArr);
			byte[] resultArr = ToByteArray(myLongs);
			System.out.println("test: " + Arrays.toString(testArr));
			System.out.println("result: " + Arrays.toString(resultArr));
			Assert.assertArrayEquals(testArr, resultArr);
		}
	}
	
	
}
