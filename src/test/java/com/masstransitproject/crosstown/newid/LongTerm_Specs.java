package com.masstransitproject.crosstown.newid;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.masstransitproject.crosstown.newid.providers.NetworkAddressWorkerIdProvider;

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

public class LongTerm_Specs extends TestCase // Generating_ids_over_time
{
	private Log log = LogFactory.getLog(LongTerm_Specs.class);

	@Test
	public void Should_keep_them_ordered_for_sql_server() throws IOException {
		NewIdGenerator generator = new NewIdGenerator(
				new TimeLapseTickProvider(),
				new NetworkAddressWorkerIdProvider());
		generator.Next();

		int limit = 1024;

		NewId[] ids = new NewId[limit];
		for (int i = 0; i < limit; i++)
			ids[i] = generator.Next();

		for (int i = 0; i < limit - 1; i++) {
			assertFalse(ids[i].Equals(ids[i + 1]));

			UUID left = ids[i].ToGuid();
			;
			UUID right = ids[i + 1].ToGuid();
			assertTrue(left.compareTo(right) < 0);
			if (i % 128 == 0)
				log.trace(String.format("Normal: {0} Sql: {1}", new Object[] {
						left, ids[i].ToSequentialGuid() }));
		}
	}

	class TimeLapseTickProvider implements ITickProvider {
		// TimeSpan _interval = TimeSpan.FromSeconds(2);
		int _interval = 2;
		Calendar _previous = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

		@Override
		public long getTicks() {
			_previous.roll(Calendar.SECOND, _interval);
			_interval = _interval + 30;
			return _previous.getTimeInMillis();

		}
	}

}