package org.vamdc.portal.session.queryBuilder.fields;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vamdc.dictionary.Restrictable;

public class RangeFieldTest {

	private RangeField field;

	@Test
	public void testEqualRange(){
		assertQueryEquals("0","0",KEYWORD+" = 0");
	}
	
	@Test
	public void testNormalRange(){
		assertQueryEquals("0","1",KEYWORD+" >= 0 AND "+KEYWORD+" <= 1");
	}
	
	@Test
	public void testInverseRange(){
		assertQueryEquals("1","0",KEYWORD+" >= 0 AND "+KEYWORD+" <= 1");
	}
	
	@Test
	public void testSingleRangeFrom(){
		assertQueryEquals("0","",KEYWORD+" >= 0");
	}
	
	@Test
	public void testSingleRangeTo(){
		assertQueryEquals("","1",KEYWORD+" <= 1");
	}
	
	@Test
	public void testNotNumber(){
		assertQueryEquals("asdf","ghij","");
	}
	
	private final static String KEYWORD = "IonCharge";
	@Before
	public void setupTest(){
		field = new RangeField(Restrictable.IonCharge, null);
	}
	
	@After
	public void clearTest(){
		field = null;
	}
	
	private void assertQueryEquals(String from, String to, String supposedResult){
		field.setLoValue(from);
		field.setHiValue(to);
		String query = field.getQuery();
		assertTrue(query.equals(supposedResult));
	}
	
}
