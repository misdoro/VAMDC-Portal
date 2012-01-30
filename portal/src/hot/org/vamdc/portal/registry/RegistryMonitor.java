package org.vamdc.portal.registry;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.vamdc.registry.client.Registry.Service;
import org.vamdc.registry.client.RegistryCommunicationException;

@Scope(ScopeType.APPLICATION)
@Startup
@Name("registry")
public class RegistryMonitor
{
	
  	@Destroy
  	public void stop(){
  		Client.INSTANCE.stopUpdates();
  	}
	
    public int getCount(){
    	if (Client.INSTANCE.get()!=null)
    		try {
    			return Client.INSTANCE.get().getIVOAIDs(Service.VAMDC_TAP).size();
    		} catch (RegistryCommunicationException e) {
    			return -1;
    		}
    	return -1;
    }
    
    public String getObject(){
    	if (Client.INSTANCE.get()!=null)
    		return Client.INSTANCE.get().toString();
    	return "null";
    }
    
  	public RegistryMonitor(){
  		if (Client.INSTANCE.get()!=null)
  			System.out.println(Client.INSTANCE.get());
		
  	}
  	

  	
    
}
