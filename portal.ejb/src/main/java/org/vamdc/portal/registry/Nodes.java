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

    public Nodes(){
    	this.registry=Client.INSTANCE.get();
    }

    public List<VamdcNode> getNodes(){
    	if (registry==null)
    		return Collections.emptyList();
    	
    	Collection<String> ivoaIDs=registry.getIVOAIDs(Service.VAMDC_TAP);
    	
		if (ivoaIDs==null)
    		return Collections.emptyList();
		
		List<VamdcNode> result = new ArrayList<VamdcNode>();
    	
		for (String id:ivoaIDs){
    		result.add(new VamdcNode(id));
    	}
    	
		return result;
    }
  
}
