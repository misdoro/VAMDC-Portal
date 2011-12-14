package org.vamdc.portal.session;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.vamdc.portal.registry.Client;
import org.vamdc.registry.client.Registry.Service;
import org.vamdc.registry.client.RegistryCommunicationException;

@Scope(ScopeType.APPLICATION)
@Name("registry")
public class RegistryFacade
{
    public int getCount(){
    	try {
			return Client.INSTANCE.get().getIVOAIDs(Service.VAMDC_TAP).size();
		} catch (RegistryCommunicationException e) {
			return -1;
		}
    }
    
    public String getObject(){
    	if (Client.INSTANCE.get()!=null)
    		return Client.INSTANCE.get().toString();
    	return "null";
    }
  //Thread executor
  	//private final Executor exec = Executors.newSingleThreadExecutor();
  	
  	//ClientThread client = new ClientThread();
  	//volatile boolean running=false;
  	
  	public RegistryFacade(){
  		try {
			System.out.println(Client.INSTANCE.get().getIVOAIDs(Service.VAMDC_TAP).size());
		} catch (RegistryCommunicationException e) {
		}
  	}
  	//	running=true;
  		//exec.execute(client);
  	//	System.out.println("Registry thread started");
  	//}
    
}
