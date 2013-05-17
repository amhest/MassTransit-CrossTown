package com.masstransitproject.crosstown.newid;

import java.nio.ByteBuffer;
import java.util.UUID;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

public class GuidInterop_Specs extends TestCase // When_interoperating_with_the_UUID_type
{

	private Log log = LogFactory.getLog(GuidInterop_Specs.class);

	@Test
	public void Should_convert_from_a_UUID_quickly() {
		UUID g = UUID.randomUUID();

		// TODO check this
		NewId n = new NewId(g.toString());

		String ns = n.toString();
		String gs = g.toString();

		assertEquals(ns, gs);
	}

	@Test
	public void Should_convert_to_UUID_quickly() {
		NewId n = NewId.Next();

		UUID g = n.ToGuid();

		String ns = n.toString();
		String gs = g.toString();

		assertEquals(ns, gs);
	}

	@Test
	public void Should_display_sequentially_for_newid() {
		NewId id = NewId.Next();

		log.trace(id.toString("DS"));
	}

	@Test
	public void Should_make_the_round_trip_successfully_via_bytes() {
		UUID g = UUID.randomUUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(g.getMostSignificantBits());
		bb.putLong(g.getLeastSignificantBits());
		bb.array();
		NewId n = new NewId(bb.array());

		UUID gn = UUID.nameUUIDFromBytes(n.ToByteArray());

		assertEquals(g, gn);
	}

	@Test
	public void Should_match_String_output_b() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				12, 14, 15 };

		UUID g = UUID.nameUUIDFromBytes(bytes);
		NewId n = new NewId(bytes);

		String gs = g.toString();
		String ns = n.toString("B");

		assertEquals(gs, ns);
	}

	@Test
	public void Should_match_String_output_d() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				12, 14, 15 };

		UUID g = UUID.nameUUIDFromBytes(bytes);
		NewId n = new NewId(bytes);

		String gs = g.toString();
		String ns = n.toString("d");

		assertEquals(gs, ns);
	}

	@Test
	public void Should_match_String_output_n() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				12, 14, 15 };

		UUID g = UUID.nameUUIDFromBytes(bytes);
		NewId n = new NewId(bytes);

		String gs = g.toString();
		String ns = n.toString("N");

		assertEquals(gs, ns);
	}

	@Test
	public void Should_match_String_output_p() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				12, 14, 15 };

		UUID g = UUID.nameUUIDFromBytes(bytes);
		NewId n = new NewId(bytes);

		String gs = g.toString();
		String ns = n.toString("P");

		assertEquals(gs, ns);
	}

	@Test
	public void Should_properly_handle_String_passthrough() {
		NewId n = NewId.Next();

		String ns = n.toString("D");

		UUID g = UUID.fromString(ns);

		NewId nn = new NewId(g.toString());

		assertEquals(n, nn);
	}

	// @Test
	// public void Should_support_the_same_constructor()
	// {
	// UUID guid = new UUID(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
	// NewId newid = new NewId(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
	//
	// assertEquals(guid.toString(), newid.toString());
	// }

	@Test
	public void Should_work_from_newid_to_UUID_to_newid() {
		NewId n = NewId.Next();

		UUID g = UUID.nameUUIDFromBytes(n.ToByteArray());
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(g.getMostSignificantBits());
		bb.putLong(g.getLeastSignificantBits());
		;
		NewId ng = new NewId(bb.array());

		log.trace(g.toString());

		assertEquals(n, ng);
	}
}
