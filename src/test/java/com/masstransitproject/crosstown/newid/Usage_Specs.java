package com.masstransitproject.crosstown.newid;

import java.nio.ByteBuffer;
import java.util.UUID;



import org.junit.Assert;
import org.junit.Test;

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

public class Usage_Specs  // Using_a_new_id
{
	@Test
	public void Should_format_just_like_a_default_UUID_formatter() {
		NewId newId = new NewId();

		Assert.assertEquals("00000000-0000-0000-0000-000000000000", newId.toString());
	}

	@Test
	public void Should_format_just_like_a_fancy_UUID_formatter() {
		NewId newId = new NewId();

		Assert.assertEquals("{00000000-0000-0000-0000-000000000000}",
				newId.toString("B"));
	}

	@Test
	public void Should_format_just_like_a_narrow_UUID_formatter() {
		NewId newId = new NewId();

		Assert.assertEquals("00000000000000000000000000000000", newId.toString("N"));
	}

	@Test
	public void Should_format_just_like_a_parenthesis_UUID_formatter() {
		NewId newId = new NewId();

		Assert.assertEquals("(00000000-0000-0000-0000-000000000000)",
				newId.toString("P"));
	}

	@Test
	public void Should_work_from_UUID_to_newid_to_UUID() {
		UUID g = UUID.randomUUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(g.getMostSignificantBits());
		bb.putLong(g.getLeastSignificantBits());

		NewId n = new NewId(bb.array());

		String gs = g.toString();// g.toString("d");
		String ns = n.toString("d");

		Assert.assertEquals(gs, ns);
	}
}
