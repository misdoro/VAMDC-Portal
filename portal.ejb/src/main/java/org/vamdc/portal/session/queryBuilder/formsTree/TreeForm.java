package org.vamdc.portal.session.queryBuilder.formsTree;

import org.vamdc.portal.session.queryBuilder.QueryTreeInterface;

public abstract class TreeForm implements TreeFormInterface{
	
	QueryTreeInterface tree;
	public TreeForm(QueryTreeInterface tree){
		this.tree=tree;
	}
	
}
