package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.registry.RegistryFacade;
import org.vamdc.portal.session.queryBuilder.QueryData;

/**
 * Class implementing logic for node keywords 
 * node filtering by query keywords
 * and the whole nodes and keywords tree display
 * @author doronin
 *
 */

@Name("nodeTree")
@Scope(ScopeType.STATELESS)
public class NodeTree{

	@In private RegistryFacade registryFacade;

	@In private QueryData queryData;



	private TreeNodeImpl<TreeNodeElement> setupTree() {
		TreeNodeImpl<TreeNodeElement> root = new TreeNodeImpl<TreeNodeElement>();
		for (String ivoaID:registryFacade.getTapIvoaIDs()){
			root.addChild(ivoaID, new VamdcNode(root,registryFacade,ivoaID,queryData));
		}
		return root;
	}

	public TreeNode<TreeNodeElement> getRoot(){


		return setupTree();

	}

	public Collection<String> getActiveNodes(){
		Collection<String> result = new ArrayList<String>();

		Collection<Restrictable> keywords = queryData.getKeywords();
		for (String ivoaID:registryFacade.getTapIvoaIDs()){
			if (keywords.size()>0 && registryFacade.getRestrictables(ivoaID).containsAll(keywords))
				result.add(ivoaID);
		}
		
		return result;
	}

}
