package org.vamdc.portal.session.queryBuilder.branches;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.Requestable;
import org.vamdc.dictionary.RequestableLogic;
import org.vamdc.dictionary.Keyword;
/**
 * Class implementing branches selector tree view
 *
 */
@Name("branches")
@Scope(ScopeType.STATELESS)
public class BranchesTree {
	
	public TreeNode<Keyword> getRequestNode(Requestable key){
		TreeNode<Keyword> result = new TreeNodeImpl<Keyword>();
		RequestableLogic logic = org.vamdc.dictionary.Factory.getRequestableLogic();
		result.setData(key);
		for (Requestable children:logic.getLogicChildren(key)){
			result.addChild(children, getRequestNode(children));
		}
		return result;
	}

	public TreeNode<Keyword> getRoot() {
		TreeNode<Keyword> root = new TreeNodeImpl<Keyword>();
		root.setData(new RootKeyword());
		root.addChild(0, getRequestNode(Requestable.Species));
		root.addChild(1, getRequestNode(Requestable.Processes));
		
		return root;
	}
	

}
