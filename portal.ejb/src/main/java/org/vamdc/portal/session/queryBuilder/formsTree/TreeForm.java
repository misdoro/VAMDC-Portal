package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;

public abstract class TreeForm implements TreeFormInterface{
	
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
