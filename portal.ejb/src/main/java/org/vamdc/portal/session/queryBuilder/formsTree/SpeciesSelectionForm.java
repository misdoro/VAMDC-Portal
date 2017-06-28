package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class SpeciesSelectionForm extends AbstractForm implements FormForFields, TreeFormInterface{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6587803873839744100L;
	protected Integer atomCount;
	protected Integer moleculeCount;
	protected Integer particleCount;
	protected Integer maxElementCount = 5;
	private QueryTreeInterface tree;
	private Boolean queryable = true;	
	
	public SpeciesSelectionForm(QueryTreeInterface tree){
		this.tree = tree;
	}

	public SpeciesSelectionForm(QueryTreeInterface tree, String prefix){
		this.tree = tree;
		this.setPrefix(prefix);
	}

	
	@Override
	public String getView() {		
		return "/xhtml/query/queryTree/speciesSelectionForm.xhtml";
	}

	@Override
	public void validate() {

		
		for(int i=0;i<atomCount;i++){
			tree.addForm(new AtomsTreeForm(tree));
		}
		
		for(int i=0;i<particleCount;i++){
			tree.addForm(new ParticlesTreeForm(tree));
		}
		
		for(int i=0;i<moleculeCount;i++){
			tree.addForm(new MoleculesTreeForm(tree));
			
		}
		
		this.setQueryable(false);		
	}

	@Override
	public void fieldUpdated(AbstractField field) {
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

	@Override
	public String getTitle() {
		if (this.getPrefix() != null){
			return this.getPrefix();
		}
		
		return null;
	}

	@Override
	public Integer getOrder() {
		return Order.GuidedSpeciesType;
	}	
	
	@Override
	public Boolean getQueryable() {
		return queryable;
	}
	
	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}
	
	public void enable(){
		this.setQueryable(true);
	}

}
