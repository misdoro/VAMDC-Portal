package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class WithReactionCollisionForm extends ReactionCollisionForm implements FormForFields, TreeFormInterface{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6587803873839744100L;
	
	public WithReactionCollisionForm(QueryTreeInterface tree){
		super(tree, "reactant", "product");
	}

	@Override
	public String getView() {		
		return "/xhtml/query/queryTree/withReactionCollisionForm.xhtml";
	}
	
	
	public Integer getReactantAtomCount() {
		return this.getTypeAAtomCount();
	}

	public void setReactantAtomCount(Integer reactantAtomCount) {
		this.setTypeAAtomCount(reactantAtomCount);;
	}
	
	public Integer getReactantMoleculeCount() {
		return this.getTypeAMoleculeCount();
	}

	public void setReactantMoleculeCount(Integer reactantMoleculeCount) {
		this.setTypeAMoleculeCount(reactantMoleculeCount);
	}
	
	public Integer getReactantParticleCount() {
		return this.getTypeAParticleCount();
	}

	public void setReactantParticleCount(Integer reactantParticleCount) {
		this.setTypeAParticleCount(reactantParticleCount);
	}

	public Integer getProductAtomCount() {
		return this.getTypeBAtomCount();
	}

	public void setProductAtomCount(Integer productAtomCount) {
		this.setTypeBAtomCount(productAtomCount);
	}

	public Integer getProductMoleculeCount() {
		return this.getTypeBMoleculeCount();
	}

	public void setProductMoleculeCount(Integer productMoleculeCount) {
		this.setTypeBMoleculeCount(productMoleculeCount);
	}
	
	public Integer getProductParticleCount() {
		return this.getTypeBParticleCount();
	}

	public void setProductParticleCount(Integer productParticleCount) {
		this.setTypeBParticleCount(productParticleCount);
	}
	

}
