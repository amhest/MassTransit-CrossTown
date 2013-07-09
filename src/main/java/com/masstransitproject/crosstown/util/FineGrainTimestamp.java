package com.masstransitproject.crosstown.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FineGrainTimestamp extends Timestamp {

	
		private static final long stopwatch;
		private static final long secondsAsMillis;
		//private static final long extraNanos;
		
		
		
	
	static { // This really just ensures that the JVM is internally
				// consistent

		// Need to do some machinations to not double-count nanos
		
		//TODO this block should be injectable
		 long nanoBase = System.nanoTime();
		 long currentMillis = System.currentTimeMillis();

			System.out.println("nanoBase="+nanoBase);
			System.out.println("currentMillis=" + currentMillis);
		 
			long fractionalSeconds =  (currentMillis % 1000);
			

			System.out.println("fractionalSeconds=" + fractionalSeconds);
			secondsAsMillis = currentMillis - fractionalSeconds;

			System.out.println("secondsAsMillis=" + secondsAsMillis);
			long extraNanos = fractionalSeconds * 1000000;

			System.out.println("extraNanos=" + extraNanos);
			stopwatch = nanoBase-extraNanos;

			System.out.println("stopwatch=" + stopwatch);
		
	}

	private ThreadLocal<DateFormat> defaultFormatter = new ThreadLocal<DateFormat>();

	public static void init() {
		
	}
	
	
	protected static long zeroOutMillis(long timeInMillis) {
		
		long l =  (timeInMillis / 1000) * 1000;
		
		
///DEBUGGIMG
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSSZ");
		final TimeZone utc = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(utc);
		System.out.println(sdf.format(new Date(l)));
		System.out.println(sdf.format(new Date(timeInMillis)));
		return l;
	}

	protected FineGrainTimestamp(long currentTimeZeroMillis) {

		super(currentTimeZeroMillis);

	}
	protected FineGrainTimestamp(long currentTimeZeroMillis, int nanos) {

		super(currentTimeZeroMillis);
		this.setNanos(nanos);

	}

	public static FineGrainTimestamp fromNanos(long timeInNanos) {
		
		System.out.println("timeInNanos=" +timeInNanos);

//		if (timeInNanos < stopwatch) {
//			//Really only an issue for JUnit
//			
//			throw new IllegalStateException("internals not initialized before calling argumented creator.  Call Init first.");
//		}

		long delta = (timeInNanos - stopwatch);// + extraNanos;
		System.out.println("delta=" +delta);
		long secondsDelta = delta / 1000000000; //nanos to seconds
		System.out.println("secondsDelta=" +secondsDelta);
		long nanosDelta =  ((delta % 1000000000) ); //nanos up to seconds
		System.out.println("nanosDelta=" +nanosDelta);
	    long dateZeroMills = secondsAsMillis + (secondsDelta * 1000); 
		System.out.println("dateZeroMills=" +dateZeroMills);
		

		System.out.println("dateZeroMillis=" +dateZeroMills);
	    
//		//Could use some conversion here but not trivial
//		
//		if (conversions.nanosCheck > timeInNanos)
//			throw new IllegalArgumentException(timeInNanos
//					+ " should be a highly granular value greater than "
//					+ conversions.nanosCheck + ". Should you call fromMillis() instead?");

		
		
		return new FineGrainTimestamp(dateZeroMills,(int) nanosDelta);
	}

	public static FineGrainTimestamp fromMillis(long timeInMillis) {

//		if (timeInMillis > conversions.nanosCheck)
//			throw new IllegalArgumentException(timeInMillis
//					+ " appears to be a highly granular value greater than "
//					+ conversions.nanosCheck + ". Should you call fromNanos() instead?");
//
//		if (timeInMillis > conversions.nanosCheck)
//			throw new IllegalArgumentException(
//					timeInMillis
//							+ " is too small to be a reasonable value for a recent time. ( < "
//							+ conversions.millisCheck + ")");

		return new FineGrainTimestamp(timeInMillis);
	}


	@Override
	public String toString() {

		// TODO Need to localize and UTC better.

		if (defaultFormatter.get() == null) {

			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSSSZ");
			final TimeZone utc = TimeZone.getTimeZone("UTC");
			sdf.setTimeZone(utc);

			defaultFormatter.set(sdf);
		}
		String s = defaultFormatter.get().format(this);
		return s.subSequence(0, s.lastIndexOf('.') + 1)
				+ String.valueOf(this.getNanos())
				+ "+0000";
	}

	public static FineGrainTimestamp getInstance() {
		
		return fromNanos(System.nanoTime());
	   
		
	}
	

	public long getTotalNanos() {

		// Zero out the millis, pad and then add nanos.
		long total =  getTime() / 1 * 1000000 + getNanos();
		System.out.println("getNanos=" + getNanos());
		System.out.println("getTime=" +  getTime());
		System.out.println("total=" + total);
		return total;
	}

}
