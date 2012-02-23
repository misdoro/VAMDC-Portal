package org.vamdc.portal.session.queryBuilder.fields;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vamdc.dictionary.Restrictable;

public class AbstractFieldTest {
	
	private AbstractField field;
	
	@Before
	public void setupTest(){
		field = new SimpleField(Restrictable.AtomSymbol, null);
	}
	
	@After
	public void clearTest(){
		field = null;
	}
	
	@Test
	public void getSingleQuery() {
		assertQueryEquals("Fe","AtomSymbol = 'Fe'");
	}
	
	@Test
	public void trimSingleQuery(){
		assertQueryEquals("Fe ","AtomSymbol = 'Fe'");
	}
	
	@Test
	public void getInQuery(){
		assertQueryEquals("Fe, Co","AtomSymbol IN ('Fe','Co')");
	}

	public void assertQueryEquals(String parameter, String supposedResult){
		field.setValue(parameter);
		String query = field.getQuery();
		assertTrue(query.equals(supposedResult));
	}
	
}
