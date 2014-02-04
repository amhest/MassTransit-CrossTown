package com.masstransitproject.crosstown.newid;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.uuid.UUIDType;
import com.fasterxml.uuid.impl.UUIDUtil;

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

public class GuidInterop_Specs  // When_interoperating_with_the_UUID_type
{

	private Logger log = LogManager.getLogger(GuidInterop_Specs.class);

	@Test
	public void should_convert_from_a_UUID_quickly() {
		UUID g = UUID.randomUUID();

		// TODO check this
		NewId n = new NewId(g.toString());

		String ns = n.toString();
		String gs = g.toString();

		Assert.assertEquals(ns, gs);
	}

	@Test
	public void should_convert_to_UUID_quickly() {
		NewId n = NewId.next();

		UUID g = n.toGuid();

		String ns = n.toString();
		String gs = g.toString();

		Assert.assertEquals(ns, gs);
	}

	@Test
	public void should_display_sequentially_for_newid() {
		NewId id = NewId.next();

		log.trace(id.toString("DS"));
	}

	@Test
	@Ignore
	public void should_make_the_round_trip_successfully_via_bytes() {
		UUID g = UUID.randomUUID();

		NewId n = new NewId(g);

		UUID gn = UUIDUtil.constructUUID(UUIDType.TIME_BASED,n.toByteArray());

		Assert.assertEquals(g, gn);
	}

	@Test
	public void should_match_String_output_b() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				12, 14, 15 };

		UUID g = UUID.nameUUIDFromBytes(bytes);
		NewId n = new NewId(g);

		String gs = "{"+g.toString()+"}";
		String ns = n.toString("B");

		Assert.assertEquals(gs, ns);
	}

	@Test
	public void should_match_String_output_d() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				12, 14, 15 };

		UUID g = UUID.nameUUIDFromBytes(bytes);
		NewId n = new NewId(g);

		String gs = g.toString();
		String ns = n.toString("d");

		Assert.assertEquals(gs, ns);
	}

	@Test
	public void should_match_String_output_n() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				12, 14, 15 };

		UUID g = UUID.nameUUIDFromBytes(bytes);
		NewId n = new NewId(g);

		String gs = g.toString().replaceAll("-", "");
		String ns = n.toString("N");

		Assert.assertEquals(gs, ns);
	}

	@Test
	public void should_match_String_output_p() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				12, 14, 15 };

		UUID g = UUID.nameUUIDFromBytes(bytes);
		NewId n = new NewId(g);

		String gs = "("+g.toString()+")";
		String ns = n.toString("P");

		Assert.assertEquals(gs, ns);
	}

	@Test
	public void should_properly_handle_String_passthrough() {
		NewId n = NewId.next();

		String ns = n.toString("D");

		UUID g = UUID.fromString(ns);

		NewId nn = new NewId(g.toString());

		Assert.assertEquals(n, nn);
	}


	@Test
	public void should_work_from_newid_to_UUID_to_newid() {
		NewId n = NewId.next();

		//UUID g = UUIDUtil.constructUUID(UUIDType.TIME_BASED,n.ToByteArray());
		UUID g = n.toGuid();
		
		NewId ng = new NewId(g);

		Assert.assertEquals(n, ng);
	}
	
	@Test
	public void should_work_from_newid_to_bytes_to_newid_for_Specific_uuid_String() {
		
		NewId n = new NewId("00080000-ffc0-ffff-24e0-13005c2898f2");

		System.out.println("n=" + n);
		byte[] bytes = n.toByteArray();

		System.out.println("bytes=" + Arrays.toString((bytes)));
		NewId ng = new NewId(bytes);


		System.out.println("ng=" + ng);
		System.out.println("ngbytes=" + Arrays.toString((ng.toByteArray())));
		Assert.assertEquals(n, ng);
	}
	@Test
	public void should_work_from_newid_to_bytes_to_newid() {
		NewId n = NewId.next();

		System.out.println("n=" + n);
		byte[] bytes = n.toByteArray();

		System.out.println("bytes=" + Arrays.toString((bytes)));
		NewId ng = new NewId(bytes);


		System.out.println("ng=" + ng);
		System.out.println("ngbytes=" + Arrays.toString((ng.toByteArray())));
		Assert.assertEquals(n, ng);
	}

	@Test
	public void should_new_and_from_String_yield_same_bytes() {
		NewId n = NewId.next();

		String s = n.toString();

		NewId n2 = new NewId(s);
	
		byte[] bytes = n.toByteArray();
		byte[] bytes2 = n2.toByteArray();
		System.out.println("n=" + n);
		System.out.println("n2=" + n2);
		System.out.println("bytes=" + Arrays.toString((bytes)));
		System.out.println("bytes2=" + Arrays.toString((bytes2)));

		Assert.assertArrayEquals(bytes,bytes2);
	}
	

	@Test
	public void should_work_from_bytes_to_newid_to_bytes() {
		byte[] bytes= new byte[]{ 0, 0, 29, 10, 70, 113, 1, 1, 51, 24, 19, 0, 69, 109, 55, 57};
		
		NewId n = new NewId(bytes);

		System.out.println("n=" + n);
		byte[] converted = n.toByteArray();

		Assert.assertArrayEquals(bytes, converted);
	}


	@Test
	public void should_work_from_bytes_to_newid_to_bytes_with_neg() {
		byte[] bytes= new byte[]{ 11, 0, 29, -10, 70, 113, 1, 1, -51, 24, 19, 0, -69, 109, 55, 57};
		System.out.println("bytes=" + Arrays.toString((bytes)));
		NewId n = new NewId(bytes);

		System.out.println("n=" + n);
		byte[] converted = n.toByteArray();
		System.out.println("converted=" + Arrays.toString((converted)));
		
		Assert.assertArrayEquals(bytes, converted);
	}
}
