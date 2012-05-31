package org.vamdc.portal.session.queryBuilder.forms;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vamdc.portal.session.queryBuilder.fields.ProxyRangeField;


public class testWLFieldSetup {

	private ProxyRangeField field = null;
	
	@Test
	public void testWlToWl(){
		field.setSelectedField(0);
		field.getConverter().setSelectedOption("mm");
		
		field.setUserHiValue("100");
		field.setUserLoValue("100");
		
		String query = field.getQuery();
		assertTrue(query.contains("RadTransWavelength ="));
		
		System.out.println(query);
	}
	
	@Test
	public void testGHzToWlEqual(){
		field.setSelectedField(1);
		field.getConverter().setSelectedOption("GHz");
		
		field.setUserLoValue("0");
		field.setUserHiValue("10");
		
		
		String query = field.getQuery();
		//assertTrue(query.contains("RadTransWavelength ="));
		
		System.out.println(query);
	}
	
	@Before
	public void init(){
		field = RadiativeForm.setupWLField();
	}
	
	@After
	public void clean(){
		field = null;
	}
	
	
}
