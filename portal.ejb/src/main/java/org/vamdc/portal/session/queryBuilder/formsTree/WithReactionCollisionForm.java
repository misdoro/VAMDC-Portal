package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class WithReactionCollisionForm extends AbstractForm implements FormForFields, TreeFormInterface{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6587803873839744100L;
	protected Integer reactantAtomCount;
	protected Integer reactantMoleculeCount;
	protected Integer reactantParticleCount;
	protected Integer productAtomCount;
	protected Integer productMoleculeCount;
	protected Integer productParticleCount;
	protected Integer maxElementCount = 5;
	private QueryTreeInterface tree;
	private Boolean queryable = true;
	private Integer formsCount = 0;
	
	public WithReactionCollisionForm(QueryTreeInterface tree){
		this.tree = tree;
	}

	@Override
	public String getView() {		
		return "/xhtml/query/queryTree/withReactionCollisionForm.xhtml";
	}

	@Override
	public void validate() {
		for(int i=0;i<reactantAtomCount;i++){
			tree.addForm(new AtomsTreeForm(tree, "reactant"));
		}
		
		for(int i=0;i<reactantParticleCount;i++){
			tree.addForm(new ParticlesTreeForm(tree, "reactant"));
		}
		
		for(int i=0;i<reactantMoleculeCount;i++){
			tree.addForm(new MoleculesTreeForm(tree, "reactant"));			
		}
		
		for(int i=0;i<productAtomCount;i++){
			tree.addForm(new AtomsTreeForm(tree, "product"));
		}
		
		for(int i=0;i<productParticleCount;i++){
			tree.addForm(new ParticlesTreeForm(tree, "product"));
		}
		
		for(int i=0;i<productMoleculeCount;i++){
			tree.addForm(new MoleculesTreeForm(tree, "product"));			
		}

		this.setQueryable(false);		
	}

	@Override
	public void fieldUpdated(AbstractField field) {
	}
	
	public Integer getReactantAtomCount() {
		return this.reactantAtomCount;
	}

	public void setReactantAtomCount(Integer reactantAtomCount) {
		this.reactantAtomCount = reactantAtomCount;
	}
	
	public Integer getReactantMoleculeCount() {
		return this.reactantMoleculeCount;
	}

	public void setReactantMoleculeCount(Integer reactantMoleculeCount) {
		this.reactantMoleculeCount = reactantMoleculeCount;
	}
	
	public Integer getReactantParticleCount() {
		return this.reactantParticleCount;
	}

	public void setReactantParticleCount(Integer reactantParticleCount) {
		this.reactantParticleCount = reactantParticleCount;
	}

	public Integer getProductAtomCount() {
		return productAtomCount;
	}

	public void setProductAtomCount(Integer productAtomCount) {
		this.productAtomCount = productAtomCount;
	}

	public Integer getProductMoleculeCount() {
		return productMoleculeCount;
	}

	public void setProductMoleculeCount(Integer productMoleculeCount) {
		this.productMoleculeCount = productMoleculeCount;
	}
	
	public Integer getProductParticleCount() {
		return productParticleCount;
	}

	public void setProductParticleCount(Integer productParticleCount) {
		this.productParticleCount = productParticleCount;
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
