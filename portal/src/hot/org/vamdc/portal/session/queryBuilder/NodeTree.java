package org.vamdc.portal.session.queryBuilder;

import java.util.Collection;
import java.util.Collections;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.registry.Client;
import org.vamdc.portal.session.queryBuilder.nodeTree.TreeNodeElement;
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

@Name("nodeTree")
@Scope(ScopeType.CONVERSATION)
public class NodeTree{

	@In private StatusMessages statusMessages;
	
	@In private QueryData queryData;
	
	@Logger
	private Log log;
	
	private TreeNodeImpl<TreeNodeElement> root;
	
	private Registry registry;

	private Collection<Restrictable> activeKeys;

	public NodeTree(){
		
		registry = Client.INSTANCE.get();
		root = new TreeNodeImpl<TreeNodeElement>();

		activeKeys = Collections.emptyList();
		
		if (queryData!=null)
			activeKeys=queryData.getKeywords();
		

		try {
			for (String ivoaID:registry.getIVOAIDs(Service.VAMDC_TAP)){
				
				root.addChild(ivoaID, new VamdcNode(root,registry,ivoaID));
			}
		} catch (RegistryCommunicationException e) {
			statusMessages.add("Node filter is unable to communicate with the registry!"+e.getMessage());
		}
	}
	
	public TreeNode<TreeNodeElement> getRoot(){
		if (queryData!=null)
			activeKeys=queryData.getKeywords();
		log.info("keys so far: "+activeKeys.size());
		return root;
		
	}
	
	
	
}
