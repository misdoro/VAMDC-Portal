package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;

public class SpeciesSelectionForm extends TreeForm implements FormForFields{


	protected Integer atomCount;
	protected Integer moleculeCount;
	protected Integer particleCount;
	protected Integer maxElementCount = 5;
	
	
	public SpeciesSelectionForm(QueryTreeInterface tree){
		super(tree);
		
	}

	@Override
	public String getView() {
		return "/xhtml/query/queryTree/speciesSelectionForm.xhtml";
	}

	@Override
	public void validate() {
		for(int i=0;i<atomCount;i++){
			tree.addForm(new AtomsForm(tree));
		}
		
		this.setQueryable(false);
		
	}

	@Override
	public void fieldUpdated(AbstractField field) {
		System.out.println("Field "+field.getTitle()+" was updated!");		
	}
	
	public Integer getAtomCount() {
		return atomCount;
	}

	public void setAtomCount(Integer atomCount) {
		this.atomCount = atomCount;
	}

	public Integer getMoleculeCount() {
		return moleculeCount;
	}

	public void setMoleculeCount(Integer moleculeCount) {
		this.moleculeCount = moleculeCount;
	}

	public Integer getParticleCount() {
		return particleCount;
	}

	public void setParticleCount(Integer particleCount) {
		this.particleCount = particleCount;
	}
	
	public Integer getMaxElementCount() {
		return maxElementCount;
	}

	public void setMaxElementCount(Integer maxElementCount) {
		this.maxElementCount = maxElementCount;
	}	

}
