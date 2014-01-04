package com.masstransitproject.crosstown.newid;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.masstransitproject.crosstown.newid.providers.StopwatchTickProvider;

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

public class NewId_Specs  // Using_the_newid_generator
{
	private Logger log = LogManager.getLogger(NewId_Specs.class);

	@Test
	public void Should_be_able_to_extract_timestamp() {
		Calendar now = Calendar.getInstance();
		NewId id = NewId.Next();

		Date timestamp = id.getTimestamp();

		log.trace(String.format("Now: {0}, Timestamp: {1}", new Object[] { now,
				timestamp }));

		long difference = now.getTimeInMillis() - timestamp.getTime();

		Assert.assertTrue(difference <= 60000);
	}

	@Test
	public void Should_generate_sequential_ids_quickly() {
		NewId.SetTickProvider(new StopwatchTickProvider());
		NewId.Next();

		int limit = 10;

		NewId[] ids = new NewId[limit];
		for (int i = 0; i < limit; i++)
			ids[i] = NewId.Next();

		for (int i = 0; i < limit - 1; i++) {
			Assert.assertFalse(ids[i].equals(ids[i + 1]));
			log.trace(ids[i].toString());
		}
	}

	@Test
	public void Should_generate_unique_identifiers_with_each_invocation() {
		NewId.Next();

		long start = System.currentTimeMillis();

		int limit = 1024 * 1024;

		NewId[] ids = new NewId[limit];
		for (int i = 0; i < limit; i++)
			ids[i] = NewId.Next();

		long stop = System.currentTimeMillis();

		for (int i = 0; i < limit - 1; i++) {
			Assert.assertFalse(ids[i].equals(ids[i + 1]));
			String end = ids[i].toString().substring(32, 32+4);
			if (end == "0000")
				log.trace(ids[i].toString());
		}

		log.trace(String.format("Generated {0} ids in {1}ms ({2}/ms)",
				new Object[] { limit, stop - start, limit / (stop - start) }));

	}

	@Test
	public void Should_generate_multiple_timestamps() {
		NewId.Next();
		HashSet tss = new HashSet();
		long start = System.currentTimeMillis();

		int limit = 1024 * 1024;

		NewId[] ids = new NewId[limit];
		for (int i = 0; i < limit; i++) {
			ids[i] = NewId.Next();
			
			if (tss.size() < 100) {
				System.out.println(ids[i].getTimestamp());
				
			}
		}
		
		long stop = System.currentTimeMillis();

		for (int i = 0; i < limit - 1; i++) {
			Assert.assertFalse(ids[i].equals(ids[i + 1]));
			String end = ids[i].toString().substring(32, 32+4);
			if (end == "0000")
				log.trace(ids[i].toString());
		}
 
		log.trace(String.format("Generated {0} ids in {1}ms ({2}/ms)",
				new Object[] { limit, stop - start, limit / (stop - start) }));

	}
	
}