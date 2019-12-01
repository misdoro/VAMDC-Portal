package org.vamdc.portal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.portal.species.VamdcNode;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.Registry.Service;

@Name("nodes")
@Scope(ScopeType.STATELESS)
public class Nodes
{
	
    private Registry registry;
    
    private enum NodeStatus{
    	ACTIVE, INACTIVE
    }

    public Nodes(){
    	this.registry=Client.INSTANCE.get();
    }
    
    public List<VamdcNode> getNodes(){
    	return this.getNodes(NodeStatus.ACTIVE);
    }
    
    public List<VamdcNode> getInactiveNodes(){
    	return this.getNodes(NodeStatus.INACTIVE);
    }
    
    private  List<VamdcNode> getNodes(NodeStatus status){
    	if (registry==null)
    		return Collections.emptyList();
    	
    	Collection<String> ivoaIDs = null;
    	
    	if(status == NodeStatus.ACTIVE){
    		ivoaIDs=registry.getIVOAIDs(Service.VAMDC_TAP);
    	}else if(status == NodeStatus.INACTIVE){
    		ivoaIDs=registry.getInactiveIVOAIDs(Service.VAMDC_TAP);
    	}
    	
		if (ivoaIDs==null)
    		return Collections.emptyList();
		
		List<VamdcNode> result = new ArrayList<VamdcNode>();
		for (String id:ivoaIDs){
    		result.add(new VamdcNode(id));
    	}
    	
		Collections.sort(result);
		return result;
    }
  
}
