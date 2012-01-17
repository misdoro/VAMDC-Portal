package org.vamdc.portal.session.queryBuilder;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.portal.registry.Client;
import org.vamdc.portal.session.queryBuilder.nodeTree.VamdcNode;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.Registry.Service;
import org.vamdc.registry.client.RegistryCommunicationException;

/**
 * Class implementing logic for node keywords 
 * node filtering by query keywords
 * and the whole nodes and keywords tree display
 * @author doronin
 *
 */
@Scope(ScopeType.STATELESS)
@Name("nodeFilter")
public class NodeFilter {

	@In private StatusMessages statusMessages;
	
	private TreeNodeImpl<String> root;
	
	private Registry registry;

	public NodeFilter(){
		
		registry = Client.INSTANCE.get();
		root = new TreeNodeImpl<String>();
		root.setData("name1");
		try {
			for (String ivoaID:registry.getIVOAIDs(Service.VAMDC_TAP)){
				root.addChild(ivoaID, new VamdcNode(root,registry,ivoaID));
			}
		} catch (RegistryCommunicationException e) {
			statusMessages.add("Node filter is unable to communicate with the registry!"+e.getMessage());
		}
	}
	
	public TreeNode<String> getRoot(){
		
		return root;
	}
	
}
