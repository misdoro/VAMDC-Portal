package org.vamdc.portal.session.queryBuilder.forms;

import static org.junit.Assert.*;

import org.junit.Test;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.session.queryBuilder.QueryData;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;

public class UtilFormTest {
	
	private QueryData queryData;
	
	@Test
	public void testDoiQueryIn(){
		queryData = new QueryData();
		Form form = new UtilForm();
		queryData.addForm(form);
		for (AbstractField field:form.getFields()){
			if(field.getKeyword().equals(Restrictable.SourceDOI)){
				field.setValue("10.1016/S0009-2614(03)01096-0");
			}
		}
		String query = queryData.getQueryString();
		System.out.println(query);
		assertTrue(query.contains("10.1016/S0009-2614(03)01096-0"));
		
	}
	
}
