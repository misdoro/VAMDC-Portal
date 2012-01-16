package org.vamdc.portal.session.queryBuilder;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.richfaces.model.TreeNodeImpl;
import org.vamdc.portal.registry.Client;
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
	
	private TreeNodeImpl<?> root;
	
	private Registry registry;

    private NodeFilter(){
    	this.registry=Client.INSTANCE.get();
    	
    	root = new TreeNodeImpl<String>();
    
    	try {
			for (String ivoaID:registry.getIVOAIDs(Service.VAMDC_TAP)){
				root.addChild(ivoaID, new TreeNode(ivoaID));
			}
		} catch (RegistryCommunicationException e) {
			statusMessages.add("Registry communication problem: "+e.getMessage());
		}
    }
	
	public class VNode extends TreeNodeImpl<TreeNode>{

		private static final long serialVersionUID = -5367332635482457672L;
		public TreeNode(String ivoaID){
			
		}
		
	}
	
	public TreeNodeImpl<?> getRoot(){
		return root;
	}
	
}
