package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public abstract class ReactionCollisionForm extends AbstractForm implements FormForFields, TreeFormInterface{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6587803873839744100L;
	protected Integer typeAAtomCount;
	protected Integer typeAMoleculeCount;
	protected Integer typeAParticleCount;
	protected Integer typeBAtomCount;
	protected Integer typeBMoleculeCount;
	protected Integer typeBParticleCount;	
	protected Integer maxElementCount = 5;
	private QueryTreeInterface tree;
	private Boolean queryable = true;
		
	
	private String typeAName;
	private String typeBName;
	
	public ReactionCollisionForm(QueryTreeInterface tree, String typeA, String typeB){
		this.tree = tree;
		this.typeAName = typeA;
		this.typeBName = typeB;		
	}	

	@Override
	public void validate() {
		
		for(int i=0;i<typeAAtomCount;i++){
			tree.addForm(new AtomsTreeForm(tree, typeAName));
		}
		
		for(int i=0;i<typeAParticleCount;i++){
			tree.addForm(new ParticlesTreeForm(tree, typeAName));
		}
		
		for(int i=0;i<typeAMoleculeCount;i++){
			tree.addForm(new MoleculesTreeForm(tree, typeAName));			
		}
		
		for(int i=0;i<typeBAtomCount;i++){
			tree.addForm(new AtomsTreeForm(tree, typeBName));
		}
		
		for(int i=0;i<typeBParticleCount;i++){
			tree.addForm(new ParticlesTreeForm(tree, typeBName));
		}
		
		for(int i=0;i<typeBMoleculeCount;i++){
			tree.addForm(new MoleculesTreeForm(tree, typeBName));			
		}

		this.setQueryable(false);		
	}

	@Override
	public void fieldUpdated(AbstractField field) {
	}
	
	public Integer getTypeAAtomCount() {
		return this.typeAAtomCount;
	}

	public void setTypeAAtomCount(Integer typeAAtomCount) {
		this.typeAAtomCount = typeAAtomCount;
	}
	
	public Integer getTypeAMoleculeCount() {
		return this.typeAMoleculeCount;
	}

	public void setTypeAMoleculeCount(Integer typeAMoleculeCount) {
		this.typeAMoleculeCount = typeAMoleculeCount;
	}
	
	public Integer getTypeAParticleCount() {
		return this.typeAParticleCount;
	}

	public void setTypeAParticleCount(Integer typeAParticleCount) {
		this.typeAParticleCount = typeAParticleCount;
	}

	public Integer getTypeBAtomCount() {
		return typeBAtomCount;
	}

	public void setTypeBAtomCount(Integer typeBAtomCount) {
		this.typeBAtomCount = typeBAtomCount;
	}

	public Integer getTypeBMoleculeCount() {
		return typeBMoleculeCount;
	}

	public void setTypeBMoleculeCount(Integer typeBMoleculeCount) {
		this.typeBMoleculeCount = typeBMoleculeCount;
	}
	
	public Integer getTypeBParticleCount() {
		return typeBParticleCount;
	}

	public void setTypeBParticleCount(Integer typeBParticleCount) {
		this.typeBParticleCount = typeBParticleCount;
	}
	
	public Integer getMaxElementCount() {
		return maxElementCount;
	}

	public void setMaxElementCount(Integer maxElementCount) {
		this.maxElementCount = maxElementCount;
	}

	@Override
	public String getTitle() {
		return "Please, enter the number of "+ typeAName +"(s) and "+typeBName+"(s) you wish to add";
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
