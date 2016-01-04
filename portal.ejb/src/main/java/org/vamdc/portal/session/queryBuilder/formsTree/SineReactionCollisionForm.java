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
}
