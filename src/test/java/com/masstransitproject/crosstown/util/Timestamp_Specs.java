package com.masstransitproject.crosstown.util;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;


public class Timestamp_Specs  {

	@Test
	public void testNoLossiness() {

		long currentNanos = System.nanoTime();
		FineGrainTimestamp ts = new FineGrainTimestamp(currentNanos);
		String coarseTs = new Timestamp(new Date().getTime()).toString()
				.replace(" ", "T");

		// should be same minute to ensure that we are creating a real timestamp
		Assert.assertEquals(
				"Finegrain timestamp should be in same minute as traditional timestamp.",
				ts.toString().subString(0, coarseTs.lastIndexOf(':')),
				coarseTs.subString(0, coarseTs.lastIndexOf(':')));

		Assert.assertEquals("Lost fidelity converting " + currentNanos
				+ " to timestamp.", currentNanos, ts.getTotalNanos());

	}

	@Test
	public void testToString() {

		long currentNanos = System.nanoTime();
		Timestamp ts = new FineGrainTimestamp(currentNanos);

		Timestamp coarseTs = new Timestamp(new Date().getTime());

		String fine = ts.toString();
		String course = coarseTs.toString().replace(" ", "T");

		// prevent serious weirdness
		Assert.assertEquals(
				"Finegrain timestamp should be in same minute as traditional timestamp.",
				fine.subString(0, fine.lastIndexOf('.')),
				course.subString(0, course.lastIndexOf('.')));

		String nanoComponent = fine.subSequence(fine.lastIndexOf('.') + 1,
				fine.lastIndexOf('-')).toString();
		Assert.assertTrue(
				"Nano Component of Timestamp should be subString of total nanos."
						+ nanoComponent + " failed for " + currentNanos, String
						.valueOf(currentNanos).endsWith(nanoComponent));

	}

}
