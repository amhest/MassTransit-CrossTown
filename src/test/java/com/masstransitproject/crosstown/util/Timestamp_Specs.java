package com.masstransitproject.crosstown.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;



public class Timestamp_Specs  {

	
	@BeforeClass
	public  static void setup() {
		FineGrainTimestamp.init();
		try {
			Thread.currentThread().sleep(1);
		} catch (InterruptedException e) {
			//Ignore
		}
	}

	private final SimpleDateFormat formatter;
	
	public Timestamp_Specs() {
		formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSSZ");
		final TimeZone utc = TimeZone.getTimeZone("UTC");
		formatter.setTimeZone(utc);
		
	}
	@Test
	public void testNoLossiness() {

		long currentNanos = System.nanoTime();
		FineGrainTimestamp ts =  FineGrainTimestamp.fromNanos(currentNanos);
		String coarseTs = formatter.format(new Timestamp(new Date().getTime()))
				.replace(" ", "T");

		// should be same minute to ensure that we are creating a real timestamp
		Assert.assertEquals(
				"Finegrain timestamp should be in same minute as traditional timestamp.",
				ts.toString().substring(0, coarseTs.lastIndexOf(':')),
				coarseTs.substring(0, coarseTs.lastIndexOf(':')));

		Assert.assertEquals("Lost fidelity converting " + currentNanos
				+ " to timestamp.", currentNanos, ts.getTotalNanos());

	}
	@Test
	public void testMath() {
		
		System.out.println(1373395591234070000l-1373395591000260000l);
	}

	@Test
	public void testToString() {

		
		long currentNanos = FineGrainTimestamp.getInstance().getTotalNanos();
		long nanos = System.nanoTime();
		long millis = new Date().getTime();
		Timestamp ts =  FineGrainTimestamp.fromNanos(currentNanos);

		Timestamp coarseTs = new Timestamp(millis);

		String fine = ts.toString();
		String course = formatter.format(coarseTs);

		// prevent serious weirdness
		Assert.assertEquals(
				"Finegrain timestamp should be in same minute as traditional timestamp.",
				fine.substring(0, fine.lastIndexOf('.')),
				course.substring(0, course.lastIndexOf('.')));

		String nanoComponent = fine.subSequence(fine.lastIndexOf('.') + 1,
				fine.lastIndexOf('+')).toString();
		Assert.assertTrue(
				"Nano Component of Timestamp should be subString of total nanos."
						+ nanoComponent + " failed for " + currentNanos, String
						.valueOf(currentNanos).endsWith(nanoComponent));

	}

	@Test
	public void time_in_current_year() {

		Timestamp ts =  FineGrainTimestamp.getInstance();
		

		
		
		Calendar base = Calendar.getInstance();
		
		Calendar fine = Calendar.getInstance();
		fine.setTimeInMillis(ts.getTime());
		System.out.println(fine);
		System.out.println(base);
		Assert.assertEquals(base.get(Calendar.YEAR), fine.get(Calendar.YEAR));

	}
}
