package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class SineReactionCollisionForm extends ReactionCollisionForm implements FormForFields, TreeFormInterface{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6587803873839744100L;
	
	public SineReactionCollisionForm(QueryTreeInterface tree){
		super(tree, "target", "collider");
	}

	@Override
	public String getView() {		
		return "/xhtml/query/queryTree/sineReactionCollisionForm.xhtml";
	}
	
	
	public Integer getTargetAtomCount() {
		return this.getTypeAAtomCount();
	}

	public void setTargetAtomCount(Integer targetCount) {
		this.setTypeAAtomCount(targetCount); 
	}
	
	public Integer getTargetMoleculeCount() {
		return this.getTypeAMoleculeCount();
	}

	public void setTargetMoleculeCount(Integer targetMoleculeCount) {
		this.setTypeAMoleculeCount(targetMoleculeCount);
	}
	
	public Integer getTargetParticleCount() {
		return this.getTypeAParticleCount();
	}

	public void setTargetParticleCount(Integer targetParticleCount) {
		this.setTypeAParticleCount(targetParticleCount);
	}

	public Integer getColliderAtomCount() {
		return this.getTypeBAtomCount();
	}

	public void setColliderAtomCount(Integer colliderAtomCount) {
		this.setTypeBAtomCount(colliderAtomCount);
	}

	public Integer getColliderMoleculeCount() {
		return this.getTypeBMoleculeCount();
	}

	public void setColliderMoleculeCount(Integer colliderMoleculeCount) {
		this.setTypeBMoleculeCount(colliderMoleculeCount);
	}
	
	public Integer getColliderParticleCount() {
		return this.getTypeBParticleCount();
	}

	public void setColliderParticleCount(Integer colliderParticleCount) {
		this.setTypeBParticleCount(colliderParticleCount);
	}
}
