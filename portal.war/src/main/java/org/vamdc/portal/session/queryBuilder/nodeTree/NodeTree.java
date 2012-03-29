package org.vamdc.portal.session.queryBuilder.nodeTree;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
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
public class NodeTree{

	@In(create=true) private RegistryFacade registryFacade;

	@In private QueryData queryData;



	private TreeNode<TreeNodeElement> setupTree() {
		TreeNode<TreeNodeElement> root = new TreeNodeImpl<TreeNodeElement>();
		for (String ivoaID:registryFacade.getTapIvoaIDs()){
			root.addChild(ivoaID, new VamdcNode(registryFacade,ivoaID,queryData));
		}
		return root;
	}

	public TreeNode<TreeNodeElement> getRoot(){


		return setupTree();

	}



}
