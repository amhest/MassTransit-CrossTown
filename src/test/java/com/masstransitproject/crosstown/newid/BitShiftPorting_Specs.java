package com.masstransitproject.crosstown.newid;


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
	
	
	
}
