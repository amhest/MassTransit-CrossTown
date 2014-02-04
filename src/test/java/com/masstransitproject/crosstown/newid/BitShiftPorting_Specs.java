package com.masstransitproject.crosstown.newid;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class BitShiftPorting_Specs {

	@Test
	public void testRoundTripForNow() {
	
		doRoundTrip(System.currentTimeMillis());
		
	}

	@Test
	public void testRoundTripForMaxLong() {
	
		doRoundTrip(Long.MAX_VALUE);
		doRoundTrip(Long.MAX_VALUE-1);
		doRoundTrip((long) Math.pow(Long.MAX_VALUE,.5));
		
	}
	@Test
	public void testRoundTripForMinLong() {
	
		doRoundTrip(Long.MIN_VALUE);
		doRoundTrip(Long.MAX_VALUE+1);
		doRoundTrip((long) -Math.pow(Long.MAX_VALUE,.5)+1);
		
	}

	@Test
	public void testTwiddleBits() {
	
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
	public void testRoundTripIntUpCast() {
	
		doRoundTrip(Integer.MAX_VALUE);
		
	}
	@Test
	public void testRoundTripForDoubleDownCast() {
	
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
		long _b = ticks & 0xFFFFFFFFl ; 
		System.out.println("b=" +_b);
		long rebuilt = (((long)_a)  << 32) | (_b);

		System.out.println("result=" +rebuilt);
		org.junit.Assert.assertEquals(ticks, rebuilt);
	}
	

    public byte[] toByteArray(ABCD abcd) {
		
	    ByteBuffer buffer = ByteBuffer.allocate(16);
	    buffer.order(ByteOrder.LITTLE_ENDIAN);
	    buffer.putInt((int) abcd.d);
	    buffer.putInt((int) abcd.c);
	    
	    ByteBuffer bb = ByteBuffer.allocate(4);
	    bb.putInt((int)abcd.b);
	    bb.flip();
	    byte[] b = bb.array();
	    buffer.order(ByteOrder.BIG_ENDIAN);
	    buffer.put(b[2]);
	    buffer.put(b[3]);
	    buffer.putInt((int) abcd.a);
	    buffer.put(b[0]);
	    buffer.put(b[1]);
	    
	    return buffer.array();
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
    ABCD fromByteArray(byte[] bytes) {
		ABCD holder = new ABCD();
		holder.a = NewId.bytesToInt(bytes[10],bytes[11],bytes[12] ,bytes[13]);
		holder.b = NewId.bytesToInt(bytes[14],bytes[15],bytes[8],bytes[9]);
		holder.c = NewId.bytesToInt(bytes[7],bytes[6],bytes[5],bytes[4]);
		holder.d = NewId.bytesToInt(bytes[3],bytes[2],bytes[1],bytes[0]);
		
		return holder;
	}
	
	
	
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
			ABCD myLongs = fromByteArray(testArr);
			byte[] resultArr = toByteArray(myLongs);
			System.out.println("test: " + Arrays.toString(testArr));
			System.out.println("result: " + Arrays.toString(resultArr));
			Assert.assertArrayEquals(testArr, resultArr);
		}
	}
	
	
}
