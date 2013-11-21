package org.vamdc.portal.session.queryBuilder.forms;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.vamdc.portal.session.queryBuilder.QueryData;

public class SpeciesPositionTest {
	private QueryData queryData;
	
	@Test
	public void testAddForm() {
		queryData = new QueryData();
		addAtomsForm("Fe");
		addMoleculesForm("Carbon monoxide");
		for (SpeciesForm form:queryData.getSpeciesForms()){
			System.out.println("position:"+form.getPosition());
			assertTrue(form.getPosition()==1);
		}
		queryData=null;
	}

	@Test
	public void testDeleteFormPosition() {
		queryData = new QueryData();
		addMoleculesForm("Carbon monoxide");
		Form form2=addMoleculesForm("Carbon dioxide");
		addMoleculesForm("Carbon trioxide");
		int count=0;
		for (SpeciesForm form:queryData.getSpeciesForms()){
			count++;
			//Test if we have all three molecules
			switch(form.getPosition()){
			case 1:
				assertEquals(form.getFields().iterator().next().getValue(),"Carbon monoxide");
				break;
			case 2:
				assertEquals(form.getFields().iterator().next().getValue(),"Carbon dioxide");
				break;
			case 3:
				assertEquals(form.getFields().iterator().next().getValue(),"Carbon trioxide");
				break;
			default:
				fail("Some obscure form in query data species");
			}
		}
		assertTrue(count==3);
		queryData.deleteForm(form2);
		
		for (SpeciesForm form:queryData.getSpeciesForms()){
			count++;
			//ensure that we now have only two molecules and the last one has the position 2
			switch(form.getPosition()){
			case 1:
				assertEquals(form.getFields().iterator().next().getValue(),"Carbon monoxide");
				break;
			case 2:
				assertEquals(form.getFields().iterator().next().getValue(),"Carbon trioxide");
				break;
			default:
				fail("Some obscure form in query data species");
			}
		}
		assertTrue(count==5);
		queryData=null;
	}
	
	private Form addAtomsForm(String element) {
		Form form = new AtomsForm();
		form.getFields().iterator().next().setValue(element);
		queryData.addForm(form);
		return form;
	}
	
	private Form addMoleculesForm(String name){
		Form form = new MoleculesForm();
		form.getFields().iterator().next().setValue(name);
		queryData.addForm(form);
		return form;
	}

}
