package org.vamdc.portal.session.queryBuilder.forms;

import static org.junit.Assert.*;

import org.junit.Test;
import org.vamdc.portal.session.queryBuilder.QueryTreeController;
import org.vamdc.portal.session.queryBuilder.formsTree.AtomsTreeForm;
import org.vamdc.portal.session.queryBuilder.formsTree.RootForm;
import org.vamdc.portal.session.queryBuilder.formsTree.SpeciesSelectionForm;

public class TestAddRemoveTreeForms {
	private QueryTreeController queryTree;
	
	@Test
	public void testInitialization() {
		queryTree=new QueryTreeController();
		assertTrue(queryTree.getFormCount() == 1);
		assertTrue(queryTree.getForms().get(0) instanceof RootForm);
	}
	
	@Test 
	public void testAddForm(){
		queryTree=new QueryTreeController();
		RootForm rf = new RootForm(queryTree);
		queryTree.addForm(rf);
		assertTrue(queryTree.getFormCount() == 1); // only one RootForm
		SpeciesSelectionForm spSelect = new SpeciesSelectionForm(queryTree);
		queryTree.addForm(spSelect);
		assertTrue(queryTree.getFormCount() == 2);				
		assertTrue(queryTree.getForms().get(0) instanceof RootForm);
		assertTrue(queryTree.getForms().get(1) instanceof SpeciesSelectionForm);		
	}
	
	@Test 
	public void testAddDeleteForm(){
		queryTree=new QueryTreeController();
		SpeciesSelectionForm spSelect = new SpeciesSelectionForm(queryTree);
		queryTree.addForm(spSelect);
		assertTrue(queryTree.getFormCount() == 2);	
		
		AtomsTreeForm atom1 = new AtomsTreeForm(queryTree);
		AtomsTreeForm atom2 = new AtomsTreeForm(queryTree);
		AtomsTreeForm atom3 = new AtomsTreeForm(queryTree);
		
		queryTree.addForm(atom1);
		queryTree.addForm(atom2);
		queryTree.addForm(atom3);
		assertTrue(queryTree.getQueryData().getSpeciesForms().size() == 3);
		
		int pos = 1;
		for(Form f : queryTree.getQueryData().getSpeciesForms()){
			assertTrue(f.getPosition() == pos);
			pos++;
		}
		
		queryTree.removeForm(atom1);
		
		pos = 1;
		for(Form f : queryTree.getQueryData().getSpeciesForms()){
			assertTrue(f.getPosition() == pos);
			pos++;
		}

	}
	
}
