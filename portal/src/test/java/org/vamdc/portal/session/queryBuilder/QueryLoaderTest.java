package org.vamdc.portal.session.queryBuilder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the queryLoader to check if we can reconstruct queryData forms from the supplied query.
 * @author doronin
 *
 */
public class QueryLoaderTest {

	private QueryData queryData;
	private String query;
	
	@Before
	public void construct(){
		queryData = new QueryData();
		query="";
	}
	
	@After
	public void afterTest(){
		queryData = null;
		query = null;
	}
	
	@Test
	public void testLoadTransitionsWavelengthProbability(){
		query = "select * where (RadTransWavelength >= 100.0 AND RadTransWavelength <= 500.0 AND RadTransProbabilityA >= 1 AND RadTransProbabilityA <= 2)";
		assertQueryLoadsFine(query);
	}
	
	@Test
	public void testLoadTransitionsWavelengthSingleProbability(){
		query = "select * where (RadTransWavelength >= 100.0 AND RadTransWavelength <= 500.0 AND RadTransProbabilityA = 1)";
		assertQueryLoadsFine(query);
	}

	@Test
	public void testFullLoadTransitions(){
		query = "select * where (RadTransWavelength >= 100.0 AND RadTransWavelength <= 500.0 AND upper.StateEnergy >= 500.0 AND upper.StateEnergy <= 600.0 AND lower.StateEnergy >= 600.0 AND lower.StateEnergy <= 700.0 AND RadTransProbabilityA >= 1 AND RadTransProbabilityA <= 2)";
		assertQueryLoadsFine(query);
	}
	
	@Test
	public void testLoadEnvironment(){
		query = "select * where (Temperature >= 1.0 AND Temperature <= 23.0 AND Pressure >= 10000.0 AND Pressure <= 10100.0)";
		assertQueryLoadsFine(query);
	}
	
	/*@Test
	public void testLoadTwoAtoms(){
	    String query = "select * where ((AtomSymbol = 'Co') OR (AtomSymbol = 'Fe'))";
	    
	    assertQueryLoadsFine(query);
	    
	}*/
	
	private void assertQueryLoadsFine(String query) {
		assertTrue(QueryLoader.loadQuery(queryData, query));
		System.out.println("original: "+query);
		System.out.println("rebuilt : "+queryData.getQueryString());
		assertEquals(queryData.getQueryString(),query);
	}
	
	
	
}
