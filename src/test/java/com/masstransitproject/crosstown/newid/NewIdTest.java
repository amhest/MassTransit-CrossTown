package com.masstransitproject.crosstown.newid;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import com.masstransitproject.crosstown.newid.providers.StopwatchTickProvider;

public class NewIdTest {

	@Test
	public void test_Should_be_able_to_extract_timestamp() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		NewId id = NewId.Next();

		Timestamp timestamp = id.getTimestamp();

		System.out.println("Now: " + now + "Timestamp: "
				+ timestamp);

		long diff = timestamp.getTime() - now.getTimeInMillis();
		if (diff < 0)
			diff = -diff;

		Assert.assertTrue(diff <= 60 * 1000);
	}

	@Test
	public void test_Should_generate_sequential_ids_quickly() {
		NewId.SetTickProvider(new StopwatchTickProvider());
		NewId.Next();

		int limit = 10;

		NewId[] ids = new NewId[limit];
		for (int i = 0; i < limit; i++)
			ids[i] = NewId.Next();

		for (int i = 0; i < limit - 1; i++) {
			Assert.assertFalse(ids[i] + " equals " + ids[i + 1],
					ids[i].equals(ids[i + 1]));
			System.out.println(ids[i]);
		}
	}
	@Test
	public void test_Should_generate_sequential_ids_quicklyMultiThread() {
		
		Runnable r = new Runnable() {
			public void run() {
				test_Should_generate_sequential_ids_quickly();
		}
		};
		Thread[] ts = new Thread[5];
		for (int i = 0; i <ts.length; i++) {
			ts[i] = new Thread(r);
			ts[i].start();
			
		}
		for (int i = 0; i <ts.length; i++) {
			try {
				ts[i].join();
			} catch (InterruptedException e) {
				//Ignore and wait for next one..
			}
		}
		
	}
		
	@Test	
	public void test_Should_generate_unique_identifiers_with_each_invocation() {
		NewId.Next();

		// Stopwatch timer = Stopwatch.StartNew();
		long start = System.currentTimeMillis();

		int limit = 1024 * 1024;

		NewId[] ids = new NewId[limit];
		for (int i = 0; i < limit; i++)
			ids[i] = NewId.Next();

		long stop = System.currentTimeMillis();
		long elapsed = stop - start;

		for (int i = 0; i < limit - 1; i++) {
			Assert.assertFalse(ids[i] + " equals " + ids[i + 1],
					ids[i].equals(ids[i + 1]));
			String end = ids[i].toString("d",true).substring(32, 32+4);
			if (end == "0000")
				System.out.println(ids[i].toString());
		}

		System.out.println("Generated " + limit + " ids in " + elapsed + "ms ("
				+ (limit / elapsed) + "/ms)");

	}
}
