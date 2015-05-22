package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class SineReactionCollisionForm extends AbstractForm implements FormForFields, TreeFormInterface{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6587803873839744100L;
	protected Integer targetAtomCount;
	protected Integer targetMoleculeCount;
	protected Integer targetParticleCount;
	protected Integer colliderAtomCount;
	protected Integer colliderMoleculeCount;
	protected Integer colliderParticleCount;
	protected Integer maxElementCount = 5;
	private QueryTreeInterface tree;
	private Boolean queryable = true;
	private Integer formsCount = 0;
	
	public SineReactionCollisionForm(QueryTreeInterface tree){
		this.tree = tree;
	}

	@Override
	public String getView() {		
		return "/xhtml/query/queryTree/sineReactionCollisionForm.xhtml";
	}

	@Override
	public void validate() {
		for(int i=0;i<targetAtomCount;i++){
			tree.addForm(new AtomsTreeForm(tree, "target"));
		}
		
		for(int i=0;i<targetParticleCount;i++){
			tree.addForm(new ParticlesTreeForm(tree, "target"));
		}
		
		for(int i=0;i<targetMoleculeCount;i++){
			tree.addForm(new MoleculesTreeForm(tree, "target"));			
		}
		
		for(int i=0;i<colliderAtomCount;i++){
			tree.addForm(new AtomsTreeForm(tree, "collider"));
		}
		
		for(int i=0;i<colliderParticleCount;i++){
			tree.addForm(new ParticlesTreeForm(tree, "collider"));
		}
		
		for(int i=0;i<colliderMoleculeCount;i++){
			tree.addForm(new MoleculesTreeForm(tree, "collider"));			
		}

		this.setQueryable(false);		
	}

	@Override
	public void fieldUpdated(AbstractField field) {
	}
	
	public Integer getTargetAtomCount() {
		return this.targetAtomCount;
	}

	public void setTargetAtomCount(Integer targetCount) {
		this.targetAtomCount = targetCount;
	}
	
	public Integer getTargetMoleculeCount() {
		return this.targetMoleculeCount;
	}

	public void setTargetMoleculeCount(Integer targetMoleculeCount) {
		this.targetMoleculeCount = targetMoleculeCount;
	}
	
	public Integer getTargetParticleCount() {
		return this.targetParticleCount;
	}

	public void setTargetParticleCount(Integer targetParticleCount) {
		this.targetParticleCount = targetParticleCount;
	}

	public Integer getColliderAtomCount() {
		return colliderAtomCount;
	}

	public void setColliderAtomCount(Integer colliderAtomCount) {
		this.colliderAtomCount = colliderAtomCount;
	}

	public Integer getColliderMoleculeCount() {
		return colliderMoleculeCount;
	}

	public void setColliderMoleculeCount(Integer colliderMoleculeCount) {
		this.colliderMoleculeCount = colliderMoleculeCount;
	}
	
	public Integer getColliderParticleCount() {
		return colliderParticleCount;
	}

	public void setColliderParticleCount(Integer colliderParticleCount) {
		this.colliderParticleCount = colliderParticleCount;
	}
	
	public Integer getMaxElementCount() {
		return maxElementCount;
	}

	public void setMaxElementCount(Integer maxElementCount) {
		this.maxElementCount = maxElementCount;
	}

	@Override
	public String getTitle() {
		return "Please, enter the number of targets and colliders you wish to add";
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
