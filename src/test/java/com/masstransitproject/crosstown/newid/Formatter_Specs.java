package com.masstransitproject.crosstown.newid;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.masstransitproject.crosstown.newid.formatters.Base32Formatter;
import com.masstransitproject.crosstown.newid.formatters.ZBase32Formatter;
import com.masstransitproject.crosstown.newid.parsers.Base32Parser;
import com.masstransitproject.crosstown.newid.parsers.ZBase32Parser;

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

public class Formatter_Specs  // Using_the_newid_formatters
{
	@Test
	@Ignore
	public void Should_convert_back_using_parser() {
		NewId n = new NewId("F6B27C7C-8AB8-4498-AC97-3A6107A21320");

		INewIdFormatter formatter = new ZBase32Formatter(true);

		String ns = n.toString(formatter, false);

		INewIdParser parser = new ZBase32Parser();
		NewId newId = parser.Parse(ns);

		Assert.assertEquals(n, newId);
	}

	@Test
	@Ignore
	public void Should_convert_back_using_standard_parser() {
		NewId n = new NewId("F6B27C7C-8AB8-4498-AC97-3A6107A21320");

		INewIdFormatter formatter = new Base32Formatter(true);

		String ns = n.toString(formatter, false);

		INewIdParser parser = new Base32Parser();
		NewId newId = parser.Parse(ns);

		Assert.assertEquals(n, newId);
	}

	@Test
	@Ignore
	public void Should_convert_using_custom_base32_formatting_characters() {
		NewId n = new NewId("F6B27C7C-8AB8-4498-AC97-3A6107A21320");

		INewIdFormatter formatter = new Base32Formatter(
				"0123456789ABCDEFGHIJKLMNOPQRSTUV");

		String ns = n.toString(formatter, false);

		Assert.assertEquals("UQP7OV4AN129HB4N79GGF8GJ10", ns);
	}

	@Test
	@Ignore
	public void Should_convert_using_standard_base32_formatting_characters() {
		NewId n = new NewId("F6B27C7C-8AB8-4498-AC97-3A6107A21320");

		INewIdFormatter formatter = new Base32Formatter(true);

		String ns = n.toString(formatter, false);

		Assert.assertEquals("62ZHY7EKXBCJRLEXHJQQPIQTBA", ns);
	}

	@Test
	@Ignore
	public void Should_convert_using_the_optimized_human_readable_formatter() {
		NewId n = new NewId("F6B27C7C-8AB8-4498-AC97-3A6107A21320");

		INewIdFormatter formatter = new ZBase32Formatter(true);

		String ns = n.toString(formatter, false);

		Assert.assertEquals("6438A9RKZBNJTMRZ8JOOXEOUBY", ns);
	}

	@Test
	@Ignore
	public void Should_translate_often_transposed_characters_to_proper_values() {
		NewId n = new NewId("F6B27C7C-8AB8-4498-AC97-3A6107A21320");

		String ns = "6438A9RK2BNJTMRZ8J0OXE0UBY";

		INewIdParser parser = new ZBase32Parser(true);
		NewId newId = parser.Parse(ns);

		Assert.assertEquals(n, newId);
	}

}