package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;
import org.vamdc.portal.session.queryBuilder.forms.AbstractForm;

public abstract class TreeForm extends AbstractTreeForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4276738328478787746L;
	QueryTreeInterface tree;
	protected Boolean queryable = true;
	
	public TreeForm(QueryTreeInterface tree){
		this.tree=tree;
	}
	
	
	public Boolean getQueryable() {
		return queryable;
	}

	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}
	
}
