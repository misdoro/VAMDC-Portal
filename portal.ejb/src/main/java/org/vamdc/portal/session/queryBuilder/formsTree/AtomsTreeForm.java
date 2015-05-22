package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.forms.FormForFields;
import org.vamdc.portal.session.queryBuilder.forms.AtomsForm;
import org.vamdc.portal.session.queryBuilder.forms.Order;

public class AtomsTreeForm extends AtomsForm implements FormForFields, TreeFormInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5992593235932991330L;
	protected Integer position; 
	private QueryTreeInterface tree;
	public AtomsTreeForm(QueryTreeInterface tree) {
		//super(tree);
		this.tree = tree;
		position = tree.getFormCount();
	}
	
	public AtomsTreeForm(QueryTreeInterface tree, String prefix) {
		//super(tree);
		this.tree = tree;
		this.setPrefix(prefix);
		position = tree.getFormCount();
	}
	
	public String getObject(){
		return this.toString();
	}

	/*@Override
	public String getView() {
		return "/xhtml/query/queryTree/atomsForm.xhtml";
	}*/

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		tree.addForm(this);
	}
	

	@Override
	public void fieldUpdated(AbstractField field) {
		// TODO Auto-generated method stub
	}

	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return Order.Atoms;
	}

	@Override
	public Boolean getQueryable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getTitle() { return "Atom "; }

}
