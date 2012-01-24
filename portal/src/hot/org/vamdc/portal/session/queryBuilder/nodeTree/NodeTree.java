package org.vamdc.portal.session.queryBuilder.nodeTree;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.portal.registry.Client;
import org.vamdc.portal.session.queryBuilder.QueryData;
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
@Scope(ScopeType.STATELESS)
public class NodeTree{

	@In private StatusMessages statusMessages;
	
	@In private QueryData queryData;

	private TreeNodeImpl<TreeNodeElement> setupTree() {
		TreeNodeImpl<TreeNodeElement> root;
		Registry registry = Client.INSTANCE.get();
		root = new TreeNodeImpl<TreeNodeElement>();
		try {
			for (String ivoaID:registry.getIVOAIDs(Service.VAMDC_TAP)){
				
				root.addChild(ivoaID, new VamdcNode(root,registry,ivoaID,queryData));
			}
		} catch (RegistryCommunicationException e) {
			statusMessages.add("Node filter is unable to communicate with the registry!"+e.getMessage());
		}
		return root;
	}
	
	public TreeNode<TreeNodeElement> getRoot(){
		
		
		return setupTree();
		
	}
	
	public Collection<String> getActiveNodes(){
		Collection<String> result = new ArrayList<String>();

		Registry registry = Client.INSTANCE.get();
		Collection<Restrictable> keywords = queryData.getKeywords();
		
		try {
			for (String ivoaID:registry.getIVOAIDs(Service.VAMDC_TAP)){
				if (keywords.size()>0 && registry.getRestrictables(ivoaID).containsAll(keywords)){
					result.add(ivoaID);
				}
			}
		} catch (RegistryCommunicationException e) {
			
		}
		
		return result;
	}
	
}
