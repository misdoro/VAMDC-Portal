package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm;

public class MoleculesTreeForm extends MoleculesForm implements FormForFields, TreeFormInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Integer position; 
	private QueryTreeInterface tree;
	
	public MoleculesTreeForm(){
		super();
	}
	

	public MoleculesTreeForm(QueryTreeInterface tree){
		this.tree = tree;
		position = tree.getFormCount();
	}
	
	public MoleculesTreeForm(QueryTreeInterface tree, String prefix){
		this.tree = tree;
		this.setPrefix(prefix);
		position = tree.getFormCount();
	}

	@Override
	public Boolean getQueryable() {
		return true;
	}

	@Override
	public void validate() {
		tree.addForm(this);		
	}
	
	/*@Override
	public String getView() {
		return "/xhtml/query/queryTree/moleculesForm.xhtml";
	}*/
	
	@Override
	public String getTitle() { return "Molecule "; }

}
