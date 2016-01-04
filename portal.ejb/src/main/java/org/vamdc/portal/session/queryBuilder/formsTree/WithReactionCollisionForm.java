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

}
