package org.vamdc.portal.registry;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Collection;

import org.junit.Test;

public class RegistryFacadeTest {
	private static RegistryFacade facade;
	static{
		System.out.println("Init the regisry...");
		facade=new RegistryFacade();
		facade.forceUpdate();
		System.out.println("The registry is updated.");
	}
	
	/**
	 * Test that we can get resource titles for all registered nodes.
	 */
	@Test
	public void testGetResourceTitle() {
		Collection<String> ivoaids=facade.getTapIvoaIDs();
		assertNotNull(ivoaids);
		assertTrue(ivoaids.size()>0);
		for (String ivoaid:ivoaids){
			String title=facade.getResourceTitle(ivoaid);
			assertNotNull(title);
			assertTrue(title.length()>0);
		}
	}

	@Test
	public void testGetResourceDescription() {
		Collection<String> ivoaids=facade.getTapIvoaIDs();
		assertNotNull(ivoaids);
		assertTrue(ivoaids.size()>0);
		for (String ivoaid:ivoaids){
			String descr=facade.getResourceDescription(ivoaid);
			assertNotNull(descr);
			assertTrue(descr.length()>0);
		}
	}
	
	@Test
	public void testGetNumberOfInputs() {
		Collection<String> ivoaids=facade.getConsumerIvoaIDs();
		assertNotNull(ivoaids);
		assertTrue(ivoaids.size()>0);
		for (String ivoaid:ivoaids){
			Integer numInputs=facade.getConsumerNumberOfInputs(ivoaid);
			assertNotNull(numInputs);
			assertTrue(numInputs>0);
		}
	}

	@Test
	public void testGetConsumerServiceURL() {
		Collection<String> ivoaids=facade.getConsumerIvoaIDs();
		assertNotNull(ivoaids);
		assertTrue(ivoaids.size()>0);
		for (String ivoaid:ivoaids){
			URL serviceURL=facade.getConsumerServiceURL(ivoaid);
			assertNotNull(serviceURL);
			assertTrue(serviceURL.getProtocol().contains("http"));
			System.out.println(serviceURL.toString());
			//Can't really check the serviceURL, they can be any valid HTTP URLs.
		}
	}

}
