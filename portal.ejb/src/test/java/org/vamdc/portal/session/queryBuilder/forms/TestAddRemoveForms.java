package org.vamdc.portal.session.queryBuilder.forms;

import static org.junit.Assert.*;

import org.junit.Test;
import org.vamdc.portal.session.queryBuilder.QueryData;

public class TestAddRemoveForms {
	private QueryData queryData;
	
	@Test
	public void testAddTwoProcessForms() {
		queryData=new QueryData();
		RadiativeForm rf=new RadiativeForm();
		CollisionsForm cf=new CollisionsForm(); 
		queryData.addForm(rf);
		queryData.addForm(cf);
		int count=0;
		for (Form frm:queryData.getForms()){
			count++;
			assertEquals(frm,rf);//Suppose that we have only the radiative form
		}
		assertTrue(count==1);//Suppose that we had at least one form
	}

	@Test
	public void testAddDeleteAddProcessForm() {
		queryData=new QueryData();
		RadiativeForm rf=new RadiativeForm();
		CollisionsForm cf=new CollisionsForm(); 
		queryData.addForm(rf);
		queryData.deleteForm(rf);
		queryData.addForm(cf);
		int count=0;
		for (Form frm:queryData.getForms()){
			count++;
			assertEquals(frm,cf);//Suppose that we have only the radiative form
		}
		assertTrue(count==1);//Suppose that we had at least one form
	}
	
}
