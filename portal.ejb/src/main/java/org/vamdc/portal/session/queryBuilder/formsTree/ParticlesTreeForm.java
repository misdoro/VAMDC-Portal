package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.ParticlesForm;

public class ParticlesTreeForm extends ParticlesForm implements FormForFields, TreeFormInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7247779540094437626L;
	protected Integer position; 
	private QueryTreeInterface tree;
	
	public ParticlesTreeForm(QueryTreeInterface tree){
		this.tree = tree;
		position = tree.getFormCount();
	}
	
	
	@Override
	public Boolean getQueryable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		tree.addForm(this);
	}
	
	/*@Override
	public String getView() {
		return "/xhtml/query/queryTree/particlesForm.xhtml";
	}*/


	@Override
	public String getTitle() { return "Particle "; }
}
