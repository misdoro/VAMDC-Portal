package org.vamdc.portal.session.queryBuilder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.vamdc.portal.session.queryBuilder.forms.AtomsForm;
import org.vamdc.portal.session.queryBuilder.forms.Form;

public class QueryGeneratorTest {
	
	private QueryData queryData;
	
	@Before
	public void construct(){
		queryData = new QueryData();
	}
	
	@Test
	public void testOrUnprefixed(){
		addAtomsForm("H","reactant");
		addAtomsForm("He","product");
		addAtomsForm("Li","");
		addAtomsForm("Be","reactant");
		addAtomsForm("B","");
		addAtomsForm("C","product");
		addAtomsForm("N","");
		
		String query = queryData.getQueryString();
		assertTrue(query.contains("OR"));
		assertTrue(query.contains("AND"));
		assertTrue(query.contains("reactant0"));
		assertTrue(query.contains("reactant1"));
		assertTrue(query.contains("product0"));
		assertTrue(query.contains("product1"));
	}
	
	private void addAtomsForm(String element,String prefix) {
		Form form = new AtomsForm();
		queryData.addForm(form);
		form.getFields().iterator().next().setValue(element);
		form.setPrefix(prefix);
	}
	
}
