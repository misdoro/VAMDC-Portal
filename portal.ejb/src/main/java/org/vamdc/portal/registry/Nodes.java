package org.vamdc.portal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.vamdc.registry.client.Registry;
import org.vamdc.registry.client.Registry.Service;
import org.vamdc.registry.client.RegistryCommunicationException;

@Name("nodes")
@Scope(ScopeType.STATELESS)
public class Nodes
{

	@In private StatusMessages statusMessages;
	
    private Registry registry;

    public Nodes(){
    	this.registry=Client.INSTANCE.get();
    }

    public List<VamdcNode> getNodes(){
    	List<VamdcNode> result = new ArrayList<VamdcNode>();
    	
    	if (registry==null)
    		return result;
    	
    	Collection<String> ivoaIDs=null;
		try {
			ivoaIDs = registry.getIVOAIDs(Service.VAMDC_TAP);
		} catch (RegistryCommunicationException e) {
			statusMessages.add("Registry communication problem: "+e.getMessage());
		}
    	
		if (ivoaIDs==null)
    		return result;
    	
		for (String id:ivoaIDs){
    		result.add(new VamdcNode(id));
    	}
    	
		return result;
    }
  
}
