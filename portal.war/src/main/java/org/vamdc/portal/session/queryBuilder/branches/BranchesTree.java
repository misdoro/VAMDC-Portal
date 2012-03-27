package org.vamdc.portal.session.queryBuilder.branches;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;
import org.vamdc.dictionary.Requestable;
import org.vamdc.portal.session.queryBuilder.QueryData;

/**
 * Class implementing branches selector tree view
 *
 */
@Name("branches")
@Scope(ScopeType.STATELESS)
public class BranchesTree {
	
	@In QueryData queryData;
	
	private RequestNode root;
	
	public BranchesTree(){

		root = new RequestNode(new RootKeyword(),"XSAMS");
		root.addChild(0, new RequestNode(Requestable.Species,"Species"));
		root.addChild(1, new RequestNode(Requestable.Processes,"Processes"));
		
		
		
	}

	public RequestNode getRoot() {
		return root;
	}
	
	public boolean isOpen(){
		return true;
	}
	
}
