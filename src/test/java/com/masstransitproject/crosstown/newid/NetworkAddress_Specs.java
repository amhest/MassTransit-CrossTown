package com.masstransitproject.crosstown.newid;

import java.io.IOException;

import org.junit.Assert;
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

public class NetworkAddress_Specs  // When_getting_a_network_address_for_the_id_generator
{
	@Test
	public void Should_pull_the_network_adapter_mac_address()
			throws IOException {
		IWorkerIdProvider networkIdProvider = new NetworkAddressWorkerIdProvider();

		byte[] networkId = networkIdProvider.GetWorkerId(0);

		Assert.assertNotNull(networkId);
		Assert.assertTrue(6==networkId.length || 8 == networkId.length);
	}
}
