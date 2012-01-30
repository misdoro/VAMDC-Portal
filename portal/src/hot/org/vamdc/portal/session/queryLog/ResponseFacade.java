package org.vamdc.portal.session.queryLog;

import org.jboss.seam.annotations.In;
import org.vamdc.portal.entity.query.HttpHeadResponse;
import org.vamdc.portal.registry.Client;
import org.vamdc.portal.registry.RegistryFacade;
import org.vamdc.registry.client.RegistryCommunicationException;

public class ResponseFacade {

	@In(create=true) RegistryFacade registryFacade;

	private HttpHeadResponse response;
	
	public ResponseFacade (HttpHeadResponse node){
		this.response = node;
	}

	public String getNode(){
		return registryFacade.getResourceTitle(response.getIvoaID());
	}
	
	public String getStats(){
		if (response!=null)
			return "Sp: "+response.getSpecies()+" -st:"+response.getStates()+" - Pr:"+response.getProcesses();
		return "?";
	}
	
	public String getFullQueryURL(){
		return response.getFullQueryURL();
	}
	
	
}
