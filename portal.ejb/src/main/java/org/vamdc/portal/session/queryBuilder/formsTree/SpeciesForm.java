package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;

public class SpeciesForm extends TreeForm implements FormForFields{

	public SpeciesForm(QueryTreeInterface tree) {
		super(tree);
		// TODO Auto-generated constructor stub
	}
	
	private void buildForms(Integer atomCount){
		for(int i=0;i<atomCount;i++){
			tree.addForm(new AtomsForm(tree));
		}		
	}

	@Override
	public String getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fieldUpdated(AbstractField field) {
		// TODO Auto-generated method stub
		
	}

}
