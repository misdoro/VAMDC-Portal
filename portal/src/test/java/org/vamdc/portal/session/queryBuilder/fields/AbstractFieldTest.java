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
		field.setValue("Fe");
		String query = field.getQuery();
		assertTrue(query.equals("AtomSymbol = 'Fe'"));
	}
	
	@Test
	public void getInQuery(){
		field.setValue("Fe, Co");
		String query = field.getQuery();
		assertTrue(query.equals("AtomSymbol IN ('Fe','Co')"));
	}

}
